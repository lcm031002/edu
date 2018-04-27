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

    /**
     * @Title: deletePost
     * @Description: 作废岗位
     */
    Integer deletePost(Integer id) throws Exception;

    /**
     * @Title: queryPostId
     * @Description: 查询已存在岗位
     */
    Long queryPostId(String name) throws Exception;

    /**
     * @Title: queryPost
     * @Description: 查询岗位名称信息
     */
    List<Map<String, Object>> queryPost(Map<String, Object> param) throws Exception;


    /**
     * @Title: queryPostType
     * @Description: 查询数据字典获取岗位类型
     */
    List<Map<String, Object>> queryPostType() throws Exception;
}
