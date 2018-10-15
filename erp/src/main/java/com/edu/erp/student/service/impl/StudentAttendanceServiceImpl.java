package com.edu.erp.student.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.edu.common.constants.ProductLine;
import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import com.edu.erp.orders.service.FeeDetailService;
import com.edu.common.util.NumberUtils;
import com.edu.erp.dao.TScheduleSplitTimeDao;
import com.edu.erp.model.TScheduleSplitTime;
import com.edu.erp.student.service.StudentScheduleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TAttendanceHt;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.orders.service.FeeService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.ModelDataUtils;
import com.edu.erp.util.StringUtil;

@Service(value = "studentAttendanceService")
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(StudentAttendanceServiceImpl.class);

    @Resource(name = "attendanceDao")
    private AttendanceDao attendanceDao;

    @Resource(name = "tOrderCourseDao")
    private TOrderCourseDao tOrderCourseDao;

    @Resource(name = "feeService")
    private FeeService feeService;

    @Resource(name = "feeDetailService")
    private FeeDetailService feeDetailService;

    @Resource(name = "studentInfoService")
    private StudentInfoService studentInfoService;

    @Resource(name = "studentScheduleService")
    private StudentScheduleService studentScheduleService;

    @Resource(name = "tScheduleSplitTimeDao")
    private TScheduleSplitTimeDao tScheduleSplitTimeDao;

    @Resource(name = "studentInfoDao")
    private StudentInfoDao studentInfoDao;

    @Resource(name = "organizationDao")
    private OrganizationDao organizationDao;

    @Resource(name = "courseSchedulingDao")
    private CourseSchedulingDao courseSchedulingDao;

    @Resource(name = "studentIntegralDao")
    private StudentIntegralDao studentIntegralDao;

    @Resource(name = "studentIntegralDetailsDao")
    private StudentIntegralDetailsDao studentIntegralDetailsDao;

    @Resource(name = "tCourseSchedulingAssistDao")
    private TCourseSchedulingAssistDao tCourseSchedulingAssistDao;

    @Resource(name = "tCourseDao")
    private TCourseDao tCourseDao;

    /**
     * 学生考勤主接口
     *
     * @param param <pre>
     *              attendanceId : 考勤记录ID,java.lang.Long,可为空
     *              schedulingId : 排课表ID,java.lang.Long,不可为空
     *              studentId    : 学生ID,java.lang.Long,不可为空
     *              courseDate   : 上课日期,java.util.Date,班级课和一对一可为空,晚辅导不可为空
     *              attendType   : 考勤状态类型,java.lang.Long,不可为空
     *              userId       : 用户,考勤人,java.lang.Long,不可为空
     *              branchId     : 校区,考勤校区,java.lang.Long,不可为空
     *              errorCode    : 出参,java.lang.Long,是否出错
     *              errorDesc    : 出参,java.lang.String,错误信息
     *              </pre>
     * @throws Exception
     */
    @Override
    public void attandanceSubmit(Map<String, Object> param) throws Exception {
        param.put("errorCode", 0);
        param.put("errorDesc", "");

        if (param.get("forQuit") == null) {
            param.put("forQuit", 0); // 退费挂起标识
        }
        attendanceDao.attandanceSubmit(param);

        String errorCode = StringUtil.nullToBlank(param.get("errorCode"));
        if (!"0".equals(errorCode)) {
            throw new Exception(StringUtil.nullToBlank(param.get("errorDesc")));
        }
        //更新t_attendance的remark字段
        updateAttandRemark(param);
        //更新t_attendanct_ht的remark字段
        updateAttandHTRemark(param);
    }

    /**
     * 考勤通用校验
     *
     * @steps:
     * 1.校验学生是否存在
     * 3.校验排课数据是否存在
     * 4.校验是否达到上课时间
     * 5.校验上课日期是否正确
     * 6.校验考勤状态是否正确
     * 7.校验该学生是否有包含当前考勤课程的订单
     * 8.校验当前课次依然有效
     * 9.校验当前课次在退费审批中不允许退费
     */
    public void attendanceCommonCheck(Map<String, Object> map) throws Exception {
        Integer count = 0;
        //1.校验学生是否存在
        Long studentId = (Long) map.get("studentId");
        StudentInfo studentInfo = studentInfoDao.queryById(studentId);
        if (null == studentInfo) {
            throw new RuntimeException("考勤校验:当前学生ID不存在");
        }
        //2.校验考勤校区是否存在  注释原因：考勤校区应该为课程校区
//        Long branchId = (Long) map.get("branchId");
//        OrganizationInfo branch = organizationDao.selectById(branchId);
//        if(null == branch) {
//            throw new RuntimeException("考勤校验:当前考勤校区不存在");
//        }
        //3.校验排课数据是否存在
        Long schedulingId = (Long) map.get("schedulingId");
        CourseScheduling courseScheduling = courseSchedulingDao.queryOne(schedulingId);
        if (null == courseScheduling) {
            throw new RuntimeException("考勤校验:当前考勤课程对应排课表数据不存在");
        }
        //4.校验是否达到上课时间
        Long attendTypeId = (Long) map.get("attendType");
        if (!attendTypeId.equals(10L) &&!attendTypeId.equals(20L) && !attendTypeId.equals(30L) && DateUtil.getCurrDateOfDate("yyyy-MM-dd").before(DateUtil.stringToDate(courseScheduling.getCourse_date().toString(),DateUtil.DATE_FORMAT1))) {
            throw new RuntimeException("考勤校验:当前课次未到上课时间，不能考勤");
        }
        //6.校验考勤状态是否正确

        //7.校验该学生是否有包含当前考勤课程的订单
        AttendanceAuxiliaryData attendanceAuxiliaryData = courseSchedulingDao.queryAttendanceSchedualingInfo(studentId, schedulingId);
        Long orderId = attendanceAuxiliaryData.getOrderId();
        if (null == orderId) {
            throw new RuntimeException("考勤校验:该学生目前没有包含当前考勤课程的有效订单");
        }
        //8.校验当前课次依然有效
        count = courseSchedulingDao.queryValidCourseTimeByStudentAndSchedualing(studentId, schedulingId);
        if (count == 0) {
            throw new RuntimeException("考勤校验:该学生的课次已经转班或者退费，不能考勤");
        }
        //9.校验当前课次在退费审批中不允许退费
        count = courseSchedulingDao.validIsRefund(studentId, schedulingId);
        if (count > 0) {
            throw new RuntimeException("考勤校验:该学生的考勤课次存在正在退费审批中，待审核完毕可以考勤");
        }
        // 10.课次是否处于退费挂起中
        TAttendance attendance = attendanceDao.getForQuitAttendance(studentId, schedulingId);
        if (attendance != null && (attendTypeId.equals(10L) || attendTypeId.equals(20L) || attendTypeId.equals(30L)) && attendance.getFor_quit() != null && Long.valueOf(attendance.getFor_quit()) > 0L) {
            throw new RuntimeException("考勤校验:退费挂起，不允许置空");
        }
    }

    public void attendanceBjk(Map<String, Object> param) throws Exception {

        // 校验
        attendanceCommonCheck(param);

        //1.校验排课数据是否存在
        Long schedulingId = (Long) param.get("schedulingId");
        CourseScheduling courseScheduling = courseSchedulingDao.queryOne(schedulingId);

        if (courseScheduling.getBusiness_type().equals(1L)) {//培英班
            // 获取订单id
            Long studentId = (Long) param.get("studentId");
            AttendanceAuxiliaryData attendanceAuxiliaryData = courseSchedulingDao.queryAttendanceSchedualingInfo(studentId, schedulingId);
            Long orderId = attendanceAuxiliaryData.getOrderId();
            attendanceDao.lockOrderById(orderId);//获取订单写锁

            // 业务锁校验（佳音）
            if (attendanceDao.validExistLock(1, orderId) < 0) {
                throw new RuntimeException("班级课考勤校验错误:考勤订单被锁定，不允许考勤");
            }
            if (attendanceDao.validExistLock(2, orderId) < 0) {
                throw new RuntimeException("班级课考勤校验错误:考勤课程被锁定，不允许考勤");
            }
            if (attendanceDao.validExistLock(3, orderId) < 0) {
                throw new RuntimeException("班级课考勤校验错误:考勤课次被锁定，不允许考勤");
            }
            // 考勤
            Long attendanceId = (Long) param.get("attendanceId");
            if (null == attendanceId) {//新增考勤
                //校验该学生该课次的考勤记录是否已存在
                TAttendance existAttendanceInDB = attendanceDao.queryValidAttendanceId(studentId, schedulingId);
                if (null == existAttendanceInDB) {
                    Long orderCourseId = (Long) attendanceAuxiliaryData.getOrderCourseId();
                    Double attendAmount = attendanceAuxiliaryData.getDiscountUnitPrice().doubleValue();
                    Long courseBranchId = (Long) attendanceAuxiliaryData.getCourseBranchId();

                    if (orderCourseId == null || attendAmount == null || courseBranchId == null) {
                        throw new RuntimeException("数据异常：orderCourseId 或 discountUnitPrice 或 courseBranchId");
                    }
                    //初始化考勤对象
                    TAttendance attendance = new TAttendance();
                    attendance.setScheduling_id(schedulingId);
                    attendance.setStudent_id(studentId);
                    attendance.setAttend_user((Long) param.get("userId"));
                    //updateAttendance.setAttend_date(); 通过sysdate
                    attendance.setAttend_branch_id(courseBranchId);
                    attendance.setAttend_type(10L);
                    attendance.setCourse_date(courseScheduling.getCourse_date());
                    attendance.setStart_time(courseScheduling.getStart_time());
                    attendance.setEnd_time(courseScheduling.getEnd_time());
                    attendance.setCourse_times(courseScheduling.getCourse_times());
                    attendance.setCreate_user((Long) param.get("userId"));
                    attendance.setUpdate_user((Long) param.get("userId"));
                    attendance.setRemark((String) param.get("remark"));
                    attendance.setTs_flag("S");
                    attendance.setCarried("N");
                    attendance.setAttend_amount(attendAmount);
                    attendance.setOrder_course_id(orderCourseId);

                    attendance.setFor_quit("0");

                    //插入考勤初始记录
                    Integer count = attendanceDao.insertAttendanceBjk(attendance);
                    if (count < 1) {
                        throw new RuntimeException("考勤失败：" + attendanceDao);
                    }
                    attendance.setAttend_type((Long) param.get("attendType"));
                    attendance.setProductLine((Long) param.get("productLine"));
                    //更新考勤记录及相关业务
                    updateAttendStatusWithoutCommonCheck(attendance);
                } else {
                    //更新考勤记录
                    TAttendance attendance = new TAttendance();
                    attendance.setId(existAttendanceInDB.getId());
                    attendance.setAttend_user((Long) param.get("userId"));
                    attendance.setAttend_type((Long) param.get("attendType"));
                    attendance.setCourse_date(courseScheduling.getCourse_date());
                    attendance.setStart_time(courseScheduling.getStart_time());
                    attendance.setEnd_time(courseScheduling.getEnd_time());
                    attendance.setCourse_times(courseScheduling.getCourse_times());
                    attendance.setUpdate_user((Long) param.get("userId"));
                    attendance.setProductLine((Long) param.get("productLine"));
                    attendance.setRemark((String) param.get("remark"));
                    updateAttendStatusWithoutCommonCheck(attendance);
                }
            } else {
                //更新考勤记录
                TAttendance existAttendanceInDB = attendanceDao.queryAttendanceInfoById(attendanceId);
                TAttendance attendance = new TAttendance();
                attendance.setId(existAttendanceInDB.getId());
                attendance.setAttend_user((Long) param.get("userId"));
                attendance.setAttend_type((Long) param.get("attendType"));
                attendance.setCourse_date(courseScheduling.getCourse_date());
                attendance.setStart_time(courseScheduling.getStart_time());
                attendance.setEnd_time(courseScheduling.getEnd_time());
                attendance.setCourse_times(courseScheduling.getCourse_times());
                attendance.setUpdate_user((Long) param.get("userId"));
                attendance.setProductLine((Long) param.get("productLine"));
                attendance.setRemark((String) param.get("remark"));
                updateAttendStatusWithoutCommonCheck(attendance);
            }
        } else {
            throw new RuntimeException("班级课考勤校验错误:排课课次业务类型错误");
        }
    }

    /**
     * 更新考勤记录及相关业务,包含结转与反结转
     *
     * @param updateAttendance
     * @throws Exception
     */
    public void updateAttendStatusWithoutCommonCheck(TAttendance updateAttendance) throws Exception {
        // 1.考勤信息
        TAttendance tAttendance = attendanceDao.queryAttendanceInfoById(updateAttendance.getId());
        Assert.notNull(tAttendance, "考勤失败：考勤信息不存在" + tAttendance.getId());
        // 1.1校验考勤类型是否发生变化
        if (updateAttendance.getAttend_type().equals(tAttendance.getAttend_type())) {
            logger.info("考勤类型没有发生变化{}", updateAttendance.getAttend_type());
            return;
        }
        // 2.查找排课
        CourseScheduling courseScheduling = courseSchedulingDao.queryOne(tAttendance.getScheduling_id());
        Assert.notNull(courseScheduling, "考勤失败：排课信息不存在" + courseScheduling.getId());
        //Assert.notNull(courseScheduling.getTeacher_id(), "考勤失败：排课记录中不存在教师信息,课次" + courseScheduling.getCourse_times());

        // 3.查找订单课程
        TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(tAttendance.getOrder_course_id());
        Assert.notNull(tOrderCourse, "考勤失败：订单课程信息不存在" + tOrderCourse.getId());

        // 4.扣费标识
        String feeReduceFlag = "";
        if (tAttendance.getAttend_type().equals(10L)) {//培英班置空
            if (updateAttendance.getAttend_type().equals(11L) || updateAttendance.getAttend_type().equals(12L)) {//正常上课挂起
                feeReduceFlag = "扣费";
            }
        } else if (tAttendance.getAttend_type().equals(11L) || tAttendance.getAttend_type().equals(12L)) {//正常上课挂起
            if (updateAttendance.getAttend_type().equals(10L)) {//置空
                feeReduceFlag = "退费";
            }
        } /*else if(tAttendance.getAttend_type().equals(21L) || tAttendance.getAttend_type().equals(22L) ) {
            if(updateAttendance.getAttend_type().equals(20L) || updateAttendance.getAttend_type().equals(23L) || updateAttendance.getAttend_type().equals(24L) || updateAttendance.getAttend_type().equals(25L)
                    || updateAttendance.getAttend_type().equals(26L) || updateAttendance.getAttend_type().equals(27L)) {
                feeReduceFlag = "退费";
            }
        }else if(tAttendance.getAttend_type().equals(20L) || tAttendance.getAttend_type().equals(23L) || tAttendance.getAttend_type().equals(24L) || tAttendance.getAttend_type().equals(25L)
                || tAttendance.getAttend_type().equals(26L) || tAttendance.getAttend_type().equals(27L)){
            if (updateAttendance.getAttend_type().equals(21L) || updateAttendance.getAttend_type().equals(22L) ){
                feeReduceFlag = "扣费";
            }
        }*/ else if (tAttendance.getAttend_type().equals(30L) && updateAttendance.getAttend_type().equals(31L)) {//正常上课到挂起
            feeReduceFlag = "扣费";
        } else if (tAttendance.getAttend_type().equals(31L) && updateAttendance.getAttend_type().equals(30L)) {//挂起到正常上课
            feeReduceFlag = "退费";
        }
        Assert.hasText(feeReduceFlag, "扣费标识计算异常:可能原因：（1.正常上课->挂起）");

        // 5.考勤更新记录
        TAttendance attendanceForUpdate = new TAttendance();
        attendanceForUpdate.setId(tAttendance.getId());
        attendanceForUpdate.setCarried(feeReduceFlag.equals("扣费") ? "Y" : "N");
        attendanceForUpdate.setUpdate_user(updateAttendance.getUpdate_user());
        attendanceForUpdate.setAttend_type(updateAttendance.getAttend_type());
        attendanceForUpdate.setTeacher_id(courseScheduling.getTeacher_id());
        attendanceForUpdate.setAttend_user(updateAttendance.getAttend_user());
        attendanceForUpdate.setRemark(updateAttendance.getRemark());

        //佳音培英班考勤
        if(ProductLine.JIA_YIN.getId().equals(updateAttendance.getProductLine()) && (updateAttendance.getAttend_type().equals(12L) || updateAttendance.getAttend_type().equals(11L) )) {
            TCourseSchedulingAssist courseManager = new TCourseSchedulingAssist();
            TCourseSchedulingAssist chineseTeacher = new TCourseSchedulingAssist();
            TCourseSchedulingAssist foreignTeacher = new TCourseSchedulingAssist();

            if(updateAttendance.getAttend_type().equals(12L) ) {//正常上课
                // 获取课次关联的高级参数（中教，外教，学管）
                List<TCourseSchedulingAssist> tCourseSchedulingAssists = tCourseSchedulingAssistDao.queryCourseSchedulingTimeAssist(courseScheduling.getId());
                if (null == tCourseSchedulingAssists || tCourseSchedulingAssists.size() == 0) {
                    //排课没有设置高级参数，则读取课程的高级参数
                    tCourseSchedulingAssists = tCourseSchedulingAssistDao.queryCourseSchedulingAssist(courseScheduling.getCourse_id());
                }
                if (tCourseSchedulingAssists.size() > 3) {
                    throw new RuntimeException("高级参数异常，请联系信息事业部处理");
                }
                for (TCourseSchedulingAssist assist : tCourseSchedulingAssists) {
                    switch (assist.getCourseKey()) {
                        case "course_manager"://学管
                            courseManager = assist;
                            break;
                        case "course_tearcher_en"://外教
                            foreignTeacher = assist;
                            break;
                        case "course_tearcher_cn"://中教
                            chineseTeacher = assist;
                            break;
                    }
                }
                //TODO 佳音考勤校验课时长度
                /*存在口语 ?
                - 存在 Y
                    -  中教课时+外教课时+口语长度>=课时长度 ?
                        - Y 考勤
                        - N 抛出异常
                - 不存在 N
                    - 中教课时+外教课时 = 课时长度 ?
                        -Y 考勤
                        -N 抛出异常*/

                //课时长度校验
                TCourse course = tCourseDao.getCourseById(courseScheduling.getCourse_id());
                Double chineseTeacherLength = (chineseTeacher.getCourseVal() == null || chineseTeacher.getExtandVal1() ==null|| chineseTeacher.getExtandVal1() <0)?0:chineseTeacher.getExtandVal1();
                Double foreignTeacherLength = (foreignTeacher.getCourseVal() == null || foreignTeacher.getExtandVal1() == null|| foreignTeacher.getExtandVal1()<0)?0:foreignTeacher.getExtandVal1();
                Double spokenHourLength = (foreignTeacher.getCourseVal() == null || foreignTeacher.getExtandVal2() == null|| foreignTeacher.getExtandVal2()<0)?0:foreignTeacher.getExtandVal2();
                Double courseHourLenghtWithoutSpoken = chineseTeacherLength + foreignTeacherLength;
                Double hourLen = course.getHour_len() == null ? 0L : course.getHour_len();
                if(hourLen <=0) {
                    throw new RuntimeException("课时长度不允许为空");
                }
                String[] format = {"HH:mm"};
                Date startTime = DateUtils.parseDate(courseScheduling.getStart_time(),format);
                Date endTime = DateUtils.parseDate(courseScheduling.getEnd_time(), format);
                hourLen = (endTime.getTime()-startTime.getTime())/60000.0;

                if(courseHourLenghtWithoutSpoken*60 != hourLen ) {
                    throw new RuntimeException("高级参数的 (中教时长" + chineseTeacherLength + "+外教时长" + foreignTeacherLength + " 不等于 课时长度"+ hourLen/60 +"小时-"+hourLen+" 分钟)，请修改高级参数后重试考勤");
                }
            }
            attendanceForUpdate.setCourseManagerAdvancedParam(courseManager.getCourseVal() == null ? null:Long.valueOf(courseManager.getCourseVal()));
            attendanceForUpdate.setChineseTeacherAdvanceParam(chineseTeacher.getCourseVal() == null ? null:Long.valueOf(chineseTeacher.getCourseVal()));
            attendanceForUpdate.setForeignTeacherAdvanceParam(foreignTeacher.getCourseVal() == null ? null:Long.valueOf(foreignTeacher.getCourseVal()));
            attendanceForUpdate.setSpokenLanguageHourLength(foreignTeacher.getExtandVal2());
            attendanceForUpdate.setChineseTeacherAdvanceParamHourLength(chineseTeacher.getExtandVal1());
            attendanceForUpdate.setChineseTeacherAdvanceParamRemark(chineseTeacher.getRemark());
            attendanceForUpdate.setForeignTeacherAdvanceParamHourLength(foreignTeacher.getExtandVal1());
            attendanceForUpdate.setForeignTeacherAdvanceParamRemark(foreignTeacher.getRemark());
            attendanceForUpdate.setProductLine(updateAttendance.getProductLine());
        }
        //更新考勤信息
        attendanceDao.updateAttendanceBjk(attendanceForUpdate);

        // 6.插入考勤历史记录
        attendanceDao.saveAttendanceHtForBjk(tAttendance.getId(), tAttendance.getAttend_type());

        // 7.处理预结转
        Double changeMoney = tAttendance.getAttend_amount() * (feeReduceFlag.equals("扣费") ? 1 : -1);
        TFee fee = new TFee();
        fee.setOrder_id(tOrderCourse.getOrder_id());
        fee.setFee_type(61L);
        fee.setFee_flag(1L);
        fee.setFee_amount(changeMoney);
        fee.setFee_status(1L);
        fee.setInsert_time(new Date());
        fee.setFinish_time(new Date());
        fee.setOperate_type(6L);
        fee.setOperate_no(tAttendance.getId().toString());
        feeService.saveFee(fee);

        TFeeDetail feeDetail = new TFeeDetail();
        feeDetail.setFee_id(fee.getId());
        feeDetail.setOrder_id(fee.getOrder_id());
        feeDetail.setOrder_detail_id(tOrderCourse.getId());
        feeDetail.setFee_type(fee.getFee_type());
        feeDetail.setFee_flag(fee.getFee_flag());
        feeDetail.setFee_amount(fee.getFee_amount());
        feeDetail.setCourse_sum((courseScheduling.getCourse_cnt() == null ? 1 : courseScheduling.getCourse_cnt()) * (feeReduceFlag.equals("扣费") ? 1 : -1));
        feeDetail.setOperate_type(fee.getOperate_type());
        feeDetail.setOperate_no(tAttendance.getId());
        feeDetailService.saveFeeDetail(feeDetail);

        // 8.考勤更新订单课程
        TOrderCourse orderCourseForUpdate = new TOrderCourse();
        orderCourseForUpdate.setId(tOrderCourse.getId());
        orderCourseForUpdate.setCourse_surplus_count(tOrderCourse.getCourse_surplus_count() - 1 * (feeReduceFlag.equals("扣费") ? 1 : -1));
        orderCourseForUpdate.setSurplus_cost(orderCourseForUpdate.getCourse_surplus_count() * tOrderCourse.getDiscount_unit_price());
        orderCourseForUpdate.setUpdate_user(updateAttendance.getUpdate_user());
        tOrderCourseDao.updateOrderCourseForBjkAttandence(orderCourseForUpdate);


        // 9.处理积分
        StudentIntegral currentIntegral = studentIntegralDao.getIntegral(tAttendance.getStudent_id(), tAttendance.getAttend_branch_id());
        StudentIntegralDetails studentIntegralDetails = new StudentIntegralDetails();
        if (currentIntegral == null) {
            // 新增积分
            currentIntegral = new StudentIntegral();
            currentIntegral.setStudent_id(tAttendance.getStudent_id());
            currentIntegral.setBranch_id(tOrderCourse.getBranch_id());
            currentIntegral.setLast_integral(0L);
            currentIntegral.setLast_attend_amount(0L);
            currentIntegral.setAttend_amount(changeMoney.longValue());
            currentIntegral.setCrrent_integral(changeMoney.longValue() / 100);
            currentIntegral.setUpdate_user(updateAttendance.getUpdate_user());
            studentIntegralDao.saveIntegral(currentIntegral);

            // - 积分流水
            //studentIntegralDetails.setDatetime();
            studentIntegralDetails.setAccount_id(currentIntegral.getId());
            studentIntegralDetails.setLast_attend_amount(0L);
            studentIntegralDetails.setChange_amount(currentIntegral.getAttend_amount());
            studentIntegralDetails.setAttend_amount(currentIntegral.getAttend_amount());
            studentIntegralDetails.setCrrent_integral(0L);
            studentIntegralDetails.setChange_integral(currentIntegral.getCrrent_integral());
            studentIntegralDetails.setAfter_integral(currentIntegral.getCrrent_integral() );
            studentIntegralDetails.setUpdate_user(updateAttendance.getUpdate_user());
            studentIntegralDetails.setChange_type(1L);
            studentIntegralDetails.setBranch_id(currentIntegral.getBranch_id());
            studentIntegralDetails.setChange_val(String.valueOf(updateAttendance.getAttend_type()));
        } else {
            // - 积分流水
            //studentIntegralDetails.setDatetime();
            studentIntegralDetails.setAccount_id(currentIntegral.getId());
            studentIntegralDetails.setLast_attend_amount(currentIntegral.getAttend_amount());
            studentIntegralDetails.setChange_amount(changeMoney.longValue());
            studentIntegralDetails.setAttend_amount(currentIntegral.getAttend_amount() + changeMoney.longValue());
            studentIntegralDetails.setCrrent_integral(currentIntegral.getCrrent_integral());
            studentIntegralDetails.setChange_integral(studentIntegralDetails.getAttend_amount() / 100 - studentIntegralDetails.getCrrent_integral());
            studentIntegralDetails.setAfter_integral(studentIntegralDetails.getAttend_amount() / 100);
            studentIntegralDetails.setUpdate_user(updateAttendance.getUpdate_user());
            studentIntegralDetails.setChange_type(1L);
            studentIntegralDetails.setBranch_id(currentIntegral.getBranch_id());
            studentIntegralDetails.setChange_val(String.valueOf(updateAttendance.getAttend_type()));

            //修改积分
            StudentIntegral studentIntegralForUpdate = new StudentIntegral();
            studentIntegralForUpdate.setId(currentIntegral.getId());
            studentIntegralForUpdate.setLast_integral(currentIntegral.getCrrent_integral());
            studentIntegralForUpdate.setCrrent_integral(studentIntegralDetails.getAfter_integral());
            studentIntegralForUpdate.setAttend_amount(studentIntegralDetails.getAttend_amount());
            studentIntegralForUpdate.setUpdate_user(studentIntegralDetails.getUpdate_user());
            studentIntegralForUpdate.setLast_attend_amount(currentIntegral.getAttend_amount());
            studentIntegralDao.updateIntegral(studentIntegralForUpdate);

        }

        // 插入积分流水
        studentIntegralDetailsDao.saveStudentIntegralHt(studentIntegralDetails);

        // 10.判断新老学员
        Long integralThreshold = studentInfoDao.getIntegralThreshold(tAttendance.getStudent_id());// 学员所属团队老学员积分标准
        if (studentIntegralDetails.getAfter_integral() >= integralThreshold) { // 老学员
            studentInfoDao.updateStudentToOld(tAttendance.getStudent_id());
        }

        // 11.更新排课表考勤状态
        if (updateAttendance.getAttend_type().equals(11L) || updateAttendance.getAttend_type().equals(12L) || updateAttendance.getAttend_type().equals(21L)
                || updateAttendance.getAttend_type().equals(22L) || updateAttendance.getAttend_type().equals(23L) || updateAttendance.getAttend_type().equals(24L)
                || updateAttendance.getAttend_type().equals(25L) || updateAttendance.getAttend_type().equals(26L) || updateAttendance.getAttend_type().equals(27L)) {
            courseSchedulingDao.updateAttendStatus(courseScheduling.getId(), "Y", updateAttendance.getUpdate_user());
        }
    }

    /**
     * 8 批量考勤接口
     *
     * @param listMap
     * @throws Exception
     */
    @Override
    public Map<String, Object> attandanceBatchSubmit(List<Map<String, Object>> listMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", false);
        map.put("message", "");

        if (listMap instanceof List) {
            for (Map<String, Object> m : listMap) {
                try {
                    attandanceSubmit(m);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(m.get("errorDesc") == null ? "" : m.get("errorDesc").toString());
                }
            }
            return map;
        } else {
            map.put("error", true);
            map.put("message", "非法参数格式");
            return map;
        }

    }

    /***
     * 晚辅导 学生考勤
     *
     * @param json
     *            {"students":[{"order_course_id":"","scheduling_id":"",
     *            "attend_id"
     *            :"","student_id":"","attend_type":""}],"course_date"
     *            :"","p_remark":""}
     * @param userId
     *            用户ID
     * @param branchId
     *            校区Id
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> wfdAttend(String json, Long userId, Long branchId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.RespMapKey.ERROR, false);
        map.put(Constants.RespMapKey.MESSAGE, "");

        try {
            if (StringUtil.isEmpty(json) || StringUtil.isEmpty(userId) || StringUtil.isEmpty(branchId)) {
                map.put(Constants.RespMapKey.ERROR, true);
                map.put(Constants.RespMapKey.MESSAGE, "参数不能为空！");
                return map;
            }
            JSONObject jsonObj = JSONObject.fromObject(json);
            JSONArray jsonArray = jsonObj.getJSONArray("students");
            Map<String, Object> paramMap = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                paramMap = new HashMap<String, Object>();
                jsonObj = jsonArray.getJSONObject(i);
                paramMap.put("p_order_course_id", jsonObj.getLong("orderCourseId"));
                Object attend_id = jsonObj.get("attend_id");
                if (!(attend_id instanceof JSONNull || attend_id == null)) {
                    paramMap.put("p_attend_id", jsonObj.getLong("attendanceId"));
                } else {
                    paramMap.put("p_attend_id", null);
                }

                paramMap.put("p_student_id", jsonObj.getLong("studentId"));
                Object counselor_id = jsonObj.get("counselorId");
                if (counselor_id != null) {
                    paramMap.put("p_counselor_id", counselor_id);
                } else {
                    paramMap.put("p_counselor_id", null);
                }

                if (paramMap.get("forQuit") == null) {
                    paramMap.put("p_for_quit", 0); // 退费挂起标识
                }

                paramMap.put("p_attend_type", jsonObj.getLong("attendType"));
                paramMap.put("p_course_date", DateUtil.stringToDate(jsonObj.getString("courseDate"), "yyyy-MM-dd"));
                paramMap.put("p_remark", jsonObj.getString("remark"));
                paramMap.put("p_input_user", userId);
                paramMap.put("p_teacher_id", null);
                paramMap.put("p_branch_id", branchId);
                paramMap.put("o_err_code", null);
                paramMap.put("o_err_desc", null);
                attendanceDao.wfdAttend(paramMap);
                if (!paramMap.get("o_err_code").toString().equals("0")) {
                    map.put(Constants.RespMapKey.MESSAGE, paramMap.get("o_err_desc"));
                    throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
                }
            }
            return map;
        } catch (Exception e) {
            map.put(Constants.RespMapKey.ERROR, true);
            map.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            return map;
        }
    }

    /***
     * 教师考勤 service 接口
     *
     * @param json
     *            {teachers:[{"teacher_id":"教师ID","attend_type":"考勤状态",
     *            "attend_id":""}],"attend_date":"上课日期","remark":"备注"}
     * @param userId
     *            用户Id
     * @param branchId
     *            校区ID
     * @return
     * @throws Exception
     */
    @Override
    public void teacherAttend(String json, Long userId, Long branchId) throws Exception {
        Assert.notNull(json);
        Assert.notNull(userId);
        Assert.notNull(branchId);
        JSONObject jsonObj = JSONObject.fromObject(json);
        Assert.notNull(jsonObj.getString("course_date"), "yyyy-MM-dd");
        JSONArray jsonArray = jsonObj.getJSONArray("teachers");
        Map<String, Object> paramMap = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            paramMap = new HashMap<String, Object>();
            Assert.notNull(jsonArray.getJSONObject(i).get("teacher_id"));
            Assert.notNull(jsonArray.getJSONObject(i).get("attend_type"));
            paramMap.put("p_attend_id", jsonArray.getJSONObject(i).get("attend_id"));
            paramMap.put("p_teacher_id", jsonArray.getJSONObject(i).get("teacher_id"));
            paramMap.put("p_attend_type", jsonArray.getJSONObject(i).get("attend_type"));
            paramMap.put("p_attend_date", DateUtil.stringToDate(jsonObj.getString("course_date"), "yyyy-MM-dd"));
            paramMap.put("p_remark", jsonObj.getString("remark"));
            paramMap.put("p_input_user", userId);
            paramMap.put("p_branch_id", branchId);
            paramMap.put("o_err_code", null);
            paramMap.put("o_err_desc", null);
            attendanceDao.teacherAttend(paramMap);
            if (!paramMap.get("o_err_code").toString().equals("0")) {
                throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
            }
        }
    }

    @Override
    public Map<String, Object> querySchedulingById(String schId) throws Exception {
        return attendanceDao.querySchedulingById(schId);

    }

    @Override
    public List<Map<String, Object>> queryAttendCourseTimesDetails(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap);
        Assert.notEmpty(paramMap);
        Assert.notNull(paramMap.get("student_id"));
        Assert.notNull(paramMap.get("scheduling_id"));

        Long student_id = (Long) paramMap.get("student_id");
        Long scheduling_id = (Long) paramMap.get("scheduling_id");
        if (student_id.longValue() > 0 && scheduling_id.longValue() > 0) {
            paramMap = new HashMap<String, Object>();
            paramMap.put("student_id", student_id);
            paramMap.put("scheduling_id", scheduling_id);
            List<Map<String, Object>> details = (List<Map<String, Object>>) attendanceDao
                    .queryAttendCourseTimesDetails(paramMap);
            return ModelDataUtils.lowerMapList(details);
        } else
            throw new Exception("传入参数非法");
    }

    @Override
    public Integer countAttendBySchedulingId(Map<String, Object> paramMap) throws Exception {
        try {
            return attendanceDao.countAttendBySchedulingId(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void updateAttandRemark(Map<String, Object> param) throws Exception {
        attendanceDao.updateAttandRemark(param);
    }

    @Override
    public void updateAttandHTRemark(Map<String, Object> param) throws Exception {
        attendanceDao.updateAttendHTRemark(param);
    }

    @Override
    public List<Map<String, Object>> queryStudentAttendanceById(Map<String, Object> param) throws Exception {
        return attendanceDao.queryStudentAttendanceById(param);
    }

    @Override
    public List<Map<String, Object>> checkVideoUploadStatus(Map<String, Object> param) throws Exception {
        Assert.notNull(param);
        Assert.notEmpty(param);
        Assert.notNull(param.get("scheduling_id"));
        return attendanceDao.checkVideoUploadStatus(param);
    }

    @Override
    public List<Map<String, Object>> checkSaveExtralesson(Map<String, Object> param) throws Exception {
        Assert.notNull(param);
        Assert.notEmpty(param);
        Assert.notNull(param.get("scheduling_id"));
        Assert.notNull(param.get("student_id"));
        Assert.notNull(param.get("valid_start_date"));
        return attendanceDao.checkSaveExtralesson(param);
    }

    @Override
    public String saveExtralesson(Map<String, Object> param) throws Exception {
        Assert.notNull(param);
        Assert.notEmpty(param);
        Assert.notNull(param.get("create_user"));
        String resvCodePrefix = this.attendanceDao.queryResvCodePrefix(param);
        if (StringUtils.isEmpty(resvCodePrefix)) {
            throw new Exception("获取补课预约码前缀错误，发送失败！");
        }

        String activation_code = (resvCodePrefix.equals("py") ? "" : resvCodePrefix) + StringUtil.getRandomString(6);
        param.put("activation_code", activation_code);
        attendanceDao.saveExtralesson(param);
        return activation_code;
    }

    @Override
    public List<Map<String, Object>> queryWfdDetails(Map<String, Object> paramMap) throws Exception {
        return this.attendanceDao.queryWfdDetails(paramMap);
    }

    @Override
    public void wfdAttend(JSONObject jsonObj, Long userId, Long branchId) throws Exception {

        Assert.notNull(jsonObj.getLong("student_id"));
        Assert.notNull(jsonObj.getLong("attend_type"));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("p_order_course_id", jsonObj.getLong("order_course_id"));
        Object attend_id = jsonObj.get("attend_id");
        if (null != attend_id && !(attend_id instanceof JSONNull)) {
            paramMap.put("p_attend_id", jsonObj.getLong("attend_id"));
        } else {
            paramMap.put("p_attend_id", null);
        }
        paramMap.put("p_student_id", jsonObj.getLong("student_id"));
        paramMap.put("p_attend_type", jsonObj.getLong("attend_type"));
        paramMap.put("p_course_date", DateUtil.stringToDate(jsonObj.getString("course_date"), "yyyy-MM-dd"));
        paramMap.put("p_remark", jsonObj.getString("remark"));
        paramMap.put("p_input_user", userId);
        paramMap.put("p_branch_id", branchId);
        paramMap.put("o_err_code", null);
        paramMap.put("o_err_desc", null);
        attendanceDao.wfdAttend(paramMap);
        if (!paramMap.get("o_err_code").toString().equals("0")) {
            throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
        }
        updateAttandRemark(paramMap);
    }

    @Override
    public void saveAttandance(TAttendance tAttendance) throws Exception {
        attendanceDao.saveAttandance(tAttendance);
    }

    @Override
    public void saveAttandanceHt(TAttendanceHt tAttendanceHt) throws Exception {
        attendanceDao.saveAttandanceHt(tAttendanceHt);
    }

    /**
     * 一对一考勤
     *
     * @param attend   待考勤信息
     * @param paramMap 地区、校区、处理人信息
     * @throws Exception
     */
    @Override
    public void ydyAttend(TAttendance attend, Map<String, Object> paramMap) throws Exception {
        Long branchId = (Long) paramMap.get("branchId");
        Long userId = (Long) paramMap.get("userId");

        Long lastAttendType = NumberUtils.object2Long(paramMap.get("lastAttendType"));
        // 如果上次考勤状态和本次一致，不做处理
        if (lastAttendType != null && lastAttendType.longValue() == attend.getAttend_type().longValue()) {
            return;
        }

        updateAttendance(attend, userId, branchId);

        paramMap.put("attendId", attend.getId());
        List<TScheduleSplitTime> tScheduleSplitTimeList = tScheduleSplitTimeDao.queryTScheduleSplitTimeList(paramMap);
        for (TScheduleSplitTime tScheduleSplitTime : tScheduleSplitTimeList) {
            TOrderCourse orderCourse = tOrderCourseDao.queryOrderCourseById(tScheduleSplitTime.getOrderCourseId());
            Long attendType = attend.getAttend_type();
            if (Constants.AttendType.YDY_SET_NULL == attendType || Constants.AttendType.YDY_NORMAL_ATTEND == attendType) {
                Double attendAmount = orderCourse.getDiscount_unit_price() * tScheduleSplitTime.getTimes();
                attendAmount = (Constants.AttendType.YDY_SET_NULL == attendType) ? (-1 * attendAmount) : attendAmount;
                this.feeService.createAttendFee(attend, orderCourse, attendAmount);
            }
            updateOrderCourse(orderCourse, attendType, tScheduleSplitTime.getTimes(), userId);
        }

        this.attendanceDao.addHtByAttendanceId(paramMap);
    }

    private void updateAttendance(TAttendance attend, Long userId, Long branchId)
            throws Exception {
        Long attendType = attend.getAttend_type();
        if (Constants.AttendType.YDY_SET_NULL == attendType || Constants.AttendType.YDY_NORMAL_ATTEND == attendType) {
            Double attendAmount = attend.getAttend_amount();
            // 正常考勤，生成一条考勤费用及费用明细；考勤置空，生成一条红冲考勤费用及费用明细
            attendAmount = Constants.AttendType.YDY_SET_NULL == attendType ? (-1 * attendAmount) : attendAmount;
            attend.setAttend_amount(attendAmount);
        }

        attend.setCarried(Constants.AttendType.YDY_SET_NULL == attendType ? Constants.NO : Constants.YES);
        attend.setAttend_user(userId);
        attend.setAttend_date(DateUtil.getCurrDateTime());
        attend.setAttend_type_teacher(attendType);
        attend.setUpdate_user(userId);
        this.attendanceDao.updateAttendance(attend);
    }

    private void updateOrderCourse(TOrderCourse orderCourse, Long attendType, Long courseTimes, Long userId) throws Exception {
        Long courseSurplusCount = orderCourse.getCourse_surplus_count();
        Long courseScheduleCount = orderCourse.getCourse_schedule_count();
        Long courseAttendCount = orderCourse.getCourse_attend_count() == null ? 0 : orderCourse
                .getCourse_attend_count();

        if (Constants.AttendType.YDY_CANCEL_SCHED == attendType || Constants.AttendType.YDY_CANCEL_ATTEND == attendType) { // 排课取消或者考勤作废
            courseScheduleCount += courseTimes; // 剩余可排课时 + 取消课时
        } else if (Constants.AttendType.YDY_NORMAL_ATTEND == attendType) { // 正常上课
            courseAttendCount += courseTimes; // 已考勤课时 + 考勤课时
            courseSurplusCount -= courseTimes; // 剩余可用课时 - 考勤课时
        } else if (Constants.AttendType.YDY_SET_NULL == attendType) { // 置空
            courseAttendCount -= courseTimes; // 已考勤课时 - 置空课时
            courseSurplusCount += courseTimes; // 剩余可用课时 + 置空课时
        }

        orderCourse.setCourse_surplus_count(courseSurplusCount);
        orderCourse.setCourse_schedule_count(courseScheduleCount);
        orderCourse.setCourse_attend_count(courseAttendCount);
        orderCourse.setSurplus_cost(courseSurplusCount * orderCourse.getDiscount_unit_price());
        orderCourse.setUpdate_user(userId);
        this.tOrderCourseDao.updateOrderCourse(orderCourse);
    }

    @Override
    public TAttendance queryValidAttendanceId( Long studentId,Long schedualingId) {
        return attendanceDao.queryValidAttendanceId(studentId,schedualingId);
    }
}
