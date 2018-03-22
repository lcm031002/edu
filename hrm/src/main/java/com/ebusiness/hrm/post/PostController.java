package com.ebusiness.hrm.post;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.Post;


/**
 * @ClassName: PostController
 * @Description: 职务管理
 * @author WP
 * @date 2016年10月26日 
 * 
 */
@Controller
public class PostController {
	private static Logger log = Logger.getLogger(PostController.class);
	
	@Resource(name="postService")
	private PostService postService;
	
	/**
	* 
	* @Title: queryPostForPage
	* @Description: 查询岗位信息列表
	* @param     设定文件
	* @return result    返回类型
	* @throws
	*/
	/*@RequestMapping(value={"/commonPage/postservice"},method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryPostForPage(HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query post");
		}
		Map<String, Object> result = new HashMap<>();
		try{
			List<Map<String, Object>> list = postService.queryPostForPage();
			result.put("error", false);
			result.put("data", list);
		}catch(Exception e){
			log.error("error found,see:"+e);
			result.put("error", true);
			result.put("msg", "查询岗位信息失败");
		}
		return result;
	}*/
	
	/**
	* 
	* @Title: addPost
	* @Description: 新增岗位
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/common/postservice"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addPost(@RequestBody Post param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to insert post");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = postService.addPost(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("insertPost");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to insert post");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: updatePost
	* @Description: 修改岗位
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/common/postservice"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updatePost(@RequestBody Post param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update post");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = postService.updatePost(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updatePost");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update post");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: deletePost
	* @Description: 作废岗位
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/common/postservice"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deletePost(Integer id,HttpServletRequest request,
	HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete post");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = postService.deletePost(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deletePost");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete post");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: queryPost
	* @Description: 查询岗位id,名称信息
	* @param     设定文件
	* @return result    返回类型
	* @throws
	*/
	@RequestMapping(value={"/common/postservice"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryPost(HttpServletRequest request,HttpServletResponse
			response){
		if(log.isDebugEnabled()){
			log.debug("begin to query postName");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			Map<String, Object> param = WebUtils.getParametersStartingWith(request, "p_");
			List<Map<String, Object>> result = postService.queryPost(param);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query postName");
		}
		return httpReponse;
	} 
	
	
	/**
	 * 
	 * @Title: queryPostTypeName
	 * @Description: 查询数据字典获取岗位类型
	 * @param     设定文件
	 * @return result    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/common/dict_type_sub/postservice"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryPostTypeName(HttpServletRequest request,HttpServletResponse
			response){
		if(log.isDebugEnabled()){
			log.debug("begin to query postTypeName");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> result = postService.queryPostType();
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query postTypeName");
		}
		return httpReponse;
	} 
	
}
