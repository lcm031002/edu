package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.report.model.TBusinessAttendanceMonthReport;

/**
 * @ClassName: BusinessAttendanceMonthReportDao
 * @Description: 月度分类考勤表
 *
 */
@Repository(value = "businessAttendanceMonthReportDao")
public interface BusinessAttendanceMonthReportDao {

	List<TBusinessAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}
