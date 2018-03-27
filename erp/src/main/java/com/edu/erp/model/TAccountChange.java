package com.edu.erp.model;


/**
 * 账户变动记录表
 * 
 */
public class TAccountChange extends BaseObject {
	
	private static final long serialVersionUID = 4001236220378161997L;
	// ID
	private Long id;
	//账户ID
	private Long account_id;
	// 订单ID
	private Long order_id;
	// 单据ID
	private Long encoder_id;
	// 变更标识:0存入,1取出
	private Long change_flag;
	// 变更类型
	private Long change_type;
	// 变更金额
	private Double change_amount;
	// 变更前金额
	private Double pre_amount;
	// 变更后金额
	private Double next_amount;
	// 收付费类型
	private Long pay_mode;
	// old_id
	private String old_id;
	// 动户记录ID
	private Long dynamic_id;
	// 账户类型
	private Long account_type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getEncoder_id() {
		return encoder_id;
	}
	public void setEncoder_id(Long encoder_id) {
		this.encoder_id = encoder_id;
	}
	public Long getChange_flag() {
		return change_flag;
	}
	public void setChange_flag(Long change_flag) {
		this.change_flag = change_flag;
	}
	public Long getChange_type() {
		return change_type;
	}
	public void setChange_type(Long change_type) {
		this.change_type = change_type;
	}
	public Double getChange_amount() {
		return change_amount;
	}
	public void setChange_amount(Double change_amount) {
		this.change_amount = change_amount;
	}
	public Double getPre_amount() {
		return pre_amount;
	}
	public void setPre_amount(Double pre_amount) {
		this.pre_amount = pre_amount;
	}
	public Double getNext_amount() {
		return next_amount;
	}
	public void setNext_amount(Double next_amount) {
		this.next_amount = next_amount;
	}
	public Long getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(Long pay_mode) {
		this.pay_mode = pay_mode;
	}
	public String getOld_id() {
		return old_id;
	}
	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}
	public Long getDynamic_id() {
		return dynamic_id;
	}
	public void setDynamic_id(Long dynamic_id) {
		this.dynamic_id = dynamic_id;
	}
	public Long getAccount_type() {
		return account_type;
	}
	public void setAccount_type(Long account_type) {
		this.account_type = account_type;
	}
	
	

}
