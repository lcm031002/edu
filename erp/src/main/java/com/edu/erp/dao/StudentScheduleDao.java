package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TAttendance;

@Repository(value = "studentScheduleDao")
public interface StudentScheduleDao {

	List<TAttendance> queryYDYSchedule(Map<String, Object> queryParam) throws Exception;
	
	List<TAttendance> queryBJKSchedule(Map<String, Object> queryParam) throws Exception;
}
