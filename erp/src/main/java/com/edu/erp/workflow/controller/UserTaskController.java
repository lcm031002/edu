package com.edu.erp.workflow.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.NumberUtils;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.util.BaseController;
import com.edu.erp.workflow.business.MapType;
import com.edu.erp.workflow.business.TaskBusiness;
import com.edu.erp.workflow.service.TaskOwService;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.common.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/workflow" })
public class UserTaskController extends BaseController {

	@Resource(name = "taskOwService")
	private TaskOwService taskOwService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	private static final Logger log = Logger
			.getLogger(UserTaskController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/task/todo", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryUserTasksPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			String p_task_info = request.getParameter("queryString");
			if (StringUtils.isNotEmpty(p_task_info)) {
				p_task_info = URLDecoder.decode(p_task_info, "utf-8");
			}

			// 校区
			Integer p_branch_id = genIntParameter("branchId", request);

			String module = request.getParameter("module");
			if (StringUtils.isNotEmpty(module)) {
				module = URLDecoder.decode(request.getParameter("module"),
						"utf-8");
			}
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("recandidate", account.getId());
			params.put("task_info", p_task_info);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("unassignee", true);
			params.put("module", module);
			params.put("branch_id", p_branch_id);

			String role_ids = "" + account.getEmployeeId();
			params.put("candidates", role_ids);

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

			Page<TaskBusiness> tasks = taskOwService.findTasks(params);
			PageInfo<TaskBusiness> pageData = new PageInfo<TaskBusiness>(tasks);

			List<TaskBusiness> resultData = pageData.getList();
			for (TaskBusiness tb : resultData) {
				params.clear();
				params.put("executionId", tb.getExecutionId());
				List<MapType> extDataL = (List<MapType>) taskOwService
						.findVariables(params);
				tb.setExtDataL(extDataL);
				tb.procExtData();
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pageSize", pageSize);
			data.put("currentPage", currentPage);
			data.put("totalCount", pageData.getTotal());
			data.put("totalPage", pageData.getPages());
			data.put("resultList", resultData);
			result.put("data", data);
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "异常查询");
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/task/myAppication", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryUserAppicationPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);

			String auditStatus = request.getParameter("auditStatus");
			if (StringUtils.isNotEmpty(auditStatus)) {
				auditStatus = URLDecoder.decode(
						request.getParameter("auditStatus"), "utf-8");
			}
			String app_info = request.getParameter("app_info");
			if (StringUtils.isNotEmpty(app_info)) {
				app_info = URLDecoder.decode(request.getParameter("app_info"),
						"utf-8");
			}
			String module = request.getParameter("module");
			if (StringUtils.isNotEmpty(module)) {
				module = URLDecoder.decode(request.getParameter("module"),
						"utf-8");
			}

			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("APPLICATION_ID", account.getId());
			map.put("auditStatus", auditStatus);
			map.put("app_info", app_info);
			map.put("module", module);
			map.put("start_date", start_date);
			map.put("end_date", end_date);

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

			Page<Map<String, Object>> tasks = userTaskService
					.queryApplication(map);
			PageInfo<Map<String, Object>> pageData = new PageInfo<Map<String, Object>>(
					tasks);
			
			// 查询结果不空， 从WORKURL中解析出订单Id 和 学生Id
			if(!CollectionUtils.isEmpty(tasks.getResult())) {
				String workUrl = null; //格式：#/order-detail/1/100490726/525411537
				String studentId = null;
				String orderId = null;
				int index = -1;
				for(Map<String, Object> rowData : tasks.getResult()) {
					workUrl = (String)rowData.get("WORKURL");
					if(StringUtils.isBlank(workUrl)) {
						continue;
					}
					index = workUrl.lastIndexOf("/");
					if(index > -1) {
						orderId = workUrl.substring(index+1);
						workUrl = workUrl.substring(0, index);
						index = workUrl.lastIndexOf("/");
						if(index > -1) {
							studentId = workUrl.substring(index+1);
						}
					}
					rowData.put("studentId", studentId);
					rowData.put("orderId", orderId);
					rowData.put("isTemp", true);

					Long tabOrderId = NumberUtils.string2Long(orderId);
					if (tabOrderId != null) {
						TabOrderInfo order = this.orderInfoService.queryOrderInfo(tabOrderId);
						if (order != null && order.getPay_status() != null && order.getPay_status().longValue() == 1) {
							rowData.put("isTemp", false);
						}
					}

				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pageSize", pageSize);
			data.put("currentPage", currentPage);
			data.put("totalCount", pageData.getTotal());
			data.put("totalPage", pageData.getPages());
			data.put("resultList", tasks.getResult());
			result.put("data", data);
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "NobranchException");
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/task/branchAppication", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryBranchAppicationPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String auditStatus = request.getParameter("auditStatus");
			if (StringUtils.isNotEmpty(auditStatus)) {
				auditStatus = URLDecoder.decode(
						request.getParameter("auditStatus"), "utf-8");
			}
			String app_info = request.getParameter("app_info");
			if (StringUtils.isNotEmpty(app_info)) {
				app_info = URLDecoder.decode(request.getParameter("app_info"),
						"utf-8");
			}
			String module = request.getParameter("module");
			if (StringUtils.isNotEmpty(module)) {
				module = URLDecoder.decode(request.getParameter("module"),
						"utf-8");
			}

			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("task_info", app_info);
			params.put("module", module);
			params.put("auditStatus", auditStatus);
			params.put("start_date", start_date);
			params.put("end_date", end_date);

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getType() == null || !"4".endsWith(orgModel.getType())) {
				log.error("未选择校区，不能查询校区任务！"); 
				throw new Exception("未选择校区，校区任务无法查询！");
			}

			params.put("branch_id", orgModel.getId());
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

			Page<TaskBusiness> tasks = taskOwService.findBranchTaskPage(params);
			PageInfo<TaskBusiness> pageData = new PageInfo<TaskBusiness>(tasks);
			List<TaskBusiness> resultList = pageData.getList();
			
			if (!CollectionUtils.isEmpty(resultList)) {
				// 参数放到map
				for (TaskBusiness tb : resultList) {
					params.clear();
					params.put("executionId", tb.getExecutionId());
					List<MapType> extDataL = (List<MapType>) taskOwService
							.findVariables(params);
					tb.setExtDataL(extDataL);
					tb.procExtData();
				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("pageSize", pageSize);
			data.put("currentPage", currentPage);
			data.put("totalCount", pageData.getTotal());
			data.put("totalPage", pageData.getPages());
			data.put("resultList", resultList);
			result.put("data", data);
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", e.getMessage());
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/task/myAppication", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> deleteUserAppication(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long applicationId = genLongParameter("applicationId", request);

			userTaskService.deleteApplication(applicationId);
			result.put("error", false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "参数异常，调用失败！");
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/task/orderId", headers = "Accept=application/json")
    public @ResponseBody
    Map<String, Object> queryTaskByOrderId(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Account account = WebContextUtils.genUser(request);
            String orderId = request.getParameter("orderId");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("recandidate", account.getId());
            params.put("orderId", orderId);
            String role_ids = "" + account.getEmployeeId();
            params.put("candidates", role_ids);

            TaskBusiness task = taskOwService.findTaskByOrderId(params);
            result.put("data", task);
            result.put("error", false);
        } catch (IllegalArgumentException e) {
            result.put("error", true);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("error", true);
            result.put("message", e.getMessage());
        }
        return result;
    }

}
