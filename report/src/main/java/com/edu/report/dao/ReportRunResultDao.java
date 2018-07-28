package com.edu.report.dao;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TReportRunResult;
import org.springframework.stereotype.Repository;

import com.edu.report.model.TReportRunResult;
import com.github.pagehelper.Page;

/**
 * @ClassName: ReportRunResultDao
 * @Description: 报表运行结果
 *
 */
@Repository("reportRunResultDao")
public interface ReportRunResultDao {

    List<TReportRunResult> queryReportRunResult(TReportRunResult queryParam) throws Exception;

    TReportRunResult queryMaxTaskFlowResult(TReportRunResult queryParam) throws Exception;

    void saveReportRunResult(TReportRunResult reportRunResult) throws Exception;
    
    void updateReportRunResult(TReportRunResult reportRunResult) throws Exception;

    Page<TReportRunResult> selectForPage(Map<String, Object> queryParam) throws Exception;
}
