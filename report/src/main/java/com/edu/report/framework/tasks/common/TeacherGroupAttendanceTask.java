package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.common.service.TeacherGroupAttendanceService;
import com.edu.report.api.AbstactDailyReportTask;

/**
 * @ClassName: TeacherGroupAttendanceTask
 * @Description: 培英班教研组统计任务
 *
 */
public class TeacherGroupAttendanceTask extends AbstactDailyReportTask {

	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		TeacherGroupAttendanceService teacherGroupAttendanceService = (TeacherGroupAttendanceService) ApplicationContextUtil
				.getBean("teacherGroupAttendanceService");
		teacherGroupAttendanceService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

	@Override
	protected String getTableName() {
		return "T_TEACHER_GROUP_ATTENDANCE";
	}

	@Override
	protected String getTableKey() {
		return "COURSE_DATE";
	}

}
