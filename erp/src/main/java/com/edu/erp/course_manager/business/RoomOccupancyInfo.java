package com.edu.erp.course_manager.business;

import com.edu.erp.model.TRoom;

import java.util.Date;

/**
 * Created by zenglw on 2017/11/16.
 * 教室占用信息
 */
public class RoomOccupancyInfo {


    private  Long id;
    // 教室名称
    private String roomName;
    // 教室编码
    private String roomCode;
    // 所属校区名称
    private String branchName;
    // 教室人数
    private Long roomNum;
    //占用或空闲日期
    private Date courseDate;

    public RoomOccupancyInfo() {
    }

    public RoomOccupancyInfo(RoomOccupancyInfo room,Date date) {
        this.id = room.getId();
        this.roomCode = room.getRoomCode();
        this.roomName = room.getRoomName();
        this.branchName = room.getBranchName();
        this.roomNum = room.getRoomNum();
        this.courseDate = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Long roomNum) {
        this.roomNum = roomNum;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }
}
