package com.ebusiness.hrm.post;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.Post;

/**
 * @ClassName: PostService
 * @Description: 职务管理
 * @author WP
 * @date 2016年10月26日 
 * 
 */
public interface PostService {

	/**
	 * 
	 * @Title: queryPostForPage
	 * @Description: 查询岗位信息列表
	 * 
	 * @return PageInfo<Post> 返回类型
	 */
	//List<Map<String, Object>> queryPostForPage() throws Exception;
	
	/**
	 * 
	 * @Title: addPost
	 * @Description: 添加岗位
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addPost(Post param) throws Exception;
	
	/**
	 * 
	 * @Title: update
	 * @Description: 修改岗位
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updatePost(Post param) throws Exception;
	
	/**
	 * 
	 * @Title: deletePost
	 * @Description: 作废岗位
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deletePost(Integer id) throws Exception;
	
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
