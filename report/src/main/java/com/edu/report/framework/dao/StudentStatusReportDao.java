package com.edu.report.framework.dao;



import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;

import java.util.List;
import java.util.Map;

/**
 * 学员状态表DAO
 *
 */
public interface StudentStatusReportDao {

     //学员状态统计列表
     List<StudentStatusReport> queryStudentStatusReport(Map<String,Object> param) ;


     List<ReportStudentdetails> queryReportStudentdetails(Map<String,Object> param);


     Long queryProductLine(Map<String,Object> param);
}
