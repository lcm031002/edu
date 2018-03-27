package com.edu.erp.model;

/**
 * 学员状态修改记录类
 *
 * @author: linj
 * @create: 2018/2/9  17:12
 */
public class StudentStatusReviseNote {
    private Long id;
    private Long studentId;
    private Integer beforeStatus;
    private String beforeStatusName;
    private Integer afterStatus;
    private String afterStatusName;
    private Long buId;
    private Long updateUser;
    private String updateUserName;
    private String updateTime;
    private int ifShow;//为0则过滤，不展示给前端

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(Integer beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public Integer getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(Integer afterStatus) {
        this.afterStatus = afterStatus;
    }

    public Long getBuId() {
        return buId;
    }

    public void setBuId(Long buId) {
        this.buId = buId;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getBeforeStatusName() {
        return beforeStatusName;
    }

    public void setBeforeStatusName(String beforeStatusName) {
        this.beforeStatusName = beforeStatusName;
    }

    public String getAfterStatusName() {
        return afterStatusName;
    }

    public void setAfterStatusName(String afterStatusName) {
        this.afterStatusName = afterStatusName;
    }

    public int getIfShow() {
        return ifShow;
    }

    public void setIfShow(int ifShow) {
        this.ifShow = ifShow;
    }
}
