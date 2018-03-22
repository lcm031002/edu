package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.Role;
import com.github.pagehelper.Page;

/**
 * @ClassName: RoleDao
 * @Description: 角色管理持久层
 * @author WP
 * @date 2016年11月3日 
 * 
 */
@Repository(value="roleDao")
public interface RoleDao {

	/**
	 * 
	 * @Title: queryRole
	 * @Description: 查询角色信息列表
	 * 
	 * @return Page<Role> 返回类型
	 */
	public Page<Role> queryRole(Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @Title: updateRole
	 * @Description: 修改角色
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateRole(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: addRole
	 * @Description: 添加角色
	 * 
	 * @return Integer 返回类型
	 */
	Integer addRole(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteRole
	 * @Description: 禁用角色
	 * @param status TODO
	 * 
	 * @return Integer 返回类型
	 */
	Integer changeRoleStatus(@Param("roleId")Long roleId, @Param("status")Long status) throws Exception;
	
	/**
	 * @Title: queryRoleId
	 * @Description: 查询已存在角色
	 * 
	 * @return Long 返回类型
	 */
	Long queryRoleId(Map<String, Object> param) throws Exception;
	
	
}
