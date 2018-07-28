package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.common.service.ReportAccountCashierService;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.common.service.ReportAccountCashierService;

/**
 * @ClassName: TAccountCashierTask
 * @Description: 出纳报表任务
 *
 */
public class TAccountCashierTask extends BaseReportTask {
	private static final String TABLE = "T_ACCOUNT_CASHIER";
	private static final String TABLE_KEY = "INPUT_TIME";
	private static final String TABLE_TYPE = "INCREMENT";

	
	private ReportAccountCashierService reportAccountCashierService = (ReportAccountCashierService) ApplicationContextUtil
			.getBean("reportAccountCashierService");

	@Override
	public void run(String taskId, String taskFlow, Long pageSize, String startId)
			throws Exception {
		setTableInfo(TABLE, TABLE_KEY, TABLE_TYPE);
		calculateOperateNo(taskId,taskFlow, pageSize, startId);
		// 更新报表数据
		reportAccountCashierService.updateByTaskFlow(taskFlow,
				minOperateNo, maxOperateNo);

	}

}
