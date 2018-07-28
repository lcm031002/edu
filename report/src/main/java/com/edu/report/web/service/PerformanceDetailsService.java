package com.edu.report.web.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TPerformanceDetails;
import com.edu.report.model.TPerformanceDetails;
import com.github.pagehelper.Page;


/**
 * @ClassName: PerformanceDetailsService
 * @Description: 业绩明细
 *
 */
public interface PerformanceDetailsService {
    /**
     * 查询学员业绩明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 学员业绩明细
     * @throws Exception
     */
    Page<TPerformanceDetails> selectForPage(Map<String, Object> paramMap) throws Exception;
    
    /**
     * 查询学员业绩明细
     * @param paramMap 查询条件  团队、校区、学员姓名、学员编号、开始时间、结束时间
     * @return 学员业绩明细
     * @throws Exception
     */
    List<TPerformanceDetails> selectForList(Map<String, Object> paramMap) throws Exception;
    
    
	/**
	 * 
	 * @Title: updateReportPerformanceDetail
	 * @Description: 更新一批次报表详细信息
	 * @param taskFlow
	 *            批次流水
	 * @param minOperateNo
	 *            最小操作id
	 * @param maxOperateNo
	 *            最大操作id
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updateReportPerformanceDetail(String taskFlow, Long minOperateNo,
			Long maxOperateNo) throws Exception;
}
