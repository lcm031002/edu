package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeExperience implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long employee_id;
	private Date begin_time;
	private Date end_time;
	private String company_name;
	//公司性质
	private String company_type;
	//职位名称
	private String job_title;
	//工作职责
	private String responsibility;
	//下属人数
	private Integer subordinates;
	//离职原因
	private String reasons;
	//是否有证明
	private Integer has_prove;
	//证明信息
	private String reterence;
	//附件
	private String enclosure;
	
	
	public String getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
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
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getCompany_type() {
		return company_type;
	}
	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public Integer getSubordinates() {
		return subordinates;
	}
	public void setSubordinates(Integer subordinates) {
		this.subordinates = subordinates;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public Integer getHas_prove() {
		return has_prove;
	}
	public void setHas_prove(Integer has_prove) {
		this.has_prove = has_prove;
	}
	public String getReterence() {
		return reterence;
	}
	public void setReterence(String reterence) {
		this.reterence = reterence;
	}
	
	
	
}
