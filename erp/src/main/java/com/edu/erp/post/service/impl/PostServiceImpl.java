package com.edu.erp.post.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.post.service.PostService;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.PostDao;
import com.edu.erp.model.Post;

import junit.framework.Assert;


/**
 * @ClassName: EmployeeExtServiceImpl
 * @Description: 职务管理服务处理类
 */
@Service(value = "postService")
public class PostServiceImpl implements PostService {

    @Resource(name = "postDao")
    private PostDao postDao;

    //添加岗位
    @Override
    public boolean addPost(Post param) throws Exception {
        Assert.assertNotNull(param.getPost_name());
        String name = param.getPost_name() == null ? "" : param.getPost_name();
        Long queryPostId = postDao.queryPostId(name);
        if (null != queryPostId) {
            throw new Exception("该岗位已存在，请重新命名");
        }
        Integer ret = postDao.addPost(param);
        if (ret < 1) {
            throw new Exception("添加失败");
        }
        return ret == 1;
    }


    //修改岗位
    @Override
    public boolean updatePost(Post param) throws Exception {
        Assert.assertNotNull(param.getPost_name());
        String name = param.getPost_name() == null ? "" : param.getPost_name();
        Long queryPostId = postDao.queryPostId(name);
        if (null != queryPostId && param.getId() != queryPostId) {
            throw new Exception("该岗位已存在，请重新命名");
        }
        Integer ret = postDao.updatePost(param);
        if (ret < 1) {
            throw new Exception("修改失败");
        }
        return ret == 1;
    }

    //作废岗位
    @Override
    public boolean deletePost(Integer id) throws Exception {
        Integer ret = postDao.deletePost(id);
        if (ret < 1) {
            throw new Exception("删除失败");
        }
        return ret == 1;
    }

    //查询岗位名称信息
    @Override
    public List<Map<String, Object>> queryPost(Map<String, Object> param) throws Exception {
        return postDao.queryPost(param);
    }

    //查询数据字典获取岗位类型
    @Override
    public List<Map<String, Object>> queryPostType() throws Exception {
        return postDao.queryPostType();
    }
}
