package com.edu.erp.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.erp.model.CourseYDYInfo;
import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.student.service.StudentCourseService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping(value = { "/studentservice" })
public class StudentCourseController extends BaseController {
	private static final Logger log = Logger
			.getLogger(StudentCourseController.class);

	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name = "studentCourseService")
	private StudentCourseService studentCourseService;

	@ResponseBody
	@RequestMapping(value = { "/orderCourse" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryOrderCourses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".equals(orgModel.getType())) {
				log.error("未选择校区!");
				throw new Exception("请选择校区！");
			}

			Map<String, Object> queryParam = this.initParamMap(request, false, StringUtils.EMPTY);
			if (queryParam.get("studentId") == null || queryParam.get("businessType") == null) {
				log.error("查询参数不正确！");
				throw new Exception("查询参数不正确！");
			}

			Long isAllBranch= genLongParameter("isAllBranch", request);
			queryParam.put("branchId", (isAllBranch != null && isAllBranch == 1) ? null : orgModel.getId());

			List<TOrderCourse> studentCourse = orderService
					.queryStudentOrderCourse(queryParam);
			resultMap.put("error", false);
			resultMap.put("data", studentCourse);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}


	@ResponseBody
	@RequestMapping(value = { "/ydy" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryStudentYDY(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择查询校区或团队！");
				return resultMap;
			}
			
			Long buId = orgModel.getBuId();
			Long studentId = genLongParameter("studentId", request);
			Long businessType = genLongParameter("businessType", request);
			Long month = genLongParameter("month", request);

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("buId", buId);
			queryParam.put("studentId", studentId);
			queryParam.put("businessType", businessType);
			queryParam.put("month", month);

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

			Page<CourseYDYInfo> studentYDY = studentCourseService
					.queryStudentYDY(queryParam);
			resultMap.put("error", false);
			resultMap.put("data", studentYDY.toArray());
			resultMap.put("total", studentYDY.getTotal());
			resultMap.put("totalPage", studentYDY.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

}
