package com.edu.erp.student.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.TCourseListeningDao;
import com.edu.erp.model.TCourseListening;
import com.edu.erp.student.service.TCourseListeningService;
import com.github.pagehelper.Page;

/**
 * @ClassName: TCourseListeningServiceImpl
 * @Description: 课程试听服务
 *
 */
@Service("tCourseListeningService")
public class TCourseListeningServiceImpl implements TCourseListeningService {
	private static final Logger log = Logger.getLogger(TCourseListeningServiceImpl.class);
	
	@Resource(name = "tCourseListeningDao")
	private TCourseListeningDao tCourseListeningDao;

	@Override
	public Page<Map<String,Object>> queryListeningPage(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		Assert.notEmpty(param);
		Assert.notNull(param.get("student_id"), "学生Id不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.notNull(param.get("business_type"), "业务类型不能为空！");
//		Assert.notNull(param.get("start_date"));
//		Assert.notNull(param.get("end_date"));
		return tCourseListeningDao.queryListeningPage(param);
	}

	@Override
	public void insertTCourseListening(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		Assert.notEmpty(param);
		Assert.notNull(param.get("BRANCH_ID"));
		Assert.notNull(param.get("STUDENT_ID"));
		Assert.notNull(param.get("COURSE_ID"));
		Assert.notNull(param.get("COURSE_TIMES"));
		Assert.notNull(param.get("LISTENING_DATE"));
//		Assert.notNull(param.get("PAY_STATUS"));
		
		Assert.notNull(param.get("RECORDER"));
		Assert.notNull(param.get("RECORD_TIME"));
		Assert.notNull(param.get("UPDATER"));
		Assert.notNull(param.get("UPDATE_TIME"));
//		Assert.notNull(param.get("REMARK"));
		
		Integer count = tCourseListeningDao.isExist(param);
		if(count != null && count > 0) {
			throw new Exception("学生已试听该课次，不能重复试听！");
		}
		tCourseListeningDao.insertTCourseListening(param);
	}

	@Override
	public void updateTCourseListening(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		Assert.notEmpty(param);
		Assert.notNull(param.get("STUDENT_ID"));
		Assert.notNull(param.get("COURSE_ID"));
		Assert.notNull(param.get("PAY_STATUS"));
		
		Assert.notNull(param.get("UPDATER"));
		Assert.notNull(param.get("UPDATE_TIME"));
		tCourseListeningDao.updateTCourseListening(param);
	}

	@Override
	public List<Map<String, Object>> queryTCourseListeningSc(
			Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("STUDENT_ID"));
		Assert.notNull(param.get("COURSE_ID"));
		return tCourseListeningDao.queryTCourseListeningSc(param);
	}

	@Override
	public List<TCourseListening> queryListeningList(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "查询参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String)param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String)param.get("end_date"), "截止日期不能为空！");
		
		return tCourseListeningDao.selectListeningList(param);
	}

}
