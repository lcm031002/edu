package com.edu.erp.dao;

import com.edu.erp.model.TabStudentScoreInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "tabStudentScoreInfoDao")
public interface TabStudentScoreInfoDao {
    Page<Long> selectForPage(Map<String, Object> queryParam) throws  Exception;

    List<TabStudentScoreInfo> selectForList(Map<String, Object> queryParam) throws Exception;

    TabStudentScoreInfo selectById(Long id) throws Exception;

    void save(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void update(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void changeStatus(TabStudentScoreInfo tabStudentScoreInfo) throws Exception;

    void deleteById(Long id) throws Exception;

}
