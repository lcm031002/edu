package com.edu.erp.student.service.impl;

import com.edu.erp.dao.StudentTraceDetailDao;
import com.edu.erp.model.StudentTraceDetail;
import com.edu.erp.student.service.StudentTraceDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentTraceDetailService")
public class StudentTraceDetailServiceImpl implements StudentTraceDetailService {

	@Resource(name = "studentTraceDetailDao")
	private StudentTraceDetailDao studentTraceDetailDao;

	@Override
	public void addList(List<StudentTraceDetail> studentTraceDetailList) throws Exception {
		this.studentTraceDetailDao.addList(studentTraceDetailList);
	}

	@Override
	public void deleteByTraceId(Long traceId) throws Exception {
		this.studentTraceDetailDao.deleteByTraceId(traceId);
	}
}
