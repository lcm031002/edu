package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TTeacherWorkloadReport;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.model.TStudentInfo;

/**
 * @ClassName: TeacherWorkloadReportDao
 * @Description: 培英班教师工作量表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月17日 下午8:10:38
 *
 */
@Repository(value = "teacherWorkloadReportDao")
public interface TeacherWorkloadReportDao {

	List<TTeacherWorkloadReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;

	List<TStudentInfo> queryForOrderStudents(Map<String, Object> param) throws Exception;

	List<TStudentInfo> queryForAttendanceStudents(Map<String, Object> param) throws Exception;
	
	/**
	 * 更新 试听学生数/试听拒缴学生数 
	 * @param param
	 * @throws Exception
	 */
	void updateListeningStudentsByTaskFlow(Map<String, Object> param) throws Exception;
}
