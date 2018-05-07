/**  
 * @Title: TPerformanceDetailsTask.java
 * @Package com.edu.report.tasks.common
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:42:02
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.tasks.common;

import java.util.Date;

import com.edu.report.api.BaseReportTask;
import com.edu.report.model.TTaskTableResult;
import org.apache.commons.lang.StringUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.common.util.DateUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;
import com.edu.report.web.service.PerformanceDetailsService;

/**
 * @ClassName: TPerformanceDetailsTask
 * @Description: 业绩明细报表任务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月24日 下午4:42:02
 * 
 */
public class TPerformanceDetailsTask extends BaseReportTask {
	private static final String TABLE = "T_Performance_Details";
	private static final String TABLE_KEY = "FINISH_TIME";
	private static final String TABLE_TYPE = "INCREMENT";

	private PerformanceDetailsService performanceDetailsService = (PerformanceDetailsService) ApplicationContextUtil
			.getBean("performanceDetailsService");

	public void calculateOperateNo(String taskId,String taskFlow, Long pageSize,
			String startId) throws Exception {

		TaskTableResultService taskTableResultService = (TaskTableResultService) ApplicationContextUtil
				.getBean("taskTableResultService");
		// 如果该批次已经执行过，则查询当前批次的运行记录信息，进行重新计算
		TTaskTableResult taskTableResult = taskTableResultService
				.queryTaskFlowTaskTableResult(getTableName(), taskFlow);

		// 如果当前批次没有执行过，则需要从上一次的最大id+1开始的一页记录进行计算
		if (taskTableResult == null) {
			Long taskTableLastVal = 0L;
			// 查询上一次的最大的id
			TTaskTableResult taskTableResultMax = taskTableResultService
					.queryTaskTableMaxCurrentVal(getTableName());

			if (taskTableResultMax != null) {
				taskTableLastVal = taskTableResultMax.getTaskTableCurrentVal();
			} else {
				// 如果没有查询到上一次运行的记录，即当前为初次执行，需要按照配置的初始运行id开始计算，或者最小的id
				if (StringUtils.isBlank(startId)) {
					taskTableResultMax = taskTableResultService.queryMinId(
							getTableName(), getTableKey());
					if (taskTableResultMax != null) {
						taskTableLastVal = taskTableResultMax
								.getTaskTableLastVal();
					}
				} else {
					taskTableLastVal = Long.parseLong(startId);
				}
			}
			

			// 获取最大、最小的id
			maxOperateNo = Long
					.parseLong(DateUtil.dateToString(DateUtil.addDate(DateUtil
							.stringToDate(taskTableLastVal + "", "yyyyMMdd"),
							pageSize.intValue()), "yyyyMMdd"));
			minOperateNo = Long.parseLong(DateUtil.dateToString(
					DateUtil.addDate(DateUtil.stringToDate(taskTableLastVal
							+ "", "yyyyMMdd"), 1), "yyyyMMdd"));

			Long currentTime = Long.parseLong(DateUtil.dateToString(new Date(),
					"yyyyMMdd"));
			if (currentTime < maxOperateNo) {
				maxOperateNo = currentTime;
			}
			if (currentTime < minOperateNo) {
				minOperateNo = currentTime;
			}
			
			// 记录当前批次状态情况
			TTaskTableResult taskTableResultCurrent = new TTaskTableResult();
			taskTableResultCurrent.setTaskFlow(taskFlow);
			taskTableResultCurrent.setTaskTable(getTableName());
			taskTableResultCurrent.setTaskTableId(getTableKey());
			taskTableResultCurrent.setTaskTableLastVal(minOperateNo);
			taskTableResultCurrent.setTaskTableCurrentVal(maxOperateNo);
			taskTableResultCurrent.setTaskTableType(getTableType());
			taskTableResultCurrent.setTaskId(taskId);
			taskTableResultService.saveTaskTableResult(taskTableResultCurrent);

		} else {
			// 当前批次已经记录批次信息的，直接取批次对应的最大的id和最小的id
			maxOperateNo = (Long) taskTableResult.getTaskTableCurrentVal();
			minOperateNo = (Long) taskTableResult.getTaskTableLastVal();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.api.IReportTask#run()
	 */
	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId)
			throws Exception {
		setTableInfo(TABLE, TABLE_KEY, TABLE_TYPE);
		calculateOperateNo(taskId,taskFlow, pageSize, startId);
		// 更新报表数据
		performanceDetailsService.updateReportPerformanceDetail(taskFlow,
				minOperateNo, maxOperateNo);

	}

}
