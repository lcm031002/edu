package com.ebusiness.hrm.model;

/**
 * 
 *@ClassName: TabHrmChangeeventParam 
 * @Description:TODO 人事异动参数表
 * @author:liyj
 * @time:2017年1月11日 上午9:52:00
 */
/**
 *@ClassName: TabHrmChangeeventParam 
 * @Description:TODO
 * @author:Entstudy
 * @time:2017年1月11日 上午9:55:37
 */
public class TabHrmChangeeventParam {

	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//申请id
	private Long applyId;
	//参数名称
	private String parameterName;
	//参数Key
	private String parameterKey;
	//参数值
	private String parameterValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterKey() {
		return parameterKey;
	}
	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
}
