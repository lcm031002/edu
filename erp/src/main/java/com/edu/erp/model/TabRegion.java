package com.edu.erp.model;


/***
 * Description ： 全国省市地区信息表
 * 
 * Author ：junli.zhang
 * 
 * Date : 2014-08-29
 */
public class TabRegion extends BaseObject {

	private static final long serialVersionUID = 3524148239473637640L;
	
	private Long rid;				// 主键
	private Long parent_id;			// 上级ID
	private String rname;			// 名称
	private Long display_order;		// 排序
	private String catagery;		// 编号
	private String postal_code;
	
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Long getDisplay_order() {
		return display_order;
	}
	public void setDisplay_order(Long display_order) {
		this.display_order = display_order;
	}
	public String getCatagery() {
		return catagery;
	}
	public void setCatagery(String catagery) {
		this.catagery = catagery;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
}
