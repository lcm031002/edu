/**  
 * @Title: TPerformanceSumTask.java
 * @Package com.edu.report.tasks.common
 * @author Au yeung ohs@klxuexi.org  
 * @date 2017年5月8日 下午4:42:02
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.common.service.DailySummaryPerformanceService;
import com.edu.report.api.BaseReportTask;

/**
 * @ClassName: TPerformanceSumTask
 * @Description: 业绩总表任务
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午4:42:02
 * 
 */
public class TPerformanceSumTask extends BaseReportTask {
	private static final String TABLE = "T_PERFORMANCE_SUM";
	private static final String TABLE_KEY = "FINISH_TIME";
	private static final String TABLE_TYPE = "INCREMENT";

	
	private DailySummaryPerformanceService dailySummaryPerformanceService = (DailySummaryPerformanceService) ApplicationContextUtil
			.getBean("dailySummaryPerformanceService"); 

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId)
			throws Exception {
		setTableInfo(TABLE, TABLE_KEY, TABLE_TYPE);
		calculateOperateNo( taskId,taskFlow, pageSize, startId);
		// 更新报表数据
		dailySummaryPerformanceService.updateByTaskFlow(taskFlow,
				minOperateNo, maxOperateNo);

	}

}
