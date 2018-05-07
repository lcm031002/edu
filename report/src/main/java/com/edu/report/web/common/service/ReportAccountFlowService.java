package com.edu.report.web.common.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportAccountFlow;
import com.edu.report.model.TReportAccountFlow;
import com.github.pagehelper.Page;

/**
 * 报表-账户流水查询接口
 * 
 * @author lincm
 *
 */
public interface ReportAccountFlowService {
    String TABLE = "T_ACCOUNT_FLOW";
    String TABLE_KEY = "CHANGE_TIME";
    String TABLE_TYPE = "INCREMENT";
    
    /**
     * 报表-账户流水分页查询
     * 
     * @param paramMap
     *            查询条件 条件：branch_id 校区编号
     * @return 账户流水信息
     * @throws Exception
     */
    Page<TReportAccountFlow> selectForPage(Map<String, Object> paramMap) throws Exception;

    /**
     * 报表-账户流水列表查询
     * 
     * @param paramMap
     *            查询条件 条件：branch_id 校区编号
     * @return 账户流水信息
     * @throws Exception
     */
    List<TReportAccountFlow> selectForList(Map<String, Object> paramMap) throws Exception;

    /**
     * 根据账户流水编号统计账户流水
     * @param taskFlow 任务批次号
     * @param minId 账户流水编号开始
     * @param maxId 账户流水编号结束
     * @throws Exception
     */
    void addReportAccountFlow(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
