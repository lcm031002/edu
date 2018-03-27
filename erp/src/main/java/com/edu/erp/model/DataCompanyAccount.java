package com.edu.erp.model;


public class DataCompanyAccount extends BaseObject
{
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -7508999701855465872L;

	private String account_name;//账户名称
	
	private String account_num;//账号
	
	private String description;//描述
	
	private Long city_id;//城市
	
	private String account_info;// account_name + account_num
	
	private String org_name;
	
	private String employee_name;
	
	private String statusstr;
	
	
	public String getStatusstr() {
		return statusstr;
	}

	public void setStatusstr(String statusstr) {
		this.statusstr = statusstr;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getAccount_name() {
		return account_name;
	}

	public String getAccount_num() {
		return account_num;
	}

	public Long getCity_id() {
		return city_id;
	}

	public String getDescription() {
		return description;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
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

	public String toString(){
		StringBuffer buff  = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("账户名称：");
		buff.append(getAccount_name());
		buff.append("，");
		buff.append("账户号码：");
		buff.append(getAccount_num());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}
}
