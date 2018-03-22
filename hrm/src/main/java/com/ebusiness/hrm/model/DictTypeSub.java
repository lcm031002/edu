package com.ebusiness.hrm.model;

import java.io.Serializable;

public class DictTypeSub  implements Serializable{
	
	private static final long serialVersionUID = -8315093905378717026L;
	
	private Integer id;
	
	private Integer dictTypeId;
	
	private String name;
	
	private Integer code;
	
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(Integer dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dictTypeId == null) ? 0 : dictTypeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		DictTypeSub other = (DictTypeSub) obj;
		if (dictTypeId == null) {
			if (other.dictTypeId != null)
				return false;
		} else if (!dictTypeId.equals(other.dictTypeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DictTypeSub [id=" + id + ", dictTypeId=" + dictTypeId + ", name=" + name + ", remark=" + remark + "]";
	}
	
	
	

}
