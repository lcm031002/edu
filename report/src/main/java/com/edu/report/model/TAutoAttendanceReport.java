package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TAutoAttendanceReport
 * @Description: 自动考勤消耗表
 * @author ohs ohs@klxuexi.org
 * @date 2017年9月12日 下午2:44:12
 *
 */
public class TAutoAttendanceReport {
	
	private Long id;
	
	private String attend_code; 				//考勤单据
	
	private String teacher_code; 				//教师编码
	
	private String teacher_name; 			//教师名称

	private String subject_name; 				//科目

	private String course_name ; 			//课程名称
	
	private String manager_name ;				//学管师

	private String student_code;				//学生编号

	private String student_name;				//学生姓名

	private String class_date;				//上课日期

	private String grade_name;				//年级

	private String start_time;				//上课时间

	private String end_time;					//下课时间

	private Long class_times;				//上课分钟数

	private Double course_times;				//消耗课时

	private String attend_date;				//考勤日期

	private String teacher_sign_time;		//老师签到时间

	private String teacher_sign_type;		//老师签到方式

	private String student_sign_time;		//学生签到时间

	private String student_sign_type;		//学生签到方式

	private String order_no;					//报班单号

	private Long bu_id; 						//业务团队

	private String bu_name; 					//业务团队名称

	private Long branch_id;					//上课校区编号

	private String branch_name;				//上课校区

	private String task_flow; 				//任务批次

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttend_code() {
		return attend_code;
	}

	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}

	public String  getTeacher_code() {
		return teacher_code;
	}

	public void setTeacher_code(String  teacher_code) {
		this.teacher_code = teacher_code;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
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

	public String getClass_date() {
		return class_date;
	}

	public void setClass_date(String class_date) {
		this.class_date = class_date;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Long getClass_times() {
		return class_times;
	}

	public void setClass_times(Long class_times) {
		this.class_times = class_times;
	}

	public Double getCourse_times() {
		return course_times;
	}

	public void setCourse_times(Double course_times) {
		this.course_times = course_times;
	}

	public String getAttend_date() {
		return attend_date;
	}

	public void setAttend_date(String attend_date) {
		this.attend_date = attend_date;
	}

	public String getTeacher_sign_time() {
		return teacher_sign_time;
	}

	public void setTeacher_sign_time(String teacher_sign_time) {
		this.teacher_sign_time = teacher_sign_time;
	}

	public String getTeacher_sign_type() {
		return teacher_sign_type;
	}

	public void setTeacher_sign_type(String teacher_sign_type) {
		this.teacher_sign_type = teacher_sign_type;
	}

	public String getStudent_sign_time() {
		return student_sign_time;
	}

	public void setStudent_sign_time(String student_sign_time) {
		this.student_sign_time = student_sign_time;
	}

	public String getStudent_sign_type() {
		return student_sign_type;
	}

	public void setStudent_sign_type(String student_sign_type) {
		this.student_sign_type = student_sign_type;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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
}
