package com.edu.erp.model;


/**
 * 页面退费课程数据
 *
 */
public class TCChangeCourse extends BaseObject {
	
	private static final long serialVersionUID = -7523890488698275959L;
	// 批改ID
	private Long change_id;
	// 订单ID
	private Long order_id;
	// 订单分录Id
	private Long order_course_id;
	// 转班转入课程商品ID
	private Long course_id;
	// 批改课次总数
	private Long course_times;
	// 退费总额
	private Double total_amount;
	// 补考勤费用
	private Double attend_amount;
	// 返预结转
	private Double pre_amount;
	// 0：转出  1：转入
	private Long transfer_flag;
	// 转班转入校区ID
	private Long branch_id;
	// 批改类型
	private Long change_type;
	// 批改编号
	private String change_no;
	
	private Double fee_returns_amount;
	
	private Double fee_deduction_amount;
	private Long premium_type;
	
	public Long getPremium_type() {
		return premium_type;
	}
	public void setPremium_type(Long premium_type) {
		this.premium_type = premium_type;
	}
	public Long getChange_id() {
		return change_id;
	}
	public void setChange_id(Long change_id) {
		this.change_id = change_id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getOrder_course_id() {
		return order_course_id;
	}
	public void setOrder_course_id(Long order_course_id) {
		this.order_course_id = order_course_id;
	}
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	public Long getCourse_times() {
		return course_times;
	}
	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public Double getAttend_amount() {
		return attend_amount;
	}
	public void setAttend_amount(Double attend_amount) {
		this.attend_amount = attend_amount;
	}
	public Double getPre_amount() {
		return pre_amount;
	}
	public void setPre_amount(Double pre_amount) {
		this.pre_amount = pre_amount;
	}
	public Long getTransfer_flag() {
		return transfer_flag;
	}
	public void setTransfer_flag(Long transfer_flag) {
		this.transfer_flag = transfer_flag;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Long getChange_type() {
		return change_type;
	}
	public void setChange_type(Long change_type) {
		this.change_type = change_type;
	}
	public String getChange_no() {
		return change_no;
	}
	public void setChange_no(String change_no) {
		this.change_no = change_no;
	}
	public Double getFee_returns_amount() {
		return fee_returns_amount;
	}
	public void setFee_returns_amount(Double fee_returns_amount) {
		this.fee_returns_amount = fee_returns_amount;
	}
	public Double getFee_deduction_amount() {
		return fee_deduction_amount;
	}
	public void setFee_deduction_amount(Double fee_deduction_amount) {
		this.fee_deduction_amount = fee_deduction_amount;
	}
	
	
}