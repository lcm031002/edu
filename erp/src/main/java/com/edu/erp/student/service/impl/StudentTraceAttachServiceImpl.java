package com.edu.erp.student.service.impl;

import com.edu.common.qinniu.QiNiuCoreCall;
import com.edu.erp.dao.StudentTraceAttachDao;
import com.edu.erp.model.StudentTraceAttach;
import com.edu.erp.student.service.StudentTraceAttachService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentTraceAttachService")
public class StudentTraceAttachServiceImpl implements StudentTraceAttachService {
	private static final Logger log = Logger.getLogger(StudentTraceAttachServiceImpl.class);

	@Resource(name = "studentTraceAttachDao")
	private StudentTraceAttachDao studentTraceAttachDao;

	@Override
	public StudentTraceAttach queryById(Long id) throws Exception {
		return this.studentTraceAttachDao.queryById(id);
	}

	@Override
	public void addList(List<StudentTraceAttach> studentTraceAttachList) throws Exception {
		this.studentTraceAttachDao.addList(studentTraceAttachList);
	}

	@Override
	public void deleteByTraceId(Long traceId) throws Exception {
		this.studentTraceAttachDao.deleteByTraceId(traceId);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		StudentTraceAttach studentTraceAttach = this.queryById(id);
		if (studentTraceAttach != null) {
			try {
				QiNiuCoreCall.getInstance().delete(studentTraceAttach.getFileName());
			} catch (Exception e) {
				log.error(e);
			}
			this.studentTraceAttachDao.deleteById(id);
		}
	}
}
