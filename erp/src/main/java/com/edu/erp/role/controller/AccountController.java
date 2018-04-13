package com.edu.erp.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.cas.client.common.util.PwdEncryptUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.model.Account;
import com.edu.erp.role.service.AccountService;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController extends BaseController {

    private static Logger log = Logger.getLogger(AccountController.class);

    @Resource(name = "accountService")
    private AccountService accountService;

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr/page"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryAccountForPage(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query account");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = this.initParamMap(request, true, StringUtils.EMPTY);
            Page<Account> page = accountService.queryAccountForPage(paramMap);
            this.setRespDataForPage(request, page.getResult(), resultMap);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query account");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAccount(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to insert account");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            this.setDefaultValue(request, account, false);
            if (StringUtils.isNotEmpty(account.getPassword())) {
                account.setPassword(PwdEncryptUtil.encrypt(account.getPassword()));
            }
            boolean suc = accountService.addAccount(account);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to add account");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr"}, method = RequestMethod.PUT)
    @ResponseBody
    public  Map<String, Object> updateAccount(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update account");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            this.setDefaultValue(request, account, true);
            if (StringUtils.isNotEmpty(account.getPassword())) {
                account.setPassword(PwdEncryptUtil.encrypt(account.getPassword()));
            }
            boolean suc = accountService.updateAccount(account);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update account");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteAccount(Long accountId, Integer status) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete account");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean suc = accountService.deleteAccount(accountId, status);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete account");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr/queryRoleWithAccount"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryRoleWithAccount(Long user_id, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("begin to query role with accountId");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Map<String, Object>> result = accountService.queryRoleWithAccount(user_id);
            resultMap.put(Constants.RespMapKey.DATA, result);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to query role with accountId");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr/updateAccountRole"}, method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateAccountRole(@RequestBody Map<String, Object> param, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update accountRole");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean suc = accountService.updateAccountRole(param);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update accountRole");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr/updateAccountOrg"}, method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateAccountOrg(@RequestBody Map<String, Object> param, HttpServletRequest
            request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to update accountOrg");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            boolean suc = accountService.updateAccountOrg(param);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to update accountOrg");
        }
        return resultMap;
    }

    @RequestMapping(value = {"/hrmSystemSettings/hrmAccountMgr/removeAccountRoleById"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteAccountRoleById(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("begin to delete user role");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Long accountRoleId = Long.parseLong(request.getParameter("accountRoleId"));
            accountService.deleteAccountRoleById(accountRoleId);
            resultMap.put(Constants.RespMapKey.ERROR, false);
        } catch (Exception e) {
            log.error("error found,see:", e);
            resultMap.put(Constants.RespMapKey.ERROR, true);
            resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
        }
        if (log.isDebugEnabled()) {
            log.debug("end to delete user role");
        }
        return resultMap;
    }
}

