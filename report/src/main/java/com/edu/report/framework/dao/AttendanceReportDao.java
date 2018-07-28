package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceReport;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TAttendanceReport;

/**
 * @ClassName: AttendanceReportDao
 * @Description: 考勤消耗表DAO
 *
 */
@Repository(value = "attendanceReportDao")
public interface AttendanceReportDao {

	List<TAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}
