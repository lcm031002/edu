package com.ebusiness.hrm.post;

import java.util.List;

import com.ebusiness.hrm.model.PostRank;

/**
 * 岗位职级
 * @ClassName: PostRankService
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月15日 下午3:07:40
 *
 */
public interface PostRankService {

	List<PostRank> queryPostRank() throws Exception;
	
	List<PostRank> querySimplePostRank() throws Exception;
	
	void addPostRank(PostRank param) throws Exception;
	
	void updatePostRank(PostRank param) throws Exception;
	
}
