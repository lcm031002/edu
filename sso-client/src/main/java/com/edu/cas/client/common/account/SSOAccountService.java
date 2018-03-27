/**  
 * @Title: AccountService.java
 * @Package com.edu.cas.client.common.account
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午12:19:29
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.account;

import com.edu.cas.client.common.model.Account;

/**
 * @ClassName: AccountService
 * @Description: 账户服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午12:19:29
 * 
 */
public interface SSOAccountService {
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
	Account queryAccount(String userName) throws Exception;

	/**
	 * 
	 * @Title: updateAccount
	 * @Description: 修改账户信息
	 * @param account
	 * @throws Exception
	 *             设定文件
	 * @return Account 返回类型
	 * 
	 */
	Account updateAccount(Account account) throws Exception;
}
