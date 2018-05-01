package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TTeacherAttendReport;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TTeacherAttendReport;
import com.github.pagehelper.Page;

@Repository(value = "teacherAttendReportDao")
public interface TeacherAttendReportDao {

	List<TTeacherAttendReport> queryReport(Map<String, Object> param) throws Exception;

	void deleteByTaskFlow(Map<String, Object> param) throws Exception;

	void insertByTaskFlow(Map<String, Object> param) throws Exception;

}
