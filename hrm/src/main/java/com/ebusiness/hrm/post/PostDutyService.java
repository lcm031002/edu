package com.ebusiness.hrm.post;

import java.util.List;

import com.ebusiness.hrm.model.PostDuty;

/**
 * 岗位档案
 * @ClassName: PostDutyService
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月16日 上午10:45:13
 *
 */
public interface PostDutyService {

	List<PostDuty> queryPostDuty() throws Exception;
	
	List<PostDuty> querySimplePostDuty() throws Exception;
	
	void addPostDuty(PostDuty param) throws Exception;
	
	void updatePostDuty(PostDuty param) throws Exception;
	
}
