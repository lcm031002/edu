package com.edu.report.web.bjk.controller;

import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.util.BaseController;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.bjk.service.CourseAnalysisReportService;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.bjk.service.CourseAnalysisReportService;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/bjk")
public class CourseAnalysisReportController extends BaseController {
	
	private static final Logger log = Logger.getLogger(CourseAnalysisReportController.class);

	@Resource(name = "courseAnalysisReportService")
	private CourseAnalysisReportService courseAnalysisReportService;

	@RequestMapping(value = { "/courseAnalysisReport" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryCourseAnalysisReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, StringUtils.EMPTY);
			setPageInfo(request);
			Page<Map<String, Object>> page = this.courseAnalysisReportService.queryCourseAnalysisReport(paramMap);
			setRespDataForPage(request, page.getResult(), resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/courseAnalysisReport/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "course_analysis.xlsx";
		// 临时文件名
		String tempFileName = "班级分析报表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, StringUtils.EMPTY);
			Page<Map<String, Object>> page = this.courseAnalysisReportService.queryCourseAnalysisReport(paramMap);

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(page.getResult(), rootPath,
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
