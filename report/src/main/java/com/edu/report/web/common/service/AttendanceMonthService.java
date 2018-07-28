package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceMonthReport;
import com.edu.report.model.TAttendanceMonthReport;

/**
 * @ClassName: AttendanceMonthService
 * @Description: 月度消耗表Service
 *
 */
public interface AttendanceMonthService {

	List<TAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
