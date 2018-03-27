package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.OrganizationInfo;

@Repository(value = "organizationDao")
public interface OrganizationDao {
	/**
	 * 
	 * @Title: queryBuBranchs
	 * @Description: 查询团队下的校区
	 * @param param 查询团队下的校区：buId为必填参数，branchs为用户可见校区列表
	 * @return
	 * @throws Exception    设定文件
	 * List<OrganizationInfo>    返回类型
	 *
	 */
	List<OrganizationInfo> queryBuBranchs(Map<String, Object> param) throws Exception;
	
	/**
	 * 根据<T>查询List
	 * 
	 * @param orgInfo
	 * @return List<T>
	 * @throws Exception
	 */
	List<OrganizationInfo> selectList(OrganizationInfo orgInfo)
			throws Exception;
	

	/**
	 * 根据参数查询List<T>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<OrganizationInfo> queryList(Map<String, Object> param) throws Exception;
	
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
	 * 更新组织机构logo
	 * @param paramMap 更新数据
	 * @throws Exception
	 */
	void updateLogo(Map<String, Object> paramMap) throws Exception;
	
	/**
     * 删除组织机构logo
     * @param paramMap logo数据
     * @throws Exception
     */
    void deleteLogo(Map<String, Object> paramMap) throws Exception;

}
