package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.Post;
import com.github.pagehelper.Page;

/**
 * @ClassName: PostDao
 * @Description: 岗位管理持久层
 * @author WP
 * @date 2016年10月26日 
 * 
 */
@Repository(value="postDao")
public interface PostDao {

	/**
	 * 
	 * @Title: queryPostForPage
	 * @Description: 查询岗位信息列表
	 * 
	 * @return Page<Post> 返回类型
	 */
	public Page<Post> queryPostForPage(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: addPost
	 * @Description: 新增岗位
	 * 
	 * @return Integer 返回类型
	 */
	Integer addPost(Post param) throws Exception;
	
	/**
	 * 
	 * @Title: updatePost
	 * @Description: 修改岗位
	 * 
	 * @return Integer 返回类型
	 */
	Integer updatePost(Post param) throws Exception;
	
	/**
	 * 
	 * @Title: deletePost
	 * @Description: 作废岗位
	 * 
	 * @return Integer 返回类型
	 */
	Integer deletePost(Integer id) throws Exception;
	
	/**
	 * 
	 * @Title: queryPostId
	 * @Description: 查询已存在岗位
	 * 
	 * @return Long 返回类型
	 */
	Long queryPostId(String name) throws Exception;
	
	/**
	 * 
	 * @Title: queryPost
	 * @Description: 查询岗位名称信息
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryPost(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 
	 * @Title: queryPostType
	 * @Description: 查询数据字典获取岗位类型
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryPostType() throws Exception;
}
