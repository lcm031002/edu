package com.ebusiness.hrm.model;

import java.util.Date;

/**
 * 
 * @ClassName: TabHrmChangeeventHt
 * @Description:TODO 人事活动的处理历史
 * @author:liyj
 * @time:2016年12月23日 下午4:11:59
 */
public class TabHrmChangeEventHt {

	private static final long serialVersionUID = 1L;

	// 人事活动id
	private Long id;
	// 员工id
	private Long employee_id;
	// 员工name
	private String employee_name;
	// 发起人id
	private Long organiser;
	// 发起人name
	private String organiser_name;
	// 申请时间
	private Date application_time;
	//最后更新时间
	private Date last_update_time;
	// 申请类型
	private Long application_type;
	// 申请类型name
	private String application_name;
	// 处理人id
	private String handler_id;
	// 处理人name
	private String handler_name;
	// 处理状态
	private Long approval_status;
	// 处理意见
	private String handler_opinion;
	// 是否生效
	private Long is_effect;
	// 任务ID
	private String task_id;
	// 附件
	private String enclosure;
	// 团队id
	private Long bu_id;
	// 团队name
	private String bu_name;
	// 校区id
	private Long branch_id;
	// 校区name;
	private String branch_name;
	// 处理步骤
	private Long step;
	//异动id
	private Long event_id;
	
	public Long getStep() {
		return step;
	}

	public void setStep(Long step) {
		this.step = step;
	}

	public Long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getOrganiser_name() {
		return organiser_name;
	}

	public void setOrganiser_name(String organiser_name) {
		this.organiser_name = organiser_name;
	}

	public String getApplication_name() {
		return application_name;
	}

	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}

	public String getHandler_name() {
		return handler_name;
	}

	public void setHandler_name(String handler_name) {
		this.handler_name = handler_name;
	}

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
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

	public Long getOrganiser() {
		return organiser;
	}

	public void setOrganiser(Long organiser) {
		this.organiser = organiser;
	}

	public Date getApplication_time() {
		return application_time;
	}

	public void setApplication_time(Date application_time) {
		this.application_time = application_time;
	}

	public Long getApplication_type() {
		return application_type;
	}

	public void setApplication_type(Long application_type) {
		this.application_type = application_type;
	}

	public String getHandler_id() {
		return handler_id;
	}

	public void setHandler_id(String handler_id) {
		this.handler_id = handler_id;
	}

	public Long getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(Long approval_status) {
		this.approval_status = approval_status;
	}

	public String getHandler_opinion() {
		return handler_opinion;
	}

	public void setHandler_opinion(String handler_opinion) {
		this.handler_opinion = handler_opinion;
	}

	public Long getIs_effect() {
		return is_effect;
	}

	public void setIs_effect(Long is_effect) {
		this.is_effect = is_effect;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

}
