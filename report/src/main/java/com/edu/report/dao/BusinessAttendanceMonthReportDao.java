package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.report.model.TBusinessAttendanceMonthReport;

/**
 * @ClassName: BusinessAttendanceMonthReportDao
 * @Description: 月度分类考勤表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年6月16日 上午11:51:59
 *
 */
@Repository(value = "businessAttendanceMonthReportDao")
public interface BusinessAttendanceMonthReportDao {

	List<TBusinessAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}
