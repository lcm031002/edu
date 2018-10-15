package com.edu.erp.student.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.annotation.Token;
import com.edu.common.constants.Constants;
import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.erp.attendance.business.Attendance;
import com.edu.erp.attendance.business.EnumAttendType;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.dao.TLockDao;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.message.service.MessageRequirementService;
import com.edu.erp.model.TAttendance;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.BaseController;
import com.edu.erp.workflow.service.UserTaskService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/attendanceservice" })
public class StudentAttendanceController extends BaseController {
    private static final Logger log = Logger.getLogger(StudentAttendanceController.class);

    @Resource(name = "studentAttendanceService")
    private StudentAttendanceService studentAttendanceService;

    @Resource(name = "attendanceService")
    private AttendanceService attendanceService;

    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    @Resource(name = "studentInfoService")
    private StudentInfoService studentInfoService;

    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    @Resource(name = "userTaskService")
    private UserTaskService userTaskService;

    @Resource(name = "messageRequirementService")
    private MessageRequirementService messageRequirementService;

    @RequestMapping(method = RequestMethod.POST, value = "/bjk", headers = "Accept=application/json")
    public @ResponseBody
    Map<String, Object> attandanceSubmit(@RequestBody Map<String, Object> params,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Long attendType = NumberUtils.object2Long(params.get("attendType"));
        if (log.isDebugEnabled()) {
            log.debug("begin to attandanceSubmit ,attendType is " + attendType);
        }
        try {
            Account account = WebContextUtils.genUser(request);

            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || (!"4".equals(orgModel.getType()))) {
                log.error("branchId is null");
                result.put("error", true);
                result.put("message", "请选择校区!");
                return result;
            }
            Long schedulingId = NumberUtils.object2Long(params.get("schedulingId"));
            if (schedulingId == null) {
                log.error("invalid request!");
                result.put("error", true);
                result.put("message", "非法访问！");
                return result;
            }

            String lock_status = null != params.get("lock_status") ? params.get("lock_status") + "" : null;
            if (StringUtils.isNotBlank(lock_status) && "1".equals(lock_status)) {
                log.error("invalid request!");
                result.put("error", true);
                result.put("message", "课程订单已被冻结，考勤需要先解冻订单！");
                return result;
            }

            String courseDate = params.get("courseDate") + "";
            Long studentId = NumberUtils.object2Long(params.get("studentId"));
            Long attendanceId = NumberUtils.object2Long(params.get("attendance_id"));

            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            Map<String, Object> attendanceObj = new HashMap<String, Object>();
            attendanceObj.put("attendType", attendType);
            attendanceObj.put("schedulingId", schedulingId);
            attendanceObj.put("attendanceId", attendanceId);
            attendanceObj.put("studentId", studentId);
            attendanceObj.put("lock_status", lock_status);
            attendanceObj.put("userId", account.getId());
            attendanceObj.put("remark", params.get("remark"));
            attendanceObj.put("teacherId", NumberUtils.object2Long(params.get("teacherId")));
            attendanceObj.put("courseDate", DateUtil.stringToDate(courseDate, "yyyy-MM-dd"));
            attendanceObj.put("order_encoding", params.get("order_encoding"));
            attendanceObj.put("branchId", orgModel.getId());
            attendanceObj.put("cityId", orgModel.getCityId());
            attendanceObj.put("userName", account.getEmployeeName());
            attendanceObj.put("studentName", params.get("studentName"));
            attendanceObj.put("productLine",orgModel.getProductLine());

            listMap.add(attendanceObj);

            attendanceService.attandanceBatchSubmit(listMap, processEngine);

            List<Attendance> attendanceList = new ArrayList<>();
            for (Map<String, Object> attendance : listMap) {
                TAttendance attendance1 = studentAttendanceService.queryValidAttendanceId(
                        (Long)attendance.get("studentId"),
                        (Long)attendance.get("schedulingId")
                );
                if(null != attendance1) {
                    attendanceList.add(new Attendance(attendance1));
                }else {
                    log.error("找不到考勤记录,排课：" + (Long)attendance.get("schedulingId") +  " 学员：" + (Long)attendance.get("studentId"));
                }
            }

            StringBuilder detailInfoStr = new StringBuilder();
            detailInfoStr.append("学生ID:");
            detailInfoStr.append(studentId);
            detailInfoStr.append("，");
            detailInfoStr.append("排课单ID:");
            detailInfoStr.append(schedulingId);
            detailInfoStr.append("，");
            detailInfoStr.append("考勤ID:");
            detailInfoStr.append(attendanceId);
            detailInfoStr.append("，");
            detailInfoStr.append("考勤类别:");
            detailInfoStr.append(attendType);

            LogOperateUtil.getInstance().LogOperate("个人班级课考勤", detailInfoStr.toString(),
                    LogOperateUtil.getInstance().genUserInfo(request), "成功");
        } catch (Exception e) {
            log.error("系统异常:考勤记录更新失败." + e.getMessage());
            StringBuilder detailInfoStr = new StringBuilder();
            detailInfoStr.append("考勤参数:");
            detailInfoStr.append(params.toString());
            detailInfoStr.append("，");
            detailInfoStr.append("考勤类别:");
            detailInfoStr.append(attendType);
            detailInfoStr.append("，");
            detailInfoStr.append("失败原因:");
            detailInfoStr.append(e.getMessage());

            LogOperateUtil.getInstance().LogOperate("个人班级课考勤", detailInfoStr.toString(),
                    LogOperateUtil.getInstance().genUserInfo(request), "失败");
            result.put("error", true);
            result.put("message", "系统异常:考勤记录更新失败." + e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 查询课次考勤记录
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bjk/details", method = RequestMethod.GET, headers = "Accept=application/json")
    public Map<String, Object> queryAttendCourseTimesDetails(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("student_id", genLongParameter("student_id", request));
            paramMap.put("scheduling_id", genLongParameter("scheduling_id", request));
            List<Map<String, Object>> result = studentAttendanceService.queryAttendCourseTimesDetails(paramMap);
            resMap.put("error", false);
            resMap.put("data", result);
        } catch (Exception e) {
            resMap.put("error", true);
            resMap.put("message", e.getMessage());
            e.printStackTrace();
            log.error("查询失败，see:", e);
        }
        return resMap;
    }

    @RequestMapping(value = "/bjk/makeup", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    Map<String, Object> checkVideoUploadStatus(HttpServletRequest request,
                                               HttpServletResponse response) {
        Map<String, Object> resp = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("scheduling_id", genLongParameter("scheduling_id", request));
        try {
            List<Map<String, Object>> data = studentAttendanceService.checkVideoUploadStatus(param);
            resp.put("data", data);
            resp.put("error", false);
        } catch (Exception e) {
            e.printStackTrace();
            resp.put("error", true);
            resp.put("message", e.getMessage());
            log.error("查询失败，see:", e);
        }
        return resp;
    }

    @RequestMapping(value = "/bjk/makeup", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Map<String, Object> checkSaveExtralesson(@RequestBody Map<String, Object> params,
                                             HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resp = new HashMap<>();
        try {
            List<Map<String, Object>> check = studentAttendanceService.checkSaveExtralesson(params);
            if (CollectionUtils.isEmpty(check)) {
                Account account = WebContextUtils.genUser(request);
                params.put("create_user", account.getId());

                String activation_code = studentAttendanceService.saveExtralesson(params);
                if (StringUtils.isNotEmpty(activation_code)) {
                    try {
                        // 发送短信通知
                        String msg_content = "您已经预约完成" + params.get("valid_start_date") + "到"
                                + params.get("valid_end_date") + "之间的补课一次,预约码为：" + activation_code;
                        messageRequirementService.sendMessage(msg_content, params.get("phone").toString(), null);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("error found,see:", e);
                        resp.put("error", true);
                        resp.put("message", "发送短信失败！see:" + e.getMessage());
                    }
                    resp.put("activation_code", activation_code);
                    resp.put("error", false);
                } else {
                    resp.put("error", true);
                    resp.put("message", "保存预约信息失败！");
                }

            } else {
                resp.put("message", "该课程已存在补课预约，预约截止时间为:" + check.get(0).get("VALID_END_DATE"));
                resp.put("error", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.put("message", e.getMessage());
            resp.put("error", true);
            log.error("预约失败，see:", e);
        }
        return resp;
    }

    /**
     * 学员主页-学员考勤-晚辅导 学员考勤明细查询
     *
     * @param request
     * @param response
     * @return 学员考勤明细
     */
    @ResponseBody
    @RequestMapping(value = "/wfd", method = RequestMethod.GET, headers = "Accept=application/json")
    public Map<String, Object> queryWfdDetails(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, false);
            List<Map<String, Object>> wfdDetails = this.studentAttendanceService.queryWfdDetails(paramMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, wfdDetails);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 学员主页-学员考勤-晚辅导 学员考勤
     *
     * @param request
     * @param response
     * @return 考勤结果
     */
    @ResponseBody
    @RequestMapping(value = "/wfd", method = RequestMethod.POST, headers = "Accept=application/json")
    public Map<String, Object> wfdAttend(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
                                         HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (CollectionUtils.isEmpty(paramMap)) {
                throw new Exception("考勤参数不能为空！");
            }

            if (paramMap.get("attendanceId") != null) {
                JSONObject josnObject = JSONObject.fromObject(paramMap);
                this.attendanceService.wfdAttandanceBatchSubmit(josnObject, processEngine, null);
            } else {
                OrgModel orgModel = getOrgModel(request);
                Account account = WebContextUtils.genUser(request);
                JSONObject jsonObject = JSONObject.fromObject(paramMap);
                String strJson = jsonObject.toString();
                strJson = "{students : [" + strJson + "]}";
                resultMap = this.studentAttendanceService.wfdAttend(strJson, account.getId(), orgModel.getId());
            }
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 一对一考勤
     *
     * @param request
     * @param response
     * @return 考勤结果
     */
    @ResponseBody
    @Token(remove = true)
    @RequestMapping(value = "/ydy", method = RequestMethod.POST, headers = "Accept=application/json")
    public Map<String, Object> ydyAttend(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                         HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = getOrgModel(request);
            Account account = WebContextUtils.genUser(request);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userId", account.getId());
            paramMap.put("userName", account.getUserName());
            paramMap.put("branchId", orgModel.getId());
            paramMap.put("cityId", orgModel.getCityId());
            JSONArray jsonArray = jsonObject.getJSONArray("attendList");
            Collection<TAttendance> attendList = JSONArray.toCollection(jsonArray, TAttendance.class);
            JSONArray p = new JSONArray();
            for (TAttendance attendance : attendList) {
                com.alibaba.fastjson.JSONObject var = new com.alibaba.fastjson.JSONObject();
                var.put("attendId",attendance.getId());
                var.put("attendType", EnumAttendType.of(attendance.getAttend_type()));
                var.put("subAttendType",EnumAttendType.of(attendance.getSub_attend_type()));
                var.put("remark",attendance.getRemark());
                var.put("attendUserId",account.getId());
                p.add(var);
            }
            //OpenApiUtils.invokePost(Constants.TaService.ATTENDANCE_SERVER_KEY,"/attendance/ydyBatchAttendAndCheck", p);
            // 原先逻辑
            this.attendanceService.ydyAttendBatchSubmit(attendList, paramMap, processEngine);

            List<Attendance> attendanceList = new ArrayList<>();
            for (TAttendance attendance : attendList) {
                TAttendance attendance1 = attendanceService.queryById(attendance.getId());
                if(null != attendance1) {
                    attendanceList.add(new Attendance(attendance));
                } else {
                    log.error("找不到考勤记录：" + attendance1.getId());
                }
            }
            String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(attendanceList);
            //topicMessageSender.send(PropertiesTools.getPropertiesByKey("mns.topic.active.ydyBatchAttendAndCheck"),jsonString);

            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }
    /**
     * 查询考勤信息
     *
     * @param request
     * @param response
     * @return 考勤信息
     * @throws
     */

    @ResponseBody
    @RequestMapping(value = {"/batchPrint"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> printAttendanceInfo(HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = getOrgModel(request);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String ids=request.getParameter("attendanceListIds");
            List<TAttendance> attendance=attendanceService.queryPrintAttendanceInfo(ids);
            resultMap.put("data", attendance);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
}