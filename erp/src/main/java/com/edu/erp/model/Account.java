package com.edu.erp.model;

import java.math.BigDecimal;

public class Account extends BaseObject {

    // 学生ID
    private Long studentId;

    // 校区ID
    private Long branchId;

    // 账户余额
    private BigDecimal feeAmount;

    /**
     * 设置 学生ID,对应字段 t_account.student_id
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取 学生ID,对应字段 t_account.student_id
     */
    public Long getStudentId() {
        return this.studentId;
    }

    /**
     * 设置 校区ID,对应字段 t_account.branch_id
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取 校区ID,对应字段 t_account.branch_id
     */
    public Long getBranchId() {
        return this.branchId;
    }

    /**
     * 设置 账户余额,对应字段 t_account.fee_amount
     */
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * 获取 账户余额,对应字段 t_account.fee_amount
     */
    public BigDecimal getFeeAmount() {
        return this.feeAmount;
    }
}