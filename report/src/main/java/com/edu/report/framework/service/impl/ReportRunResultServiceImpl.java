package com.edu.report.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.ReportRunResultDao;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.model.TReportRunResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edu.report.dao.ReportRunResultDao;
import com.edu.report.framework.service.ReportRunResultService;
import com.edu.report.model.TReportRunResult;
import com.github.pagehelper.Page;

/**
 * @ClassName: ReportRunResultServiceImpl
 * @Description: 任务运算结果服务
 *
 */
@Service(value = "reportRunResultService")
public class ReportRunResultServiceImpl implements ReportRunResultService {

	@Resource(name = "reportRunResultDao")
	private ReportRunResultDao reportRunResultDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.ReportRunResultService#
	 * queryReportRunResult(java.lang.String)
	 */
	@Override
	public List<TReportRunResult> queryReportRunResult(String taskId)
			throws Exception {
		TReportRunResult reportRunResult = new TReportRunResult();
		reportRunResult.setTaskId(taskId);
		return reportRunResultDao.queryReportRunResult(reportRunResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.ReportRunResultService#
	 * queryMaxTaskFlowResult(java.lang.String)
	 */
	@Override
	public TReportRunResult queryMaxTaskFlowResult(String taskId)
			throws Exception {
		TReportRunResult reportRunResult = new TReportRunResult();
		reportRunResult.setTaskId(taskId);
		return reportRunResultDao.queryMaxTaskFlowResult(reportRunResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.edu.report.framework.service.ReportRunResultService#
	 * saveReportRunResult(com.edu.report.model.TReportRunResult)
	 */
	@Override
	public void saveReportRunResult(TReportRunResult reportRunResult)
			throws Exception {
	    if (reportRunResult.getId() != null) {
	        reportRunResultDao.updateReportRunResult(reportRunResult);
	    } else {
	        reportRunResultDao.saveReportRunResult(reportRunResult);
	    }
	}

    @Override
    public Page<TReportRunResult> selectForPage(Map<String, Object> queryParam) throws Exception {
        return this.reportRunResultDao.selectForPage(queryParam);
    }

    @Override
    public TReportRunResult queryReportRunResult(String taskId, String taskFlow) throws Exception {
        TReportRunResult reportRunResult = new TReportRunResult();
        reportRunResult.setTaskId(taskId);
        reportRunResult.setTaskFlow(taskFlow);
        List<TReportRunResult> resultList = reportRunResultDao.queryReportRunResult(reportRunResult);
        return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
    }

}
