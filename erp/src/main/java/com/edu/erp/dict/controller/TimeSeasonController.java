package com.edu.erp.dict.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.dict.service.TimeSeasonService;
import com.edu.erp.model.TimeSeason;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/dictionary/timeSeason")
public class TimeSeasonController extends BaseController {
	private static final Logger log = Logger
			.getLogger(TimeSeasonController.class);

	// 注入Service
	@Resource(name = "timeSeasonService")
	private TimeSeasonService timeSeasonService;

	@Resource(name = "selectOptionService")
	private SelectOptionService selectOptionService;

	@ResponseBody
	@RequestMapping(value = { "/toManage" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> toManage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取所有课程季集合
		List<TimeSeason> timeSeasonList = new ArrayList<TimeSeason>();
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("org_city_Id", orgModel.getCityId());
		timeSeasonList = timeSeasonService.queryList(param);
		// 储存，用于选择上个课程季
		// 有的课程季 没有上一个课程季
		TimeSeason noParentSeason = new TimeSeason();
		noParentSeason.setId(-1l);
		noParentSeason.setCourse_season_name("不选");
		timeSeasonList.add(0, noParentSeason);
		resultMap.put("timeSeasonList", timeSeasonList);
		resultMap.put("productLineList",
				selectOptionService.selectProductLine(null));
		resultMap.put("error", false);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/list" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if(orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请先选择校区或团队！");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("org_city_id", orgModel.getCityId());
			param.put("product_line", genLongParameter("product_line", request)); 
			List<TimeSeason> timeSeasonList = timeSeasonService.queryList(param);
			resultMap.put("data", timeSeasonList);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 查询课程季
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = initParamMap(request, true);
			Page<TimeSeason> page = timeSeasonService.queryPage(queryParam);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody TimeSeason timeSeason,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			check(timeSeason);
			setDefaultValue(request, timeSeason, false);
			timeSeasonService.save(timeSeason);
			result.put("error", false);
			result.put("data", timeSeason);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> update(@RequestBody TimeSeason timeSeason,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			check(timeSeason);
			setDefaultValue(request, timeSeason, true);
			timeSeasonService.update(timeSeason);
			result.put("error", false);
			result.put("data", timeSeason);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.DELETE)
	public Map<String, Object> deleteById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				result.put("error", true);
				result.put("message", "请选择删除数据");
				return result;
			}
			timeSeasonService.deleteById(id);
			result.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	private void check(TimeSeason timeSeason) throws Exception {
		if (StringUtils.isEmpty(timeSeason.getCourse_season_name())
				|| timeSeason.getSeason() == null
				|| StringUtils.isEmpty(timeSeason.getStart_date())
				|| StringUtils.isEmpty(timeSeason.getEnd_date())
				|| timeSeason.getBusiness_type() == null) {
			throw new Exception("名称、季节、开始日期、结束日期和业务类型必输");
		}
	}
	
	@RequestMapping(value = { "/changeStatus" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> changeStatus(@RequestBody TimeSeason pojo, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Account account = WebContextUtils.genUser(request);
			Long loginUserId = account.getId();
			timeSeasonService.toChangeStatus(String.valueOf(pojo.getId()), pojo.getStatus(), loginUserId);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "更改状态失败: "+ e.getMessage());
		}

		return resultMap;
	}
}
