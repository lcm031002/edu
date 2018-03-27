package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.EmpOrgPost;
import com.github.pagehelper.Page;

public interface EmpOrgPostDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<EmpOrgPost> page(Map<String, Object> page) throws Exception;

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<EmpOrgPost> list(Map<String, Object> param) throws Exception;

	/**
	 * 新增
	 * 
	 * @param EmpOrgPost
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toAdd(EmpOrgPost EmpOrgPost) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param EmpOrgPost
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toUpdate(EmpOrgPost EmpOrgPost) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;

	void toBatchAdd(List<EmpOrgPost> aList) throws Exception;

	void toBatchUpdate(List<EmpOrgPost> uList) throws Exception;

}
