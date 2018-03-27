package com.edu.erp.model;

import java.util.Date;

public class TStudentSchedulePlan extends BaseObject {
    private static final long serialVersionUID = 1L;
    private Long applyId;
    private Date courseDate;
    private String startTime;
    private String endTime;
    private String weekday;
    private Long teacherId;
    private String teacherName;
    private String teacherEncoding;
    private Long subjectId;
    private String subjectName;
    private Long courseArrangerId;
    private String courseArranger;
    private Date courseArrangeDate;
    private String remark;
    private String statusName;
    private String branchName;
    // 申请单据号
    private String applyNo;
    private String studentName;
    private String studentEncoding;

    private String teacherPhone;
    // 排课专员ID
    private Long courseSpId;

    private String teacherGender;

    private Long teachGroupId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
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

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getCourseArrangerId() {
        return courseArrangerId;
    }

    public void setCourseArrangerId(Long courseArrangerId) {
        this.courseArrangerId = courseArrangerId;
    }

    public Date getCourseArrangeDate() {
        return courseArrangeDate;
    }

    public void setCourseArrangeDate(Date courseArrangeDate) {
        this.courseArrangeDate = courseArrangeDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEncoding() {
        return studentEncoding;
    }

    public void setStudentEncoding(String studentEncoding) {
        this.studentEncoding = studentEncoding;
    }

    public String getTeacherEncoding() {
        return teacherEncoding;
    }

    public void setTeacherEncoding(String teacherEncoding) {
        this.teacherEncoding = teacherEncoding;
    }

    public String getCourseArranger() {
        return courseArranger;
    }

    public void setCourseArranger(String courseArranger) {
        this.courseArranger = courseArranger;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public Long getCourseSpId() {
        return courseSpId;
    }

    public void setCourseSpId(Long courseSpId) {
        this.courseSpId = courseSpId;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public Long getTeachGroupId() {
        return teachGroupId;
    }

    public void setTeachGroupId(Long teachGroupId) {
        this.teachGroupId = teachGroupId;
    }
}
