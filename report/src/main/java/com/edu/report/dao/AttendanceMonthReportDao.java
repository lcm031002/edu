package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceMonthReport;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TAttendanceMonthReport;

/**
 * @ClassName: AttendanceMonthReportDao
 * @Description: 月度消耗表Dao
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年6月16日 上午11:27:48
 *
 */
@Repository(value = "attendanceMonthReportDao")
public interface AttendanceMonthReportDao {

	List<TAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}