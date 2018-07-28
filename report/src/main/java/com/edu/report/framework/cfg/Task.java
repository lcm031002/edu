package com.edu.report.framework.cfg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: Task
 * @Description: 任务定义对象
 *
 */
@XmlRootElement(name = "task")
public class Task implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -7764658945259158563L;
	
	private Long id;
	
	@XmlElement(name = "taskId") 
	private String taskId;

	@XmlElement(name = "name") 
	private String name;

	@XmlElement(name = "classImpl") 
	private String classImpl;

	@XmlElement(name = "tables") 
	private String tables;

	@XmlElement(name = "startDate") 
	private String startDate;

	@XmlElement(name = "endDate") 
	private String endDate;

	@XmlElement(name = "unit") 
	private String unit;

	@XmlElement(name = "period") 
	private String period;

	@XmlElement(name = "runTime") 
	private String runTime;

	@XmlElement(name = "type")
	private String type;
	
	@XmlElement(name = "pageSize")
	private String pageSize;
	
	@XmlElement(name = "startId")
	private String startId;

	public final String getTaskId() {
		return taskId;
	}

	public final String getName() {
		return name;
	}

	public final String getClassImpl() {
		return classImpl;
	}

	public final String getTables() {
		return tables;
	}

	public final String getStartDate() {
		return startDate;
	}

	public final String getEndDate() {
		return endDate;
	}

	public final String getRunTime() {
		return runTime;
	}

	public final String getUnit() {
		return unit;
	}

	public final String getPeriod() {
		return period;
	}

	public final String getType() {
		return type;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getPageSize() {
		return pageSize;
	}

	public final String getStartId() {
		return startId;
	}

	
}
