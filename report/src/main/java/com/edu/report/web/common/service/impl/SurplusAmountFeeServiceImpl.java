package com.edu.report.web.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.SurplusAmountFeeDao;
import com.edu.report.model.TSurplusAmountFee;
import com.edu.report.web.common.service.SurplusAmountFeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.dao.SurplusAmountFeeDao;
import com.edu.report.model.TSurplusAmountFee;
import com.edu.report.web.common.service.SurplusAmountFeeService;

/**
 * @ClassName: SurplusAmountFeeServiceImpl
 * @Description: 剩余课时费用表服务
 *
 */
@Service("surplusAmountFeeService")
public class SurplusAmountFeeServiceImpl implements SurplusAmountFeeService {
	
	@Resource(name = "surplusAmountFeeDao")
	private SurplusAmountFeeDao surplusAmountFeeDao;

	@Override
	public List<TSurplusAmountFee> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		
		return surplusAmountFeeDao.queryReport(param);
	}

	@Override
	public void updateReport(Map<String, Object> param) throws Exception {
		// 1.先删除旧的数据
		surplusAmountFeeDao.deleteAll(param);
		// 2.重新插入数据
		surplusAmountFeeDao.insert(param);
	}

}
