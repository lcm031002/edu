/**
 * @Title: SubjectController.java
 * @Package: com.edu.erp.dict.controller
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月3日 下午8:00:45
 * @version KLXX ERPV5.0
 */
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

/**
 * @ClassName: SubjectController
 * @Description:
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月3日 下午8:00:45
 * 
 */
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
			Map<String, Object> param = new HashMap<String, Object>();
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			
			param.put("bu_id", orgModel.getBuId());
			param.put("branch_id", genLongParameter("branch_id", request));
			param.put("grade_id", genLongParameter("grade_id", request));
			param.put("season_id", genLongParameter("season_id", request));
			param.put("subject_name", request.getParameter("subjectName")); //科目名称
	
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

			Page<TPSubject> result = subjectService.queryDataList(param);
			resultMap.put("error", false);
			resultMap.put("data", result.toArray());
			resultMap.put("total", result.getTotal());
			resultMap.put("totalPage", result.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);
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
			Map<String, Object> param = new HashMap<String, Object>();
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("请选择校区！");
			}
			
			param.put("bu_id", orgModel.getBuId());
			param.put("branch_id", genLongParameter("branch_id", request));
			param.put("grade_id", genLongParameter("grade_id", request));
			param.put("season_id", genLongParameter("season_id", request));
			param.put("subject_name", request.getParameter("subjectName")); //科目名称

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
			Long bu_id = genLongParameter("bu_id",request);
			param.put("bu_id", bu_id==null?orgModel.getBuId():bu_id);
			
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
			subject.setStatus(BaseObject.StatusEnum.VALID.getCode());
			subject.setCity_id(orgModel.getCityId());
			Account account = WebContextUtils.genUser(request);
			subject.setCreate_user(account.getId());
			subject.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			
			Map<String, Object> buRel = new HashMap<String, Object>();
			buRel.put("bu_id", orgModel.getBuId());
			buRel.put("dict_type", "tp_subject");
			
			subjectService.save(subject, buRel);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append(subject.toString());
//			logUtil.LogOperate("科目-添加", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "新增失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append(subject.toString());
//			buff.append("，");
//			buff.append("失败信息：");
//			buff.append(e);
//			logUtil.LogOperate("科目-添加", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "失败");
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
			// 获取该数据对象
			TPSubject data = subjectService.queryData(String.valueOf(subject.getId()));
			// 更新科目描述
			data.setDescription(subject.getDescription());
			// 更新编码
			data.setEncoding(subject.getEncoding());
			// 更新科目名称
			data.setName(subject.getName());
			Account account = WebContextUtils.genUser(request);
			data.setUpdate_user(account.getId());
			data.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			// 执行更新
			subjectService.updateData(data);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append(subject.toString());
//			logUtil.LogOperate("科目-修改", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "更新失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append(subject.toString());
//			buff.append("，");
//			buff.append("失败信息：");
//			buff.append(e);
//			logUtil.LogOperate("科目-修改", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "失败");
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
			param.put("bu_id", orgModel.getBuId());
			param.put("dict_id", subjectId);
			subjectService.deleteData(param);
			resultMap.put("error", false);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append("ID：");
//			buff.append(subjectId);
//			logUtil.LogOperate("科目-删除", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
//			StringBuffer buff  = new StringBuffer();
//			buff.append("ID：");
//			buff.append(subjectId);
//			buff.append("，");
//			buff.append("失败信息：");
//			buff.append(e);
//			logUtil.LogOperate("科目-删除", logUtil.subDetailInfo(buff.toString()), logUtil.genUserInfo(request), "失败");
		}
		return resultMap;
	}
}
