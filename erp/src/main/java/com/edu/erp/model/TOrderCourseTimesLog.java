/**  
 * @Title: TOrderCourseTimesLog.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月22日 下午7:02:33
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.util.Date;

/**
 * @ClassName: TOrderCourseTimesLog
 * @Description: 课程课次信息
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月22日 下午7:02:33
 *
 */
public class TOrderCourseTimesLog {
	private Long id;
	
	private Long changeId;
	
	private Long ocid;
	
	private Long times;
	
	private Long isValid;
	
	private Long createUser;
	
	private Date createTime;
	
	private Long finConfirmUser;
	
	private Date finConfirmTime;
	
	private Long teacherId;
	
	private Date courseDate;
	
	private String teacherName;
	
	private String createrName;
	
	private String confirmerName;
	
	private Long changeType;
	
	private Long changeStatus;
	
	private Long transferFlag;

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

	public final Long getOcid() {
		return ocid;
	}

	public final void setOcid(Long ocid) {
		this.ocid = ocid;
	}

	public final Long getTimes() {
		return times;
	}

	public final void setTimes(Long times) {
		this.times = times;
	}

	public final Long getIsValid() {
		return isValid;
	}

	public final void setIsValid(Long isValid) {
		this.isValid = isValid;
	}

	public final Long getCreateUser() {
		return createUser;
	}

	public final void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public final Date getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public final Long getFinConfirmUser() {
		return finConfirmUser;
	}

	public final void setFinConfirmUser(Long finConfirmUser) {
		this.finConfirmUser = finConfirmUser;
	}

	public final Date getFinConfirmTime() {
		return finConfirmTime;
	}

	public final void setFinConfirmTime(Date finConfirmTime) {
		this.finConfirmTime = finConfirmTime;
	}

	public final Long getTeacherId() {
		return teacherId;
	}

	public final void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public final Date getCourseDate() {
		return courseDate;
	}

	public final void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public final String getTeacherName() {
		return teacherName;
	}

	public final void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public final String getCreaterName() {
		return createrName;
	}

	public final void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public final String getConfirmerName() {
		return confirmerName;
	}

	public final void setConfirmerName(String confirmerName) {
		this.confirmerName = confirmerName;
	}

	public final Long getChangeType() {
		return changeType;
	}

	public final void setChangeType(Long changeType) {
		this.changeType = changeType;
	}

	public final Long getChangeStatus() {
		return changeStatus;
	}

	public final void setChangeStatus(Long changeStatus) {
		this.changeStatus = changeStatus;
	}

	public Long getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(Long transferFlag) {
		this.transferFlag = transferFlag;
	}
	
}
