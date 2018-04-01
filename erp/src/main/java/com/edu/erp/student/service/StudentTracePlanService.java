package com.edu.erp.student.service;

import com.edu.erp.model.StudentTracePlan;

import java.util.List;

public interface StudentTracePlanService {

	void addList(List<StudentTracePlan> studentTracePlanList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

}
