package com.edu.erp.model;
public class AttendTeacherGroupRef extends BaseObject {
	private static final long serialVersionUID = 7537528415455107366L;
	
	private Long group_id;
	private Long teacher_id;
	
	private AttendTeacherGroup attendTeacherGroup;
	private Teacher teacher;
	
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	public AttendTeacherGroup getAttendTeacherGroup() {
		return attendTeacherGroup;
	}
	public void setAttendTeacherGroup(AttendTeacherGroup attendTeacherGroup) {
		this.attendTeacherGroup = attendTeacherGroup;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}