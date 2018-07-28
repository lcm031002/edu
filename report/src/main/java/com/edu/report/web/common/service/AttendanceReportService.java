package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TAttendanceReport;

/**
 * @ClassName: AttendanceReportService
 * @Description: 考勤消耗表
 *
 */
public interface AttendanceReportService {

	List<TAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
