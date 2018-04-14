package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.Grade;
import com.github.pagehelper.Page;

public interface DataGradeService {
	
	/**
	 * 查询分页效果
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Grade> queryPage(Map<String, Object> param) throws Exception;
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<Grade> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	long toAdd(Grade pojo) throws Exception;
	void toAddBuRel(Map<String,Object> data) throws Exception ;
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(Grade pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 *
	 * @throws Exception
	 */
	void deleteData(Map<String,Object> param) throws Exception;
	
}
