package com.edu.erp.dao;

import com.edu.erp.model.Account;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository(value="accountDao")
public interface AccountDao {

	/**
	 * 
	 * @Title: queryAccountForPage
	 * @Description: 查询账户信息列表
	 * 
	 * @return Page<Account> 返回类型
	 */
	public Page<Account> queryAccountForPage(Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @Title: addAccount
	 * @Description: 新增账户
	 * 
	 * @return Integer 返回类型
	 */
	Integer addAccount(Account param) throws Exception;
	
	/**
	 * 
	 * @Title: updateAccount
	 * @Description: 修改账户
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateAccount(Account param) throws Exception;
	
	/**
	 * 禁用或启用账户
	 * @param status 
	 * @Title: deleteAccount
	 * @Description: 禁用账户
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteAccount(@Param("accountId") Long accountId, @Param("status") Integer status) throws Exception;
	
	/**
	 * 
	 * @Title: queryAccountId
	 * @Description: 查询已存在账户
	 * 
	 * @return Long 返回类型
	 */
	Long queryAccountId(String accountName) throws Exception;

	/**
	 * 查询员工是否被其它用户绑定
	 * @param account 用户信息
	 * @return 员工绑定用户数 大于0表示已被绑定
	 * @throws Exception
	 */
	Integer queryEmployeeBindedCount(Account account) throws Exception;

}
