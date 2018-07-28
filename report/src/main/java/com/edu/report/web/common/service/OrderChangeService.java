package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TOrderChangeReport;
import com.edu.report.model.TOrderChangeReport;

/**
 * @ClassName: PerformanceSumService
 * @Description: 报退冻转服务API
 *
 */
public interface OrderChangeService {

	List<TOrderChangeReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
