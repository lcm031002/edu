package com.ebusiness.workflow.modules.index.model;

import java.io.Serializable;

/**
 * @ClassName: ExtBusinessrolMapping
 * @Description: 业务角色映射表
 *
 */
public class ExtBusinessRoleMapping implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 5686801269899544747L;

	private String businessRole;

	private String businessRoleId;

	private long id;

	private Integer isdefault;

	private ExtProcessRoleDef processRoleDef;

	private long processRoleDefId;

	public String getBusinessRole() {
		return businessRole;
	}

	public String getBusinessRoleId() {
		return businessRoleId;
	}

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public ExtProcessRoleDef getProcessRoleDef() {
		return processRoleDef;
	}


	public final long getProcessRoleDefId() {
		return processRoleDefId;
	}

	public final void setProcessRoleDefId(long processRoleDefId) {
		this.processRoleDefId = processRoleDefId;
	}

	public void setBusinessRole(String businessRole) {
		this.businessRole = businessRole;
	}

	public void setBusinessRoleId(String businessRoleId) {
		this.businessRoleId = businessRoleId;
	}


	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public void setProcessRoleDef(ExtProcessRoleDef processRoleDef) {
		this.processRoleDef = processRoleDef;
	}


	

	

}
