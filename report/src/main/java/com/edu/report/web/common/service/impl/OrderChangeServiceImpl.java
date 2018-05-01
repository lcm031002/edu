package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.OrderChangeReportDao;
import com.edu.report.model.TOrderChangeReport;
import com.edu.report.web.common.service.OrderChangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.OrderChangeReportDao;
import com.edu.report.model.TOrderChangeReport;
import com.edu.report.web.common.service.OrderChangeService;

/**
 * @ClassName: PerformanceSumServiceImpl
 * @Description: 报退冻转服务实现类
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:40:13
 *
 */
@Service("orderChangeService")
public class OrderChangeServiceImpl implements OrderChangeService {
	
	@Resource(name = "orderChangeReportDao")
	private OrderChangeReportDao orderChangeReportDao;

	@Override
	public List<TOrderChangeReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return orderChangeReportDao.queryReport(param);
	}

	@Override
	public void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		// 删除批次数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		orderChangeReportDao.removeByTaskFlow(param);
		// 2.重新插入数据
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		
		// 报班、报班作废业绩
		orderChangeReportDao.saveBaoBan(param);
		
		// 退费业绩、退费作废作废
		orderChangeReportDao.saveTuiFei(param);
		
		// 冻结业绩、冻结作废
		orderChangeReportDao.saveDonJie(param);
		
		// 转班业绩
		orderChangeReportDao.saveZhuanBan(param);
	}

}
