package com.ebusiness.hrm.role;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.Role;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: RoleService
 * @Description: 角色管理
 * @author WP
 * @date 2016年11月3日 
 * 
 */
public interface RoleService {

	/**
	 * 
	 * @Title: queryRole
	 * @Description: 查询角色信息列表
	 * 
	 * @return PageInfo<Role> 返回类型
	 */
	PageInfo<Role> queryRole(String roleName,Integer status,PageParam pageParam) throws Exception;
	
	/**
	 * 
	 * @Title: addRole
	 * @Description: 添加角色
	 * 
	 * @return boolean 返回类型
	 */
	boolean addRole(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: updateRole
	 * @Description: 修改角色
	 * 
	 * @return boolean 返回类型
	 */
	boolean updateRole(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteRole
	 * @Description: 禁用角色
	 * @param status TODO
	 * 
	 * @return boolean 返回类型
	 */
	boolean changeRoleStatus(Long id, Long status) throws Exception;
	
	/**
	 * 
	 * @Title: queryRoleMenu
	 * @Description: 查询角色菜单权限关系
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<String> queryRoleMenu(Long id) throws Exception;
	
	/**
	 * 
	 * @Title: updateRoleMenu
	 * @Description: 修改角色菜单权限关系
	 * 
	 * @return boolean 返回类型
	 */
	boolean updateRoleMenu(Map<String, Object> param) throws Exception;
	
	
}
