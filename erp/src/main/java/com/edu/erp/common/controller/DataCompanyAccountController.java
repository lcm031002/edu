package com.edu.erp.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.common.service.DataCompanyAccountService;
import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping(value = { "/common/companyaccount" })
public class DataCompanyAccountController extends BaseController {
	private static final Logger log = Logger.getLogger(DataCompanyAccountController.class);
	@Resource(name = "dataCompanyAccountService")
	private DataCompanyAccountService dataCompanyAccountService;

	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (!"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}
			Integer currentPage = genIntParameter("currentPage", request);
			Map<String, Object> param = initParamMap(request, currentPage != null);
			Page<DataCompanyAccount> page = dataCompanyAccountService.queryPage(param);
			setRespDataForPage(request, page, result);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		return result;
	}

	/**
	 * 新增公司账户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@RequestBody DataCompanyAccount companyAccount,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			this.check(companyAccount);
			setDefaultValue(request, companyAccount, false);
			log.debug(ToStringBuilder.reflectionToString(companyAccount, ToStringStyle.SHORT_PREFIX_STYLE));
			dataCompanyAccountService.save(companyAccount);
			result.put("error", false);
			result.put("data", companyAccount);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		return result;
	}

	/**
	 * 修改公司账户信息
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> update(@RequestBody DataCompanyAccount companyAccount,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			this.check(companyAccount);
			setDefaultValue(request, companyAccount, true);
			dataCompanyAccountService.update(companyAccount);
			result.put("error", false);
			result.put("data", companyAccount);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	/**
	 * 删除公司账户，已经在用的公司账户不能删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Long id = genLongParameter("id", request);
		if (id == null) {
			result.put("error", true);
			result.put("message", "请选择删除数据");
			return result;
		}
		dataCompanyAccountService.deleteById(id);
		result.put("error", false);
		return result;
	}

	/**
	 * 修改账户使用状态 参数： accountId：必填，账户id； status：必填，1-启用，0-停用；
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/status" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> status(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		return result;
	}

	private void check(DataCompanyAccount companyAccount) throws Exception {
		if (StringUtils.isBlank(companyAccount.getAccount_name())
				|| StringUtils.isBlank(companyAccount.getAccount_num())) {
			throw new Exception("账户名称、账户号码必填数据");
		}
	}

}
