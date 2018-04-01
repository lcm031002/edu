package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentGradeReviseNote;
import com.edu.erp.model.StudentNameReviseNote;
import com.edu.erp.model.StudentRecorder;
import com.edu.erp.model.StudentStatusReviseNote;
import com.github.pagehelper.Page;

public interface StudentRecorderService {

	void insertStudentRecorder(StudentRecorder studentRecorder);

	Page<StudentRecorder> queryStudentRecorderList(Map<String,Object> studentRecorder);

	/**
	 * 查询报备人信息
	 * @param nameOrEncoding
	 * @return
	 */
	List<Map<String, Object>> queryRecorder(String nameOrEncoding,Long city_id);


	/**
	 * 查询学生姓名更改记录
	 * @param studentId
	 * @return
	 */
	List<StudentNameReviseNote> queryStudentNameRecord(Long studentId);

	/**
	 * 查询学生年级修改记录
	 * @param studentId
	 * @return
	 */
	List<StudentGradeReviseNote> queryStudentGradeRecord(Long studentId);

	/**
	 * 查询学生状态修改记录
 	 * @return
	 */
	List<StudentStatusReviseNote>  queryStudentStatusRecord(Map<String,Object> queryMap);
}
