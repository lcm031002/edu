/**  
 * @Title: PrivilegeRuleController.java
 * @Package com.ebusiness.erp.promotion.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月1日 上午11:07:30
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.promotion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.model.PrivilegeRule;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: PrivilegeRuleController
 * @Description: 优惠规则服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月1日 上午11:07:30
 * 
 */
@Controller
@RequestMapping(value = { "/privilegerule" })
public class PrivilegeRuleController extends BaseController {
	private static final Logger log = Logger
			.getLogger(PrivilegeRuleController.class);

	@Resource(name = "privilegeRuleService")
	private PrivilegeRuleService privilegeRuleService;

	@RequestMapping(method = RequestMethod.GET, value = "/service", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryBranchRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query Privilege Rule.");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Long branchId = orgModel.getId();

			String isOrder = request.getParameter("isOrder");
			
 			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("branchId", branchId);
			queryParam.put("isOrder", isOrder);
			List<PrivilegeRule> queryRules = privilegeRuleService
					.queryRuleByBranchId(queryParam);
			result.put("error", false);
			result.put("data", queryRules);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/servicePage", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> pageBranchRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query Privilege Rule.");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			String rule_name = request.getParameter("searchInfo");
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("searchInfo", rule_name);			
			
			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);

			if (currentPage == null) {
				currentPage = 1;
			}

			if (pageSize == null) {
				pageSize = 10;
			}
			
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);
			
			Page<PrivilegeRule> page = privilegeRuleService
					.queryPrivilegeRuleForPage(queryParam);
			
			PageInfo<PrivilegeRule> pageData = new PageInfo<PrivilegeRule>(
					page);
			result.put("error", false);
			result.put("data", pageData.getList());
			result.put("total", pageData.getTotal());
			result.put("totalPage", pageData.getPages());
			result.put("currentPage", currentPage);
			result.put("pageSize", pageSize);
			return result;
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> addPrivilegeRule(
			@RequestBody PrivilegeRule privilegeRule, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save Privilege Rule.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			Account account = WebContextUtils.genUser(request);
			privilegeRule.setCity_id(orgModel.getCityId());
			privilegeRule.setBu_id(orgModel.getBuId());
			privilegeRule.setStatus(PrivilegeRule.StatusEnum.VALID.getCode());
			privilegeRule.setCreate_user(account.getId());
			privilegeRule.setUpdate_user(account.getId());
			privilegeRule.setCreate_time(new Date());
			privilegeRule.setUpdate_time(new Date());
			privilegeRuleService.addPrivilegeRule(privilegeRule, privilegeRule.getBranch_ids());
			
			result.put("error", false);
			result.put("data", privilegeRule);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save Privilege Rule.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updatePrivilegeRule(
			@RequestBody PrivilegeRule privilegeRule, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save Privilege Rule.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

			Account account = WebContextUtils.genUser(request);
			privilegeRule.setUpdate_user(account.getId());
			privilegeRule.setUpdate_time(new Date());
			privilegeRuleService.updatePrivilegeRule(privilegeRule, privilegeRule.getBranch_ids());
			
			result.put("error", false);
			result.put("data", privilegeRule);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save Privilege Rule.");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSubject(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String ruleId = request.getParameter("id");
			privilegeRuleService.deletePrivilegeRule(ruleId);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
}
