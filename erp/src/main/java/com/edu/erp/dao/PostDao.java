package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.Post;
import com.github.pagehelper.Page;

/**
 * @ClassName: PostDao
 * @Description: 岗位管理持久层
 */
@Repository(value = "postDao")
public interface PostDao {

    /**
     * @Title: addPost
     * @Description: 新增岗位
     */
    Integer addPost(Post param) throws Exception;

    /**
     * @Title: updatePost
     * @Description: 修改岗位
     */
    Integer updatePost(Post param) throws Exception;

    Post queryByName(String name) throws Exception;

    /**
     * @Title: deletePost
     * @Description: 作废岗位
     */
    Integer changeStatus(Post post) throws Exception;

    /**
     * @Title: queryPost
     * @Description: 查询岗位名称信息
     */
    List<Post> queryPost(Map<String, Object> param) throws Exception;

}
