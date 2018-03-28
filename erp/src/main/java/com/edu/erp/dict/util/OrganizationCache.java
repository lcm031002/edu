package com.edu.erp.dict.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.erp.dao.OrganizationDao;
import com.edu.erp.model.OrganizationInfo;


/**
 * 组织机构缓存
 * 
 * @author wCong
 */
public class OrganizationCache {
	
	private static OrganizationDao organizationDao;
	static{
		organizationDao = ApplicationContextUtil.getBean("organizationDao");
	}
	
	private List<OrganizationInfo> cacheList = new ArrayList<OrganizationInfo>();
	
	/**
	 * 设置缓存
	 * 场景 ： 登录
	 * 
	 * @param orgList
	 */
	@SuppressWarnings("all")
	public void fillCache() throws Exception{
		cacheList = organizationDao.queryList(new HashMap());
	}
	
	/**
	 * 根据ID获取组织机构
	 * 
	 * @param org_id
	 * @return
	 * @throws Exception
	 */
	public OrganizationInfo getOrgById(Long org_id) throws Exception{
		OrganizationInfo orgById = null;
		for(OrganizationInfo cache : cacheList){
			if(org_id.longValue() == cache.getId().longValue()){
				orgById = cache;
				break;
			}
		}
		return orgById;
	}
	
	public List<OrganizationInfo> getOrgByParentId(Long parent_id) throws Exception{
		List<OrganizationInfo> orgByParent = new ArrayList<OrganizationInfo>();
		for(OrganizationInfo cache : cacheList){
			if(parent_id.longValue() == cache.getParent_id().longValue()){
				orgByParent.add(cache);
			}
		}
		return orgByParent;
	}
	
	/**
	 * 获取特点类型的子对象
	 * 
	 * @param parent_id
	 * @return
	 * @throws Exception
	 */
	public List<OrganizationInfo> getChildOrgById(Long org_id, Integer org_type) throws Exception{
		List<OrganizationInfo> org = new ArrayList<OrganizationInfo>();
		for(OrganizationInfo cache : cacheList){
			if(org_id.longValue() == cache.getParent_id().longValue()){
				if(org_type != null && org_type.intValue() != 0){
					if(org_type.intValue() == cache.getOrg_type().intValue())
						org.add(cache);
				}else
					org.add(cache);
				for(OrganizationInfo cc: getChildOrgById(cache.getId(), org_type)){
					org.add(cc);
				}
			}
		}
		return org;
	}
	
	private OrganizationCache(){
		
	}
	static class SingletonHolder {
		static OrganizationCache instance = new OrganizationCache();
	}
	public static OrganizationCache getInstance() {
		return SingletonHolder.instance;
	}
}
