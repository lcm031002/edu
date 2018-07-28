package com.edu.report.framework.dao;

import com.edu.report.model.TeacherGroupAttendanceReport;
import com.edu.report.model.TeacherGroupAttendanceReport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TeacherGroupAttendanceDao
 * @Description: 培英班教研组报表DAO
 *
 */
@Repository(value = "teacherGroupAttendanceDao")
public interface TeacherGroupAttendanceDao {

	List<TeacherGroupAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}
