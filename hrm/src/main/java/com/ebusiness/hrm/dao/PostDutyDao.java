package com.ebusiness.hrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.PostDuty;

/**
 * 岗位档案
 * @ClassName: PostDutyDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月15日 下午5:40:22
 *
 */
@Repository(value="postDutyDao")
public interface PostDutyDao {

	List<PostDuty> selectList();
	
	List<PostDuty> selectSimpleList();
	
	Integer insert(PostDuty param);
	
	Integer update(PostDuty param);
	
}
