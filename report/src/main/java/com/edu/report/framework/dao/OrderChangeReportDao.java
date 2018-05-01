package com.edu.report.framework.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TOrderChangeReport;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TOrderChangeReport;

@Repository(value = "orderChangeReportDao")
public interface OrderChangeReportDao {
    
    List<TOrderChangeReport> queryReport(Map<String, Object> paramMap) throws Exception;
    
    void removeByTaskFlow(Map<String, Object> param) throws Exception;
    
    void saveBaoBan(Map<String, Object> param) throws Exception;
    
    void saveTuiFei(Map<String, Object> param) throws Exception;
    
    void saveDonJie(Map<String, Object> param) throws Exception;
    
    void saveZhuanBan(Map<String, Object> param) throws Exception;
}
