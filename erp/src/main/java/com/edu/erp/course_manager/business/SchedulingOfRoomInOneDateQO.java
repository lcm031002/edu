package com.edu.erp.course_manager.business;

/**
 * Created by zenglw on 2017/12/20.
 */
public class SchedulingOfRoomInOneDateQO {
    private Integer courseDate;
    private String roomIds;

    public SchedulingOfRoomInOneDateQO(Integer courseDate, String roomIds) {
        this.courseDate = courseDate;
        this.roomIds = roomIds;
    }

    public Integer getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Integer courseDate) {
        this.courseDate = courseDate;
    }

    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }
}
