package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.TReportAccountCashierDao;
import com.edu.report.model.TAccountCashier;
import com.edu.report.web.common.service.ReportAccountCashierService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.TReportAccountCashierDao;
import com.edu.report.model.TAccountCashier;
import com.edu.report.web.common.service.ReportAccountCashierService;

/**
 * @ClassName: ReportAccountCashierServiceImpl
 * @Description: 充值取现服务实现类
 * @author Au yeung ohs@klxuexi.org
 * @date 2017年5月8日 下午5:40:13
 *
 */
@Service("reportAccountCashierService")
public class ReportAccountCashierServiceImpl implements ReportAccountCashierService {
	
	@Resource(name = "tReportAccountCashierDao")
	private TReportAccountCashierDao tReportAccountCashierDao;

	@Override
	public List<TAccountCashier> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportAccountCashierDao.queryReport(param);
	}

	@Override
	public void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		// 删除批次数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		tReportAccountCashierDao.removeByTaskFlow(param);
		// 2.重新插入数据
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		//tReportAccountCashierDao.addByTaskFlow(param);
		tReportAccountCashierDao.addByTaskFlowBatch1(param);
		tReportAccountCashierDao.addByTaskFlowBatch2(param);
		tReportAccountCashierDao.addByTaskFlowBatch3(param);
	}

}
