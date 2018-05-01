package com.edu.report.web.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.framework.dao.AttendanceReportDao;
import com.edu.report.model.TAttendanceReport;
import com.edu.report.web.common.service.AttendanceReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.AttendanceReportDao;
import com.edu.report.model.TAttendanceReport;
import com.edu.report.web.common.service.AttendanceReportService;

/**
 * @ClassName: AttendanceReportServiceImpl
 * @Description: 考勤消耗表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月15日 下午7:27:00
 *
 */
@Service("attendanceReportService")
public class AttendanceReportServiceImpl implements AttendanceReportService {
	
	@Resource(name = "attendanceReportDao")
	private AttendanceReportDao attendanceReportDao;

	@Override
	public List<TAttendanceReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String) param.get("end_date"), "截止日期不能为空！");
		return attendanceReportDao.queryReport(param);
	}

	@Override
	public void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		Assert.hasText(taskFlow, "任务批次不能为空！");
		Assert.notNull(minOperateNo, "最小序号不能为空！");
		Assert.notNull(maxOperateNo, "最大序号不能为空！");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("task_flow", taskFlow);
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		
		//先删除旧数据
		attendanceReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		attendanceReportDao.insertByTaskFlow(param);
	}

}
