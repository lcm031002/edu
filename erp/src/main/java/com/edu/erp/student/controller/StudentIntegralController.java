package com.edu.erp.student.controller;

import com.edu.common.util.ERPConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.common.util.DateUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.model.StudentIntegral;
import com.edu.erp.model.StudentIntegralDetails;
import com.edu.erp.student.service.StudentIntegralService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ReportWriteExcelHandler;
import com.github.pagehelper.Page;

@Controller
@RequestMapping(value = { "/student" })
public class StudentIntegralController extends BaseController {

	private static final Logger log = Logger.getLogger(StudentIntegralController.class);

	@Resource(name = "studentIntegralService")
	private StudentIntegralService studentIntegralService;

	@RequestMapping(value = { "/integral" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryStudentIntegral(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = super.initParamMap(request, false);
			List<StudentIntegral> list = studentIntegralService.queryStudentIntegral(queryParam);

			resultMap.put("data", list);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/integral_flow" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryStudentIntegralFlow(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			Page<StudentIntegralDetails> page = studentIntegralService.queryStudentIntegralDetails(queryParam);

			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = { "/integral_output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputIntegralFlow(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = super.initParamMap(request, false);
			Page<StudentIntegralDetails> page = studentIntegralService.queryStudentIntegralDetails(queryParam);

			if (page == null || CollectionUtils.isEmpty(page.getResult())) {
				throw new Exception("导出失败：没有可导出数据！");
			}

			String rootPath = request.getSession().getServletContext().getRealPath("//");
			// 模板文件名
			String templateFileName = "integral_flow.xlsx";
			// 临时文件名
			String tempFileName = "积分流水_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
			ReportWriteExcelHandler.writeDataToExcel(page.getResult(), rootPath, templateFileName,
				tempFileName);
			resultMap.put(Constants.RespMapKey.DATA, tempFileName);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}
}
