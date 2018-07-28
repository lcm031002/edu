package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 排号信息表
 *
 */
public class SortNumInfo implements Serializable{

	private static final long serialVersionUID = -3593132206963628068L;
	// 学生ID
	private Long studentId;
	// 校区
	private Long branchId;
	//课程ID
	private Long courseId;
	// 课次
	private Long seq;
	// 排号
	private Long num;
	// 主键
	private Long id;
	// 状态
	private Integer status;
	// 创建用户
	private Long createUser;
	// 创建时间
	private Date createTime;
	// 更新用户
	private Long updateUser;
	// 更新时间
	private Date updateTime;
	
	private Long peopleCount;
	
	private String courseDate;
	
	private Long seasonId;
	
	private Long gradeId;
	
	private Long subjectId;
	
	private Long teacherId;
	
	private String studentName;
	
	private String gradeName;
	
	private String courseName;
	
	private String startTime;
	
	private String endTime;
	
	private String subjectName;
	
	private String teacherName;
	
	private String phone;
	
	private Long people_checkCount;
	
	private Long people_unCheckCount;
	
	public Long getPeople_checkCount() {
		return people_checkCount;
	}

	public void setPeople_checkCount(Long people_checkCount) {
		this.people_checkCount = people_checkCount;
	}

	public Long getPeople_unCheckCount() {
		return people_unCheckCount;
	}

	public void setPeople_unCheckCount(Long people_unCheckCount) {
		this.people_unCheckCount = people_unCheckCount;
	}

	public Long getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(Long peopleCount) {
		this.peopleCount = peopleCount;
	}


	public String getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
