package com.edu.erp.dao;

import com.edu.erp.model.StudentTraceDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "studentTraceDetailDao")
public interface StudentTraceDetailDao {

	void addList(List<StudentTraceDetail> studentTraceDetailList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

}
