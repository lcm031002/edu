/**  
 * @Title: JPDLModel.java
 * @Package com.ebusiness.workflow.xml
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月3日 上午11:32:21
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.xml.model;

import java.util.List;

/**
 * @ClassName: JPDLModel
 * @Description: JPDL文件模型
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月3日 上午11:32:21
 * 
 */
public class JPDLModel {

	private String key;

	private String name;

	private String version;

	private String description;

	private List<JPDLTaskModel> task;


	public String getDescription() {
		return description;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public List<JPDLTaskModel> getTask() {
		return task;
	}

	public String getVersion() {
		return version;
	}

	public void setTask(List<JPDLTaskModel> task) {
		this.task = task;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
