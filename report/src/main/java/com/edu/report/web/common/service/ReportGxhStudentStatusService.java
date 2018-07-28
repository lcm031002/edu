package com.edu.report.web.common.service;

import com.edu.report.model.TReportGxhStudentStatus;
import com.edu.report.model.TReportGxhStudentStatus;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportGxhStudentStatusService
 * @Description: 学生状态报表
 *
 */
public interface ReportGxhStudentStatusService {

	List<TReportGxhStudentStatus> queryForBu(Map<String, Object> param) throws Exception;

	List<TReportGxhStudentStatus> queryForBranch(Map<String, Object> param) throws Exception;

	List<TReportGxhStudentStatus> queryForStudents(Map<String, Object> param) throws Exception;
}
