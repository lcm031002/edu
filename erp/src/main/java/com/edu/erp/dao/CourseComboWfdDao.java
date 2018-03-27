package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourseComboWfd;
import com.github.pagehelper.Page;

@Repository(value = "courseComboWfdDao")
public interface CourseComboWfdDao {
	
	/**
	 * 分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<TCourseComboWfd> selectForPage(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据条件查询List
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<TCourseComboWfd> selectForList(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * @param pojo
	 * @throws Exception
	 */
	void insert(TCourseComboWfd pojo) throws Exception;

	/**
	 * 根据ID修改
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	void update(TCourseComboWfd pojo) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * @param param
	 * @throws Exception
	 */
	void changeStatus(Map<String, Object> param) throws Exception;
	
}
