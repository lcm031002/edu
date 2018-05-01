package com.edu.report.model;


public class TReportRenewalRateSum {

	private Long bu_id;
	private String teacher_name;
	private Long teacher_type;
	private Long grade_id;
	private String grade_name;
	private String subject_name;
	private String group_name;
	private Long last_season_id;
	private String last_season_name;
	private Long curr_season_id;
	private String curr_season_name;
	private Long last_students;
	private Long order_students;
	private Long curr_students;
	private String estimate_rate;
	private String actual_rate;

	
	
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public Long getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Long getLast_season_id() {
		return last_season_id;
	}
	public void setLast_season_id(Long last_season_id) {
		this.last_season_id = last_season_id;
	}
	public String getLast_season_name() {
		return last_season_name;
	}
	public void setLast_season_name(String last_season_name) {
		this.last_season_name = last_season_name;
	}
	public Long getCurr_season_id() {
		return curr_season_id;
	}
	public void setCurr_season_id(Long curr_season_id) {
		this.curr_season_id = curr_season_id;
	}
	public String getCurr_season_name() {
		return curr_season_name;
	}
	public void setCurr_season_name(String curr_season_name) {
		this.curr_season_name = curr_season_name;
	}
	public Long getLast_students() {
		return last_students;
	}
	public void setLast_students(Long last_students) {
		this.last_students = last_students;
	}
	public Long getOrder_students() {
		return order_students;
	}
	public void setOrder_students(Long order_students) {
		this.order_students = order_students;
	}
	public Long getCurr_students() {
		return curr_students;
	}
	public void setCurr_students(Long curr_students) {
		this.curr_students = curr_students;
	}
	public String getEstimate_rate() {
		return estimate_rate;
	}
	public void setEstimate_rate(String estimate_rate) {
		this.estimate_rate = estimate_rate;
	}
	public String getActual_rate() {
		return actual_rate;
	}
	public void setActual_rate(String actual_rate) {
		this.actual_rate = actual_rate;
	}
	public Long getTeacher_type() { return teacher_type;}
	public void setTeacher_type(Long teacher_type) {this.teacher_type = teacher_type;	}
}
