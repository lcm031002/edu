package com.ebusiness.hrm.account;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.Account;
import com.ebusiness.hrm.model.AccountOrgRel;
import com.ebusiness.hrm.model.AccountRoleRel;
import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.Role;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: AccountService
 * @Description: 账户管理
 * @author WP
 * @date 2016年10月27日 
 * 
 */
public interface AccountService {


	/**
	 * 
	 * @Title: queryAccountForPage
	 * @Description: 查询账户信息列表
	 * 
	 * @return PageInfo<Account> 返回类型
	 */
	PageInfo<Account> queryAccountForPage(String accountName,Long employeeId,String employeeName,PageParam pageParam) throws Exception;
	
	/**
	 * 
	 * @Title: queryRoleWithAccount
	 * @Description: 根据指定账户查询角色
	 * 
	 * @return List<Role> 返回类型
	 */
	List<Map<String, Object>> queryRoleWithAccount(Long user_id) throws Exception;
	
	/**
	 * 
	 * @Title: queryAccountOrg
	 * @Description: 根据账户id查询该账户的组织列表(orgId)
	 * 
	 * @return List<Integer> 返回类型
	 */
	List<Integer> queryAccountOrg(Long accountId) throws Exception;
	
	/**
	 * 
	 * @Title: addAccount
	 * @Description: 添加账户
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addAccount(Account param) throws Exception;
	
	/**
	 * 
	 * @Title: updateAccountRole
	 * @Description: 修改账户角色关系
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateAccountRole(Map<String, Object> param) throws Exception;
	
	
	
	
	/**
	 * 
	 * @Title: updateAccountOrg
	 * @Description: 修改账户组织关系
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateAccountOrg(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: updateAccount
	 * @Description: 修改账户
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateAccount(Account param) throws Exception;
	
	/**
	 * 禁用或启用账户
	 * @Title: deleteAccount
	 * @Description: 禁用账户
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteAccount(Long accountId, Integer status) throws Exception;
	
	/**
	 * 根据编号删除用户角色
	 * @param accountRoleId 用户角色编号
	 * @throws Exception
	 */
	void deleteAccountRoleById(Long accountRoleId) throws Exception;

}
