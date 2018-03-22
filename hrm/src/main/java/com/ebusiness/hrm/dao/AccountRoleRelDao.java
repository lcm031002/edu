package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: AccountRoleRelDao
 * @Description: 账户角色关系持久层
 * @author WP
 * @date 2016年10月31日 
 * 
 */
@Repository(value="accountRoleRelDao")
public interface AccountRoleRelDao {

	/**
	 * 
	 * @Title: queryRoleWithAccount
	 * @Description: 根据指定账户查询角色
	 * 
	 * @return List<AccountRoleRel> 返回类型
	 */
	List<Map<String, Object>> queryRoleWithAccount(Long user_id) throws Exception;
	
	/**
	 * 
	 * @Title: addAccountRole
	 * @Description: 新增账户角色关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer addAccountRole(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteAccountRole
	 * @Description: 删除账户角色关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteAccountRole(Long user_id) throws Exception;
	
	/**
     * 根据编号删除用户角色
     * @param accountRoleId 用户角色编号
     * @throws Exception
     */
    void deleteAccountRoleById(Long id) throws Exception;
	
}
