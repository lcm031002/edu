/**  
 * @Title: PrivilegeCriteriaController.java
 * @Package com.ebusiness.erp.promotion.controller
 * @author ouhengshan ohs@klxuexi.org  
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
import com.edu.erp.model.PrivilegeCriteria;
import com.edu.erp.promotion.service.PrivilegeCriteriaService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: PrivilegeCriteriaController
 * @Description: 优惠前置服务
 * @author ouhengshan ohs@klxuexi.org  
 * @date 2017年1月12日 上午11:07:30
 * 
 */
@Controller
@RequestMapping(value = { "/privilegecriteria" })
public class PrivilegeCriteriaController extends BaseController {
	private static final Logger log = Logger
			.getLogger(PrivilegeCriteriaController.class);

	@Resource(name = "privilegeCriteriaService")
	private PrivilegeCriteriaService privilegeCriteriaService;

	@RequestMapping(method = RequestMethod.GET, value = "/servicePage", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> pageBranchCriteria(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query Privilege Criteria.");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			
			String Criteria_name = request.getParameter("searchInfo");
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("searchInfo", Criteria_name);			
			
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
			
			Page<PrivilegeCriteria> page = privilegeCriteriaService
					.queryPrivilegeCriteriaForPage(queryParam);
			
			PageInfo<PrivilegeCriteria> pageData = new PageInfo<PrivilegeCriteria>(
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
	public Map<String, Object> addPrivilegeCriteria(
			@RequestBody PrivilegeCriteria privilegeCriteria, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save order.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			Account account = WebContextUtils.genUser(request);
			privilegeCriteria.setCity_id(orgModel.getCityId());
			privilegeCriteria.setBu_id(orgModel.getBuId());			
			privilegeCriteria.setStatus(PrivilegeCriteria.StatusEnum.VALID.getCode());
			privilegeCriteria.setCreate_user(account.getId());
			privilegeCriteria.setCreate_time(new Date());
			privilegeCriteria.setUpdate_time(new Date());
			privilegeCriteriaService.addPrivilegeCriteria(privilegeCriteria);
			
			result.put("error", false);
			result.put("data", privilegeCriteria);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save order.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updatePrivilegeCriteria(
			@RequestBody PrivilegeCriteria privilegeCriteria, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save order.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

			Account account = WebContextUtils.genUser(request);
			privilegeCriteria.setUpdate_user(account.getId());
			privilegeCriteria.setUpdate_time(new Date());
			privilegeCriteriaService.updatePrivilegeCriteria(privilegeCriteria);
			
			result.put("error", false);
			result.put("data", privilegeCriteria);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save order.");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSubject(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String ruleId = request.getParameter("id");
			privilegeCriteriaService.deletePrivilegeCriteria(ruleId);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
}
