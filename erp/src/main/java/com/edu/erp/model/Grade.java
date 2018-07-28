package com.edu.erp.model;


/**
 * 年级管理
 */
public class Grade extends BaseObject{

	private static final long serialVersionUID = 3324679112422824305L;
	
	private	String encoding;//年级编码
	private String grade_name;//年级名称
	private Long last_id;//上一年级ID
	private String last_encoding;//上一年级编码
	private String last_name;//上一年级名称
	private Integer sort; // 排序
	private String description;//描述

	private String grade_info;// grade_name + encoding
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getLast_encoding() {
		return last_encoding;
	}
	public void setLast_encoding(String last_encoding) {
		this.last_encoding = last_encoding;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getLast_id() {
		return last_id;
	}
	public void setLast_id(Long last_id) {
		this.last_id = last_id;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getGrade_info() {
		return grade_info;
	}
	public void setGrade_info(String grade_info) {
		this.grade_info = grade_info;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("年级名称： ");
		buff.append(getGrade_name());
		buff.append("，");
		buff.append("年级编码：");
		buff.append(getEncoding());
		buff.append("，");
		buff.append("上一年级ID：");
		buff.append(getLast_id());
		buff.append("，");
		buff.append("排序：");
		buff.append(getSort());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}

}
