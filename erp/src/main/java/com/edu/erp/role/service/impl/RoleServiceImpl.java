package com.edu.erp.role.service.impl;

import com.edu.erp.dao.RoleDao;
import com.edu.erp.dao.RoleMenuRelDao;
import com.edu.erp.model.Role;
import com.edu.erp.role.service.RoleService;
import com.github.pagehelper.Page;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private static final Logger log = Logger.getLogger(RoleServiceImpl.class);

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Resource(name = "roleMenuRelDao")
    private RoleMenuRelDao roleMenuRelDao;

    // 查询角色信息列表
    @Override
    public Page<Role> queryRole(Map<String, Object> paramMap) throws Exception {
        return roleDao.queryRole(paramMap);
    }

    // 添加角色
    @Override
    public boolean addRole(Role role) throws Exception {
        boolean suc = true;
        try {
            Assert.assertNotNull(role.getRoleName());
            Map<String, Object> param2 = new HashMap<String, Object>();
            param2.put("roleName", role.getRoleName());
            Long queryPostId = roleDao.queryRoleId(param2);
            if (null != queryPostId) {
                throw new Exception("该角色已存在，请重新命名");
            }
            roleDao.addRole(role);
            roleMenuRelDao.addRoleMenu(role.getId(), role.getSelectedPrivileges());
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
            throw new Exception(e.getMessage());
        }
        return suc;
    }

    // 修改角色
    @Override
    public boolean updateRole(Role role) throws Exception {
        Assert.assertNotNull(role.getRoleName());
        Map<String, Object> param2 = new HashMap<String, Object>();
        Long roleId = role.getId();
        param2.put("roleName", role.getRoleName());
        param2.put("roleId", roleId);
        Long queryPostId = roleDao.queryRoleId(param2);
        if (null != queryPostId && queryPostId.longValue() != roleId.longValue()) {
            throw new Exception("该角色已存在，请重新命名");
        }
        Integer ret = roleDao.updateRole(role);
        return ret == 1;
    }

    // 禁用角色
    @Override
    public boolean changeRoleStatus(Long id, Long status) throws Exception {
        Integer ret = roleDao.changeRoleStatus(id, status);
        if (!(status == 0 || status == 1 || status == 2)) {
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

    // 修改角色菜单权限关系
    @Override
    public boolean updateRoleMenu(Role role) throws Exception {
        boolean suc = true;
        try {
            roleMenuRelDao.deleteRoleMenu(role.getId());
            roleMenuRelDao.addRoleMenu(role.getId(), role.getSelectedPrivileges());
        } catch (Exception e) {
            suc = false;
            throw new Exception(e.getMessage());
        }
        return suc;
    }
}
