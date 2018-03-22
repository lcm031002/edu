package com.ebusiness.hrm.post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ebusiness.hrm.dao.PostLevelDao;
import com.ebusiness.hrm.model.PostLevel;

/**
 * 岗位层级
 * @ClassName: PostLevelServiceImpl
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月14日 下午5:30:27
 *
 */
@Service(value="postLevelService")
public class PostLevelServiceImpl implements PostLevelService {
	
	@Resource(name="postLevelDao")
	private PostLevelDao postLevelDao;

	@Override
	public List<PostLevel> queryPostLevel() throws Exception {
		return postLevelDao.selectList();
	}

	@Override
	public void addPostLevel(PostLevel param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getLevel_code(), "参数[层级名称]不能为空！");
		Assert.hasText(param.getLevel_type(), "参数[类别]不能为空！");
		
		Integer ret = postLevelDao.insert(param);
		if(ret != 1){
			throw new Exception("添加失败");
		}
	}

	@Override
	public void updatePostLevel(PostLevel param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getId(), "参数[ID]不能为空！");
		Assert.hasText(param.getLevel_code(), "参数[层级名称]不能为空！");
		Assert.hasText(param.getLevel_type(), "参数[类别]不能为空！");
		
		Integer ret = postLevelDao.update(param);
		if(ret != 1){
			throw new Exception("更新失败");
		}
	}

}
