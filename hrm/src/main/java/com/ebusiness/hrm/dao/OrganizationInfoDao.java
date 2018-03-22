package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.OrganizationInfo;

@Repository(value="organizationDao")
public interface OrganizationInfoDao {
	
	/**
	 * 查询组织结构
	 * @param 
	 * @return  List<OrganizationInfo>
	 */
	List<OrganizationInfo> queryOrg() throws Exception ;
	
	/**
	 * 查询子结构
	 * @param parentId
	 * @return
	 */
	List<Map> querySubOrganizations(Integer parentId) throws Exception ; 
	
	/**
	 * 新增一个<T>
	 * 
	 * @param orgInfo
	 * @return 数据库记录数
	 * @throws Exception
	 */
	Integer addOrganizationInfo(OrganizationInfo orgInfo) throws Exception;
	
	/**
	 * 更新一个<T>
	 * 
	 * @param orgInfo
	 * @return 数据库记录数
	 * @throws Exception
	 */
	Integer updateOrganizationInfo(OrganizationInfo orgInfo) throws Exception;

	/**
	 * 删除多个T
	 * 
	 * @param orgInfo
	 * @return 数据库记录数
	 * @throws Exception
	 */
	Integer removeOrganizationInfo(Integer id) throws Exception;
	
	/**
	 * 查询组织团队结构
	 * @param 
	 * @return  List<OrganizationInfo>
	 */
	List<OrganizationInfo> queryAreas() throws Exception ;
	
	/**
	 * 查询组织校区结构
	 * @param 
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> querySch(@Param(value="buId") Long buId) throws Exception ;
}
