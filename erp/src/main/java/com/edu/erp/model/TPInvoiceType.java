package com.edu.erp.model;


/**
 * 发票类型
 * 
 * @author wCong
 *
 */
public class TPInvoiceType extends BaseObject{
	private static final long serialVersionUID = -5523306318423450951L;
	
	// 编码
	private String encoding;
	// 名称
	private String name;
	// 描述
	private String description;
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
