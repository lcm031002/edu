package com.edu.workflow.modules.index.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ExtBusinessEntrust
 * @Description: 委托任务查询表
 *
 */
public class ExtBusinessEntrust implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -9148719998058333535L;

	private Long id;

	private String consignorRole;

	private String consigneeRole;
	
	private String consigneeName;

	private String consigneeTaskId;
	
	private String taskName;

	private Integer status;

	private Date beginTime;

	private Date endTime;
	
	private Date createTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public String getConsigneeRole() {
		return consigneeRole;
	}

	public String getConsigneeTaskId() {
		return consigneeTaskId;
	}

	public String getConsignorRole() {
		return consignorRole;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Long getId() {
		return id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public void setConsigneeRole(String consigneeRole) {
		this.consigneeRole = consigneeRole;
	}

	public void setConsigneeTaskId(String consigneeTaskId) {
		this.consigneeTaskId = consigneeTaskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setConsignorRole(String consignorRole) {
		this.consignorRole = consignorRole;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

}
