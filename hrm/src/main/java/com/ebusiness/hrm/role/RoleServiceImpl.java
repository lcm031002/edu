package com.ebusiness.hrm.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.RoleDao;
import com.ebusiness.hrm.dao.RoleMenuRelDao;
import com.ebusiness.hrm.exception.DataBaseException;
import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.Role;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import junit.framework.Assert;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色管理服务处理类
 * @author WP
 * @date 2016年11月3日
 * 
 */
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

	private static final Logger log = Logger.getLogger(RoleServiceImpl.class);

	@Resource(name = "roleDao")
	private RoleDao roleDao;

	@Resource(name = "roleMenuRelDao")
	private RoleMenuRelDao roleMenuRelDao;

	// 查询角色信息列表
	@Override
	public PageInfo<Role> queryRole(String roleName, Integer status, PageParam pageParam) throws Exception {
		Page<Role> list = null;
		PageInfo<Role> page = new PageInfo<Role>();

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("roleName", roleName);
			params.put("status", status);
			// params.put("accountId", accountId);
			PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());

			list = roleDao.queryRole(params);
			log.error("list:" + list);
			page = new PageInfo<Role>(list);
		} catch (Exception e) {
			log.error("error found,see:", e);
		}
		return page;
	}

	// 添加角色
	@Override
	public boolean addRole(Map<String, Object> param) throws Exception {
		boolean suc = true;
		try {
			Assert.assertNotNull(param.get("roleName"));
			String roleName = param.get("roleName") == null ? "" : param.get("roleName").toString();
			Map<String, Object> param2 = new HashMap<String,Object>();
			param2.put("roleName", roleName);
			Long queryPostId = roleDao.queryRoleId(param2);
			if (null != queryPostId) {
				throw new Exception("该角色已存在，请重新命名");
			}
			roleDao.addRole(param);
			param.put("roleId", param.get("id"));
			roleMenuRelDao.addRoleMenu(param);
		} catch (Exception e) {
			e.printStackTrace();
			suc = false;
			throw new DataBaseException(e.getMessage());
		}
		return suc;
	}

	// 修改角色
	public boolean updateRole(Map<String, Object> param) throws Exception {
		Assert.assertNotNull(param.get("roleName"));
		Long roleId = Long.valueOf(param.get("id").toString());
		String roleName = (String) (param.get("roleName") == null ? "" : param.get("roleName"));
		Map<String, Object> param2 = new HashMap<String,Object>();
		param2.put("roleName", roleName);
		param2.put("roleId", roleId);
		Long queryPostId = roleDao.queryRoleId(param2);
		if (null != queryPostId && queryPostId.longValue() != roleId.longValue()) {
			throw new Exception("该角色已存在，请重新命名");
		}
		Integer ret = roleDao.updateRole(param);
		if (ret < 1) {
			throw new Exception("修改角色失败");
		}
		return ret == 1;
	}
	
	public static void main(String[] args) {
		Long roleId = Long.valueOf("100000658");
		Long queryPostId = 100000658L;
		
		System.out.println(queryPostId == roleId);
		
	}

	// 禁用角色
	@Override
	public boolean changeRoleStatus(Long id, Long status) throws Exception {
		Integer ret = roleDao.changeRoleStatus(id, status);
		if(!(status == 0 || status == 1 || status==2)) {
			throw new RuntimeException("不支持该状态值");
		}
		if (ret < 1) {
			throw new RuntimeException("激活/禁用角色失败");
		}
		return ret == 1;
	}

	// 根据角色id查询角色菜单权限关系表
	@Override
	public List<String> queryRoleMenu(Long id) throws Exception {
		return roleMenuRelDao.queryRoleMenu(id);
	}

	// 添加角色菜单权限关系
	public boolean addRoleMenu(Map<String, Object> param) throws Exception {
		Integer ret = roleMenuRelDao.addRoleMenu(param);
		if (ret < 1) {
			throw new Exception("添加角色菜单权限关系失败");
		}
		return ret == 1;
	}

	// 修改角色菜单权限关系
	@Override
	public boolean updateRoleMenu(Map<String, Object> param) throws DataBaseException {
		boolean suc = true;
		try {
			roleMenuRelDao.deleteRoleMenu(Long.valueOf(param.get("roleId").toString()));
			roleMenuRelDao.addRoleMenu(param);
		} catch (Exception e) {
			e.printStackTrace();
			suc = false;
			throw new DataBaseException(e.getMessage());
		}
		return suc;
	}

}
