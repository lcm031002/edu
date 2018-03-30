/**  
 * @Title: OrgService.java
 * @Package com.edu.cas.client.common.org
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 上午11:41:13
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.org;

import java.util.List;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.model.OrgModel;

/**
 * @ClassName: OrgService
 * @Description: 组织结构树服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 上午11:41:13
 * 
 */
public interface OrgService {
	/**
	 * 
	 * @Title: queryRightOrg
	 * @Description: 查询用户权限树
	 * @throws Exception
	 *             设定文件
	 * @return OrgModel 返回类型
	 * 
	 */
	OrgModel queryRightOrg(Long userId) throws Exception;
	/**
	 * 查询用户权限的校区列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<OrgModel> queryOrgBranchList(Long userId) throws Exception;
	
	/**
	 * 查询用户权限的团队列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<OrgModel> queryOrgBuList(Long userId) throws Exception;
	
	OrgModel queryAdminOrgTree() throws Exception;
	/**
	 * 获取管理员的组织机构列表
	 * @return
	 * @throws Exception
	 */
	List<OrgModel> queryAdminOrgList() throws Exception;
	/**
	 * 
	 * @Title: selectedOrg
	 * @Description: 选中一个组织节点
	 * @param org
	 *            组织结构节点
	 * @param userId
	 *            用户id
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	void selectedOrg(OrgModel org, Long userId) throws Exception;

	/**
	 * 
	 * @Title: querySelectedOrg
	 * @Description: 查询用户选中的组织
	 * @throws Exception
	 *             设定文件
	 * @return OrgModel 返回类型
	 */
	OrgModel querySelectedOrg(Long userId)
			throws Exception;
}
