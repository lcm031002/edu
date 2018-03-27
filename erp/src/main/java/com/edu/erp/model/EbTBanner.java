package com.edu.erp.model;

import java.io.Serializable;
import java.util.Date;


/**
 * banner轮播配置
 * @author yecb
 *
 */
public class EbTBanner implements Serializable{
	
	private static final long serialVersionUID = -4233745955087762808L;
	
	private Long id;
	// banner
	private String title;
	// 开始日期
	private Date startDate;
	// 结束日期
	private Date endDate;
	// 图片链接
	private String imgUrl;
	
	private String oldImgUrl;
	// 链接地址
	private String linkUrl;
	// 排序
	private int sort;
	// 所属团队
	private Long buId;
	// 所属校区
	private Long branchId;
	// 地区
	private Long cityId;
	// 状态
	private int status;
	
	// 创建用户
	private Long createUser;
	// 创建时间
	private Date createTime;
// 更新用户
	private Long updateUser;
	// 更新时间
	private Date updateTime;
	
	// 地区名称
	private String cityName;
	// 团队名称
	private String buName;
	// 校区名称
	private String branchName;
	
	private String createUserName;
	
	 public static enum StatusEnum{
			VALID(1, "启用"),
			INVALID(1, "停用"),
			DEL(0, "删除");
			private Integer code;
			private String desc;
			private StatusEnum(Integer code, String desc){
				this.code = code;
				this.desc = desc;
			}
			public Integer getCode() {
				return code;
			}
			public void setCode(Integer code) {
				this.code = code;
			}
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			public static String getDesc(Integer code){
				StatusEnum[] vals=StatusEnum.values();
				for(StatusEnum v : vals){
					if(v.getCode()==code){
						return v.getDesc();
					}
				}
				return "";
			}
		}
	
	public String getOldImgUrl() {
		return oldImgUrl;
	}
	public void setOldImgUrl(String oldImgUrl) {
		this.oldImgUrl = oldImgUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Long getBuId() {
		return buId;
	}
	public void setBuId(Long buId) {
		this.buId = buId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBuName() {
		return buName;
	}
	public void setBuName(String buName) {
		this.buName = buName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	
}
