package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.AttendanceMonthService;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.AttendanceMonthService;

/**
 * @ClassName: AttendanceMonthReportTask
 * @Description: 月度消耗表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年6月16日 上午11:38:49
 *
 */
public class AttendanceMonthReportTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		AttendanceMonthService attendanceMonthService = (AttendanceMonthService) ApplicationContextUtil
				.getBean("attendanceMonthService");
		attendanceMonthService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_MONTH_ATTEND_REPORT";
	}

	@Override
	protected String getTableKey() {
		return "BUSI_DATE";
	}

}
