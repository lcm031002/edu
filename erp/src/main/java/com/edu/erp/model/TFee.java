package com.edu.erp.model;

import java.util.Date;

/**
 * 费用主表
 * 
 */
public class TFee extends BaseObject {
	
	private static final long serialVersionUID = -6727164746143955829L;
	// 费用ID
	private Long id;
	//订单ID
	private Long order_id;
	// 费用类型
	private Long fee_type;
	// 收付费标识
	private Long fee_flag;
	// 收付费方式
	private Long pay_mode;
	// 费用总额
	private Double fee_amount;
	// 费用发生日期
	private Date insert_time;
	// 费用确认日期
	private Date finish_time;
	//费用状态
	private Long fee_status;
	// 单据ID
	private Long encoder_id;
	// 操作类型
	private Long operate_type;
	 // 操作编号
	private String operate_no;
	
	private Date old_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getFee_type() {
		return fee_type;
	}

	public void setFee_type(Long fee_type) {
		this.fee_type = fee_type;
	}

	public Long getFee_flag() {
		return fee_flag;
	}

	public void setFee_flag(Long fee_flag) {
		this.fee_flag = fee_flag;
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

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public Long getFee_status() {
		return fee_status;
	}

	public void setFee_status(Long fee_status) {
		this.fee_status = fee_status;
	}

	public Long getEncoder_id() {
		return encoder_id;
	}

	public void setEncoder_id(Long encoder_id) {
		this.encoder_id = encoder_id;
	}

	public Long getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(Long operate_type) {
		this.operate_type = operate_type;
	}

	public String getOperate_no() {
		return operate_no;
	}

	public void setOperate_no(String operate_no) {
		this.operate_no = operate_no;
	}

	public Date getOld_id() {
		return old_id;
	}

	public void setOld_id(Date old_id) {
		this.old_id = old_id;
	}

}
