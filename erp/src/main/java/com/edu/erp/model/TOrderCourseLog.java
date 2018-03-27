package com.edu.erp.model;

import java.util.Date;

/**
 * 订单课程备份表
 * 
 * @author chenyl
 * 
 */
public class TOrderCourseLog extends BaseObject {

	private static final long serialVersionUID = -6898485812902633801L;

	private Long change_id;  //批改ID
	
	private Long old_new; //新旧标识:1修改前数据,2修改后数据
	
	private Long id;
    // 订单主键
    private Long order_id;
    // 课程ID
    private Long course_id;
    // 课程所属校区
    private Long branch_id;
    // 原单价
    private Double former_unit_price;
    // 原总价
    private Double former_sum_price;
    // 折后单价
    private Double discount_unit_price;
    // 折后总价
    private Double discount_sum_price;
    // 折扣
    private Double discount_rate;
    // 折扣金额
    private Double discount_amount;
    // 预结转
    private Double manage_fee;
    // 课程（课时）总次数
    private Long course_total_count;
    // 剩余课程（课时）总次数
    private Long course_surplus_count;
	// 剩余可排课时
	private Long course_schedule_count;
    // 剩余费用
    private Double surplus_cost;
    // 退费标识
    private Long quit_flag;
    // 订单类型
    private Long order_type;

    private String old_id;

    private Long root_course_id;
    
    private Long is_sended;
    
    private String syn_exception;
    
    private Date log_create_time;
    
    private Date log_update_time;
    
    private Double cur_former_unit_price;
    
    private Double cur_discount_unit_price;
    
    private Long charge_back_num;
    
    private Long cur_course_surplus_count;

	private Long cur_course_schedule_count;

	public Long getChange_id() {
		return change_id;
	}

	public void setChange_id(Long change_id) {
		this.change_id = change_id;
	}

	public Long getOld_new() {
		return old_new;
	}

	public void setOld_new(Long old_new) {
		this.old_new = old_new;
	}

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

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Double getFormer_unit_price() {
		return former_unit_price;
	}

	public void setFormer_unit_price(Double former_unit_price) {
		this.former_unit_price = former_unit_price;
	}

	public Double getFormer_sum_price() {
		return former_sum_price;
	}

	public void setFormer_sum_price(Double former_sum_price) {
		this.former_sum_price = former_sum_price;
	}

	public Double getDiscount_unit_price() {
		return discount_unit_price;
	}

	public void setDiscount_unit_price(Double discount_unit_price) {
		this.discount_unit_price = discount_unit_price;
	}

	public Double getDiscount_sum_price() {
		return discount_sum_price;
	}

	public void setDiscount_sum_price(Double discount_sum_price) {
		this.discount_sum_price = discount_sum_price;
	}

	public Double getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(Double discount_rate) {
		this.discount_rate = discount_rate;
	}

	public Double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Double getManage_fee() {
		return manage_fee;
	}

	public void setManage_fee(Double manage_fee) {
		this.manage_fee = manage_fee;
	}

	public Long getCourse_total_count() {
		return course_total_count;
	}

	public void setCourse_total_count(Long course_total_count) {
		this.course_total_count = course_total_count;
	}

	public Long getCourse_surplus_count() {
		return course_surplus_count;
	}

	public void setCourse_surplus_count(Long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public Double getSurplus_cost() {
		return surplus_cost;
	}

	public void setSurplus_cost(Double surplus_cost) {
		this.surplus_cost = surplus_cost;
	}

	public Long getQuit_flag() {
		return quit_flag;
	}

	public void setQuit_flag(Long quit_flag) {
		this.quit_flag = quit_flag;
	}

	public Long getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Long order_type) {
		this.order_type = order_type;
	}

	public String getOld_id() {
		return old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public Long getRoot_course_id() {
		return root_course_id;
	}

	public void setRoot_course_id(Long root_course_id) {
		this.root_course_id = root_course_id;
	}

	public Long getIs_sended() {
		return is_sended;
	}

	public void setIs_sended(Long is_sended) {
		this.is_sended = is_sended;
	}

	public String getSyn_exception() {
		return syn_exception;
	}

	public void setSyn_exception(String syn_exception) {
		this.syn_exception = syn_exception;
	}

	public Date getLog_create_time() {
		return log_create_time;
	}

	public void setLog_create_time(Date log_create_time) {
		this.log_create_time = log_create_time;
	}

	public Date getLog_update_time() {
		return log_update_time;
	}

	public void setLog_update_time(Date log_update_time) {
		this.log_update_time = log_update_time;
	}

	public Double getCur_former_unit_price() {
		return cur_former_unit_price;
	}

	public void setCur_former_unit_price(Double cur_former_unit_price) {
		this.cur_former_unit_price = cur_former_unit_price;
	}

	public Double getCur_discount_unit_price() {
		return cur_discount_unit_price;
	}

	public void setCur_discount_unit_price(Double cur_discount_unit_price) {
		this.cur_discount_unit_price = cur_discount_unit_price;
	}

	public Long getCharge_back_num() {
		return charge_back_num;
	}

	public void setCharge_back_num(Long charge_back_num) {
		this.charge_back_num = charge_back_num;
	}

	public Long getCur_course_surplus_count() {
		return cur_course_surplus_count;
	}

	public void setCur_course_surplus_count(Long cur_course_surplus_count) {
		this.cur_course_surplus_count = cur_course_surplus_count;
	}

	public Long getCourse_schedule_count() {
		return course_schedule_count;
	}

	public void setCourse_schedule_count(Long course_schedule_count) {
		this.course_schedule_count = course_schedule_count;
	}

	public Long getCur_course_schedule_count() {
		return cur_course_schedule_count;
	}

	public void setCur_course_schedule_count(Long cur_course_schedule_count) {
		this.cur_course_schedule_count = cur_course_schedule_count;
	}
}
