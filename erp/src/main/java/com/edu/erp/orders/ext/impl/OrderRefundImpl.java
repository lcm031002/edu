package com.edu.erp.orders.ext.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.dao.TLockDao;
import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.model.TCourseLadder;
import com.edu.erp.model.TLadder;
import com.edu.erp.model.TLock;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.model.TabChangeCourse;
import com.edu.erp.orders.ext.IOrderRefund;
import com.edu.erp.orders.ext.IOrderYDY;
import com.edu.erp.orders.service.ChangeCourseService;
import com.edu.erp.orders.service.ChangeCourseTimesService;
import com.edu.erp.orders.service.OrderChangeCheckService;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.orders.service.TcOrderCourseService;

@Service(value = "orderRefund")
public class OrderRefundImpl implements IOrderRefund {

    @Resource(name = "changeCourseService")
    private ChangeCourseService changeCourseService;

    @Resource(name = "changeCourseTimesService")
    private ChangeCourseTimesService changeCourseTimesService;

    @Resource(name = "orderChangeService")
    private OrderChangeService orderChangeService;

    @Resource(name = "orderChangeCheckService")
    private OrderChangeCheckService orderChangeCheckService;

    @Resource(name = "tcOrderCourseService")
    private TcOrderCourseService tcOrderCourseService;

    @Resource(name = "iOrderYDY")
    private IOrderYDY iOrderYDY;

    @Resource(name = "tOrderCourseDao")
    private TOrderCourseDao tOrderCourseDao;

    @Resource(name = "attendanceDao")
    private AttendanceDao attendanceDao;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    /**
     * 订单退费数据准备
     * 
     * @param refundMap
     *            退费订单信息 order_detail_id 订单课程编号 course_cnt 退费课时 course_times
     *            退费课次 change_no 批改号，自动生成的唯一序号
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    @Override
    public void readyPremium(Map<String, Object> refundMap, Long businessType) throws Exception {
        Long orderCourseId = NumberUtils.object2Long(refundMap.get("order_detail_id"));
        Integer refundCourseCount = NumberUtils.object2Integer(refundMap.get("course_cnt"));
        String refundCourseTimes = (String) refundMap.get("course_times");
        String changeNo = (String) refundMap.get("change_no");
        Integer premiumType = NumberUtils.object2Integer(refundMap.get("premiumType"));

        // 生成课程批改信息
        TabChangeCourse changeCourse = new TabChangeCourse();
        changeCourse.setOrder_course_id(orderCourseId);
        changeCourse.setCourse_times(refundCourseCount);
        changeCourse.setChange_no(changeNo);
        changeCourse.setPremium_type(premiumType);
        changeCourse.setChange_user(NumberUtils.object2Long(refundMap.get("user_id")));
        changeCourse.setFee_deduction_amount(NumberUtils.object2Double(refundMap.get("p_premium_deduction_amount")));
        this.changeCourseService.add(changeCourse);

        // 生成课次批改信息
        this.changeCourseTimesService.add(changeCourse.getId(), refundCourseTimes, changeNo);

        // 获取订单课程信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", orderCourseId);
        List<TOrderCourse> orderCourseList = this.tOrderCourseDao.queryOrderCourse(paramMap);
        if (!CollectionUtils.isEmpty(orderCourseList)) {
            calculate(changeCourse, orderCourseList.get(Constants.LIST_FIRST_INDEX), changeNo, businessType,
                    premiumType);
        }
    }

    private void calculate(TabChangeCourse changeCourse, TOrderCourse orderCourse, String changeNo, Long businessType,
            Integer premiumType) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderCourseId", orderCourse.getId());
        paramMap.put("rootCourseId", orderCourse.getRoot_course_id());
        // 获取订单课程预结转金额（本单+子单+主单+同属本单子单预结转金额）
        Map<String, Object> manageFeeMap = this.tOrderCourseDao.queryTotalManageFee(paramMap);
        Double manageFee = ((BigDecimal) manageFeeMap.get("manage_fee")).doubleValue();
        Long courseSurplusCount = ((BigDecimal) manageFeeMap.get("course_surplus_count")).longValue();
        // 反预结转金额
        Double preAmount = manageFee;

        paramMap.put("changeNo", changeNo);
        paramMap.put("changeCourseId", changeCourse.getId());
        // 获取退费课次
        Integer changeCourseTimes = changeCourse.getCourse_times();
        if (businessType == 1) {
            changeCourseTimes = this.changeCourseTimesService.queryChangeCourseTimes(paramMap);
            if (changeCourseTimes != 0) {
                changeCourse.setCourse_times(changeCourseTimes);
            }
        }

        if (premiumType.intValue() == 2) { // VIP退费
            // 反预结转金额
            preAmount = Math.floor(manageFee *(changeCourseTimes.doubleValue() / courseSurplusCount));
            // 退费金额
            Double totalAmount = orderCourse.getDiscount_unit_price() * changeCourseTimes + preAmount;
            changeCourse.setTotal_amount(totalAmount);
            changeCourse.setAttend_amount(0d);
        } else {
            setChangeCourseAmount(changeCourse, orderCourse, paramMap, changeCourseTimes, businessType);
        }

        changeCourse.setPre_amount(preAmount);
        changeCourse.setCourse_times(changeCourseTimes);
        this.changeCourseService.update(changeCourse);
    }

    private Double getFormerUnitPrice(TOrderCourse orderCourse, Map<String, Object> paramMap,
            Integer changeCourseTimes, Long businessType) throws Exception {
        Double discountSumPrice = orderCourse.getDiscount_sum_price();
        if (businessType == Constants.BusinessType.GXH) {
            paramMap.put("student_id", orderCourse.getStudent_id());
            paramMap.put("create_date", DateUtil.getDateOfYearsAgoOrLater(-1));
            paramMap.put("course_id", orderCourse.getCourse_id());
        }
        return discountSumPrice > 0 ? orderCourse.getFormer_unit_price() : orderCourse.getDiscount_unit_price();
    }

    private void setChangeCourseAmount(TabChangeCourse changeCourse, TOrderCourse orderCourse,
            Map<String, Object> paramMap, Integer changeCourseTimes, Long businessType) throws Exception {
        Double formerUnitPrice = orderCourse.getFormer_unit_price();
        Double discountUnitPrice = orderCourse.getDiscount_unit_price();
        Double discountSumPrice = orderCourse.getDiscount_sum_price();
        Long courseTotalCount = orderCourse.getCourse_total_count();
        Long surplusCount = orderCourse.getCourse_surplus_count();
        Double surplusAmount = orderCourse.getSurplus_cost();
        Long attendCount = orderCourse.getCourse_total_count() - orderCourse.getCourse_surplus_count();
        Double totalAmount = 0d;
        
        if (Constants.BusinessType.GXH == businessType) {
            formerUnitPrice = getFormerUnitPrice(orderCourse, paramMap, changeCourseTimes, businessType);
            // 退费金额 订单总金额 - 订单原价 * （订单课次 - 退费课次）
            totalAmount = surplusAmount - formerUnitPrice * (surplusCount - changeCourseTimes);
        } else {
            if (discountUnitPrice.doubleValue() != formerUnitPrice.doubleValue()) {
                if (orderCourse.getRoot_course_id() != null) {
                    orderCourse = this.tOrderCourseDao.queryOrderCourseById(orderCourse.getRoot_course_id());
                }

                formerUnitPrice = getFormerUnitPrice(orderCourse, paramMap, changeCourseTimes, businessType);
                discountSumPrice = orderCourse.getDiscount_sum_price();
                discountUnitPrice = orderCourse.getDiscount_unit_price();
                courseTotalCount = orderCourse.getCourse_total_count();
                attendCount = this.attendanceDao.countAttendByOrderCourseId(paramMap);
                // 退费金额 订单总金额 - 订单原价 * （订单课次 - 退费课次）
                totalAmount = discountSumPrice - formerUnitPrice * (courseTotalCount - changeCourseTimes);
            } else {
                totalAmount = discountUnitPrice * changeCourseTimes;
            }
        }

        // 补考勤金额 （订单原价 - 订单折扣价） * 考勤次数
        Double attendAmount = (formerUnitPrice - discountUnitPrice) * attendCount;
        changeCourse.setTotal_amount(totalAmount);
        changeCourse.setAttend_amount(attendAmount);
    }

    @Override
    public void premium(Map<String, Object> refundMap, Long businessType) throws Exception {
        String changeNo = (String) refundMap.get("change_no");
        Long orderId = this.tOrderCourseDao.queryOrderIdByChangeNo(changeNo);

        checkBeforeSaveOrderChange(changeNo, orderId);
        TOrderChange orderChange = getOrderChange(refundMap, orderId);
        this.orderChangeService.saveOrderChange(orderChange);
        refundMap.put("v_change_id", orderChange.getId());

        try {
            this.iOrderYDY.refundOrder(orderChange, changeNo);
        } catch (Exception e) {
            TLock tLock = new TLock();
            tLock.setBusiType(5L);
            tLock.setBusiId(orderChange.getId());
            this.tLockDao.releaseLock(tLock);
            throw e;
        }
    }

    private void checkBeforeSaveOrderChange(String changeNo, Long orderId) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("lock_type", 1L);
        paramMap.put("order_id", orderId);
        this.orderChangeCheckService.isOrderLock(paramMap, "当前订单被锁定，不能申请退费！");
        this.orderChangeCheckService.isOrderCanceled(orderId, "该订单已作废,不能再退费！");
        this.orderChangeCheckService.hasChangeCourseRecord(changeNo, "当前订单课程或其主订单课程或其子课程存在未完成的退费操作，不能再次申请退费！");
    }

    private TOrderChange getOrderChange(Map<String, Object> refundMap, Long orderId) {
        Date currDate = DateUtil.getCurrDateTime();
        Long userId = (Long) refundMap.get("user_id");

        TOrderChange orderChange = new TOrderChange();

        orderChange.setOrder_id(orderId);
        orderChange.setChange_type(1L);
        orderChange.setEncoding((String) refundMap.get("p_encoding"));
        orderChange.setBranch_id((Long) refundMap.get("branch_id"));
        orderChange.setApply_user(userId);
        orderChange.setApply_time(currDate);
        orderChange.setChange_status(2L);
        orderChange.setCreate_user(userId);
        orderChange.setCreate_time(currDate);
        orderChange.setUpdate_user(userId);
        orderChange.setUpdate_time(currDate);
        orderChange.setFee_deduction_amount(NumberUtils.object2Double(refundMap.get("p_premium_deduction_amount")));
        orderChange.setRemark((String) refundMap.get("p_remark"));
        orderChange.setOrderCourseId(NumberUtils.object2Long(refundMap.get("order_detail_id")));
        return orderChange;
    }

    @Override
    public void updateOrderCourseScheduleCount(Map<String, Object> refundMap) throws Exception {
        Long orderCourseId = NumberUtils.object2Long(refundMap.get("order_detail_id"));
        Integer refundCourseCount = NumberUtils.object2Integer(refundMap.get("course_cnt"));

        // 获取订单课程信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", orderCourseId);
        List<TOrderCourse> orderCourseList = this.tOrderCourseDao.queryOrderCourse(paramMap);
        if (!CollectionUtils.isEmpty(orderCourseList)) {
            TOrderCourse orderCourse = orderCourseList.get(Constants.LIST_FIRST_INDEX);
            if (orderCourse.getCourse_schedule_count() != null && orderCourse.getCourse_schedule_count() >= refundCourseCount) {
                // 剩余可排课时减去退费课时
                orderCourse.setCourse_schedule_count(orderCourse.getCourse_schedule_count() - refundCourseCount);
                this.tOrderCourseDao.updateOrderCourse(orderCourse);
            }
        }
    }

}
