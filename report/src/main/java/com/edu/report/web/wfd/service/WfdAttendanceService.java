/**  
 * @Title: PerformanceDetailsService.java
 * @Package com.edu.report.web.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月27日 下午5:19:17
 * @version KLXX ERPV4.0  
 */
package com.edu.report.web.wfd.service;

import java.util.List;
import java.util.Map;

import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TTeacherAttendReport;
import com.edu.report.model.TTeacherAttendReport;

/**
 * @ClassName: TeacherWorkloadReportService
 * @Description: 晚辅导教师考勤表
 * @author zenglw zenglw@klxuexi.org
 * @date 2017年5月17日 上午5:17:17
 * 
 */
public interface WfdAttendanceService {

	List<TTeacherAttendReport> queryReport(Map<String, Object> param) throws Exception;

	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
