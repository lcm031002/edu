package com.edu.erp.orders.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserOrderController extends BaseController {
	private static final Logger log = Logger
			.getLogger(UserOrderController.class);

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@RequestMapping(method = RequestMethod.GET, value = "/ordermanager/user/myOrders", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryUserOrders(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);

			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String auditStatus = request.getParameter("auditStatus");
			String app_info = request.getParameter("app_info");
			if (StringUtils.isNotEmpty(auditStatus)) {
				auditStatus = URLDecoder.decode(
						request.getParameter("auditStatus"), "utf-8");
			}
			if (StringUtils.isNotEmpty(app_info)) {
				app_info = URLDecoder.decode(request.getParameter("app_info"),
						"utf-8");
			}

			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);

			if (currentPage == null) {
				currentPage = 1;
			}

			if (pageSize == null) {
				pageSize = 20;
			}

			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);

			Map<String, Object> map = new HashMap<String, Object>();

			if (null != app_info) {
				map.put("app_info", app_info);
			}
			if (auditStatus.equals("暂存")) {
				map.put("check_status", 1);
				map.put("pay_status", 0);
			}
			if (auditStatus.equals("审核中")) {
				map.put("check_status", 2);
				map.put("pay_status", 0);
			}
			if (auditStatus.equals("已完成")) {
				map.put("check_status", 3);
				map.put("pay_status", 1);
			}
			if (auditStatus.equals("异常订单")) {
				map.put("check_status", 2);
				map.put("pay_status", 1);
			}
			if (auditStatus.equals("欠费")) {
				map.put("check_status", 3);
				map.put("pay_status", 0);
			}
			if (auditStatus.equals("审核未通过")) {
				map.put("check_status", 4);
				map.put("pay_status", 0);
			}
			map.put("userId", account.getId());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			
			Page<Map<String, Object>> resultOrders = orderInfoService
					.queryUserOrderList(map);
			PageInfo<Map<String, Object>> pageData = new PageInfo<Map<String, Object>>(
					resultOrders);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pageSize", pageSize);
			data.put("currentPage", currentPage);
			data.put("totalCount", pageData.getTotal());
			data.put("totalPage", pageData.getPages());
			data.put("resultList", pageData.getList());
			result.put("data", data);
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			result.put("error", true);
			result.put("message", "非法访问！");
			log.error(e);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
			log.error(e);
		}
		return result;
	}
}
