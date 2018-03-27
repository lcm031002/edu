package com.edu.erp.model;

public class TabChangeCourse {
    private Long id;
    private Long change_id;
    private Long order_id;
    private Long order_course_id;
    private Integer course_times;
    private Double total_amount;
    private Double attend_amount;
    private Double pre_amount;
    private Long change_user;
    private String change_no;
    private Integer transfer_flag;
    private Double fee_returns_amount;
    private Double fee_deduction_amount;
    private Integer premium_type;

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

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getOrder_course_id() {
        return order_course_id;
    }

    public void setOrder_course_id(Long order_course_id) {
        this.order_course_id = order_course_id;
    }

    public Integer getCourse_times() {
        return course_times;
    }

    public void setCourse_times(Integer course_times) {
        this.course_times = course_times;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getAttend_amount() {
        return attend_amount;
    }

    public void setAttend_amount(Double attend_amount) {
        this.attend_amount = attend_amount;
    }

    public Double getPre_amount() {
        return pre_amount;
    }

    public void setPre_amount(Double pre_amount) {
        this.pre_amount = pre_amount;
    }

    public Long getChange_user() {
        return change_user;
    }

    public void setChange_user(Long change_user) {
        this.change_user = change_user;
    }

    public String getChange_no() {
        return change_no;
    }

    public void setChange_no(String change_no) {
        this.change_no = change_no;
    }

    public Integer getTransfer_flag() {
        return transfer_flag;
    }

    public void setTransfer_flag(Integer transfer_flag) {
        this.transfer_flag = transfer_flag;
    }

    public Double getFee_returns_amount() {
        return fee_returns_amount;
    }

    public void setFee_returns_amount(Double fee_returns_amount) {
        this.fee_returns_amount = fee_returns_amount;
    }

    public Double getFee_deduction_amount() {
        return fee_deduction_amount;
    }

    public void setFee_deduction_amount(Double fee_deduction_amount) {
        this.fee_deduction_amount = fee_deduction_amount;
    }

    public Integer getPremium_type() {
        return premium_type;
    }

    public void setPremium_type(Integer premium_type) {
        this.premium_type = premium_type;
    }
}
