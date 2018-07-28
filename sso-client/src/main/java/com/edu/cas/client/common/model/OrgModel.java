package com.edu.cas.client.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: OrgModel
 * @Description: 组织结构模型
 *
 */
public class OrgModel  implements Serializable{
	
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -505793537539310124L;
	private Long id;
	private String text;
	private Long parentId;
	private Long userId;
	private String type;
	private Long buId;
	private Long cityId;
	private Long productLine;
	private Integer orgKind;
	private String cityName;
	
	private Map<String,String> state = new HashMap<String,String>();
	
	private List<OrgModel> children = new ArrayList<OrgModel>();

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final Map<String, String> getState() {
		return state;
	}

	public final void setState(Map<String, String> state) {
		this.state = state;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public final List<OrgModel> getChildren() {
		return children;
	}

	public final void setChildren(List<OrgModel> children) {
		this.children = children;
	}

	public final Long getParentId() {
		return parentId;
	}

	public final void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		OrgModel other = (OrgModel) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public Long getBuId() {
		return buId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getProductLine() {
		return productLine;
	}

	public void setProductLine(Long productLine) {
		this.productLine = productLine;
	}

	public Integer getOrgKind() {
        return orgKind;
    }

    public void setOrgKind(Integer orgKind) {
        this.orgKind = orgKind;
    }

    @Override
	public String toString() {
		return "OrgModel [id=" + id + ", text=" + text + ", parentId="
				+ parentId + ", userId=" + userId + ", buId=" + buId+ ", type=" + type+ ", cityId=" +cityId 
				+ ", state=" + state + ", children=" + children + "]";
	}

	
}
