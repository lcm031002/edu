package com.edu.erp.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value="accountOrgRelDao")
public interface AccountOrgRelDao {

	
	/**
	 * 
	 * @Title: queryAccountOrg
	 * @Description: 根据账户id查询该账户的组织列表
	 * 
	 * @return List<Integer> 返回类型
	 */
	List<Integer> queryAccountOrg(Long accountId) ;
	
	/**
	 * 
	 * @Title: deleteAccountOrg
	 * @Description: 删除账户组织关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteAccountOrg(Long accountId) throws Exception;
	
	/**
	 * 
	 * @Title: addAccountOrg
	 * @Description: 新增账户组织关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer addAccountOrg(Map<String, Object> param) throws Exception;

}
