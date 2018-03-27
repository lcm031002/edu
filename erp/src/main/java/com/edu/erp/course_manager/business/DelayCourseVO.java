package com.edu.erp.course_manager.business;

/**
 * Created by zenglw on 2017/12/8.
 * 延课界面，延课课程列表
 */
public class DelayCourseVO {

    private Long courseId;
    private String courseName;
    private String courseNo;
    private Integer courseTime;
    private String branchName;
    private String courseSeasonName;
    private String gradeName;
    private String subjectName;
    private String teacherName;
    private String startDate;
    private String endDate;
    private Long mtCourseId;
    private String mtCourseName;

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCourseSeasonName() {
        return courseSeasonName;
    }

    public void setCourseSeasonName(String courseSeasonName) {
        this.courseSeasonName = courseSeasonName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getMtCourseId() {
        return mtCourseId;
    }

    public void setMtCourseId(Long mtCourseId) {
        this.mtCourseId = mtCourseId;
    }

    public String getMtCourseName() {
        return mtCourseName;
    }

    public void setMtCourseName(String mtCourseName) {
        this.mtCourseName = mtCourseName;
    }
}
