package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TBusinessAttendanceMonthReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TBusinessAttendanceMonthReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;

/**
 * @ClassName: BusinessAttendanceMonthController
 * @Description: 月度分类考勤表Controller
 *
 */
@Controller
@RequestMapping("/common")
public class BusinessAttendanceMonthController{
	
	private static final Logger log = Logger.getLogger(BusinessAttendanceMonthController.class);

	@Resource(name = "businessAttendanceMonthService")
	private BusinessAttendanceMonthService businessAttendanceMonthService;

	@RequestMapping(value = { "/monthbusinessattendance" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TBusinessAttendanceMonthReport> queryResult = businessAttendanceMonthService.queryReport(queryParam);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/monthbusinessattendance_output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "month_business_attendance.xlsx";
		// 临时文件名
		String tempFileName = "月度分类考勤表" + "【#bu_name#】_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TBusinessAttendanceMonthReport> queryResult = businessAttendanceMonthService.queryReport(queryParam);

			// 有个合计行
			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}
			tempFileName = tempFileName.replace("#bu_name#", queryResult.get(0).getBu_name());

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath,
					templateFileName, tempFileName, 1);

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
