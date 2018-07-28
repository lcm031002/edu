package com.edu.erp.model;

import java.io.Serializable;

/**
 * 课程讲义信息
 * 
 *
 */
public class CourseLectureInfo implements Serializable {
	private static final long serialVersionUID = -2568954075161330978L;

	private Integer id;
	private String lectureId;
	private String subjectName;
	private String gradeName;
	private String courseDate;
	private String startTime;
	private String endTime;
	private String lectureName;
	private String studentName;
	private String teacherName;
	private String courseManagerName;
	private Integer printCount;
	private Boolean highlightFlag;//是否高亮提示：true-高亮，false
	private String branchName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLectureId() {
		return lectureId;
	}

	public void setLectureId(String lectureId) {
		this.lectureId = lectureId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLectureName() {
		return lectureName;
	}

	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseManagerName() {
		return courseManagerName;
	}

	public void setCourseManagerName(String courseManagerName) {
		this.courseManagerName = courseManagerName;
	}

	public Integer getPrintCount() {
		return printCount;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	public Boolean getHighlightFlag() {
		return highlightFlag;
	}

	public void setHighlightFlag(Boolean highlightFlag) {
		this.highlightFlag = highlightFlag;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}