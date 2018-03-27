package com.edu.erp.dao;

import com.edu.erp.model.TabStudentScore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "tabStudentScoreDao")
public interface TabStudentScoreDao {
    List<TabStudentScore> queryByStuScoreInfoId(Long stuScoreInfoId) throws Exception;

    void save(TabStudentScore tabStudentScore) throws Exception;

    void saveList(List<TabStudentScore> tabStudentScoreList) throws Exception;

    void update(TabStudentScore tabStudentScore) throws Exception;

    void deleteById(Long id) throws Exception;

    void deleteByStuScoreInfoId(Long stuScoreInfoId) throws Exception;
}
