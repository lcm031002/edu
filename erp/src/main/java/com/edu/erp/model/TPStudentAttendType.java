package com.edu.erp.model;


/**
 * 学生考勤状态
 *
 */
public class TPStudentAttendType extends BaseObject{
	private static final long serialVersionUID = 512521607708392873L;
	
	// 编码
	private String encoding;
	// 名称
	private String name;
	// 班级课是否可用（Y/N）
	private char bjk_enabled;
	// 晚辅导是否可用（Y/N）
	private char wfd_enabled;
	// 一对一是否可用（Y/N）
	private char ydy_enabled;
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
	public char getBjk_enabled() {
		return bjk_enabled;
	}
	public void setBjk_enabled(char bjk_enabled) {
		this.bjk_enabled = bjk_enabled;
	}
	public char getWfd_enabled() {
		return wfd_enabled;
	}
	public void setWfd_enabled(char wfd_enabled) {
		this.wfd_enabled = wfd_enabled;
	}
	public char getYdy_enabled() {
		return ydy_enabled;
	}
	public void setYdy_enabled(char ydy_enabled) {
		this.ydy_enabled = ydy_enabled;
	}
}
