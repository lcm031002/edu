/**  
 * @Title: StudentAttendanceServiceImpl.java
 * @Package com.ebusiness.erp.attendance.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月18日 下午3:51:52
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.attendance.service.impl;

import com.edu.common.constants.Constants.AttendType;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.edu.common.util.EncodingSequenceUtil;
import com.edu.common.constants.ProductLine;
import com.edu.erp.dao.TScheduleSplitTimeDao;
import com.edu.erp.model.*;
import jxl.common.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.dao.TLockDao;
import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.DetailBusinessInfoFormat;
import com.edu.erp.util.DxbCityCfg;
import com.edu.erp.util.WorkflowHelper;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;

/**
 * @ClassName: AttendanceServiceImpl
 * @Description: 考勤服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月18日 下午3:51:52
 * 
 */
@Service(value = "attendanceService")
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger log = Logger.getLogger(AttendanceServiceImpl.class);

    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    @Resource(name = "studentAttendanceService")
    private StudentAttendanceService studentAttendanceService;

    @Resource(name = "userTaskService")
    private UserTaskService userTaskService;

    @Resource(name = "studentInfoService")
    private StudentInfoService studentInfoService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "organizationService")
    private OrganizationService organizationService;

    @Resource(name = "attendanceDao")
    private AttendanceDao attendanceDao;

    @Resource(name = "tOrderCourseDao")
    private TOrderCourseDao tOrderCourseDao;

    @Resource(name = "tScheduleSplitTimeDao")
    private TScheduleSplitTimeDao tScheduleSplitTimeDao;

    private Map<String, Map<String, Object>> queryLock() {
        Map<String, Map<String, Object>> cache = new HashMap<String, Map<String, Object>>();
        try {
            List<Map<String, Object>> result = tLockDao
                    .queryTLockOrderInfo(new HashMap<String, Object>());

            for (Map<String, Object> row : result) {
                if (row != null) {
                    String SCHEDULING_ID = null != row.get("SCHEDULING_ID") ? row.get("SCHEDULING_ID") + "" : null;
                    String STUDENT_ID = null != row.get("STUDENT_ID") ? row.get("STUDENT_ID") + "" : null;
                    if (StringUtils.isBlank(SCHEDULING_ID) || StringUtils.isBlank(STUDENT_ID)) {
                        log.error("SCHEDULING_ID:" + SCHEDULING_ID + ",STUDENT_ID:" + STUDENT_ID + " is blank");
                        continue;
                    }
                    String key = genLockKey(SCHEDULING_ID, STUDENT_ID);

                    cache.put(key, row);
                } else {
                    log.error("Query Lock Order Info Row is Null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    private String genLockKey(String SCHEDULING_ID, String STUDENT_ID) {
        String key = SCHEDULING_ID + "_" + STUDENT_ID;
        return key;
    }

    @Override
    public void checkLockInfo(Map<String, Object> attandanceObj) throws Exception {
        checkLockInfo(null, attandanceObj);
    }

    @Override
    public void checkLockInfo(Map<String, Map<String, Object>> lockInfos, Map<String, Object> attandanceObj)
            throws Exception {
        if (lockInfos == null) {
            lockInfos = queryLock();
        }

        if (lockInfos.get(genLockKey(attandanceObj.get("schedulingId") + "", attandanceObj.get("studentId") + "")) != null) {
            log.error("invalid request!");
            throw new Exception("当前考勤课次存在退费审批等信息【学员：" + attandanceObj.get("studentName") + "】，待审核完毕才可以考勤！");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ebusiness.erp.attendance.service.AttendanceService#
     * attandanceBatchSubmit (java.util.List, org.jbpm.api.ProcessEngine)
     */
    @Override
    public void attandanceBatchSubmit(List<Map<String, Object>> listMap, ProcessEngine processEngine) throws Exception {
        Assert.notNull(listMap);
        if (!CollectionUtils.isEmpty(listMap)) {
            Map<String, Map<String, Object>> lockInfos = queryLock();
            for (Map<String, Object> attandanceObj : listMap) {
                studentAttendanceService.attendanceBjk(attandanceObj);
            }
        }
    }

    @Override
    public void attandanceSubmit(Map<String, Object> attandanceObj, ProcessEngine processEngine,
            Map<String, Map<String, Object>> lockInfos) throws Exception {
        Assert.notNull(attandanceObj);
        if (!CollectionUtils.isEmpty(attandanceObj)) {
            checkLockInfo(lockInfos, attandanceObj);
            if (attandanceObj != null && null != attandanceObj.get("attendanceId")) {
                String lock_status = null != attandanceObj.get("lock_status") ? attandanceObj.get("lock_status") + ""
                        : null;
                if (StringUtils.isNotBlank(lock_status) && "1".equals(lock_status)) {
                    log.error("invalid request!");
                    throw new Exception("课程订单已被冻结，考勤需要先解冻订单！【学员：" + attandanceObj.get("studentName") + "】");
                }
                Map<String, Object> row = new HashMap<String, Object>();
                row.put("id", Long.parseLong(attandanceObj.get("attendanceId") + ""));
                List<Map<String, Object>> resultMap = studentAttendanceService
                        .queryStudentAttendanceById(row);
                if (!CollectionUtils.isEmpty(resultMap)) {
                    Map<String, Object> attendanceInfo = resultMap.get(0);
                    String KQMonth = DateUtil.dateToString((Date) attendanceInfo.get("ATTEND_DATE"), "MM");
                    check_kuayue_zhikong(attandanceObj, KQMonth, processEngine);
                }
            } else {
                studentAttendanceService.attandanceSubmit(attandanceObj);
            }
        }
    }

    private void check_kuayue_zhikong(Map<String, Object> param, String KQMonth, ProcessEngine processEngine)
            throws Exception {
        String nowMonth = DateUtil.getCurrDate("MM");
        int attendType = Integer.parseInt(param.get("attendType") + "");
        if ((attendType == 10 || attendType == 30) && !nowMonth.equals(KQMonth)) {
            String city_id = param.get("cityId").toString();

            String flag = DxbCityCfg.getInstance().getConfigItem("workflow.audit.attend." + city_id, "false");

            if (flag.equals("true")) {

                if (!WorkflowHelper.isDeployed(processEngine, "erpv5.DXB_kuayue_zhikong")) {
                    throw new Exception("跨月考勤置空审批尚未发布，请联系管理员发布流程！");
                }
                Long branchId = (Long) param.get("branchId");
                param.put("branch_id", branchId);
                Map<String, Object> userApplication = new HashMap<String, Object>();
                userApplication.put("APPLICATION_ID", param.get("userId"));
                StringBuilder application = new StringBuilder("跨月考勤置空审批：");

                Map<String, Object> queryParam = new HashMap<String, Object>();
                queryParam.put("studentId", param.get("studentId"));
                StudentInfo stu = studentInfoService.queryStudentById(queryParam);
                String schInfoStr = "";
                if (attendType == 10) {
                    // 获取课次和课程名称
                    Map<String, Object> schInfo = studentAttendanceService.querySchedulingById(param
                            .get("schedulingId").toString());
                    schInfoStr = "(跨月置空课程" + schInfo.get("COURSE_NAME") + "的第【" + schInfo.get("COURSE_TIMES") + "】次课)";
                    // 附加说明
                    param.put("COURSE_NAME", schInfo.get("COURSE_NAME"));
                    param.put("COURSE_TIMES", schInfo.get("COURSE_TIMES"));
                }
                if (attendType == 30) {
                    schInfoStr = "晚辅导跨月考勤置空";
                }

                if (null != param.get("studentId")) {
                    application.append("学生ID[");
                    application.append(stu.getStudent_name() + "" + param.get("studentId"));
                    application.append(schInfoStr);
                    application.append("]");
                }

                userApplication.put("APPLICATION", application.toString());
                userApplication.put("STATUS", 1L);
                userApplication.put("CREATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                userApplication.put("CURRENT_STATE", "申请已提交");
                userApplication.put("CURRENT_STEP", "申请提交");

                userApplication.put("BUSI_ID", param.get("attendanceId"));
                // 考勤流程类型11
                userApplication.put("BUSI_TYPE", 11);

                if (null != param.get("userName")) {
                    userApplication.put("CURRENT_MAN", param.get("userName"));
                }

                userApplication.put("UPDATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                userTaskService.insertApplication(userApplication);
                param.put("application_id", userApplication.get("id"));

                param.put("student_id", stu.getId());
                param.put("student_name", stu.getStudent_name() + schInfoStr);
                param.put("student_encoding", stu.getEncoding());
                param.put("order_encoding", param.get("order_encoding"));

                param.put("businessDetailInfo", DetailBusinessInfoFormat.kuaYueZhiKongString(param));
                param.put("schedulingId", param.get("schedulingId").toString());
                param.put("bizTypeName", "培英精品班");

                ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(
                        "erpv5.DXB_kuayue_zhikong", param);
                processEngine.getExecutionService().createVariables(pi.getId(), param, true);
            } else {
                studentAttendanceService.attandanceSubmit(param);
                //studentAttendanceService.attendanceBjk(param);
            }
        } else {
            studentAttendanceService.attandanceSubmit(param);
            //studentAttendanceService.attendanceBjk(param);
        }
    }

    @Override
    public Page<Map<String, Object>> queryPageForWfj(Map<String, Object> paramMap) throws Exception {
        return this.attendanceDao.queryPageForWfj(paramMap);
    }

    @Override
    public List<Map<String, Object>> queryStudentsForWfdAttn(Map<String, Object> paramMap) throws Exception {
        return this.attendanceDao.queryStudentsForWfdAttn(paramMap);
    }

    @Override
    public List<Map<String, Object>> queryTeachersForWfdAttn(Map<String, Object> paramMap) throws Exception {
        return this.attendanceDao.queryTeachersForWfdAttn(paramMap);
    }

    @Override
    public Integer countAttend1ByOrderCourseId(Map<String, Object> paramMap) throws Exception {
        return attendanceDao.countAttend1ByOrderCourseId(paramMap);
    }

    @Override
    public Integer countAttend2ByOrderCourseId(Map<String, Object> paramMap) throws Exception {
        return attendanceDao.countAttend2ByOrderCourseId(paramMap);
    }

    @Override
    public Integer countAttend3ByOrderCourseId(Map<String, Object> paramMap) throws Exception {
        return attendanceDao.countAttend3ByOrderCourseId(paramMap);
    }

    @Override
    public List<Map<String, Object>> queryTeacherLabelsPage(Map<String, Object> paramMap) throws Exception {
        return attendanceDao.queryTeacherLabelsPage(paramMap);
    }

    @Override
    public void wfdAttandanceBatchSubmit(JSONObject jsonObj, ProcessEngine processEngine, HttpServletRequest request)
            throws Exception {
        Assert.notNull(jsonObj);
        JSONArray jsonArray = jsonObj.getJSONArray("students");
        String course_date = jsonObj.getString("course_date");
        Assert.notNull(course_date);
        String remark = jsonObj.getString("remark");
        Assert.notNull(jsonArray);
        for (int i = 0; i < jsonArray.size(); i++) {
            wfdAttandanceSubmit(course_date, remark, jsonArray.getJSONObject(i), processEngine, request);
        }
    }

    /***
     * 晚辅导 学生考勤
     * 
     * @param remark
     * @param course_date
     * @param jsonObj
     *            {"students":[{"order_course_id":"","scheduling_id":"",
     *            "attend_id"
     *            :"","student_id":"","attend_type":""}],"course_date"
     *            :"","p_remark":""}
     * @param processEngine
     *            工作流引擎
     * @return
     * @throws Exception
     */
    @Override
    public void wfdAttandanceSubmit(String course_date, String remark, JSONObject jsonObj, ProcessEngine processEngine,
            HttpServletRequest request) throws Exception {

        String nowMonth = DateUtil.getCurrDate("MM");
        Long attendType = jsonObj.getLong("attend_type");
        String KQMonth = course_date.substring(5, 7);
        Account user = WebContextUtils.genUser(request);
        Assert.notNull(user);
        Long userId = user.getId();
        jsonObj.put("course_date", course_date);
        jsonObj.put("remark", remark == null ? "" : remark);

        OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
        if (attendType == 30 && !nowMonth.equals(KQMonth)) {
            // 取消考勤，跨月置空
            Long city_id = orgModel.getCityId();
            String flag = DxbCityCfg.getInstance().getConfigItem("workflow.audit.attend." + city_id, "false");
            if (flag.equals("true")) {
                // 走审批流
                HashMap<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("order_course_id", jsonObj.getLong("order_course_id"));
                Object attend_id = jsonObj.get("attend_id");
                if (null != attend_id && !(attend_id instanceof JSONNull)) {
                    paramMap.put("attendanceId", jsonObj.getLong("attend_id"));
                } else {
                    paramMap.put("attendanceId", null);
                }
                Assert.notNull(jsonObj.getLong("student_id"));
                Assert.notNull(jsonObj.getLong("attend_type"));
                paramMap.put("studentId", jsonObj.getLong("student_id"));
                paramMap.put("attendType", jsonObj.getLong("attend_type"));
                paramMap.put("courseDate", DateUtil.stringToDate(course_date, "yyyy-MM-dd"));
                paramMap.put("remark", remark);
                paramMap.put("userId", userId);
                paramMap.put("err_code", null);
                paramMap.put("err_desc", null);
                paramMap.put("branchId", orgModel.getId());
                paramMap.put("cityId", city_id);
                paramMap.put("schedulingId", jsonObj.toString());
                check_kuayue_zhikong_for_wdf(paramMap, orgModel.getId(), user, processEngine);
            } else {

                studentAttendanceService.wfdAttend(jsonObj, userId, orgModel.getId());
            }
        } else {
            studentAttendanceService.wfdAttend(jsonObj, userId, orgModel.getId());
        }
    }

    void check_kuayue_zhikong_for_wdf(Map<String, Object> param, Long branch_id, Account user,
            ProcessEngine processEngine) throws Exception {
        String schInfoStr = "晚辅导跨月考勤置空";

        if (!WorkflowHelper.isDeployed(processEngine, "erpv5.DXB_kuayue_zhikong")) {
            throw new Exception("跨月考勤置空审批尚未发布，请联系管理员发布流程！");
        }

        param.put("branch_id", branch_id);
        param.put("workbenchURL", "/apps/student_index/index-student#/personal-attend/" + param.get("studentId"));

        Map<String, Object> userApplication = new HashMap<String, Object>();

        userApplication.put("APPLICATION_ID", user.getId());
        StringBuilder application = new StringBuilder("跨月考勤置空审批：");

        StudentBusiness stuB = new StudentBusiness();
        stuB.setId(Long.parseLong(param.get("studentId").toString()));
        StudentInfo stu = studentInfoService.selectOneStudent(stuB);

        if (null != param.get("studentId")) {
            application.append("学生ID[");
            application.append(stu.getStudent_name() + "" + param.get("studentId"));
            application.append(schInfoStr);
            application.append("]");
        }

        userApplication.put("APPLICATION", application.toString());
        userApplication.put("STATUS", 1L);
        userApplication.put("CREATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        userApplication.put("CURRENT_STATE", "申请已提交");
        userApplication.put("CURRENT_STEP", "申请提交");

        userApplication.put("BUSI_ID", param.get("attendanceId"));
        // 考勤流程类型11
        userApplication.put("BUSI_TYPE", 11);

        userApplication.put("CURRENT_MAN", user.getEmployeeName());

        userApplication.put("UPDATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        userApplication.put("WORKURL", "#/personal-attend/" + param.get("studentId"));
        userTaskService.insertApplication(userApplication);
        param.put("application_id", userApplication.get("id"));

        param.put("student_id", stu.getId());
        param.put("student_name", stu.getStudent_name() + schInfoStr);
        param.put("student_encoding", stu.getEncoding());
        param.put("order_encoding", param.get("order_encoding"));

        param.put("businessDetailInfo", DetailBusinessInfoFormat.kuaYueZhiKongString(param));
        param.put("bizTypeName", "培英精品班");

        ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_kuayue_zhikong",
                param);
        processEngine.getExecutionService().createVariables(pi.getId(), param, true);
    }

    @Override
    public Page<TAttendance> queryYDYScheduleCoursePage(Map<String, Object> queryParam) throws Exception {
        return attendanceDao.queryYDYScheduleCoursePage(queryParam);
    }
    /**
     * 个性化时间排课批量处理
     */
    @Override
    public ArrayList<TAttendance> addYDYSchedulingList(List<TAttendance>  tAttendanceList, OrgModel orgModel) throws Exception {
        ArrayList<TAttendance> attendanceArrayList=new ArrayList<TAttendance>();
        if(!CollectionUtils.isEmpty(tAttendanceList)){
    		for(TAttendance tAttendance:tAttendanceList){
                ArrayList<TAttendance> tempList=addYDYScheduling( tAttendance, orgModel);
                attendanceArrayList.addAll(tempList);
    		}
    	}
    	return attendanceArrayList;
    }

    @Override
    public  ArrayList<TAttendance> addYDYScheduling(TAttendance tAttendance, OrgModel orgModel) throws Exception {
        // 获取总的课程课次数
        Assert.notNull(tAttendance.getStartDate(), "排课日期不允许为空!");
    	Assert.notNull(tAttendance.getStart_time(), "上课时间不允许为空!");
    	Assert.notNull(tAttendance.getEnd_time(), "下课时间不允许为空!");
      	Assert.notNull(tAttendance.getSubject_id(), "科目不允许为空!");
    	Assert.notNull( tAttendance.getTeacher_id(), "教师不允许为空!");
    	Assert.notNull( tAttendance.getStudent_id(), "学生不允许为空!");
        tAttendance.setAttend_branch_id(orgModel.getId());
        String schedule_type = tAttendance.getScheduleType();
        Long studentId = tAttendance.getStudent_id();
        String orderCourseIds = tAttendance.getOrderCourseIds();

        String[] orderCourseIdArry = orderCourseIds.split(",");
        if (orderCourseIdArry.length == 0|| "".equals(orderCourseIds)) {
            throw new Exception("请选择对应的课程商品!");
        }
        //
//      List<TOrderCourse> studentCourse = orderService.queryStudentOrderCourse(studentId, 2l, null);
//      checkedExistEarlyCourse(studentCourse, orderCourseIdArry);
        Map<String,Object> curCounselorMap=new HashMap<String,Object>();
        curCounselorMap.put("studentId",tAttendance.getStudent_id());
        curCounselorMap.put("buId",orgModel.getBuId());
        Map<String, Object> counselorMap=studentInfoService.queryStudentCurrCounselors( curCounselorMap );
        if(counselorMap!=null&&counselorMap.get("LEARNINGMGR_ID")!=null){
            tAttendance.setCounselor_id(Long.parseLong(counselorMap.get("LEARNINGMGR_ID").toString()));
        }
        ArrayList<TAttendance> tAttendanceArrayList=new ArrayList<>();
        // 按时间排课 日期 上课时间 下课时间 科目 上课教师
        if ("time".equals(schedule_type)) {
            tAttendanceArrayList=processTimeSchedule(orderCourseIdArry,tAttendance,orgModel);
        }
        // 按期排课 周期 排课课时 开始时间 截止时间 上课时间 下课时间 科目 上课教师
        if ("period".equals(schedule_type)) {
            String attend_class_period = tAttendance.getAttendClassPeriod();
            Long scheduleTimes = tAttendance.getCourseScheduleCount(); // 总的计划排课的课时
            Assert.notNull( attend_class_period, "上课周期不允许为空!");
            Assert.notNull( scheduleTimes, "计划时间不允许为空!");
            tAttendanceArrayList=processPeriodSchedule(orderCourseIdArry,tAttendance,orgModel);
        }
        return tAttendanceArrayList;
    }

    /**
     * 按时间排课
     * @param orderCourseIdArry
     * @param tAttendance
     * @param orgModel
     * @throws Exception
     */
    private ArrayList<TAttendance> processTimeSchedule(String[] orderCourseIdArry,TAttendance tAttendance,OrgModel orgModel) throws Exception {
        int courseTimes=0;
        Double tmpAttendanceAmount=0.0;
        Long tempTCourseTime=0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String endTime = tAttendance.getEnd_time();
        if (Constants.DAY_START_TIME.equals(endTime)) {
            endTime = Constants.DAY_END_TIME;
        }
        Long totalMiniutes= ((format.parse(endTime).getTime() - format.parse(tAttendance.getStart_time()).getTime()) % 86400000) / 60000;  //换算成分钟,一次课排课多少分钟
        ArrayList<TScheduleSplitTime> tScheduleSplitTimeArrayList =new ArrayList<TScheduleSplitTime>();
        ArrayList<TAttendance> tAttendanceArrayList=new ArrayList<TAttendance>() ;
        TAttendance tAttendanceTmp = new TAttendance();
        for (String orderCourseId : orderCourseIdArry) {
            TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(Long.parseLong(orderCourseId));
            if(tOrderCourse.getHour_len()==null){
                throw new Exception("课程的课时长度不允许为空!");
            }
            if(totalMiniutes%tOrderCourse.getHour_len()==0){
                courseTimes= (int) (totalMiniutes / tOrderCourse.getHour_len());
            }else{
                throw new Exception("请确保课时长度对应课程长度可有效计算!");
            }

            tAttendanceTmp.setApply_id(tAttendance.getApply_id());
            tAttendanceTmp.setStudent_id(tAttendance.getStudent_id());
            tAttendanceTmp.setCounselor_id(tAttendance.getCounselor_id());
            tAttendanceTmp.setAttend_type(TAttendance.AttendTypeEnum.ZCPK.getCode());
            tAttendanceTmp.setOrder_course_id(Long.parseLong(orderCourseId));
            tAttendanceTmp.setAttend_date(new Date());
            tAttendanceTmp.setAttend_branch_id(orgModel.getId());
            tAttendanceTmp.setCreate_time(new Date());
            tAttendanceTmp.setCreate_user(orgModel.getUserId());
            tAttendanceTmp.setCourse_date(Long.parseLong(sdf.format(sdf2.parse(tAttendance.getStartDate()))));
            tAttendanceTmp.setTeacher_id(tAttendance.getTeacher_id());
            tAttendanceTmp.setStart_time(tAttendance.getStart_time());
            tAttendanceTmp.setEnd_time(tAttendance.getEnd_time());
            tAttendanceTmp.setSubject_id(tAttendance.getSubject_id());

            Long course_schedule_count = tOrderCourse.getCourse_schedule_count() == null ? 0 : tOrderCourse.getCourse_schedule_count(); //剩余可排课时
            Long course_surplus_count = course_schedule_count - courseTimes;
            Long tempCount=course_surplus_count;
            Boolean flag=true;
            if (course_surplus_count < 0) {
                flag=false;
                course_surplus_count = 0L;
                tempTCourseTime+=course_schedule_count;
                tmpAttendanceAmount+=(course_schedule_count*tOrderCourse.getDiscount_unit_price());
                courseTimes = (int) (0 + course_schedule_count);
                tOrderCourse.setCourse_schedule_count(0L);
            } else {
                tmpAttendanceAmount+=(courseTimes*tOrderCourse.getDiscount_unit_price());
                tempTCourseTime+=courseTimes;
                tAttendanceTmp.setAttend_amount(tmpAttendanceAmount);
                tAttendanceTmp.setCourseTimes(tempTCourseTime);
                tOrderCourse.setCourse_schedule_count(course_surplus_count);
                tempTCourseTime=0L;
                tmpAttendanceAmount=0.0;
            }

            //记录拆分课时的情况
            TScheduleSplitTime tScheduleSplitTime =new TScheduleSplitTime();
            tScheduleSplitTime.setOrderCourseId(Long.parseLong(orderCourseId));
            tScheduleSplitTime.setTimes(Long.parseLong(String.valueOf(courseTimes)));
            tScheduleSplitTime.setAttendAmount(tScheduleSplitTime.getTimes()*tOrderCourse.getDiscount_unit_price());
            tScheduleSplitTimeArrayList.add(tScheduleSplitTime);

            totalMiniutes-=(long) (courseTimes*tOrderCourse.getHour_len());
            tOrderCourseDao.updateOrderCourse(tOrderCourse);
            if(course_surplus_count> 0|| tempCount==0){
                //校验改排课记录是否有已排课的情况
                Integer conflictCount=attendanceDao.queryYDYConflictScheduleTimes(tAttendanceTmp);
                if(conflictCount!=null&&conflictCount>0){
                    throw new Exception("当前时段该老师或者学生已经有档期,不允许新增排课!");
                }

                tAttendanceTmp.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.YDY_ATTEND_PREFIX));

                attendanceDao.saveAttandance(tAttendanceTmp);
                HashMap<String,Object> param=new HashMap<String,Object>();
                param.put("id",tAttendanceTmp.getId());
                Page<TAttendance> attendancePage=attendanceDao.queryYDYScheduleCoursePage(param);
                tAttendanceArrayList.addAll(attendancePage.getResult());
                if(!CollectionUtils.isEmpty(tScheduleSplitTimeArrayList)){
                    for(TScheduleSplitTime tmpTScheduleSplitTime : tScheduleSplitTimeArrayList){
                        tmpTScheduleSplitTime.setAttendId(tAttendanceTmp.getId());
                        tmpTScheduleSplitTime.setCreate_time(new Date());
                        //新增记录
                        tScheduleSplitTimeDao.insert(tmpTScheduleSplitTime);
                    }
                    tScheduleSplitTimeArrayList.clear();
                }
                // 考勤历史表（T_ATTENDANCE_HT），记录一条流水信息
                attendanceDao.saveAttandanceHt(tAttendanceTmp);
            }
            if(course_surplus_count>=0&&flag){
                break;
            }
        }
        // 是否选择课时数足够消耗，不够需要抛出异常
        if (totalMiniutes > 0) {
            throw new Exception("单据的剩余课时不足以抵扣拟排课课时!");
        }
        return tAttendanceArrayList;
    }

    /**
     * 按照周期排课
     * @param orderCourseIdArry
     * @param tAttendance
     * @param orgModel
     * @throws Exception
     */
    private ArrayList<TAttendance> processPeriodSchedule(String[] orderCourseIdArry,TAttendance tAttendance,OrgModel orgModel) throws Exception {
        Long surplusTimes = 0L;
        int courseTimes=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date start_date = sdf2.parse(tAttendance.getStartDate());
        String start_time = tAttendance.getStart_time();
        String attend_class_period = tAttendance.getAttendClassPeriod();
        Long scheduleTimes = tAttendance.getCourseScheduleCount(); // 总的计划排课的课时
        String endTime = tAttendance.getEnd_time();
        if (Constants.DAY_START_TIME.equals(endTime)) {
            endTime = Constants.DAY_END_TIME;
        }
        Long totalMiniutes= ((format.parse(endTime).getTime() - format.parse(tAttendance.getStart_time()).getTime()) % 86400000) / 60000;  //换算成分钟,一次课排课多少分钟
        Date end_date = sdf2.parse(tAttendance.getEndDate());
        int courseTotal_num = 0;
        Long tempCourseTime=0L;
        Double tmpAttendanceAmount=0.0;
        ArrayList<TAttendance> tAttendanceArrayList=new ArrayList<TAttendance>() ;
        ArrayList<TScheduleSplitTime> tScheduleSplitTimeArrayList =new ArrayList<TScheduleSplitTime>();
        for (String orderCourseId : orderCourseIdArry) {
            TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(Long.parseLong(orderCourseId));
            if(tOrderCourse.getHour_len()==null){
                throw new Exception("课程的课时长度不允许为空!");
            }
            if(totalMiniutes%tOrderCourse.getHour_len()==0){
                courseTimes=(int) (totalMiniutes/tOrderCourse.getHour_len());
            }else{
                throw new Exception("请确保课时长度对应课程长度可有效计算!");
            }
            surplusTimes += tOrderCourse.getCourse_schedule_count();
            Calendar c = Calendar.getInstance();
            while (courseTotal_num < scheduleTimes && tOrderCourse.getCourse_schedule_count()>0
                    && (start_date.getTime() <= end_date.getTime())) {
                // 查询当前时间是第几周，第几天
                c.setTime(start_date);
                int week_num = c.get(Calendar.WEEK_OF_YEAR); // 当前时间是第几周
                long day_num = c.get(Calendar.DAY_OF_WEEK); // 1=Sunday,2=Monday,,,7=Saturday。
                // 当前时间是本周第几天
                boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
                // 获取周几
                // 若一周第一天为星期天，则-1
                if (isFirstSunday) {
                    day_num = day_num - 1;
                    if (day_num == 0) {
                        day_num = 7;
                    }
                }
                // 是否当天属于排课周期范围
                if(attend_class_period.contains(String.valueOf(day_num))) {
                    // TODO 判断是否数据节假日,从节假日表过滤，目前先不实现

                    String course_date = sdf.format(start_date);
                    // 考勤表（T_ATTENDANCE）中插入一条考勤状态为28-正常排课的考勤记录
                    TAttendance tAttendanceTmp = new TAttendance();
                    tAttendanceTmp.setApply_id(tAttendance.getApply_id());
                    tAttendanceTmp.setCounselor_id(tAttendance.getCounselor_id());
                    tAttendanceTmp.setStudent_id(tAttendance.getStudent_id());
                    tAttendanceTmp.setAttend_type(TAttendance.AttendTypeEnum.ZCPK.getCode());
                    tAttendanceTmp.setOrder_course_id(Long.parseLong(orderCourseId));
                    tAttendanceTmp.setAttend_date(new Date());
                    tAttendanceTmp.setAttend_branch_id(orgModel.getId());
                    tAttendanceTmp.setCreate_time(new Date());
                    tAttendanceTmp.setCreate_user(orgModel.getUserId());
                    tAttendanceTmp.setCourse_date(Long.parseLong(course_date));
                    tAttendanceTmp.setTeacher_id(tAttendance.getTeacher_id());
                    tAttendanceTmp.setSubject_id(tAttendance.getSubject_id());
                    tAttendanceTmp.setStart_time(start_time);
                    tAttendanceTmp.setEnd_time(tAttendance.getEnd_time());

                    // （T_ORDER_COURSE），排课课次(COURSE_SCHEDULING_COUNT)-1
                    Long course_schedule_count = tOrderCourse.getCourse_schedule_count() == null ? 0 : tOrderCourse.getCourse_schedule_count();
                    Long course_surplus_count = course_schedule_count - courseTimes;
                    if (course_surplus_count < 0) {
                        course_surplus_count = 0L;
                        tempCourseTime+=tOrderCourse.getCourse_schedule_count();
                        tmpAttendanceAmount+=( tOrderCourse.getCourse_schedule_count()*tOrderCourse.getDiscount_unit_price());
                        courseTimes = (int) ( 0 + tOrderCourse.getCourse_schedule_count());
                        courseTotal_num += courseTimes;
                        tOrderCourse.setCourse_schedule_count(0L);
                        totalMiniutes-=(long) (courseTimes*tOrderCourse.getHour_len());
                        //记录拆分课时的情况
                        TScheduleSplitTime tScheduleSplitTime =new TScheduleSplitTime();
                        tScheduleSplitTime.setOrderCourseId(Long.parseLong(orderCourseId));
                        tScheduleSplitTime.setTimes(Long.parseLong(String.valueOf(courseTimes)));
                        tScheduleSplitTime.setAttendAmount(tScheduleSplitTime.getTimes()*tOrderCourse.getDiscount_unit_price());
                        tScheduleSplitTimeArrayList.add(tScheduleSplitTime);
                    } else {
                        tOrderCourse.setCourse_schedule_count(course_schedule_count - courseTimes);
                        tmpAttendanceAmount+=(courseTimes*tOrderCourse.getDiscount_unit_price());
                        tempCourseTime+=courseTimes;
                        tAttendanceTmp.setAttend_amount(tmpAttendanceAmount);
                        tAttendanceTmp.setCourseTimes(tempCourseTime);

                        //校验改排课记录是否有已排课的情况
                        Integer conflictCount=attendanceDao.queryYDYConflictScheduleTimes(tAttendanceTmp);
                        if(conflictCount!=null&&conflictCount>0){
                            throw new Exception("当前时段该老师或者学生已经有档期,不允许新增排课!");
                        }

                        //记录拆分课时的情况
                        TScheduleSplitTime tScheduleSplitTime =new TScheduleSplitTime();
                        tScheduleSplitTime.setOrderCourseId(Long.parseLong(orderCourseId));
                        tScheduleSplitTime.setTimes(Long.parseLong(String.valueOf(courseTimes)));
                        tScheduleSplitTime.setAttendAmount(tScheduleSplitTime.getTimes()*tOrderCourse.getDiscount_unit_price());
                        tScheduleSplitTimeArrayList.add(tScheduleSplitTime);



                        tAttendanceTmp.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.YDY_ATTEND_PREFIX));
                        attendanceDao.saveAttandance(tAttendanceTmp);
                        HashMap<String,Object> param=new HashMap<String,Object>();
                        param.put("id",tAttendanceTmp.getId());
                        Page<TAttendance> attendancePage=attendanceDao.queryYDYScheduleCoursePage(param);
                        tAttendanceArrayList.addAll(attendancePage.getResult());
                        if(!CollectionUtils.isEmpty(tScheduleSplitTimeArrayList)){
                            for(TScheduleSplitTime tmpTScheduleSplitTime : tScheduleSplitTimeArrayList){
                                tmpTScheduleSplitTime.setAttendId(tAttendanceTmp.getId());
                                tmpTScheduleSplitTime.setCreate_time(new Date());
                                //新增记录
                                tScheduleSplitTimeDao.insert(tmpTScheduleSplitTime);
                            }
                            tScheduleSplitTimeArrayList.clear();
                        }
                        // 考勤历史表（T_ATTENDANCE_HT），记录一条流水信息
                        attendanceDao.saveAttandanceHt(tAttendanceTmp);
                        courseTotal_num += courseTimes;
                        c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                        start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
                        courseTimes=tempCourseTime.intValue();
                        tempCourseTime=0L;
                        tmpAttendanceAmount=0.0;
                    }
                    tOrderCourse.setCourse_schedule_count(course_surplus_count); // 剩余课次减少排课课时
                    tOrderCourseDao.updateOrderCourse(tOrderCourse);
                }else{
                    c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                    start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
                }
            }
        }
        // 是否选择课时数足够消耗，不够需要抛出异常
        if (surplusTimes < scheduleTimes) {
            throw new Exception("单据的剩余课时不足以抵扣拟排课课时!");
        }
        return tAttendanceArrayList;
    }

    /**
     * 校验是否有更新的单据需要优先消耗
     * 
     * @param studentCourseList
     * @param branch_id
     * @throws Exception
     */
    private void checkedExistEarlyCourse(List<TOrderCourse> studentCourseList, Long branch_id) throws Exception {
        Map<Long, TOrderCourse> mapCurrBranchMap = new HashMap<Long, TOrderCourse>();
        Map<Long, TOrderCourse> mapNoCurrBranchMap = new HashMap<Long, TOrderCourse>();
        if (studentCourseList != null && studentCourseList.size() > 0) {
            for (TOrderCourse tOrderCourse : studentCourseList) {
                if (NumberUtils.isLongValueEqual(tOrderCourse.getBranch_id(), branch_id)) {
                    mapCurrBranchMap.put(tOrderCourse.getOrder_create_date().getTime(), tOrderCourse);
                    continue;
                }
                if (tOrderCourse.getCourse_surplus_count() == 0) {
                    continue;
                }
                mapNoCurrBranchMap.put(tOrderCourse.getOrder_create_date().getTime(), tOrderCourse);
            }
            Set<Long> currSet = mapCurrBranchMap.keySet();
            Object[] currObj = currSet.toArray();
            Arrays.sort(currObj);
            Set<Long> noCurrSet = mapNoCurrBranchMap.keySet();
            Object[] noCurrObj = noCurrSet.toArray();
            Arrays.sort(noCurrObj);
            if (noCurrObj != null && noCurrObj.length > 0 && currObj.length>0 && ((Long) currObj[0] > (Long) noCurrObj[0])) {
                TOrderCourse tOrderCourse = mapNoCurrBranchMap.get(noCurrObj[0]);
                throw new Exception("该学生存在更早的单据:[" + tOrderCourse.getOrder_no() + "],所在的校区:["
                        + tOrderCourse.getBranch_name() + "]请优先消耗此订单!");
            }
        }
    }

    /**
     * 校验是否有更早的单据需要优先消耗
     *
     * @param studentCourseList
     * @param orderCourseIdArry
     * @throws Exception
     */
    private void checkedExistEarlyCourse(List<TOrderCourse> studentCourseList,String[] orderCourseIdArry) throws Exception {
        Map<Long, TOrderCourse> mapCurrBranchMap = new HashMap<Long, TOrderCourse>();
        Map<Long, TOrderCourse> mapNoCurrBranchMap = new HashMap<Long, TOrderCourse>();
        if (studentCourseList != null && studentCourseList.size() > 0) {
            for (TOrderCourse tOrderCourse : studentCourseList) {
                if (tOrderCourse.getCourse_schedule_count() == 0) {
                    continue;
                }
                mapCurrBranchMap.put(tOrderCourse.getOrder_create_date().getTime(), tOrderCourse);
                for(String orderCourseId:orderCourseIdArry){
                    if(tOrderCourse.getId().toString().equals(orderCourseId)){
                        mapNoCurrBranchMap.put(tOrderCourse.getOrder_create_date().getTime(), tOrderCourse);
                    }
                }
            }
            Set<Long> currSet = mapCurrBranchMap.keySet();
            Object[] currObj = currSet.toArray();
            Arrays.sort(currObj);
            Set<Long> noCurrSet = mapNoCurrBranchMap.keySet();
            Object[] noCurrObj = noCurrSet.toArray();
            Arrays.sort(noCurrObj);
            if (noCurrObj != null && noCurrObj.length > 0 && currObj.length>0 && ((Long) currObj[0] < (Long) noCurrObj[0])) {
                TOrderCourse tOrderCourse = mapCurrBranchMap.get(currObj[0]);
                throw new Exception("该学生存在更早的单据:[" + tOrderCourse.getOrder_no() + "],所在的校区:["
                        + tOrderCourse.getBranch_name() + "]请优先消耗此订单!");
            }
        }
    }

    public  ArrayList<TAttendance> queryYDYPeriodScheduleList(TAttendance tAttendance, OrgModel orgModel) throws Exception {
        // 获取总的课程课次数
        Assert.notNull(tAttendance.getStartDate(), "排课日期不允许为空!");
        Assert.notNull(tAttendance.getStart_time(), "上课时间不允许为空!");
        Assert.notNull(tAttendance.getEnd_time(), "下课时间不允许为空!");
        Assert.notNull(tAttendance.getSubject_id(), "科目不允许为空!");
        Assert.notNull( tAttendance.getTeacher_id(), "教师不允许为空!");
        Assert.notNull( tAttendance.getStudent_id(), "学生不允许为空!");
        tAttendance.setAttend_branch_id(orgModel.getId());
        String schedule_type = tAttendance.getScheduleType();
        String orderCourseIds = tAttendance.getOrderCourseIds();

        String[] orderCourseIdArry = orderCourseIds.split(",");
        if (orderCourseIdArry.length == 0|| "".equals(orderCourseIds)) {
            throw new Exception("请选择对应的课程商品!");
        }

        Map<String,Object> curCounselorMap=new HashMap<String,Object>();
        curCounselorMap.put("studentId",tAttendance.getStudent_id());
        curCounselorMap.put("buId",orgModel.getBuId());
        Map<String, Object> counselorMap=studentInfoService.queryStudentCurrCounselors( curCounselorMap );
        if(counselorMap!=null&&counselorMap.get("LEARNINGMGR_ID")!=null){
            tAttendance.setCounselor_id(Long.parseLong(counselorMap.get("LEARNINGMGR_ID").toString()));
        }
        ArrayList<TAttendance> tAttendanceArrayList=new ArrayList<>();
        // 按时间排课 日期 上课时间 下课时间 科目 上课教师
        if ("time".equals(schedule_type)) {
            tAttendanceArrayList=processTimeSchedule(orderCourseIdArry,tAttendance,orgModel);
        }
        // 按期排课 周期 排课课时 开始时间 截止时间 上课时间 下课时间 科目 上课教师
        if ("period".equals(schedule_type)) {
            Assert.notNull( tAttendance.getAttendClassPeriod(), "上课周期不允许为空!");
            Assert.notNull( tAttendance.getCourseScheduleCount(), "计划时间不允许为空!");
            tAttendanceArrayList=queryPeriodSchedule(orderCourseIdArry,tAttendance,orgModel);
        }
        return tAttendanceArrayList;
    }


    /**
     * 查询周期排课的列表
     * @param orderCourseIdArry
     * @param tAttendance
     * @param orgModel
     * @throws Exception
     */
    private ArrayList<TAttendance> queryPeriodSchedule(String[] orderCourseIdArry,TAttendance tAttendance,OrgModel orgModel) throws Exception {
        Long surplusTimes = 0l;
        int courseTimes=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date start_date = sdf2.parse(tAttendance.getStartDate());
        String start_time = tAttendance.getStart_time();
        String end_time = tAttendance.getEnd_time();
        String attend_class_period = tAttendance.getAttendClassPeriod();
        Long scheduleTimes = tAttendance.getCourseScheduleCount(); // 总的计划排课的课时
        Long totalMiniutes= ((format.parse(tAttendance.getEnd_time()).getTime() - format.parse(tAttendance.getStart_time()).getTime()) % 86400000) / 60000;  //换算成分钟,一次课排课多少分钟
        Date end_date = sdf2.parse(tAttendance.getEndDate());
        int courseTotal_num = 0;
        Long tempCourseTime=0l;
        Double tmpAttendanceAmount=0.0;
        ArrayList<TAttendance> tAttendanceArrayList=new ArrayList<TAttendance>() ;
        ArrayList<TScheduleSplitTime> tScheduleSplitTimeArrayList =new ArrayList<TScheduleSplitTime>();
        for (String orderCourseId : orderCourseIdArry) {
            TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(Long.parseLong(orderCourseId));
            if(tOrderCourse.getHour_len()==null){
                throw new Exception("课程的课时长度不允许为空!");
            }
            if(totalMiniutes%tOrderCourse.getHour_len()==0){
                courseTimes=(int) (totalMiniutes/tOrderCourse.getHour_len());
            }else{
                throw new Exception("请确保课时长度对应课程长度可有效计算!");
            }
            surplusTimes += tOrderCourse.getCourse_schedule_count();
            Calendar c = Calendar.getInstance();
            while (courseTotal_num < scheduleTimes && tOrderCourse.getCourse_schedule_count()>0
                    && (start_date.getTime() <= end_date.getTime())) {
                // 查询当前时间是第几周，第几天
                c.setTime(start_date);
                int week_num = c.get(Calendar.WEEK_OF_YEAR); // 当前时间是第几周
                long day_num = c.get(Calendar.DAY_OF_WEEK); // 1=Sunday,2=Monday,,,7=Saturday。
                // 当前时间是本周第几天
                boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
                // 获取周几
                // 若一周第一天为星期天，则-1
                if (isFirstSunday) {
                    day_num = day_num - 1;
                    if (day_num == 0) {
                        day_num = 7;
                    }
                }
                // 是否当天属于排课周期范围
                if(attend_class_period.contains(String.valueOf(day_num))) {
                    // TODO 判断是否数据节假日,从节假日表过滤，目前先不实现

                    String course_date = sdf.format(start_date);
                    // 考勤表（T_ATTENDANCE）中插入一条考勤状态为28-正常排课的考勤记录
                    TAttendance tAttendanceTmp = new TAttendance();
                    tAttendanceTmp.setCounselor_id(tAttendance.getCounselor_id());
                    tAttendanceTmp.setStudent_id(tAttendance.getStudent_id());
                    tAttendanceTmp.setAttend_type(TAttendance.AttendTypeEnum.ZCPK.getCode());
                    tAttendanceTmp.setOrder_course_id(Long.parseLong(orderCourseId));
                    tAttendanceTmp.setAttend_date(new Date());
                    tAttendanceTmp.setAttend_branch_id(orgModel.getId());
                    tAttendanceTmp.setCreate_time(new Date());
                    tAttendanceTmp.setCreate_user(orgModel.getUserId());
                    tAttendanceTmp.setCourse_date(Long.parseLong(course_date));
                    tAttendanceTmp.setTeacher_id(tAttendance.getTeacher_id());
                    tAttendanceTmp.setSubject_id(tAttendance.getSubject_id());
                    tAttendanceTmp.setStart_time(start_time);
                    tAttendanceTmp.setEnd_time(end_time);

                    // （T_ORDER_COURSE），排课课次(COURSE_SCHEDULING_COUNT)-1
                    Long course_schedule_count = tOrderCourse.getCourse_schedule_count() == null ? 0 : tOrderCourse.getCourse_schedule_count();
                    Long course_surplus_count = course_schedule_count - courseTimes;
                    if (course_surplus_count < 0) {
                        course_surplus_count = 0l;
                        tempCourseTime+=tOrderCourse.getCourse_schedule_count();
                        tmpAttendanceAmount+=( tOrderCourse.getCourse_schedule_count()*tOrderCourse.getDiscount_unit_price());
                        courseTimes = (int) ( 0 + tOrderCourse.getCourse_schedule_count());
                        courseTotal_num += courseTimes;
                        tOrderCourse.setCourse_schedule_count(0l);
                        totalMiniutes-=(long) (courseTimes*tOrderCourse.getHour_len());
                        //记录拆分课时的情况
                        TScheduleSplitTime tScheduleSplitTime =new TScheduleSplitTime();
                        tScheduleSplitTime.setOrderCourseId(Long.parseLong(orderCourseId));
                        tScheduleSplitTime.setTimes(Long.parseLong(String.valueOf(courseTimes)));
                        tScheduleSplitTime.setAttendAmount(tScheduleSplitTime.getTimes()*tOrderCourse.getDiscount_unit_price());
                        tScheduleSplitTimeArrayList.add(tScheduleSplitTime);
                    } else {
                        tOrderCourse.setCourse_schedule_count(course_schedule_count - courseTimes);
                        tmpAttendanceAmount+=(courseTimes*tOrderCourse.getDiscount_unit_price());
                        tempCourseTime+=courseTimes;
                        tAttendanceTmp.setAttend_amount(tmpAttendanceAmount);
                        tAttendanceTmp.setCourseTimes(tempCourseTime);

                        //记录拆分课时的情况
                        TScheduleSplitTime tScheduleSplitTime =new TScheduleSplitTime();
                        tScheduleSplitTime.setOrderCourseId(Long.parseLong(orderCourseId));
                        tScheduleSplitTime.setTimes(Long.parseLong(String.valueOf(courseTimes)));
                        tScheduleSplitTime.setAttendAmount(tScheduleSplitTime.getTimes()*tOrderCourse.getDiscount_unit_price());
                        tScheduleSplitTimeArrayList.add(tScheduleSplitTime);

                        tAttendanceTmp.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.YDY_ATTEND_PREFIX));

                        tAttendanceArrayList.add(tAttendanceTmp);

                        courseTotal_num += courseTimes;
                        c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                        start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
                        courseTimes=tempCourseTime.intValue();
                        tempCourseTime=0l;
                        tmpAttendanceAmount=0.0;
                    }
                }else{
                    c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                    start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
                }
            }
        }
        // 是否选择课时数足够消耗，不够需要抛出异常
        if (surplusTimes < scheduleTimes) {
            throw new Exception("单据的剩余课时不足以抵扣拟排课课时!");
        }
        return tAttendanceArrayList;
    }


    @Override
    public void cancelYDYScheduling(String attend_ids) throws Exception {
        String[] attendIdList = attend_ids.split(",");
        for (String attendId : attendIdList) {
            TAttendance tAttendanceTmp = attendanceDao.queryAttendanceInfoById(Long.parseLong(attendId));
            if(tAttendanceTmp.getAttend_type()==TAttendance.AttendTypeEnum.YJKQ.getCode()){
            	throw new Exception("考勤过的记录不允许取消!");
            }
            tAttendanceTmp.setAttend_type(TAttendance.AttendTypeEnum.QXPK.getCode());
            // 查询出对应的考勤信息
            attendanceDao.updateAttendance(tAttendanceTmp);
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("attendId",tAttendanceTmp.getId());
            ArrayList<TScheduleSplitTime> tScheduleSplitTimeList=tScheduleSplitTimeDao.queryTScheduleSplitTimeList(param);
            for(TScheduleSplitTime tScheduleSplitTime:tScheduleSplitTimeList){
                TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(tScheduleSplitTime.getOrderCourseId());
                Long course_schedule_count = tOrderCourse.getCourse_schedule_count() == null ? 0 : tOrderCourse .getCourse_schedule_count();
                tOrderCourse.setCourse_schedule_count(course_schedule_count+tScheduleSplitTime.getTimes());
                tOrderCourseDao.updateOrderCourse(tOrderCourse);
            }
            attendanceDao.updateAttendanceHt(tAttendanceTmp);
        }

    }

    @Override
    public void updateYDYScheduling(TAttendance tAttendance) throws Exception {
        // 已经取消排课的记录，不能修改
        if (tAttendance.getAttend_type().equals(TAttendance.AttendTypeEnum.QXPK.getCode())) {
            throw new Exception("异常:已经取消排课的记录，不能修改！");
        }
        //校验改排课记录是否有已排课的情况
        Integer conflictCount=attendanceDao.queryYDYConflictScheduleTimes(tAttendance);
        if(conflictCount!=null&&conflictCount>0){
            throw new Exception("当前时段该老师已经有档期,不允许修改该排课!");
        }
        // TODO 排课单绑定的订单不能修改，只能排课取消
        // 可以修改上课日期、上课时间、下课时间、上课老师、上课科目
        attendanceDao.updateAttendance(tAttendance);

    }

    @Override
    public List<Map<String, Object>> queryWfdCourseAttendRecord(Map<String, Object> param) throws Exception {
        return attendanceDao.queryWfdCourseAttendRecord(param);
    }

    private void checkBeforeAttend(TAttendance attend, TAttendance oldAttend, Long branchId) throws Exception {
        String errMsgPrefix = "考勤单号[" + attend.getId() + "]，";

        Long attendType = attend.getAttend_type();
        if (oldAttend.getAttend_type() == attendType) {
            throw new Exception(errMsgPrefix
                    + (attendType == Constants.AttendType.YDY_NORMAL_ATTEND ? "已正常上课，"
                            : (attendType == Constants.AttendType.YDY_CANCEL_SCHED ? "已排课取消，" : "已置空，")) + "不能考勤！");
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("studentId", attend.getStudent_id());
        StudentInfo student = this.studentInfoService.queryStudentById(paramMap);
        if (student == null) {
            throw new Exception(errMsgPrefix + "学生不存在，不能考勤！");
        }
    }

    @Override
    public void ydyAttendBatchSubmit(Collection<TAttendance> attendList, Map<String, Object> paramMap,
            ProcessEngine processEngine) throws Exception {
        if (CollectionUtils.isEmpty(attendList)) {
            return;
        }

        Long branchId = (Long) paramMap.get("branchId");
        for (TAttendance attend : attendList) {
            TAttendance oldAttend = this.attendanceDao.queryAttendanceInfoById(attend.getId());
            checkBeforeAttend(attend, oldAttend, branchId);
            
            paramMap.put("lastAttendType", oldAttend.getAttend_type()); // 记录上一次考勤状态

            Long attendType = attend.getAttend_type();
            attend.setAttend_date(oldAttend.getAttend_date());
            attend.setOrder_course_id(oldAttend.getOrder_course_id());
            attend.setStart_time(oldAttend.getStart_time());
            attend.setEnd_time(oldAttend.getEnd_time());
            attend.setAttend_amount(oldAttend.getAttend_amount());
            // 考勤作废操作，跨月置空检查
            if (attendType == Constants.AttendType.YDY_CANCEL_ATTEND) {
                check_kuayue_zhikong_ydy(attend, paramMap, processEngine);
            } else {
                this.studentAttendanceService.ydyAttend(attend, paramMap);
            }
        }
    }

    /**
     * 1对1跨月考勤置空处理
     * 
     * @param attend
     *            考勤信息
     * @param param
     *            地区、校区、处理人信息
     * @param processEngine
     *            工作流引擎
     * @throws Exception
     */
    private void check_kuayue_zhikong_ydy(TAttendance attend, Map<String, Object> param, ProcessEngine processEngine)
            throws Exception {
        String nowMonth = DateUtil.getCurrDate("MM");
        String KQMonth = DateUtil.dateToString(attend.getAttend_date(), "MM");
        Long attendType = attend.getAttend_type();
        if (!nowMonth.equals(KQMonth)) {
            String city_id = param.get("cityId").toString();
            String flag = DxbCityCfg.getInstance().getConfigItem("workflow.audit.attend." + city_id, "false");
            if ("true".equals(flag)) {
                if (!WorkflowHelper.isDeployed(processEngine, "erpv5.DXB_kuayue_zhikong")) {
                    throw new Exception("跨月考勤置空审批尚未发布，请联系管理员发布流程！");
                }

                Integer auditCount = this.attendanceDao.countAudit(attend.getId());
                if (auditCount != null && auditCount > 0) {
                    throw new Exception("该考勤记录已提交审批，不能再次提交！");
                }

                StringBuilder application = new StringBuilder("跨月考勤置空审批：");
                Map<String, Object> queryParam = new HashMap<String, Object>();
                queryParam.put("studentId", attend.getStudent_id());
                StudentInfo stu = studentInfoService.queryStudentById(queryParam);
                String schInfoStr = "1对1跨月考勤置空";
                if (stu != null) {
                    application.append("学生ID[");
                    application.append(stu.getStudent_name() + "" + stu.getId());
                    application.append(schInfoStr);
                    application.append("]");
                }

                Map<String, Object> userApplication = new HashMap<String, Object>();
                userApplication.put("APPLICATION_ID", param.get("userId"));
                userApplication.put("APPLICATION", application.toString());
                userApplication.put("STATUS", 1L);
                userApplication.put("CREATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                userApplication.put("CURRENT_STATE", "申请已提交");
                userApplication.put("CURRENT_STEP", "申请提交");

                userApplication.put("BUSI_ID", attend.getId().toString());
                // 考勤流程类型11
                userApplication.put("BUSI_TYPE", "11");

                if (null != param.get("userName")) {
                    userApplication.put("CURRENT_MAN", param.get("userName"));
                }

                userApplication.put("UPDATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                userTaskService.insertApplication(userApplication);
                param.put("application_id", userApplication.get("id"));
                param.put("studentId", stu.getId());
                param.put("student_name", stu.getStudent_name() + schInfoStr);
                param.put("student_encoding", stu.getEncoding());
                param.put("branch_id", param.get("branchId"));
                param.put("attendanceId", attend.getId());
                param.put("attendType", Constants.AttendType.YDY_CANCEL_ATTEND);
                param.put("schedulingId", "-1");
                param.put("courseDate", attend.getAttend_date());
                param.put("businessDetailInfo", DetailBusinessInfoFormat.kuaYueZhiKongString(param));
                param.put("bizTypeName", "一对一");

                ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(
                        "erpv5.DXB_kuayue_zhikong", param);
                processEngine.getExecutionService().createVariables(pi.getId(), param, true);

                TAttendance attendance = new TAttendance();
                attendance.setId(attend.getId());
                attendance.setAudit_status(TAttendance.AuditStatusEnum.AUDITING.getValue());
                attendance.setUpdate_user(NumberUtils.object2Long(param.get("userId")));
                this.attendanceDao.updateAttendance(attendance);
            } else {
                do_kuayue_zhikong_ydy(attend, param);
            }
        } else {
            do_kuayue_zhikong_ydy(attend, param);
        }
    }
    
    @Override
    public void do_kuayue_zhikong_ydy(TAttendance attend, Map<String, Object> param) throws Exception {
     if(attend.getAudit_status()!=null){
        if (TAttendance.AuditStatusEnum.REJECT.getValue().intValue() == attend.getAudit_status()) {
            attend.setAttend_type(Constants.AttendType.YDY_NORMAL_ATTEND);
            this.attendanceDao.updateAttendance(attend);
            return;
        }
         }

        TAttendance oldAttend = this.attendanceDao.queryAttendanceInfoById(attend.getId());
        param.put("lastAttendType", oldAttend.getAttend_type());
        Long attendType = attend.getAttend_type();
        // 考勤置空
        attend.setAttend_type(Constants.AttendType.YDY_SET_NULL);
        attend.setOrder_course_id(oldAttend.getOrder_course_id());
        attend.setStart_time(oldAttend.getStart_time());
        attend.setEnd_time(oldAttend.getEnd_time());
        studentAttendanceService.ydyAttend(attend, param); // 考勤置空
        // 排课取消
        attend.setAttend_type(attendType);
        param.put("lastAttendType", Constants.AttendType.YDY_SET_NULL); // 设置上次考勤状态
        studentAttendanceService.ydyAttend(attend, param);
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
    public void updateAttendance(TAttendance attendance) throws Exception {
        this.attendanceDao.updateAttendance(attendance);
        this.attendanceDao.updateAttendanceHt(attendance);
    }

    @Override
    public List<TAttendance> queryYDYConflictScheduleList(TAttendance attendance) throws Exception {
        return this.attendanceDao.queryYDYConflictScheduleList(attendance);
    }

    @Override
    public List<TAttendance> queryPrintAttendanceInfo(String ids) throws Exception {
        List<TAttendance> attendances = attendanceDao.queryPrintAttendanceInfo(ids);
        return attendances;
    }
}
