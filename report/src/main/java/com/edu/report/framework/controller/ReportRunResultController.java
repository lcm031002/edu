package com.edu.report.framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.model.TReportRunResult;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.model.TReportRunResult;
import com.edu.report.util.BaseController;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/taskrun")
public class ReportRunResultController extends BaseController {
    @Resource(name = "reportRunResultService")
    private ReportRunResultService reportRunResultService;
    
    @Resource(name = "reportTaskSettingsService")
    private ReportTaskSettingsService reportTaskSettingsService;

    @RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
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

            Page<TReportRunResult> page = this.reportRunResultService.selectForPage(paramMap);
            setRespDataForPage(request, page, resultMap);

        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }
    
    @RequestMapping(value = { "/execute" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> executeTask(@RequestBody JSONObject jsonObj, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //getOrgModel(request);
            String taskFlow = jsonObj.getString("taskFlow");
            String taskId = jsonObj.getString("taskId");
            if (StringUtils.isEmpty(taskFlow)) {
                throw new Exception("没有任务批次号，运行失败！");
            }
            
            if (StringUtils.isEmpty(taskId)) {
                throw new Exception("没有任务编号，运行失败！");
            }
            this.reportTaskSettingsService.runTask(taskFlow, taskId);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

}
