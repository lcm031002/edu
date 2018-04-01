package com.edu.erp.orders.service;

import java.util.List;

import com.edu.erp.model.TabChangeCourse;

public interface ChangeCourseService {
    void add(TabChangeCourse tabChangeCourse) throws Exception;
    
    void update(TabChangeCourse tabChangeCourse) throws Exception;
    
    void updateByChangeNo(TabChangeCourse tabChangeCourse) throws Exception;
    
    List<TabChangeCourse> queryByChangeNo(String changeNo) throws Exception;
}
