package com.edu.erp.course_manager.business;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zenglw on 2017/11/17.
 * 教室安排表，单个教室排课详情
 */
public class RoomSchedulingInfo {

    /**
     * 排课记录ID
     */
    private Long schedulingId;

    /**
     * 课程课次教室关系id
     */
    private Long courseTimeRoomRef;
    /**
     * 上课日期
     */
    private Date courseDate;
    /**
     * 上课日期-字符串表示
     */
    private String courseDateString;
    /**
     * 上课时间
     */
    private String startTime;
    /**
     * 下课时间
     */
    private String endTime;
    /**
     * 教室名称
     */
    private String roomName;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程教师
     */
    private String teacherName;
    /**
     * 中文教师名称
     */
    private String chineseTeacherName;
    /**
     * 外文教师名称
     */
    private String foreignTeacherName;
    /**
     * 辅导教师名称
     */
    private String assistTeacherName;
    /**
     * 校区名称
     */
    private String branchName;
    /**
     * 校区ID
     */
    private Long branchId;

    /**
     * 在读学生总数
     */
    private Integer readingStudent;

    /**
     * 报名学生总数
     */
    private Integer signUpStudent;

    /**
     * 第几次课
     */
    private short courseTime;

    /**
     * 教室id
     */
    private Long roomId;

    /**星期*/
    private Integer weekend;

    public Long getCourseTimeRoomRef() {
        return courseTimeRoomRef;
    }

    public void setCourseTimeRoomRef(Long courseTimeRoomRef) {
        this.courseTimeRoomRef = courseTimeRoomRef;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Long schedulingId) {
        this.schedulingId = schedulingId;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getChineseTeacherName() {
        return chineseTeacherName;
    }

    public void setChineseTeacherName(String chineseTeacherName) {
        this.chineseTeacherName = chineseTeacherName;
    }

    public String getForeignTeacherName() {
        return foreignTeacherName;
    }

    public void setForeignTeacherName(String foreignTeacherName) {
        this.foreignTeacherName = foreignTeacherName;
    }

    public String getAssistTeacherName() {
        return assistTeacherName;
    }

    public void setAssistTeacherName(String assistTeacherName) {
        this.assistTeacherName = assistTeacherName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getReadingStudent() {
        return readingStudent;
    }

    public void setReadingStudent(Integer readingStudent) {
        this.readingStudent = readingStudent;
    }

    public Integer getSignUpStudent() {
        return signUpStudent;
    }

    public void setSignUpStudent(Integer signUpStudent) {
        this.signUpStudent = signUpStudent;
    }

    public short getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(short courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getWeekend() {
        if(null == courseDate) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCourseDate());
        int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
        setWeekend(day == 0?7:day);
        return this.weekend ;
    }

    public void setWeekend(Integer weekend) {
        this.weekend = weekend;
    }

    public String getCourseDateString() {
        if(null == courseDate) {
            return null;
        }
        setCourseDateString(DateFormatUtils.format(this.getCourseDate(),"yyyy-MM-dd"));
        return courseDateString;
    }

    public void setCourseDateString(String courseDateString) {
        this.courseDateString = courseDateString;
    }
}
