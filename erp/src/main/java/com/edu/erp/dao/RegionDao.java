package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabRegion;

/***
 * Description ： 省市地区DAO 接口
 *
 */
@Repository(value = "regionDao")
public interface RegionDao {

	/**
	 * 根据父级ID查询出城市相关信息列表
	 * @param 	parentId			城市表父级ID
	 * @return	List<TabGbsRegion>	城市相关信息列表
	 */
	List<TabRegion> queryRegionListByParentId(Long parentId)throws Exception;
	
	List<TabRegion> list(Map<String, Object> paramMap)throws Exception;
}
