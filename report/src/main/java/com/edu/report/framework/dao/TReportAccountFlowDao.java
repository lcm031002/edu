package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportAccountFlow;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TReportAccountFlow;
import com.github.pagehelper.Page;

@Repository(value = "tReportAccountFlowDao")
public interface TReportAccountFlowDao {
    Page<TReportAccountFlow> selectForPage(Map<String, Object> paramMap) throws Exception;
    
    List<TReportAccountFlow> selectForList(Map<String, Object> paramMap) throws Exception;
    
    void removeByTaskFlow(Map<String, Object> paramMap) throws Exception;
    
    void addAccountFlow(Map<String, Object> paramMap) throws Exception;
}
