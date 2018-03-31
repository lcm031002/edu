package com.edu.erp.common.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TabDataDevice;
import com.github.pagehelper.Page;

/**
 * @ClassName: DeviceService
 * @Description:
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月8日 下午2:29:06
 * 
 */
public interface DeviceService {

	/**
	 * 分页查询
	 *
	 * @return
	 * @throws Exception
	 */
	Page<TabDataDevice> queryForPage(Map<String,Object> param) throws Exception;
	
	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param 动态参数
	 * @return
	 * @throws Exception
	 */
	List<TabDataDevice> queryForList(Map<String, Object> param) throws Exception;
	
	/**
	 * @param id
	 *            数据ID
	 * @return 
	 * @throws Exception
	 */
	TabDataDevice queryById(String id) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toAdd(TabDataDevice pojo) throws Exception;
	
	/**
	 * 根据ID修改
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void toUpdate(TabDataDevice pojo) throws Exception;
	
	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @param updateUser
	 * @throws Exception
	 */
	void toChangeStatus(String ids, Integer status, Long updateUser) throws Exception;
}
