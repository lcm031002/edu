package com.edu.erp.model;

import java.util.Date;

/**
 *费用明细表
 * 
 */
public class TFeeDetail extends BaseObject {
	private static final long serialVersionUID = 6296927407699537100L;

	// 费用ID
	private Long fee_id;
	// 订单ID
	private Long order_id;
	// 订单分录ID
	private Long order_detail_id;
	// 费用类型
	private Long fee_type;
	// 收付费标识
	private Long fee_flag;
	// 费用总额
	private Double fee_amount;
	// 课次总数
	private Long course_sum;
	// 操作类型
	private Long operate_type;
	 // 操作编号
	private Long operate_no;
	
	private String old_id;

	public Long getFee_id() {
		return fee_id;
	}

	public void setFee_id(Long fee_id) {
		this.fee_id = fee_id;
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

	public Double getFee_amount() {
		return fee_amount;
	}

	public void setFee_amount(Double fee_amount) {
		this.fee_amount = fee_amount;
	}

	public Long getCourse_sum() {
		return course_sum;
	}

	public void setCourse_sum(Long course_sum) {
		this.course_sum = course_sum;
	}

	public Long getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(Long operate_type) {
		this.operate_type = operate_type;
	}

	public Long getOperate_no() {
		return operate_no;
	}

	public void setOperate_no(Long operate_no) {
		this.operate_no = operate_no;
	}

	public String getOld_id() {
		return old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	

}
