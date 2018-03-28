package com.edu.erp.common.service;

import com.edu.erp.model.DataSchool;
import com.edu.erp.model.TPScheduleTime;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TpScheduleTimeService {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TPScheduleTime> page(Page<TPScheduleTime> page) throws Exception;
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> queryPage(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TPScheduleTime> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toAdd(TPScheduleTime pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(TPScheduleTime pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;
	
}
