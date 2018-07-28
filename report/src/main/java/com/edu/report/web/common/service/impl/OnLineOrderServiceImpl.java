package com.edu.report.web.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.OnLineOrderReportDao;
import com.edu.report.model.TOnlineOrderReport;
import com.edu.report.web.common.service.OnLineOrderService;
import org.springframework.stereotype.Service;

import com.edu.report.dao.OnLineOrderReportDao;
import com.edu.report.model.TOnlineOrderReport;
import com.edu.report.web.common.service.OnLineOrderService;
import com.github.pagehelper.Page;

/**
 * 在线支付明细
 *
 */
@Service("onLineOrderService")
public class OnLineOrderServiceImpl implements OnLineOrderService {
	
	@Resource(name = "onLineOrderReportDao")
	private OnLineOrderReportDao onLineOrderReportDao;
	
	@Override
	public Page<TOnlineOrderReport> selectForPage(Map<String, Object> paramMap)
			throws Exception {
		return onLineOrderReportDao.selectForPage(paramMap);
	}

	@Override
	public List<TOnlineOrderReport> selectForList(Map<String, Object> paramMap)
			throws Exception {
		return onLineOrderReportDao.selectForList(paramMap);
	}

}
