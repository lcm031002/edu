package com.edu.report.framework.dao;


import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "tFullClassRateDao")
public interface TFullClassRateDao {

    List<TFullClassRate> queryReport(Map<String,Object> paramMap) throws  Exception;

//    void removeByTaskFlow(Map<String, Object> param) throws Exception;
//
//    void addByTaskFlow(Map<String, Object> param) throws Exception;
    List<TFULLClassRateLast> queryDetail(Map<String,Object> paramMap) throws  Exception;

}
