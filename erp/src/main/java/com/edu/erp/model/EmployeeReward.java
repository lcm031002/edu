package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeReward implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long employee_id;
    //获奖时间
    private Date date_time;
    //发奖单位
    private String award_party;
    //奖项名称
    private String award_name;
    private String describe;
    private String enclosure;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getAward_name() {
        return award_name;
    }

    public void setAward_name(String award_name) {
        this.award_name = award_name;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getAward_party() {
        return award_party;
    }

    public void setAward_party(String award_party) {
        this.award_party = award_party;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }


}
