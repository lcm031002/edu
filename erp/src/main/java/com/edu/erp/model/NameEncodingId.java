package com.edu.erp.model;

/**
 * 名称，编码，ID组合对象
 *
 */
public class NameEncodingId {
	
	private String name;
	
	private String encoding;
	
	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
