package com.edu.erp.student.service;

import com.edu.erp.model.StudentTraceDetail;

import java.util.List;

public interface StudentTraceDetailService {

	void addList(List<StudentTraceDetail> studentTraceDetailList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

}
