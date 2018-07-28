package com.edu.report.web.wfd.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TTeacherAttendReport;
import com.edu.report.model.TTeacherAttendReport;

/**
 * @ClassName: TeacherWorkloadReportService
 * @Description: 晚辅导教师考勤表
 *
 */
public interface WfdAttendanceService {

	List<TTeacherAttendReport> queryReport(Map<String, Object> param) throws Exception;

	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
