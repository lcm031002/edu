package com.edu.report.web.common.service;


import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;

import java.util.List;
import java.util.Map;

/**
 * 学员状态报表服务
 *
 */
public interface StudentStatusReportService {

    //学员状态统计列表
    List<StudentStatusReport> queryStudentStatusReport(Map<String,Object> param) ;


    //学员状态详细列表
    List<ReportStudentdetails> queryReportStudentdetails(Map<String,Object> param);
}
