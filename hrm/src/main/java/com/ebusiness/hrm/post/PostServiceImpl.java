package com.ebusiness.hrm.post;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.PostDao;
import com.ebusiness.hrm.model.Post;

import junit.framework.Assert;



/**
 * @ClassName: EmployeeExtServiceImpl
 * @Description: 职务管理服务处理类
 * @author WP
 * @date 2016年10月26日 
 * 
 */
@Service(value="postService")
public class PostServiceImpl implements PostService{

	//private static final Logger log = Logger.getLogger(PostServiceImpl.class);
	
	@Resource(name="postDao")
	private PostDao postDao;
	
	//查询岗位信息列表
	/*@Override
	public PageInfo<Post> queryPostForPage(Long BuId,Long BranchId,String name,PageParam pageParam) throws Exception{
		Page<Post> list = null;
		PageInfo<Post> page = new PageInfo<Post>();
		
		try{
			Map<String, Object> param = new HashMap<>();
			param.put("BuId", BuId);
			param.put("BranchId", BranchId);
			param.put("name", name);
			PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());
			
			list = postDao.queryPostForPage(param);
			log.error("list:"+list);
			page = new PageInfo<Post>(list);
		}catch(Exception e){
			log.error("error found,see:",e);
		}
		return page;
	}*/
	
	//添加岗位
	@Override
	public boolean addPost(Post param) throws Exception{
		Assert.assertNotNull(param.getPost_name());
		String name = param.getPost_name()==null?"":param.getPost_name();
		Long queryPostId=postDao.queryPostId(name);
		if(null!=queryPostId){
			throw new Exception("该岗位已存在，请重新命名");
		}
		Integer ret = postDao.addPost(param);
		if(ret<1){
			throw new Exception("添加失败");
		}
		return ret==1;
	}
	
	
	//修改岗位
	@Override
	public boolean updatePost(Post param) throws Exception{
		Assert.assertNotNull(param.getPost_name());
		String name = param.getPost_name()==null?"":param.getPost_name();
		Long queryPostId=postDao.queryPostId(name);
		if(null!=queryPostId && param.getId() != queryPostId){
			throw new Exception("该岗位已存在，请重新命名");
		}
		Integer ret = postDao.updatePost(param);
		if(ret<1){
			throw new Exception("修改失败");
		}
		return ret==1;
	}
	
	//作废岗位
	@Override
	public boolean deletePost(Integer id) throws Exception{
		Integer ret = postDao.deletePost(id);
		if(ret<1){
			throw new Exception("删除失败");
		}
		return ret==1;
	}
	
	//查询岗位名称信息
	@Override
	public List<Map<String, Object>> queryPost(Map<String, Object> param) throws Exception{
		return postDao.queryPost(param);
	}
	
	//查询数据字典获取岗位类型
	@Override
	public List<Map<String, Object>> queryPostType() throws Exception{
		return postDao.queryPostType();
	}
}
