package com.edu.report.web.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.TReportAccountDao;
import com.edu.report.model.TReportAccount;
import com.edu.report.web.common.service.ReportAccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.NumberUtils;
import com.edu.report.framework.dao.TReportAccountDao;
import com.edu.report.model.TReportAccount;
import com.edu.report.web.common.service.ReportAccountService;

/**
 * @ClassName: ReportAccountServiceImpl
 * @Description: 学员账户余额服务实现类
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月2日 下午5:40:13
 *
 */
@Service("reportAccountService")
public class ReportAccountServiceImpl implements ReportAccountService {
	
	@Resource(name = "tReportAccountDao")
	private TReportAccountDao tReportAccountDao;

	@Override
	public List<TReportAccount> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		TReportAccount newParam = new TReportAccount();
		newParam.setBu_id(NumberUtils.object2Long(param.get("bu_id")));
		newParam.setBranch_id(NumberUtils.object2Long(param.get("branch_id")));

		return tReportAccountDao.queryReport(newParam);
	}

	@Override
	public void updateReport(Map<String, Object> param) throws Exception {
		// 1.先删除旧的数据
		tReportAccountDao.deleteAll(param);
		// 2.重新插入数据
		tReportAccountDao.insert(param);
	}

}
