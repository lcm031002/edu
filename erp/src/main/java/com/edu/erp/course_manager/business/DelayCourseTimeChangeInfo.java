package com.edu.erp.course_manager.business;

import com.edu.erp.model.TCourseRoomRel;

/**
 * Created by zenglw on 2017/12/7.
 * 延课课次变动信息
 */
public class DelayCourseTimeChangeInfo {

    private Long id;
    /**课程ID*/
    private Long courseId;
    /**课程课次ID*/
    private Long courseSchedulingId;
    /**课程名称*/
    private String courseName;
    /**课程编码*/
    private String courseNo;
    /**第几次课*/
    private Integer courseTime;

    /**是否是延课课次*/
    private boolean isDelayCourseTime;

    /**延课前上课日期*/
    private String courseDateBeforeDelay;
    /**延课后上课日期*/
    private String courseDateAfterDelay;

    /**延课前上课时间*/
    private String startTimeBeforeDelay;
    /**延课后上课时间*/
    private String startTimeAfterDelay;

    /**延课前下课时间*/
    private String endTimeBeforeDelay;
    /**延课后下课时间*/
    private String endTimeAfterDelay;

    /**延课详情id*/
    private Long delayCourseDetailId;

    /**双师课程id*/
    private Long mtCourseId;

    private TCourseRoomRel courseRoomRel;

    public Long getDelayCourseDetailId() {
        return delayCourseDetailId;
    }

    public void setDelayCourseDetailId(Long delayCourseDetailId) {
        this.delayCourseDetailId = delayCourseDetailId;
    }

    public String getEndTimeBeforeDelay() {
        return endTimeBeforeDelay;
    }

    public void setEndTimeBeforeDelay(String endTimeBeforeDelay) {
        this.endTimeBeforeDelay = endTimeBeforeDelay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public TCourseRoomRel getCourseRoomRel() {
        return courseRoomRel;
    }

    public void setCourseRoomRel(TCourseRoomRel courseRoomRel) {
        this.courseRoomRel = courseRoomRel;
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

    public boolean isDelayCourseTime() {
        return isDelayCourseTime;
    }

    public void setDelayCourseTime(boolean delayCourseTime) {
        isDelayCourseTime = delayCourseTime;
    }

    public String getCourseDateBeforeDelay() {
        return courseDateBeforeDelay;
    }

    public void setCourseDateBeforeDelay(String courseDateBeforeDelay) {
        this.courseDateBeforeDelay = courseDateBeforeDelay;
    }

    public String getCourseDateAfterDelay() {
        return courseDateAfterDelay;
    }

    public void setCourseDateAfterDelay(String courseDateAfterDelay) {
        this.courseDateAfterDelay = courseDateAfterDelay;
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

    public String getEndTimeAfterDelay() {
        return endTimeAfterDelay;
    }

    public void setEndTimeAfterDelay(String endTimeAfterDelay) {
        this.endTimeAfterDelay = endTimeAfterDelay;
    }

    public Long getMtCourseId() {
        return mtCourseId;
    }

    public void setMtCourseId(Long mtCourseId) {
        this.mtCourseId = mtCourseId;
    }

    public Long getCourseSchedulingId() {
        return courseSchedulingId;
    }

    public void setCourseSchedulingId(Long courseSchedulingId) {
        this.courseSchedulingId = courseSchedulingId;
    }
}
