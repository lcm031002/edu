package com.edu.report.model;

import java.math.BigDecimal;

/**
 * 剩余课时费用表
 * @ClassName: TSurplusAmountFee
 * @Description: 
 *
 */
public class TSurplusAmountFee {
	
	private String task_flow; //任务批次，备用，存储的信息是每次执行的任务批次，方便后续查找问题

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
	
	private String course_name;				//课程名称
	
	private Long remain_coursetime;			//剩余课时
	
	private BigDecimal unit_price;			//当前单价
	
	private BigDecimal forward_transfer;	//预结转
	
	private BigDecimal surplus_amount;		//剩余金额
	
	private Integer business_type;			//业务模式
	
	private String business_type_name;		//业务模式名称

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

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getRemain_coursetime() {
		return remain_coursetime;
	}

	public void setRemain_coursetime(Long remain_coursetime) {
		this.remain_coursetime = remain_coursetime;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public BigDecimal getForward_transfer() {
		return forward_transfer;
	}

	public void setForward_transfer(BigDecimal forward_transfer) {
		this.forward_transfer = forward_transfer;
	}

	public BigDecimal getSurplus_amount() {
		return surplus_amount;
	}

	public void setSurplus_amount(BigDecimal surplus_amount) {
		this.surplus_amount = surplus_amount;
	}

	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

	public String getBusiness_type_name() {
		return business_type_name;
	}

	public void setBusiness_type_name(String business_type_name) {
		this.business_type_name = business_type_name;
	}

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}
	
}
