package com.edu.erp.orders.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.common.util.DateUtil;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.dao.TCOrderCourseDao;
import com.edu.erp.dao.TFeeDao;
import com.edu.erp.dao.TOrderChangeDao;
import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TEncoder;
import com.edu.erp.model.TFee;
import com.edu.erp.model.TFeeDetail;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.orders.service.EncoderService;
import com.edu.erp.orders.service.FeeDetailService;
import com.edu.erp.orders.service.FeeService;
import com.edu.erp.orders.service.OrderChangeService;

/**
 * 费用总表服务
 *
 */
@Service(value = "feeService")
public class FeeServiceImpl implements FeeService {
    private static final Logger log = Logger.getLogger(FeeServiceImpl.class);

    @Resource(name = "tFeeDao")
    private TFeeDao tFeeDao;
    @Resource(name = "attendanceDao")
    private AttendanceDao attendanceDao;
    @Resource(name = "tOrderChangeDao")
    private TOrderChangeDao tOrderChangeDao;
    @Resource(name = "tCOrderCourseDao")
    private TCOrderCourseDao tCOrderCourseDao;
    @Resource(name = "attendanceService")
    private AttendanceService attendanceService; // 考勤服务
    @Resource(name = "tOrderCourseDao")
    private TOrderCourseDao tOrderCourseDao;
    @Resource(name = "encoderService")
    private EncoderService encoderService; // 业务单据服务
    @Resource(name = "feeService")
    private FeeService feeService; // 费用总表服务
    @Resource(name = "feeDetailService")
    private FeeDetailService feeDetailService; // 费用明细表服务
    @Resource(name = "orderChangeService")
    private OrderChangeService orderChangeService; // 订单变动服务

    @Override
    public int saveFee(TFee fee) throws Exception {
        // TODO Auto-generated method stub
        return tFeeDao.saveFee(fee);
    }

    @Override
    public void deleteFee(HashMap<String, String> hashMap) throws Exception {
        tFeeDao.deleteFee(hashMap);

    }

    @Override
    public void updateFeeEncoderIdByOrderId(HashMap<String, String> hashMap) throws Exception {
        tFeeDao.updateFeeEncoderIdByOrderId(hashMap);

    }

    @Override
    public TFee queryFeeAmountByOrderId(HashMap<String, String> hashMap) throws Exception {
        return tFeeDao.queryFeeAmountByOrderId(hashMap);

    }

    @Override
    public void updateFeeStatusByOrderId(HashMap<String, Object> hashMap) throws Exception {
        hashMap.put("finish_time", DateUtil.getCurrDateTime());
        tFeeDao.updateFeeStatusByOrderId(hashMap);

    }

    @Override
    public void updateFeeStatusByEncoderId(HashMap<String, Object> hashMap) throws Exception {
        hashMap.put("finish_time", DateUtil.getCurrDateTime());
        tFeeDao.updateFeeStatusByEncoderId(hashMap);
    }

    @Override
    public TFee queryFeeAmountByChangeId(Map<String, Object> hashMap) throws Exception {
        return tFeeDao.queryFeeAmountByChangeId(hashMap);
    }

    @Override
    public void updateFeeEncoderIdByChangeId(Map<String, Object> hashMap) throws Exception {
        tFeeDao.updateFeeEncoderIdByChangeId(hashMap);

    }

    @Override
    public void refundFee(TOrderChange tOrderChange) throws Exception {
        // 生成费用明细
        // 个性话业务省略--写转班单（与原单非同一个校区）恢复原价产生的金额 从原校区转出 转入到对应校区，如果转入的已考勤则产生补结转 进行消耗
        List<TCOrderCourse> tcOrderCourseList = tCOrderCourseDao.queryTcOrderCourseByChangeId(tOrderChange.getId());
        Double fee_deduction_amount = 0.0;
        TFeeDetail tFeeDetail = new TFeeDetail();
        TFee tFee = new TFee();
        Map<String, Object> param = new HashMap<String, Object>();
        for (TCOrderCourse tcOrderCourse : tcOrderCourseList) {

            tFeeDetail.setOrder_id(tOrderChange.getOrder_id());
            tFeeDetail.setOperate_type(5l);
            tFeeDetail.setFee_type(55l);
            tFeeDetail.setFee_flag(2l);
            tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
            tFeeDetail.setOperate_no(tcOrderCourse.getChange_id());
            tFeeDetail.setOrder_detail_id(tcOrderCourse.getOrder_course_id());

            param.put("order_course_id", tcOrderCourse.getOrder_course_id());
            param.put("root_course_id", tcOrderCourse.getRoot_course_id());
            int attend_cnt = attendanceService.countAttend1ByOrderCourseId(param);
            int attend_cnt2 = attendanceService.countAttend2ByOrderCourseId(param);
            int attend_cnt3 = attendanceService.countAttend3ByOrderCourseId(param);
            param.put("id", tcOrderCourse.getOrder_course_id());
            List<TOrderCourse> tOrderCourseList = tOrderCourseDao.queryOrderCourse(param);
            Double attendAmount = (attend_cnt + attend_cnt2 + attend_cnt3)
                    * (tOrderCourseList.get(0).getFormer_unit_price() - tOrderCourseList.get(0)
                            .getDiscount_unit_price());
            Double totalAmount = 0.0;
            if (tOrderCourseList != null && tOrderCourseList.size() > 0) {
                totalAmount = tcOrderCourse.getTotal_amount();
                if (totalAmount < 0 && tcOrderCourse.getAttend_amount() > 0) {
                    attendAmount += totalAmount;
                    totalAmount = 0.0;
                    if (attendAmount < 0) {
                        fee_deduction_amount += attendAmount;
                        attendAmount = 0.0;
                    }
                }
            }
            tFeeDetail.setFee_amount(totalAmount);
            feeDetailService.saveFeeDetail(tFeeDetail);
            if (attendAmount > 0) {
                tFeeDetail.setFee_type(62l);
                tFeeDetail.setFee_flag(1l);
                tFeeDetail.setFee_amount(attendAmount);
                tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
                feeDetailService.saveFeeDetail(tFeeDetail);
            }
            if (tcOrderCourse.getPre_amount() > 0) {
                tFeeDetail.setFee_type(42l);
                tFeeDetail.setFee_flag(1l);
                tFeeDetail.setFee_amount(-1 * tcOrderCourse.getPre_amount());
                tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
                feeDetailService.saveFeeDetail(tFeeDetail);
            }
        }

        if (tOrderChange.getFee_deduction_amount() != 0) {
            tFeeDetail.setFee_type(54l);
            tFeeDetail.setFee_flag(2l);
            tFeeDetail.setFee_amount(-1.0 * tOrderChange.getFee_deduction_amount());
            tFeeDetail.setCourse_sum(0l);
            tFeeDetail.setOrder_detail_id(tOrderChange.getOrderCourseId());
            feeDetailService.saveFeeDetail(tFeeDetail);
        }
        // 生成费用
        tFee.setOperate_type(5l);
        tFee.setOperate_no(tOrderChange.getId().toString());
        tFee.setInsert_time(new Date());
        tFee.setFee_status(0l);
        tFeeDetail.setOperate_no(tOrderChange.getId());
        List<TFeeDetail> tFeeDetailList = feeDetailService.queryFeeDetailByChangeId(tFeeDetail);
        for (TFeeDetail tFeeDetailTmp : tFeeDetailList) {
            tFee.setFee_type(tFeeDetailTmp.getFee_type());
            tFee.setOrder_id(tFeeDetailTmp.getOrder_id());
            tFee.setFee_flag(tFeeDetailTmp.getFee_flag());
            tFee.setFee_amount(tFeeDetailTmp.getFee_amount());
            feeService.saveFee(tFee);
            tFeeDetailTmp.setOperate_no(Long.valueOf(tFee.getOperate_no()));
            feeDetailService.updateFeeIdByFeeChangeId(tFeeDetailTmp);
        }
        param.put("operate_no", tOrderChange.getId());
        param.put("operate_type", 5);
        param.put("fee_type", "'54','55'");
        TFee tFeeTmp = feeService.queryFeeAmountByChangeId(param);
        if (tFeeTmp.getFee_amount() > 0) {
            TEncoder tEncoder = new TEncoder();
            tEncoder.setFee_amount(tFeeTmp.getFee_amount());
            tEncoder.setStatus(0);
            tEncoder.setBusi_type(5l);
            tEncoder.setEncoder_type(7l);
            tEncoder.setFee_flag(2l);
            tEncoder.setOrder_id(tOrderChange.getOrder_id());
            tEncoder.setEncoder_no(tOrderChange.getId()+"");
            encoderService.saveTEncoder(tEncoder);
        }
        // 更新费用字段
        feeService.updateFeeEncoderIdByChangeId(param);
        // 冻结补扣的数据 TODO 这块需要再验证下
        orderChangeService.updateAmountByChangeId(param);
    }

    @Override
    public void refundVipFee(TOrderChange tOrderChange) throws Exception {

    }

    @Override
    public void createAttendFee(TAttendance attend, TOrderCourse orderCourse, Double attendAmount) throws Exception {
        TFee fee = new TFee();
        fee.setOrder_id(orderCourse.getOrder_id());
        fee.setFee_type(61L);
        fee.setFee_flag(1L);
        fee.setFee_amount(attendAmount);
        fee.setFee_status(1L);
        fee.setInsert_time(DateUtil.getCurrDateTime());
        fee.setFinish_time(DateUtil.getCurrDateTime());
        fee.setOperate_type(6L);
        fee.setOperate_no(String.valueOf(attend.getId()));
        this.saveFee(fee);
        this.feeDetailService.createAttendFeeDetail(fee.getId(), attend, orderCourse, attendAmount);
    }

    @Override
    public TFee queryFeeByOrderIdAndFeeType(Long orderId, Integer feeType) {
        return tFeeDao.queryFeeByOrderIdAndFeeType(orderId, feeType);
    }

}
