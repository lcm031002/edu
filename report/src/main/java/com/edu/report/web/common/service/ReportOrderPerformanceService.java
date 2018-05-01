package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TOrderPerformance;
import com.edu.report.model.TBusinessStatistics;
import com.edu.report.model.TBusinessStatistics;
import com.edu.report.model.TOrderPerformance;

/**
 * @ClassName: ReportOrderPerformanceService
 * @Description: 课程顾问绩效API
 * @author ohs@klxuexi.org
 * @date 2017年8月24日 下午5:36:48
 *
 */
public interface ReportOrderPerformanceService {

	List<TOrderPerformance> queryReport(Map<String, Object> param) throws Exception;

	List<TBusinessStatistics> queryStatisticsReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
