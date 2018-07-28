package com.edu.erp.common.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.common.service.TpScheduleTimeService;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.TPScheduleTime;
import com.edu.erp.model.TPSchoolType;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ModelDataUtils;
import com.github.pagehelper.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: TpScheduleTimeController
 * @Description: 排课档期控制器
 *
 */
@Controller
@RequestMapping(value = { "/common/tpScheduleTime" })
public class TpScheduleTimeController extends BaseController{
	private static final Logger log = Logger.getLogger(TpScheduleTimeController.class);
	
	@Resource(name = "tpScheduleTimeService")
	private TpScheduleTimeService tpScheduleTimeService;

	/**
	 * 查询排课档期List
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		List<TPScheduleTime> scheduleTimeList = null;
		try {
			Map<String, Object> paramMap = initParamMap(request, false);
			scheduleTimeList = tpScheduleTimeService.list(paramMap);
			result.put("error", false);
			result.put("data", scheduleTimeList);
		} catch (Exception e) {
			System.err.println(e);
			result.put("error", true);
			result.put("message", "查询失败！see:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 排课档期的分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryTpScheduleTimePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {			
			Map<String, Object> queryParam = initParamMap(request, true);
			Page<Map<String, Object>> page = tpScheduleTimeService.queryPage(queryParam);
			setRespDataForPage(request, page, resultMap);
			resultMap.put("error", false);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 新增排课档期信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addTpScheduleTime(@RequestBody Map<String, Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			TPScheduleTime tpScheduleTime = ModelDataUtils.getPojoMatch(TPScheduleTime.class, param);
			tpScheduleTime.setBranchId(orgModel.getId());
			tpScheduleTime.setBuId(orgModel.getBuId());
			tpScheduleTime.setCreate_user(account.getId());
			tpScheduleTime.setStatus(TPScheduleTime.StatusEnum.VALID.getCode());
			tpScheduleTime.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			tpScheduleTimeService.toAdd(tpScheduleTime);
			resultMap.put("error", false);
			resultMap.put("message", "新增成功!");
		} catch (Exception e) {
			resultMap.put("error", true);
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	/**
	 * 更新排课档期信息
	 * @param tpScheduleTime
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateSchool(@RequestBody TPScheduleTime tpScheduleTime,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			tpScheduleTime.setUpdate_user(account.getId());
			tpScheduleTime.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			tpScheduleTimeService.toUpdate(tpScheduleTime);
			resultMap.put("error", false);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	/**
	 * 删除排课档期信息
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSchool(@RequestParam String ids,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			tpScheduleTimeService.toChangeStatus(ids,BaseObject.StatusEnum.DELETE.getCode());
			resultMap.put("error", false);
			resultMap.put("message", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "删除失败！see:" + e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 修改排课档期状态
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/changeStatus" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> updateTpScheduleTimeStatus(@RequestParam String ids,@RequestParam Integer status,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			tpScheduleTimeService.toChangeStatus(ids,status);
			resultMap.put("error", false);
			resultMap.put("message", "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "修改失败！see:" + e.getMessage());
		}
		return resultMap;
	}
	
	
}
