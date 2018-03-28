package com.edu.erp.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.common.service.RegionService;
import com.edu.erp.dao.RegionDao;
import com.edu.erp.model.TabRegion;

/***
 * Description ： 省市地区Service 接口实现
 * 
 * Author ：junli.zhang
 *
 * Date : 2014-08-29
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {

	@Resource(name = "regionDao")
	private RegionDao regionDao;
	
	/**
	 * 根据父级ID查询城市联动列表信息
	 * @param 	parentId					城市表父级ID
	 * @return	List<Map<String, String>>	城市联动列表信息
	 */
	@Override
	public List<Map<String, String>> queryRegionListByParentId(Long parentId)throws Exception{
		try {
			List<Map<String, String>> cityLinkList = new ArrayList<Map<String,String>>();
			List<TabRegion>  regionList = regionDao.queryRegionListByParentId(parentId);
			
			Map<String, String> cityLinkMap = new HashMap<String, String>();
			cityLinkMap.put("value", "-1");
			cityLinkMap.put("label", "请选择");
			cityLinkList.add(cityLinkMap);
			
			if(cityLinkList != null && cityLinkList.size() > 0)
			{
				for(TabRegion region : regionList)
				{
					cityLinkMap = new HashMap<String, String>();
					cityLinkMap.put("value", region.getRid().toString());
					cityLinkMap.put("label", region.getRname());
					cityLinkList.add(cityLinkMap);
				}
			}
			
			return cityLinkList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<TabRegion> list(Map<String, Object> paramMap)throws Exception{
		return regionDao.list(paramMap);
	}
}
