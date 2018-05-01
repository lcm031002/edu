package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.AutoAttendanceReportService;
import com.edu.report.api.AbstactDailyReportTask;

/**
 * @ClassName: TAutoAttendanceReportTask
 * @Description: 自动考勤报表任务
 * @author ohs ohs@klxuexi.org
 * @date 2017年9月12日 下午7:44:03
 *
 */
public class TAutoAttendanceReportTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		AutoAttendanceReportService autoAttendanceReportService = (AutoAttendanceReportService) ApplicationContextUtil
				.getBean("autoAttendanceReportService");
		autoAttendanceReportService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_AUTO_ATTENDANCE_REPORT";
	}

	@Override
	protected String getTableKey() {
		return "ATTEND_DATE";
	}

}
