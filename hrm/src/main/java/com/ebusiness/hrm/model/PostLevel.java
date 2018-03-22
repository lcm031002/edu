package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 岗位层级
 * @ClassName: PostLevel
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午3:59:52
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PostLevel implements Serializable {
	
	private static final long serialVersionUID = -5305249212852004204L;

	private String id;
	
	private String level_code;
	
	private String level_type;

	private String remark;
	// 创建用户
	private Long create_user;
	// 创建时间
	private Date create_time;
	// 更新用户
	private Long update_user;
	// 更新时间
	private Date update_time;
	
	private Integer deleted;  //0-未删除 1-已删除
	
	private String level_type_name; //专业通道(P)  PROFESSIONAL, 技术通道(T)  TECHNOLOGY, 管理通道(M)  MANAGEMENT
	// 新增用户名
	private String create_user_name;
	// 修改用户名
	private String update_user_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel_code() {
		return level_code;
	}
	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}
	public String getLevel_type() {
		return level_type;
	}
	public void setLevel_type(String level_type) {
		this.level_type = level_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Long getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(Long update_user) {
		this.update_user = update_user;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public String getLevel_type_name() {
		return level_type_name;
	}
	public void setLevel_type_name(String level_type_name) {
		this.level_type_name = level_type_name;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	
}
