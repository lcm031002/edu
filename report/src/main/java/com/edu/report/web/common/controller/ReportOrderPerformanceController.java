package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.cas.client.common.model.Account;
import com.edu.report.model.TBusinessStatistics;
import com.edu.report.web.common.service.UserPermissionService;
import com.edu.report.model.TBusinessStatistics;
import com.edu.report.model.TOrderPerformance;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportOrderPerformanceService;
import com.edu.report.web.common.service.UserPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TOrderPerformance;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportOrderPerformanceService;

/**
 * @ClassName: ReportAccountRechargeCashController
 * @Description: 出纳信息表控制类
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:44:41
 *
 */
@Controller
@RequestMapping("/common")
public class ReportOrderPerformanceController {
	
	private static final Logger log = Logger.getLogger(ReportOrderPerformanceController.class);

	@Resource(name = "reportOrderPerformanceService")
	private ReportOrderPerformanceService reportOrderPerformanceService;

	@Autowired
	private UserPermissionService userPermissionService;
	
	@RequestMapping(value = { "/order/performance" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");

			String bu_id = (String)queryParam.get("bu_id");
			if(StringUtils.isBlank(bu_id)) {
				throw new Exception("请选择团队！");
			}
			if(null == queryParam.get("branch_id") || StringUtils.isEmpty(queryParam.get("branch_id").toString()) || queryParam.get("branch_id").equals(-1) ) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
				queryParam.put("validBranchIds", validBranchIds);
			}

			List<TOrderPerformance> queryResult = reportOrderPerformanceService.queryReport(queryParam);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/order/performance/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "orderPerformace.xlsx";
		// 临时文件名
		String tempFileName = "课程顾问绩效表" + "_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");

			String bu_id = (String)queryParam.get("bu_id");
			if(StringUtils.isBlank(bu_id)) {
				throw new Exception("请选择团队！");
			}
			if(null == queryParam.get("branch_id") || StringUtils.isEmpty(queryParam.get("branch_id").toString()) || queryParam.get("branch_id").equals(-1) ) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
				queryParam.put("validBranchIds", validBranchIds);
			}

			List<TOrderPerformance> queryResult = reportOrderPerformanceService.queryReport(queryParam);

			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}
			
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

	@RequestMapping(value = { "/order/statistics" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryStatisticsReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");

			String bu_id = (String)queryParam.get("bu_id");
			if(StringUtils.isBlank(bu_id)) {
				throw new Exception("请选择团队！");
			}
			if(null == queryParam.get("branch_id") || StringUtils.isEmpty(queryParam.get("branch_id").toString()) || queryParam.get("branch_id").equals(-1) ) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
				queryParam.put("validBranchIds", validBranchIds);
			}

			List<TBusinessStatistics> queryResult = reportOrderPerformanceService.queryStatisticsReport(queryParam);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/order/statistics/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputStatisticsReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "businessStatistics.xlsx";
		// 临时文件名
		String tempFileName = "培英班运营统计总表" + "_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");

			String bu_id = (String)queryParam.get("bu_id");
			if(StringUtils.isBlank(bu_id)) {
				throw new Exception("请选择团队！");
			}
			if(null == queryParam.get("branch_id") || StringUtils.isEmpty(queryParam.get("branch_id").toString()) || queryParam.get("branch_id").equals(-1) ) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
				queryParam.put("validBranchIds", validBranchIds);
			}

			List<TBusinessStatistics> queryResult = reportOrderPerformanceService.queryStatisticsReport(queryParam);

			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}

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
