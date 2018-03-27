package com.edu.erp.dao;

import java.util.Map;

import com.github.pagehelper.Page;

public interface OrderDao {

	/**
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Map<String,Object>> page(Map<String, Object> param) throws Exception;
}
