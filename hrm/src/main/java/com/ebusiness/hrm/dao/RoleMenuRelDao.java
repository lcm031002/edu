package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: RoleMenuRelDao
 * @Description: 角色菜单权限关系
 * @author WP
 * @date 2016年11月9日 
 * 
 */
@Repository(value="roleMenuRelDao")
public interface RoleMenuRelDao {

	/**
	 * 
	 * @Title: queryRoleMenu
	 * @Description: 根据角色id查询角色菜单权限关系
	 * 
	 * @return Page<Role> 返回类型
	 */
	List<String> queryRoleMenu(Long id) throws Exception;
	
	
	/**
	 * 
	 * @Title: addRoleMenu
	 * @Description: 添加角色菜单权限关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer addRoleMenu(Map<String, Object> param);
	
	/**
	 * 
	 * @Title: deleteRoleMenu
	 * @Description:删除角色菜单权限关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteRoleMenu(Long roleId);
}
