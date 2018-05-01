package com.edu.report.model;

import java.util.Date;

public class TReportAccountFlow {
    private Long id;
    private Long branch_id;
    private String branch_name;
    private Long bu_id;
    private String bu_name;
    private String student_code;
    private String student_name;
    private Long operate_type;
    private String operate_type_name;
    private Double change_account_amount;
    private Double change_frozen_amount;
    private Double change_refund_amount;
    private Double before_account_amount;
    private Double before_frozen_amount;
    private Double before_refund_amount;
    private Double after_account_amount;
    private Double after_frozen_amount;
    private Double after_refund_amount;
    private Double charge_mode;
    private String charge_mode_name;
    private Date operate_time;
    private Long operate_id;
    private String operate_name;
    private String task_flow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public Long getBu_id() {
        return bu_id;
    }

    public void setBu_id(Long bu_id) {
        this.bu_id = bu_id;
    }

    public String getBu_name() {
        return bu_name;
    }

    public void setBu_name(String bu_name) {
        this.bu_name = bu_name;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Long getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(Long operate_type) {
        this.operate_type = operate_type;
    }

    public String getOperate_type_name() {
        return operate_type_name;
    }

    public void setOperate_type_name(String operate_type_name) {
        this.operate_type_name = operate_type_name;
    }

    public Double getChange_account_amount() {
        return change_account_amount;
    }

    public void setChange_account_amount(Double change_account_amount) {
        this.change_account_amount = change_account_amount;
    }

    public Double getChange_frozen_amount() {
        return change_frozen_amount;
    }

    public void setChange_frozen_amount(Double change_frozen_amount) {
        this.change_frozen_amount = change_frozen_amount;
    }

    public Double getChange_refund_amount() {
        return change_refund_amount;
    }

    public void setChange_refund_amount(Double change_refund_amount) {
        this.change_refund_amount = change_refund_amount;
    }

    public Double getBefore_account_amount() {
        return before_account_amount;
    }

    public void setBefore_account_amount(Double before_account_amount) {
        this.before_account_amount = before_account_amount;
    }

    public Double getBefore_frozen_amount() {
        return before_frozen_amount;
    }

    public void setBefore_frozen_amount(Double before_frozen_amount) {
        this.before_frozen_amount = before_frozen_amount;
    }

    public Double getBefore_refund_amount() {
        return before_refund_amount;
    }

    public void setBefore_refund_amount(Double before_refund_amount) {
        this.before_refund_amount = before_refund_amount;
    }

    public Double getAfter_account_amount() {
        return after_account_amount;
    }

    public void setAfter_account_amount(Double after_account_amount) {
        this.after_account_amount = after_account_amount;
    }

    public Double getAfter_frozen_amount() {
        return after_frozen_amount;
    }

    public void setAfter_frozen_amount(Double after_frozen_amount) {
        this.after_frozen_amount = after_frozen_amount;
    }

    public Double getAfter_refund_amount() {
        return after_refund_amount;
    }

    public void setAfter_refund_amount(Double after_refund_amount) {
        this.after_refund_amount = after_refund_amount;
    }

    public Double getCharge_mode() {
        return charge_mode;
    }

    public void setCharge_mode(Double charge_mode) {
        this.charge_mode = charge_mode;
    }

    public String getCharge_mode_name() {
        return charge_mode_name;
    }

    public void setCharge_mode_name(String charge_mode_name) {
        this.charge_mode_name = charge_mode_name;
    }

    public Date getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(Date operate_time) {
        this.operate_time = operate_time;
    }

    public Long getOperate_id() {
        return operate_id;
    }

    public void setOperate_id(Long operate_id) {
        this.operate_id = operate_id;
    }

    public String getOperate_name() {
        return operate_name;
    }

    public void setOperate_name(String operate_name) {
        this.operate_name = operate_name;
    }

    public String getTask_flow() {
        return task_flow;
    }

    public void setTask_flow(String task_flow) {
        this.task_flow = task_flow;
    }

}
