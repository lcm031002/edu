package com.edu.report.framework.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.ReportTaskSettingsDao;
import com.edu.report.framework.ReportTaskConfig;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.framework.cfg.Task;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TReportTaskSettings;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.report.api.IReportTask;
import com.edu.report.dao.ReportTaskSettingsDao;
import com.edu.report.framework.ReportTaskConfig;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.framework.cfg.Task;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TReportTaskSettings;
import com.edu.report.util.TaskFlowUtils;

/**
 * @ClassName: ReportTaskSettingsServiceImpl
 * @Description: 报表任务配置服务
 *
 */
@Service(value = "reportTaskSettingsService")
public class ReportTaskSettingsServiceImpl implements ReportTaskSettingsService {
    private static final Logger log = Logger.getLogger(ReportTaskSettingsServiceImpl.class);

    @Resource(name = "reportTaskSettingsDao")
    private ReportTaskSettingsDao reportTaskSettingsDao;

    @Resource(name = "reportRunResultService")
    ReportRunResultService reportRunResultService;

    /*
     * (non-Javadoc)
     * 
     * @see com.edu.report.framework.service.ReportTaskSettingsService#
     * queryReportTaskSettings()
     */
    @Override
    public List<TReportTaskSettings> queryReportTaskSettings() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", 1L);
        return reportTaskSettingsDao.queryReportTaskSettings(paramMap);
    }

    @Override
    public List<TReportTaskSettings> selectForList(Map<String, Object> paramMap) throws Exception {
        return this.reportTaskSettingsDao.queryReportTaskSettings(paramMap);
    }

    @Override
    public void addByReportCfg(ReportCfg reportCfg) throws Exception {
        ReportTaskConfig.genInstance().getCfg();
        if (reportCfg != null && !CollectionUtils.isEmpty(reportCfg.getTasks())) {
            try {
                List<String> taskIdList = reportTaskSettingsDao.queryReportTaskIds(Collections.EMPTY_MAP);
                List<TReportTaskSettings> addTaskSettingList = new ArrayList<TReportTaskSettings>();

                List<Task> taskList = reportCfg.getTasks();
                TReportTaskSettings taskSettings = null;
                for (Task task : taskList) {
                    taskSettings = new TReportTaskSettings();
                    BeanUtils.copyProperties(task, taskSettings, new String[] { "pageSize", "unit", "period", "name" });
                    taskSettings.setPageSize(StringUtils.isEmpty(task.getPageSize()) ? 0 : Long.parseLong(task
                            .getPageSize()));
                    taskSettings
                            .setPeriod(StringUtils.isEmpty(task.getPeriod()) ? 0 : Long.parseLong(task.getPeriod()));
                    taskSettings.setUnit(getUnit(task.getUnit()));
                    taskSettings.setTaskName(task.getName());

                    if (taskIdList.contains(task.getTaskId())) {
                        continue;
                    }
                    addTaskSettingList.add(taskSettings);
                }

                if (!CollectionUtils.isEmpty(addTaskSettingList)) {
                    this.reportTaskSettingsDao.addByReportCfg(addTaskSettingList);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    private Long getUnit(String unit) {
        switch (unit) {
        case "D":
            return 1L;
        case "H":
            return 2L;
        case "M":
            return 3L;
        case "S":
            return 4L;
        case "MS":
            return 5L;

        default:
            return 3L;
        }
    }

    @Override
    public void add(TReportTaskSettings taskSettings) throws Exception {
        checkTaskIdExist(taskSettings);
        this.reportTaskSettingsDao.add(taskSettings);
    }

    @Override
    public void update(TReportTaskSettings taskSettings) throws Exception {
        checkTaskIdExist(taskSettings);
        this.reportTaskSettingsDao.update(taskSettings);
    }

    @Override
    public void changeStatus(TReportTaskSettings taskSettings) throws Exception {
        this.reportTaskSettingsDao.changeStatus(taskSettings);
    }

    private void checkTaskIdExist(TReportTaskSettings taskSettings) throws Exception {
        int count = this.reportTaskSettingsDao.selectCountByTaskId(taskSettings);
        if (count > 0) {
            throw new Exception("任务编号已存在，请重新输入！");
        }
    }

    @Override
    public void runTask(List<TReportTaskSettings> tobeRunTaskList) throws Exception {
        String taskFlow = TaskFlowUtils.genNextFlow();
        for (TReportTaskSettings taskSettings : tobeRunTaskList) {
            if (StringUtils.isEmpty(taskSettings.getClassImpl())) {
                continue;
            }
            runTask(taskFlow, taskSettings);
        }
    }

    @Override
    public void runTask(String taskFlow, String taskId) throws Exception {
        TReportTaskSettings taskSettings = this.reportTaskSettingsDao.queryByTaskId(taskId);
        if (taskSettings == null) {
            throw new Exception("任务编号为：" + taskId + "的配置不存在，运行失败！");
        }
        runTask(taskFlow, taskSettings);
    }

    private void runTask(String taskFlow, TReportTaskSettings taskSettings) throws Exception {
        Long startTime = System.currentTimeMillis();
        TReportRunResult reportRunResult = this.reportRunResultService.queryReportRunResult(taskSettings.getTaskId(),
                taskFlow);
        try {
            // 获取待执行的报表任务
            Long pageSize = taskSettings.getPageSize() == null ? 1000L : taskSettings.getPageSize();
            String startId = taskSettings.getStartId();
            Object classImpl = Class.forName(taskSettings.getClassImpl().trim()).newInstance();
            if (classImpl instanceof IReportTask) {
                // 执行报表任务
                IReportTask reportTask = (IReportTask) classImpl;
                log.debug("--------------------------begin to run task:" + taskSettings.getTaskName()
                        + "------------------------");
                reportTask.run(taskSettings.getTaskId(), taskFlow, pageSize, startId);
                log.debug("--------------------------task run end:" + taskSettings.getTaskName()
                        + "------------------------");
                Long endTime = System.currentTimeMillis();
                // 记录报表执行记录
                if (reportRunResult == null) {
                    reportRunResult = new TReportRunResult();
                }
                
                reportRunResult.setRunResult(1L);
                reportRunResult.setTaskId(taskSettings.getTaskId());
                reportRunResult.setTaskName(taskSettings.getTaskName());
                reportRunResult.setRunTime(DateUtil.getCurrDateTime());
                reportRunResult.setStartTime(Long.parseLong(DateUtil
                        .dateToString(new Date(startTime), "yyyyMMddHHmmss")));
                reportRunResult.setEndTime(Long.parseLong(DateUtil.dateToString(new Date(endTime), "yyyyMMddHHmmss")));
                reportRunResult.setTaskFlow(taskFlow);
                reportRunResultService.saveReportRunResult(reportRunResult);
            }
        } catch (Exception e) {
            log.error("error found,see:", e);
            Long endTime = System.currentTimeMillis();
            // 记录报表执行记录
            if (reportRunResult == null) {
                reportRunResult = new TReportRunResult();
            }
            reportRunResult.setRunResult(0L);
            reportRunResult.setTaskId(taskSettings.getTaskId());
            reportRunResult.setTaskName(taskSettings.getTaskName());
            reportRunResult.setRunTime(DateUtil.getCurrDateTime());
            reportRunResult.setTaskFlow(taskFlow);
            reportRunResult.setStartTime(Long.parseLong(DateUtil.dateToString(new Date(startTime), "yyyyMMddHHmmss")));
            reportRunResult.setEndTime(Long.parseLong(DateUtil.dateToString(new Date(endTime), "yyyyMMddHHmmss")));
            String message = e.getMessage();
            if (message.length() > 300) {
                message = message.substring(0, 300);
            }
            reportRunResult.setRemark(message);
            reportRunResultService.saveReportRunResult(reportRunResult);
            // 任务出错时，先把任务暂停了，排查问题后，手动开启
            // taskSettings.setStatus(0L);
            // this.changeStatus(taskSettings);
        }
    }

}
