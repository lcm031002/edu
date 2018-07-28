package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportRenewalRateService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportRenewalRateService;

/**
 * @ClassName: ReportAccountRechargeCashController
 * @Description: 培英班续报率报表控制类
 *
 */
@Controller
@RequestMapping("/common")
public class ReportRenewalRateController {
    private static final int HEAD_ROW_NUM = 1;
	
	private static final Logger log = Logger.getLogger(ReportRenewalRateController.class);

	@Resource(name = "reportRenewalRateService")
	private ReportRenewalRateService reportRenewalRateService;
	
	@RequestMapping(value = { "/renewalRate/sum" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForSum(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TReportRenewalRateSum> queryResult = reportRenewalRateService.queryForSum(queryParam);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/sum/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputForSum(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
		// 模板文件名
		String templateFileName = "renewalRateSum.xlsx";
        
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportRenewalRateSum> queryResult = reportRenewalRateService.queryForSum(paramMap);
            if (CollectionUtils.isEmpty(queryResult)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "培英班续报率报表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath,
					templateFileName, tempFileName, 1);
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/lastbase" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForLastBase(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForLastBase(queryParam);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/lastbase/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputForLastBase(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
		// 模板文件名
		String templateFileName = "renewalRateLastBase.xlsx";
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForLastBase(paramMap);
            if (CollectionUtils.isEmpty(queryResult)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "培英班续报率报表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath, 
					templateFileName, tempFileName, 1);
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/estimate" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForEstimate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForEstimate(queryParam);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/estimate/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputForEstimate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
		// 模板文件名
		String templateFileName = "renewalRateEstimate.xlsx";
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForEstimate(paramMap);
            if (CollectionUtils.isEmpty(queryResult)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "培英班续报率报表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath, 
					templateFileName, tempFileName, 1);
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/actual" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryForActual(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForActual(queryParam);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/renewalRate/actual/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputForActual(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
		// 模板文件名
		String templateFileName = "renewalRateActual.xlsx";
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportRenewalRateDetail> queryResult = reportRenewalRateService.queryForActual(paramMap);
            if (CollectionUtils.isEmpty(queryResult)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "培英班续报率报表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath, 
					templateFileName, tempFileName, 1);
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
	}
	
}
