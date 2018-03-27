package com.edu.erp.model;

import java.util.List;

/**
 * 考勤教师分组
 * 
 * @author wCong
 *
 */
public class AttendTeacherGroup extends BaseObject {
	private static final long serialVersionUID = -2568954075161330978L;
	
	// 教师组id
	private Long id;
	//关联的教师ids
	private String teacher_ids;
	// 组名称
	private String name;
	// 团队
	private Long bu_id;
	// 校区
	private Long branch_id;
	
	private String bu_name;
	
	private String branch_name;
	
	private List<AttendTeacherGroupRef> groupTeacherList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeacher_ids() {
		return teacher_ids;
	}
	public void setTeacher_ids(String teacher_ids) {
		this.teacher_ids = teacher_ids;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public String getBu_name() {
		return bu_name;
	}
	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public List<AttendTeacherGroupRef> getGroupTeacherList() {
		return groupTeacherList;
	}
	public void setGroupTeacherList(List<AttendTeacherGroupRef> groupTeacherList) {
		this.groupTeacherList = groupTeacherList;
	}
	@Override
	public String toString() {
		return "AttendTeacherGroup [name=" + name + ", bu_id=" + bu_id
				+ ", branch_id=" + branch_id + ", bu_name=" + bu_name
				+ ", branch_name=" + branch_name + ", groupTeacherList="
				+ groupTeacherList + "]";
	}
	
}