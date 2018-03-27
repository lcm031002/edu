package com.edu.erp.model;


public class TMoreCourseScheduling extends BaseObject{

	private static final long serialVersionUID = -7072837902296701052L;
	
	private Long moreCourseId;
	private Long courseDate;
	private String startTime;
	private String endTime;
	private Long weekNumber;
	private Long courseTimes;
	private Long courseCnt;
	private String remark;
	
	public Long getMoreCourseId() {
		return moreCourseId;
	}
	public void setMoreCourseId(Long moreCourseId) {
		this.moreCourseId = moreCourseId;
	}
	public Long getCourseDate() {
		return courseDate;
	}
	public void setCourseDate(Long courseDate) {
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
	public Long getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(Long weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Long getCourseTimes() {
		return courseTimes;
	}
	public void setCourseTimes(Long courseTimes) {
		this.courseTimes = courseTimes;
	}
	public Long getCourseCnt() {
		return courseCnt;
	}
	public void setCourseCnt(Long courseCnt) {
		this.courseCnt = courseCnt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
