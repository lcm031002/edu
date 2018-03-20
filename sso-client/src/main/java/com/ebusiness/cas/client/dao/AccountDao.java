/**  
 * @Title: AccountDao.java
 * @Package com.ebusiness.cas.client.common.account
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午3:28:04
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.dao;

import org.springframework.stereotype.Repository;

import com.ebusiness.cas.client.common.model.Account;

/**
 * @ClassName: AccountDao
 * @Description: 账户持久层服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午3:28:04
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
