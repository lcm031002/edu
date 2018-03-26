package com.edu.erp.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccountChange extends BaseObject {

    // 账户ID
    private Long accountId;

    // 订单ID
    private Long orderId;

    // 单据ID
    private Long encoderId;

    // 变更标识:0存入,1取出
    private Integer changeFlag;

    // 变更类型:-2v3账户迁移到v5新增记录,0客户充值,1订单收费取出,2订单退费存入,3客户取出,4一元转校,5:转账6:理赔7:转班转入8:充值作废 9：理赔作废 10：取款作废 11:报班作废 12:退费作废 13:冻结
    private Integer changeType;

    // 变更金额
    private BigDecimal changeAmount;

    // 变更前金额
    private BigDecimal preAmount;

    // 变更后金额
    private BigDecimal nextAmount;

    // 收付费类型:0现金,1内部转账,2银行转账
    private Integer payMode;

    // 动户记录ID
    private Long dynamicId;

    // 交易时间
    private Date changeTime;

    private Integer accountType;


    /**
     * 设置 账户ID,对应字段 t_account_change.account_id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取 账户ID,对应字段 t_account_change.account_id
     */
    public Long getAccountId() {
        return this.accountId;
    }

    /**
     * 设置 订单ID,对应字段 t_account_change.order_id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 订单ID,对应字段 t_account_change.order_id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 单据ID,对应字段 t_account_change.encoder_id
     */
    public void setEncoderId(Long encoderId) {
        this.encoderId = encoderId;
    }

    /**
     * 获取 单据ID,对应字段 t_account_change.encoder_id
     */
    public Long getEncoderId() {
        return this.encoderId;
    }

    /**
     * 设置 变更标识:0存入,1取出,对应字段 t_account_change.change_flag
     */
    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
    }

    /**
     * 获取 变更标识:0存入,1取出,对应字段 t_account_change.change_flag
     */
    public Integer getChangeFlag() {
        return this.changeFlag;
    }

    /**
     * 设置 变更类型:-2v3账户迁移到v5新增记录,0客户充值,1订单收费取出,2订单退费存入,3客户取出,4一元转校,5:转账6:理赔7:转班转入8:充值作废 9：理赔作废 10：取款作废 11:报班作废 12:退费作废 13:冻结,对应字段 t_account_change.change_type
     */
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    /**
     * 获取 变更类型:-2v3账户迁移到v5新增记录,0客户充值,1订单收费取出,2订单退费存入,3客户取出,4一元转校,5:转账6:理赔7:转班转入8:充值作废 9：理赔作废 10：取款作废 11:报班作废 12:退费作废 13:冻结,对应字段 t_account_change.change_type
     */
    public Integer getChangeType() {
        return this.changeType;
    }

    /**
     * 设置 变更金额,对应字段 t_account_change.change_amount
     */
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    /**
     * 获取 变更金额,对应字段 t_account_change.change_amount
     */
    public BigDecimal getChangeAmount() {
        return this.changeAmount;
    }

    /**
     * 设置 变更前金额,对应字段 t_account_change.pre_amount
     */
    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
    }

    /**
     * 获取 变更前金额,对应字段 t_account_change.pre_amount
     */
    public BigDecimal getPreAmount() {
        return this.preAmount;
    }

    /**
     * 设置 变更后金额,对应字段 t_account_change.next_amount
     */
    public void setNextAmount(BigDecimal nextAmount) {
        this.nextAmount = nextAmount;
    }

    /**
     * 获取 变更后金额,对应字段 t_account_change.next_amount
     */
    public BigDecimal getNextAmount() {
        return this.nextAmount;
    }

    /**
     * 设置 收付费类型:0现金,1内部转账,2银行转账,对应字段 t_account_change.pay_mode
     */
    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    /**
     * 获取 收付费类型:0现金,1内部转账,2银行转账,对应字段 t_account_change.pay_mode
     */
    public Integer getPayMode() {
        return this.payMode;
    }

    /**
     * 设置 动户记录ID,对应字段 t_account_change.dynamic_id
     */
    public void setDynamicId(Long dynamicId) {
        this.dynamicId = dynamicId;
    }

    /**
     * 获取 动户记录ID,对应字段 t_account_change.dynamic_id
     */
    public Long getDynamicId() {
        return this.dynamicId;
    }

    /**
     * 设置 交易时间,对应字段 t_account_change.change_time
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * 获取 交易时间,对应字段 t_account_change.change_time
     */
    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAccountType() {
        return this.accountType;
    }
}