package com.edu.erp.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.Constants;
import com.edu.erp.model.Role;
import com.edu.erp.role.service.RoleService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleController extends BaseController {

    private static Logger log = Logger.getLogger(RoleController.class);

    @Resource(name = "roleService")
    private RoleService roleService;

    /**
     *
     * @Title: queryRoleForPage @Description: 查询角色信息列表 @param 设定文件 @return
     * result 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/page" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> queryRoleForPage(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query Role");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = this.initParamMap(request, true, StringUtils.EMPTY);

            Page<Role> page = roleService.queryRole(paramMap);
            this.setRespDataForPage(request, page.getResult(), resultMap);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put("error", true);
            resultMap.put("msg", "查询角色信息失败");
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query Role");
        }
        return resultMap;
    }

    /**
     *
     * @Title: addRole @Description: 新增角色 @param 设定文件 @return httpReponse
     * 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> addRole(@RequestBody Role role, HttpServletRequest request,
                                             HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to insert role");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<String> selectedPrivileges = role.getSelectedPrivileges();
            if (selectedPrivileges == null || CollectionUtils.isEmpty(selectedPrivileges)) {
                throw new Exception("请选择菜单权限！");
            }

            this.setDefaultValue(request, role, false);
            boolean suc = roleService.addRole(role);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to insert role");
        }
        return resultMap;
    }

    /**
     *
     * @Title: updateRole @Description: 更新角色 @param 设定文件 @return httpReponse
     * 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> updateRole(@RequestBody Role role, HttpServletRequest request,
                                                HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update role");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 获取当前登陆的id
            this.setDefaultValue(request, role, true);
            boolean suc = roleService.updateRole(role);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update role");
        }
        return resultMap;
    }

    /**
     *
     * @Title: deleteRole @Description: 禁用角色 @param 设定文件 @return httpReponse
     * 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> changeRoleStatus(Long roleId, Long status) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete role");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Assert.notNull(roleId,"缺少参数roleId");
            Assert.notNull(status,"缺少参数status");
            boolean suc = roleService.changeRoleStatus(roleId, status);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete role");
        }
        return resultMap;
    }

    /**
     *
     * @Title: queryRoleMenu @Description: 查询角色菜单权限关系表 @param 设定文件 @return
     * HttpReponse 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/sub" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> queryRoleMenu(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query RoleMenu");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<String> privileges = roleService.queryRoleMenu(id);
            resultMap.put(Constants.RespMapKey.DATA, privileges);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query RoleMenu");
        }
        return resultMap;
    }

    /**
     *
     * @Title: updateRoleMenu @Description: 修改角色菜单权限关系 @param 设定文件 @return
     * httpReponse 返回类型 @throws
     */
    @RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/sub" }, method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> updateRoleMenu(@RequestBody Role role, HttpServletRequest request,
                                                    HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update RoleMenu");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean suc = roleService.updateRoleMenu(role);
            resultMap.put(Constants.RespMapKey.ERROR, suc ? false : true);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update RoleMenu");
        }
        return resultMap;
    }

}

