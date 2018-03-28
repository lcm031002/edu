package com.edu.erp.common.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TabRegion;


/***
 * Description ： 省市地区Service 接口
 * 
 * Author ：junli.zhang
 *
 * Date : 2014-08-29
 */
public interface RegionService {

	/**
	 * 根据父级ID查询城市联动列表信息
	 * @param 	parentId					城市表父级ID
	 * @return	List<Map<String, String>>	城市联动列表信息
	 */
	List<Map<String, String>> queryRegionListByParentId(Long parentId)throws Exception;
	
	List<TabRegion> list(Map<String, Object> paramMap)throws Exception;
}
