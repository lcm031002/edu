package com.edu.erp.dict.controller;

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
import com.edu.erp.dict.service.DataGradeService;
import com.edu.erp.model.Grade;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ModelDataUtils;
import com.github.pagehelper.Page;

/**
 * @ClassName: GradeController
 * @Description: 年级服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月14日 下午5:05:13
 * 
 */
@Controller
@RequestMapping(value = { "/dictionary/grade" })
public class GradeController extends BaseController {
	private static final Logger log = Logger.getLogger(GradeController.class);

	@Resource(name = "dataGradeService")
	private DataGradeService dataGradeService;
	/**
	 * 查詢年級信列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryGrade(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		if (orgModel == null || orgModel.getBuId() == null) {
			resultMap.put("error", true);
			resultMap.put("message", "请选择校区!");
			return resultMap;
		}
		param.put("org_city_id", orgModel.getCityId());
		param.put("branch_id", genLongParameter("branch_id", request));
		param.put("season_id", genLongParameter("season_id", request));
		param.put("grade_name", request.getParameter("grade_name"));

		List<Grade> result = dataGradeService.list(param);
		resultMap.put("error", false);
		resultMap.put("data", result);
		return resultMap;
	}
	
	/**
	 * 查询年级的分页信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryGradePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {			
			Map<String, Object> queryParam = initParamMap(request, true);
			Page<Grade> page = dataGradeService.queryPage(queryParam);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 新增年級信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addGrade(@RequestBody Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			Grade grade = ModelDataUtils.getPojoMatch(Grade.class, param);
			setDefaultValue(request, grade, false);
			grade.setCity_id(orgModel.getCityId());
			dataGradeService.toAdd(grade);
			resultMap.put("error", false);
			resultMap.put("message", "新增成功!");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "添加错误!"+e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}
	/**
	 * 更新年級信息
	 * @param grade
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateGrade(
			@RequestBody Grade grade,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			setDefaultValue(request, grade, true);
			dataGradeService.toUpdate(grade);
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
	 * 删除年級信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> delGrade(@RequestParam String grade_ids,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		try {
			param.put("bu_id", orgModel.getBuId());
			param.put("dict_id", grade_ids);
			dataGradeService.deleteData(param);
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
	 * 修改年级状态
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/changeStatus" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> updateGradeStatus(@RequestParam String id,@RequestParam Integer status,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		try {
			param.put("bu_id", orgModel.getBuId());
			param.put("dict_id", id);
			dataGradeService.deleteData(param);
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
