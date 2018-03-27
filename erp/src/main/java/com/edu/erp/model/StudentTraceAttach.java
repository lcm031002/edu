package com.edu.erp.model;

/**
 * 学生跟踪信息
 * 
 * @author wsf
 * @date 2014-08-20
 */
public class StudentTraceAttach extends BaseObject {

	private static final long serialVersionUID = 3324679112422824305L;

	private Long traceId;

	private String fileName;

	private String fileUrl;

	private String fileType;

	public Long getTraceId() {
		return traceId;
	}

	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
