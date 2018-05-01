package com.edu.report.framework.dao;

import com.edu.report.model.TAutoAttendanceReport;
import com.edu.report.model.TAutoAttendanceReport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AutoAttendanceReportDao
 * @Description: 自动考勤报表DAO
 * @author ohs ohs@klxuexi.org
 * @date 2017年9月12日 下午2:42:22
 *
 */
@Repository(value = "autoAttendanceReportDao")
public interface AutoAttendanceReportDao {

	List<TAutoAttendanceReport> queryReport(Map<String, Object> param) throws Exception;
	
	void deleteByTaskFlow(Map<String, Object> param) throws Exception;
	
	void insertByTaskFlow(Map<String, Object> param) throws Exception;
}
