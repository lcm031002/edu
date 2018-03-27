package com.edu.erp.course_manager.business;

import java.util.Date;
import java.util.List;

/**
 * Created by zenglw on 2017/12/9.
 * 延课单
 */
public class DelayCourseRecord {

    private Long id;
    /**延课总数*/
    private Integer delayCourseNum;
    /**制单时间*/
    private Date createTime;
    /**制单人*/
    private Long createUser;
    /**制单人姓名*/
    private String createUserName;
    /**延课日期*/
    private String delayCourseDate;
    /**延课原因*/
    private String reason;

    private Long buId;
    //延课是否成功
    private Integer error;

    /**延课课程列表详情*/
    private List<DelayCourseDetail> delayCourseDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuId() {
        return buId;
    }

    public void setBuId(Long buId) {
        this.buId = buId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<DelayCourseDetail> getDelayCourseDetailList() {
        return delayCourseDetailList;
    }

    public void setDelayCourseDetailList(List<DelayCourseDetail> delayCourseDetailList) {
        this.delayCourseDetailList = delayCourseDetailList;
    }

    public Integer getDelayCourseNum() {
        return delayCourseNum;
    }

    public void setDelayCourseNum(Integer delayCourseNum) {
        this.delayCourseNum = delayCourseNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getDelayCourseDate() {
        return delayCourseDate;
    }

    public void setDelayCourseDate(String delayCourseDate) {
        this.delayCourseDate = delayCourseDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
