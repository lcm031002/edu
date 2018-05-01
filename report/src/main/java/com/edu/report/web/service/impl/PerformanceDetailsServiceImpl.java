/**  
 * @Title: PerformanceDetailsServiceImpl.java
 * @Package com.edu.report.web.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月27日 下午5:19:43
 * @version KLXX ERPV4.0  
 */
package com.edu.report.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.TPerformanceDetailsDao;
import com.edu.report.model.TPerformanceDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.dao.TPerformanceDetailsDao;
import com.edu.report.model.TPerformanceDetails;
import com.edu.report.web.service.PerformanceDetailsService;
import com.github.pagehelper.Page;

/**
 * @ClassName: PerformanceDetailsServiceImpl
 * @Description: 业绩明细服务实现
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月27日 下午5:19:43
 * 
 */
@Service(value = "performanceDetailsService")
public class PerformanceDetailsServiceImpl implements PerformanceDetailsService {

	@Resource(name = "performanceDetailsDao")
	private TPerformanceDetailsDao performanceDetailsDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.web.service.PerformanceDetailsService#
	 * updateReportPerformanceDetail(java.lang.String, java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public void updateReportPerformanceDetail(String taskFlow,
			Long minOperateNo, Long maxOperateNo) throws Exception {
		Assert.notNull(taskFlow);
		Assert.notNull(minOperateNo);
		Assert.notNull(maxOperateNo);
		
		// 删除批次数据
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("taskFlow", taskFlow);
		performanceDetailsDao.deleteTaskFlow(queryParam);
		
		queryParam.put("minOperateNo", minOperateNo);
		queryParam.put("maxOperateNo", maxOperateNo);
		
		// 报班业绩
		performanceDetailsDao.savePerformanceDetail41and42(queryParam);
		
		// 报班作废业绩
		performanceDetailsDao.savePerformanceDetail53(queryParam);
		
		// 退费业绩、退费作废
		performanceDetailsDao.savePerformanceDetail51and54(queryParam);
		
		// 冻结业绩、冻结作废
		performanceDetailsDao.savePerformanceDetail55and54(queryParam);
		
		// 转班业绩
		performanceDetailsDao.savePerformanceDetail52(queryParam);
		
		// 理赔业绩 or 理赔作废业绩
		performanceDetailsDao.savePerformanceDetailLipei(queryParam);
	}

    @Override
    public Page<TPerformanceDetails> selectForPage(Map<String, Object> paramMap) throws Exception {
        return this.performanceDetailsDao.selectForPage(paramMap);
    }

    @Override
    public List<TPerformanceDetails> selectForList(Map<String, Object> paramMap) throws Exception {
        return this.performanceDetailsDao.selectForList(paramMap);
    }

}
