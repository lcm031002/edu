package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeSummarize implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long employee_id;
    private Date start_time;
    private Date end_time;
    //标题
    private String title;
    //内容
    private String content;
    //审批人
    private Integer approver;
    //审批状态
    private Integer approval_status;
    //审批意见
    private String approval_opinion;
    //附件
    private String enclosure;
    //审批人姓名
    private String employeeName;


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApprover() {
        return approver;
    }

    public void setApprover(Integer approver) {
        this.approver = approver;
    }

    public Integer getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(Integer approval_status) {
        this.approval_status = approval_status;
    }

    public String getApproval_opinion() {
        return approval_opinion;
    }

    public void setApproval_opinion(String approval_opinion) {
        this.approval_opinion = approval_opinion;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }


}
