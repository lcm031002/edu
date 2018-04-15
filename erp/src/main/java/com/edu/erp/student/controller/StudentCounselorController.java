package com.edu.erp.student.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.Constants;
import com.edu.erp.util.*;
import com.edu.common.util.ERPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.model.EmpOrgPost;
import com.edu.erp.model.StudentCounselorInfo;
import com.edu.erp.student.service.StudentCounselorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 咨询学管
 * 
 * @author wCong
 * 
 */
@Controller
@RequestMapping(value = { "/studentservice/student/counselor" })
public class StudentCounselorController extends BaseController {

	private static final Logger log = Logger.getLogger(StudentCounselorController.class);

	@Resource(name = "studentCounselorService")
	private StudentCounselorService studentCounselorService;

	/**
	 * 学生联系方式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("web/student_manager/");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = { "page" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> page(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || null == orgModel.getBuId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区或校区！");
				return resultMap;
			}

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam = initParamMap(request, true);
			Account account = WebContextUtils.genUser(request);
			if (account != null) {
				queryParam.put("counselor_id", account.getEmployeeId());
			}
			queryParam.put("bu_id",orgModel.getBuId());
			Page<StudentCounselorInfo> page = studentCounselorService.page(queryParam);
			resultMap.put("error", false);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 查询可以转移的学管师列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/distribute/to" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryToDistributeConselor(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (!"4".equals(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			Map<String, Object> param = initParamMap(request, true);
			Page<EmpOrgPost> page = studentCounselorService.selectForToPage(param);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found ,see:", e);
			resultMap.put("error", true);
		}
		return resultMap;
	}

	@RequestMapping(value = { "/distribute/from" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryFromDistributeConselor(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (!"4".equals(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			Map<String, Object> param = initParamMap(request, true);
			Page<EmpOrgPost> page = studentCounselorService.selectForFromPage(param);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found ,see:", e);
			resultMap.put("error", true);
		}
		return resultMap;
	}

	/**
	 * 根据学管师ID查询管理的学生
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/distribute" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryStudentList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (!"4".equals(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			Map<String, Object> param = initParamMap(request, true);
			Page<StudentCounselorInfo> page = studentCounselorService.queryStudentListPage(param);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found ,see:", e);
			resultMap.put("error", true);
		}
		return resultMap;
	}

	/**
	 * 批量转移学员，把某个学管师名下的学员批量转移给另外一个学管师
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/distribute/batch" }, headers = {
			"Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> toDistributeBatch(@RequestBody() Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", true);
		try {
			String p_from_conselor_id = (param.get("p_from_conselor_id") == null) ? StringUtils.EMPTY : param.get("p_from_conselor_id").toString();
			String p_to_conselor_id = (param.get("p_to_conselor_id") == null) ? StringUtils.EMPTY : param.get("p_to_conselor_id").toString();
			if (StringUtils.isEmpty(p_from_conselor_id)) {
				log.error("要转移的学管师为空");
				resultMap.put("message", "没有选择转入学管师");
				return resultMap;
			}
			if (StringUtils.isEmpty(p_to_conselor_id)) {
				log.error("要转移的学管师为空");
				resultMap.put("message", "没有选择学管师");
				return resultMap;
			}

			if (p_from_conselor_id.equals(p_to_conselor_id)) {
				log.error("转入和转出学管师为同一个人，转出失败！");
				resultMap.put("message", "转入和转出学管师为同一个人，转出失败！");
				return resultMap;
			}

			studentCounselorService.toBatchTransfer(p_from_conselor_id, p_to_conselor_id);
			//记录日志
			StringBuffer buff  = new StringBuffer();
			buff.append("转移前[学管师id:").append(p_from_conselor_id);
			buff.append("]转移后[学管师id:").append(p_to_conselor_id);
			buff.append("]");
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "学员分配失败");
		}
		return resultMap;

	}

	/**
	 * 单个学生分配 修改学生的归属学管师 把原来的学生归属信息置为无效，在新增一条学生归属信息，conselor_id为新的学管id
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/distribute" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> toDistribute(@RequestBody() Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", true);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}

			String newConselorId = (param.get("new_counselor_id") == null) ? StringUtils.EMPTY : param.get("new_counselor_id").toString();
			String stu_conselor_id = (param.get("stu_counselor_id") == null) ? StringUtils.EMPTY : param.get("stu_counselor_id").toString();
			if (StringUtils.isEmpty(newConselorId)) {
				log.error("要转移的学管师为空");
				resultMap.put("message", "没有选择转入学管师");
				return resultMap;
			}
			if (StringUtils.isEmpty(stu_conselor_id)) {
				log.error("要转移的学员为空");
				resultMap.put("message", "没有选择学员");
				return resultMap;
			}

			if (newConselorId.equals(stu_conselor_id)) {
				log.error("转入和转出学管师为同一个人，转出失败！");
				resultMap.put("message", "转入和转出学管师为同一个人，转出失败！");
				return resultMap;
			}

			studentCounselorService.toTransfer(stu_conselor_id, newConselorId);
			//记录日志
			StringBuffer buff  = new StringBuffer();
			buff.append("转移前[学生学管师id:").append(stu_conselor_id);
			buff.append("]转移后[新学管师id:").append(newConselorId);
			buff.append("]");
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 新增学管师和咨询师
	 * 
	 * <pre>
	 * 		产品线：个性化
	 * 		业务场景：
	 * 			1：个性化中一个学员只有一个咨询师，如果新添加的咨询师的起止时间和上一个咨询师的起止时间有交集，则先前的咨询师会被删除
	 * 			2：个性化中，一个学员在不同的校区上课，则每一个校区都存在一个学管师，当在同一个校区添加学管师，
	 * 			        如果添加的学管师的起止时间和上一个学管师的起止时间有交集，则先前的学管师会被删除
	 * </pre>
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> toAdd(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StudentCounselorInfo studentCounselorInfo = ModelDataUtils.getPojoMatch(StudentCounselorInfo.class, param);
			Account account = WebContextUtils.genUser(request);
			studentCounselorInfo.setCreate_user(account.getId());
			studentCounselorInfo.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentCounselorInfo.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentCounselorInfo.setUpdate_user(account.getId());

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			studentCounselorInfo.setBu_id(orgModel.getBuId());
			studentCounselorInfo.setBranch_id(orgModel.getId());
			studentCounselorService.toAdd(studentCounselorInfo);
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "新增失败！see:" + e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 修改学管师和咨询师
	 * 
	 * <pre>
	 * 		产品线：个性化
	 * 		业务场景：
	 * 			1：个性化中一个学员只有一个咨询师，如果新添加的咨询师的起止时间和上一个咨询师的起止时间有交集，则先前的咨询师会被删除
	 * 			2：个性化中，一个学员在不同的校区上课，则每一个校区都存在一个学管师，当在同一个校区添加学管师，
	 * 			        如果添加的学管师的起止时间和上一个学管师的起止时间有交集，则先前的学管师会被删除
	 * </pre>
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> toUpdate(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StudentCounselorInfo studentCounselorInfo = ModelDataUtils.getPojoMatch(StudentCounselorInfo.class, param);
			Account account = WebContextUtils.genUser(request);
			studentCounselorInfo.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentCounselorInfo.setUpdate_user(account.getId());
			
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}
			studentCounselorInfo.setBu_id(orgModel.getBuId());
			studentCounselorInfo.setBranch_id(orgModel.getId());
			studentCounselorService.toUpdate(studentCounselorInfo);
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "新增失败！see:" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: queryCounselorInfo
	 * @Description: 查询当前的咨询师与学管师
	 * @param request
	 * @param response
	 * @return 设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryCounselorInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			param.put("student_id", genLongParameter("student_id", request));
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}
			param.put("bu_id", orgModel.getBuId());
			//param.put("branch_id", orgModel.getId());

			param.put("employee_name", request.getParameter("employee_name"));
			Long counselor_type = genLongParameter("counselor_type", request);
			if (counselor_type == null) {
				counselor_type = 1L;
			}
			param.put("cur_date", request.getParameter("cur_date"));

			param.put("counselor_type", counselor_type);

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

			Page<StudentCounselorInfo> ret = studentCounselorService.list(param);
			PageInfo<StudentCounselorInfo> page = new PageInfo<StudentCounselorInfo>(ret);
			result.put("error", false);
			result.put("data", page.getList());
			result.put("total", page.getTotal());
			result.put("totalPage", page.getPages());
			result.put("currentPage", currentPage);
			result.put("pageSize", pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "查询失败！see:" + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/exportExcel" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> exportExcel(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam = initParamMap(request, false);
			Account account = WebContextUtils.genUser(request);
			if (account != null) {
				queryParam.put("counselor_id", account.getEmployeeId());
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || null == orgModel.getBuId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区或校区！");
				return resultMap;
			}
			queryParam.put("bu_id",orgModel.getBuId());
			Page<StudentCounselorInfo> page = studentCounselorService.page(queryParam);
			if (page == null || CollectionUtils.isEmpty(page.getResult())) {
				throw new Exception("导出失败：没有导出数据！");
			}

			String rootPath = request.getSession().getServletContext().getRealPath("//");
			// 模板文件名
			String templateFileName = "counselorStudent.xls";
			// 临时文件路径
			String tempFilePath = rootPath + File.separator + "temp";
			// 临时文件名
			String tempFileName = "counselor_student_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
			ReportWriteExcelHandler.writeDataToExcel(page.getResult(), rootPath, templateFileName, tempFileName);
			resultMap.put(Constants.RespMapKey.DATA, tempFileName);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "导出失败：" + e.getMessage());
		}

		return resultMap;
	}
}
