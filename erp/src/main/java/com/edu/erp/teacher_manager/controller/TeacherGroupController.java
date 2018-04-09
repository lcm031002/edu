package com.edu.erp.teacher_manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.edu.common.constants.Constants;
import com.edu.erp.model.BaseObject;
import com.edu.erp.model.TeacherGroup;
import com.edu.erp.teacher_manager.service.TeacherGroupService;
import com.edu.erp.util.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;

@Controller
@RequestMapping(value = {"/teacherGroup"})
public class TeacherGroupController extends BaseController {
    @Resource(name = "teacherGroupService")
    private TeacherGroupService teacherGroupService;

    @ResponseBody
    @RequestMapping(value = {"/toManage"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> toManage(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            Long id = genLongParameter("id", request);
            TeacherGroup teacherGroup = this.teacherGroupService.selectById(id);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, teacherGroup);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> selectForPage(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, true);
            Page<TeacherGroup> page = this.teacherGroupService.selectForPage(paramMap);
            setRespDataForPage(request, page.getResult(), resultMap);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/list"}, headers = {"Accept=application/json"}, method = RequestMethod.GET)
    public Map<String, Object> selectForList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, false);
            List<TeacherGroup> teacherGroupList = this.teacherGroupService.selectForList(paramMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, teacherGroupList);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody TeacherGroup teacherGroup, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            setDefaultValue(request, teacherGroup, false);
            this.teacherGroupService.add(teacherGroup);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, teacherGroup);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestBody TeacherGroup teacherGroup, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            setDefaultValue(request, teacherGroup, true);
            this.teacherGroupService.update(teacherGroup);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, teacherGroup);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = {"/service"}, headers = {"Accept=application/json"}, method = RequestMethod.DELETE)
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Long id = genLongParameter("id", request);
            TeacherGroup teacherGroup = new TeacherGroup();
            teacherGroup.setId(id);
            teacherGroup.setStatus(BaseObject.StatusEnum.INVALID.getCode());
            setDefaultValue(request, teacherGroup, true);
            this.teacherGroupService.update(teacherGroup);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }

        return resultMap;
    }
}
