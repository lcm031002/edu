package com.edu.erp.course_manager.business;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zenglw on 2017/12/7.
 * 课次上课时间，包括上课日期，上课时间，下课时间，第几周
 */
public class CourseTimeDate {

    private Date courseTimeDate;
    private String courseTimeDateString;
    private String startTime;
    private String endTime;
    private int weekNum;
    private int dayOfWeek;

    public int getWeekNum() {
        if(null == courseTimeDate) return -1;
        Calendar c = Calendar.getInstance();
        c.setTime(courseTimeDate);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public int getDayOfWeek() {
        if(null == courseTimeDate) return -1;
        Calendar c = Calendar.getInstance();
        c.setTime(courseTimeDate);
        return c.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getCourseTimeDate() {
        return courseTimeDate;
    }

    public void setCourseTimeDate(Date courseTimeDate) {
        this.courseTimeDate = courseTimeDate;
    }

    public String getCourseTimeDateString() {
        return DateFormatUtils.format(courseTimeDate,"yyyyMMdd");
    }

    public void setCourseTimeDateString(String courseTimeDateString) {
        this.courseTimeDateString = courseTimeDateString;
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

    @Override
    public String toString() {
        return "CourseTimeDate{" +
                "courseTimeDate=" + courseTimeDate +
                ", courseTimeDateString='" + getCourseTimeDateString() + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
