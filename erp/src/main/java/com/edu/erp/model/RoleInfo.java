package com.edu.erp.model;


import java.util.Date;


public class RoleInfo extends BaseObject {
	    
	// 角色编码
        private String roleCode;
    
	// 角色名称
        private String roleName;
    
	// 状态(1=有效 0=逻辑删除)
        
	// 创建用户
        
	// 创建时间
        
	// 修改用户
        
	// 创建时间
        

	    /** 设置 角色编码,对应字段 tab_role_info.role_code */
    public void setRoleCode(String roleCode){
    	this.roleCode = roleCode;
    }
        /** 获取 角色编码,对应字段 tab_role_info.role_code */
    public String getRoleCode(){
    	return this.roleCode;
    }
	    /** 设置 角色名称,对应字段 tab_role_info.role_name */
    public void setRoleName(String roleName){
    	this.roleName = roleName;
    }
        /** 获取 角色名称,对应字段 tab_role_info.role_name */
    public String getRoleName(){
    	return this.roleName;
    }
}