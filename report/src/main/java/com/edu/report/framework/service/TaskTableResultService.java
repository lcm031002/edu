/**  
 * @Title: TaskTableResultService.java
 * @Package com.edu.report.framework.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午5:37:43
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TTaskTableResult;
import com.edu.report.model.TTaskTableResult;

/**
 * @ClassName: TaskTableResultService
 * @Description: 查询任务表信息
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月26日 下午5:37:43
 * 
 */
public interface TaskTableResultService {

    /**
     * 
     * @Title: queryTaskTableResult
     * @Description: 查询任务报表结果
     * @param taskTable
     *            待查询表
     * @param taskFlow
     *            任务批次
     * @return 返回任务查询结果
     * @throws Exception
     *             设定文件 TTaskTableResult 返回类型
     * 
     */
    TTaskTableResult queryTaskFlowTaskTableResult(String taskTable, String taskFlow) throws Exception;

    TTaskTableResult queryTaskTableMaxCurrentVal(String taskTable) throws Exception;

    TTaskTableResult queryMinId(String table, String key) throws Exception;

    void saveTaskTableResult(TTaskTableResult taskTableResult) throws Exception;
    
    /**
     * 执行失败是，根据taskFlow删除已记录的处理结果信息
     * @param taskFlow 批次号
     * @throws Exception
     */
    void deleteByTaskFlow(String taskFlow) throws Exception;
    
    /**
	 * 
	 * @Title: queryCurrentValTaskFlows
	 * @Description: 查询
	 * @param taskId
	 * @return 返回当前日期已经计算过的任务
	 * @throws Exception
	 *             设定文件 List<TTaskTableResult> 返回类型
	 * 
	 */
	List<TTaskTableResult> queryCurrentValTaskFlows(String taskId)
			throws Exception;
	
	/**
     * 查询昨天处理的任务，凌晨再重新处理一次，防止每天0点前处理的数据没有被定时任务执行
     * @return 昨天执行的任务
     * @throws Exception
     */
    List<TTaskTableResult> queryYesterdayTaskFlows() throws Exception;

}
