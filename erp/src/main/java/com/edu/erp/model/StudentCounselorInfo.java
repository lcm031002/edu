package com.edu.erp.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 咨询学管
 * 
 * @author wCong
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StudentCounselorInfo extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	
	private Long stu_counselor_id;
	// 学生ID
	private Long student_id;
	// 咨询(学馆)ID,指向的员工id
	private Long counselor_id;
	// 所在团队
	private Long bu_id;
	// 所在校区
	private Long branch_id;
	/* 1：咨询师   2：学管师 */
	private Long counselor_type;
	// 开始日期
	private String start_date;
	// 结束日期
	private String end_date;
	// 是否有效
	/* 1：有效  0：无效  */
	private Integer is_valid;
	
	private String remark;
	
	private String encoding;
	private String name; // 学管师名称
	private String bu_name;
	private String valid_name;
	
	private String student_name;

	private String student_encoding;
	private String  school_name;
	private String  grade_name;
	private Long  course_surplus_count;
	private Long  course_schedule_count;
	private String counselor_name; // 咨询师名称
	
	public Long getStu_counselor_id() {
		return stu_counselor_id;
	}
	
	public void setStu_counselor_id(Long stu_counselor_id) {
		this.stu_counselor_id = stu_counselor_id;
	}
	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public Long getCounselor_id() {
		return counselor_id;
	}
	public void setCounselor_id(Long counselor_id) {
		this.counselor_id = counselor_id;
	}
	public Long getCounselor_type() {
		return counselor_type;
	}
	public void setCounselor_type(Long counselor_type) {
		this.counselor_type = counselor_type;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public Integer getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}
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
	public String getValid_name() {
		return valid_name;
	}
	public void setValid_name(String valid_name) {
		this.valid_name = valid_name;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStudent_encoding() {
		return student_encoding;
	}

	public void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public Long getCourse_surplus_count() {
		return course_surplus_count;
	}

	public void setCourse_surplus_count(Long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public Long getCourse_schedule_count() {
		return course_schedule_count;
	}

	public void setCourse_schedule_count(Long course_schedule_count) {
		this.course_schedule_count = course_schedule_count;
	}

	public String getCounselor_name() {
		return counselor_name;
	}

	public void setCounselor_name(String counselor_name) {
		this.counselor_name = counselor_name;
	}

	@Override
	public String toString() {
		String content = ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		return content;
	}
	
	
}
