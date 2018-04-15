package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.jstree.TreeModel;
import com.edu.erp.model.OrganizationInfo;

public interface OrganizationService {

	/**
	 * 查询
	 * 
	 * @param orgInfo
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<OrganizationInfo> queryOrganizationInfo(OrganizationInfo orgInfo)
			throws Exception;

	/**
	 * 根据参数查询List<T>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<OrganizationInfo> list(Map<String, Object> param) throws Exception;
	/**
	 * 
	 * @Title: queryBuBranchs
	 * @Description: 查询团队用户可见的校区
	 * @param userId 用户id
	 * @param buId 团队id
	 * @return 用户可见校区
	 * @throws Exception    设定文件
	 * List<OrganizationInfo>    返回类型
	 *
	 */
	List<OrganizationInfo> queryBuBranchs(Long userId,Long buId) throws Exception;
	
	/**
	 * 
	 * @Title: queryBuBranchs
	 * @Description: 查询团队下的校区
	 * @param buId 团队id
	 * @return 用户可见校区
	 * @throws Exception    设定文件
	 * List<OrganizationInfo>    返回类型
	 *
	 */
	List<OrganizationInfo> queryBuBranchs(Long buId) throws Exception;
	
	List<OrganizationInfo> queryBuBranchs(OrganizationInfo orgInfo) throws Exception;

	
	OrganizationInfo selectById(Long id) throws Exception;
	
	/**
     * 组织机构新增
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
    void insert(OrganizationInfo orgInfo) throws Exception;
    
    /**
     * 组织机构更新
     * @param orgInfo 组织机构信息
     * @throws Exception
     */
    void update(OrganizationInfo orgInfo) throws Exception;
    
    /**
     * 上传组织机构logo
     * @param jsonMap
     * @throws Exception
     */
    String uploadLogo(Map<String, Object> jsonMap) throws Exception;
    
    /**
     * 删除组织机构logo
     * @param jsonMap logo数据
     * @throws Exception
     */
    void deleteLogo(Map<String, Object> jsonMap) throws Exception;

	/**
	 * 查询账户校区列表树
	 * @param
	 * @return List<TreeModel>
	 */
	List<TreeModel> queryOrgTreeModel(Long accountId) throws Exception;
	
}
