package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TTeacherWorkloadReport
 * @Description: 培英班教师工作量
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月17日 下午4:04:40
 *
 */
public class TTeacherWorkloadReport {
	
	private String task_flow; //任务批次

	private Long id;
	
	private Long bu_id; 					//业务团队
	
	private String bu_name; 				//业务团队名称
	
	private Long branch_id; 				//业务校区
	
	private String branch_name; 			//业务校区名称
	
	private Long course_branch_id;			//课程校区	
	
	private String course_branch_name;		//课程校区名称

	private Long teacher_id;				//老师ID

	private String teacher_code;			//老师编码

	private String teacher_name;			//老师名称

	private Long assteacher_id;				//辅导老师ID
	
	private String assteacher_code;			//辅导老师编码
	
	private String assteacher_name;			//辅导老师名称

	private Long course_id;					//课程id
	
	private String course_no;				//课程编码
	
	private String course_name;				//课程名称
	
	private Long grade_id;					//年级
	 
	private String grade_name;           	//年级名称
	
	private Long subject_id; 				//科目
	
	private String subject_name; 			//科目名称
	
	private String teacher_group_name; 		//教研组名称
	
	private String start_time;				//上课时间
	
	private String end_time;				//下课时间
	
	private BigDecimal hour_length; 		//上课时长
	
	private String schedule_date;			//上课日期 (排课日期)
	
	private String business_date;			//考勤日期(业务日期)
	
	private Long course_time;				//课次
	
	private Long attend_students_num;			//出勤学生数
	
	private Long normal_attend_students_num;	//正常上课学生数
	
	private Long hangup_students_num;			//挂起学生数
	
	private Long tobe_students_num;				//应到学生数
	
	private Long listening_students_num;		//试听学生数
	
	private Long listen_rejection_students_num;	//试听拒缴学生数

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

	public Long getCourse_branch_id() {
		return course_branch_id;
	}

	public void setCourse_branch_id(Long course_branch_id) {
		this.course_branch_id = course_branch_id;
	}

	public String getCourse_branch_name() {
		return course_branch_name;
	}

	public void setCourse_branch_name(String course_branch_name) {
		this.course_branch_name = course_branch_name;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_code() {
		return teacher_code;
	}

	public void setTeacher_code(String teacher_code) {
		this.teacher_code = teacher_code;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public String getCourse_no() {
		return course_no;
	}

	public void setCourse_no(String course_no) {
		this.course_no = course_no;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
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

	public BigDecimal getHour_length() {
		return hour_length;
	}

	public void setHour_length(BigDecimal hour_length) {
		this.hour_length = hour_length;
	}

	public String getSchedule_date() {
		return schedule_date;
	}

	public void setSchedule_date(String schedule_date) {
		this.schedule_date = schedule_date;
	}

	public String getBusiness_date() {
		return business_date;
	}

	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
	}

	public Long getCourse_time() {
		return course_time;
	}

	public void setCourse_time(Long course_time) {
		this.course_time = course_time;
	}

	public Long getAttend_students_num() {
		return attend_students_num;
	}

	public void setAttend_students_num(Long attend_students_num) {
		this.attend_students_num = attend_students_num;
	}

	public Long getTobe_students_num() {
		return tobe_students_num;
	}

	public void setTobe_students_num(Long tobe_students_num) {
		this.tobe_students_num = tobe_students_num;
	}

	public Long getListening_students_num() {
		return listening_students_num;
	}

	public void setListening_students_num(Long listening_students_num) {
		this.listening_students_num = listening_students_num;
	}

	public Long getListen_rejection_students_num() {
		return listen_rejection_students_num;
	}

	public void setListen_rejection_students_num(Long listen_rejection_students_num) {
		this.listen_rejection_students_num = listen_rejection_students_num;
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

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getTeacher_group_name() {
		return teacher_group_name;
	}

	public void setTeacher_group_name(String teacher_group_name) {
		this.teacher_group_name = teacher_group_name;
	}

	public Long getNormal_attend_students_num() {
		return normal_attend_students_num;
	}

	public void setNormal_attend_students_num(Long normal_attend_students_num) {
		this.normal_attend_students_num = normal_attend_students_num;
	}

	public Long getHangup_students_num() {
		return hangup_students_num;
	}

	public void setHangup_students_num(Long hangup_students_num) {
		this.hangup_students_num = hangup_students_num;
	}

	public Long getAssteacher_id() {
		return assteacher_id;
	}

	public void setAssteacher_id(Long assteacher_id) {
		this.assteacher_id = assteacher_id;
	}

	public String getAssteacher_code() {
		return assteacher_code;
	}

	public void setAssteacher_code(String assteacher_code) {
		this.assteacher_code = assteacher_code;
	}

	public String getAssteacher_name() {
		return assteacher_name;
	}

	public void setAssteacher_name(String assteacher_name) {
		this.assteacher_name = assteacher_name;
	}
}
