/**  
 * @Title: AttendanceController.java
 * @Package com.ebusiness.erp.attendance.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月18日 下午3:45:34
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.attendance.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.model.TAttendance;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.erp.attendance.business.AttendanceBusiness;
import com.edu.erp.attendance.business.RequestParam;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ReportWriteExcelHandler;
import com.github.pagehelper.Page;

import net.sf.json.JSONObject;

/**
 * @ClassName: AttendanceController
 * @Description: 考勤服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月18日 下午3:45:34
 * 
 */
@Controller
@RequestMapping(value = { "/attendance" })
public class AttendanceController extends BaseController {

	private static final Logger log = Logger.getLogger(AttendanceController.class);

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name = "studentAttendanceService")
	private StudentAttendanceService studentAttendanceService;

	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@ResponseBody
	@RequestMapping(value = "/courseTimes", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> queryAttendanceCourseTimes(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Long businessType = genLongParameter("businessType", request);
			Long courseId = genLongParameter("courseId", request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel.getId() == null || !"4".equals(orgModel.getType())) {
				throw new Exception("请选择校区！");
			}

			if (businessType == null) {
				businessType = 1L;
			}
			if (businessType.intValue() == 1) {
				List<Map<String, Object>> courseTimesAttendance = courseService.queryCourseTimesAttendance(courseId);
				resMap.put("data", courseTimesAttendance);
			}

			resMap.put("error", false);

		} catch (Exception e) {
			resMap.put("error", true);
			resMap.put("message", e.getMessage());
			e.printStackTrace();
			log.error("查询失败，see:", e);
		}
		return resMap;
	}

	@ResponseBody
	@RequestMapping(value = "/students", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> queryAttendanceCourseTimesStudents(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			Long businessType = genLongParameter("businessType", request);
			Long courseId = genLongParameter("courseId", request);
			Long courseTime = genLongParameter("courseTime", request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel.getId() == null || !"4".equals(orgModel.getType())) {
				throw new Exception("请选择校区！");
			}
			if (courseId == null || courseTime == null) {
				throw new Exception("没有要考勤的课程或课次！");
			}

			if (businessType == null) {
				businessType = 1L;
			}
			if (businessType.intValue() == 1) {
				List<Map<String, Object>> courseTimesAttendanceStudents = courseSchedulingService
						.queryCourseSchedulingStudents(courseId, courseTime);
				resMap.put("data", courseTimesAttendanceStudents);
			}

			resMap.put("error", false);

		} catch (Exception e) {
			resMap.put("error", true);
			resMap.put("message", e.getMessage());
			e.printStackTrace();
			log.error("查询失败，see:", e);
		}
		return resMap;
	}
	/**
	 * 班级课考勤导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/courseTimes_attend_bjk/output" }, method = RequestMethod.GET, headers = {"Accept=application/json" })
	@ResponseBody
	public Map<String, Object> exportToExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			Map<String, Object> resMap = new HashMap<String, Object>();
			try {
				Long businessType = genLongParameter("businessType", request);
				Long courseId = genLongParameter("courseId", request);
				Long courseTime = genLongParameter("courseTime", request);
				OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
				if (orgModel.getId() == null || !"4".equals(orgModel.getType())) {
					throw new Exception("请选择校区！");
				}
				if (courseId == null || courseTime == null) {
					throw new Exception("没有要考勤的课程或课次！");
				}

				if (businessType == null) {
					businessType = 1L;
				}
				List<Map<String, Object>> courseTimesAttendanceStudents = null;
				if (businessType.intValue() == 1) {
					courseTimesAttendanceStudents = courseSchedulingService.queryCourseSchedulingStudents(courseId, courseTime);
					resMap.put("data", courseTimesAttendanceStudents);
				}
				if (CollectionUtils.isEmpty(courseTimesAttendanceStudents)) {
					throw new Exception("没有可导出的数据");
				}
			
				String rootPath = request.getSession().getServletContext().getRealPath("//");
				String tempFileName = "班级课点名表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
				// 导出文件目录
				ReportWriteExcelHandler.writeDataToExcel(courseTimesAttendanceStudents, rootPath, "bjk_attend_course_times.xlsx", tempFileName);
				resMap.put("data", tempFileName);
				resMap.put("error", false);
			} catch (Exception e) {
				resMap.put("error", true);
				resMap.put("message", e.getMessage());
			}
			return resMap;
	}

	/**
	 * 学生批量考勤
	 * 
	 * @param requestParam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/students", headers = "Accept=application/json")
	public Map<String, Object> attandanceSubmitList(@RequestBody RequestParam requestParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}

			if (requestParam == null || CollectionUtils.isEmpty(requestParam.getSubmitAttendanceList())) {
				log.error("没有要考勤的学员！");
				throw new Exception("没有要考勤的学员！");
			}

			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (AttendanceBusiness attendanceBusiness : requestParam.getSubmitAttendanceList()) {
				Map<String, Object> attendanceObj = new HashMap<String, Object>();
				attendanceObj.put("attendType", attendanceBusiness.getAttendType());
				attendanceObj.put("business_type", attendanceBusiness.getBusiness_type());
				attendanceObj.put("schedulingId", attendanceBusiness.getSchedulingId());
				attendanceObj.put("attendanceId", attendanceBusiness.getAttendanceId());
				attendanceObj.put("studentId", attendanceBusiness.getStudentId());
				attendanceObj.put("studentName", attendanceBusiness.getStudentName());
				attendanceObj.put("lock_status", attendanceBusiness.getLock_status());
				attendanceObj.put("userId", account.getId());
				attendanceObj.put("remark", attendanceBusiness.getRemark());
				attendanceObj.put("teacherId", attendanceBusiness.getTeacherId());
				attendanceObj.put("courseDate", DateUtil.stringToDate(attendanceBusiness.getCourseDate(), "yyyyMMdd"));
				attendanceObj.put("order_encoding", attendanceBusiness.getOrder_encoding());
				attendanceObj.put("branchId", orgModel.getId());
				attendanceObj.put("cityId", orgModel.getCityId());
				attendanceObj.put("userName", account.getEmployeeName());
                attendanceObj.put("productLine",orgModel.getProductLine());

				listMap.add(attendanceObj);
			}
			attendanceService.attandanceBatchSubmit(listMap, processEngine);

			result.put("error", false);
			result.put("message", "考勤成功！请刷新页面查询考勤结果.");

		} catch (Exception e) {
			log.error("系统异常:考勤记录更新失败." + e.getMessage());
			result.put("error", true);
			result.put("message", "系统异常:考勤记录更新失败." + e.getMessage());
			return result;
		}
		return result;
	}
	
	  /**
     * 教务平台-考勤管理-晚辅导考勤
     * 
     * @param request
     * @param response
     * @return 晚辅导每日考勤情况
     */
    @ResponseBody
    @RequestMapping(value = "/branchinfo", method = RequestMethod.GET, headers = "Accept=application/json")
    public Map<String, Object> queryPageForWfd(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.RespMapKey.ERROR, false);
        try {
           
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || (!"4".equals(orgModel.getType()))) {
                log.error("没有选择校区！");
                throw new Exception("请选择校区!");
            }
            Map<String, Object> paramMap = getParamMap(request);
            paramMap.put("branch_id", orgModel.getId());
            setPageInfo(request);
            Page<Map<String, Object>> page = this.attendanceService.queryPageForWfj(paramMap);
            setRespDataForPage(request, page, resultMap);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

	/**
	 * 教务平台-考勤管理-晚辅导考勤明细 学生考勤
	 * 
	 * @param request
	 * @param response
	 * @return 待考勤学生列表
	 */
	@ResponseBody
	@RequestMapping(value = "/students/wfd_attn_list", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> queryStudentsForWfdAttn(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			Map<String, Object> paramMap = initParamMap(request, false);
			paramMap.put("branch_id", orgModel.getId());
			List<Map<String, Object>> studentList = this.attendanceService.queryStudentsForWfdAttn(paramMap);
			resultMap.put(Constants.RespMapKey.DATA, studentList);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 教务平台-考勤管理-晚辅导考勤明细 教师考勤
	 * 
	 * @param request
	 * @param response
	 * @return 待考勤教师列表
	 */
	@ResponseBody
	@RequestMapping(value = "/teachers/wfd_attn_list", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> queryTeachersForWfdAttn(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			Map<String, Object> paramMap = initParamMap(request, false);
			paramMap.put("branch_id", orgModel.getId());
			List<Map<String, Object>> teacherList = this.attendanceService.queryTeachersForWfdAttn(paramMap);
			resultMap.put(Constants.RespMapKey.DATA, teacherList);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 教务平台-考勤管理-晚辅导考勤查询教师组列表
	 * 
	 * @param request
	 * @param response
	 * @return 待考勤教师列表
	 */
	@ResponseBody
	@RequestMapping(value = "/teachers/wfd_attn_group_list", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> queryTeacherLabelsPage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			Map<String, Object> paramMap = initParamMap(request, false);
			paramMap.put("branch_id", orgModel.getId());
			List<Map<String, Object>> teacherGroupList = this.attendanceService.queryTeacherLabelsPage(paramMap);
			resultMap.put(Constants.RespMapKey.DATA, teacherGroupList);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}

	/***
	 * 晚辅导教师考勤
	 * 
	 * @param {teachers:[{"teacher_id":"教师ID","attend_type":"考勤状态","attend_id":""
	 *            }],"attend_date":"上课日期","remark":"备注"}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/teachers", headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> teacherAttend(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", false);
		try {
			Long userId = WebContextUtils.genUser(request).getId();

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			studentAttendanceService.teacherAttend(json, userId, orgModel.getId());

		} catch (Exception e) {
			result.put("error", true);
			result.put("message", "系统异常:考勤记录更新失败." + e.getMessage());
			return result;
		}
		return result;
	}

	/***
	 * 晚辅导学员考勤
	 * 
	 * @param {"students":[{"order_course_id":"","scheduling_id":"","attend_id":""
	 *            ,"student_id":"","attend_type":""}],"course_date":"","p_remark":""
	 *            }
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/service/wfd", headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> wfdAttend(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			JSONObject jsonObj = JSONObject.fromObject(json);
			attendanceService.wfdAttandanceBatchSubmit(jsonObj, processEngine,request);

			result.put("error", false);
			result.put("message", "考勤成功！请刷新页面查询考勤结果.");

		} catch (Exception e) {
			log.error("系统异常:考勤记录更新失败." + e.getMessage());
			result.put("error", true);
			result.put("message", "系统异常:考勤记录更新失败." + e.getMessage());
			return result;
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/service", headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateAttendance(@RequestBody TAttendance attendance, HttpServletRequest request,
															  HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", false);
		try {
			this.setDefaultValue(request, attendance, true);
			attendanceService.updateAttendance(attendance);
			result.put("error", false);
			result.put("message", "修改成功");
		} catch (Exception e) {
			log.error("系统异常:考勤记录更新失败." + e.getMessage());
			result.put("error", true);
			result.put("message", "系统异常:考勤记录更新失败." + e.getMessage());
			return result;
		}
		return result;
	}


	/**
	 * 
	 * @Title: querySchedulingMakeupList
	 * @Description: 查询课程预约补课信息<该功能已经移动到MakeupController中>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	// @ResponseBody
	// @RequestMapping(value = { "/makeup" }, method = RequestMethod.GET,
	// headers = { "Accept=application/json" })
	// public Map<String, Object> querySchedulingMakeupList(
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	//
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// try {
	// Long courseId = genLongParameter("courseId", request);
	// Long courseTime = genLongParameter("courseTime", request);
	// String queryInfo = request.getParameter("queryInfo");
	// if (courseId == null || courseTime == null) {
	// log.error("error found ,see:要查询的课程为空！");
	// throw new Exception("访问非法！");
	// }
	//
	// List<Map<String, Object>> courseSchedulingList = courseSchedulingService
	// .querySchedulingAttendanceMakeup(courseId, courseTime,
	// queryInfo);
	//
	// resultMap.put("data", courseSchedulingList);
	// resultMap.put("error", false);
	// } catch (Exception e) {
	// log.error("error found ,see:", e);
	// resultMap.put("error", true);
	// resultMap.put("message", e.getMessage());
	// }
	// return resultMap;
	// }

}
