package com.edu.report.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.model.TReportTaskSettings;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.model.TReportTaskSettings;

@Controller
@RequestMapping("/settings")
public class TaskSettingsController {
    @Resource(name = "reportTaskSettingsService")
    private ReportTaskSettingsService reportTaskSettingsService;

    @RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> selectForList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            List<TReportTaskSettings> taskSettings = this.reportTaskSettingsService.selectForList(paramMap);
            resultMap.put("error", false);
            resultMap.put("data", taskSettings);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> add(@RequestBody TReportTaskSettings taskSettings, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            check(taskSettings);
            this.reportTaskSettingsService.add(taskSettings);
            resultMap.put("error", false);
            resultMap.put("data", taskSettings);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> update(@RequestBody TReportTaskSettings taskSettings, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            check(taskSettings);
            this.reportTaskSettingsService.update(taskSettings);
            resultMap.put("error", false);
            resultMap.put("data", taskSettings);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    @RequestMapping(value = { "/changeStatus" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestBody TReportTaskSettings taskSettings, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
            if (null == orgModel || orgModel.getBuId() == null) {
                throw new Exception("请选择校区或团队！");
            }

            this.reportTaskSettingsService.changeStatus(taskSettings);
            resultMap.put("error", false);
            resultMap.put("data", taskSettings);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    private void check(TReportTaskSettings taskSettings) throws Exception {
        if (StringUtils.isEmpty(taskSettings.getTaskId()) || StringUtils.isEmpty(taskSettings.getTaskName())
                || StringUtils.isEmpty(taskSettings.getClassImpl()) || StringUtils.isEmpty(taskSettings.getStartDate())
                || StringUtils.isEmpty(taskSettings.getEndDate()) || StringUtils.isEmpty(taskSettings.getRunTime())
                || StringUtils.isEmpty(taskSettings.getType()) || taskSettings.getUnit() == null
                || taskSettings.getPeriod() == null) {
            throw new Exception("【任务编号、任务名称、处理类、开始日期、截止日期、运行时间、运行周期、单位、类别】不能为空");
        }

        if (taskSettings.getStartDate().compareTo(taskSettings.getEndDate()) > 0) {
            throw new Exception("截止日期不能小于开始日期");
        }
    }
}
