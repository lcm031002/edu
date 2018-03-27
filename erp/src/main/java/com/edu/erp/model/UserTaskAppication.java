/**  
 * @Title: UserApplicationTask.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月12日 下午4:42:43
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

/**
 * @ClassName: UserApplicationTask
 * @Description: 工作流任务情况
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月12日 下午4:42:43
 *
 */
public class UserTaskAppication {
	private Long 	id;
	private String 	application;
	private String 	remark;
	private String 	createtime;
	private String 	currentState;
	private String 	currentStep;
	private Long 	status;
	private Long 	applicationId;
	private String 	updatetime;
	private String 	workurl;
	private String 	currentMan;
	private String 	busiType;
	private String 	busiId;
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getApplication() {
		return application;
	}
	public final void setApplication(String application) {
		this.application = application;
	}
	public final String getRemark() {
		return remark;
	}
	public final void setRemark(String remark) {
		this.remark = remark;
	}
	public final String getCreatetime() {
		return createtime;
	}
	public final void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public final String getCurrentState() {
		return currentState;
	}
	public final void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public final String getCurrentStep() {
		return currentStep;
	}
	public final void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}
	public final Long getApplicationId() {
		return applicationId;
	}
	public final void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public final String getUpdatetime() {
		return updatetime;
	}
	public final void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public final String getWorkurl() {
		return workurl;
	}
	public final void setWorkurl(String workurl) {
		this.workurl = workurl;
	}
	public final String getCurrentMan() {
		return currentMan;
	}
	public final void setCurrentMan(String currentMan) {
		this.currentMan = currentMan;
	}
	public final String getBusiType() {
		return busiType;
	}
	public final void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public final String getBusiId() {
		return busiId;
	}
	public final void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	@Override
	public String toString() {
		return "UserApplicationTask [id=" + id + ", application=" + application
				+ ", remark=" + remark + ", createtime=" + createtime
				+ ", currentState=" + currentState + ", currentStep="
				+ currentStep + ", status=" + status + ", applicationId="
				+ applicationId + ", updatetime=" + updatetime + ", workurl="
				+ workurl + ", currentMan=" + currentMan + ", busiType="
				+ busiType + ", busiId=" + busiId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime * result
				+ ((applicationId == null) ? 0 : applicationId.hashCode());
		result = prime * result + ((busiId == null) ? 0 : busiId.hashCode());
		result = prime * result
				+ ((busiType == null) ? 0 : busiType.hashCode());
		result = prime * result
				+ ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result
				+ ((currentMan == null) ? 0 : currentMan.hashCode());
		result = prime * result
				+ ((currentState == null) ? 0 : currentState.hashCode());
		result = prime * result
				+ ((currentStep == null) ? 0 : currentStep.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((updatetime == null) ? 0 : updatetime.hashCode());
		result = prime * result + ((workurl == null) ? 0 : workurl.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTaskAppication other = (UserTaskAppication) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (busiId == null) {
			if (other.busiId != null)
				return false;
		} else if (!busiId.equals(other.busiId))
			return false;
		if (busiType == null) {
			if (other.busiType != null)
				return false;
		} else if (!busiType.equals(other.busiType))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (currentMan == null) {
			if (other.currentMan != null)
				return false;
		} else if (!currentMan.equals(other.currentMan))
			return false;
		if (currentState == null) {
			if (other.currentState != null)
				return false;
		} else if (!currentState.equals(other.currentState))
			return false;
		if (currentStep == null) {
			if (other.currentStep != null)
				return false;
		} else if (!currentStep.equals(other.currentStep))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updatetime == null) {
			if (other.updatetime != null)
				return false;
		} else if (!updatetime.equals(other.updatetime))
			return false;
		if (workurl == null) {
			if (other.workurl != null)
				return false;
		} else if (!workurl.equals(other.workurl))
			return false;
		return true;
	}
	
	
}
