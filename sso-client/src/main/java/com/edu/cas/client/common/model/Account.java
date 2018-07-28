package com.edu.cas.client.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Account
 * @Description: 用户账户对象
 *
 */
public class Account implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 5848365043062591794L;

	private Long id;

	private String userName;

	private transient String password;

	private Long employeeId;
	
	private String employeeName;

	private Long status;

	private Date createTime;

	private Date updateTime;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getUserName() {
		return userName;
	}

	public final void setUserName(String userName) {
		this.userName = userName;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final Long getEmployeeId() {
		return employeeId;
	}

	public final void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
	}

	public final Date getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public final String getEmployeeName() {
		return employeeName;
	}

	public final void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public final Date getUpdateTime() {
		return updateTime;
	}

	public final void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", userName=" + userName + ", employeeId="
				+ employeeId + ", employeeName=" + employeeName + ", status="
				+ status + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}

	
}
