package com.edu.erp.course_manager.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TCourse;
import com.edu.erp.model.TempTCourse;

public interface TempTCourseService {

	public List<TempTCourse> prepareInputDataCheck(List<Map<String, Object>> paramMapList) throws Exception;
	
	/**
	 * 判断 课程编号  是否唯一(重复性判断)
	 * @param business_type
	 * @param course_no
	 * @return
	 * @throws Exception
	 */
	public void checkCourseNoUnique(Long business_type, String course_no) throws Exception;
	
	/**
	 * 增加导入的班级课
	 * @param course
	 * @throws Exception
	 */
	public void addImportBjkCourse(TCourse course) throws Exception;
}
