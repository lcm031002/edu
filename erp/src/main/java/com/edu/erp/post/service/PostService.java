package com.edu.erp.post.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.Post;

/**
 * @ClassName: PostService
 * @Description: 职务管理
 */
public interface PostService {
    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addPost
     * @Description: 添加岗位
     */
    boolean addPost(Post param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: update
     * @Description: 修改岗位
     */
    boolean updatePost(Post param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: deletePost
     * @Description: 作废岗位
     */
    boolean changeStatus(Post post) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryPost
     * @Description: 查询岗位名称信息
     */
    List<Post> queryPost(Map<String, Object> param) throws Exception;

}
