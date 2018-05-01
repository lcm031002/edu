package com.edu.report.web.common.service.impl;


import com.edu.report.framework.dao.StudentStatusReportDao;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.web.common.service.StudentStatusReportService;
import com.edu.report.framework.dao.StudentStatusReportDao;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.web.common.service.StudentStatusReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员报表服务实现类
 *
 * @author: linj
 * @create: 2018/3/2  10:00
 */
@Service("studentStatusReportService")
public class StudentStatusReportServiceImpl implements StudentStatusReportService {

    @Autowired
    private StudentStatusReportDao studentStatusReportDao;


    @Override
    public List<StudentStatusReport> queryStudentStatusReport(Map<String, Object> param) {
        return studentStatusReportDao.queryStudentStatusReport(param);
    }


    @Override
    public List<ReportStudentdetails> queryReportStudentdetails(Map<String, Object> param) {
        Map<String,Object> queryParam = new HashMap<String, Object>();
        Long buId = Long.parseLong(param.get("bu_id").toString());
        queryParam.put("buId",buId);
        Long productLine = studentStatusReportDao.queryProductLine(queryParam);
        param.put("productLine",productLine);
        return studentStatusReportDao.queryReportStudentdetails(param);
    }
}
