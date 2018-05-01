/**  
 * @Title: TPerformanceDetailsTask.java
 * @Package com.edu.report.tasks.common
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月24日 下午4:42:02
 * @version KLXX ERPV4.0  
 */
package com.edu.report.framework.tasks.wfd;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.wfd.service.WfdAttendanceService;
import com.edu.report.api.BaseReportTask;
import com.edu.report.web.wfd.service.WfdAttendanceService;

/**
 * @ClassName: WfdAttendTeacherReportTask
 * @Description: 晚辅导教师考勤表
 * @author zenglw zenglw@klxuexi.org
 * @date 2017年5月17日 上午8:53:02
 * 
 */
public class WfdAttendTeacherReportTask extends BaseReportTask {
	//private static final String TABLE = "T_ATTENDANCE_HT";如果配置成T_ATTENDANCE_HT那么在t_task_table_result表中会和班级课教师，一对一教师考勤发生冲突，
	private static final String TABLE = "T_WFDTEACHER_ATTEND_REPORT";
	private static final String TABLE_KEY = "ATTEND_DATE";
	private static final String TABLE_TYPE = "INCREMENT";

	private WfdAttendanceService wfdAttendanceService = (WfdAttendanceService) ApplicationContextUtil
			.getBean("wfdAttendanceService");

	@Override
	public void run(String taskId,String taskFlow, Long pageSize, String startId) throws Exception {
		setTableInfo(TABLE, TABLE_KEY, TABLE_TYPE);
		calculateOperateNo( taskId,taskFlow, pageSize, startId);
		// 更新报表数据
		wfdAttendanceService.updateReport(taskFlow, minOperateNo, maxOperateNo);
	}

}
