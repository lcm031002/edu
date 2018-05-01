package com.edu.report.web.bjk.service;

import com.github.pagehelper.Page;
import java.util.Map;

public interface CourseAnalysisReportService {
	Page<Map<String, Object>> queryCourseAnalysisReport(Map<String, Object> paramMap) throws Exception;
}
