package com.edu.erp.model;

public class CourseYDYInfo extends BaseObject {

	private static final long serialVersionUID = 1L;

	// 课程名
	private String course_name;
	// 课程编号
	private String course_no;
	// 年级名称
	private String grade_name;
	// 课程（课时）总次数
	private Long course_total_count;
	// 转入
	private Long change_in;
	// 转出
	private Long change_out;
	// 冻结课时
	private Long course_lock;
	// 退费课时
	private Long course_quit_times;
	// 考勤课时
	private Long course_attend_times;
	// 排课课时
	private Long course_cnt;
	// 剩余课程（课时）总次数
	private Long course_surplus_count;

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_no() {
		return course_no;
	}

	public void setCourse_no(String course_no) {
		this.course_no = course_no;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public Long getCourse_total_count() {
		return course_total_count;
	}

	public void setCourse_total_count(Long course_total_count) {
		this.course_total_count = course_total_count;
	}

	public Long getChange_in() {
		return change_in;
	}

	public void setChange_in(Long change_in) {
		this.change_in = change_in;
	}

	public Long getChange_out() {
		return change_out;
	}

	public void setChange_out(Long change_out) {
		this.change_out = change_out;
	}

	public Long getCourse_surplus_count() {
		return course_surplus_count;
	}

	public void setCourse_surplus_count(Long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public Long getCourse_lock() {
		return course_lock;
	}

	public void setCourse_lock(Long course_lock) {
		this.course_lock = course_lock;
	}

	public Long getCourse_quit_times() {
		return course_quit_times;
	}

	public void setCourse_quit_times(Long course_quit_times) {
		this.course_quit_times = course_quit_times;
	}

	public Long getCourse_attend_times() {
		return course_attend_times;
	}

	public void setCourse_attend_times(Long course_attend_times) {
		this.course_attend_times = course_attend_times;
	}

	public Long getCourse_cnt() {
		return course_cnt;
	}

	public void setCourse_cnt(Long course_cnt) {
		this.course_cnt = course_cnt;
	}

}
