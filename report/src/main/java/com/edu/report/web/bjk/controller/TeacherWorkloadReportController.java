package com.edu.report.web.bjk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;
import com.edu.report.web.common.service.UserPermissionService;

/**
 * @ClassName: TeacherWorkloadReportController
 * @Description: 培英班教师工作量
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月17日 下午8:29:52
 *
 */
@Controller
@RequestMapping("/bjk")
public class TeacherWorkloadReportController{
	
	private static final Logger log = Logger.getLogger(TeacherWorkloadReportController.class);

	@Resource(name = "teacherWorkloadReportService")
	private TeacherWorkloadReportService teacherWorkloadReportService;
	

    @Autowired
    private UserPermissionService userPermissionService;

	@RequestMapping(value = { "/teacherworkload" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            String bu_id = (String)paramMap.get("bu_id");
            if(StringUtils.isBlank(bu_id)) {
            	 throw new Exception("请选择团队！");
            }
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			List<TTeacherWorkloadReport> queryResult = teacherWorkloadReportService.queryReport(paramMap);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/teacherworkload_output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "teacher_workload.xlsx";
		// 临时文件名
		String tempFileName = "培英班教师工作量表" + "【#bu_name#】_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            String bu_id = (String)paramMap.get("bu_id");
            if(StringUtils.isBlank(bu_id)) {
            	 throw new Exception("请选择团队！");
            }
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			List<TTeacherWorkloadReport> queryResult = teacherWorkloadReportService.queryReport(paramMap);

			// 有个合计行
			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}
			tempFileName = tempFileName.replace("#bu_name#", queryResult.get(0).getBu_name());

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath,
					templateFileName, tempFileName,1);

			log.debug("生成导出临时文件：" + outFilePath);

			resultMap.put("data", tempFileName);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}

		return resultMap;
	}

	@RequestMapping(value = { "/teacherworkload/order" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForOrderStudents(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TStudentInfo> queryResult = teacherWorkloadReportService.queryForOrderStudents(queryParam);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/teacherworkload/order/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputOrderStudents(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "teacher_workload_order.xlsx";
		// 临时文件名
		String tempFileName = "教师工作量表应到人数_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");

			List<TStudentInfo> queryResult = teacherWorkloadReportService.queryForOrderStudents(paramMap);

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath,
					templateFileName, tempFileName,1);

			log.debug("生成导出临时文件：" + outFilePath);

			resultMap.put("data", tempFileName);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}

		return resultMap;
	}

	@RequestMapping(value = { "/teacherworkload/attend" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForAttendanceStudents(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TStudentInfo> queryResult = teacherWorkloadReportService.queryForAttendanceStudents(queryParam);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/teacherworkload/attend/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputAttendStudents(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "teacher_workload_attend.xlsx";
		// 临时文件名
		String tempFileName = "教师工作量表实到人数_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");

			List<TStudentInfo> queryResult = teacherWorkloadReportService.queryForAttendanceStudents(paramMap);

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath,
					templateFileName, tempFileName,1);

			log.debug("生成导出临时文件：" + outFilePath);

			resultMap.put("data", tempFileName);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}

		return resultMap;
	}

}
