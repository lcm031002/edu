package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabChangeCourse;

@Repository(value = "tabChangeCourseDao")
public interface TabChangeCourseDao {
    void add(TabChangeCourse tabChangeCourse) throws Exception;

    void update(TabChangeCourse tabChangeCourse) throws Exception;
    
    Integer queryChangeCourseCount(String changeNo) throws Exception;
    
    void updateByChangeNo(TabChangeCourse tabChangeCourse) throws Exception;
    
    List<TabChangeCourse> queryByChangeNo(String changeNo) throws Exception;
    
    List<TabChangeCourse> queryChangeCourseInfo(Map<String, Object> paramMap) throws Exception;
}
