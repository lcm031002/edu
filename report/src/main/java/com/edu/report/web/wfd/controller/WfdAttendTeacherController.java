package com.edu.report.web.wfd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.model.TTeacherAttendReport;
import com.edu.report.util.ReportWriteExcelHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.TPerformanceDetails;
import com.edu.report.model.TTeacherAttendReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.UserPermissionService;
import com.edu.report.web.service.PerformanceDetailsService;
import com.edu.report.web.wfd.service.WfdAttendanceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ReportPerformanceDetailsController
 * @Description: 晚辅导教师考勤报表控制类
 *
 */
@Controller
@RequestMapping("/wfd")
public class WfdAttendTeacherController {
	
	@Resource(name = "wfdAttendanceService")
	private WfdAttendanceService wfdAttendanceService;
	
	 @Autowired
    private UserPermissionService userPermissionService;

	@RequestMapping(value = { "/teacher_attendance" }, method = RequestMethod.GET, headers = {
			"Accept=application/json" })
	@ResponseBody
	public Map<String, Object> selectForPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            String bu_id = (String)paramMap.get("bu_id");
            if(StringUtils.isBlank(bu_id)) {
            	 throw new Exception("请选择团队！");
            }
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			List<TTeacherAttendReport> list = this.wfdAttendanceService.queryReport(paramMap);
			resultMap.put("error", false);
			resultMap.put("data", list);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = { "/teacher_attendance/output" }, method = RequestMethod.GET, headers = {
			"Accept=application/json" })
	@ResponseBody
	public Map<String, Object> exportToExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            String bu_id = (String)paramMap.get("bu_id");
            if(StringUtils.isBlank(bu_id)) {
            	 throw new Exception("请选择团队！");
            }
            if(null == paramMap.get("branch_id") || StringUtils.isEmpty(paramMap.get("branch_id").toString()) || paramMap.get("branch_id").equals(-1) ) {
            	Account account = WebContextUtils.genUser(request);
            	String validBranchIds = userPermissionService.queryValidBranchInBu(Long.valueOf(bu_id), account.getId());
            	paramMap.put("validBranchIds", validBranchIds);
            }
			List<TTeacherAttendReport> performanceDetails = this.wfdAttendanceService.queryReport(paramMap);
			if (CollectionUtils.isEmpty(performanceDetails)) {
				throw new Exception("没有可导出的数据");
			}

			String rootPath = request.getSession().getServletContext().getRealPath("//");
			String tempFileName = "晚辅导教师考勤表" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
			// 导出文件目录
			ReportWriteExcelHandler.writeDataToExcelBySxssf(performanceDetails, rootPath, "wfd_teacher_workload_report.xlsx", tempFileName,1);
			resultMap.put("data", tempFileName);
			resultMap.put("error", false);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}
}
