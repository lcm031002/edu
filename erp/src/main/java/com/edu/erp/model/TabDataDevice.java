package com.edu.erp.model;

/**
 * 设备管理
 */
public class TabDataDevice extends BaseObject {

    private static final long serialVersionUID = 272009015148065646L;

    private String device_name; // 设备名称
    private String simple_name; // 设备简称
    private Integer account_id;// 公司账号ID
    private String description;// 描述
    private String account_info; // 账户信息 账户名（账户卡号）
    private String device_code; // 设备代码
    private String account_name; // 公司账户名称
    private Long company_card_id;
    private Long bu_id;
    private String bu_name;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getSimple_name() {
        return simple_name;
    }

    public void setSimple_name(String simple_name) {
        this.simple_name = simple_name;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccount_info() {
        return account_info;
    }

    public void setAccount_info(String account_info) {
        this.account_info = account_info;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Long getCompany_card_id() {
        return company_card_id;
    }

    public void setCompany_card_id(Long company_card_id) {
        this.company_card_id = company_card_id;
    }

    public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}


	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("ID：");
        buff.append(getId());
        buff.append("，");
        buff.append("设备名称： ");
        buff.append(getDevice_name());
        buff.append("，");
        buff.append("设备简称：");
        buff.append(getSimple_name());
        buff.append("，");
        buff.append("公司账户ID：");
        buff.append(getAccount_id());
        buff.append("，");
        buff.append("描述：");
        buff.append(getDescription());
        return buff.toString();
    }
}
