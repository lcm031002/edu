package com.edu.erp.model;

public class TabChangeCourseTimes {
    private Long id;
    private Long change_id;
    private Long change_course_id;
    private Integer course_times;
    private Double pre_price;
    private Double next_price;
    private String change_no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChange_id() {
        return change_id;
    }

    public void setChange_id(Long change_id) {
        this.change_id = change_id;
    }

    public Long getChange_course_id() {
        return change_course_id;
    }

    public void setChange_course_id(Long change_course_id) {
        this.change_course_id = change_course_id;
    }

    public Integer getCourse_times() {
        return course_times;
    }

    public void setCourse_times(Integer course_times) {
        this.course_times = course_times;
    }

    public Double getPre_price() {
        return pre_price;
    }

    public void setPre_price(Double pre_price) {
        this.pre_price = pre_price;
    }

    public Double getNext_price() {
        return next_price;
    }

    public void setNext_price(Double next_price) {
        this.next_price = next_price;
    }

    public String getChange_no() {
        return change_no;
    }

    public void setChange_no(String change_no) {
        this.change_no = change_no;
    }

}
