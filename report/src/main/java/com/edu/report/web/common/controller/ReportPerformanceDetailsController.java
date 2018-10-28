package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TPerformanceDetails;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.UserPermissionService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TPerformanceDetails;
import com.edu.report.util.ExcelMergePerformanceDetailStrategy;
import com.edu.report.util.ExcelMergeStrategy;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.UserPermissionService;
import com.edu.report.web.service.PerformanceDetailsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ReportPerformanceDetailsController
 * @Description: 业绩明细报表控制类
 *
 */
@Controller
@RequestMapping("/common")
public class ReportPerformanceDetailsController {
    @Resource(name = "performanceDetailsService")
    private PerformanceDetailsService performanceDetailsService;
    
    @Autowired
    private UserPermissionService userPermissionService;

    @RequestMapping(value = { "/performanceDetails/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            List<TPerformanceDetails> performanceDetails = this.performanceDetailsService.selectForList(paramMap);
            resultMap.put("error", false);
            resultMap.put("data", performanceDetails);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/performanceDetails/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> exportToExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
            List<TPerformanceDetails> performanceDetails = this.performanceDetailsService.selectForList(paramMap);
            if (CollectionUtils.isEmpty(performanceDetails)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "业绩明细表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(performanceDetails, rootPath, "performace.xlsx", tempFileName,1,
            		new ExcelMergePerformanceDetailStrategy("order_number",new Integer[]{8,9,10,11,12,13,14}));
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        
        return resultMap;
    }

    @RequestMapping(value = { "/performanceDetails/settlement/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> exportSettleToExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
            List<TPerformanceDetails> performanceDetails = this.performanceDetailsService.selectSettlementForList(paramMap);
            if (CollectionUtils.isEmpty(performanceDetails)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "业绩结算表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(performanceDetails, rootPath, "settlement_performace.xlsx", tempFileName,1,
                new ExcelMergePerformanceDetailStrategy("order_number",new Integer[]{11,12,13,14,15,16}));
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }
}
