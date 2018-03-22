package com.ebusiness.hrm.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.AccountDao;
import com.ebusiness.hrm.dao.AccountOrgRelDao;
import com.ebusiness.hrm.dao.AccountRoleRelDao;
import com.ebusiness.hrm.model.Account;
import com.ebusiness.hrm.model.PageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import junit.framework.Assert;


/**
 * @ClassName: AccountServiceImpl
 * @Description: 账户管理服务处理类
 * @author WP
 * @date 2016年10月27日 
 * 
 */
@Service(value="accountService")
public class AccountServiceImpl implements AccountService{

	private static final Logger log = Logger.getLogger(AccountServiceImpl.class);
	
	@Resource(name="accountDao")
	private AccountDao accountDao;
	
	@Resource(name="accountRoleRelDao")
	private AccountRoleRelDao accountRoleRelDao;
	
	@Resource(name="accountOrgRelDao")
	private AccountOrgRelDao accountOrgRelDao;
	
	//查询账户信息列表
	@Override
	public PageInfo<Account> queryAccountForPage(String accountName,Long employeeId,String employeeName,PageParam pageParam) throws Exception{
		Page<Account> list = null;
		PageInfo<Account> page = new PageInfo<Account>();
		
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("accountName", accountName);
			params.put("employeeId", employeeId);
			params.put("employeeName", employeeName);
			PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
			
			list = accountDao.queryAccountForPage(params);
			log.error("list:"+list);
			page = new PageInfo<Account>(list);
		}catch(Exception e){
			log.error("error found,see:",e);
		}
		return page;
	}
	
	//根据指定账户查询角色
		@Override
		public List<Map<String, Object>> queryRoleWithAccount(Long user_id) throws Exception{
			List<Map<String, Object>> result = accountRoleRelDao.queryRoleWithAccount(user_id);
			return result;
		}
	
	//根据账户id查询该账户的组织列表
	@Override
	public List<Integer> queryAccountOrg(Long accountId) throws Exception{
		return accountOrgRelDao.queryAccountOrg(accountId);
	}
	
	//添加账户
	@Override
	public boolean addAccount(Account param) throws Exception{
			Assert.assertNotNull(param.getAccountName());
			String accountName = param.getAccountName()==null?"":param.getAccountName();
			Long queryAccountId=accountDao.queryAccountId(accountName);
			if(null!=queryAccountId){
				throw new Exception("该账户已存在");
			}
		checkEmployeeBinded(param);
		Integer ret = accountDao.addAccount(param);
		if(ret<1){
			throw new Exception("添加失败");
		}
		
		return ret==1;
	}
	
	//修改账户角色关系
	@Override
	public boolean  updateAccountRole(Map<String, Object> param) throws Exception{
		boolean suc = true;
		try{
			Long user_id = Long.valueOf(param.get("user_id").toString());
			accountRoleRelDao.deleteAccountRole(user_id);
			addAccountRole(param);
		}catch(Exception e){
			e.printStackTrace();
			suc=false;
			throw new Exception(e.getMessage());
		}
		return suc;
	}
	
	//添加账户角色关系
	public boolean addAccountRole(Map<String,Object> param) throws Exception{
		Integer ret = accountRoleRelDao.addAccountRole(param);
		if(ret<1){
			throw new Exception("添加账户角色关系失败");
		}
		return ret==1;
	}
	
	
	
	//修改账户组织关系
	@Override
	public boolean  updateAccountOrg(Map<String, Object> param) throws Exception{
		boolean suc = true;
		try{
			Long accountId = Long.valueOf(param.get("accountId").toString());
			accountOrgRelDao.deleteAccountOrg(accountId);
			List selectedBranch = (List) param.get("selectedBranch");
            if (CollectionUtils.isNotEmpty(selectedBranch)) {
                addAccountOrg(param);
            }
		}catch(Exception e){
			e.printStackTrace();
			suc=false;
			throw new Exception(e.getMessage());
		}
		return suc;
	}
	
	//添加账户组织关系
	public boolean addAccountOrg(Map<String, Object> param) throws Exception{
		Integer ret = accountOrgRelDao.addAccountOrg(param);
		if(ret<1){
			throw new Exception("添加账户组织关系失败");
		}
		return ret==1;
	}
	
	
	//修改账户
	@Override
	public boolean updateAccount(Account param) throws Exception{
		Assert.assertNotNull(param.getAccountName());
		String accountName=param.getAccountName()==null?"":param.getAccountName();
		Long accountId=param.getUser_id();
		Long queryAccountId=accountDao.queryAccountId(accountName);
		if(null!=queryAccountId&&queryAccountId.longValue()!=accountId.longValue()){
			throw new Exception("该账户已存在");
		}
		checkEmployeeBinded(param);
		Integer ret = accountDao.updateAccount(param);
		if(ret<1){
			throw new Exception("修改失败");
		}
		return ret==1;
	}

	private void checkEmployeeBinded(Account account) throws Exception {
		if (accountDao.queryEmployeeBindedCount(account) > 0) {
			throw new Exception("该员工已被其他用户绑定！");
		}
	}
	
	//禁用账户
	@Override
	public boolean deleteAccount(Long accountId,Integer status) throws Exception{
		Integer ret = accountDao.deleteAccount(accountId,status);
		if(ret<1){
			throw new Exception("禁用失败");
		}
		return ret==1;
	}

    @Override
    public void deleteAccountRoleById(Long accountRoleId) throws Exception {
        this.accountRoleRelDao.deleteAccountRoleById(accountRoleId);
    }
}
