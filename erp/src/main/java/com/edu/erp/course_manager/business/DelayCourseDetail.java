package com.edu.erp.course_manager.business;

import com.edu.erp.model.TMoreTeacherCourse;
import org.apache.commons.lang.enums.EnumUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 延课详情 on 2017/12/9.
 */
public class DelayCourseDetail implements Serializable{

    private Long id;
    /**课程名称*/
    private Long courseId;
    /**课程名称*/
    private String courseName;
    /**课程编码*/
    private String courseNo;
    /**第几次课*/
    private Integer courseTime;

    /**延课前上课日期*/
    private String courseDateBeforeDelay;
    /**延课后上课日期*/
    private String courseDateAfterDelay;

    /**延课前开课时间*/
    private String startDateBeforeDelay;
    /**延课后开课时间*/
    private String startDateAfterDelay;

    /**延课前结课时间*/
    private String endDateBeforeDelay;
    /**延课后结课时间*/
    private String endDateAfterDelay;

    /**延课前上课时间*/
    private String startTimeBeforeDelay;
    /**延课后上课时间*/
    private String startTimeAfterDelay;

    /**延课前下课时间*/
    private String endTimeBeforeDelay;
    /**延课后下课时间*/
    private String endTimeAfterDelay;

    /**延课单ID*/
    private Long delayCourseRecordId;

    /**双师课程ID*/
    private Long mtCourseId;

    /**双师课程名称*/
    private String mtCourseName;
    //双师课程类型
    private Integer type;
    //双师课程名称
    private String typeName;

    /**
     * 延课异常信息
     */
    private String errorMessage;

    /**
     * 同步异常信息
     */
    private String synErrorMessage;

    /**延课课次变动列表*/
    List<DelayCourseTimeChangeInfo> changeInfoList ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDelayCourseRecordId() {
        return delayCourseRecordId;
    }

    public void setDelayCourseRecordId(Long delayCourseRecordId) {
        this.delayCourseRecordId = delayCourseRecordId;
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

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseDateBeforeDelay() {
        return courseDateBeforeDelay;
    }

    public void setCourseDateBeforeDelay(String courseDateBeforeDelay) {
        this.courseDateBeforeDelay = courseDateBeforeDelay;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCourseDateAfterDelay() {
        return courseDateAfterDelay;
    }

    public void setCourseDateAfterDelay(String courseDateAfterDelay) {
        this.courseDateAfterDelay = courseDateAfterDelay;
    }

    public String getStartDateBeforeDelay() {
        return startDateBeforeDelay;
    }

    public void setStartDateBeforeDelay(String startDateBeforeDelay) {
        this.startDateBeforeDelay = startDateBeforeDelay;
    }

    public String getStartDateAfterDelay() {
        return startDateAfterDelay;
    }

    public void setStartDateAfterDelay(String startDateAfterDelay) {
        this.startDateAfterDelay = startDateAfterDelay;
    }

    public String getEndDateBeforeDelay() {
        return endDateBeforeDelay;
    }

    public void setEndDateBeforeDelay(String endDateBeforeDelay) {
        this.endDateBeforeDelay = endDateBeforeDelay;
    }

    public String getEndDateAfterDelay() {
        return endDateAfterDelay;
    }

    public void setEndDateAfterDelay(String endDateAfterDelay) {
        this.endDateAfterDelay = endDateAfterDelay;
    }

    public String getStartTimeBeforeDelay() {
        return startTimeBeforeDelay;
    }

    public void setStartTimeBeforeDelay(String startTimeBeforeDelay) {
        this.startTimeBeforeDelay = startTimeBeforeDelay;
    }

    public String getStartTimeAfterDelay() {
        return startTimeAfterDelay;
    }

    public void setStartTimeAfterDelay(String startTimeAfterDelay) {
        this.startTimeAfterDelay = startTimeAfterDelay;
    }

    public String getEndTimeBeforeDelay() {
        return endTimeBeforeDelay;
    }

    public void setEndTimeBeforeDelay(String endTimeBeforeDelay) {
        this.endTimeBeforeDelay = endTimeBeforeDelay;
    }

    public String getEndTimeAfterDelay() {
        return endTimeAfterDelay;
    }

    public void setEndTimeAfterDelay(String endTimeAfterDelay) {
        this.endTimeAfterDelay = endTimeAfterDelay;
    }

    public List<DelayCourseTimeChangeInfo> getChangeInfoList() {
        return changeInfoList;
    }

    public void setChangeInfoList(List<DelayCourseTimeChangeInfo> changeInfoList) {
        this.changeInfoList = changeInfoList;
    }

    public Long getMtCourseId() {
        return mtCourseId;
    }

    public void setMtCourseId(Long mtCourseId) {
        this.mtCourseId = mtCourseId;
    }

    public String getSynErrorMessage() {
        return synErrorMessage;
    }

    public void setSynErrorMessage(String synErrorMessage) {
        this.synErrorMessage = synErrorMessage;
    }

    public String getMtCourseName() {
        return mtCourseName;
    }

    public void setMtCourseName(String mtCourseName) {
        this.mtCourseName = mtCourseName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        TMoreTeacherCourse.MoreTeacherCourseTypeEnum[] values = TMoreTeacherCourse.MoreTeacherCourseTypeEnum.values();
        for(TMoreTeacherCourse.MoreTeacherCourseTypeEnum e :values) {
            if(e.getCode().equals(type)) {
                this.typeName = e.getDesc();
                break;
            }
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
