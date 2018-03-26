package com.edu.erp.model;

public class EbsRolemenuRel extends BaseObject {

    // 角色id
    private Long roleId;

    // 模板id
    private String menuId;

    /**
     * 设置 角色id,对应字段 ebs_rolemenu_rel.role_id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取 角色id,对应字段 ebs_rolemenu_rel.role_id
     */
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * 设置 模板id,对应字段 ebs_rolemenu_rel.menu_id
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取 模板id,对应字段 ebs_rolemenu_rel.menu_id
     */
    public String getMenuId() {
        return this.menuId;
    }
}