package com.ebusiness.hrm.org;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.jstree.TreeModel;
import com.ebusiness.hrm.model.OrganizationInfo;

public interface OrganizationService {
	
	/**
	 * 查询组织机构所有
	 * @param 
	 * @return List<OrganizationInfo>
	 */
	List<OrganizationInfo> queryOrg() throws Exception;
	
	/**
	 * 查询子机构
	 * @param parentId
	 * @return
	 */
	List<Map> querySubOrganizations(Integer parentId) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param orgInfo
	 * @throws Exception
	 */
	void addOrganizationInfo(OrganizationInfo orgInfo) throws Exception;
	
	/**
	 * 更新
	 * 
	 * @param orgInfo
	 *            动态参数
	 * @throws Exception
	 */
	void updateOrganizationInfo(OrganizationInfo orgInfo) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	boolean removeOrganizationInfo(Integer id) throws Exception;
	
	/**
	 * 查询组织团队机构
	 * @param 
	 * @return List<OrganizationInfo>
	 */
	List<OrganizationInfo> queryAreas() throws Exception;
	
	/**
	 * 查询组织校区机构
	 * @param buId
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> querySch(Long buId) throws Exception;
	
	/**
	 * 查询账户校区列表树
	 * @param 
	 * @return List<TreeModel>
	 */
	List<TreeModel> queryOrgTreeModel(Long accountId) throws Exception;
}
