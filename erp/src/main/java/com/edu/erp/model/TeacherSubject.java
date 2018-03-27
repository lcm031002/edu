package com.edu.erp.model;


/**
 * 教师科目
 * @author wsf
 * @date 2014-09-11
 */
public class TeacherSubject extends BaseObject{
	
	private static final long serialVersionUID = -4753586364238991941L;
	
	private Long teacher_id;
	private Long subject_id;
	private Long bu_id;
	private Long grade_id;
	
	private Teacher teacher;
	private TPSubject subject;
	private OrganizationInfo bu;
	private Grade grade;
	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public TPSubject getSubject() {
		return subject;
	}
	public void setSubject(TPSubject subject) {
		this.subject = subject;
	}
	public OrganizationInfo getBu() {
		return bu;
	}
	public void setBu(OrganizationInfo bu) {
		this.bu = bu;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public Long getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	public Long getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
 	

}
