package com.edu.erp.model;

import java.io.Serializable;

public class Post implements Serializable {

    private static final long serialVersionUID = -155368268497815743L;

    private Long id;
    private String name;

    //岗位编码
    private String post_code;
    //岗位类型
    private Integer post_type;
    //岗位名称
    private String post_name;

    private String post_type_id;    //岗位Id， 现在的Post改成职务档案

    private String rank_id;    //职级Id， 现在的Post改成职务档案

    private  Integer status;


    public Integer getPost_type() {
        return post_type;
    }

    public void setPost_type(Integer post_type) {
        this.post_type = post_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_type_id() {
        return post_type_id;
    }

    public void setPost_type_id(String post_type_id) {
        this.post_type_id = post_type_id;
    }

    public String getRank_id() {
        return rank_id;
    }

    public void setRank_id(String rank_id) {
        this.rank_id = rank_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
