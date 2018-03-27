package com.edu.erp.model;

public class OrganizationExtend extends BaseObject {

    private static final long serialVersionUID = 1L;
    // 组织机构ID
    private Long orgId;
    // 扩展类型 1-域名
    private String extendType;
    // 扩展信息
    private String extendInfo;
    // 微信公众号appId
    private String appId;
    // 微信公众号secret
    private String secret;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getExtendType() {
        return extendType;
    }

    public void setExtendType(String extendType) {
        this.extendType = extendType;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
