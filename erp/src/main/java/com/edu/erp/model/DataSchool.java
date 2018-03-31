package com.edu.erp.model;


public class DataSchool extends BaseObject
{
	private static final long serialVersionUID = 1L;

	private String school_name;//学校名称
	
	private String short_school_name;//学校简称
	
	private Integer school_type;//学校类型
	
	private String link_man;//联系人
	
	private String phone;//电话号码
	
	private String description;//描述
	
	private Integer province_id;//省份
	
	private Integer area_id;//地区
	
	private Long org_id;//所属地区
	
	private String address;//学校地址
	
	private String province_name;
	
	private String area_name;
	
	private String org_name; //所属地区
	
	private String school_type_name; 
	
	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getShort_school_name() {
		return short_school_name;
	}

	public void setShort_school_name(String short_school_name) {
		this.short_school_name = short_school_name;
	}

	public Integer getSchool_type() {
		return school_type;
	}

	public void setSchool_type(Integer school_type) {
		this.school_type = school_type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}

	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}

	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getSchool_type_name() {
		return school_type_name;
	}

	public void setSchool_type_name(String school_type_name) {
		this.school_type_name = school_type_name;
	}

	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("学校名称： ");
		buff.append(getSchool_name());
		buff.append("，");
		buff.append("学校简称：");
		buff.append(getShort_school_name());
		buff.append("，");
		buff.append("联系人：");
		buff.append(getLink_man());
		buff.append("，");
		buff.append("联系电话：");
		buff.append(getPhone());
		buff.append("，");
		buff.append("省ID：");
		buff.append(getProvince_id());
		buff.append("，");
		buff.append("市ID：");
		buff.append(getCity_id());
		buff.append("，");
		buff.append("区ID：");
		buff.append(getArea_id());
		buff.append("，");
		buff.append("学校类型：");
		buff.append(getSchool_type());
		buff.append("，");
		buff.append("地址：");
		buff.append(getAddress());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}
}
