/**  
 * @Title: ReportPerformanceDetailsServiceImpl.java
 * @Package com.edu.report.web.common.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:48:08
 * @version KLXX ERPV4.0  
 */
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
 * @author yecb
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
