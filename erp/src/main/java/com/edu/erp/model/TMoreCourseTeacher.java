package com.edu.erp.model;


public class TMoreCourseTeacher extends BaseObject {
	private static final long serialVersionUID = -4503516701609135775L;

	private Long moreCourseId;
	private Long moreCourseTimeId;
	private Long teacherId;
	private String teacherName;
	private String teacherNo;
	private Long courseId;
	private String courseName;
	private String courseNo;

	public Long getMoreCourseId() {
		return moreCourseId;
	}

	public void setMoreCourseId(Long moreCourseId) {
		this.moreCourseId = moreCourseId;
	}

	public Long getMoreCourseTimeId() {
		return moreCourseTimeId;
	}

	public void setMoreCourseTimeId(Long moreCourseTimeId) {
		this.moreCourseTimeId = moreCourseTimeId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public final Long getCourseId() {
		return courseId;
	}

	public final void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public final String getCourseName() {
		return courseName;
	}

	public final void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public final String getCourseNo() {
		return courseNo;
	}

	public final void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public final String getTeacherNo() {
		return teacherNo;
	}

	public final void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}
	
}
