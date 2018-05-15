package com.edu.erp.role.service.impl;

import com.edu.erp.dao.AccountDao;
import com.edu.erp.dao.AccountOrgRelDao;
import com.edu.erp.dao.AccountRoleRelDao;
import com.edu.erp.model.Account;
import com.edu.erp.model.TAccount;
import com.edu.erp.role.service.AccountService;
import com.github.pagehelper.Page;
import junit.framework.Assert;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "accountDao")
    private AccountDao accountDao;

    @Resource(name = "accountRoleRelDao")
    private AccountRoleRelDao accountRoleRelDao;

    @Resource(name = "accountOrgRelDao")
    private AccountOrgRelDao accountOrgRelDao;

    //查询账户信息列表
    @Override
    public Page<Account> queryAccountForPage(Map<String, Object> paramMap) throws Exception {
        return accountDao.queryAccountForPage(paramMap);
    }

    //根据指定账户查询角色
    @Override
    public List<Map<String, Object>> queryRoleWithAccount(Long user_id) throws Exception {
        return accountRoleRelDao.queryRoleWithAccount(user_id);
    }

    //根据账户id查询该账户的组织列表
    @Override
    public List<Integer> queryAccountOrg(Long accountId) throws Exception {
        return accountOrgRelDao.queryAccountOrg(accountId);
    }

    //添加账户
    @Override
    public boolean addAccount(Account account) throws Exception {
        Assert.assertNotNull(account.getAccountName());
        Long queryAccountId = accountDao.queryAccountId(account.getAccountName());
        if (null != queryAccountId) {
            throw new Exception("该账户已存在");
        }
        checkEmployeeBinded(account);
        Integer ret = accountDao.addAccount(account);
        if (ret < 1) {
            throw new Exception("添加失败");
        }

        return ret == 1;
    }

    //修改账户角色关系
    @Override
    public boolean updateAccountRole(Map<String, Object> param) throws Exception {
        boolean suc = true;
        try {
            Long user_id = Long.valueOf(param.get("id").toString());
            accountRoleRelDao.deleteAccountRole(user_id);
            addAccountRole(param);
        } catch (Exception e) {
            suc = false;
            throw new Exception(e.getMessage());
        }
        return suc;
    }

    //添加账户角色关系
    public boolean addAccountRole(Map<String, Object> param) throws Exception {
        Integer ret = accountRoleRelDao.addAccountRole(param);
        if (ret < 1) {
            throw new Exception("添加账户角色关系失败");
        }
        return ret == 1;
    }


    //修改账户组织关系
    @Override
    public boolean updateAccountOrg(Map<String, Object> param) throws Exception {
        boolean suc = true;
        try {
            Long accountId = Long.valueOf(param.get("accountId").toString());
            accountOrgRelDao.deleteAccountOrg(accountId);
            List selectedBranch = (List) param.get("selectedBranch");
            if (CollectionUtils.isNotEmpty(selectedBranch)) {
                addAccountOrg(param);
            }
        } catch (Exception e) {
            suc = false;
            throw new Exception(e.getMessage());
        }
        return suc;
    }

    //添加账户组织关系
    public boolean addAccountOrg(Map<String, Object> param) throws Exception {
        Integer ret = accountOrgRelDao.addAccountOrg(param);
        if (ret < 1) {
            throw new Exception("添加账户组织关系失败");
        }
        return ret == 1;
    }

    //修改账户
    @Override
    public boolean updateAccount(Account account) throws Exception {
        Assert.assertNotNull(account.getAccountName());
        Long accountId = account.getId();
        Long queryAccountId = accountDao.queryAccountId(account.getAccountName());
        if (null != queryAccountId && queryAccountId.longValue() != accountId.longValue()) {
            throw new Exception("该账户已存在");
        }
        checkEmployeeBinded(account);
        Integer ret = accountDao.updateAccount(account);
        if (ret < 1) {
            throw new Exception("修改失败");
        }
        return ret == 1;
    }

    private void checkEmployeeBinded(Account account) throws Exception {
        if (accountDao.queryEmployeeBindedCount(account) > 0) {
            throw new Exception("该员工已被其他用户绑定！");
        }
    }

    //禁用账户
    @Override
    public boolean deleteAccount(Long accountId, Integer status) throws Exception {
        Integer ret = accountDao.deleteAccount(accountId, status);
        if (ret < 1) {
            throw new Exception("禁用失败");
        }
        return ret == 1;
    }

    @Override
    public void deleteAccountRoleById(Long accountRoleId) throws Exception {
        this.accountRoleRelDao.deleteAccountRoleById(accountRoleId);
    }

    @Override
    public TAccount queryByStudentIdAndBuId(Long studentId, Long buId) {
        return this.accountDao.queryByStudentIdAndBuId(studentId, buId);
    }
}
