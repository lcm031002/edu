package com.edu.erp.model;

import java.util.Date;

/**
 * 补课信息
 * 
 * @author zengliwen
 * 
 */
public class Makeup extends BaseObject {
	private static final long serialVersionUID = 1L;
	private String act_code;// 预约码
	private String vs_date;// 开始时间
	private String ve_date;// 结束时间
	private String active_time;// 激活时间
	private String status_name;// 状态
	private Long course_times;// 课次
	private String course_name;// 课程
	private String attended;// 考勤状态
	public String getAct_code() {
		return act_code;
	}
	public void setAct_code(String act_code) {
		this.act_code = act_code;
	}
	public String getVs_date() {
		return vs_date;
	}
	public void setVs_date(String vs_date) {
		this.vs_date = vs_date;
	}
	public String getVe_date() {
		return ve_date;
	}
	public void setVe_date(String ve_date) {
		this.ve_date = ve_date;
	}
	public String getActive_time() {
		return active_time;
	}
	public void setActive_time(String active_time) {
		this.active_time = active_time;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public Long getCourse_times() {
		return course_times;
	}
	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getAttended() {
		return attended;
	}
	public void setAttended(String attended) {
		this.attended = attended;
	}
	@Override
	public String toString() {
		return "Makeup [act_code=" + act_code + ", vs_date=" + vs_date
				+ ", ve_date=" + ve_date + ", active_time=" + active_time
				+ ", status_name=" + status_name + ", course_times="
				+ course_times + ", course_name=" + course_name + ", attended="
				+ attended + "]";
	}


}
