package com.edu.erp.model;

/**
 * @ClassName: TLock
 * @Description: 锁对象
 *
 */
public class TLock {
	private Long id;
	private Long type;
	private Long resourceId;
	private Long busiType;
	private Long busiId;
	
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Long getType() {
		return type;
	}
	public final void setType(Long type) {
		this.type = type;
	}
	public final Long getResourceId() {
		return resourceId;
	}
	public final void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public final Long getBusiType() {
		return busiType;
	}
	public final void setBusiType(Long busiType) {
		this.busiType = busiType;
	}
	public final Long getBusiId() {
		return busiId;
	}
	public final void setBusiId(Long busiId) {
		this.busiId = busiId;
	}
	@Override
	public String toString() {
		return "TLock [id=" + id + ", type=" + type + ", resourceId="
				+ resourceId + ", busiType=" + busiType + ", busiId=" + busiId
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busiId == null) ? 0 : busiId.hashCode());
		result = prime * result
				+ ((busiType == null) ? 0 : busiType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TLock other = (TLock) obj;
		if (busiId == null) {
			if (other.busiId != null)
				return false;
		} else if (!busiId.equals(other.busiId))
			return false;
		if (busiType == null) {
			if (other.busiType != null)
				return false;
		} else if (!busiType.equals(other.busiType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
