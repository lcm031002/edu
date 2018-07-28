package com.edu.erp.model;

/**
 * 课程教室绑定与解绑类
 *
 */
public class RoomClass {

    private Long courseId;
    private String weekday;
    private Long roomId;
    private String startTime;
    private String endTime;
    private String courseDate;
    private int weekNumber;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
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

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {return false;}
        if(this == obj){ return true;}
        if(obj instanceof RoomClass){
            RoomClass roomClass =(RoomClass)obj;
            // 比较3个字段之后再去比较 hashCode
            if(roomClass.weekday.equals(this.weekday)&& roomClass.startTime.equals(this.startTime)&&roomClass.endTime.equals(this.endTime)) {return true;}
        }
        return false;
    }

    @Override
    public int hashCode() {
        return weekday.hashCode() * startTime.hashCode() * endTime.hashCode();
    }
}
