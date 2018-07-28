package com.edu.report.web.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.BusinessAttendanceMonthReportDao;
import com.edu.report.model.TBusinessAttendanceMonthReport;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.report.dao.BusinessAttendanceMonthReportDao;
import com.edu.report.model.TBusinessAttendanceMonthReport;
import com.edu.report.web.common.service.BusinessAttendanceMonthService;

/**
 * @ClassName: BusinessAttendanceMonthServiceImpl
 * @Description: 月度分类考勤表
 *
 */
@Service("businessAttendanceMonthService")
public class BusinessAttendanceMonthServiceImpl implements BusinessAttendanceMonthService {
	
	@Resource(name = "businessAttendanceMonthReportDao")
	private BusinessAttendanceMonthReportDao businessAttendanceMonthReportDao;

	@Override
	public List<TBusinessAttendanceMonthReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("yearMonth"), "月份不能为空！");
		
		String yearMonth = (String) param.get("yearMonth");
		String startDateStr = yearMonth + "-01";
		Date startDate = DateUtil.stringToDate(startDateStr, "yyyy-MM-dd");
		String endDateStr = DateUtil.getMonthStartAndEndDate(startDate)[1];
		param.put("start_date", startDateStr);
		param.put("end_date", endDateStr);
		
		return businessAttendanceMonthReportDao.queryReport(param);
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
		businessAttendanceMonthReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		businessAttendanceMonthReportDao.insertByTaskFlow(param);
	}

}
