package com.edu.erp.model;

/**
 * 考勤辅助数据
 * @Author zenglw@20171021
 */
public class AttendanceAuxiliaryData {
    private Long orderId;
    private Long orderCourseId;
    private Long discountUnitPrice;
    private Long courseBranchId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderCourseId() {
        return orderCourseId;
    }

    public void setOrderCourseId(Long orderCourseId) {
        this.orderCourseId = orderCourseId;
    }

    public Long getDiscountUnitPrice() {
        return discountUnitPrice;
    }

    public void setDiscountUnitPrice(Long discountUnitPrice) {
        this.discountUnitPrice = discountUnitPrice;
    }

    public Long getCourseBranchId() {
        return courseBranchId;
    }

    public void setCourseBranchId(Long courseBranchId) {
        this.courseBranchId = courseBranchId;
    }
}
