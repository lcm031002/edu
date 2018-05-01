package com.edu.report.web.common.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.web.common.service.StudentStatusReportService;
import com.edu.report.model.ReportStudentdetails;
import com.edu.report.model.StudentStatusReport;
import com.edu.report.util.ReportWriteExcelHandler;
import com.edu.report.web.common.service.StudentStatusReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import com.edu.report.util.ReportWriteExcelHandler;
/**
 * 学员状态报表控制层
 *
 * @author: linj
 * @create: 2018/3/2  10:34
 */
@Controller
@RequestMapping("/common")
public class StudentStatusReportController {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ReportGxhStudentStatusController.class);
    @Resource(name="studentStatusReportService")
    private StudentStatusReportService studentStatusReportService;

    @RequestMapping(value = {"/studentStatusReport/queryStudentStatusReport"},method = RequestMethod.GET,headers = {"Accept=application/json"})
    @ResponseBody
    public Map<String,Object> queryStudentStatusReport(HttpServletRequest request, HttpServletResponse response){
           Map<String,Object> resultMap = new HashMap<String, Object>();
           resultMap.put("error",false);
        try {
            Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
            List<StudentStatusReport> studentStatusReportList = studentStatusReportService.queryStudentStatusReport(queryParam);
            resultMap.put("data", studentStatusReportList);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", "查询失败: " + e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping(value = {"/studentStatusReport/queryStudentStatusReport/queryReportStudentdetails"},method = RequestMethod.GET,headers = {"Accept=application/json"})
    @ResponseBody
    public Map<String,Object> queryReportStudentdetails(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error",false);
        try {
            Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
            List<ReportStudentdetails> reportStudentdetailsList= studentStatusReportService.queryReportStudentdetails(queryParam);
            resultMap.put("data", reportStudentdetailsList);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", "查询失败: " + e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping(value = {"/studentStatusReport/queryStudentStatusReport/outputExcel"},method = RequestMethod.GET,headers ={"Accept=application/json"})
    @ResponseBody
    public Map<String,Object> outputExcel(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        String rootPath = request.getSession().getServletContext().getRealPath("//");
        // 模板文件名
        String templateFileName = "al_student_status.xlsx";
        // 临时文件名
        String tempFileName = "艾伦学员状态报表" + "【#bu_name#】_"
                + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
        try {
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request, "p_");
            List<StudentStatusReport> studentStatusReportList = studentStatusReportService.queryStudentStatusReport(paramMap);

            if(CollectionUtils.isEmpty(studentStatusReportList) || studentStatusReportList.size() < 2) {
                throw new Exception("没有数据可导出！");
            }
            tempFileName = tempFileName.replace("#bu_name#", studentStatusReportList.get(0).getBranchName());

            // 导出文件目录
            String outFilePath = ReportWriteExcelHandler.writeDataToExcelBySxssf(studentStatusReportList, rootPath,
                    templateFileName, tempFileName, 1);
            log.debug("生成导出临时文件：" + outFilePath);

            resultMap.put("data", tempFileName);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", "导出失败: " + e.getMessage());
            log.error("error found ,see:", e);
        }

        return resultMap;
    }

}
