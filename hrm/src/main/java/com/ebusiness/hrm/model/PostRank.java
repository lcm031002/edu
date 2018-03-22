package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 岗位职级
 * @ClassName: PostRank
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午4:06:38
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PostRank implements Serializable {

	private static final long serialVersionUID = -2645665866297051742L;

	private String id;
	
	private String rank_code;
	
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
	
	private String level_ids;	//层级Id
	
	private String level_codes;	//层级编码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLevel_ids() {
		return level_ids;
	}

	public void setLevel_ids(String level_ids) {
		this.level_ids = level_ids;
	}

	public String getLevel_codes() {
		return level_codes;
	}

	public void setLevel_codes(String level_codes) {
		this.level_codes = level_codes;
	}

}
