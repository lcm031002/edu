package com.edu.report.framework.tasks.common;

import java.util.HashMap;
import java.util.Map;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.IReportTask;
import com.edu.report.web.common.service.ReportAccountService;

/**
 * @ClassName: TAccountTask
 * @Description: 学员账户余额表任务
 *
 */
public class TAccountTask implements IReportTask {

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId) throws Exception {
		ReportAccountService reportAccountService = (ReportAccountService) ApplicationContextUtil
				.getBean("reportAccountService");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("task_flow", taskFlow); //任务批次
		
		reportAccountService.updateReport(param);
	}

}
