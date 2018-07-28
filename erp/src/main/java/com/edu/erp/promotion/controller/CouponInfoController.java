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
import com.edu.erp.model.CouponInfo;
import com.edu.erp.promotion.service.CouponInfoService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: CouponInfoController
 * @Description: 优惠券
 *
 */
@Controller
@RequestMapping(value = { "/couponinfo" })
public class CouponInfoController extends BaseController{
	private static final Logger log = Logger.getLogger(CouponInfoController.class);
	
	@Resource(name = "couponInfoService")
	private CouponInfoService couponInfoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/service", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryCouponInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query coupon info.");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

			String encoding = request.getParameter("encoding");
			
			CouponInfo couponInfo = couponInfoService.queryCouponInfoByCode(encoding);
			result.put("error", false);
			result.put("data", couponInfo);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/services", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> queryCouponInfos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query coupon info.");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			String rule_name = request.getParameter("searchInfo");
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("searchInfo", rule_name);	
			
			List<CouponInfo> queryCouponInfos = couponInfoService.queryCouponInfoForList(queryParam);
			result.put("error", false);
			result.put("data", queryCouponInfos);
		} catch (Exception e) {
			result.put("error", true);
			result.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/servicePage", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> pageBranchCoupon(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (log.isDebugEnabled()) {
				log.debug("query Coupon Info.");
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
			
			Page<CouponInfo> page = couponInfoService
					.queryCouponInfoForPage(queryParam);
			
			PageInfo<CouponInfo> pageData = new PageInfo<CouponInfo>(
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
	public Map<String, Object> addCouponInfo(
			@RequestBody CouponInfo couponInfo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save Privilege Rule.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			Account account = WebContextUtils.genUser(request);
			couponInfo.setCity_id(orgModel.getCityId());
			couponInfo.setBu_id(orgModel.getBuId());
			couponInfo.setStatus(CouponInfo.StatusEnum.VALID.getCode());
			couponInfo.setCreate_user(account.getId());
			couponInfo.setCreate_time(new Date());
			couponInfo.setUpdate_time(new Date());
			
			couponInfoService.addCouponInfo(couponInfo);
			
			result.put("error", false);
			result.put("data", couponInfo);
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
	public Map<String, Object> updateCouponInfo(
			@RequestBody CouponInfo couponInfo, HttpServletRequest request,
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
			couponInfo.setUpdate_user(account.getId());
			couponInfo.setUpdate_time(new Date());
			couponInfoService.updateCouponInfo(couponInfo);
			
			result.put("error", false);
			result.put("data", couponInfo);
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
			couponInfoService.deleteCouponInfo(ruleId);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	
}
