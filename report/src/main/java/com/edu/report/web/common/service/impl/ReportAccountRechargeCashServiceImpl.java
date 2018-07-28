package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.TReportAccountRechargeCashDao;
import com.edu.report.model.TAccountRechargeCash;
import com.edu.report.web.common.service.ReportAccountRechargeCashService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.TReportAccountRechargeCashDao;
import com.edu.report.model.TAccountRechargeCash;
import com.edu.report.web.common.service.ReportAccountRechargeCashService;

/**
 * @ClassName: ReportAccountRechargeCashServiceImpl
 * @Description: 充值取现服务实现类
 *
 */
@Service("reportAccountRechargeCashService")
public class ReportAccountRechargeCashServiceImpl implements ReportAccountRechargeCashService {
	
	@Resource(name = "tReportAccountRechargeCashDao")
	private TReportAccountRechargeCashDao tReportAccountRechargeCashDao;

	@Override
	public List<TAccountRechargeCash> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return tReportAccountRechargeCashDao.queryReport(param);
	}

	@Override
	public void updateByTaskFlow(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		// 删除批次数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		tReportAccountRechargeCashDao.removeByTaskFlow(param);
		// 2.重新插入数据
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		tReportAccountRechargeCashDao.addByTaskFlow(param);
	}

}
