package com.edu.report.web.wfd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.dao.TeacherAttendReportDao;
import com.edu.report.model.TTeacherAttendReport;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.dao.TeacherAttendReportDao;
import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TTeacherAttendReport;
import com.edu.report.web.wfd.service.WfdAttendanceService;

/**
 * @ClassName: PerformanceDetailsServiceImpl
 * @Description: 业绩明细服务实现
 *
 */
@Service(value = "wfdAttendanceService")
public class WfdAttendanceServiceImpl implements WfdAttendanceService {

	@Resource(name = "teacherAttendReportDao")
	private TeacherAttendReportDao teacherAttendReportDao;

	@Override
	public List<TTeacherAttendReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		return teacherAttendReportDao.queryReport(param);
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
		teacherAttendReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		teacherAttendReportDao.insertByTaskFlow(param);
	}
}
