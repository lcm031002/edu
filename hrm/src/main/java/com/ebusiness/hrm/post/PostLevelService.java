package com.ebusiness.hrm.post;

import java.util.List;

import com.ebusiness.hrm.model.PostLevel;

/**
 * 岗位层级
 * @ClassName: PostLevelService
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午5:28:24
 *
 */
public interface PostLevelService {

	List<PostLevel> queryPostLevel() throws Exception;
	
	void addPostLevel(PostLevel param) throws Exception;
	
	void updatePostLevel(PostLevel param) throws Exception;
	
}
