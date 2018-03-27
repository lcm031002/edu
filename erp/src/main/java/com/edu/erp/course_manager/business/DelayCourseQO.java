package com.edu.erp.course_manager.business;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by zenglw on 2017/12/5.
 * 延课课程查询对象
 */
public class DelayCourseQO {

    private Long branchId;
    private String branchsInBu;
    private Long seasonId;
    private Long gradeId;
    private Long subjectId;
    private String teacherSearchInfo;
    private String courseSearchInfo;
    private String delayCourseDateString;
    private Integer delayCourseDateInteger;
    //双师课程类型
    private Long type;
    //双师课程类型
    private Long operationType;

    public String getBranchsInBu() {
        return branchsInBu;
    }

    public void setBranchsInBu(String branchsInBu) {
        this.branchsInBu = branchsInBu;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
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

    public String getTeacherSearchInfo() {
        return teacherSearchInfo;
    }

    public void setTeacherSearchInfo(String teacherSearchInfo) {
        this.teacherSearchInfo = teacherSearchInfo;
    }

    public String getCourseSearchInfo() {
        return courseSearchInfo;
    }

    public void setCourseSearchInfo(String courseSearchInfo) {
        this.courseSearchInfo = courseSearchInfo;
    }

    public String getDelayCourseDateString() {
        return delayCourseDateString;
    }

    public void setDelayCourseDateString(String delayCourseDateString) {
        this.delayCourseDateString = delayCourseDateString;
    }

    public Integer getDelayCourseDateInteger() {
        return StringUtils.isEmpty(delayCourseDateString)?null:Integer.valueOf(delayCourseDateString.replace("-",""));
    }

    public void setDelayCourseDateInteger(Integer delayCourseDateInteger) {
        this.delayCourseDateInteger = delayCourseDateInteger;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getOperationType() {
        return operationType;
    }

    public void setOperationType(Long operationType) {
        this.operationType = operationType;
    }
}
