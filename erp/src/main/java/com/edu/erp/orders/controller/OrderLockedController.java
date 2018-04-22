package com.edu.erp.orders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.Constants;
import com.edu.common.util.ERPConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.model.TOrderLock;
import com.edu.erp.orders.service.OrderLockedService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ReportWriteExcelHandler;

@Controller
@RequestMapping(value = { "/orderlocked" })
public class OrderLockedController extends BaseController {
	
	private static final Logger log = Logger.getLogger(OrderLockedController.class);
	
	@Resource(name = "orderLockedService")
	private OrderLockedService orderLockedService;
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = super.initParamMap(request, false);
			List<TOrderLock> data = orderLockedService.queryLockedOrderList(queryParam);

			resultMap.put("data", data);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> carryForward(@RequestBody Map<String, Object> param, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Account account = WebContextUtils.genUser(request);

			orderLockedService.carryForward(param, account.getId());
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "结转失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/export", headers = "Accept=application/json")
    public Map<String, Object> export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	Map<String, Object> queryParam = super.initParamMap(request, false);
			List<TOrderLock> data = orderLockedService.queryLockedOrderList(queryParam);
            if (CollectionUtils.isEmpty(data)) {
                throw new Exception("导出失败：没有导出数据！");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            // 模板文件名
            String templateFileName = "orderLocked.xlsx";
            // 临时文件名
            String tempFileName = "锁定订单表_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            ReportWriteExcelHandler.writeDataToExcel(data, rootPath, templateFileName,
                    tempFileName);
            resultMap.put(Constants.RespMapKey.DATA, tempFileName);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }
}
