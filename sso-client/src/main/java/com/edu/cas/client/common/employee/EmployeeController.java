package com.edu.cas.client.common.employee;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.Employee;
import com.edu.cas.client.common.model.HttpReponse;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: EmployeeController
 * @Description: 员工控制类
 *
 */
@Controller
public class EmployeeController {
	private static Logger log = Logger.getLogger(EmployeeController.class);

	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@RequestMapping(value = { "/common/employeeservice" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	HttpReponse queryEmployee(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user employee info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			httpReponse.setError(false);
			try {

				if (null != request.getParameter("employeeId")) {
					String str = request.getParameter("employeeId");
					Long employeeId = Long.parseLong(str);
					Employee employee = employeeService.queryById(employeeId);
					httpReponse.setData(employee);
					httpReponse.setError(false);
				} else {
					if (null != account.getEmployeeId()) {
						Employee employee = employeeService.queryById(account
								.getEmployeeId());
						httpReponse.setData(employee);
						httpReponse.setError(false);
					} else {
						httpReponse.setError(true);
						httpReponse.setMessage("account not bind employee.");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user employee info");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: queryEmployeeList
	 * @Description: 查询可用员工列表
	 * @param request
	 *            查询参数上下文对象
	 * @param response
	 *            返回对象
	 * @return 设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@RequestMapping(value = { "/common/employeeservice/list" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryEmployeeList(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user employee info");
		}
		Map<String, Object> httpReponse = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = new HashMap<String, Object>();

			String searchName = request.getParameter("searchName");
			if (StringUtils.isNotEmpty(searchName)) {
				searchName = URLDecoder.decode(
						request.getParameter("searchName"), "utf-8");
			}
			queryParam.put("searchName", searchName);
			log.debug("begin to query employee info,searchName is "
					+ searchName);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getCityId() == null) {
				log.error("未选择组织机构，无法定位所在城市！");
				throw new Exception("请选择组织机构，无法定位所在城市！");
			}
			queryParam.put("cityId", orgModel.getCityId());
			// 当前页数searchName
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

			Page<Employee> queryPageInfo = employeeService
					.queryEmployeeList(queryParam);
			PageInfo<Employee> pageData = new PageInfo<Employee>(queryPageInfo);
			httpReponse.put("error", false);
			httpReponse.put("data", pageData.getList());
			httpReponse.put("total", pageData.getTotal());
			httpReponse.put("totalPage", pageData.getPages());
			httpReponse.put("currentPage", currentPage);
			httpReponse.put("pageSize", pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found ,see:", e);
			httpReponse.put("error", true);
			httpReponse.put("message", e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user employee info");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: updateEmployee
	 * @Description: 更新员工信息
	 * @param employee
	 * @param request
	 * @param response
	 * @return 设定文件
	 * @return HttpReponse 返回类型
	 * @throws
	 */
	@RequestMapping(value = { "/common/employeeservice" }, method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody
	HttpReponse updateEmployee(@RequestBody Employee employee,
			HttpServletRequest request, HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to update user employee.");
		}
		try {
			employeeService.update(employee);
			// FIXME 系统审计日志
			httpReponse.setError(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to update user employee.");
		}
		return httpReponse;
	}

	protected Integer genIntParameter(String name, HttpServletRequest request) {
		String param = request.getParameter(name);
		Integer longParam = null;
		try {
			if (StringUtils.isNotBlank(param)) {
				longParam = Integer.parseInt(param);
			}
		} catch (Exception e) {
			log.error("error found,see:", e);
		}
		return longParam;
	}

}
