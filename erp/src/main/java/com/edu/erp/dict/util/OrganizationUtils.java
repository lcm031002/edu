package com.edu.erp.dict.util;

import java.util.ArrayList;
import java.util.List;

import com.edu.erp.model.OrganizationInfo;

/**
 * 
 * @author wCong
 *
 */
@SuppressWarnings("static-access")
public class OrganizationUtils extends BaseModuleUtils{
	
	private static OrganizationCache organizationDao;
	
	static{
		organizationDao = OrganizationCache.getInstance().getInstance();
	}
	
	/**
	 * 获取所有子节点IDS(不含自身)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static synchronized List<Long> getChildIds(Long id) throws Exception{
		return getChildIds(id, null);
	}
	
	/**
	 * 获取字符串格式所有子节点IDS(不含自身)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getChildStrIds(Long id) throws Exception{
		return getChildStrIds(id, null);
	}
	
	/**
	 * 获取特定类型字符串格式所有子节点IDS(不含自身)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getChildStrIds(Long id, Integer org_type) throws Exception{
		List<Long> ids = getChildIds(id, org_type);
		String str_ids = "";
		for(Long d : ids){
			if(str_ids.length() > 0)
				str_ids += ",";
			str_ids += d;
		}
		return str_ids;
	}
	
	/**
	 * 获取特定类型所有字节点IDS(不含自身)
	 * 
	 * @param id
	 * @param org_type
	 * @return
	 * @throws Exception
	 */
	public static synchronized List<Long> getChildIds(Long id, Integer org_type) throws Exception{
		List<Long> ids = new ArrayList<Long>();
		List<OrganizationInfo> pis = organizationDao.getOrgByParentId(id);
		for(OrganizationInfo o : pis){
			if(org_type != null && org_type != 0){
				if(org_type == o.getOrg_type())
					ids.add(o.getId());
			}else
				ids.add(o.getId());
			List<Long> addIds = getChildIds(o.getId(), org_type);
			for(Long add : addIds){
				ids.add(add);
			}
		}
		return ids;
	}
	
	/**
	 * 获取所有子节点 字符串格式IDS(含自身)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getChildAndSelfStrIds(Long id) throws Exception{
		StringBuffer buff = new StringBuffer();
		buff.append(id);
		List<OrganizationInfo> pis = organizationDao.getOrgByParentId(id);
		for(OrganizationInfo o : pis){
			buff.append("," + o.getId());
			List<Long> addIds = getChildIds(o.getId());
			for(Long add : addIds){
				buff.append("," + add);
			}
		}
		
		String ret = buff.toString();
		if(ret.startsWith(",")){
			ret = ret.substring(1, ret.length());
		}
		return ret;
	}
	
	/**
	 * 拼接JSON树形字符串
	 * 
	 * @param orgInfos
	 * @param emptyIds
	 * @return
	 */
	public static synchronized String jsonTreeByOrg(List<OrganizationInfo> orgInfos, String emptyIds){
		StringBuffer bf = new StringBuffer();
		if(emptyIds == null)
			emptyIds = "";
		
		String[] emptyIdArr = emptyIds.split(",");
		boolean checked = false;
		
		bf.append("[");
		for(OrganizationInfo org : orgInfos){
			
			for(String emptyId : emptyIdArr){
				if(emptyId.trim().length() == 0)
					continue;
				if(emptyId.equalsIgnoreCase(String.valueOf(org.getId())))
					checked = true;	
			}
			bf.append("  {\n")
			  .append("  \"id\":" + org.getId() + ",\n")	
			  .append("  \"text\":\"" + org.getOrg_name() + "\",\n")	
			  .append("  \"state\":\"" + "closed" + "\",\n")	
			  .append("  \"checked\":" + checked + ",\n")	
			  .append("  \"attributes\":{")
			  .append("  \"org_name\":\"" + org.getOrg_name() + "\",\n")
			  .append("  \"product_line\":\"" + org.getProduct_line() + "\",\n")
			  .append("  \"sort_number\":\"" + org.getSort_num() + "\",\n")
			  .append("  \"org_type\":\"" + org.getOrg_type() + "\"\n")
			  .append("  \n}")
			  .append("  \n}");
			bf.append("  ,");
			checked = false;
		}
		bf.append("]");
		String ret = bf.toString();
		if(bf.length() > 2){
			ret = bf.substring(0, bf.length() - 2) + "]";
		}
		return ret;
	}
	
	/**
	 * 组织机构下拉框
	 * 
	 * @param list
	 * @param emptyIds
	 * @return
	 */
	public static synchronized StringBuffer jsonComboByOrg(List<OrganizationInfo> list, String emptyIds){
		StringBuffer bf = new StringBuffer();
		if(emptyIds == null)
			emptyIds = "";
		
		String[] emptyIdArr = emptyIds.split(",");
		int size = 0;
		boolean checked = false;
		
		bf.append("[");
		for(OrganizationInfo m : list){
			for(String emptyId : emptyIdArr){
				if(emptyId.trim().length() == 0)
					continue;
				if(emptyId.equalsIgnoreCase(String.valueOf(m.getId())))
					checked = true;	
			}
			
			bf.append("{\n")
			  .append("\"id\":" + m.getId() + ",\n")
			  .append("\"text\":\"" + m.getOrg_name() + "\",\n")
			  .append("\"checked\":" + checked + "\n")
			  .append("}");
			
			if(size < (list.size() -1))
				bf.append(" ,");
			checked = false;
			size ++ ;
		}
		
		bf.append("]");
		
		return bf;
	}
	
	/**
	 * 权限组织过滤
	 * 
	 * @param srcOrgList
	 * @param perOrgList
	 * @return
	 */
	public static List<OrganizationInfo> perOrgFilter(List<OrganizationInfo> srcOrgList, List<OrganizationInfo> perOrgList) throws Exception{
		List<OrganizationInfo> hasOrgList = new ArrayList<OrganizationInfo>();
		if(perOrgList == null){
			return hasOrgList;
		}
		for(OrganizationInfo srcOrg : srcOrgList){
			boolean isAdd = false;
			Long srcId = srcOrg.getId();
			List<Long> srcidList = OrganizationUtils.getChildIds(srcId);
			for(OrganizationInfo perOrg : perOrgList){
				Long perId = perOrg.getId();
				if(srcId.longValue() == perId.longValue()){
					isAdd = true;
					break;
				}
				for(Long id : srcidList){
					if(id.longValue() == perId.longValue()){
						isAdd = true;
						break;
					}
				}
			}
			// 一级父类无需做权限判断
			if(isAdd || srcOrg.getParent_id().longValue() == 0l)
				hasOrgList.add(srcOrg);
		}
		
		return hasOrgList;
	}
}
