package com.edu.erp.model;


/**
 * 资金用途
 * 
 */
public class TFinFeeUse extends BaseObject {
	
	private static final long serialVersionUID = -4949554181988023718L;
	// 费用ID
	private Long id;
	//资金ID
	private Long fin_fee_id;
	// 资金用途
	private Long use_type;
	// 订单ID
	private Long order_id;
	// 金额
	private Double fee_amount;
	// 单据ID
	private Long encoder_id;
	
	private String old_id;
	// 确认日期
	private Long account_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFin_fee_id() {
		return fin_fee_id;
	}
	public void setFin_fee_id(Long fin_fee_id) {
		this.fin_fee_id = fin_fee_id;
	}
	public Long getUse_type() {
		return use_type;
	}
	public void setUse_type(Long use_type) {
		this.use_type = use_type;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Double getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}
	public Long getEncoder_id() {
		return encoder_id;
	}
	public void setEncoder_id(Long encoder_id) {
		this.encoder_id = encoder_id;
	}
	public String getOld_id() {
		return old_id;
	}
	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}
	public Long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}

}
