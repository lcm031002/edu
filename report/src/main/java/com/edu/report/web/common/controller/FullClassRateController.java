package com.edu.report.web.common.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.util.ExcelMergeStrategy;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.TFullClassRateService;
import com.edu.report.web.common.service.UserPermissionService;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.TFullClassRateService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FullClassRateController
 * @Description: 满班率报表控制类
 * @author  linj@klxuexi.org
 * @date 2017年10月17日 下午16:42:36
 *
 */
@Controller
@RequestMapping("/common")
public class FullClassRateController {
	
	private static final Logger log = Logger.getLogger(FullClassRateController.class);

	@Resource(name = "tFullClassRateService")
	private TFullClassRateService tFullClassRateService;
	
	@Autowired
    private UserPermissionService userPermissionService;
	
	@RequestMapping(value = { "/fullClass/rate" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
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
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString())) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			
			List<TFullClassRate> queryResult = tFullClassRateService.queryReport(paramMap);
			
			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/fullClass/rate/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "fullClassRate.xlsx";
		// 临时文件名
		String tempFileName = "培英精品班满班率报表" + "【#bu_name#】_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            String bu_id = (String)paramMap.get("bu_id");
            if(StringUtils.isBlank(bu_id)) {
            	 throw new Exception("请选择团队！");
            }
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString())) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			List<TFullClassRate> queryResult = tFullClassRateService.queryReport(paramMap);

			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}
			tempFileName = tempFileName.replace("#bu_name#", queryResult.get(0).getBu_name());

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath, templateFileName, tempFileName,1,
					new ExcelMergeStrategy("opt_encoding",new Integer[]{9,10,11,12,13}));
			log.debug("生成导出临时文件：" + outFilePath);

			resultMap.put("data", tempFileName);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}

		return resultMap;
	}
	@RequestMapping(value = { "/fullClass/last" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			paramMap.put("bu_id", orgModel.getBuId());
			if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString())) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(orgModel.getBuId()), account.getId());
				paramMap.put("validBranchIds", validBranchIds);
			}
			List<TFULLClassRateLast> queryResult = tFullClassRateService.queryDetail(paramMap);

			resultMap.put("data", queryResult);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/fullClass/last/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> exportDetailExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "fullClassRateLast.xlsx";
		// 临时文件名
		String tempFileName = "培英精品班满班率报表" + "【#bu_name#】_"
				+ DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			paramMap.put("bu_id", orgModel.getBuId());
			if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString())) {
				Account account = WebContextUtils.genUser(request);
				String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(orgModel.getBuId()), account.getId());
				paramMap.put("validBranchIds", validBranchIds);
			}
			List<TFULLClassRateLast> queryResult = tFullClassRateService.queryDetail(paramMap);

			if(CollectionUtils.isEmpty(queryResult) || queryResult.size() < 2) {
				throw new Exception("没有数据可导出！");
			}
			tempFileName = tempFileName.replace("#bu_name#", queryResult.get(0).getBu_name());

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(queryResult, rootPath, templateFileName, tempFileName,1,
					new ExcelMergeStrategy("opt_encoding",new Integer[]{9,10,11,12,13}));
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
