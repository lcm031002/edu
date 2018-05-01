package com.edu.report.model;

/**
 * 电商在线支付明细
 * @author yecb
 *
 */
public class TOnlineOrderReport {
    private String eb_no;
    private String order_no;
    private String pay_time;
    private String payment_way;
    private String pay_amount;
    private String login_no;
    private String student_name;
    private String branch_name;
    private String course_name;
    private String subject_name;
    private String coupon_type;
	public String getEb_no() {
		return eb_no;
	}
	public void setEb_no(String eb_no) {
		this.eb_no = eb_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getLogin_no() {
		return login_no;
	}
	public void setLogin_no(String login_no) {
		this.login_no = login_no;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}

	public String getPayment_way() {
		return payment_way;
	}

	public void setPayment_way(String payment_way) {
		this.payment_way = payment_way;
	}
}
