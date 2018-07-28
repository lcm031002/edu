package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAccountCashier;
import com.edu.report.model.TAccountCashier;

/**
 * @ClassName: ReportAccountCashierService
 *
 */
public interface ReportAccountCashierService {

	List<TAccountCashier> queryReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
