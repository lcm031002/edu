package com.edu.erp.model;

import java.util.Date;

/**
 * 资金往来表
 * 
 */
public class TFinFee extends BaseObject {
	
	private static final long serialVersionUID = 4593241678063422791L;
	// 费用ID
	private Long id;
	//学生ID
	private Long student_id;
	// 收付费标识
	private Long pay_flag;
	// 收付费方式
	private Long pay_mode;
	// 费用总额
	private Double fee_amount;
	// 录入时间日期
	private Date insert_time;
	private Long insert_user;
	// 确认日期
	private Date confirm_time;
	private Long confirm_user;
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
	public Long getPay_flag() {
		return pay_flag;
	}
	public void setPay_flag(Long pay_flag) {
		this.pay_flag = pay_flag;
	}
	public Long getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(Long pay_mode) {
		this.pay_mode = pay_mode;
	}
	public Double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}
	public Long getInsert_user() {
		return insert_user;
	}
	public void setInsert_user(Long insert_user) {
		this.insert_user = insert_user;
	}
	public Date getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(Date confirm_time) {
		this.confirm_time = confirm_time;
	}
	public Long getConfirm_user() {
		return confirm_user;
	}
	public void setConfirm_user(Long confirm_user) {
		this.confirm_user = confirm_user;
	}
	

}
