package com.edu.report.api;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.common.util.DateUtil;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TTaskTableResult;

public abstract class BaseReportTask implements IReportTask {
	private String tableName;
	private String tableKey;
	private String tableType;

	// 取数区间
	protected Long maxOperateNo = 19000101L;
	protected Long minOperateNo = 19000101L;

	@Override
	public abstract void run(String taskId,String taskFlow, Long pageSize, String startId)
			throws Exception;

	protected void calculateOperateNo(String taskId, String taskFlow, Long pageSize,
			String startId) throws Exception {
		TaskTableResultService taskTableResultService = (TaskTableResultService) ApplicationContextUtil
				.getBean("taskTableResultService");
		// 如果该批次已经执行过，则查询当前批次的运行记录信息，进行重新计算
		TTaskTableResult taskTableResult = taskTableResultService
				.queryTaskFlowTaskTableResult(tableName, taskFlow);

		// 如果当前批次没有执行过，则需要从上一次的最大id+1开始的一页记录进行计算
		if (taskTableResult == null) {
			Long taskTableLastVal = 0L;
			// 查询上一次的最大的id
			TTaskTableResult taskTableResultMax = taskTableResultService
					.queryTaskTableMaxCurrentVal(tableName);

			if (taskTableResultMax != null) {
				taskTableLastVal = taskTableResultMax.getTaskTableCurrentVal();
			} else {
				// 如果没有查询到上一次运行的记录，即当前为初次执行，需要按照配置的初始运行id开始计算，或者最小的id
				if (StringUtils.isBlank(startId)) {
					taskTableResultMax = taskTableResultService.queryMinId(
							tableName, tableKey);
					if (taskTableResultMax != null) {
						taskTableLastVal = taskTableResultMax
								.getTaskTableLastVal();
					}
				} else {
					taskTableLastVal = Long.parseLong(startId);
				}
			}

			// 当前批次处理范围 处理minOperateNo到maxOperateNo区间内的数据
			maxOperateNo = Long.parseLong(DateUtil.dateToString(DateUtil
					.addDate(DateUtil.stringToDate(
							String.valueOf(taskTableLastVal), "yyyyMMdd"),
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
			taskTableResultCurrent.setTaskTable(tableName);
			taskTableResultCurrent.setTaskId(taskId);
			taskTableResultCurrent.setTaskTableId(tableKey);
			taskTableResultCurrent.setTaskTableLastVal(minOperateNo);
			taskTableResultCurrent.setTaskTableCurrentVal(maxOperateNo);
			taskTableResultCurrent.setTaskTableType(tableType);
			taskTableResultService.saveTaskTableResult(taskTableResultCurrent);

		} else {
			// 当前批次已经记录批次信息的，直接取批次对应的区间
			maxOperateNo = (Long) taskTableResult.getTaskTableCurrentVal();
			minOperateNo = (Long) taskTableResult.getTaskTableLastVal();
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public void setTableInfo(String tableName, String tableKey, String tableType) {
		this.tableName = tableName;
		this.tableKey = tableKey;
		this.tableType = tableType;
	}
}
