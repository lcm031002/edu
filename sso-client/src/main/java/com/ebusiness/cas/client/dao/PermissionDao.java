/**  
 * @Title: PermissionDao.java
 * @Package com.ebusiness.cas.client.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月14日 下午7:37:25
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.cas.client.common.model.Permission;

/**
 * @ClassName: PermissionDao
 * @Description: 权限持久层
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月14日 下午7:37:25
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
