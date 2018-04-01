package com.edu.erp.student.service;

import com.edu.erp.model.TabStudentScore;

import java.util.List;

public interface TabStudentScoreService {
    List<TabStudentScore> queryByStuScoreInfoId(Long stuScoreInfoId) throws Exception;

    void save(TabStudentScore tabStudentScore) throws Exception;

    void saveList(List<TabStudentScore> tabStudentScoreList) throws Exception;

    void update(TabStudentScore tabStudentScore) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByStuScoreInfoId(Long stuScoreInfoId) throws Exception;
}
