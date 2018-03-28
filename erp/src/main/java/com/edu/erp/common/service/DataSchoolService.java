package com.edu.erp.common.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.DataSchool;
import com.github.pagehelper.Page;

public interface DataSchoolService {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<DataSchool> page(Page<DataSchool> page) throws Exception;
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
	List<DataSchool> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @param org_ids 校区
	 * @throws Exception
	 */
	void toAdd(DataSchool pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(DataSchool pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status) throws Exception;
	
}
