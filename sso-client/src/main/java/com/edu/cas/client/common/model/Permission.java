/**  
 * @Title: RolePermission.java
 * @Package com.edu.cas.client.common.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月14日 下午7:34:49
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.model;

import java.io.Serializable;

/**
 * @ClassName: RolePermission
 * @Description: 权限许可
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月14日 下午7:34:49
 *
 */
public class Permission  implements Serializable{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 8268041655530543447L;
	private String permissionId;
	private Long userId;
	
	public final Long getUserId() {
		return userId;
	}
	public final void setUserId(Long userId) {
		this.userId = userId;
	}
	public final String getPermissionId() {
		return permissionId;
	}
	public final void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((permissionId == null) ? 0 : permissionId.hashCode());
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
		Permission other = (Permission) obj;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
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
		return "Permission [permissionId=" + permissionId + ", userId="
				+ userId + "]";
	}
	

}
