package com.edu.erp.dict.controller;

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
import com.edu.erp.dict.service.SubjectService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.TPSubject;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value = { "/dictionary/subject" })
public class SubjectController extends BaseController {

	private static final Logger log = Logger.getLogger(SubjectController.class);
	
	@Resource(name = "subjectService")
	private SubjectService subjectService;

	/**
	 * 查询科目
	 * @Title: querySubject
	 * @Description: 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *      设定文件 Map<String,Object> 返回类型
	 *
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> querySubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			Map<String, Object> param = initParamMap(request, true, StringUtils.EMPTY);
			Page<TPSubject> result = subjectService.queryDataList(param);
			setRespDataForPage(request, result.getResult(), resultMap);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	/**
	 * inner Join t_course表获取科目
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> querySubjectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}

			Map<String, Object> param = initParamMap(request, false, StringUtils.EMPTY);
			List<TPSubject> result = subjectService.queryList(param);
			resultMap.put("error", false);
			resultMap.put("data", result.toArray());
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	/**
	 * 查询某个团队下的有效科目
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/listSubject" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			param.put("org_city_id", orgModel.getCityId());
			List<TPSubject> result = subjectService.querySubjectListByBuID(param);
			resultMap.put("error", false);
			resultMap.put("data", result.toArray());
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 新增科目
	 * @Title: addSubject
	 * @Description:
	 * @param request
	 * @return
	 * @throws Exception
	 *      设定文件 Map<String,Object> 返回类型
	 *
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addSubject(@RequestBody TPSubject subject, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			
			// 默认状态
			setDefaultValue(request, subject, false);
			subjectService.save(subject);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "新增失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	/**
	 * 更新科目
	 * @Title: updateSubject
	 * @Description: 
	 * @param subject
	 * @param request
	 * @return
	 * @throws Exception
	 *      设定文件 Map<String,Object> 返回类型
	 *
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateSubject(@RequestBody TPSubject subject, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			setDefaultValue(request, subject, true);
			// 执行更新
			subjectService.updateData(subject);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "更新失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delSubject(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String subjectId = request.getParameter("subjectId");
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		try {
			param.put("org_city_id", orgModel.getCityId());
			param.put("dict_id", subjectId);
			subjectService.deleteData(param);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
}
