package com.edu.erp.promotion.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.util.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.common.util.ModelDataUtils;
import com.edu.erp.model.ActivityInfo;
import com.edu.erp.model.CouponInfo;
import com.edu.erp.promotion.service.ActivityInfoService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 优惠活动
 *
 */
@Controller
@RequestMapping(value = {"activityInfo"})
public class ActivityInfoController extends BaseController {
	private static final Logger log = Logger
			.getLogger(ActivityInfoController.class);
	
	@Resource(name = "activityInfoService")
	private ActivityInfoService activityInfoService;
	
	@Resource(name = "privilegeRuleService")
	private PrivilegeRuleService privilegeRuleService;
	
	/**
	 * 表格分页
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/services", headers = "Accept=application/json")
	public Map<String, Object> pageActivityInfo(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>(); 
		try {	
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			
			String activity_name = request.getParameter("searchInfo");
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("searchInfo", activity_name);	
			
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
			
			Page<ActivityInfo> page = activityInfoService.queryActivityInfoForPage(queryParam);
			
			PageInfo<ActivityInfo> pageData = new PageInfo<ActivityInfo>(
					page);
			
			result.put("error", false);
			result.put("data", pageData.getList());
			result.put("total", pageData.getTotal());
			result.put("totalPage", pageData.getPages());
			result.put("currentPage", currentPage);
			result.put("pageSize", pageSize);
			return result;
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return result;
	}
	

	/**
	 * 新增
	 * 
	 * @param activityInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/services" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> addActivityInfo(@RequestBody ActivityInfo activityInfo,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Account account = WebContextUtils.genUser(request);
			
			activityInfo.setCreate_user(account.getId());
			activityInfo.setCreate_time(new Date());
			activityInfo.setUpdate_time(new Date());
			activityInfo.setStatus(ActivityInfo.StatusEnum.VALID.getCode());
			activityInfo.setCity_id(orgModel.getCityId());
			activityInfo.setBu_id(orgModel.getBuId());
			activityInfoService.addActivityInfo(activityInfo);
			
			result.put("error", false);
			result.put("data", activityInfo);
		}catch(Exception e){
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 更新
	 * 
	 * @param activityInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/services" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updateActivityInfo(@RequestBody ActivityInfo activityInfo,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Account account = WebContextUtils.genUser(request);
			activityInfo.setUpdate_user(account.getId());
			activityInfo.setUpdate_time(new Date());
			activityInfoService.updateActivityInfo(activityInfo);
			
			result.put("error", false);
			result.put("data", activityInfo);
		}catch(Exception e){
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/services" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSubject(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String ruleId = request.getParameter("id");
			activityInfoService.deleteActivityInfo(ruleId);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 券仓库
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"couponDepot"})
	public ModelAndView couponDepot(HttpServletRequest request){
		try{
			ModelDataUtils.transferParaToAttr(request);
		}catch(Exception e){
			System.err.println(e);
		}
		return new ModelAndView("system/promotion/couponDepot");
	}
	
	/**
	 * 生成券仓库
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/generateCouponDepot" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> generateCoupon(@RequestBody CouponInfo couponDepot,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Account account = WebContextUtils.genUser(request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);

			couponDepot.setCity_id(orgModel.getCityId());
			couponDepot.setBu_id(orgModel.getBuId());			
			couponDepot.setCreate_user(account.getId()!=null?account.getId():null);
			activityInfoService.generateCouponDepot(couponDepot, Long.parseLong(couponDepot.getCouponCount()));
			
			result.put("error", false);
			result.put("data", couponDepot);
		}catch(Exception e){
			result.put("error", false);
			result.put("data", couponDepot);
		}
		return result;
	}
	
}

