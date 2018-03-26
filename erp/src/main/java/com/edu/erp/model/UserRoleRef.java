package com.edu.erp.model;

public class UserRoleRef extends BaseObject {

    // 用户id
    private Long userId;

    // 角色id
    private Long roleId;


    /**
     * 设置 用户id,对应字段 tab_user_role_ref.user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户id,对应字段 tab_user_role_ref.user_id
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * 设置 角色id,对应字段 tab_user_role_ref.role_id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取 角色id,对应字段 tab_user_role_ref.role_id
     */
    public Long getRoleId() {
        return this.roleId;
    }
}