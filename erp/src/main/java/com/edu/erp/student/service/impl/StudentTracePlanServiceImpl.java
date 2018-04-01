package com.edu.erp.student.service.impl;

import com.edu.erp.dao.StudentTracePlanDao;
import com.edu.erp.model.StudentTracePlan;
import com.edu.erp.student.service.StudentTracePlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentTracePlanService")
public class StudentTracePlanServiceImpl implements StudentTracePlanService {

	@Resource(name = "studentTracePlanDao")
	private StudentTracePlanDao studentTracePlanDao;

	@Override
	public void addList(List<StudentTracePlan> studentTracePlanList) throws Exception {
		this.studentTracePlanDao.addList(studentTracePlanList);
	}

	@Override
	public void deleteByTraceId(Long traceId) throws Exception {
		this.studentTracePlanDao.deleteByTraceId(traceId);
	}
}
