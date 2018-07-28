package com.edu.cas.client.dao;

import java.util.List;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.model.UserPersonalSettings;
import org.springframework.stereotype.Repository;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.model.UserPersonalSettings;

/**
 * @ClassName: OrgModelDao
 * @Description: 组织结构服务
 *
 */
@Repository("orgModelDao")
public interface OrgModelDao {
	/**
	 * 
	 * @Title: queryRightOrg
	 * @Description: 查询模型
	 * @param queryModel
	 *            查询对象模型
	 * @throws Exception
	 *             设定文件
	 * @return OrgModel 返回类型
	 */
	List<OrgModel> queryByUserId(OrgModel queryModel) throws Exception;
	/**
	 * 用户可见的团队的列表查询
	 * @param queryModel
	 * @return
	 * @throws Exception
	 */
	List<OrgModel> queryOrgBuList(OrgModel queryModel) throws Exception;

	/**
	 * @Title: queryAll
	 * @Description: 查询系统所有的权限树
	 * @throws Exception
	 *             设定文件
	 * @return List<OrgModel> 返回类型
	 */
	List<OrgModel> queryAll() throws Exception;

	/**
	 * 
	 * @Title: querySelectedOrg
	 * @Description: 查询默认选中的组织节点
	 * @param settings
	 * @throws Exception
	 *             设定文件
	 * @return OrgModel 返回类型
	 */
	List<OrgModel> querySelectedOrg(UserPersonalSettings settings)
			throws Exception;
}
