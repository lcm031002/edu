package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabChangeCourseTimes;

@Repository(value = "tabChangeCourseTimesDao")
public interface TabChangeCourseTimesDao {
    void add(TabChangeCourseTimes tabChangeCourseTimes) throws Exception;

    void addList(@Param("tabChangeCourseTimesList") List<TabChangeCourseTimes> tabChangeCourseTimesList) throws Exception;

    Integer queryChangeCourseTimes(Map<String, Object> paramMap) throws Exception;
    
    void updateByChangeNo(TabChangeCourseTimes tabChangeCourseTimes) throws Exception;
}
