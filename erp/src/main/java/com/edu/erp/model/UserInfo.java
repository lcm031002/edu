package com.edu.erp.model;

import java.util.Date;

public class UserInfo extends BaseObject {

    // 用户名
    private String username;

    // 用户密码
    private String password;

    // 员工id
    private Long employeeId;

    // 密码更新时间
    private Date updatePwdTime;

    // 是否管理员更新
    private Integer sysUpdate;

    /**
     * 设置 用户名,对应字段 tab_user_info.username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 用户名,对应字段 tab_user_info.username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置 用户密码,对应字段 tab_user_info.password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 用户密码,对应字段 tab_user_info.password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置 员工id,对应字段 tab_user_info.employee_id
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 获取 员工id,对应字段 tab_user_info.employee_id
     */
    public Long getEmployeeId() {
        return this.employeeId;
    }

    /**
     * 设置 密码更新时间,对应字段 tab_user_info.update_pwd_time
     */
    public void setUpdatePwdTime(Date updatePwdTime) {
        this.updatePwdTime = updatePwdTime;
    }

    /**
     * 获取 密码更新时间,对应字段 tab_user_info.update_pwd_time
     */
    public Date getUpdatePwdTime() {
        return this.updatePwdTime;
    }

    /**
     * 设置 是否管理员更新,对应字段 tab_user_info.sys_update
     */
    public void setSysUpdate(Integer sysUpdate) {
        this.sysUpdate = sysUpdate;
    }

    /**
     * 获取 是否管理员更新,对应字段 tab_user_info.sys_update
     */
    public Integer getSysUpdate() {
        return this.sysUpdate;
    }
}