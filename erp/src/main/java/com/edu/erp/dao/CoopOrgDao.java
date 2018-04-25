package com.edu.erp.dao;

import com.edu.erp.model.CoopOrg;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "coopOrgDao")
public interface CoopOrgDao {
	/**
	 * 分页查询
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Page<CoopOrg> selectForPage(Map<String, Object> paramMap);

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param paramMap 动态参数
	 * @return
	 * @throws Exception
	 */
	List<CoopOrg> selectList(Map<String, Object> paramMap);
	
	/**
	 * 新增
	 * 
	 * @param coopOrg
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer insert(CoopOrg coopOrg);
	
	/**
	 * 根据ID修改
	 * 
	 * @param coopOrg
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer update(CoopOrg coopOrg);
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param paramMap
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer changeStatus(Map<String, Object> paramMap);
}