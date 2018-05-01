package com.edu.report.web.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.AttendanceMonthReportDao;
import com.edu.report.model.TAttendanceMonthReport;
import com.edu.report.web.common.service.AttendanceMonthService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.report.dao.AttendanceMonthReportDao;
import com.edu.report.model.TAttendanceMonthReport;
import com.edu.report.web.common.service.AttendanceMonthService;

/**
 * @ClassName: AttendanceMonthServiceImpl
 * @Description: 月度消耗表
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年6月16日 上午11:33:20
 *
 */
@Service("attendanceMonthService")
public class AttendanceMonthServiceImpl implements AttendanceMonthService {
	
	@Resource(name = "attendanceMonthReportDao")
	private AttendanceMonthReportDao attendanceMonthReportDao;

	@Override
	public List<TAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("yearMonth"), "月份不能为空！");
		
		String yearMonth = (String) param.get("yearMonth");
		String startDateStr = yearMonth + "-01";
		Date startDate = DateUtil.stringToDate(startDateStr, "yyyy-MM-dd");
		String endDateStr = DateUtil.getMonthStartAndEndDate(startDate)[1];
		param.put("start_date", startDateStr);
		param.put("end_date", endDateStr);
		
		return attendanceMonthReportDao.queryReport(param);
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
		attendanceMonthReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		attendanceMonthReportDao.insertByTaskFlow(param);
	}

}
