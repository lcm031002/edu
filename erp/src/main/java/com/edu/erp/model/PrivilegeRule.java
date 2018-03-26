package com.edu.erp.model;

import java.math.BigDecimal;

public class PrivilegeRule extends BaseObject {

    // 优惠规则名称
    private String ruleName;

    // 优惠规则 1折扣优惠   2 优惠金额  3每课时优惠
    private Integer couponType;

    // 优惠
    private BigDecimal couponContent;

    // 开始日期
    private String startDate;

    // 结束日期
    private String endDate;

    // 适用范围1通用 2老学员3新学员
    private Integer useScope;

    // 产品线
    private Integer productLine;

    /**
     * 设置 优惠规则名称,对应字段 tab_privilege_rule.rule_name
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * 获取 优惠规则名称,对应字段 tab_privilege_rule.rule_name
     */
    public String getRuleName() {
        return this.ruleName;
    }

    /**
     * 设置 优惠规则 1折扣优惠   2 优惠金额  3每课时优惠,对应字段 tab_privilege_rule.coupon_type
     */
    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    /**
     * 获取 优惠规则 1折扣优惠   2 优惠金额  3每课时优惠,对应字段 tab_privilege_rule.coupon_type
     */
    public Integer getCouponType() {
        return this.couponType;
    }

    /**
     * 设置 优惠,对应字段 tab_privilege_rule.coupon_content
     */
    public void setCouponContent(BigDecimal couponContent) {
        this.couponContent = couponContent;
    }

    /**
     * 获取 优惠,对应字段 tab_privilege_rule.coupon_content
     */
    public BigDecimal getCouponContent() {
        return this.couponContent;
    }

    /**
     * 设置 开始日期,对应字段 tab_privilege_rule.start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取 开始日期,对应字段 tab_privilege_rule.start_date
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * 设置 结束日期,对应字段 tab_privilege_rule.end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取 结束日期,对应字段 tab_privilege_rule.end_date
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * 设置 适用范围1通用 2老学员3新学员,对应字段 tab_privilege_rule.use_scope
     */
    public void setUseScope(Integer useScope) {
        this.useScope = useScope;
    }

    /**
     * 获取 适用范围1通用 2老学员3新学员,对应字段 tab_privilege_rule.use_scope
     */
    public Integer getUseScope() {
        return this.useScope;
    }

    /**
     * 设置 产品线,对应字段 tab_privilege_rule.product_line
     */
    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    /**
     * 获取 产品线,对应字段 tab_privilege_rule.product_line
     */
    public Integer getProductLine() {
        return this.productLine;
    }
}