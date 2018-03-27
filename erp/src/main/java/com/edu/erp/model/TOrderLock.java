/**  
 * @Title: TOrderLock.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月22日 下午3:48:27
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.util.Date;

/**
 * @ClassName: TOrderLock
 * @Description: 订单冻结状态
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月22日 下午3:48:27
 *
 */
public class TOrderLock {
	
	private Long id;
	
	private Long orderId;
	
	private Long creater;
	
	private String createrName;
	
	private Date createTime;
	
	private Long updater;
	
	private String updaterName;
	
	private Date updateTime; 
	
	private Long status;
	
	private String statusName;
	
	private String remark;
	
	private long course_surplus_count;
	
	private Double surplus_cost;
	
	private Date unlockTime;	//解锁时间
	
	/****以下非DB字段*****/
	private String order_encoding;	//报班单号
	
	private String student_code;	//学生编码
	
	private String student_name;	//学生姓名
	
	private String branch_name;		//校区名称
	
	private Long oldstatus;			//旧的状态
	
	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getOrderId() {
		return orderId;
	}

	public final void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public final Long getCreater() {
		return creater;
	}

	public final void setCreater(Long creater) {
		this.creater = creater;
	}

	public final Date getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public final Long getUpdater() {
		return updater;
	}

	public final void setUpdater(Long updater) {
		this.updater = updater;
	}

	public final Date getUpdateTime() {
		return updateTime;
	}

	public final void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final String getCreaterName() {
		return createrName;
	}

	public final void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public final String getUpdaterName() {
		return updaterName;
	}

	public final void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public final String getStatusName() {
		return statusName;
	}

	public final void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public long getCourse_surplus_count() {
		return course_surplus_count;
	}

	public void setCourse_surplus_count(long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public Double getSurplus_cost() {
		return surplus_cost;
	}

	public void setSurplus_cost(Double surplus_cost) {
		this.surplus_cost = surplus_cost;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getOrder_encoding() {
		return order_encoding;
	}

	public void setOrder_encoding(String order_encoding) {
		this.order_encoding = order_encoding;
	}

	public String getStudent_code() {
		return student_code;
	}

	public void setStudent_code(String student_code) {
		this.student_code = student_code;
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

	public Long getOldstatus() {
		return oldstatus;
	}

	public void setOldstatus(Long oldstatus) {
		this.oldstatus = oldstatus;
	}

}
