package com.edu.erp.model;

/**
 * 学生跟踪信息
 * 
 */
public class StudentTracePlan extends BaseObject {

	private static final long serialVersionUID = 3324679112422824305L;

	private Long traceId;

	private String type;

	private String content;

	public Long getTraceId() {
		return traceId;
	}

	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
