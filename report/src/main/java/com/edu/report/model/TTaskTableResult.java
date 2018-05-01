/**  
 * @Title: TTaskTableResult.java
 * @Package com.edu.report.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午5:45:32
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

import java.util.Date;

/**
 * @ClassName: TTaskTableResult
 * @Description: 任务运行依赖表记录
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午5:45:32
 * 
 */
public class TTaskTableResult {
	private Long id;
	private String taskTable;
	private String taskTableId;
	private Long taskTableLastVal;
	private Long taskTableCurrentVal;
	private Date taskLastTime;
	private Long taskLastResult;
	private String taskTableType;
	private String taskFlow;
	private String taskId;
	private String classImpl;
	private String startId;
	private Long pageSize;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getTaskTable() {
		return taskTable;
	}

	public final void setTaskTable(String taskTable) {
		this.taskTable = taskTable;
	}

	public final String getTaskTableId() {
		return taskTableId;
	}

	public final void setTaskTableId(String taskTableId) {
		this.taskTableId = taskTableId;
	}

	public final Long getTaskTableLastVal() {
		return taskTableLastVal;
	}

	public final void setTaskTableLastVal(Long taskTableLastVal) {
		this.taskTableLastVal = taskTableLastVal;
	}

	public final Long getTaskTableCurrentVal() {
		return taskTableCurrentVal;
	}

	public final void setTaskTableCurrentVal(Long taskTableCurrentVal) {
		this.taskTableCurrentVal = taskTableCurrentVal;
	}

	public final Date getTaskLastTime() {
		return taskLastTime;
	}

	public final void setTaskLastTime(Date taskLastTime) {
		this.taskLastTime = taskLastTime;
	}

	public final Long getTaskLastResult() {
		return taskLastResult;
	}

	public final void setTaskLastResult(Long taskLastResult) {
		this.taskLastResult = taskLastResult;
	}

	public final String getTaskTableType() {
		return taskTableType;
	}

	public final void setTaskTableType(String taskTableType) {
		this.taskTableType = taskTableType;
	}

	public final String getTaskFlow() {
		return taskFlow;
	}

	public final void setTaskFlow(String taskFlow) {
		this.taskFlow = taskFlow;
	}

	public final String getTaskId() {
		return taskId;
	}

	public final void setTaskId(String taskId) {
		this.taskId = taskId;
	}

    public String getClassImpl() {
        return classImpl;
    }

    public void setClassImpl(String classImpl) {
        this.classImpl = classImpl;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
	
}
