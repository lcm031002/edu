package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAccountCashier;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TAccountCashier;

@Repository(value = "tReportAccountCashierDao")
public interface TReportAccountCashierDao {
    
    List<TAccountCashier> queryReport(Map<String, Object> paramMap) throws Exception;
    
    void removeByTaskFlow(Map<String, Object> param) throws Exception;
    
    void addByTaskFlow(Map<String, Object> param) throws Exception;
    void addByTaskFlowBatch1(Map<String, Object> param) throws Exception;
    void addByTaskFlowBatch2(Map<String, Object> param) throws Exception;
    void addByTaskFlowBatch3(Map<String, Object> param) throws Exception;
}
