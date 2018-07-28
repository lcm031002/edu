package com.edu.report.model;

import java.util.Date;

/**
 * @ClassName: TReportRunResult
 * @Description: 任务运算结果表
 *
 */
public class TReportRunResult {
    private Long id;

    private String taskId;

    private String taskName;

    private Date runTime;

    private Long runResult;

    private String runResultName;

    private String taskFlow;

    private String remark;

    private Long startTime;

    private Long endTime;

    private String type;

    private String typeName;

    private Long taskTableLastVal;

    private Long taskTableCurrentVal;

    private String taskTableType;

    private String taskTableTypeName;

    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final String getTaskId() {
        return taskId;
    }

    public final void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public final String getTaskName() {
        return taskName;
    }

    public final void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public final Date getRunTime() {
        return runTime;
    }

    public final void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public final Long getRunResult() {
        return runResult;
    }

    public final void setRunResult(Long runResult) {
        this.runResult = runResult;
    }

    public final String getRemark() {
        return remark;
    }

    public final void setRemark(String remark) {
        this.remark = remark;
    }

    public final String getTaskFlow() {
        return taskFlow;
    }

    public final void setTaskFlow(String taskFlow) {
        this.taskFlow = taskFlow;
    }

    public final Long getStartTime() {
        return startTime;
    }

    public final void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public final Long getEndTime() {
        return endTime;
    }

    public final void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRunResultName() {
        return runResultName;
    }

    public void setRunResultName(String runResultName) {
        this.runResultName = runResultName;
    }

    public Long getTaskTableLastVal() {
        return taskTableLastVal;
    }

    public void setTaskTableLastVal(Long taskTableLastVal) {
        this.taskTableLastVal = taskTableLastVal;
    }

    public Long getTaskTableCurrentVal() {
        return taskTableCurrentVal;
    }

    public void setTaskTableCurrentVal(Long taskTableCurrentVal) {
        this.taskTableCurrentVal = taskTableCurrentVal;
    }

    public String getTaskTableType() {
        return taskTableType;
    }

    public void setTaskTableType(String taskTableType) {
        this.taskTableType = taskTableType;
    }

    public String getTaskTableTypeName() {
        return taskTableTypeName;
    }

    public void setTaskTableTypeName(String taskTableTypeName) {
        this.taskTableTypeName = taskTableTypeName;
    }

}
