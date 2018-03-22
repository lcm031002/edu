package com.ebusiness.hrm.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.Role;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: RoleController
 * @Description: 角色管理
 * @author WP
 * @date 2016年11月3日
 * 
 */
@Controller
public class RoleController {

	private static Logger log = Logger.getLogger(RoleController.class);

	@Resource(name = "roleService")
	private RoleService roleService;

	/**
	 * 
	 * @Title: queryRoleForPage @Description: 查询角色信息列表 @param 设定文件 @return
	 * result 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/page" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryRoleForPage(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query Role");
		}
		Map<String, Object> result = new HashMap<>();
		PageInfo<Role> pageInfo = new PageInfo<Role>();
		PageParam pageParam = new PageParam();
		try {
			Role param = new Role();
			param.setRoleName(request.getParameter("roleName"));
			param.setStatus(Integer.valueOf(request.getParameter("status").trim()));
			// param.setAccountId(Long.valueOf(request.getParameter("accountId").trim()));//根据账户查询角色信息，账户管理用到
			pageParam.setPageNum(Integer.valueOf(request.getParameter("pageNum").trim()));
			pageParam.setPageSize(Integer.valueOf(request.getParameter("pageSize").trim()));
			param.setPageParam(pageParam);

			String roleName = param.getRoleName();
			Integer status = param.getStatus();
			// Long accountId=param.getAccountId();

			pageInfo = roleService.queryRole(roleName, status, param.getPageParam());
			result.put("error", false);
			result.put("data", pageInfo.getList());
			if (pageInfo.getPages() == 0) {
				result.put("nodata", true);
			} else {
				result.put("nodata", false);
			}
			result.put("pageParam", new PageParam(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getSize(),
					pageInfo.getTotal(), pageInfo.getPages()));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("msg", "查询角色信息失败");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query Role");
		}
		return result;
	}

	/**
	 * 
	 * @Title: addRole @Description: 新增角色 @param 设定文件 @return httpReponse
	 * 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.POST)
	public @ResponseBody HttpReponse addRole(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to insert role");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
		    List<String> selectedPrivileges = (List<String>) param.get("selectedPrivileges");
		    if (selectedPrivileges == null || CollectionUtils.isEmpty(selectedPrivileges)) {
		        throw new Exception("请选择菜单权限！");
		    }
		    
			// 获取当前登陆的id
			Account account = (Account) request.getSession().getAttribute("USER_OBJ");
			param.put("create_user", account.getId());
			boolean suc = roleService.addRole(param);
			httpReponse.setError(suc ? false : true);
			httpReponse.setData("addRole");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to insert role");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: updateRole @Description: 更新角色 @param 设定文件 @return httpReponse
	 * 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.PUT)
	public @ResponseBody HttpReponse updateRole(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to update role");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			// 获取当前登陆的id
			Account account = (Account) request.getSession().getAttribute("USER_OBJ");
			param.put("update_user", account.getId());
			boolean suc = roleService.updateRole(param);
			httpReponse.setError(suc ? false : true);
			httpReponse.setData("updateRole");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to update role");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: deleteRole @Description: 禁用角色 @param 设定文件 @return httpReponse
	 * 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpReponse changeRoleStatus(Long roleId,Long status) {
		if (log.isDebugEnabled()) {
			log.debug("begin to delete role");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			Assert.notNull(roleId,"缺少参数roleId");
			Assert.notNull(status,"缺少参数status");
			boolean suc = roleService.changeRoleStatus(roleId, status);
			httpReponse.setError(suc ? false : true);
			httpReponse.setData("deleteRole");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to delete role");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: queryRoleMenu @Description: 查询角色菜单权限关系表 @param 设定文件 @return
	 * HttpReponse 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/sub" }, method = RequestMethod.GET)
	public @ResponseBody HttpReponse queryRoleMenu(Long id, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query RoleMenu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			List<String> privileges = roleService.queryRoleMenu(id);
			httpReponse.setData(privileges);
			httpReponse.setError(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query RoleMenu");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: updateRoleMenu @Description: 修改角色菜单权限关系 @param 设定文件 @return
	 * httpReponse 返回类型 @throws
	 */
	@RequestMapping(value = { "/hrmSystemSettings/hrmRoleMgr/sub" }, method = RequestMethod.PUT)
	public @ResponseBody HttpReponse updateRoleMenu(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to update RoleMenu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			boolean suc = roleService.updateRoleMenu(param);
			httpReponse.setError(suc ? false : true);
			httpReponse.setData("updateRole");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to update RoleMenu");
		}
		return httpReponse;
	}

}
