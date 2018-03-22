package com.ebusiness.hrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.PostRank;
import com.ebusiness.hrm.model.PostRankLevelRel;

/**
 * 岗位职级
 * @ClassName: PostRankDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月15日 下午3:04:43
 *
 */
@Repository(value="postRankDao")
public interface PostRankDao {

	List<PostRank> selectList();
	
	List<PostRank> selectSimpleList();
	
	Integer insert(PostRank param);
	
	Integer update(PostRank param);
	
	Integer deleteRelByRankId(String rankId);
	
	Integer insertRankLevelRel(List<PostRankLevelRel> list);
	
}
