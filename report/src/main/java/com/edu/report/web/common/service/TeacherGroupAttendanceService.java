package com.edu.report.web.common.service;

import com.edu.report.model.TeacherGroupAttendanceReport;
import com.edu.report.model.TeacherGroupAttendanceReport;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TeacherGroupAttendanceService
 * @Description: 培英班教研组统计
 * @author ohs ohs@klxuexi.org
 * @date 2017年11月7日 下午7:25:24
 *
 */
public interface TeacherGroupAttendanceService {

	List<TeacherGroupAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception;
}
