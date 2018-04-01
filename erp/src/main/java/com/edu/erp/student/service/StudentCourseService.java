package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.CourseYDYInfo;
import com.edu.erp.model.TAttendance;
import com.github.pagehelper.Page;

public interface StudentCourseService {

	/**
	 * 学员主页-1对1课程查询
	 * 
	 * @param queryParam 动态参数
	 * @return
	 */
	Page<CourseYDYInfo> queryStudentYDY(Map<String, Object> queryParam)throws Exception;
	

	/**
	 * 查询1对1的排课信息
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TAttendance> queryYDYScheduleCourse(Map<String, Object> queryParam)throws Exception;
}
