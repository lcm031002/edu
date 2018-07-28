package com.edu.report.model;

/**
 * @ClassName: TOrderPerformance
 * @Description: 课程顾问绩效表
 *
 */
public class TOrderPerformance {
    private Long id;
    private String task_flow;
    private String order_number;
    private String order_no;
    private Long bu_id;
    private Long branch_id;
    private String branch_name;    
    private Long performance_branch_id;
    private String performance_branch_name;    
    private String business_date;
    private String operate_type_name;
    private String student_code;
    private String student_name;    
    private Long course_time;   
    private String course_name;   
    private Long season_id;
    private String season_name;
    private String grade_name;   
    private String subject_name;   
    private String teacher_encoding;
    private String teacher_name;
    private Long agent_id;
    private String agent_name;    
    private Long original_create_user;
    private String original_create_user_name;
    private String order_type;
    private String student_status;
    private String recorder;    
    private String performancer;

    private Long curr_season_order;
    private Long newalrate_subject;
	private Long newalrate;
	private Long new_student;

	private Double performance;
	private Long  performance_id;
	private Long course_id;
	private Long grade_id;
	private Long subject_id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTask_flow() {
		return task_flow;
	}
	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
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
	public Long getPerformance_branch_id() {
		return performance_branch_id;
	}
	public void setPerformance_branch_id(Long performance_branch_id) {
		this.performance_branch_id = performance_branch_id;
	}
	public String getPerformance_branch_name() {
		return performance_branch_name;
	}
	public void setPerformance_branch_name(String performance_branch_name) {
		this.performance_branch_name = performance_branch_name;
	}
	public String getBusiness_date() {
		return business_date;
	}
	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
	}
	public String getOperate_type_name() {
		return operate_type_name;
	}
	public void setOperate_type_name(String operate_type_name) {
		this.operate_type_name = operate_type_name;
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
	public Long getCourse_time() {
		return course_time;
	}
	public void setCourse_time(Long course_time) {
		this.course_time = course_time;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Long getSeason_id() {
		return season_id;
	}
	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
	}
	public String getSeason_name() {
		return season_name;
	}
	public void setSeason_name(String season_name) {
		this.season_name = season_name;
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
	public String getTeacher_encoding() {
		return teacher_encoding;
	}
	public void setTeacher_encoding(String teacher_encoding) {
		this.teacher_encoding = teacher_encoding;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public Long getOriginal_create_user() {
		return original_create_user;
	}
	public void setOriginal_create_user(Long original_create_user) {
		this.original_create_user = original_create_user;
	}
	public String getOriginal_create_user_name() {
		return original_create_user_name;
	}
	public void setOriginal_create_user_name(String original_create_user_name) {
		this.original_create_user_name = original_create_user_name;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getStudent_status() {
		return student_status;
	}
	public void setStudent_status(String student_status) {
		this.student_status = student_status;
	}
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getPerformancer() {
		return performancer;
	}
	public void setPerformancer(String performancer) {
		this.performancer = performancer;
	}

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public Long getPerformance_id() {
		return performance_id;
	}

	public void setPerformance_id(Long performance_id) {
		this.performance_id = performance_id;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public Long getCurr_season_order() {
		return curr_season_order;
	}

	public void setCurr_season_order(Long curr_season_order) {
		this.curr_season_order = curr_season_order;
	}

	public Long getNewalrate_subject() {
		return newalrate_subject;
	}

	public void setNewalrate_subject(Long newalrate_subject) {
		this.newalrate_subject = newalrate_subject;
	}

	public Long getNewalrate() {
		return newalrate;
	}

	public void setNewalrate(Long newalrate) {
		this.newalrate = newalrate;
	}

	public Long getNew_student() {
		return new_student;
	}

	public void setNew_student(Long new_student) {
		this.new_student = new_student;
	}
}
