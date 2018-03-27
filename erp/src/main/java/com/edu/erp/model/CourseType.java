package com.edu.erp.model;


/**
 * 课程商品类型
 * 
 * @author wCong
 *
 */
public class CourseType extends BaseObject{
	private static final long serialVersionUID = 37600796495718312L;

	// 类型名称
	private String type_name;
	// 所属校区
	private Long org_id;
	
	private String org_name;
	
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("类型名称： ");
		buff.append(getType_name());
		buff.append("，");
		buff.append("所属校区ID：");
		buff.append(getOrg_id());
		return buff.toString();
	}
}	
