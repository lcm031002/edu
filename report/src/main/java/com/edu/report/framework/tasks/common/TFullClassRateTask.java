///**
// * @Title: TAccountCashierTask.java
// * @Package com.edu.report.tasks.common
// */
//package com.edu.report.framework.tasks.common;
//
//import com.edu.common.util.ApplicationContextUtil;
//import com.edu.report.api.AbstactDailyReportTask;
//import com.edu.report.web.common.service.TFullClassRateService;
//
///**
// * @ClassName: TFullClassRateTask
// * @Description: 满班率报表任务
// *
// */
//public class TFullClassRateTask extends AbstactDailyReportTask {
//
//	@Override
//	protected void doBusiness(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
//		TFullClassRateService tFullClassRateService = (TFullClassRateService) ApplicationContextUtil
//				.getBean("tFullClassRateService");
////		tFullClassRateService.updateByTaskFlow(taskFlow, minOperateNo, maxOperateNo);
//	}
//
//	@Override
//	protected String getTableName() {
//		return "T_FULL_CLASS_RATE";
//	}
//
//	@Override
//	protected String getTableKey() {
//		return " ";
//	}
//
//}
