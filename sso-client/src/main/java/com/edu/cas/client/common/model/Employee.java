package com.edu.cas.client.common.model;

import java.util.Date;

/**
 * @ClassName: Employee
 * @Description: 员工对象
 *
 */
public class Employee {
	private Long id;

	private String encoding;

	private String employeeName;

	private Long sex;

	private String identificationCard;

	private String email;

	private String phone;

	private String address;

	private String description;

	private Long status;

	private Long createUser;

	private Date createTime;

	private Long updateUser;
	
	private String orgName;

	private Date updateTime;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getEncoding() {
		return encoding;
	}

	public final void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public final String getEmployeeName() {
		return employeeName;
	}

	public final void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public final Long getSex() {
		return sex;
	}

	public final void setSex(Long sex) {
		this.sex = sex;
	}

	public final String getIdentificationCard() {
		return identificationCard;
	}

	public final void setIdentificationCard(String identificationCard) {
		this.identificationCard = identificationCard;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final String getPhone() {
		return phone;
	}

	public final void setPhone(String phone) {
		this.phone = phone;
	}

	public final String getAddress() {
		return address;
	}

	public final void setAddress(String address) {
		this.address = address;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
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

	public final Long getUpdateUser() {
		return updateUser;
	}

	public final void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public final Date getUpdateTime() {
		return updateTime;
	}

	public final String getOrgName() {
		return orgName;
	}

	public final void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public final void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((identificationCard == null) ? 0 : identificationCard
						.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result
				+ ((updateUser == null) ? 0 : updateUser.hashCode());
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
		Employee other = (Employee) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificationCard == null) {
			if (other.identificationCard != null)
				return false;
		} else if (!identificationCard.equals(other.identificationCard))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
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
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", encoding=" + encoding
				+ ", employeeName=" + employeeName + ", sex=" + sex
				+ ", identificationCard=" + identificationCard + ", email="
				+ email + ", phone=" + phone + ", address=" + address
				+ ", description=" + description + ", status=" + status
				+ ", createUser=" + createUser + ", createTime=" + createTime
				+ ", updateUser=" + updateUser + ", updateTime=" + updateTime
				+ "]";
	}

}
