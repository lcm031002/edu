package com.edu.erp.model;

public class UserNoticeRel {
    private Long id;
    private Long user_id;
    private Long notice_id;
    private Integer read_flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(Long notice_id) {
        this.notice_id = notice_id;
    }

    public Integer getRead_flag() {
        return read_flag;
    }

    public void setRead_flag(Integer read_flag) {
        this.read_flag = read_flag;
    }

}
