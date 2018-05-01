package com.edu.report.web.bjk.service.impl;

import com.edu.report.dao.CourseAnalysisReportDao;
import com.edu.report.web.bjk.service.CourseAnalysisReportService;
import com.edu.report.dao.CourseAnalysisReportDao;
import com.edu.report.web.bjk.service.CourseAnalysisReportService;
import com.github.pagehelper.Page;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("courseAnalysisReportService")
public class CourseAnalysisReportServiceImpl implements CourseAnalysisReportService {

    @Resource(name = "courseAnalysisReportDao")
    private CourseAnalysisReportDao courseAnalysisReportDao;

    @Override
    public Page<Map<String, Object>> queryCourseAnalysisReport(Map<String, Object> paramMap) throws Exception {
        return this.courseAnalysisReportDao.queryCourseAnalysisReport(paramMap);
    }
}
