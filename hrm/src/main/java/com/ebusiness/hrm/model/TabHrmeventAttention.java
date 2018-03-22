package com.ebusiness.hrm.model;

import java.util.Date;

/**
 * 
 *@ClassName: TabHrmeventAttention 
 * @Description:TODO 人事异动关注表 model
 * @author:liyj
 * @time:2016年12月23日 下午4:05:53
 */
public class TabHrmeventAttention {

	private static final long serialVersionUID = 1L;
	//人事异动关注id
	private Long id;
	//关注员工id
	private Long employee_id;
	//异动id
	private Long event_id;
	//是否有效
	private Long is_effect;
	//关注时间
	private Date create_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	public Long getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}
	public Long getIs_effect() {
		return is_effect;
	}
	public void setIs_effect(Long is_effect) {
		this.is_effect = is_effect;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
