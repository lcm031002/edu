package com.edu.erp.post.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.Constants;
import com.edu.erp.post.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.erp.model.Post;

@Controller
public class PostController {
    private static Logger log = Logger.getLogger(PostController.class);

    @Resource(name = "postService")
    private PostService postService;

    @RequestMapping(value = {"/common/postservice"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPost(@RequestBody Post param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean suc = postService.addPost(param);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            resultMap.put(Constants.RespMapKey.ERROR, false);
        }
        return resultMap;
    }

    /**
     * @throws
     * @Title: updatePost
     * @Description: 修改岗位
     */
    @RequestMapping(value = {"/common/postservice"}, method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updatePost(@RequestBody Post param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean suc = postService.updatePost(param);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            resultMap.put(Constants.RespMapKey.ERROR, false);
        }
        return resultMap;
    }

    /**
     * @throws
     * @Title: deletePost
     * @Description: 作废岗位
     */
    @RequestMapping(value = {"/common/postservice"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deletePost(Integer id, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean suc = postService.deletePost(id);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            resultMap.put(Constants.RespMapKey.ERROR, false);
        }
        return resultMap;
    }

    /**
     * @throws
     * @Title: queryPost
     * @Description: 查询岗位id, 名称信息
     */
    @RequestMapping(value = {"/common/postservice"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> param = WebUtils.getParametersStartingWith(request, "p_");
            List<Map<String, Object>> result = postService.queryPost(param);
            resultMap.put(Constants.RespMapKey.DATA, result);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            resultMap.put(Constants.RespMapKey.ERROR, false);
        }
        return resultMap;
    }


    /**
     * @throws
     * @Title: queryPostTypeName
     * @Description: 查询数据字典获取岗位类型
     */
    @RequestMapping(value = {"/common/dict_type_sub/postservice"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPostTypeName(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> result = postService.queryPostType();
            resultMap.put(Constants.RespMapKey.DATA, result);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
            resultMap.put(Constants.RespMapKey.ERROR, false);
        }
        return resultMap;
    }

}
