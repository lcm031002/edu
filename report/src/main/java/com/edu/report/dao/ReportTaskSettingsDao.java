/**  
 * @Title: ReportTaskSettingsDao.java
 * @Package com.edu.report.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午5:39:44
 * @version KLXX ERPV4.0  
 */
package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.report.model.TReportTaskSettings;

/**
 * @ClassName: ReportTaskSettingsDao
 * @Description: 报表任务设置
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午5:39:44
 * 
 */
@Repository("reportTaskSettingsDao")
public interface ReportTaskSettingsDao {
    List<TReportTaskSettings> queryReportTaskSettings(Map<String, Object> paramMap) throws Exception;
    List<String> queryReportTaskIds(Map<String, Object> paramMap) throws Exception;

    void addByReportCfg(List<TReportTaskSettings> taskSettingList) throws Exception;
    
    void updateByReportCfg(List<TReportTaskSettings> taskSettingList) throws Exception;
    
    void add(TReportTaskSettings taskSettings) throws Exception;
    
    void update(TReportTaskSettings taskSettings) throws Exception;
    
    void changeStatus(TReportTaskSettings taskSettings) throws Exception;
    
    Integer selectCountByTaskId(TReportTaskSettings taskSettings) throws Exception;
    
    TReportTaskSettings queryByTaskId(String taskId) throws Exception;
}
