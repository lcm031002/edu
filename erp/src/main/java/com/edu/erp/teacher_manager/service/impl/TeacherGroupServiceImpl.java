package com.edu.erp.teacher_manager.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.dao.TeacherGroupDao;
import com.edu.erp.model.TeacherGroup;
import com.edu.erp.model.TeacherGroupRef;
import com.edu.erp.model.TeacherLeader;
import com.edu.erp.teacher_manager.service.TeacherGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;

@Service(value = "teacherGroupService")
public class TeacherGroupServiceImpl implements TeacherGroupService {
    @Resource(name = "teacherGroupDao")
    private TeacherGroupDao teacherGroupDao;

    @Override
    public Page<TeacherGroup> selectForPage(Map<String, Object> paramMap) {
        Page<Long> idPage = this.teacherGroupDao.selectForPage(paramMap);
        Page<TeacherGroup> teacherGroupPage = new Page<TeacherGroup>();
        if (idPage != null && !CollectionUtils.isEmpty(idPage.getResult())) {
            List<Long> ids = idPage.getResult();
            StringBuilder idBuilder = new StringBuilder();
            for (Long id : ids) {
                idBuilder.append(",").append(id.longValue());
            }
            paramMap.put("ids", idBuilder.substring(1));
            List<TeacherGroup> teacherGroupList = this.teacherGroupDao.selectForList(paramMap);
            teacherGroupPage.addAll(teacherGroupList);
            teacherGroupPage.setPageNum(idPage.getPageNum());
            teacherGroupPage.setPageSize(idPage.getPageSize());
            teacherGroupPage.setTotal(idPage.getTotal());
            teacherGroupPage.setPages(idPage.getPages());
        } else {
            teacherGroupPage.setPageNum(1);
            teacherGroupPage.setPageSize(10);
            teacherGroupPage.setTotal(0);
            teacherGroupPage.setPages(0);
        }
        return teacherGroupPage;
    }

    @Override
    public List<TeacherGroup> selectForList(Map<String, Object> paramMap) {
        return this.teacherGroupDao.selectForList(paramMap);
    }

    @Override
    public void add(TeacherGroup teacherGroup) {
        this.teacherGroupDao.add(teacherGroup);
        handleTeacherGroup(teacherGroup, false);
    }

    @Override
    public void update(TeacherGroup teacherGroup) {
        this.teacherGroupDao.update(teacherGroup);
        handleTeacherGroup(teacherGroup, true);
    }

    @Override
    public TeacherGroup selectById(Long id) {
        TeacherGroup teacherGroup = this.teacherGroupDao.selectById(id);
        List<TeacherLeader> leaderList = this.teacherGroupDao.selectLeaderByGroupId(id);
        if (!CollectionUtils.isEmpty(leaderList)) {
            teacherGroup.setLeaderList(leaderList);
        }

        List<TeacherGroupRef> teacherList = this.teacherGroupDao.selectTeacherByGroupId(id);
        if (!CollectionUtils.isEmpty(teacherList)) {
            teacherGroup.setTeacherList(teacherList);
        }

        return teacherGroup;
    }

    private void handleTeacherGroup(TeacherGroup teacherGroup, boolean isUpdate) {
        Long teacherGroupId = teacherGroup.getId();

        if (isUpdate) {
            this.teacherGroupDao.removeTeacherLeader(teacherGroupId);
            this.teacherGroupDao.removeTeacher(teacherGroupId);
        }

        List<TeacherLeader> teacherLeaderList = teacherGroup.getLeaderList();
        if (!CollectionUtils.isEmpty(teacherLeaderList)) {
            for (TeacherLeader teacherLeader : teacherLeaderList) {
                teacherLeader.setTeach_group_id(teacherGroup.getId());
            }
            this.teacherGroupDao.addTeacherLeader(teacherLeaderList);
        }

        List<TeacherGroupRef> teacherGroupRefList = teacherGroup.getTeacherList();
        if (!CollectionUtils.isEmpty(teacherGroupRefList)) {
            for (TeacherGroupRef teacherGroupRef : teacherGroupRefList) {
                teacherGroupRef.setTeach_group_id(teacherGroup.getId());
            }
            this.teacherGroupDao.addTeacher(teacherGroupRefList);
        }
    }

}
