package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportAccount;
import com.edu.report.model.TReportAccount;

/**
 * @ClassName: ReportAccountService
 * @Description: 学员账户余额服务API
 *
 */
public interface ReportAccountService {

	List<TReportAccount> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(Map<String, Object> param) throws Exception;
}
