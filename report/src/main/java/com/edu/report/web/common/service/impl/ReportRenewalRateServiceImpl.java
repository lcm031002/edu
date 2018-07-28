package com.edu.report.web.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.TReportRenewalRateDao;
import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import com.edu.report.web.common.service.ReportRenewalRateService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.TReportRenewalRateDao;
import com.edu.report.model.TReportRenewalRateDetail;
import com.edu.report.model.TReportRenewalRateSum;
import com.edu.report.web.common.service.ReportRenewalRateService;

/**
 * @ClassName: PerformanceSumServiceImpl
 * @Description: 业绩总表服务实现类
 *
 */
@Service("reportRenewalRateService")
public class ReportRenewalRateServiceImpl implements ReportRenewalRateService {
	
	@Resource(name = "tReportRenewalRateDao")
	private TReportRenewalRateDao tReportRenewalRateDao;

	@Override
	public List<TReportRenewalRateSum> queryForSum(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportRenewalRateDao.queryForSum(param);
	}

	@Override
	public List<TReportRenewalRateDetail> queryForLastBase(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportRenewalRateDao.queryForLastBase(param);
	}
	
	@Override
	public List<TReportRenewalRateDetail> queryForEstimate(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportRenewalRateDao.queryForEstimate(param);
	}
	
	@Override
	public List<TReportRenewalRateDetail> queryForActual(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportRenewalRateDao.queryForActual(param);
	}
	
}
