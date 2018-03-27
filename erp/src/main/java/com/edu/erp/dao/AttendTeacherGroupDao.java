package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.AttendTeacherGroup;
import com.github.pagehelper.Page;

@Repository(value = "attendTeacherGroupDao")
public interface AttendTeacherGroupDao {


	Page<AttendTeacherGroup> page(Map<String, Object> param);
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	public List<AttendTeacherGroup> selectList(Map<String, Object> param) throws Exception;
	
	Integer toAdd(AttendTeacherGroup pojo);

	Integer toUpdate(AttendTeacherGroup pojo);

	void toChangeStatus(Map<String, Object> param);

	void toRemoveTeacherRef(Map<String, Object> param);

	void toAddTeacherRef(List<Map<String, Long>> ref);
	

}