/**  
 * @Title: AccountServiceImpl.java
 * @Package com.ebusiness.cas.client.common.account.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午3:27:33
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.account.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ebusiness.cas.client.common.account.SSOAccountService;
import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.util.PwdEncryptUtil;
import com.ebusiness.cas.client.dao.AccountDao;

/**
 * @ClassName: AccountServiceImpl
 * @Description: 账户服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午3:27:33
 * 
 */
@Service(value = "accountSSOService")
public class AccountServiceImpl implements SSOAccountService {

	private static final Logger log = Logger
			.getLogger(AccountServiceImpl.class);

	@Resource(name = "SSOaccountDao")
	private AccountDao accountDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.cas.client.common.account.AccountService#queryAccount(java
	 * .lang.String)
	 */
	public Account queryAccount(String userName) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to queryAccount,userName is " + userName);
		}
		Assert.notNull(userName);
		Account account = new Account();
		account.setUserName(userName);
		return accountDao.queryAccountByUserName(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.cas.client.common.account.AccountService#updateAccount(
	 * com.ebusiness.cas.client.common.model.Account)
	 */
	public Account updateAccount(Account account) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to updateAccount,account is " + account.toString());
		}
		
		Assert.notNull(account);
		Assert.notNull(account.getId());
		
		Account accountQuery = new Account();
		accountQuery.setId(account.getId());
		accountQuery = accountDao.queryAccountById(accountQuery);
		
		Assert.notNull(accountQuery);

		accountQuery.setUserName(account.getUserName());
		
		//查询当前用户密码和前台传过来的面永远不会相等的;所以要修改成不相等的时候才把password.Set进去
		if (null != account.getPassword()
				&& ! accountQuery.getPassword().equals(account.getPassword())) {
			accountQuery.setPassword(PwdEncryptUtil.encrypt(account.getPassword()));
		}
		
		if (null != account.getEmployeeId()) {
			accountQuery.setEmployeeId(account.getEmployeeId());
		}

		if (null != account.getStatus()) {
			accountQuery.setStatus(account.getStatus());
		}
		
		if (log.isDebugEnabled()) {
			log.debug("begin to updateAccount,see : " + accountQuery.toString());
		}
		
		accountDao.updateAccount(accountQuery);
		
		if (log.isDebugEnabled()) {
			log.debug("end to updateAccount,see : " + accountQuery.toString());
		}
		
		return accountQuery;
	}

}
