package com.edu.erp.model;

import java.math.BigDecimal;

public class TOrderQuitInput extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -3100125134281459636L;
	private Long id;// 主键
	private Long order_id;// 订单id
	private Long order_detail_id;// 订单分录id
	private Long quit_count;// 退费次数
	private String quit_lesson;// 退费课次
	private BigDecimal commission_change;// 手续费
	private Long order_change_id;// 批改id
	private BigDecimal quit_money;// 退费金额
	private BigDecimal fee_deduction;// 退费补扣
	private BigDecimal return_pre_forward;// 返预结转
	private BigDecimal extra_income;// 额外收入

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

	public Long getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public Long getQuit_count() {
		return quit_count;
	}

	public void setQuit_count(Long quit_count) {
		this.quit_count = quit_count;
	}

	public String getQuit_lesson() {
		return quit_lesson;
	}

	public void setQuit_lesson(String quit_lesson) {
		this.quit_lesson = quit_lesson;
	}

	public BigDecimal getCommission_change() {
		return commission_change;
	}

	public void setCommission_change(BigDecimal commission_change) {
		this.commission_change = commission_change;
	}

	public Long getOrder_change_id() {
		return order_change_id;
	}

	public void setOrder_change_id(Long order_change_id) {
		this.order_change_id = order_change_id;
	}

	public BigDecimal getQuit_money() {
		return quit_money;
	}

	public void setQuit_money(BigDecimal quit_money) {
		this.quit_money = quit_money;
	}

	public BigDecimal getFee_deduction() {
		return fee_deduction;
	}

	public void setFee_deduction(BigDecimal fee_deduction) {
		this.fee_deduction = fee_deduction;
	}

	public BigDecimal getReturn_pre_forward() {
		return return_pre_forward;
	}

	public void setReturn_pre_forward(BigDecimal return_pre_forward) {
		this.return_pre_forward = return_pre_forward;
	}

	public BigDecimal getExtra_income() {
		return extra_income;
	}

	public void setExtra_income(BigDecimal extra_income) {
		this.extra_income = extra_income;
	}

}
