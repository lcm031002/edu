package com.edu.erp.model;

public class DataDevice extends BaseObject {

    // POS编码
    private String encoding;

    // POS名称
    private String posName;

    // 简称
    private String shortPostName;

    // 公司账号
    private Long accountId;

    // 描述
    private String description;

    /**
     * 设置 POS编码,对应字段 tab_data_device.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 POS编码,对应字段 tab_data_device.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 POS名称,对应字段 tab_data_device.pos_name
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }

    /**
     * 获取 POS名称,对应字段 tab_data_device.pos_name
     */
    public String getPosName() {
        return this.posName;
    }

    /**
     * 设置 简称,对应字段 tab_data_device.short_post_name
     */
    public void setShortPostName(String shortPostName) {
        this.shortPostName = shortPostName;
    }

    /**
     * 获取 简称,对应字段 tab_data_device.short_post_name
     */
    public String getShortPostName() {
        return this.shortPostName;
    }

    /**
     * 设置 公司账号,对应字段 tab_data_device.account_id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取 公司账号,对应字段 tab_data_device.account_id
     */
    public Long getAccountId() {
        return this.accountId;
    }

    /**
     * 设置 描述,对应字段 tab_data_device.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 tab_data_device.description
     */
    public String getDescription() {
        return this.description;
    }
}