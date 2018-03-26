package com.edu.erp.model;

import java.util.Date;

public class EmployeeInfo extends BaseObject {

    // 员工编码
    private String encoding;

    // 员工姓名
    private String employeeName;

    // 性别(1=男 2=女)
    private Integer Sex;

    // 身份证号
    private String idCard;

    // 邮箱
    private String email;

    // 电话
    private String phone;

    // 地址
    private String address;

    // 描述
    private String description;

    // 员工类型(1=正式员工 2=试用员工 3=实习员工)
    private Integer userType;

    // 微信
    private String wechat;

    // 部门
    private String dept;

    // 组织id
    private Long orgId;

    // 职位
    private String position;

    // 入职日期
    private Date entryDate;

    /**
     * 设置 员工编码,对应字段 tab_employee_info.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 员工编码,对应字段 tab_employee_info.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 员工姓名,对应字段 tab_employee_info.employee_name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * 获取 员工姓名,对应字段 tab_employee_info.employee_name
     */
    public String getEmployeeName() {
        return this.employeeName;
    }

    /**
     * 设置 性别(1=男 2=女),对应字段 tab_employee_info.Sex
     */
    public void setSex(Integer Sex) {
        this.Sex = Sex;
    }

    /**
     * 获取 性别(1=男 2=女),对应字段 tab_employee_info.Sex
     */
    public Integer getSex() {
        return this.Sex;
    }

    /**
     * 设置 身份证号,对应字段 tab_employee_info.id_card
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取 身份证号,对应字段 tab_employee_info.id_card
     */
    public String getIdCard() {
        return this.idCard;
    }

    /**
     * 设置 邮箱,对应字段 tab_employee_info.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取 邮箱,对应字段 tab_employee_info.email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置 电话,对应字段 tab_employee_info.phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 电话,对应字段 tab_employee_info.phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 地址,对应字段 tab_employee_info.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取 地址,对应字段 tab_employee_info.address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 描述,对应字段 tab_employee_info.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 tab_employee_info.description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置 员工类型(1=正式员工 2=试用员工 3=实习员工),对应字段 tab_employee_info.user_type
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取 员工类型(1=正式员工 2=试用员工 3=实习员工),对应字段 tab_employee_info.user_type
     */
    public Integer getUserType() {
        return this.userType;
    }

    /**
     * 设置 微信,对应字段 tab_employee_info.wechat
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取 微信,对应字段 tab_employee_info.wechat
     */
    public String getWechat() {
        return this.wechat;
    }

    /**
     * 设置 部门,对应字段 tab_employee_info.dept
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * 获取 部门,对应字段 tab_employee_info.dept
     */
    public String getDept() {
        return this.dept;
    }

    /**
     * 设置 组织id,对应字段 tab_employee_info.org_id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取 组织id,对应字段 tab_employee_info.org_id
     */
    public Long getOrgId() {
        return this.orgId;
    }

    /**
     * 设置 职位,对应字段 tab_employee_info.position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取 职位,对应字段 tab_employee_info.position
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * 设置 入职日期,对应字段 tab_employee_info.entry_date
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * 获取 入职日期,对应字段 tab_employee_info.entry_date
     */
    public Date getEntryDate() {
        return this.entryDate;
    }
}