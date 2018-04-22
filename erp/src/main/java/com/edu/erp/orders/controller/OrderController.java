package com.edu.erp.orders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping(value = { "/order" })
public class OrderController extends BaseController {
	private static final Logger log = Logger
			.getLogger(OrderController.class);

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//必填项：默认为当前团队 BUID. 校验
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Long bu_id=orgModel.getBuId();
			if(!StringUtils.isEmpty( request.getParameter("bu_id"))){
				bu_id=Long.parseLong(request.getParameter("bu_id").toString());
		    }
			Map<String, Object> queryParam = initParamMap(request, true);
			paramMap.putAll(queryParam);
			paramMap.put("order_no",  request.getParameter("order_no"));
			paramMap.put("orderType", request.getParameter("orderType"));
			paramMap.put("student_name",  request.getParameter("student_name"));
			paramMap.put("bu_id", bu_id);
			paramMap.put("branch_id", request.getParameter("branch_id"));
			paramMap.put("business_type", StringUtils.isEmpty( request.getParameter("business_type"))?1:request.getParameter("business_type").toString());
			Page<Map<String, Object>> page = orderService.queryPage(paramMap);
			resultMap.put("bu_id", bu_id);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 订单详情
	 * @param orderId 订单ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryOrderCouseDetails(@RequestParam Long orderId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<TOrderCourse> tOrderCouseList=orderService.queryOrderCoursePage(orderId);
			setRespDataForPage(request, tOrderCouseList, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 订单作废
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value ={ "/service"}, headers = "Accept=application/json",method = RequestMethod.DELETE )
	public  Map<String, Object> orderCancel(@RequestParam Long orderId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Account account = WebContextUtils.genUser(request);
			String remark = request.getParameter("remark");
			if (StringUtils.isNotBlank(remark) && remark.length() > 100) {
				remark = remark.substring(0, 100);
			}
			orderInfoService.cancelOrderInfo(orderId, remark, LogOperateUtil.getInstance().genUserInfo(request), account, orgModel, resultMap,processEngine);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 订单作废
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value ={ "/lock"}, headers = "Accept=application/json",method = RequestMethod.PUT )
	public  Map<String, Object> orderLock(@RequestBody Map<String, Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Account account = WebContextUtils.genUser(request);
			Long orderId= Long.parseLong(param.get("orderId").toString());
			String remark = param.get("remark").toString();
			String status = param.get("status").toString();
			if (StringUtils.isNotBlank(remark) && remark.length() > 100) {
				remark = remark.substring(0, 100);
			}
			orderInfoService.lockOrder(status,orderId, remark, LogOperateUtil.getInstance().genUserInfo(request), account, orgModel, resultMap,processEngine);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
}
