package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.ReportOrderPerformanceService;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.ReportOrderPerformanceService;

/**
 * @ClassName: BusinessOrderPerformanceReportTask
 * @Description: 课程顾问绩效表
 * @author ohs ohs@klxuexi.org
 * @date 2017年8月25日 上午11:38:49
 *
 */
public class BusinessOrderPerformanceReportTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		ReportOrderPerformanceService businessOrderPerformanceService = (ReportOrderPerformanceService) ApplicationContextUtil
				.getBean("reportOrderPerformanceService");
		businessOrderPerformanceService.updateByTaskFlow(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_ORDER_PERFORMANCE";
	}

	@Override
	protected String getTableKey() {
		return "BUSINESS_DATE";
	}

}
