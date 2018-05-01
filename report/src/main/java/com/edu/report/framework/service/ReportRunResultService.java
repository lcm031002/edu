/**  
 * @Title: ReportRunResultService.java
 * @Package com.edu.report.framework.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午6:53:34
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TReportRunResult;
import com.github.pagehelper.Page;

/**
 * @ClassName: ReportRunResultService
 * @Description: 报表运行状态
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午6:53:34
 * 
 */
public interface ReportRunResultService {
    /**
     * 
     * @Title: queryReportRunResult
     * @Description: 查询指定任务的运行状态结果
     * @param taskId
     *            任务id
     * @return 报表运行结果
     * @throws Exception
     *             设定文件 List<TReportRunResult> 返回类型
     * 
     */
    List<TReportRunResult> queryReportRunResult(String taskId) throws Exception;

    /**
     * 
     * @Title: queryMaxTaskFlow
     * @Description: 查询任务的最大执行批次
     * @param taskId
     *            任务id
     * @return
     * @throws Exception
     *             设定文件 List<TReportRunResult> 返回类型
     * 
     */
    TReportRunResult queryMaxTaskFlowResult(String taskId) throws Exception;

    /**
     * 
     * @Title: saveReportRunResult
     * @Description: 保存报表执行的结果信息
     * @param reportRunResult
     * @throws Exception
     *             设定文件 void 返回类型
     * 
     */
    void saveReportRunResult(TReportRunResult reportRunResult) throws Exception;

    Page<TReportRunResult> selectForPage(Map<String, Object> queryParam) throws Exception;

    /**
     * 
     * @Title: queryReportRunResult
     * @Description: 查询指定任务的运行状态结果
     * @param taskId
     *            任务id
     * @param taskFlow
     *            任务运行批次
     * @return 报表运行结果
     * @throws Exception
     *             设定文件 List<TReportRunResult> 返回类型
     * 
     */
    TReportRunResult queryReportRunResult(String taskId, String taskFlow) throws Exception;
}
