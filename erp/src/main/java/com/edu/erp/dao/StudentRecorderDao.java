package com.edu.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentGradeReviseNote;
import com.edu.erp.model.StudentNameReviseNote;
import com.edu.erp.model.StudentStatusReviseNote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.StudentRecorder;
import com.github.pagehelper.Page;

@Repository("studentRecorderDao")
public interface StudentRecorderDao {
	
	void insertStudentRecorder(StudentRecorder studentRecorder);
	
	Page<StudentRecorder> queryStudentRecorderPage(Map<String, Object> params);
	
	void updateStudentRecorder(StudentRecorder studentRecorder);
	
	//StudentRecorder queryValidStudentRecorder(Long studentId);
	
	//List<StudentRecorder> queryIntersectionStudentRecorder(Long studentId,Date startDate);
	
	/**
	 * 查询报备人信息
	 * @param nameOrEncoding
	 * @return
	 */
	List<Map<String, Object>> queryRecorder(@Param("nameOrEncoding") String nameOrEncoding, @Param("city_id") Long city_id);
	
	/**
	 * 查询最近的报备人信息
	 * @param studentId
	 * @return
	 */
	StudentRecorder queryNewestRecorder(Long studentId);

	List<StudentNameReviseNote> queryStudentNameRecord(@Param("studentId") Long studentId);

	List<StudentGradeReviseNote> queryStudentGradeRecord(@Param("studentId") Long studentId);

	/**
	 * 通过团队查询产品线
	 * @param
	 * @return
	 */
	Long queryProductLine(Map<String, Object> params);

	List<StudentStatusReviseNote> queryStudentStatusRecord(Map<String, Object> queryMap);

	String queryStatusName(@Param("status") Integer status, @Param("productLine") Long productLine);
}
