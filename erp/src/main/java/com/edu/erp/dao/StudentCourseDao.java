package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.CourseYDYInfo;
import com.edu.erp.model.TAttendance;
import com.github.pagehelper.Page;

public interface StudentCourseDao {
	
	/**
	 * 
	 * @Title: queryStudentYDY
	 * @Description: 学员主页-1对1课程分页查询
	 * @param queryParam
	 * @return
	 * @throws Exception    设定文件
	 * Page<TOrderCourse>    返回类型
	 *
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
