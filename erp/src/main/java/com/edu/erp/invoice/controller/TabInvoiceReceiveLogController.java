package com.edu.erp.invoice.controller;

import com.edu.common.constants.Constants.RespMapKey;
import com.edu.erp.invoice.service.TabInvoiceReceiveLogService;
import com.edu.erp.model.TabInvoiceReceiveLog;
import com.edu.erp.util.BaseController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/invoice/receive" })
public class TabInvoiceReceiveLogController extends BaseController {
    @Resource(name = "tabInvoiceReceiveLogService")
    private TabInvoiceReceiveLogService tabInvoiceReceiveLogService;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    @ResponseBody
    public Map transferClass(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(RespMapKey.ERROR, false);
        try {
            Long invoiceId = genLongParameter("invoiceId", request);
            List<TabInvoiceReceiveLog> tabInvoiceReceiveLogList = this.tabInvoiceReceiveLogService.queryByInvoiceId(invoiceId);
            resultMap.put(RespMapKey.DATA, tabInvoiceReceiveLogList);
        } catch (Exception e) {
            resultMap.put(RespMapKey.ERROR, true);
            resultMap.put(RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/service", headers = "Accept=application/json")
    @ResponseBody
    public Map receiveInvoice(@RequestBody TabInvoiceReceiveLog invoiceReceiveLog, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(RespMapKey.ERROR, false);
        try {
            this.setDefaultValue(request, invoiceReceiveLog, false);
            this.tabInvoiceReceiveLogService.add(invoiceReceiveLog);
        } catch (Exception e) {
            resultMap.put(RespMapKey.ERROR, true);
            resultMap.put(RespMapKey.MESSAGE, e.getMessage());
        }
        return resultMap;
    }

}
