package com.edu.erp.model;

public class OrganizationInfo extends BaseObject {

    // 组织名称
    private String orgName;

    // 组织机构简称
    private String shortOrgName;

    // 上级组织
    private Long parentId;

    // 组织级别 1=地区级别 2=部门级别 3=团队级别 4=校区级别
    private Integer orgType;

    // 排序
    private Integer sortNum;

    // 地址
    private String address;

    // logo
    private String Logo;

    // 电话
    private String phone;

    // 邮箱
    private String email;

    private Integer productLine;

    private Integer orgKind;


    /**
     * 设置 组织名称,对应字段 tab_organization_info.org_name
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 组织名称,对应字段 tab_organization_info.org_name
     */
    public String getOrgName() {
        return this.orgName;
    }

    /**
     * 设置 组织机构简称,对应字段 tab_organization_info.short_org_name
     */
    public void setShortOrgName(String shortOrgName) {
        this.shortOrgName = shortOrgName;
    }

    /**
     * 获取 组织机构简称,对应字段 tab_organization_info.short_org_name
     */
    public String getShortOrgName() {
        return this.shortOrgName;
    }

    /**
     * 设置 上级组织,对应字段 tab_organization_info.parent_id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取 上级组织,对应字段 tab_organization_info.parent_id
     */
    public Long getParentId() {
        return this.parentId;
    }

    /**
     * 设置 组织级别 1=地区级别 2=部门级别 3=团队级别 4=校区级别 ,对应字段 tab_organization_info.org_type
     */
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    /**
     * 获取 组织级别 1=地区级别 2=部门级别 3=团队级别 4=校区级别 ,对应字段 tab_organization_info.org_type
     */
    public Integer getOrgType() {
        return this.orgType;
    }

    /**
     * 设置 排序,对应字段 tab_organization_info.sort_num
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 获取 排序,对应字段 tab_organization_info.sort_num
     */
    public Integer getSortNum() {
        return this.sortNum;
    }

    /**
     * 设置 地址,对应字段 tab_organization_info.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取 地址,对应字段 tab_organization_info.address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 logo,对应字段 tab_organization_info.Logo
     */
    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    /**
     * 获取 logo,对应字段 tab_organization_info.Logo
     */
    public String getLogo() {
        return this.Logo;
    }

    /**
     * 设置 电话,对应字段 tab_organization_info.phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 电话,对应字段 tab_organization_info.phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 邮箱,对应字段 tab_organization_info.email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取 邮箱,对应字段 tab_organization_info.email
     */
    public String getEmail() {
        return this.email;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public Integer getProductLine() {
        return this.productLine;
    }

    public void setOrgKind(Integer orgKind) {
        this.orgKind = orgKind;
    }

    public Integer getOrgKind() {
        return this.orgKind;
    }
}