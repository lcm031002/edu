package com.edu.erp.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.erp.common.service.RegionService;
import com.edu.erp.dict.controller.GradeController;
import com.edu.erp.model.TabRegion;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.StringUtil;

/***
 * Description : 全国城市联动
 * 
 * Author : junli.zhang
 * 
 * Date : 2014-08-29
 */
@Controller
@RequestMapping("/common/region")
public class RegionController extends BaseController {

	private static final Logger log = Logger.getLogger(GradeController.class);
	
	@Resource(name="regionService")
	private RegionService regionService;

	/**
	 * 根据父节点获取子节点
	 * @param  	parentId  					父节点Id
	 * @return	List<Map<String, String>>
	 */
	@ResponseBody
	@RequestMapping(value = {"queryRegionList"}, method = RequestMethod.GET, headers = {"Accept=application/json"})
	public Map<String, Object> queryRegionList(@RequestParam("id") Long parentId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			parentId = StringUtil.isEmpty(parentId) ? 0L : parentId;
			List<Map<String, String>> list = regionService.queryRegionListByParentId(parentId);
			resultMap.put("error", false);
			resultMap.put("data", list);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		
		return resultMap;
	}
	/**
	 * 查询城市列表
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"list"}, method = RequestMethod.GET, headers = {"Accept=application/json"})
	public Map<String, Object> list(@RequestBody Map<String, Object> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<TabRegion> regionList = regionService.list(paramMap);
			resultMap.put("error", false);
			resultMap.put("data", regionList);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
}
