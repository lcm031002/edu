package com.edu.erp.jstree;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lyk
 *
 */
public class TreeModel {
	private Integer id;
	private String text;
	private String type;
	private List<TreeModel> children=new ArrayList<TreeModel>();
	private  State state;
	//团队
	private Integer companyId;
	//城市
	private Integer cityId;
	//校区
	private Integer schId;
	//父节点id
	private Integer parentId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getSchId() {
		return schId;
	}
	public void setSchId(Integer schId) {
		this.schId = schId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	

	
	



}
