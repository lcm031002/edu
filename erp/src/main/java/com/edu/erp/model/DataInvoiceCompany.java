package com.edu.erp.model;

public class DataInvoiceCompany extends BaseObject {

    // 公司名称
    private String companyName;

    // 描述
    private String description;

    /**
     * 设置 公司名称,对应字段 tab_data_invoice_company.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取 公司名称,对应字段 tab_data_invoice_company.company_name
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * 设置 描述,对应字段 tab_data_invoice_company.description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 描述,对应字段 tab_data_invoice_company.description
     */
    public String getDescription() {
        return this.description;
    }
}