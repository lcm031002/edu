package com.edu.erp.attendance.service;

import com.edu.erp.model.AttendTeacherGroup;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;


public interface AttendanceGroupService {

	
	Page<AttendTeacherGroup> queryAttendanceGroupForPage(Map<String, Object> param)throws Exception;


	void toAdd(AttendTeacherGroup pojo) throws Exception;


	void toUpdate(AttendTeacherGroup pojo) throws Exception;


	void toChangeStatus(String ids, Integer code) throws Exception;
	
	/**
	 * 设置教师关系
	 */
	public void toSetTeacher(String teach_group_id, String teacher_ids) throws Exception ;


	List<AttendTeacherGroup> listAttendanceGroup(Map param) throws Exception;
}
