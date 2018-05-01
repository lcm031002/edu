/**  
 * @Title: TPerformanceDetailsDao.java
 * @Package com.edu.report.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月26日 下午5:40:24
 * @version KLXX ERPV4.0  
 */
package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.report.model.TOnlineOrderReport;
import com.github.pagehelper.Page;

/**
 * 在线支付订单DAO
 * @author yecb
 *
 */
@Repository("onLineOrderReportDao")
public interface OnLineOrderReportDao {
	/**
     * 查询在线支付的明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 在线支付明细
     * @throws Exception
     */
    Page<TOnlineOrderReport> selectForPage(Map<String, Object> paramMap) throws Exception;
    
    /**
     *  查询在线支付的明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 学在线支付明细
     * @throws Exception
     */
    List<TOnlineOrderReport> selectForList(Map<String, Object> paramMap) throws Exception;
}
