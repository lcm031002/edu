package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TAttendanceMonthReport
 * @Description: 月度消耗表
 *
 */
public class TAttendanceMonthReport {
	
	private String task_flow; 				//任务批次

	private Long id;						//主键ID
	
	private Long student_id;				//学生ID
	
	private String student_code;			//学生编号
	
	private String student_name;			//学生姓名
	
	private Long bu_id; 					//业务团队
	
	private String bu_name; 				//业务团队名称
	
	private Long branch_id; 				//业务校区
	
	private String branch_name; 			//业务校区名称
	
	private BigDecimal month_order_amount;		//本月新增金额
	
	private Long month_order_course_length;		//本月新增课时
    
    private BigDecimal month_attend_amount;		//本月消耗金额
    
	private Long month_attend_course_length;	//本月消耗课时
    
    private BigDecimal month_lipei;				//本月理赔基金
    
    private String busi_date;					//业务日期

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

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
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

	public BigDecimal getMonth_order_amount() {
		return month_order_amount;
	}

	public void setMonth_order_amount(BigDecimal month_order_amount) {
		this.month_order_amount = month_order_amount;
	}

	public Long getMonth_order_course_length() {
		return month_order_course_length;
	}

	public void setMonth_order_course_length(Long month_order_course_length) {
		this.month_order_course_length = month_order_course_length;
	}

	public BigDecimal getMonth_attend_amount() {
		return month_attend_amount;
	}

	public void setMonth_attend_amount(BigDecimal month_attend_amount) {
		this.month_attend_amount = month_attend_amount;
	}

	public Long getMonth_attend_course_length() {
		return month_attend_course_length;
	}

	public void setMonth_attend_course_length(Long month_attend_course_length) {
		this.month_attend_course_length = month_attend_course_length;
	}

	public BigDecimal getMonth_lipei() {
		return month_lipei;
	}

	public void setMonth_lipei(BigDecimal month_lipei) {
		this.month_lipei = month_lipei;
	}

	public String getBusi_date() {
		return busi_date;
	}

	public void setBusi_date(String busi_date) {
		this.busi_date = busi_date;
	}
	
}
