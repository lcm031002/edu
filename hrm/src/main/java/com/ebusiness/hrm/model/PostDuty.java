package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 岗位档案
 * @ClassName: PostDuty
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午4:10:10
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PostDuty implements Serializable {

	private static final long serialVersionUID = -5910682181939797376L;

	private String id;
	
	private String duty_name;	//职务名称
	
//	private Integer post_id;	//岗位id
//	
//	private String rank_id;		//职级id
	
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
	// 新增用户名
	private String create_user_name;
	// 修改用户名
	private String update_user_name;
	
	private String post_name;	//岗位名称
	
	private String rank_code;	//职级编码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
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

	public String getPost_name() {
		return post_name;
	}

	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}

	public String getRank_code() {
		return rank_code;
	}

	public void setRank_code(String rank_code) {
		this.rank_code = rank_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
