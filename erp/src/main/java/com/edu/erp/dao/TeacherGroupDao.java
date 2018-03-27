package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TeacherGroup;
import com.edu.erp.model.TeacherGroupRef;
import com.edu.erp.model.TeacherLeader;
import com.github.pagehelper.Page;

@Repository(value = "teacherGroupDao")
public interface TeacherGroupDao {
    Page<Long> selectForPage(Map<String, Object> paramMap);

    List<TeacherGroup> selectForList(Map<String, Object> paramMap);

    void add(TeacherGroup teacherGroup);

    void update(TeacherGroup teacherGroup);

    void removeTeacherLeader(Long teacherGroupId);

    void addTeacherLeader(List<TeacherLeader> teacherLeaderList);

    void removeTeacher(Long teacherGroupId);

    void addTeacher(List<TeacherGroupRef> teacherGroupRefList);

    TeacherGroup selectById(Long id);

    List<TeacherLeader> selectLeaderByGroupId(Long id);

    List<TeacherGroupRef> selectTeacherByGroupId(Long id);
}
