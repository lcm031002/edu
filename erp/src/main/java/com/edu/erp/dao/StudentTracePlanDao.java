package com.edu.erp.dao;

import com.edu.erp.model.StudentTracePlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "studentTracePlanDao")
public interface StudentTracePlanDao {

	void addList(List<StudentTracePlan> studentTracePlanList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

}
