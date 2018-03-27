package com.edu.erp.model;


/**
 * 排课课时拆分表
 * 
 * @author yecb
 *
 */
public class TScheduleSplitTime extends BaseObject{

	// 考勤ID
	private Long attendId;
	// 拆分课时记录
	private Long times;
	//订单课程ID
	private Long orderCourseId;

	private Double attendAmount;

	public Long getAttendId() {
		return attendId;
	}

	public void setAttendId(Long attendId) {
		this.attendId = attendId;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Long getOrderCourseId() {
		return orderCourseId;
	}

	public void setOrderCourseId(Long orderCourseId) {
		this.orderCourseId = orderCourseId;
	}

	public Double getAttendAmount() {
		return attendAmount;
	}

	public void setAttendAmount(Double attendAmount) {
		this.attendAmount = attendAmount;
	}
}
