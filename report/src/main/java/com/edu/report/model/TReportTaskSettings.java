/**  
 * @Title: TReportTaskSettings.java
 * @Package com.edu.report.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午5:00:36
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

/**
 * 
 * @ClassName: TReportTaskSettings
 * @Description: 报表任务设置
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月24日 下午5:00:36
 * 
 */
public class TReportTaskSettings {
    private Long id;

    private String taskName;

    private String taskId;

    private String startDate;

    private String endDate;

    private String runTime;

    private Long unit;

    private String unitName;

    private Long period;

    private String tables;

    private String classImpl;

    private String type;

    private String typeName;

    private String remark;

    private Long status;

    private String statusName;

    private Long pageSize;

    private String startId;

    public final String getClassImpl() {
        return classImpl;
    }

    public final String getEndDate() {
        return endDate;
    }

    public final Long getId() {
        return id;
    }

    public final Long getPeriod() {
        return period;
    }

    public final String getRemark() {
        return remark;
    }

    public final String getRunTime() {
        return runTime;
    }

    public final String getStartDate() {
        return startDate;
    }

    public final Long getStatus() {
        return status;
    }

    public final String getTables() {
        return tables;
    }

    public final String getTaskId() {
        return taskId;
    }

    public final String getTaskName() {
        return taskName;
    }

    public final String getType() {
        return type;
    }

    public final Long getUnit() {
        return unit;
    }

    public final void setClassImpl(String classImpl) {
        this.classImpl = classImpl;
    }

    public final void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final void setPeriod(Long period) {
        this.period = period;
    }

    public final void setRemark(String remark) {
        this.remark = remark;
    }

    public final void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public final void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public final void setStatus(Long status) {
        this.status = status;
    }

    public final void setTables(String tables) {
        this.tables = tables;
    }

    public final void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public final void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public final void setType(String type) {
        this.type = type;
    }

    public final void setUnit(Long unit) {
        this.unit = unit;
    }

    public final Long getPageSize() {
        return pageSize;
    }

    public final void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public final String getStartId() {
        return startId;
    }

    public final void setStartId(String startId) {
        this.startId = startId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
