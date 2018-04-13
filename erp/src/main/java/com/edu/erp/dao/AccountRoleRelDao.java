package com.edu.erp.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "accountRoleRelDao")
public interface AccountRoleRelDao {

    /**
     * @return List<AccountRoleRel> 返回类型
     * @Title: queryRoleWithAccount
     * @Description: 根据指定账户查询角色
     */
    List<Map<String, Object>> queryRoleWithAccount(Long user_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addAccountRole
     * @Description: 新增账户角色关系
     */
    Integer addAccountRole(Map<String, Object> param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: deleteAccountRole
     * @Description: 删除账户角色关系
     */
    Integer deleteAccountRole(Long user_id) throws Exception;

    /**
     * 根据编号删除用户角色
     *
     * @param id 用户角色编号
     * @throws Exception
     */
    void deleteAccountRoleById(Long id) throws Exception;

}
