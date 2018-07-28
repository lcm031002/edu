package com.edu.report.web.common.service.impl;

import com.edu.report.framework.dao.TReportGxhStudentStatusDao;
import com.edu.report.model.TAutoAttendanceReport;
import com.edu.report.model.TReportGxhStudentStatus;
import com.edu.report.web.common.service.ReportGxhStudentStatusService;
import com.edu.report.framework.dao.TReportGxhStudentStatusDao;
import com.edu.report.model.TReportGxhStudentStatus;
import com.edu.report.web.common.service.ReportGxhStudentStatusService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportGxhStudentStatusServiceImpl
 * @Description: 学生状态报表
 *
 */
@Service("reportGxhStudentStatusService")
public class ReportGxhStudentStatusServiceImpl implements ReportGxhStudentStatusService {
	
	@Resource(name = "reportGxhStudentStatusDao")
	private TReportGxhStudentStatusDao reportGxhStudentStatusDao;


	@Override
	public List<TReportGxhStudentStatus> queryForBu(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		return reportGxhStudentStatusDao.queryForBu(param);
	}

	@Override
	public List<TReportGxhStudentStatus> queryForBranch(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		return reportGxhStudentStatusDao.queryForBranch(param);
	}

	@Override
	public List<TReportGxhStudentStatus> queryForStudents(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		return reportGxhStudentStatusDao.queryForStudents(param);
	}

}
