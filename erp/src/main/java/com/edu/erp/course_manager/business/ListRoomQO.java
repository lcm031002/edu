package com.edu.erp.course_manager.business;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * Created by zenglw on 2017/11/13.
 */
public class ListRoomQO {

    private String roomId;
    /**
     * 上课日期开始期间
     */
    private String startDate;
    /**
     * 上课日期i5截止时间
     */
    private String endDate;
    /**
     * 上课日期开始期间 int类型转化
     */
    private int startDateInNum;
    /**
     * 上课日期截止期间 int类型转化
     */
    private int endDateInNum;
    private String startTime;
    private String endTime;
    private Long branchId;
    private String roomName;
    private String teacherName;
    private String courseName;
    private Long buId;
    /**
     * 查询教室类型
     */
    private String condition;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getStartDateInNum() {
        return Integer.valueOf(startDate.replace("-",""));
    }

    public void setStartDateInNum(int startDateInNum) {
        this.startDateInNum = startDateInNum;
    }

    public int getEndDateInNum() {
        return Integer.valueOf(endDate.replace("-",""));
    }

    public void setEndDateInNum(int endDateInNum) {
        this.endDateInNum = endDateInNum;
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

    public String getStartTime() {
        return StringUtils.isEmpty(startTime)?"00:00":startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime(){
        endTime = StringUtils.isEmpty(endTime)?"23:59":endTime;
        String[] format = {"HH:mm"};
        Date start = null;
        Date end = null;
        try{
            start = DateUtils.parseDate(getStartTime(),format);
            end = DateUtils.parseDate(endTime,format);

        } catch (Exception e) {
            throw  new RuntimeException("上课时间或下课时间解析异常");
        }
        if(!start.before(end)) throw new RuntimeException("时间区间异常，截止时间不应大于等于起始时间");
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getBuId() {
        return buId;
    }

    public void setBuId(Long buId) {
        this.buId = buId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
