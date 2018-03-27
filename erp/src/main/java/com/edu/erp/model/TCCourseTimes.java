/**  
 * @Title: TCCourseTimes.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午10:06:01
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.io.Serializable;

/**
 * @ClassName: TCCourseTimes
 * @Description: 课次批改信息
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月19日 下午10:06:01
 * 
 */
public class TCCourseTimes implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8466505392870558151L;
	private Long id;
	private Long changeId;
	private Long changeCourseId;
	private Long orderCourseId;
	private Long courseTimes;
	private Long orderId;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getChangeId() {
		return changeId;
	}

	public final void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	public final Long getChangeCourseId() {
		return changeCourseId;
	}

	public final void setChangeCourseId(Long changeCourseId) {
		this.changeCourseId = changeCourseId;
	}

	public final Long getCourseTimes() {
		return courseTimes;
	}

	public final void setCourseTimes(Long courseTimes) {
		this.courseTimes = courseTimes;
	}

	public final Long getOrderId() {
		return orderId;
	}

	public final void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderCourseId() {
		return orderCourseId;
	}

	public void setOrderCourseId(Long orderCourseId) {
		this.orderCourseId = orderCourseId;
	}

}
