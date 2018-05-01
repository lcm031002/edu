package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.DailySummaryPerformanceDao;
import com.edu.report.model.TDailySummaryPerformance;
import com.edu.report.web.common.service.DailySummaryPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.DailySummaryPerformanceDao;
import com.edu.report.model.TDailySummaryPerformance;
import com.edu.report.web.common.service.DailySummaryPerformanceService;

/**
 * @ClassName: PerformanceSumServiceImpl
 * @Description: 业绩总表服务实现类
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:40:13
 *
 */
@Service("dailySummaryPerformanceService")
public class DailySummaryPerformanceServiceImpl implements DailySummaryPerformanceService {
	
	@Resource(name = "dailySummaryPerformanceDao")
	private DailySummaryPerformanceDao dailySummaryPerformanceDao;

	@Override
	public List<TDailySummaryPerformance> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return dailySummaryPerformanceDao.queryReport(param);
	}

	@Override
	public void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		// 删除批次数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		dailySummaryPerformanceDao.removeByTaskFlow(param);
		// 2.重新插入数据
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		dailySummaryPerformanceDao.addByTaskFlow(param);
	}

}
