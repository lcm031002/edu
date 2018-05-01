package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TDailySummaryPerformance;
import com.edu.report.model.TDailySummaryPerformance;

/**
 * @ClassName: PerformanceSumService
 * @Description: 业绩总表服务API
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:36:48
 *
 */
public interface DailySummaryPerformanceService {

	List<TDailySummaryPerformance> queryReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
