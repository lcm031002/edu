package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;

/**
 * @ClassName: ReportRenewalRateService
 * @Description: 业绩总表服务API
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:36:48
 *
 */
public interface ReportRenewalRateService {

	List<TReportRenewalRateSum> queryForSum(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForLastBase(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForEstimate(Map<String, Object> param) throws Exception;
	
	List<TReportRenewalRateDetail> queryForActual(Map<String, Object> param) throws Exception;
	
}
