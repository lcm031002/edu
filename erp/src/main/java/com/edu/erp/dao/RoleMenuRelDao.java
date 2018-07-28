package com.edu.erp.dao;

import com.edu.erp.model.RoleMenuRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleMenuRelDao
 * @Description: 角色菜单权限关系
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
	Integer addRoleMenu(@Param("roleId") Long roleId, @Param("menuIndexList") List<String> menuIndexList);
	
	/**
	 * 
	 * @Title: deleteRoleMenu
	 * @Description:删除角色菜单权限关系
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteRoleMenu(Long roleId);
}
