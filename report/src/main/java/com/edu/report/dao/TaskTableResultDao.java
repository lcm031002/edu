package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TTaskTableResult;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TTaskTableResult;

/**
 * @ClassName: TaskTableResultDao
 * @Description: 任务运行依赖表记录
 *
 */
@Repository("taskTableResultDao")
public interface TaskTableResultDao {

    /**
     * 
     * @Title: queryTaskFlowTaskTableResult
     * @Description: 查询任务批次
     * @param queryTaskTableResult
     *            查询任务批次依赖表的数据
     * @return 返回任务批次的表结果数据
     * @throws Exception
     *             设定文件 TTaskTableResult 返回类型
     * 
     */
    TTaskTableResult queryTaskFlowTaskTableResult(TTaskTableResult queryTaskTableResult) throws Exception;

    List<TTaskTableResult> queryTaskTableMaxCurrentVal(TTaskTableResult queryTaskTableResult) throws Exception;

	/**
	 * 
	 * @Title: queryCurrentValTaskFlows
	 * @Description: 查询
	 * @param params
	 * @return 返回当前日期已经计算过的任务
	 * @throws Exception
	 *             设定文件 List<TTaskTableResult> 返回类型
	 * 
	 */
	List<TTaskTableResult> queryCurrentValTaskFlows(Map<String, Object> params)
			throws Exception;

    void saveTaskTableResult(TTaskTableResult taskTableResult) throws Exception;

    void deleteTaskTableResult(TTaskTableResult taskTableResult) throws Exception;
    
    TTaskTableResult queryMinId(TTaskTableResult taskTableResult) throws Exception;
    
    void deleteByTaskFlow(String taskFlow) throws Exception;
    
    /**
     * 查询昨天处理的任务，凌晨再重新处理一次，防止每天0点前处理的数据没有被定时任务执行
     * @param unit 任务执行单位 1-天；2-小时；3-分钟；4-秒；5-毫秒
     * @return 昨天执行的任务
     * @throws Exception
     */
    List<TTaskTableResult> queryYesterdayTaskFlows(Integer unit) throws Exception;
}
