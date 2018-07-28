package com.edu.erp.model;


/**
 * 退费课次表
 *
 */
public class TCChangeCourseTimes extends BaseObject {
	
	private static final long serialVersionUID = -7523890488698275959L;
	// 批改ID
	private Long change_id;
	// 订单分录Id
	private Long order_course_id;
	// 批改课次总数
	private Long course_times;
	// 退费前单价
	private Double pre_price;
	// 退费后单价
	private Double next_price;
	// 批改编号
	private String change_no;
	public Long getChange_id() {
		return change_id;
	}
	public void setChange_id(Long change_id) {
		this.change_id = change_id;
	}
	public Long getOrder_course_id() {
		return order_course_id;
	}
	public void setOrder_course_id(Long order_course_id) {
		this.order_course_id = order_course_id;
	}
	public Long getCourse_times() {
		return course_times;
	}
	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}
	public Double getPre_price() {
		return pre_price;
	}
	public void setPre_price(Double pre_price) {
		this.pre_price = pre_price;
	}
	public Double getNext_price() {
		return next_price;
	}
	public void setNext_price(Double next_price) {
		this.next_price = next_price;
	}
	public String getChange_no() {
		return change_no;
	}
	public void setChange_no(String change_no) {
		this.change_no = change_no;
	}
	
	
	
	
}