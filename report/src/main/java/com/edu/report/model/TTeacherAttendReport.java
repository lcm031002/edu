package com.edu.report.model;

/**
 * @ClassName: TTeacherAttendReport
 * @Description: 晚辅导教师考勤表
 * @author zenglw zenglw@klxuexi.org
 * @date 2017年5月17日 上午10:06:12
 *
 */
public class TTeacherAttendReport {

	private Long id;
	private Long branch_id;// 校区
	private String branch_name;// 校区名称
	private Long bu_id;// 团队
	private String bu_name;// 团队名称
	private String teacher_code;// 教师编号
	private Long teacher_id;// 教师ID
	private String teacher_name;// 教师姓名
	private String teacher_attend_date;// 授课时间
	private Long teacher_attend_length;// 授课课时
	private Long teacher_attend_type;// 考勤状态
	private String teacher_attend_type_name;// 考勤状态名称
	private String attend_date;// 考勤时间
	private Long attender_id;// 考勤人
	private String attender_name;// 考勤人名称
	private String task_flow;// 运行批次

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTeacher_code() {
		return teacher_code;
	}

	public void setTeacher_code(String teacher_code) {
		this.teacher_code = teacher_code;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_attend_date() {
		return teacher_attend_date;
	}

	public void setTeacher_attend_date(String teacher_attend_date) {
		this.teacher_attend_date = teacher_attend_date;
	}

	public Long getTeacher_attend_length() {
		return teacher_attend_length;
	}

	public void setTeacher_attend_length(Long teacher_attend_length) {
		this.teacher_attend_length = teacher_attend_length;
	}

	public Long getTeacher_attend_type() {
		return teacher_attend_type;
	}

	public void setTeacher_attend_type(Long teacher_attend_type) {
		this.teacher_attend_type = teacher_attend_type;
	}

	public String getTeacher_attend_type_name() {
		return teacher_attend_type_name;
	}

	public void setTeacher_attend_type_name(String teacher_attend_type_name) {
		this.teacher_attend_type_name = teacher_attend_type_name;
	}

	public String getAttend_date() {
		return attend_date;
	}

	public void setAttend_date(String attend_date) {
		this.attend_date = attend_date;
	}

	public Long getAttender_id() {
		return attender_id;
	}

	public void setAttender_id(Long attender_id) {
		this.attender_id = attender_id;
	}

	public String getAttender_name() {
		return attender_name;
	}

	public void setAttender_name(String attender_name) {
		this.attender_name = attender_name;
	}

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
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
}
