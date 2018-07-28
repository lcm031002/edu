package com.edu.cas.client.common.model;

import java.io.Serializable;

/**
 * @ClassName: UserPersonalSettings
 * @Description: 用户自定义参数对象
 *
 */
public class UserPersonalSettings implements Serializable {
	private static final long serialVersionUID = 8167598453735911604L;
	private Long id;
	private Long userId;
	private String paramName;
	private String paramVal;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public final String getParamName() {
		return paramName;
	}

	public final void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public final String getParamVal() {
		return paramVal;
	}

	public final void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((paramName == null) ? 0 : paramName.hashCode());
		result = prime * result
				+ ((paramVal == null) ? 0 : paramVal.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserPersonalSettings other = (UserPersonalSettings) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paramName == null) {
			if (other.paramName != null)
				return false;
		} else if (!paramName.equals(other.paramName))
			return false;
		if (paramVal == null) {
			if (other.paramVal != null)
				return false;
		} else if (!paramVal.equals(other.paramVal))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserPersonalSettings [id=" + id + ", userId=" + userId
				+ ", paramName=" + paramName + ", paramVal=" + paramVal + "]";
	}

}
