package com.edu.report.web.common.service.impl;

import com.edu.report.framework.dao.AutoAttendanceReportDao;
import com.edu.report.model.TAutoAttendanceReport;
import com.edu.report.web.common.service.AutoAttendanceReportService;
import com.edu.report.framework.dao.AutoAttendanceReportDao;
import com.edu.report.model.TAutoAttendanceReport;
import com.edu.report.web.common.service.AutoAttendanceReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AutoAttendanceReportServiceImpl
 * @Description: 自动考勤报表
 *
 */
@Service("autoAttendanceReportService")
public class AutoAttendanceReportServiceImpl implements AutoAttendanceReportService {
	
	@Resource(name = "autoAttendanceReportDao")
	private AutoAttendanceReportDao autoAttendanceReportDao;


	@Override
	public List<TAutoAttendanceReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String) param.get("end_date"), "截止日期不能为空！");
		return autoAttendanceReportDao.queryReport(param);
	}

	@Override
	public void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		Assert.hasText(taskFlow, "任务批次不能为空！");
		Assert.notNull(minOperateNo, "最小序号不能为空！");
		Assert.notNull(maxOperateNo, "最大序号不能为空！");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskFlow", taskFlow);
		param.put("minOperateNo", minOperateNo);
		param.put("maxOperateNo", maxOperateNo);
		
		//先删除旧数据
		autoAttendanceReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		autoAttendanceReportDao.insertByTaskFlow(param);
	}

}
