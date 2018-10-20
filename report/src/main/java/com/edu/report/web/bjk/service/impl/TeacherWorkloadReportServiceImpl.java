package com.edu.report.web.bjk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.report.model.TStudentInfo;
import com.edu.report.framework.dao.TeacherWorkloadReportDao;
import com.edu.report.model.TStudentInfo;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.report.framework.dao.TeacherWorkloadReportDao;
import com.edu.report.model.TTeacherWorkloadReport;
import com.edu.report.web.bjk.service.TeacherWorkloadReportService;

/**
 * @ClassName: TeacherWorkloadReportServiceImpl
 * @Description: 培英班教师工作量
 *
 */
@Service("teacherWorkloadReportService")
public class TeacherWorkloadReportServiceImpl implements TeacherWorkloadReportService {
	
	@Resource(name = "teacherWorkloadReportDao")
	private TeacherWorkloadReportDao teacherWorkloadReportDao;

	@Override
	public List<TTeacherWorkloadReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String) param.get("end_date"), "截止日期不能为空！");
		
		//test
//		param.put("bu_id", 17);
//		param.put("branch_id", 73);
//		param.put("isCourseTime", "false");
//		param.put("start_date", "2015-07-23");
//		param.put("end_date", "2015-07-23");
		
		return teacherWorkloadReportDao.queryReport(param);
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
		teacherWorkloadReportDao.deleteByTaskFlow(param);
		//重新插入新数据
		teacherWorkloadReportDao.insertByTaskFlow(param);
		//更新 试听学生数/试听拒缴学生数
		//teacherWorkloadReportDao.updateListeningStudentsByTaskFlow(param);
	}

	@Override
	public List<TStudentInfo> queryForOrderStudents(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("course_id"), "课程不能为空！");
		Assert.notNull(param.get("course_times"), "课程不能为空！");

		return teacherWorkloadReportDao.queryForOrderStudents(param);
	}

	@Override
	public List<TStudentInfo> queryForAttendanceStudents(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("course_id"), "课程不能为空！");
		Assert.notNull(param.get("course_times"), "课程不能为空！");
		Assert.hasText((String) param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String) param.get("end_date"), "截止日期不能为空！");

		return teacherWorkloadReportDao.queryForAttendanceStudents(param);
	}

}
