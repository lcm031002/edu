package com.edu.erp.student.service;

import com.edu.erp.model.StudentTraceAttach;

import java.util.List;

public interface StudentTraceAttachService {

	StudentTraceAttach queryById(Long id) throws Exception;

	void addList(List<StudentTraceAttach> studentTraceAttachList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

	void deleteById(Long id) throws Exception;

}
