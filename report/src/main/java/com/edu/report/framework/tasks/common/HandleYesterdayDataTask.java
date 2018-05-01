package com.edu.report.framework.tasks.common;

import java.util.List;

import com.edu.report.api.BaseReportTask;
import com.edu.report.model.TTaskTableResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.api.IReportTask;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;

public class HandleYesterdayDataTask extends BaseReportTask {

    private TaskTableResultService taskTableResultService = (TaskTableResultService) ApplicationContextUtil
            .getBean("taskTableResultService");

    @Override
    public void run(String taskId, String taskFlow, Long pageSize, String startId) throws Exception {
        List<TTaskTableResult> taskTableResultList = this.taskTableResultService.queryYesterdayTaskFlows();
        if (CollectionUtils.isNotEmpty(taskTableResultList)) {
            for (TTaskTableResult taskTableResult : taskTableResultList) {
                if (StringUtils.isNotEmpty(taskTableResult.getClassImpl())) {
                    Object classImpl = Class.forName(taskTableResult.getClassImpl().trim()).newInstance();
                    if (classImpl instanceof IReportTask) {
                        // 执行报表任务
                        IReportTask reportTask = (IReportTask) classImpl;
                        reportTask.run(taskTableResult.getTaskId(), taskTableResult.getTaskFlow(),
                                taskTableResult.getPageSize(), taskTableResult.getStartId());
                    }
                }
            }
        }
    }

}
