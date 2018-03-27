package com.edu.erp.workflow.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.pvm.internal.task.SwimlaneImpl;
import org.jbpm.pvm.internal.util.Priority;

import com.edu.erp.model.BaseObject;
import com.edu.erp.utils.StringUtil;


public class TaskBusiness extends BaseObject {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 2255271434379202791L;

	  protected boolean hasVariables;
	  
	  protected String state;
	  protected String suspendHistoryState;
	  
	  protected boolean isNew;
	  protected String name;
	  protected String description;

	  protected String assignee;
	  protected String formResourceName;
	  protected Date createTime;
	  protected Date duedate;
	  protected Integer progress;
	  protected boolean isSignalling;

	  protected int priority = Priority.NORMAL;

	  protected String taskDefinitionName;

	  protected String executionId;
	  
	  protected String activityName;
	  
	  protected SwimlaneImpl swimlane;


	  protected Long executionDbid;
	  
	  protected Long superTaskDbid;

	  
	  private String processKey;
	  
	  private String processName;
	  
	  private String allAssignee;
	  
	  private String workbenchURL;
	  
	  private String remark;

	  private String employeeName;
  
      private Map<String,Object> extData;
 
    
      private List<MapType> extDataL;
      
	  public void procExtData() {
			if(getExtData() == null) extData = new HashMap<String,Object>();
			for(MapType e:extDataL){
				extData.put(e.getKey_field(), e.getValue_field());
				if("processDefinition.key".equals(e.getKey_field())) setProcessKey(StringUtil.nullToBlank(e.getValue_field()));
				if("processDefinition.name".equals(e.getKey_field())) setProcessName(StringUtil.nullToBlank(e.getValue_field()));
				if("allAssignee".equals(e.getKey_field())) setAllAssignee(StringUtil.nullToBlank(e.getValue_field()));
				if("workbenchURL".equals(e.getKey_field())) setWorkbenchURL(StringUtil.nullToBlank(e.getValue_field()));
				if("remark".equals(e.getKey_field())) setRemark(StringUtil.nullToBlank(e.getValue_field()));
			}
	  }
	  
	  public Map<String, Object> getExtData() {
		  return extData;
	  }
	   
	  
	
		public void setExtData(Map<String, Object> extData) {
			this.extData = extData;
		}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAssignee() {
		return assignee;
	}


	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}


	public String getFormResourceName() {
		return formResourceName;
	}


	public void setFormResourceName(String formResourceName) {
		this.formResourceName = formResourceName;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getDuedate() {
		return duedate;
	}


	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}


	public Integer getProgress() {
		return progress;
	}


	public void setProgress(Integer progress) {
		this.progress = progress;
	}


	public boolean isSignalling() {
		return isSignalling;
	}


	public void setSignalling(boolean isSignalling) {
		this.isSignalling = isSignalling;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}


	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}


	public String getExecutionId() {
		return executionId;
	}


	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}


	public String getActivityName() {
		return activityName;
	}


	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public SwimlaneImpl getSwimlane() {
		return swimlane;
	}


	public void setSwimlane(SwimlaneImpl swimlane) {
		this.swimlane = swimlane;
	}


	public Long getExecutionDbid() {
		return executionDbid;
	}

	public void setExecutionDbid(Long executionDbid) {
		this.executionDbid = executionDbid;
	}


	public Long getSuperTaskDbid() {
		return superTaskDbid;
	}


	public void setSuperTaskDbid(Long superTaskDbid) {
		this.superTaskDbid = superTaskDbid;
	}


	public boolean isHasVariables() {
		return hasVariables;
	}


	public void setHasVariables(boolean hasVariables) {
		this.hasVariables = hasVariables;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getSuspendHistoryState() {
		return suspendHistoryState;
	}


	public void setSuspendHistoryState(String suspendHistoryState) {
		this.suspendHistoryState = suspendHistoryState;
	}


	public List<MapType> getExtDataL() {
		return extDataL;
	}


	public void setExtDataL(List<MapType> extDataL) {
		this.extDataL = extDataL;
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

	public String getAllAssignee() {
		return allAssignee;
	}

	public void setAllAssignee(String allAssignee) {
		this.allAssignee = allAssignee;
	}

	public String getWorkbenchURL() {
		return workbenchURL;
	}

	public void setWorkbenchURL(String workbenchURL) {
		this.workbenchURL = workbenchURL;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmployeeName() {return employeeName;}

	public void setEmployeeName(String employeeName) { this.employeeName = employeeName;}
}
