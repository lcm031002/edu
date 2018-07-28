package com.edu.cas.client.dao;

import com.edu.cas.client.common.model.Account;
import org.springframework.stereotype.Repository;

import com.edu.cas.client.common.model.Account;

/**
 * @ClassName: AccountDao
 * @Description: 账户持久层服务
 *
 */
@Repository("SSOaccountDao")
public interface AccountDao {
	/**
	 * 
	 * @Title: queryAccount
	 * @Description: 根据用户名查询账户的服务
	 * @param userName
	 *            用户名
	 * @throws Exception
	 *             设定文件
	 * @return Account 返回类型
	 * 
	 */
	Account queryAccountByUserName(Account account) throws Exception;
	/**
	 * 
	 * @Title: queryAccount
	 * @Description: 根据用户名查询账户的服务
	 * @param userName
	 *            用户名
	 * @throws Exception
	 *             设定文件
	 * @return Account 返回类型
	 * 
	 */
	Account queryAccountById(Account account) throws Exception;

	/**
	 * 
	 * @Title: updateAccount
	 * @Description: 修改账户信息
	 * @param account
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 * 
	 */
	void updateAccount(Account account) throws Exception;
}
