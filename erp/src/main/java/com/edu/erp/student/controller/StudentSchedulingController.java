package com.edu.erp.student.controller;

import com.edu.common.annotation.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.model.DataSchool;
import com.edu.erp.util.ModelDataUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.model.TAttendance;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping(value = { "/studentservice" })
public class StudentSchedulingController extends BaseController {
	private static final Logger log = Logger
			.getLogger(StudentSchedulingController.class);

	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name = "dataGradeService")
	private DataGradeService dataGradeService;

	/**
	 * 查询个性化的排课信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@Token(save = true)
	@RequestMapping(value = { "/scheduling" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryYDYScheduling(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = getOrgModel(request);
		    Integer currentPage = genIntParameter("currentPage", request);
		    Long branch_id = genLongParameter("branch_id", request);
		    Long bu_id = genLongParameter("bu_id", request);
		    boolean isPageQuery = (currentPage != null);
			Map<String, Object> queryParam = initParamMap(request, isPageQuery, StringUtils.EMPTY);
			if(branch_id!=null){
				queryParam.put("branch_id", branch_id);
			}else{
				queryParam.put("branch_id", orgModel.getId());
			}
			if(bu_id!=null){
				queryParam.put("bu_id", bu_id);
			}
			
			Page<TAttendance> page = attendanceService.queryYDYScheduleCoursePage(queryParam);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 新增个性化的排课信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/scheduling" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addYDYScheduling(@RequestBody TAttendance tAttendance,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				log.error("未选择校区!");
				throw new Exception("请选择校区！");
			}
			ArrayList<TAttendance> attendanceList=attendanceService.addYDYScheduling(tAttendance,orgModel);

			resultMap.put("error", false);
			resultMap.put("message", "排课完成!");
			resultMap.put("data", attendanceList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 新增个性化的排课信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/schedulingList" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addYDYScheduling(@RequestBody List<Map<String,Object>> tmpAttendanceList,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				log.error("未选择校区!");
				throw new Exception("请选择校区！");
			}
			List<TAttendance> tAttendanceList=new ArrayList<TAttendance>();
			for(Map<String,Object> map:tmpAttendanceList){
				TAttendance tAttendance=ModelDataUtils.getPojoMatch(TAttendance.class, map);
				tAttendanceList.add(tAttendance);
			}
			ArrayList<TAttendance> attendanceList=attendanceService.addYDYSchedulingList(tAttendanceList,orgModel);

			resultMap.put("error", false);
			resultMap.put("data", attendanceList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 排课编辑修改
	 * @param tAttendance
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/scheduling" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateYDYScheduling(@RequestBody TAttendance tAttendance,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				log.error("未选择校区!");
				throw new Exception("请选择校区！");
			}
			attendanceService.updateYDYScheduling(tAttendance);

			resultMap.put("error", false);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 排课取消
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/scheduling" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delYDYScheduling(@RequestParam String attend_ids,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				log.error("未选择校区!");
				throw new Exception("请选择校区！");
			}
			attendanceService.cancelYDYScheduling(attend_ids);

			resultMap.put("error", false);
			resultMap.put("message", "排课取消成功!");
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 查询冲突排课列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/confict/scheduling" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> queryYDYScheduling(@RequestBody List<Map<String,Object>> tAttendanceList,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = initParamMap(request, false, StringUtils.EMPTY);
			Map<Long, Object> attendConflictMap = new HashMap<Long, Object>();
			for(Map tAttendanceMap:tAttendanceList){
				TAttendance tAttendance=ModelDataUtils.getPojoMatch(TAttendance.class, tAttendanceMap);
				List<TAttendance> attendanceList=attendanceService.queryYDYConflictScheduleList(tAttendance);
				if(!CollectionUtils.isEmpty(attendanceList)){
					attendConflictMap.put(tAttendance.getCourse_date(),attendanceList);
				}
			}
			resultMap.put("error", false);
			resultMap.put("data", attendConflictMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

}
