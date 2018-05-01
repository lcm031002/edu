/**  
 * @Title: ReportPerformanceDetailsController.java
 * @Package com.edu.report.web.common.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:47:19
 * @version KLXX ERPV4.0  
 */
package com.edu.report.web.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TOnlineOrderReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.OnLineOrderService;
import com.edu.report.web.common.service.UserPermissionService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.edu.report.model.TOnlineOrderReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.OnLineOrderService;
import com.edu.report.web.common.service.UserPermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 在线支付明细报表
 * @author yecb
 *
 */
@Controller
@RequestMapping("/common")
public class OnLineOrderReportController {
	
	private static final Logger log = Logger.getLogger(OnLineOrderReportController.class);
	
    @Resource(name = "onLineOrderService")
    private OnLineOrderService onLineOrderService;
    
    @Autowired
    private UserPermissionService userPermissionService;
    
    @RequestMapping(value = { "/onLineOrder" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            Page<TOnlineOrderReport> page = this.onLineOrderService.selectForPage(paramMap);

            PageInfo<TOnlineOrderReport> pageData = new PageInfo<TOnlineOrderReport>(page.getResult());
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

    @RequestMapping(value = { "/onLineOrder/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForList(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            List<TOnlineOrderReport> performanceDetails = this.onLineOrderService.selectForList(paramMap);
            resultMap.put("error", false);
            resultMap.put("data", performanceDetails);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/onLineOrder/output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> exportToExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }
            // 模板文件名
    		String templateFileName = "onLineOrder.xlsx";
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
            List<TOnlineOrderReport> performanceDetails = this.onLineOrderService.selectForList(paramMap);
            if (CollectionUtils.isEmpty(performanceDetails)) {
                throw new Exception("没有可导出的数据");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            String tempFileName = "电商支付明细表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            // 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(performanceDetails, rootPath,
					templateFileName, tempFileName, 1);
			log.debug("生成导出临时文件：" + outFilePath);
            resultMap.put("data", tempFileName);
            resultMap.put("error", false);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }
}
