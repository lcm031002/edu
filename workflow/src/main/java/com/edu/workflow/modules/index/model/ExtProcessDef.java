package com.edu.workflow.modules.index.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName: ExtProcessDef
 * @Description: 流程定义信息表
 *
 */
public class ExtProcessDef implements Serializable{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -579493392520260198L;
	
	private long id;
	
	private String processZip;
	
	private String processJpdl;
	
	private String processPng;
	
	private String processKey;
	
	private String processName;
	
	private Integer processState;
	
	private String processDeployId;
	
	private Date processCreateTime;
	
	private Set<ExtProcessRoleDef> processRoleDefSet;

	public Date getProcessCreateTime() {
		return processCreateTime;
	}

	public String getProcessDeployId() {
		return processDeployId;
	}

	public String getProcessJpdl() {
		return processJpdl;
	}

	public String getProcessPng() {
		return processPng;
	}

	public Integer getProcessState() {
		return processState;
	}

	public String getProcessZip() {
		return processZip;
	}

	public void setProcessCreateTime(Date processCreateTime) {
		this.processCreateTime = processCreateTime;
	}

	public void setProcessDeployId(String processDeployId) {
		this.processDeployId = processDeployId;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessJpdl(String processJpdl) {
		this.processJpdl = processJpdl;
	}

	public void setProcessPng(String processPng) {
		this.processPng = processPng;
	}

	public void setProcessState(Integer processState) {
		this.processState = processState;
	}

	public void setProcessZip(String processZip) {
		this.processZip = processZip;
	}

	public final Set<ExtProcessRoleDef> getProcessRoleDefSet() {
		return processRoleDefSet;
	}

	public final void setProcessRoleDefSet(Set<ExtProcessRoleDef> processRoleDefSet) {
		this.processRoleDefSet = processRoleDefSet;
	}

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}
}
