package com.edu.erp.model;

/**
 * 开票单位
 * 
 */
public class DataInvoiceCompany extends BaseObject{

	private static final long serialVersionUID = 2645722728499284849L;
	
	private String company_name; //开票单位名称
	private String description;//描述
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("单位名称： ");
		buff.append(getCompany_name());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
		}
}
