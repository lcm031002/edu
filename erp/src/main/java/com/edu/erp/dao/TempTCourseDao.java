package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TempTCourse;

@Repository(value = "tempTCourseDao")
public interface TempTCourseDao {
	
	void insertList(List<TempTCourse> tempTCourseList);

	/**
	 * 某些信息导入的是中文信息，需要根据中文信息更新相应的Id值
	 * 根据批次号更新整个批次的代码值
	 * @param paramMap
	 */
	void setIdByCode(Map<String, Object> paramMap);
	
	
	/**
	 * 列表查询
	 * @param paramMap 批次号
	 */
	List<TempTCourse> selectForList(Map<String, Object> paramMap);
	
	void deleteByBatchNo(String batchNo);
}
