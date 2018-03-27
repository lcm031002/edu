package com.edu.erp.model;


/**
 * 课程科目表
 * 
 * @author wCong
 *
 */
public class TPSubject extends BaseObject{
	private static final long serialVersionUID = -139597208344951302L;
	
	// 编码
	private String encoding;
	// 名称
	private String name;
	// 描述
	private String description;

	/**
	 * 团队ID
	 */
	private Long buId;
	/**
	 * 团队名称
	 */
	private String buName;

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

	public Long getBuId() {
		return buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public String getBuName() {
		return buName;
	}

	public void setBuName(String buName) {
		this.buName = buName;
	}
}
