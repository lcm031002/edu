package com.edu.erp.model;


/**
 * 员工部门关系
 * 
 *
 */
public class EmployeeDepartmentRef extends BaseObject {
	private static final long serialVersionUID = -4151412676526488859L;
	
	// 员工
	private EmployeeInfo employeeInfo;
	// 部门
	private OrganizationInfo organizationInfo;
	
	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}
	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}
	public OrganizationInfo getOrganizationInfo() {
		return organizationInfo;
	}
	public void setOrganizationInfo(OrganizationInfo organizationInfo) {
		this.organizationInfo = organizationInfo;
	}
}
