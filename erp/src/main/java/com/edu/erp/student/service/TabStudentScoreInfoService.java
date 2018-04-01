package com.edu.erp.student.service;

import com.edu.erp.model.TabStudentScoreInfo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TabStudentScoreInfoService {
    Page<TabStudentScoreInfo> selectForPage(Map<String, Object> queryParam) throws  Exception;

    List<TabStudentScoreInfo> selectForList(Map<String, Object> queryParam) throws Exception;

    TabStudentScoreInfo selectById(Long id) throws Exception;

    TabStudentScoreInfo selectOne(Map<String, Object> queryParam) throws Exception;

    void save(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void update(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void changeStatus(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void deleteById(Long id) throws Exception;
}
