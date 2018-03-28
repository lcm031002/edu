package com.edu.erp.dict.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseModuleUtils {
	
	/**
	 * 组装多对对数据 
	 * 
	 * @param major_id
	 * @param foreign_ids
	 * @return
	 * @throws Exception
	 */
	public synchronized static List<Map<String, Long>> refMajorForeign(Long major_id, String foreign_ids) throws Exception{
		if(foreign_ids == null)
			foreign_ids = "";
		String[] foreign_idArr = foreign_ids.split(",");
		Set<Long> ids = new HashSet<Long>();
		 
		// 防止重复
		for(String foreign_id : foreign_idArr){
			if(foreign_id.trim().length() == 0)
				continue; 
			ids.add(Long.parseLong(foreign_id)); 
		}
		
		List<Map<String, Long>> result = new ArrayList<Map<String,Long>>(ids.size());
		Map<String, Long> obj = null;
		
		for(Long foreign_id : ids){
			obj = new HashMap<String, Long>();
			obj.put("major_id", major_id);
			obj.put("foreign_id", foreign_id);
			result.add(obj);
		}
		return result;
	}
	
	/**
	 * 递归组织机构组装数据
	 * 
	 * @param major_id
	 * @param foreign_ids
	 * @return
	 * @throws Exception 
	 */
	public synchronized static List<Map<String, Long>> refMajorForeignOrgRecursion(Long major_id, String foreign_ids) throws Exception{
		if(foreign_ids == null)
			foreign_ids = ""; 
		String[] foreign_idArr = foreign_ids.split(",");
		Set<Long> ids = new HashSet<Long>();
		 
		for(String foreign_id : foreign_idArr){
			if(foreign_id.trim().length() == 0)
				continue; 
			ids.add(Long.parseLong(foreign_id)); 
			for(Long id : OrganizationUtils.getChildIds(Long.parseLong(foreign_id))){
				ids.add(id);
			}
		}
		
		List<Map<String, Long>> result = new ArrayList<Map<String,Long>>(ids.size());
		Map<String, Long> obj = null;
		
		for(Long foreign_id : ids){
			obj = new HashMap<String, Long>();
			obj.put("major_id", major_id);
			obj.put("foreign_id", foreign_id);
			result.add(obj);
		}
		return result;
	}
	
	
}
