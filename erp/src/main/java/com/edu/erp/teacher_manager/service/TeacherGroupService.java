package com.edu.erp.teacher_manager.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TeacherGroup;
import com.github.pagehelper.Page;

public interface TeacherGroupService {
	Page<TeacherGroup> selectForPage(Map<String, Object> paramMap);
	
	List<TeacherGroup> selectForList(Map<String, Object> paramMap);
	
	void add(TeacherGroup teacherGroup);
	
	void update(TeacherGroup teacherGroup);
	
	TeacherGroup selectById(Long id);

}
