package com.edu.erp.model;


/**
 * 课程校区权限关系
 *
 */
public class TCourseSchool extends BaseObject{
	private static final long serialVersionUID = -6030691132666278290L;
	
	private TCourse course;
	private OrganizationInfo organizationInfo;
	
	public OrganizationInfo getOrganizationInfo() {
		return organizationInfo;
	}
	public void setOrganizationInfo(OrganizationInfo organizationInfo) {
		this.organizationInfo = organizationInfo;
	}
	public TCourse getCourse() {
		return course;
	}
	public void setCourse(TCourse course) {
		this.course = course;
	}
}
