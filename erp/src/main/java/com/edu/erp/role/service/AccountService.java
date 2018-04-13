package com.edu.erp.role.service;

import com.edu.erp.model.Account;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AccountService {
    /**
     *
     * @Title: queryAccountForPage
     * @Description: 查询账户信息列表
     *
     * @return PageInfo<Account> 返回类型
     */
    Page<Account> queryAccountForPage(Map<String, Object> paramMap) throws Exception;

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
