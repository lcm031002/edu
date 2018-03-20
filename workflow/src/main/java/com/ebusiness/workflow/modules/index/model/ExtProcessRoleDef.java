/**  
 * @Title: ExtProcessroleDef.java
 * @Package com.ebusiness.workflow.modules.index.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年11月28日 下午2:46:04
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName: ExtProcessroleDef
 * @Description: 流程定义实体类
 * @author zhuliyong zly@entstudy.com
 * @date 2014年11月28日 下午2:46:04
 * 
 */
public class ExtProcessRoleDef implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -5230188448024356515L;
	private long id;
	private String processKey;
	private String processTask;
	private String remark;
	private Set<ExtBusinessRoleMapping> mappings;

	public String getProcessKey() {
		return processKey;
	}

	public String getProcessTask() {
		return processTask;
	}

	public String getRemark() {
		return remark;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public void setProcessTask(String processTask) {
		this.processTask = processTask;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<ExtBusinessRoleMapping> getMappings() {
		return mappings;
	}

	public void setMappings(Set<ExtBusinessRoleMapping> mappings) {
		this.mappings = mappings;
	}

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}
	
}
