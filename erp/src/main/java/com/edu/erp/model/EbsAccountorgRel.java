package com.edu.erp.model;

public class EbsAccountorgRel extends BaseObject {

    // 用户id
    private Long accountid;

    // 角色id
    private Long orgid;

    /**
     * 设置 用户id,对应字段 ebs_accountorg_rel.accountid
     */
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    /**
     * 获取 用户id,对应字段 ebs_accountorg_rel.accountid
     */
    public Long getAccountid() {
        return this.accountid;
    }

    /**
     * 设置 角色id,对应字段 ebs_accountorg_rel.orgid
     */
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    /**
     * 获取 角色id,对应字段 ebs_accountorg_rel.orgid
     */
    public Long getOrgid() {
        return this.orgid;
    }
}