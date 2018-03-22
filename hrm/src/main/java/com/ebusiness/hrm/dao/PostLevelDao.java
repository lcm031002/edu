package com.ebusiness.hrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.PostLevel;

/**
 * 岗位层级
 * @ClassName: PostLevelDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午5:24:10
 *
 */
@Repository(value="postLevelDao")
public interface PostLevelDao {

	public List<PostLevel> selectList();
	
	Integer insert(PostLevel param);
	
	Integer update(PostLevel param);
	
}
