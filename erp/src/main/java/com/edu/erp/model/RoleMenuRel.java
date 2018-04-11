package com.edu.erp.model;

import java.io.Serializable;

public class RoleMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long roleId;

    private String menuIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(String menuIndex) {
        this.menuIndex = menuIndex;
    }

}
