package com.ebusiness.hrm.model;
/**
 * 
 *@ClassName: TabHrmEventtype 
 * @Description:TODO 人事异动类型列表
 * @author:liyj
 * @time:2016年12月28日 上午11:02:15
 */
public class TabHrmEventtype {

	private static final long serialVersionUID = 1L;
	//人事异动id
	private Long id;
	//类型名称
	private String type_name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
}
