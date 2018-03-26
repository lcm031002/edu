package com.edu.erp.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccountDymanic extends BaseObject {

    // 类型 :-1:初始数据 1：充值 2：转账  3：理赔 4：取款  5:充值作废 6：理赔作废 7：取款作废 8:一元转校
    private Integer dynamicType;

    // 校区
    private Long branchId;

    // 学生
    private Long studentId;

    // 账户
    private Long accountId;

    // 金额
    private BigDecimal money;

    // 收付费类型 tp_fee_flag
    private Integer payFlag;

    // 收付费方式 tp_pay_mode
    private Integer payMode;

    // 操作人
    private Long inputUser;

    // 操作时间
    private Date inputTime;

    // 审批人
    private Long approveUser;

    // 审批时间
    private Date approveTime;

    // 确认人
    private Long confirmUser;

    // 确认时间
    private Date confirmTime;

    // 状态 1:待审批 2：未通过 3：生效

    // 备注
    private String remark;

    // 编号
    private String encoding;

    // 作废记录
    private Long dynamicId;

    private BigDecimal moneyFee;

    private Integer accountType;

    private String refundChangeNo;


    /**
     * 设置 类型 :-1:初始数据 1：充值 2：转账  3：理赔 4：取款  5:充值作废 6：理赔作废 7：取款作废 8:一元转校,对应字段 t_account_dymanic.dynamic_type
     */
    public void setDynamicType(Integer dynamicType) {
        this.dynamicType = dynamicType;
    }

    /**
     * 获取 类型 :-1:初始数据 1：充值 2：转账  3：理赔 4：取款  5:充值作废 6：理赔作废 7：取款作废 8:一元转校,对应字段 t_account_dymanic.dynamic_type
     */
    public Integer getDynamicType() {
        return this.dynamicType;
    }

    /**
     * 设置 校区,对应字段 t_account_dymanic.branch_id
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取 校区,对应字段 t_account_dymanic.branch_id
     */
    public Long getBranchId() {
        return this.branchId;
    }

    /**
     * 设置 学生,对应字段 t_account_dymanic.student_id
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取 学生,对应字段 t_account_dymanic.student_id
     */
    public Long getStudentId() {
        return this.studentId;
    }

    /**
     * 设置 账户,对应字段 t_account_dymanic.account_id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取 账户,对应字段 t_account_dymanic.account_id
     */
    public Long getAccountId() {
        return this.accountId;
    }

    /**
     * 设置 金额,对应字段 t_account_dymanic.money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取 金额,对应字段 t_account_dymanic.money
     */
    public BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置 收付费类型 tp_fee_flag,对应字段 t_account_dymanic.pay_flag
     */
    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    /**
     * 获取 收付费类型 tp_fee_flag,对应字段 t_account_dymanic.pay_flag
     */
    public Integer getPayFlag() {
        return this.payFlag;
    }

    /**
     * 设置 收付费方式 tp_pay_mode,对应字段 t_account_dymanic.pay_mode
     */
    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    /**
     * 获取 收付费方式 tp_pay_mode,对应字段 t_account_dymanic.pay_mode
     */
    public Integer getPayMode() {
        return this.payMode;
    }

    /**
     * 设置 操作人,对应字段 t_account_dymanic.input_user
     */
    public void setInputUser(Long inputUser) {
        this.inputUser = inputUser;
    }

    /**
     * 获取 操作人,对应字段 t_account_dymanic.input_user
     */
    public Long getInputUser() {
        return this.inputUser;
    }

    /**
     * 设置 操作时间,对应字段 t_account_dymanic.input_time
     */
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * 获取 操作时间,对应字段 t_account_dymanic.input_time
     */
    public Date getInputTime() {
        return this.inputTime;
    }

    /**
     * 设置 审批人,对应字段 t_account_dymanic.approve_user
     */
    public void setApproveUser(Long approveUser) {
        this.approveUser = approveUser;
    }

    /**
     * 获取 审批人,对应字段 t_account_dymanic.approve_user
     */
    public Long getApproveUser() {
        return this.approveUser;
    }

    /**
     * 设置 审批时间,对应字段 t_account_dymanic.approve_time
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 获取 审批时间,对应字段 t_account_dymanic.approve_time
     */
    public Date getApproveTime() {
        return this.approveTime;
    }

    /**
     * 设置 确认人,对应字段 t_account_dymanic.confirm_user
     */
    public void setConfirmUser(Long confirmUser) {
        this.confirmUser = confirmUser;
    }

    /**
     * 获取 确认人,对应字段 t_account_dymanic.confirm_user
     */
    public Long getConfirmUser() {
        return this.confirmUser;
    }

    /**
     * 设置 确认时间,对应字段 t_account_dymanic.confirm_time
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * 获取 确认时间,对应字段 t_account_dymanic.confirm_time
     */
    public Date getConfirmTime() {
        return this.confirmTime;
    }

    /**
     * 设置 备注,对应字段 t_account_dymanic.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 备注,对应字段 t_account_dymanic.remark
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 编号,对应字段 t_account_dymanic.encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 获取 编号,对应字段 t_account_dymanic.encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置 作废记录,对应字段 t_account_dymanic.dynamic_id
     */
    public void setDynamicId(Long dynamicId) {
        this.dynamicId = dynamicId;
    }

    /**
     * 获取 作废记录,对应字段 t_account_dymanic.dynamic_id
     */
    public Long getDynamicId() {
        return this.dynamicId;
    }

    public void setMoneyFee(BigDecimal moneyFee) {
        this.moneyFee = moneyFee;
    }

    public BigDecimal getMoneyFee() {
        return this.moneyFee;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountType() {
        return this.accountType;
    }

    public void setRefundChangeNo(String refundChangeNo) {
        this.refundChangeNo = refundChangeNo;
    }

    public String getRefundChangeNo() {
        return this.refundChangeNo;
    }
}