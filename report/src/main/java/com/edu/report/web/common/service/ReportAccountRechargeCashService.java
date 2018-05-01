package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAccountRechargeCash;
import com.edu.report.model.TAccountRechargeCash;

/**
 * @ClassName: ReportAccountService
 * @Description: 充值取现服务API
 * @author ohs@klxuexi.org
 * @date 2017年5月8日 下午5:36:48
 *
 */
public interface ReportAccountRechargeCashService {

	List<TAccountRechargeCash> queryReport(Map<String, Object> param) throws Exception;
	
	void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception;
}
