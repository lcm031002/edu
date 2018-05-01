package com.edu.report.framework.tasks.common;

import java.util.HashMap;
import java.util.Map;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.IReportTask;
import com.edu.report.web.common.service.SurplusAmountFeeService;
import com.edu.report.web.common.service.SurplusAmountFeeService;

/**
 * @ClassName: TSurplusAmountFeeTask
 * @Description: 学员剩余课时费用任务
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月12日 下午2:55:41
 *
 */
public class TSurplusAmountFeeTask implements IReportTask {

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId) throws Exception {
		SurplusAmountFeeService service = (SurplusAmountFeeService) ApplicationContextUtil
				.getBean("surplusAmountFeeService");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("task_flow", taskFlow); //任务批次
		
		service.updateReport(param);
	}

}
