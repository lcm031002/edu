package com.edu.erp.student.controller;

import java.util.ArrayList;
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
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.model.StudentContact;
import com.edu.erp.model.StudentContact.RelationEnum;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.student.service.StudentContactService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ModelDataUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 学生联系方式
 * 
 */
@Controller
@RequestMapping(value = { "/studentservice/student/contact" })
public class StudentContactController extends BaseController {
	private static final Logger log = Logger
			.getLogger(StudentContactController.class);

	@Resource(name = "studentContactService")
	private StudentContactService studentContactService;
	
	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	/**
	 * 学生联系方式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	/*
	 * @RequestMapping public ModelAndView init(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { ModelAndView
	 * modelAndView = new ModelAndView( "web/student_manager/student_contact");
	 * try { Map<String, Object> param = new HashMap<String, Object>();
	 * param.put("student_id", 26); List<StudentContact> studentContactList =
	 * studentContactService .list(param);
	 * modelAndView.addObject("studentContactList", studentContactList); } catch
	 * (Exception e) { System.err.println(e); } return modelAndView; }
	 */

	/**
	 * 
	 * 联系人查询
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> page(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("student_id",
					genLongParameter("student_id", request));
			queryParam.put("is_valid", 1);

			// 当前页数
			Integer currentPage = genIntParameter("page", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("rows", request);
			if (currentPage == null) {
				currentPage = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);
			Page<StudentContact> list = studentContactService.page(queryParam);
			PageInfo<StudentContact> page = new PageInfo<StudentContact>(list);
			resultMap.put("error", false);
			resultMap.put("data", page.getList());
			resultMap.put("totalPage", page.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 联系方式
	 * 
	 * @call student-center.js
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/list" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> param = new HashMap<String, Object>();
		List<StudentContact> stuContactList = null;
		try {
			param.put("student_id", genLongParameter("student_id", request));
			stuContactList = studentContactService.list(param);
			result.put("error", false);
			result.put("data", stuContactList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", false);
			result.put("message", "查询联系方式失败！see:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 联系人
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/relationList" }, headers = { "Accept=application/json" })
	public Map<String, Object> relationList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> relationList = new ArrayList<Map<String, Object>>(
				RelationEnum.values().length);
		for (RelationEnum relation : RelationEnum.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", relation.getCode());
			map.put("label", relation.getDesc());
			relationList.add(map);
		}
		result.put("error", false);
		result.put("data", relationList);
		return result;
	}

	/**
	 * 新增
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> toAdd(@RequestBody() Map<String, Object> param,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			StudentContact studentContact = ModelDataUtils.getPojoMatch(
					StudentContact.class, param);
			studentContact.setCreate_user(account.getId());
			studentContact.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			studentContact
					.setIs_valid(StudentContact.ValidEnum.VALID.getCode());
			studentContactService.toAdd(studentContact);
			resultMap.put("error", false);
			resultMap.put("data", studentContact);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "保存失败！see:" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 修改
	 * 
	 * @param studentContact
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> toUpdate(
			@RequestBody StudentContact studentContact,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			studentContact.setUpdate_user(account.getId());
			studentContact.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
			
			studentContactService.toUpdate(studentContact);
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "修改失败！see:" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 删除
	 * 
	 * @param contact_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> toRemove(@RequestParam(required=true) Long contact_id,@RequestParam(required=true) Long studentId,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//查询学生信息
			StudentInfo studentInfo = studentInfoService.queryStudentById(studentId);
			if(studentInfo == null) {
				throw new RuntimeException("通过id:" + studentId + "查找不到学生信息");
			}
			if(contact_id.equals(studentInfo.getContact_id())){
				throw new RuntimeException("该电话号码已经绑定到学员，不允许删除");
			}
			studentContactService.toChangeStatus(contact_id.toString(),StudentContact.ValidEnum.INVALID.getCode());
			resultMap.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", "删除失败！see:" + e.getMessage());
		}
		return resultMap;
	}
}
