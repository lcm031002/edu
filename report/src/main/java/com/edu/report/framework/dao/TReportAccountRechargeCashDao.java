package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAccountRechargeCash;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TAccountRechargeCash;

@Repository(value = "tReportAccountRechargeCashDao")
public interface TReportAccountRechargeCashDao {
    
    List<TAccountRechargeCash> queryReport(Map<String, Object> paramMap) throws Exception;
    
    void removeByTaskFlow(Map<String, Object> param) throws Exception;
    
    void addByTaskFlow(Map<String, Object> param) throws Exception;
}
