package com.ebusiness.common.model;

public class InvoicesSequence {
    private Long id; // 主键
    private Long invoices_type; // 单据类型 1：充值单据 2：缴费单据 3：转班单据 4：退费单据 5：取款单据
                                // 6：理赔单据 7：转账单据 8：考勤单据
    private Long sequence; // 单据编号序列

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoices_type() {
        return invoices_type;
    }

    public void setInvoices_type(Long invoices_type) {
        this.invoices_type = invoices_type;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
}
