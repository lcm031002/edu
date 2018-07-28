package com.edu.report.model;

/**
 * @ClassName: TReportGxhStudentStatus
 * @Description: 学生状态表
 *
 */
public class TReportGxhStudentStatus {
	
	private Long bu_id;
	
	private String bu_name;

	private Long branch_id;

	private String branch_name;
	
	private String student_code;

	private String student_name;

	private Long counselor_id;

	private String counselor_name;

	private String grade_name;

	private String year_month;

	private Long   status_code;

	private String	status_name;

	private Long zt_new;

	private Long zt_normal;

	private Long zt_sleep;

	private Long zt_count;

	private Long cs_new;

	private Long cs_normal;

	private Long cs_end;

	private Long cs_count;

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getStudent_code() {
		return student_code;
	}

	public void setStudent_code(String student_code) {
		this.student_code = student_code;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Long getCounselor_id() {
		return counselor_id;
	}

	public void setCounselor_id(Long counselor_id) {
		this.counselor_id = counselor_id;
	}

	public String getCounselor_name() {
		return counselor_name;
	}

	public void setCounselor_name(String counselor_name) {
		this.counselor_name = counselor_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public Long getStatus_code() {
		return status_code;
	}

	public void setStatus_code(Long status_code) {
		this.status_code = status_code;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public Long getZt_new() {
		return zt_new;
	}

	public void setZt_new(Long zt_new) {
		this.zt_new = zt_new;
	}

	public Long getZt_normal() {
		return zt_normal;
	}

	public void setZt_normal(Long zt_normal) {
		this.zt_normal = zt_normal;
	}

	public Long getZt_sleep() {
		return zt_sleep;
	}

	public void setZt_sleep(Long zt_sleep) {
		this.zt_sleep = zt_sleep;
	}

	public Long getCs_new() {
		return cs_new;
	}

	public void setCs_new(Long cs_new) {
		this.cs_new = cs_new;
	}

	public Long getCs_normal() {
		return cs_normal;
	}

	public void setCs_normal(Long cs_normal) {
		this.cs_normal = cs_normal;
	}

	public Long getCs_end() {
		return cs_end;
	}

	public void setCs_end(Long cs_end) {
		this.cs_end = cs_end;
	}

	public Long getZt_count() {
		return zt_count;
	}

	public void setZt_count(Long zt_count) {
		this.zt_count = zt_count;
	}

	public Long getCs_count() {
		return cs_count;
	}

	public void setCs_count(Long cs_count) {
		this.cs_count = cs_count;
	}
}
