package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabDataDevice;
import com.github.pagehelper.Page;

/**
 * @ClassName: DeviceDao
 * @Description:
 * 
 */
@Repository(value = "deviceDao")
public interface DeviceDao {

	List<TabDataDevice> selectForList(Map<String, Object> param) throws Exception;
	
	Page<TabDataDevice> selectForPage(Map<String, Object> param) throws Exception;
	
	TabDataDevice selectById(String id) throws Exception;
	
	void insert(TabDataDevice pojo) throws Exception;

	Integer update(TabDataDevice pojo) throws Exception;

	void changeStatus(Map<String, Object> param) throws Exception;
}
