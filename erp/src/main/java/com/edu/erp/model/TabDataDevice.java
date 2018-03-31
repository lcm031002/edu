package com.edu.erp.model;

/**
 * 设备管理
 */
public class TabDataDevice extends BaseObject {

    private static final long serialVersionUID = 272009015148065646L;

    private String pos_name; // 设备名称
    private String short_post_name; // 设备简称
    private Integer account_id;// 公司账号ID
    private String description;// 描述
    private String account_info; // 账户信息 账户名（账户卡号）
    private String encoding; // 设备代码
    private String account_name; // 公司账户名称
    private Long company_card_id;

    public String getPos_name() {
        return pos_name;
    }

    public void setPos_name(String pos_name) {
        this.pos_name = pos_name;
    }

    public String getShort_post_name() {
        return short_post_name;
    }

    public void setShort_post_name(String short_post_name) {
        this.short_post_name = short_post_name;
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

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
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

	public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("ID：");
        buff.append(getId());
        buff.append("，");
        buff.append("设备名称： ");
        buff.append(getPos_name());
        buff.append("，");
        buff.append("设备简称：");
        buff.append(getShort_post_name());
        buff.append("，");
        buff.append("公司账户ID：");
        buff.append(getAccount_id());
        buff.append("，");
        buff.append("描述：");
        buff.append(getDescription());
        return buff.toString();
    }
}
