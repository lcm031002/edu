package com.edu.erp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单批改课程表
 *
 */
public class TCOrderCourse extends BaseObject {
	private static final long serialVersionUID = -7847011407416330390L;
	
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
	// 批改状态
	private Long change_status;
	private String branch_name;
	private String course_name;
	private String createUserName;
	private Long course_surplus_count;
	private long root_course_id;
	private List<TCCourseTimes> tcCourseTimes = new ArrayList<TCCourseTimes>();
	
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
	public final List<TCCourseTimes> getTcCourseTimes() {
		return tcCourseTimes;
	}
	public final void setTcCourseTimes(List<TCCourseTimes> tcCourseTimes) {
		this.tcCourseTimes = tcCourseTimes;
	}
	public final Long getChange_type() {
		return change_type;
	}
	public final void setChange_type(Long change_type) {
		this.change_type = change_type;
	}
	public final Long getChange_status() {
		return change_status;
	}
	public final void setChange_status(Long change_status) {
		this.change_status = change_status;
	}
	public final String getCreateUserName() {
		return createUserName;
	}
	public final void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Long getCourse_surplus_count() {
		return course_surplus_count;
	}
	public void setCourse_surplus_count(Long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}
	public long getRoot_course_id() {
		return root_course_id;
	}
	public void setRoot_course_id(long root_course_id) {
		this.root_course_id = root_course_id;
	}
	
}