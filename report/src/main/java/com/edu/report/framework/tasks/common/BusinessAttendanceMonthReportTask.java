package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;

/**
 * @ClassName: BusinessAttendanceMonthReportTask
 * @Description: 月度分类考勤表
 *
 */
public class BusinessAttendanceMonthReportTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		BusinessAttendanceMonthService businessAttendanceMonthService = (BusinessAttendanceMonthService) ApplicationContextUtil
				.getBean("businessAttendanceMonthService");
		businessAttendanceMonthService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_MONTH_BUSATTEND_REPORT";
	}

	@Override
	protected String getTableKey() {
		return "BUSI_DATE";
	}

}
