package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.common.service.ReportAccountFlowService;
import com.edu.report.api.BaseReportTask;

/**
 * 账户流水报表任务
 * 
 * @author lincm
 *
 */
public class TAccountFlowTask extends BaseReportTask {
    private ReportAccountFlowService accountFlowService = (ReportAccountFlowService) ApplicationContextUtil
            .getBean("reportAccountFlowService");

    @Override
    public void run(String taskId,String taskFlow, Long pageSize, String startId) throws Exception {
        setTableInfo(ReportAccountFlowService.TABLE, ReportAccountFlowService.TABLE_KEY,
                ReportAccountFlowService.TABLE_TYPE);
        calculateOperateNo(taskId,taskFlow, pageSize, startId);
        try {
            this.accountFlowService.addReportAccountFlow(taskFlow, minOperateNo, maxOperateNo);
        } catch (Exception e) {
            //this.taskTableResultService.deleteByTaskFlow(taskFlow);
        	e.printStackTrace();
        	throw new Exception(e);
        }
    }

}
