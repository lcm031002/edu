package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportAccount;
import com.edu.report.model.TReportAccount;

/**
 * @ClassName: ReportAccountService
 * @Description: 学员账户余额服务API
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月2日 下午5:36:48
 *
 */
public interface ReportAccountService {

	List<TReportAccount> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(Map<String, Object> param) throws Exception;
}
