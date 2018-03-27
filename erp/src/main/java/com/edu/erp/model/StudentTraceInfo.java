package com.edu.erp.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 学生跟踪信息
 * 
 * @author wsf
 * @date 2014-08-20
 */
public class StudentTraceInfo extends BaseObject {

	private static final long serialVersionUID = 3324679112422824305L;
	
	private Long studentId;

	private String traceDate;

	private String traceTime;
	
	private String relation;

	private String relationName;

	private String traceType;

	private String traceTypeName;

	private String tracePurpose;
	
	private String studentName;

	private String statusName;

	private String hasAttach;

	private String relationTitle;

	List<StudentTraceDetail> studentTraceDetailList;

	List<StudentTracePlan> studentTracePlanList;

	List<StudentTraceAttach> studentTraceAttachList;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getTraceDate() {
		return traceDate;
	}

	public void setTraceDate(String traceDate) {
		this.traceDate = traceDate;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getTraceType() {
		return traceType;
	}

	public void setTraceType(String traceType) {
		this.traceType = traceType;
	}

	public String getTraceTypeName() {
		return traceTypeName;
	}

	public void setTraceTypeName(String traceTypeName) {
		this.traceTypeName = traceTypeName;
	}

	public String getTracePurpose() {
		return tracePurpose;
	}

	public void setTracePurpose(String tracePurpose) {
		this.tracePurpose = tracePurpose;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getHasAttach() {
		return hasAttach;
	}

	public void setHasAttach(String hasAttach) {
		this.hasAttach = hasAttach;
	}

	public List<StudentTraceDetail> getStudentTraceDetailList() {
		return studentTraceDetailList;
	}

	public void setStudentTraceDetailList(List<StudentTraceDetail> studentTraceDetailList) {
		this.studentTraceDetailList = studentTraceDetailList;
	}

	public List<StudentTracePlan> getStudentTracePlanList() {
		return studentTracePlanList;
	}

	public void setStudentTracePlanList(List<StudentTracePlan> studentTracePlanList) {
		this.studentTracePlanList = studentTracePlanList;
	}

	public List<StudentTraceAttach> getStudentTraceAttachList() {
		return studentTraceAttachList;
	}

	public void setStudentTraceAttachList(List<StudentTraceAttach> studentTraceAttachList) {
		this.studentTraceAttachList = studentTraceAttachList;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getRelationTitle() {
		return relationTitle;
	}

	public void setRelationTitle(String relationTitle) {
		this.relationTitle = relationTitle;
	}
}
