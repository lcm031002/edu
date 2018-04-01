package com.edu.erp.student.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.StudentCourseDao;
import com.edu.erp.model.CourseYDYInfo;
import com.edu.erp.model.TAttendance;
import com.edu.erp.student.service.StudentCourseService;
import com.github.pagehelper.Page;

@Service(value = "studentCourseService")
public class StudentCourseServiceImpl implements StudentCourseService {

	@Resource(name = "studentCourseDao")
	private StudentCourseDao studentCourseDao;
	
	/**
	 * 学员主页-1对1课程查询
	 * 
	 * @param queryParam
	 *            动态参数
	 * @return
	 */
	@Override
	public Page<CourseYDYInfo> queryStudentYDY(Map<String, Object> queryParam)
			throws Exception {
		// TODO Auto-generated method stub
		return studentCourseDao.queryStudentYDY(queryParam);
	}

	@Override
	public List<TAttendance> queryYDYScheduleCourse(
			Map<String, Object> queryParam) throws Exception {
		return studentCourseDao.queryYDYScheduleCourse(queryParam);
	}

}
