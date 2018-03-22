package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 岗位职级与层级关系
 * @ClassName: PostRankLevelRel
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午4:05:39
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PostRankLevelRel implements Serializable {

	private static final long serialVersionUID = -5962254668777542218L;

	private String id;
	
	private String rank_id;		//职级id
	
	private String level_id;	//层次id
	// 创建用户
	private Long create_user;
	// 创建时间
	private Date create_time;
	// 更新用户
	private Long update_user;
	// 更新时间
	private Date update_time;
	
	private Integer deleted;  //0-未删除 1-已删除

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRank_id() {
		return rank_id;
	}

	public void setRank_id(String rank_id) {
		this.rank_id = rank_id;
	}

	public String getLevel_id() {
		return level_id;
	}

	public void setLevel_id(String level_id) {
		this.level_id = level_id;
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
	
}
