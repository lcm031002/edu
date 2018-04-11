package com.edu.erp.role.service;

import com.edu.erp.model.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface RoleService {
    /**
     * @return PageInfo<Role> 返回类型
     * @Title: queryRole
     * @Description: 查询角色信息列表
     */
    Page<Role> queryRole(Map<String, Object> paramMap) throws Exception;

    /**
     * @return boolean 返回类型
     * @Title: addRole
     * @Description: 添加角色
     */
    boolean addRole(Role role) throws Exception;

    /**
     * @return boolean 返回类型
     * @Title: updateRole
     * @Description: 修改角色
     */
    boolean updateRole(Role role) throws Exception;

    /**
     * @param status
     * @return boolean 返回类型
     * @Title: deleteRole
     * @Description: 禁用角色
     */
    boolean changeRoleStatus(Long id, Long status) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryRoleMenu
     * @Description: 查询角色菜单权限关系
     */
    List<String> queryRoleMenu(Long id) throws Exception;

    /**
     * @return boolean 返回类型
     * @Title: updateRoleMenu
     * @Description: 修改角色菜单权限关系
     */
    boolean updateRoleMenu(Role role) throws Exception;
}
