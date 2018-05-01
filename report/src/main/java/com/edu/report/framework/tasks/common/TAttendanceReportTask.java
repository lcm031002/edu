package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.AttendanceReportService;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.AttendanceReportService;

/**
 * @ClassName: TAttendanceReportTask
 * @Description: 考勤消耗表任务
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月15日 下午7:44:03
 *
 */
public class TAttendanceReportTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		AttendanceReportService attendanceReportService = (AttendanceReportService) ApplicationContextUtil
				.getBean("attendanceReportService");
		attendanceReportService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_ATTENDANCE_REPORT";
	}

	@Override
	protected String getTableKey() {
		return "ATTEND_DATE";
	}

}
