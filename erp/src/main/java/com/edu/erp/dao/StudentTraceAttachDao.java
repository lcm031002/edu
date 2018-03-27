package com.edu.erp.dao;

import com.edu.erp.model.StudentTraceAttach;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "studentTraceAttachDao")
public interface StudentTraceAttachDao {

	StudentTraceAttach queryById(Long id) throws Exception;

	void addList(List<StudentTraceAttach> studentTraceAttachList) throws Exception;

	void deleteByTraceId(Long traceId) throws Exception;

	void deleteById(Long id) throws Exception;

}
