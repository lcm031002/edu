package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.StudentTraceInfo;
import com.github.pagehelper.Page;

/**
 * @ClassName: StudentTraceInfo
 * @Description: 学员信息服务
 * 
 */

@Repository(value = "studentTraceInfoDao")
public interface StudentTraceInfoDao {
	
	Page<StudentTraceInfo> selectForPage(Map<String, Object> param) throws Exception;

	StudentTraceInfo queryById(Long id) throws Exception;

	void add(StudentTraceInfo pojo) throws Exception;

	void update(StudentTraceInfo pojo) throws Exception;

	List<Map<String, Object>> queryCourseInfo(Map<String, Object> paramMap) throws Exception;

}
