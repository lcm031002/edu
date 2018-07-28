package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TDailySummaryPerformance;
import com.edu.report.model.TDailySummaryPerformance;

/**
 * @ClassName: PerformanceSumService
 * @Description: 业绩总表服务API
 *
 */
public interface DailySummaryPerformanceService {

	List<TDailySummaryPerformance> queryReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
