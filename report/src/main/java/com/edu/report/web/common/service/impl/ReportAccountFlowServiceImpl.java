package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.TReportAccountFlowDao;
import com.edu.report.model.TReportAccountFlow;
import com.edu.report.web.common.service.ReportAccountFlowService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.TReportAccountFlowDao;
import com.edu.report.model.TReportAccountFlow;
import com.edu.report.web.common.service.ReportAccountFlowService;
import com.github.pagehelper.Page;

@Service(value = "reportAccountFlowService")
public class ReportAccountFlowServiceImpl implements ReportAccountFlowService {
    @Resource(name = "tReportAccountFlowDao")
    private TReportAccountFlowDao tReportAccountFlowDao;

    @Override
    public Page<TReportAccountFlow> selectForPage(Map<String, Object> paramMap) throws Exception {
        return this.tReportAccountFlowDao.selectForPage(paramMap);
    }

    @Override
    public List<TReportAccountFlow> selectForList(Map<String, Object> paramMap) throws Exception {
        return this.tReportAccountFlowDao.selectForList(paramMap);
    }

    @Override
    public void addReportAccountFlow(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
        Assert.notNull(taskFlow);
        Assert.notNull(minOperateNo);
        Assert.notNull(maxOperateNo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("taskFlow", taskFlow);
        paramMap.put("minOperateNo", minOperateNo);
        paramMap.put("maxOperateNo", maxOperateNo);
        this.tReportAccountFlowDao.removeByTaskFlow(paramMap);
        this.tReportAccountFlowDao.addAccountFlow(paramMap);
    }

}
