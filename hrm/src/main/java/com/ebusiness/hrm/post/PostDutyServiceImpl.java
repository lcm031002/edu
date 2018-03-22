package com.ebusiness.hrm.post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ebusiness.hrm.dao.PostDutyDao;
import com.ebusiness.hrm.model.PostDuty;

/**
 * 岗位档案
 * @ClassName: PostDutyServiceImpl
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月16日 上午10:47:00
 *
 */
@Service(value="postDutyService")
public class PostDutyServiceImpl implements PostDutyService {
	
	@Resource(name="postDutyDao")
	private PostDutyDao postDutyDao;

	@Override
	public List<PostDuty> queryPostDuty() throws Exception {
		return postDutyDao.selectList();
	}

	@Override
	public List<PostDuty> querySimplePostDuty() throws Exception {
		return postDutyDao.selectSimpleList();
	}

	@Override
	public void addPostDuty(PostDuty param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getDuty_name(), "参数[岗位名称]不能为空！");
//		Assert.notNull(param.getPost_id(), "参数[对应岗位]不能为空！");
//		Assert.hasText(param.getRank_id(), "参数[对应职级]不能为空！");
		
		Integer ret = postDutyDao.insert(param);
		if(ret != 1){
			throw new Exception("添加失败");
		}
	}

	@Override
	public void updatePostDuty(PostDuty param) throws Exception {
		Assert.notNull(param, "参数不能为空！");
		Assert.hasText(param.getId(), "参数[ID]不能为空！");
		Assert.hasText(param.getDuty_name(), "参数[岗位名称]不能为空！");
//		Assert.notNull(param.getPost_id(), "参数[对应岗位]不能为空！");
//		Assert.hasText(param.getRank_id(), "参数[对应职级]不能为空！");

		Integer ret = postDutyDao.update(param);
		if(ret != 1){
			throw new Exception("更新失败");
		}
	}

}
