package com.edu.erp.model;

public class TabEbCouponCourse extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long coupon_id;
    private Long course_id;

    private TCourse course;

    public Long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public TCourse getCourse() {
        return course;
    }

    public void setCourse(TCourse course) {
        this.course = course;
    }

}
