package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.model.TBusinessStatistics;
import com.edu.report.dao.TOrderPerformanceDao;
import com.edu.report.model.TBusinessStatistics;
import com.edu.report.model.TOrderPerformance;
import com.edu.report.web.common.service.ReportOrderPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.dao.TOrderPerformanceDao;
import com.edu.report.model.TOrderPerformance;
import com.edu.report.web.common.service.ReportOrderPerformanceService;

/**
 * @ClassName: ReportAccountRechargeCashServiceImpl
 * @Description: 课程顾问绩效服务实现类
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年8月24日 下午5:40:13
 *
 */
@Service("reportOrderPerformanceService")
public class ReportOrderPerformanceServiceImpl implements ReportOrderPerformanceService {
	
	@Resource(name = "orderPerformanceDao")
	private TOrderPerformanceDao tOrderPerformanceDao;

	@Override
	public List<TOrderPerformance> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tOrderPerformanceDao.selectForList(param);
	}

	@Override
	public void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		// 删除批次数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		tOrderPerformanceDao.deleteTaskFlow(param);
		// 2.重新插入数据
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		tOrderPerformanceDao.saveTaskFlow(param);
	}

	@Override
	public List<TBusinessStatistics> queryStatisticsReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");

		return tOrderPerformanceDao.selectForStatisticsList(param);
	}
}
