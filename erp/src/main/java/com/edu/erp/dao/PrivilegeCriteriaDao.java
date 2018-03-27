package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.PrivilegeCriteria;
import com.github.pagehelper.Page;


public interface PrivilegeCriteriaDao{
	
	/**
	 * 分页查询
	 * 
	 * @param page 动态参数 
	 * @return
	 */
	Page<PrivilegeCriteria> queryPrivilegeCriteriaForPage(Map<String, Object> page) throws Exception;
	
	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	PrivilegeCriteria queryPrivilegeCriteriaForOne(Long id) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param PrivilegeCriteria
	 * @throws Exception
	 */
	Integer insert(PrivilegeCriteria PrivilegeCriteria) throws Exception;
	
	/**
	 * 更新
	 * 
	 * @param PrivilegeCriteria
	 * @throws Exception
	 */
	Integer update(PrivilegeCriteria PrivilegeCriteria) throws Exception;
	
	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	Integer changePrivilegeCriteriaStatus(List<PrivilegeCriteria> PrivilegeCriterias) throws Exception;
	
	
	/**
	 * @param empIdStr
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteByIds(List<String> ids) throws Exception;
}
