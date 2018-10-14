package com.edu.report.framework.tasks.bjk;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;
import com.edu.report.api.AbstactDailyReportTask;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;

/**
 * @ClassName: TTeacherWorkloadReportTask
 * @Description: 教师工作量表
 *
 */
public class TTeacherWorkloadReportTask extends AbstactDailyReportTask {
	
	@Override
	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		TeacherWorkloadReportService teacherWorkloadReportService = (TeacherWorkloadReportService) ApplicationContextUtil
				.getBean("teacherWorkloadReportService");
		teacherWorkloadReportService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}
	@Override
	protected String getTableName() {
		return "T_TEACHER_WORKLOAD";
	}
	@Override
	protected String getTableKey() {
		return "ATTEND_DATE";
	}

	
	
}
