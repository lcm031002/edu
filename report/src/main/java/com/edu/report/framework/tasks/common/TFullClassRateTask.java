///**
// * @Title: TAccountCashierTask.java
// * @Package com.edu.report.tasks.common
// * @author Au yeung ohs@klxuexi.org
// * @date 2017年5月8日 下午4:42:02
// * @version KLXX ERPV4.0
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
// * @author linj@klxuexi.org
// * @date 2017年10月18日 下午4:42:02
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
