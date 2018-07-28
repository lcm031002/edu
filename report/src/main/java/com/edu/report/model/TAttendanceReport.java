package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TAttendanceReport
 * @Description: 考勤消耗表
 *
 */
public class TAttendanceReport {
	
	private String task_flow; //任务批次

	private Long id;
	
	private String order_no; 				//报班单号
	
	private Long branch_id; 				//业务校区
	
	private String branch_name; 			//业务校区名称
	
	private Long bu_id; 					//业务团队
	
	private String bu_name; 				//业务团队名称
	
	private Long performance_branch_id;		//业绩校区	
	
	private String performance_branch_name;	//业绩校区名称
	
	private Long performance_bu_id;			//业绩团队
	
	private String performance_bu_name;		//业绩团队名称
	
	private String student_code;			//学生编号
	
	private String student_name;			//学生姓名
	
	private BigDecimal amount;				//金额
	
	private Long course_hour;				//消耗课时
	
	private Long course_id;					//课程id
	
	private String course_name;				//课程名称
	
	private String course_no;				//课程编码
	
	private Long course_count;				//报班总次数
	
	private Long course_time;				//课次
	
	private Long coop_org_id;				//合作机构ID
	
	private String coop_org_name;			//合作机构

	private Long teacher_id;				//老师ID

	private String teacher_name;			//老师名称

	private Long grade_id;					//年级ID
	
	private String grade_name;				//课程年级名称

	private String now_grade;               //当前年级
	
	private Long subject_id;				//科目ID
	
	private String subject_name;			//科目名称
	
	private BigDecimal length; 				//课时长度
	
	private String course_date;				//上课日期
	
	private String start_time;				//上课时间
	
	private String end_time;				//下课时间
	
	private String attend_date;				//考勤日期
	
	private Long attend_type;				//考勤状态
	
	private String attend_type_name;		//考勤状态名称
	
	private Long attend_type_last;			//上次考勤状态
	
	private String attend_type_last_name;	//上次考勤状态名称
	
	private Long is_next_month;				//是否跨月
	
	private String is_next_month_name;		//是否跨月名称

	private BigDecimal order_discount;       //折扣

	private Long counselor_id;				//学管师ID
	
	private String counselor_name;			//学管师名称
	
	private Long chinese_teacher_id;		//中教ID
	
	private String chinese_teacher_name;	//中教名称
	
	private BigDecimal chinese_teacher_length;	//中教长度
	
	private Long foreign_teacher_id;		//外教ID
	
	private String foreign_teacher_name;	//外教名称
	
	private BigDecimal foreign_teacher_length;	//外教长度

    private BigDecimal spoken_language_hour;	//外教长度
	
	private Integer business_type;			//业务模式
	
	private Long assteacher_id; // 辅导老师ID
	
	private String assteacher_name; // 辅导老师

	private String attend_encoding; // 考勤单号

	private String is_textbookstag;//是否教材

	private String room_name; //教室

	private String order_remark;//订单备注

	private String attendance_remark;//考勤备注

	private String chinese_teacher_remark;//高级参数中教备注

	private String foreign_teacher_remark;//高级参数外教备注

	private String course_branch_name;	//课程校区

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

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
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

	public Long getPerformance_bu_id() {
		return performance_bu_id;
	}

	public void setPerformance_bu_id(Long performance_bu_id) {
		this.performance_bu_id = performance_bu_id;
	}

	public String getPerformance_bu_name() {
		return performance_bu_name;
	}

	public void setPerformance_bu_name(String performance_bu_name) {
		this.performance_bu_name = performance_bu_name;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCourse_hour() {
		return course_hour;
	}

	public void setCourse_hour(Long course_hour) {
		this.course_hour = course_hour;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

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

	public Long getCourse_count() {
		return course_count;
	}

	public void setCourse_count(Long course_count) {
		this.course_count = course_count;
	}

	public Long getCourse_time() {
		return course_time;
	}

	public void setCourse_time(Long course_time) {
		this.course_time = course_time;
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

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getCourse_date() {
		return course_date;
	}

	public void setCourse_date(String course_date) {
		this.course_date = course_date;
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

	public String getAttend_date() {
		return attend_date;
	}

	public void setAttend_date(String attend_date) {
		this.attend_date = attend_date;
	}

	public Long getAttend_type() {
		return attend_type;
	}

	public void setAttend_type(Long attend_type) {
		this.attend_type = attend_type;
	}

	public String getAttend_type_name() {
		return attend_type_name;
	}

	public void setAttend_type_name(String attend_type_name) {
		this.attend_type_name = attend_type_name;
	}

	public Long getAttend_type_last() {
		return attend_type_last;
	}

	public void setAttend_type_last(Long attend_type_last) {
		this.attend_type_last = attend_type_last;
	}

	public String getAttend_type_last_name() {
		return attend_type_last_name;
	}

	public void setAttend_type_last_name(String attend_type_last_name) {
		this.attend_type_last_name = attend_type_last_name;
	}

	public Long getIs_next_month() {
		return is_next_month;
	}

	public void setIs_next_month(Long is_next_month) {
		this.is_next_month = is_next_month;
	}

	public String getIs_next_month_name() {
		return is_next_month_name;
	}

	public void setIs_next_month_name(String is_next_month_name) {
		this.is_next_month_name = is_next_month_name;
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

	public Long getChinese_teacher_id() {
		return chinese_teacher_id;
	}

	public void setChinese_teacher_id(Long chinese_teacher_id) {
		this.chinese_teacher_id = chinese_teacher_id;
	}

	public String getChinese_teacher_name() {
		return chinese_teacher_name;
	}

	public void setChinese_teacher_name(String chinese_teacher_name) {
		this.chinese_teacher_name = chinese_teacher_name;
	}

	public BigDecimal getChinese_teacher_length() {
		return chinese_teacher_length;
	}

	public void setChinese_teacher_length(BigDecimal chinese_teacher_length) {
		this.chinese_teacher_length = chinese_teacher_length;
	}

	public Long getForeign_teacher_id() {
		return foreign_teacher_id;
	}

	public void setForeign_teacher_id(Long foreign_teacher_id) {
		this.foreign_teacher_id = foreign_teacher_id;
	}

	public String getForeign_teacher_name() {
		return foreign_teacher_name;
	}

	public void setForeign_teacher_name(String foreign_teacher_name) {
		this.foreign_teacher_name = foreign_teacher_name;
	}

	public BigDecimal getForeign_teacher_length() {
		return foreign_teacher_length;
	}

	public void setForeign_teacher_length(BigDecimal foreign_teacher_length) {
		this.foreign_teacher_length = foreign_teacher_length;
	}

	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

    public Long getAssteacher_id() {
        return assteacher_id;
    }

    public void setAssteacher_id(Long assteacher_id) {
        this.assteacher_id = assteacher_id;
    }

    public String getAssteacher_name() {
        return assteacher_name;
    }

    public void setAssteacher_name(String assteacher_name) {
        this.assteacher_name = assteacher_name;
    }

	public String getAttend_encoding() {
		return attend_encoding;
	}

	public void setAttend_encoding(String attend_encoding) {
		this.attend_encoding = attend_encoding;
	}

	public String getNow_grade() { return now_grade; }

	public void setNow_grade(String now_grade) {this.now_grade = now_grade;}

    public BigDecimal getSpoken_language_hour() {
        return spoken_language_hour;
    }

    public void setSpoken_language_hour(BigDecimal spoken_language_hour) {
        this.spoken_language_hour = spoken_language_hour;
    }

	public String getIs_textbookstag() {
		return is_textbookstag;
	}

	public void setIs_textbookstag(String is_textbookstag) {
		this.is_textbookstag = is_textbookstag;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getOrder_remark() {
		return order_remark;
	}

	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}

	public String getAttendance_remark() {
		return attendance_remark;
	}

	public void setAttendance_remark(String attendance_remark) {
		this.attendance_remark = attendance_remark;
	}

	public String getChinese_teacher_remark() {
		return chinese_teacher_remark;
	}

	public void setChinese_teacher_remark(String chinese_teacher_remark) {
		this.chinese_teacher_remark = chinese_teacher_remark;
	}

	public String getForeign_teacher_remark() {
		return foreign_teacher_remark;
	}

	public void setForeign_teacher_remark(String foreign_teacher_remark) {
		this.foreign_teacher_remark = foreign_teacher_remark;
	}

	public BigDecimal getOrder_discount() {
		return order_discount;
	}

	public void setOrder_discount(BigDecimal order_discount) {
		this.order_discount = order_discount;
	}

	public String getCourse_branch_name() { return course_branch_name;	}

	public void setCourse_branch_name(String course_branch_name) {	this.course_branch_name = course_branch_name;}
}
