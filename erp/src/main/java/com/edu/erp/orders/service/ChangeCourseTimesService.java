package com.edu.erp.orders.service;

import java.util.Map;

import com.edu.erp.model.TabChangeCourseTimes;

public interface ChangeCourseTimesService {
    void add(Long changeCourseId, String refundCourseTimes, String changeNo) throws Exception;
    
    Integer queryChangeCourseTimes(Map<String, Object> paramMap) throws Exception;
    
    void updateByChangeNo(TabChangeCourseTimes tabChangeCourseTimes) throws Exception;

}
