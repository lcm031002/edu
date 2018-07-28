package com.edu.erp.model;


/**
 * 优惠规则校区权限
 *
 */
public class PrivilegeSchoolRef extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	private OrganizationInfo organizationInfo;
	private PrivilegeRule privilegeRule;
	
	public OrganizationInfo getOrganizationInfo() {
		return organizationInfo;
	}
	public void setOrganizationInfo(OrganizationInfo organizationInfo) {
		this.organizationInfo = organizationInfo;
	}
	public PrivilegeRule getPrivilegeRule() {
		return privilegeRule;
	}
	public void setPrivilegeRule(PrivilegeRule privilegeRule) {
		this.privilegeRule = privilegeRule;
	}
	
}
