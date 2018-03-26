package com.edu.erp.model;

public class BasCompanyAccount extends BaseObject {

    // 公司账号
    private String encoding;

    // 公司名称
    private String accountName;

    // 描述
    private String description;


    /**
     * 设置 公司账号,对应字段 t_bas_company_account.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 公司账号,对应字段 t_bas_company_account.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 公司名称,对应字段 t_bas_company_account.account_name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取 公司名称,对应字段 t_bas_company_account.account_name
     */
    public String getAccountName() {
        return this.accountName;
    }

    /**
     * 设置 描述,对应字段 t_bas_company_account.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 t_bas_company_account.description
     */
    public String getDescription() {
        return this.description;
    }
}