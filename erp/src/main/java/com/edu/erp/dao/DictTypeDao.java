package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TpDictType;
import com.github.pagehelper.Page;

public interface DictTypeDao {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<TpDictType> page(Page<TpDictType> page) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TpDictType> list(Map<String, Object> param) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toAdd(TpDictType pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toUpdate(TpDictType pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;

}
