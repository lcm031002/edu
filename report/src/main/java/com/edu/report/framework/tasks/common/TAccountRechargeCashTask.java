package com.edu.report.framework.tasks.common;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.common.service.ReportAccountRechargeCashService;
import com.edu.report.api.BaseReportTask;

/**
 * @ClassName: TAccountRechargeCashTask
 * @Description: 充值取现表任务
 *
 */
public class TAccountRechargeCashTask extends BaseReportTask {
	private static final String TABLE = "T_ACCOUNT_RECHARGE_CASH";
	private static final String TABLE_KEY = "OPERATE_NO";
	private static final String TABLE_TYPE = "INCREMENT";

	
	private ReportAccountRechargeCashService reportAccountRechargeCashService = (ReportAccountRechargeCashService) ApplicationContextUtil
			.getBean("reportAccountRechargeCashService");

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId)
			throws Exception {
		setTableInfo(TABLE, TABLE_KEY, TABLE_TYPE);
		calculateOperateNo(taskId,taskFlow, pageSize, startId);
		// 更新报表数据
		reportAccountRechargeCashService.updateByTaskFlow(taskFlow,
				minOperateNo, maxOperateNo);

	}

}
