package com.edu.erp.common.controller;

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
import com.edu.common.util.DateUtil;
import com.edu.erp.common.service.DeviceService;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.DataCompanyAccount;
import com.edu.erp.model.TabDataDevice;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/common/device")
public class DeviceController extends BaseController {

	private static final Logger log = Logger.getLogger(DeviceController.class);
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	
	@Resource(name = "selectOptionService")
	private SelectOptionService selectOptionService;
	
	/**
	 * 页面初始化，查询一些下拉列表
	 * @Title: init
	 * @Description: 
	 * @param request
	 * @param response
	 * @return
	 *      设定文件 Map<String,Object> 返回类型
	 *
	 */
	@RequestMapping(value = { "/init" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> init(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("city_id", orgModel.getCityId());
			List<DataCompanyAccount> accountList = selectOptionService.selectCompanyAccountList(queryParam);
			resultMap.put("accountList", accountList);
			
			resultMap.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "初始化失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryDevice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		    Integer currentPage = genIntParameter("currentPage", request);
			Map<String, Object> queryParam = super.initParamMap(request, currentPage != null, StringUtils.EMPTY);
			Page<TabDataDevice> page = deviceService.queryForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	@RequestMapping(value = { "/bu/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryBuDevice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, false);
			Page<TabDataDevice> page = deviceService.queryForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> addDevice(@RequestBody TabDataDevice device, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			
			// 默认状态
			setDefaultValue(request, device, false);
			deviceService.toAdd(device);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "新增失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> updateDevice(@RequestBody TabDataDevice device, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			// 获取该数据对象
			setDefaultValue(request, device, true);
			deviceService.toUpdate(device);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "更新失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> delDevice(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String deviceId = request.getParameter("deviceId");
		try {
			Account account = WebContextUtils.genUser(request);
			Long loginUserId = account.getId();
			deviceService.toChangeStatus(deviceId, BaseObject.StatusEnum.DELETE.getCode(), loginUserId);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/status" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> changeStatus(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String deviceId = request.getParameter("deviceId");
		Integer status = genIntParameter("status", request);
		try {
			Account account = WebContextUtils.genUser(request);
			Long loginUserId = account.getId();
			deviceService.toChangeStatus(deviceId, status, loginUserId);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "更改状态失败: "+ e.getMessage());
		}

		return resultMap;
	}
}
