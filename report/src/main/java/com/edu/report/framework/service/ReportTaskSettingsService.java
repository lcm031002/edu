package com.edu.report.framework.service;

import java.util.List;
import java.util.Map;

import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.model.TReportTaskSettings;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.model.TReportTaskSettings;

/**
 * @ClassName: ReportTaskSettingsService
 * @Description: 报表任务配置服务
 *
 */
public interface ReportTaskSettingsService {
    /**
     * 
     * @Title: queryReportTaskSettings
     * @Description: 查询当前所有的报表任务配置信息
     * @return 报表任务配置信息查询
     * @throws Exception
     *             设定文件 List<TReportTaskSettings> 返回类型
     * 
     */
    List<TReportTaskSettings> queryReportTaskSettings() throws Exception;
    
    List<TReportTaskSettings> selectForList(Map<String, Object> paramMap) throws Exception;
    
    void addByReportCfg(ReportCfg reportCfg)  throws Exception;
    
    void add(TReportTaskSettings taskSettings) throws Exception;
    
    void update(TReportTaskSettings taskSettings) throws Exception;
    
    void changeStatus(TReportTaskSettings taskSettings) throws Exception;
    
    void runTask(List<TReportTaskSettings> tobeRunTaskList) throws Exception;
    
    void runTask(String taskFlow, String taskId) throws Exception;
}
