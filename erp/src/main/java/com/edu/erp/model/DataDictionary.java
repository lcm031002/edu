package com.edu.erp.model;

/**
 * @author lpe
 */
public class DataDictionary extends BaseObject 
{
	private String encoding;//编码
	private String data_name;//名称
	private String data_type;//类型
	private String description;//描述
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
