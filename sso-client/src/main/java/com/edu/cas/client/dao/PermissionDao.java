package com.edu.cas.client.dao;

import java.util.List;

import com.edu.cas.client.common.model.Permission;
import org.springframework.stereotype.Repository;

import com.edu.cas.client.common.model.Permission;

/**
 * @ClassName: PermissionDao
 * @Description: 权限持久层
 *
 */
@Repository("permissionDao")
public interface PermissionDao {
	/**
	 * 
	 * @Title: queryByUserId
	 * @Description: 通过用户Id查询用户的权限
	 * @param queryParam 查询参数
	 * @throws Exception
	 *             设定文件
	 * @return List<Permission> 返回类型
	 * @throws
	 */
	List<Permission> queryByUserId(Permission queryParam) throws Exception;
}
