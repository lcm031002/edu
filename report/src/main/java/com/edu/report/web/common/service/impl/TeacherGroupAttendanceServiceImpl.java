package com.edu.report.web.common.service.impl;

import com.edu.report.framework.dao.TeacherGroupAttendanceDao;
import com.edu.report.model.TAttendanceReport;
import com.edu.report.model.TeacherGroupAttendanceReport;
import com.edu.report.web.common.service.TeacherGroupAttendanceService;
import com.edu.report.framework.dao.TeacherGroupAttendanceDao;
import com.edu.report.model.TeacherGroupAttendanceReport;
import com.edu.report.web.common.service.TeacherGroupAttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TeacherGroupAttendanceServiceImpl
 * @Description: 厦门培英班教研组统计
 * @author ohs ohs@klxuexi.org
 * @date 2017年11月7日 下午7:27:00
 *
 */
@Service("teacherGroupAttendanceService")
public class TeacherGroupAttendanceServiceImpl implements TeacherGroupAttendanceService {
	
	@Resource(name = "teacherGroupAttendanceDao")
	private TeacherGroupAttendanceDao teacherGroupAttendanceDao;

	@Override
	public List<TeacherGroupAttendanceReport> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String) param.get("course_date"), "上课日期不能为空！");
		return teacherGroupAttendanceDao.queryReport(param);
	}

	@Override
	public void updateReport(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
		Assert.hasText(taskFlow, "任务批次不能为空！");
		Assert.notNull(minOperateNo, "最小序号不能为空！");
		Assert.notNull(maxOperateNo, "最大序号不能为空！");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("task_flow", taskFlow);

		//先删除旧数据
		teacherGroupAttendanceDao.deleteByTaskFlow(param);
		//重新插入新数据
		teacherGroupAttendanceDao.insertByTaskFlow(param);
	}

}
