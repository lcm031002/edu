package com.ebusiness.hrm.post;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ebusiness.hrm.dao.PostRankDao;
import com.ebusiness.hrm.model.PostRank;
import com.ebusiness.hrm.model.PostRankLevelRel;
import com.klxx.common.util.PkUtil;

/**
 * 岗位职级
 * @ClassName: PostRankServiceImpl
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月15日 下午3:09:21
 *
 */
@Service(value="postRankService")
public class PostRankServiceImpl implements PostRankService {
	
	@Resource(name="postRankDao")
	private PostRankDao postRankDao;

	@Override
	public List<PostRank> queryPostRank() throws Exception {
		return postRankDao.selectList();
	}

	@Override
	public List<PostRank> querySimplePostRank() throws Exception {
		return postRankDao.selectSimpleList();
	}

	@Override
	public void addPostRank(PostRank param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getRank_code(), "参数[职级名称]不能为空！");
		Assert.hasText(param.getLevel_ids(), "参数[层级]不能为空！");
		
		Integer ret = postRankDao.insert(param);
		if(ret != 1){
			throw new Exception("添加失败");
		}
		
		List<PostRankLevelRel> relList = genRankLevelRels(param.getId(), param.getLevel_ids());
		postRankDao.insertRankLevelRel(relList);
	}
	
	@Override
	public void updatePostRank(PostRank param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getId(), "参数[ID]不能为空！");
		Assert.hasText(param.getRank_code(), "参数[职级名称]不能为空！");
		Assert.hasText(param.getLevel_ids(), "参数[层级]不能为空！");

		Integer ret = postRankDao.update(param);
		if(ret != 1){
			throw new Exception("更新失败");
		}
		
		// 先删除旧的关系
		postRankDao.deleteRelByRankId(param.getId());
		// 重新插入关系
		List<PostRankLevelRel> relList = genRankLevelRels(param.getId(), param.getLevel_ids());
		postRankDao.insertRankLevelRel(relList);
	}

	/**
	 * 生成 职级 与 层级 的关系
	 * @param rankId
	 * @param levelIdsStr
	 * @return
	 */
	private List<PostRankLevelRel> genRankLevelRels(String rankId, String levelIdsStr) {
		String[] levelIds = levelIdsStr.split(",");
		List<PostRankLevelRel> list  = new ArrayList<>();
		PostRankLevelRel rel = null;
		for(String levelId : levelIds) {
			rel = new PostRankLevelRel();
			rel.setId(PkUtil.getUuid());
			rel.setRank_id(rankId);
			rel.setLevel_id(levelId);
			
			list.add(rel);
		}
		
		return list;
	}
}
