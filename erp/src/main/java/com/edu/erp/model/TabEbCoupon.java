package com.edu.erp.model;

import java.util.List;

public class TabEbCoupon extends BaseObject {
    private static final long serialVersionUID = 1L;

    private String encoding;
    private String key;
    private String name;
    private String type;
    private String type_name;
    private Double rate;
    private Integer totals;
    private Integer used_num;
    private Integer validity;
    private Double amount_lower;
    private Double discount;
    private Integer course_num;
    private String fit;
    private Integer is_share;
    private String is_share_name;
    private String describe;
    private Long bu_id;
    private Long branch_id;
    private String status_name;
    private String all_course;

    List<TabEbCouponCourse> couponCourseList;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getTotals() {
        return totals;
    }

    public Integer getUsed_num() {
        return used_num;
    }

    public void setUsed_num(Integer used_num) {
        this.used_num = used_num;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Double getAmount_lower() {
        return amount_lower;
    }

    public void setAmount_lower(Double amount_lower) {
        this.amount_lower = amount_lower;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getCourse_num() {
        return course_num;
    }

    public void setCourse_num(Integer course_num) {
        this.course_num = course_num;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public Integer getIs_share() {
        return is_share;
    }

    public void setIs_share(Integer is_share) {
        this.is_share = is_share;
    }

    public String getIs_share_name() {
        return is_share_name;
    }

    public void setIs_share_name(String is_share_name) {
        this.is_share_name = is_share_name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getBu_id() {
        return bu_id;
    }

    public void setBu_id(Long bu_id) {
        this.bu_id = bu_id;
    }

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public List<TabEbCouponCourse> getCouponCourseList() {
        return couponCourseList;
    }

    public void setCouponCourseList(List<TabEbCouponCourse> couponCourseList) {
        this.couponCourseList = couponCourseList;
    }

    public String getAll_course() {
        return all_course;
    }

    public void setAll_course(String all_course) {
        this.all_course = all_course;
    }
}
