package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TReportAccountFlow;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportAccountFlowService;
import com.edu.report.web.common.service.UserPermissionService;
import org.apache.commons.lang.StringUtils;
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
import com.edu.report.model.TReportAccountFlow;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.ReportAccountFlowService;
import com.edu.report.web.common.service.UserPermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/common")
public class ReportAccountFlowController {
    private static final int HEAD_ROW_NUM = 2;

    @Resource(name = "reportAccountFlowService")
    private ReportAccountFlowService reportAccountFlowService;

    @Autowired
    private UserPermissionService userPermissionService;
    
    @RequestMapping(value = { "/accountFlow" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            Page<TReportAccountFlow> page = this.reportAccountFlowService.selectForPage(paramMap);

            PageInfo<TReportAccountFlow> pageData = new PageInfo<TReportAccountFlow>(page.getResult());
            resultMap.put("error", false);
            resultMap.put("data", pageData.getList());
            resultMap.put("total", pageData.getTotal());
            resultMap.put("totalPage", pageData.getPages());
            resultMap.put("currentPage", pageData.getPageNum());
            resultMap.put("pageSize", pageData.getPageSize());
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/accountFlow/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(orgModel.getBuId()), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportAccountFlow> accountFlowList = this.reportAccountFlowService.selectForList(paramMap);
            resultMap.put("error", false);
            resultMap.put("data", accountFlowList);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/accountFlow/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> exportToExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
		// 模板文件名
		String templateFileName = "accountFlow.xlsx";
        try {
        	OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(orgModel.getBuId()), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
            paramMap.put("bu_id", orgModel.getBuId());
            List<TReportAccountFlow> accountFlowList = this.reportAccountFlowService.selectForList(paramMap);
            if (CollectionUtils.isEmpty(accountFlowList)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "账户流水表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
            ReportWriteExcelHandler.writeDataToExcelBySxssf(accountFlowList, rootPath,
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
