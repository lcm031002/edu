package com.edu.report.dao;

import com.edu.report.model.TAttendanceMonthReport;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository(value = "courseAnalysisReportDao")
public interface CourseAnalysisReportDao {
	Page<Map<String, Object>> queryCourseAnalysisReport(Map<String, Object> paramMap) throws Exception;
}
