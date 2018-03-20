/**  
 * @Title: ExtTask.java
 * @Package com.ebusiness.workflow.modules.index.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月15日 下午3:16:20
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: ExtTask
 * @Description: 与JBPM4_TASK对应的快速查询服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月15日 下午3:16:20
 *
 */
public class ExtTask  implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 5968337066443333655L;
	private Long dbid_;
	private String class_;
	private Long dbversion_;
	private String name_;
	private String descr_;
	private String state_;
	private String susphiststate_;
	private String assignee_;
	private String form_;
	private String priority_;
	private Date create_;
	private Date duedate_;
	private Long progress_;
	private Long signalling_;
	private String execution_id_;
	private String activity_name_;
	private Long hasvars_;
	private Long supertask_;
	private Long execution_;
	private Long procinst_;
	private Long swimlane_;
	private String taskdefname_;
	private Set<ExtTaskVariable> variables;
	public final Long getDbid_() {
		return dbid_;
	}
	public final void setDbid_(Long dbid_) {
		this.dbid_ = dbid_;
	}
	public final String getClass_() {
		return class_;
	}
	public final void setClass_(String class_) {
		this.class_ = class_;
	}
	public final Long getDbversion_() {
		return dbversion_;
	}
	public final void setDbversion_(Long dbversion_) {
		this.dbversion_ = dbversion_;
	}
	public final String getName_() {
		return name_;
	}
	public final void setName_(String name_) {
		this.name_ = name_;
	}
	public final String getDescr_() {
		return descr_;
	}
	public final void setDescr_(String descr_) {
		this.descr_ = descr_;
	}
	public final String getState_() {
		return state_;
	}
	public final void setState_(String state_) {
		this.state_ = state_;
	}
	public final String getSusphiststate_() {
		return susphiststate_;
	}
	public final void setSusphiststate_(String susphiststate_) {
		this.susphiststate_ = susphiststate_;
	}
	public final String getAssignee_() {
		return assignee_;
	}
	public final void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}
	public final String getForm_() {
		return form_;
	}
	public final void setForm_(String form_) {
		this.form_ = form_;
	}
	public final String getPriority_() {
		return priority_;
	}
	public final void setPriority_(String priority_) {
		this.priority_ = priority_;
	}
	public final Date getCreate_() {
		return create_;
	}
	public final void setCreate_(Date create_) {
		this.create_ = create_;
	}
	public final Date getDuedate_() {
		return duedate_;
	}
	public final void setDuedate_(Date duedate_) {
		this.duedate_ = duedate_;
	}
	public final Long getProgress_() {
		return progress_;
	}
	public final void setProgress_(Long progress_) {
		this.progress_ = progress_;
	}
	public final Long getSignalling_() {
		return signalling_;
	}
	public final void setSignalling_(Long signalling_) {
		this.signalling_ = signalling_;
	}
	public final String getExecution_id_() {
		return execution_id_;
	}
	public final void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}
	public final String getActivity_name_() {
		return activity_name_;
	}
	public final void setActivity_name_(String activity_name_) {
		this.activity_name_ = activity_name_;
	}
	public final Long getHasvars_() {
		return hasvars_;
	}
	public final void setHasvars_(Long hasvars_) {
		this.hasvars_ = hasvars_;
	}
	public final Long getSupertask_() {
		return supertask_;
	}
	public final void setSupertask_(Long supertask_) {
		this.supertask_ = supertask_;
	}
	public final Long getExecution_() {
		return execution_;
	}
	public final void setExecution_(Long execution_) {
		this.execution_ = execution_;
	}
	public final Long getProcinst_() {
		return procinst_;
	}
	public final void setProcinst_(Long procinst_) {
		this.procinst_ = procinst_;
	}
	public final Long getSwimlane_() {
		return swimlane_;
	}
	public final void setSwimlane_(Long swimlane_) {
		this.swimlane_ = swimlane_;
	}
	public final String getTaskdefname_() {
		return taskdefname_;
	}
	public final void setTaskdefname_(String taskdefname_) {
		this.taskdefname_ = taskdefname_;
	}
	public final Set<ExtTaskVariable> getVariables() {
		return variables;
	}
	public final void setVariables(Set<ExtTaskVariable> variables) {
		this.variables = variables;
	}
}
