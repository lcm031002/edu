package com.edu.erp.model;

import java.util.List;

public class TeacherGroup extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String teach_group_name;

	private String old_id;

	private Long bu_id;

	private String status_name;

	private String bu_name;

	private String city_name;

	private List<TeacherLeader> leaderList;

	private List<TeacherGroupRef> teacherList;

	public String getTeach_group_name() {
		return teach_group_name;
	}

	public void setTeach_group_name(String teach_group_name) {
		this.teach_group_name = teach_group_name;
	}

	public String getOld_id() {
		return old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public List<TeacherLeader> getLeaderList() {
		return leaderList;
	}

	public void setLeaderList(List<TeacherLeader> leaderList) {
		this.leaderList = leaderList;
	}

	public List<TeacherGroupRef> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<TeacherGroupRef> teacherList) {
		this.teacherList = teacherList;
	}

}
