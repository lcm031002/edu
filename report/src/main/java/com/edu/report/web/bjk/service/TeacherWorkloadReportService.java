package com.edu.report.web.bjk.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TTeacherWorkloadReport;

/**
 * @ClassName: TeacherWorkloadReportService
 * @Description: 培英班教师工作量
 *
 */
public interface TeacherWorkloadReportService {

	List<TTeacherWorkloadReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;

	List<TStudentInfo> queryForOrderStudents(Map<String, Object> param) throws Exception;

	List<TStudentInfo> queryForAttendanceStudents(Map<String, Object> param) throws Exception;
}
