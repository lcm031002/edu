package com.edu.report.framework.tasks.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.edu.report.framework.ReportTaskConfig;
import com.edu.report.framework.Unit;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.framework.cfg.Task;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TReportTaskSettings;
import com.edu.report.model.TTaskTableResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.common.util.DateUtil;
import com.edu.report.api.IReportTask;
import com.edu.report.framework.ReportTaskConfig;
import com.edu.report.framework.Unit;
import com.edu.report.framework.cfg.ReportCfg;
import com.edu.report.framework.cfg.Task;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.framework.service.ReportTaskSettingsService;
import com.edu.report.framework.service.TaskTableResultService;
import com.edu.report.model.TReportRunResult;
import com.edu.report.model.TReportTaskSettings;
import com.edu.report.model.TTaskTableResult;
import com.edu.report.util.TaskFlowUtils;

/**
 * @ClassName: TaskDispatcherManager
 * @Description: 报表任务调度
 *
 */
public class ReportTaskManager implements Runnable {
	private static final Logger log = Logger.getLogger(ReportTaskManager.class);
	private static final AtomicBoolean LOADED_SETTINGS = new AtomicBoolean(
			false);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (log.isDebugEnabled()) {
			log.debug("begin to check report task.");
		}

		ReportTaskSettingsService reportTaskSettingsService = (ReportTaskSettingsService) ApplicationContextUtil
				.getBean("reportTaskSettingsService");
		if (reportTaskSettingsService == null) {
			return;
		}
		try {
			if (LOADED_SETTINGS.compareAndSet(false, true)) {
				reportTaskSettingsService.addByReportCfg(ReportTaskConfig
						.genInstance().getCfg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOADED_SETTINGS.compareAndSet(true, false);
		}

		try {
			TaskTableResultService taskTableResultService = (TaskTableResultService) ApplicationContextUtil
					.getBean("taskTableResultService");
			List<TReportTaskSettings> tobeRunTask = new ArrayList<TReportTaskSettings>();
			List<Task> tobeRunTask2 = new ArrayList<Task>();
			// 获取数据库的任务配置参数
			List<TReportTaskSettings> reportTaskSettings = reportTaskSettingsService
					.queryReportTaskSettings();
			if (!CollectionUtils.isEmpty(reportTaskSettings)) {
				for (TReportTaskSettings taskSettings : reportTaskSettings) {
					if (isNeedRun(taskSettings)) {
						tobeRunTask.add(taskSettings);
					}
				}
			} else {
				// 报表配置
				ReportCfg reportCfg = ReportTaskConfig.genInstance().getCfg();

				if (reportCfg != null
						&& !CollectionUtils.isEmpty(reportCfg.getTasks())) {

					for (Task task : reportCfg.getTasks()) {
						if (isNeedRun(task)) {
							tobeRunTask2.add(task);
						}
					}
				}
			}

			ReportRunResultService reportRunResultService = (ReportRunResultService) ApplicationContextUtil
					.getBean("reportRunResultService");
			if (!CollectionUtils.isEmpty(tobeRunTask)) {
				String taskFlow = TaskFlowUtils.genNextFlow();
				for (TReportTaskSettings taskSettings : tobeRunTask) {
					Long startTime = System.currentTimeMillis();
					try {
						// 获取待执行的报表任务
						if (StringUtils.isEmpty(taskSettings.getClassImpl())) {
							continue;
						}
						Long pageSize = taskSettings.getPageSize() == null ? 1000L
								: taskSettings.getPageSize();
						String startId = taskSettings.getStartId();
						Object classImpl = Class.forName(
								taskSettings.getClassImpl().trim())
								.newInstance();
						if (classImpl instanceof IReportTask) {
							// 执行报表任务
							IReportTask reportTask = (IReportTask) classImpl;

							// 查询当天该任务是否有执行记录，如果有则只重算；
							List<TTaskTableResult> taskRuns = taskTableResultService
									.queryCurrentValTaskFlows(taskSettings
											.getTaskId());
							if (!CollectionUtils.isEmpty(taskRuns)) {
								for (TTaskTableResult taskTableResult : taskRuns) {
									log.debug("--------------------------begin to run task:"
											+ taskSettings.getTaskName()
											+ ",task flow is :"
											+ taskTableResult.getTaskFlow()
											+ "------------------------");
									reportTask.run(taskTableResult.getTaskId(),
											taskTableResult.getTaskFlow(),
											pageSize, startId);
									log.debug("--------------------------task run end:"
											+ taskSettings.getTaskName()
											+ "------------------------");

									// 记录报表执行记录
									TReportRunResult reportRunResult = reportRunResultService.queryReportRunResult(taskTableResult.getTaskId(),
											taskTableResult.getTaskFlow());
									if(reportRunResult != null){
										reportRunResult.setRunResult(1L);
										reportRunResult.setRunTime(DateUtil.getCurrDateTime());

										reportRunResultService.saveReportRunResult(reportRunResult);
									}

									break;
								}
							} else {
								log.debug("--------------------------begin to run task:"
										+ taskSettings.getTaskName()
										+ "------------------------");

								reportTask.run(taskSettings.getTaskId(),
										taskFlow, pageSize, startId);
								log.debug("--------------------------task run end:"
										+ taskSettings.getTaskName()
										+ "------------------------");
								Long endTime = System.currentTimeMillis();
								// 记录报表执行记录
								TReportRunResult reportRunResult = new TReportRunResult();
								reportRunResult.setRunResult(1L);
								reportRunResult.setTaskId(taskSettings
										.getTaskId());
								reportRunResult.setTaskName(taskSettings
										.getTaskName());
								reportRunResult.setRunTime(DateUtil
										.getCurrDateTime());
								reportRunResult.setStartTime(Long
										.parseLong(DateUtil.dateToString(
												new Date(startTime),
												"yyyyMMddHHmmss")));
								reportRunResult.setEndTime(Long
										.parseLong(DateUtil.dateToString(
												new Date(endTime),
												"yyyyMMddHHmmss")));
								reportRunResult.setTaskFlow(taskFlow);
								reportRunResultService
										.saveReportRunResult(reportRunResult);
							}

						}
					} catch (Exception e) {
						log.error("error found,see:", e);
						Long endTime = System.currentTimeMillis();
						// 记录报表执行记录
						if (!IReportTask.COMPLETED.equals(e.getMessage())) {
							TReportRunResult reportRunResult = new TReportRunResult();
							reportRunResult.setRunResult(0L);
							reportRunResult.setTaskId(taskSettings.getTaskId());
							reportRunResult.setTaskName(taskSettings
									.getTaskName());
							reportRunResult.setRunTime(DateUtil
									.getCurrDateTime());
							reportRunResult.setTaskFlow(taskFlow);
							reportRunResult.setStartTime(Long
									.parseLong(DateUtil.dateToString(new Date(
											startTime), "yyyyMMddHHmmss")));
							reportRunResult.setEndTime(Long.parseLong(DateUtil
									.dateToString(new Date(endTime),
											"yyyyMMddHHmmss")));
							String message = e.getMessage();
							if (message.length() > 300) {
								message = message.substring(0, 300);
							}
							reportRunResult.setRemark(message);
							reportRunResultService
									.saveReportRunResult(reportRunResult);
						}
					}
				}
			}

			if (!CollectionUtils.isEmpty(tobeRunTask2)) {
				String taskFlow = TaskFlowUtils.genNextFlow();
				for (Task task : tobeRunTask2) {
					Long startTime = System.currentTimeMillis();
					try {
						// 获取待执行的报表任务
						if (StringUtils.isEmpty(task.getClassImpl())) {
							continue;
						}
						Long pageSize = task.getPageSize() == null ? 1000L
								: Long.parseLong(task.getPageSize());
						String startId = task.getStartId();

						Object classImpl = Class.forName(
								task.getClassImpl().trim()).newInstance();
						if (classImpl instanceof IReportTask) {
							// 执行报表任务
							IReportTask reportTask = (IReportTask) classImpl;

							// 查询当天该任务是否有执行记录，如果有则只重算；
							// 查询当天该任务是否有执行记录，如果有则只重算；
							List<TTaskTableResult> taskRuns = taskTableResultService
									.queryCurrentValTaskFlows(task.getTaskId());
							if (!CollectionUtils.isEmpty(taskRuns)) {
								for (TTaskTableResult taskTableResult : taskRuns) {
									log.debug("--------------------------begin to run task:"
											+ task.getName()
											+ ",task flow is :"
											+ taskTableResult.getTaskFlow()
											+ "------------------------");
									reportTask.run(task.getTaskId(),
											taskTableResult.getTaskFlow(),
											pageSize, startId);
									log.debug("--------------------------task run end:"
											+ task.getName()
											+ "------------------------");
									break;
								}
							} else {
								log.debug("--------------------------begin to run task:"
										+ task.getName()
										+ "------------------------");
								reportTask.run(task.getTaskId(), taskFlow,
										pageSize, startId);
								log.debug("--------------------------task run end:"
										+ task.getName()
										+ "------------------------");
								Long endTime = System.currentTimeMillis();
								// 记录报表执行记录
								TReportRunResult reportRunResult = new TReportRunResult();
								reportRunResult.setRunResult(1L);
								reportRunResult.setTaskId(task.getTaskId());
								reportRunResult.setTaskName(task.getName());
								reportRunResult.setTaskFlow(taskFlow);
								reportRunResult.setRunTime(DateUtil
										.getCurrDateTime());
								reportRunResult.setStartTime(Long
										.parseLong(DateUtil.dateToString(
												new Date(startTime),
												"yyyyMMddHHmmss")));
								reportRunResult.setEndTime(Long
										.parseLong(DateUtil.dateToString(
												new Date(endTime),
												"yyyyMMddHHmmss")));
								reportRunResultService
										.saveReportRunResult(reportRunResult);
							}

						}
					} catch (Exception e) {
						log.error("error found,see:", e);
						Long endTime = System.currentTimeMillis();
						// 记录报表执行记录
						TReportRunResult reportRunResult = new TReportRunResult();
						reportRunResult.setRunResult(0L);
						reportRunResult.setTaskId(task.getTaskId());
						reportRunResult.setTaskName(task.getName());
						reportRunResult.setTaskFlow(taskFlow);
						reportRunResult.setRunTime(DateUtil.getCurrDateTime());
						reportRunResult.setStartTime(Long.parseLong(DateUtil
								.dateToString(new Date(startTime),
										"yyyyMMddHHmmss")));
						reportRunResult.setEndTime(Long.parseLong(DateUtil
								.dateToString(new Date(endTime),
										"yyyyMMddHHmmss")));
						String message = e.getMessage();
						if (message.length() > 300) {
							message = message.substring(0, 300);
						}
						reportRunResult.setRemark(message);
						reportRunResultService
								.saveReportRunResult(reportRunResult);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean isNeedRun(TReportTaskSettings taskSettings)
			throws Exception {
		if (null == taskSettings || taskSettings.getUnit() == null) {
			return false;
		}

		return isNeedRun(taskSettings.getTaskId(), taskSettings.getUnit()
				.intValue(), taskSettings.getRunTime(),
				taskSettings.getPeriod());
	}

	private boolean isNeedRun(Task taskSettings) throws Exception {
		if (null == taskSettings || taskSettings.getUnit() == null) {
			return false;
		}

		Unit unit = Unit.genEnum(taskSettings.getUnit());
		return isNeedRun(taskSettings.getTaskId(), unit.getValue(),
				taskSettings.getRunTime(),
				Long.parseLong(taskSettings.getPeriod()));
	}

	private boolean isNeedRun(String taskId, int unit,
			String taskSettingRunTime, Long period) throws Exception {
		ReportRunResultService reportRunResultService = (ReportRunResultService) ApplicationContextUtil
				.getBean("reportRunResultService");
		TReportRunResult treportRunResult = reportRunResultService
				.queryMaxTaskFlowResult(taskId);
		String run_time = DateUtil.getCurrDate("HH:mm");

		// 如果未执行过，则判断是否到达时间点，如果到了则执行，如果未到则不执行
		if (treportRunResult == null || treportRunResult.getRunTime() == null) {
			return run_time.compareTo(taskSettingRunTime) >= 0;
		} else if (treportRunResult.getRunResult() < 1) {
			return true;
		}

		// 如果执行过，则需要判断间隔时间，超过间隔则执行，否则不执行
		Date runTime = treportRunResult.getRunTime();
		Date currDateTime = DateUtil.getCurrDateTime();
		long delta = currDateTime.getTime() - runTime.getTime();

		// 按天
		if (unit == 1) {
			// 如果当前的时间已经超过了时间间隔，则运行
			return delta / (24 * 60 * 60 * 1000) >= period;
		}
		// 小时
		if (unit == 2) {
			// 如果当前的时间已经超过了时间间隔，则运行
			return delta / (60 * 60 * 1000) >= period;
		}
		// 分钟
		if (unit == 3) {
			// 如果当前的时间已经超过了时间间隔，则运行
			return delta / (60 * 1000) >= period;
		}
		// 秒
		if (unit == 4) {
			// 如果当前的时间已经超过了时间间隔，则运行
			return delta / (1000) >= period;
		}
		// 毫秒
		if (unit == 5) {
			// 如果当前的时间已经超过了时间间隔，则运行
			return delta >= period;
		}
		return false;
	}

}
