package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable{

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	   //角色名称
	   private String roleName;
	   //状态
	   private Integer status;
	   //备注
	   private String remark;
	   //模块
	   private String mod;
	   
	   private String searchInfo;
	   
	   private Long accountId;
	   
	   private Date create_time;
	   private Long createId;
	   private Date update_time;
	   private Long updateId;
	   
	   private String create_user;
	   private String update_user;
	   //备注更新
	   private String updateRemark;
	   
	 //分页对象
	 		private PageParam pageParam;
	 		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMod() {
		return mod;
	}
	public void setMod(String mod) {
		this.mod = mod;
	}
	
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public String getUpdateRemark() {
		return updateRemark;
	}
	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}
	public PageParam getPageParam() {
		return pageParam;
	}
	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}
	public String getSearchInfo() {
		return searchInfo;
	}
	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	   
	
}
