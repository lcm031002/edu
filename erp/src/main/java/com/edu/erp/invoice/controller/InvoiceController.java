/**  
 * @Title: InvoiceController.java
 * @Package com.ebusiness.erp.orders.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月6日 下午1:21:28
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.invoice.controller;

import com.edu.common.constants.Constants.RespMapKey;
import com.edu.erp.model.TabInvoiceReceiveLog;
import com.edu.erp.model.TabInvoiceReceiveLog.StatusEnum;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.model.TabDataInvoice;
import com.edu.common.constants.Constants;
import com.edu.erp.invoice.service.InvoiceService;
import com.edu.erp.util.BaseController;
import com.edu.common.util.ERPConstants;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.util.ExcelWriteHandler;
import com.github.pagehelper.Page;

/**
 * @ClassName: InvoiceController
 * @Description: 发票信息管理
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月6日 下午1:21:28
 * 
 */
@Controller
public class InvoiceController extends BaseController {
    private static final Logger log = Logger.getLogger(InvoiceController.class);

    @Resource(name = "invoiceService")
    private InvoiceService invoiceService;

    /**
     * 
     * @Description: 查询当前用户可见校区
     * 
     * @param request
     *            相应需求
     * @param response
     *            相应对象
     * @param @throws Exception 设定文件
     * @return Map<String,Object> 返回类型
     */
    @RequestMapping(method = RequestMethod.GET, value = "/ordermanager/invoice/order", headers = "Accept=application/json")
    public @ResponseBody Map<String, Object> queryOrderInvoices(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("begin to query order invoice.");
            }

            Long orderId = genLongParameter("orderId", request);

            List<TabDataInvoice> dataInvoice = invoiceService.queryByOrderId(orderId);

            result.put(RespMapKey.ERROR, false);
            result.put(RespMapKey.DATA, dataInvoice);

            if (log.isDebugEnabled()) {
                log.debug("end to query order invoice.");
            }
        } catch (Exception e) {
            result.put(RespMapKey.ERROR, true);
            result.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return result;
    }

    /**
     * 订单申请开票 查询订单实际金额及已开票金额信息
     * 
     * @param request
     *            http请求实例，封装查询条件
     * @param response
     *            http响应实例
     * @return 该订单发票相关信息
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/invoice/apply", headers = "Accept=application/json")
    public @ResponseBody Map<String, Object> queryForOrderInvApply(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            OrgModel orgModel = getOrgModel(request);
            Long orderId = genLongParameter("orderId", request);
            TabDataInvoice dataInvoice = invoiceService.queryOrderInvoceMoney(orderId);

            if (orgModel.getId().longValue() != dataInvoice.getBranchId().longValue()) {
                result.put(RespMapKey.ERROR, true);
                result.put(RespMapKey.MESSAGE, "当前选择校区和订单校区不一致，不能开票");
            } else {
                result.put(RespMapKey.ERROR, false);
                result.put(RespMapKey.DATA, dataInvoice);
            }
        } catch (Exception e) {
            result.put(RespMapKey.ERROR, true);
            result.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/invoice/service", headers = "Accept=application/json")
    public Map<String, Object> queryPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, true);
            Page<TabDataInvoice> page = invoiceService.queryForPage(paramMap);
            setRespDataForPage(request, page, resultMap);
        } catch (Exception e) {
            resultMap.put(RespMapKey.ERROR, true);
            resultMap.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/invoice/service", headers = "Accept=application/json")
    public Map<String, Object> save(@RequestBody TabDataInvoice invoice, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            setDefaultValue(request, invoice, false);
            invoice.setStatus(TabDataInvoice.StatusEnum.APPLY.getCode());
            invoice.setReceiveStatus(StatusEnum.APPLY.getCode());
            check(request, invoice);

            Account account = WebContextUtils.genUser(request);
            invoice.setApplyUser(account.getId());
            invoice.setApplyUserName(account.getUserName());
            invoice.setApplyDate(Calendar.getInstance().getTime());
            // 设置发票编码
            invoice.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.TYPE_FPTT_PREFIX));

            if (StringUtils.isEmpty(invoice.getCompanyName())
                    && Constants.InvoiceHeading.PERSONAL.equals(invoice.getHeading())) {
                invoice.setCompanyName(invoice.getStudentName());
            }

            invoiceService.save(invoice);
            result.put(RespMapKey.ERROR, false);
            result.put(RespMapKey.DATA, invoice);
        } catch (Exception e) {
            result.put(RespMapKey.ERROR, true);
            result.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/invoice/service", headers = "Accept=application/json")
    public Map<String, Object> update(@RequestBody TabDataInvoice invoice, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (invoiceService.isExist(invoice.getId())) {
                check(request, invoice);
                setDefaultValue(request, invoice, true);

                // 开票，设置开票人及开票时间
                if (TabDataInvoice.StatusEnum.MAKEOUT.getCode().intValue() == invoice.getStatus()) {
                    Account account = WebContextUtils.genUser(request);
                    invoice.setInvoiceUser(account.getId());
                    invoice.setInvoiceDate(Calendar.getInstance().getTime());
                }

                invoiceService.update(invoice);
                result.put(RespMapKey.ERROR, false);
                result.put(RespMapKey.DATA, invoice);
            } else {
                result.put(RespMapKey.ERROR, true);
                result.put(RespMapKey.MESSAGE, "发票不存在");
            }
        } catch (Exception e) {
            result.put(RespMapKey.ERROR, true);
            result.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/invoice/export", headers = "Accept=application/json")
    public Map<String, Object> export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, false);
            Page<TabDataInvoice> page = invoiceService.queryForPage(paramMap);
            if (page == null || CollectionUtils.isEmpty(page.getResult())) {
                throw new Exception("导出失败：没有导出数据！");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            // 模板文件路径
            String templateFilePath = rootPath + File.separator + "template";
            // 模板文件名
            String templateFileName = "invoice.xls";
            // 临时文件路径
            String tempFilePath = rootPath + File.separator + "temp";
            // 临时文件名
            String tempFileName = "invoice_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
            ExcelWriteHandler.writeDataToExcel(page.getResult(), templateFilePath, templateFileName, tempFilePath,
                    tempFileName);
            resultMap.put(Constants.RespMapKey.DATA, tempFileName);
        } catch (Exception e) {
            resultMap.put(RespMapKey.ERROR, true);
            resultMap.put(RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    private void check(HttpServletRequest request, TabDataInvoice invoice) throws Exception {
        if (invoice == null || StringUtils.isEmpty(invoice.getHeading()) || invoice.getRequiredMoney() == null
                || invoice.getRequiredMoney() == 0) {
            throw new Exception("发票抬头、申请开票金额必输");
        }

        if (Constants.InvoiceHeading.COMPANY.equals(invoice.getHeading())
                && StringUtils.isEmpty(invoice.getCompanyName())) {
            throw new Exception("请输入开票公司");
        } else if (Constants.InvoiceHeading.PERSONAL.equals(invoice.getHeading())
                && StringUtils.isEmpty(invoice.getCompanyName())) {
            throw new Exception("请输入姓名");
        }

        if (TabDataInvoice.StatusEnum.APPLY.getCode().intValue() == invoice.getStatus()) {
            // 订单实际金额
            Long orderMoney = invoice.getOrderMoney() == null ? 0 : invoice.getOrderMoney();
            if (orderMoney <= 0) {
                throw new Exception("订单没有可开票金额，不能开票");
            }

            // 已开票金额
            Long money = invoice.getMoney() == null ? 0 : invoice.getMoney();
            if (invoice.getRequiredMoney() > orderMoney) {
                throw new Exception("申请开票金额大于订单实际可开票金额，不能开票");
            }

            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (invoice.getBranchId() != null && invoice.getBranchId().longValue() != orgModel.getId().longValue()) {
                throw new Exception("当前选择校区和订单校区不一致，不能开票");
            }
        } else if (TabDataInvoice.StatusEnum.MAKEOUT.getCode().intValue() == invoice.getStatus()) {
            Long actualMoney = invoice.getActualMoney();
            if (actualMoney == null || actualMoney == 0 || invoice.getInvoiceCompamy() == null
                    || StringUtils.isEmpty(invoice.getInvoiceCode())) {
                throw new Exception("实开金额、发票号码、发票单位必输");
            }

            Long requiredMoney = invoice.getRequiredMoney();
            if (actualMoney > requiredMoney) {
                throw new Exception("实开金额大于申请开票金额，不能开票");
            }
        }
    }
}
