/**  
 * @Title: ReportPerformanceDetailsService.java
 * @Package com.edu.report.web.common.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:47:43
 * @version KLXX ERPV4.0  
 */
package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TOnlineOrderReport;
import com.edu.report.model.TOnlineOrderReport;
import com.github.pagehelper.Page;

/**
 * 在线支付报表服务
 * @author yecb
 *
 */
public interface OnLineOrderService {
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
