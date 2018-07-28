package com.edu.report.api;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.common.util.DateUtil;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;

/**
 * @ClassName: AbstactDayReportTask
 * @Description: 按天抽取数据的报表任务基类
 * 				1、(一次任务只抽取一天的数据,适用于T-1报表) 
 *              2、为了加快期初数据的抽取，优化算法：期初数据一次抽取30天的数据,不足30天的也一次抽取
 *              3、支持T+0的报表
 *
 */
public abstract class AbstactDailyReportTask implements IReportTask {
	
	/**
	 * 业务处理
	 * @param taskFlow
	 * @param minOperateNo
	 * @param maxOperateNo
	 * @throws Exception
	 */
	protected abstract void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
	
	/**
	 * 报表表名
	 * @return
	 */
	protected abstract String getTableName();

	/**
	 * 抽取关键字
	 * @return
	 */
	protected abstract String getTableKey();

	protected String getTableType() {
    	return "INCREMENT";
    }

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId) throws Exception {
		Long maxOperateNo = 19000101L;
		Long minOperateNo = 19000101L;
		int batchSize = 30; //期初数据抽取批量数
		    
		TaskTableResultService taskTableResultService = (TaskTableResultService) ApplicationContextUtil
                .getBean("taskTableResultService");
        // 如果该批次已经执行过，则查询当前批次的运行记录信息，进行重新计算
        TTaskTableResult taskTableResult = taskTableResultService.queryTaskFlowTaskTableResult(getTableName(), taskFlow);
		
        // 如果当前批次没有执行过，则需要从上一次的最大id+1开始的一页记录进行计算
        if (taskTableResult == null) {
            // 查询上一次的最大的id
            TTaskTableResult taskTableResultMax = taskTableResultService.queryTaskTableMaxCurrentVal(getTableName());

            // T-1天
            Long yesterday = Long.parseLong(DateUtil.dateToString(DateUtil.addDate(new Date(), -1), "yyyyMMdd"));
            // 支持运行到T+0日
            Long today = Long.parseLong(DateUtil.dateToString(new Date(), "yyyyMMdd"));
           
            if (taskTableResultMax != null) {
            	//任务已经执行到了 T天，不需要再执行了(这个逻辑在ReportTaskManager中已经有控制，当天的任务执行重复计算时，应该走重复计算的逻辑)
            	if(taskTableResultMax.getTaskTableCurrentVal() >= today) {
            		throw new Exception(COMPLETED);
            	}
            	// 上一次最后的运行日期
            	minOperateNo = taskTableResultMax.getTaskTableCurrentVal();
            	// 这一次任务运行的第一天
            	minOperateNo = Long.parseLong(DateUtil.dateToString(
                        DateUtil.addDate(DateUtil.stringToDate(String.valueOf(minOperateNo), "yyyyMMdd"), 1),
                        "yyyyMMdd"));
            } else {
                // 如果没有查询到上一次运行的记录，即当前为初次执行，需要按照配置的初始运行id开始计算
                if (StringUtils.isBlank(startId)) {
                	throw new Exception("任务配置的startId不存在！");
                    //return; //cfg.xml 的初始配置有问题
                } else {
                	//第一次执行，从配置文件中的开始日期开始
                	minOperateNo = Long.parseLong(startId);
                	
                	//还没到开始日期，不需要执行
                	if(minOperateNo > today) {
                		throw new Exception(COMPLETED);
                	}
                }
            }
            
            // 期初数据只抽取到昨天，这样今天的数据会独立一个批次，重复执行时，效率较高
            // 默认只抽取一天的数据
            if(minOperateNo >= today) {
            	maxOperateNo = minOperateNo;
            } else { 
            	// 30天（第一天包括在内）后
            	Long thirtyDayAfter = Long.parseLong(DateUtil.dateToString(
            			DateUtil.addDate(DateUtil.stringToDate(String.valueOf(minOperateNo), "yyyyMMdd"), 
            			(batchSize-1) ), "yyyyMMdd"));
            	// 今天之前有超过30天未抽取数据，则一次抽取30天数据
            	if(thirtyDayAfter <= yesterday) {
            		maxOperateNo = thirtyDayAfter;
            	} else { //不足30天的,也一次性抽取
            		maxOperateNo = yesterday;
            	}
            }

            // 更新报表数据
    		doBusiness(taskFlow, minOperateNo, maxOperateNo);
    		
            // 记录当前批次状态情况
            TTaskTableResult taskTableResultCurrent = new TTaskTableResult();
            taskTableResultCurrent.setTaskFlow(taskFlow);
            taskTableResultCurrent.setTaskTable(getTableName());
            taskTableResultCurrent.setTaskId(taskId);
            taskTableResultCurrent.setTaskTableId(getTableKey());
            taskTableResultCurrent.setTaskTableLastVal(minOperateNo);
            taskTableResultCurrent.setTaskTableCurrentVal(maxOperateNo);
            taskTableResultCurrent.setTaskTableType(getTableType());
            taskTableResultService.saveTaskTableResult(taskTableResultCurrent);
        } else {  //重新计算
            // 当前批次已经记录批次信息的，直接取批次对应的区间
        	minOperateNo = (Long) taskTableResult.getTaskTableLastVal();
        	maxOperateNo = (Long) taskTableResult.getTaskTableCurrentVal();
            // 更新报表数据
            doBusiness(taskFlow, minOperateNo, maxOperateNo);
        }
	}
	
}
