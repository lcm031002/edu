package com.edu.erp.promotion.service;

import java.util.Map;

import com.edu.erp.model.PrivilegeCriteria;
import com.github.pagehelper.Page;


public interface PrivilegeCriteriaService {
	
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
	void addPrivilegeCriteria(PrivilegeCriteria PrivilegeCriteria) throws Exception;
	
	/**
	 * 更新
	 * 
	 * @param PrivilegeCriteria
	 * @throws Exception
	 */
	void updatePrivilegeCriteria(PrivilegeCriteria PrivilegeCriteria) throws Exception;
	
	/**
	 * 逻辑删除
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	void deletePrivilegeCriteria(String id)
			throws Exception;
	
	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void changePrivilegeCriteriaStatus(String ids, Integer status) throws Exception;
}
