package com.edu.erp.common.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.common.service.DataSchoolService;
import com.edu.erp.dict.service.SelectOptionService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.DataSchool;
import com.edu.erp.model.TPSchoolType;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ModelDataUtils;
import com.github.pagehelper.Page;

/**
 * 
 * @ClassName: DataSchoolController
 * @Description: 就读学校控制器
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月20日 下午8:37:37
 * 
 */
@Controller
@RequestMapping(value = { "/common/dataschool" })
public class DataSchoolController extends BaseController{
	private static final Logger log = Logger.getLogger(DataSchoolController.class);
	
	@Resource(name = "dataSchoolService")
	private DataSchoolService dataSchoolService;

	@Resource(name = "selectOptionService")
	private SelectOptionService selectOptionService;

	/**
	 * 查询学校List
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		List<DataSchool> schoolList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("school_name", request.getParameter("school_name"));
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			paramMap.put("org_city_id", orgModel.getCityId());
			schoolList = dataSchoolService.list(paramMap);
			// 第一条为待定
			paramMap.clear();
			paramMap.put("org_city_id", orgModel.getCityId());
			paramMap.put("row_num", 1);
			paramMap.put("_school_name", "待定");
			for (DataSchool school : dataSchoolService.list(paramMap)) {
				schoolList.add(0, school);
			}

			result.put("error", false);
			result.put("data", schoolList);
		} catch (Exception e) {
			System.err.println(e);
			result.put("error", true);
			result.put("message", "查询失败！see:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 全日制学校的分页查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> querySchoolPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {			
			Map<String, Object> queryParam = initParamMap(request, true);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			queryParam.put("org_city_id", orgModel.getCityId());
			Page<Map<String, Object>> page = dataSchoolService.queryPage(queryParam);
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
	 * 查询弹出框的下拉列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "dialog" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {			
			List<TPSchoolType> schoolTypeList= selectOptionService.selectSchoolType(null);
			resultMap.put("error", false);
			resultMap.put("schoolTypeList", schoolTypeList);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 新增学校信息
	 * @param school
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addSchool(@RequestBody DataSchool school,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			setDefaultValue(request, school, false);
			school.setOrg_city_id(orgModel.getCityId());
			dataSchoolService.toAdd(school);
			resultMap.put("error", false);
			resultMap.put("message", "新增成功!");
		} catch (Exception e) {
			resultMap.put("error", true);
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	/**
	 * 更新学校信息
	 * @param school
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateSchool(@RequestBody DataSchool school,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			setDefaultValue(request, school, true);
			dataSchoolService.toUpdate(school);
			resultMap.put("error", false);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSchool(@RequestParam String school_ids,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			dataSchoolService.toChangeStatus(school_ids,BaseObject.StatusEnum.DELETE.getCode());
			resultMap.put("error", false);
			resultMap.put("message", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "删除失败！see:" + e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/changeStatus" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> updateSchoolStatus(@RequestParam String id,@RequestParam Integer status,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			dataSchoolService.toChangeStatus(id,status);
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
