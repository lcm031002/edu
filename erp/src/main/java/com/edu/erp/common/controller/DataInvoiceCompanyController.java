package com.edu.erp.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.erp.common.service.DataInvoiceCompanyService;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.DataInvoiceCompany;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;

/**
 * 课程季Controller
 * 
 * @author lpe
 * @date 2014-8-6
 */
@Controller
@RequestMapping("/common/invoicecompany")
public class DataInvoiceCompanyController extends BaseController {
	private static final Logger log = Logger
			.getLogger(DataInvoiceCompanyController.class);
	// 注入Service
	@Resource(name = "dataInvoiceCompanyService")
	private DataInvoiceCompanyService dataInvoiceCompanyService;

	/**
	 * 查询课程季
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = initParamMap(request, true);
			Page<DataInvoiceCompany> page = dataInvoiceCompanyService
					.queryPage(paramMap);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/list" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = initParamMap(request, false);
			List<DataInvoiceCompany> invoiceCmpList = dataInvoiceCompanyService
					.queryList(paramMap);
			resultMap.put("error", false);
			resultMap.put("data", invoiceCmpList);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody DataInvoiceCompany dataInvoiceCompany,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			check(dataInvoiceCompany);
			setDefaultValue(request, dataInvoiceCompany, false);
			dataInvoiceCompanyService.save(dataInvoiceCompany);
			result.put("error", false);
			result.put("data", dataInvoiceCompany);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updateTimeSeason(@RequestBody DataInvoiceCompany dataInvoiceCompany,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			check(dataInvoiceCompany);
			setDefaultValue(request, dataInvoiceCompany, true);
			dataInvoiceCompanyService.update(dataInvoiceCompany);
			result.put("error", false);
			result.put("data", dataInvoiceCompany);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/changeStatus" }, headers = { "Accept=application/json" }, method = RequestMethod.DELETE)
	public Map<String, Object> changeStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String flag = request.getParameter("flag");
			if (StringUtils.isEmpty(id)) {
				result.put("error", true);
				result.put("message", "请选择数据");
				return result;
			}
			Integer status = Constants.DELETE.equals(flag) ? BaseObject.StatusEnum.DELETE
					.getCode()
					: (Constants.INVALID.equals(flag) ? BaseObject.StatusEnum.INVALID
							.getCode() : BaseObject.StatusEnum.VALID.getCode());
			dataInvoiceCompanyService.changeStatus(id, status);
			result.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	private void check(DataInvoiceCompany invoiceCompany) throws Exception {
		if (StringUtils.isEmpty(invoiceCompany.getCompany_name())) {
			throw new Exception("请输入单位名称");
		}
	}
}