package com.edu.report.web.common.service;

import com.edu.report.model.TAutoAttendanceReport;
import com.edu.report.model.TAutoAttendanceReport;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AutoAttendanceReportService
 * @Description: 自动考勤报表
 * @author ohs ohs@klxuexi.org
 * @date 2017年9月12日 下午7:25:24
 *
 */
public interface AutoAttendanceReportService {

	List<TAutoAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
