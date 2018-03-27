package com.edu.erp.model;

import java.util.Date;

/**
 * 费用主表
 * 
 */
public class TEncoder extends BaseObject {
	
	private static final long serialVersionUID = -6727164746143955829L;
	// 费用ID
	private Long id;
	//单据号
	private String encoder_no;
	//单据类型（批改类型）
	private Long encoder_type;
	// 收付费标识
	private Long fee_flag;
	// 订单ID
	private Long order_id;
	// 单据批文
	private String encoder_notice;
	// 业务类型
	private Long busi_type;
	// 业务ID
	private Long busi_id;
	//创建人
	private Long create_user;
	//创建时间
	private Date create_time;
	// 修改人
	private Long update_user;
	// 修改时间
	private Date update_time;
	 // 财务确认时间
	private Date confirm_time;
	// 总金额
	private Double fee_amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEncoder_no() {
		return encoder_no;
	}
	public void setEncoder_no(String encoder_no) {
		this.encoder_no = encoder_no;
	}
	public Long getEncoder_type() {
		return encoder_type;
	}
	public void setEncoder_type(Long encoder_type) {
		this.encoder_type = encoder_type;
	}
	public Long getFee_flag() {
		return fee_flag;
	}
	public void setFee_flag(Long fee_flag) {
		this.fee_flag = fee_flag;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getEncoder_notice() {
		return encoder_notice;
	}
	public void setEncoder_notice(String encoder_notice) {
		this.encoder_notice = encoder_notice;
	}
	public Long getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(Long busi_type) {
		this.busi_type = busi_type;
	}
	public Long getBusi_id() {
		return busi_id;
	}
	public void setBusi_id(Long busi_id) {
		this.busi_id = busi_id;
	}
	public Long getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Long getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(Long update_user) {
		this.update_user = update_user;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Date getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(Date confirm_time) {
		this.confirm_time = confirm_time;
	}
	public Double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}

	
}
