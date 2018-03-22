package com.ebusiness.hrm.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.cas.client.common.util.PwdEncryptUtil;
import com.ebusiness.hrm.model.Account;
import com.ebusiness.hrm.model.PageParam;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: AccountController
 * @Description: 账户管理
 * @author WP
 * @date 2016年10月27日 
 * 
 */
@Controller
public class AccountController {

	private static Logger log = Logger.getLogger(AccountController.class);
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	/**
	* 
	* @Title: queryAccountForPage
	* @Description: 查询账户信息列表
	* @param     设定文件
	* @return result    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr/page"},method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryAccountForPage(HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query account");
		}
		Map<String, Object> result = new HashMap<>();
		PageInfo<Account> pageInfo = new PageInfo<Account>();
		PageParam pageParam = new PageParam();
		try{
			Account param=new Account();
			param.setAccountName(request.getParameter("accountName"));
			param.setEmployeeId(Long.valueOf(request.getParameter("employeeId").trim()));
			param.setEmployeeName(request.getParameter("employeeName"));
			
			String accountName = param.getAccountName();
			Long employeeId = param.getEmployeeId();
			String employeeName = param.getEmployeeName();
			
			pageParam.setPageNum(Integer.valueOf(request.getParameter("pageNum").trim()));
			pageParam.setPageSize(Integer.valueOf(request.getParameter("pageSize").trim()));
			param.setPageParam(pageParam);
			
			pageInfo = accountService.queryAccountForPage(accountName,employeeId,employeeName,param.getPageParam());
			result.put("error", false);
			result.put("data", pageInfo.getList());
			if(pageInfo.getPages()==0){
				result.put("nodata", false);
			}else{
				result.put("nodata", true);
			}
			result.put("pageParam", new PageParam(pageInfo.getPageNum(),
											pageInfo.getPageSize(),pageInfo.getSize(),
											pageInfo.getTotal(),pageInfo.getPages()));
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			result.put("error", true);
			result.put("msg", "查询账户信息失败");
		}
		if(log.isDebugEnabled()){
			log.debug("end to query account");
		}
		return result;
	}
	
	/**
	* 
	* @Title: addAccount
	* @Description: 新增账户
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addAccount(@RequestBody Account param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to insert account");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			// 获取当前登陆的id
			com.ebusiness.cas.client.common.model.Account account = (com.ebusiness.cas.client.common.model.Account) request.getSession().getAttribute(
					"USER_OBJ");
			param.setCreateUser(account.getId());
			String password=param.getPassword()==null?StringUtils.EMPTY:param.getPassword();
			param.setPassword(PwdEncryptUtil.encrypt(password));//密码加密并获取
			boolean suc = accountService.addAccount(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addAccount");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add account");
		}
		return httpReponse;
	} 
	
	
	/**
	* 
	* @Title: updateAccount
	* @Description: 修改账户
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateAccount(@RequestBody Account param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update account");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			// 获取当前登陆的id
			com.ebusiness.cas.client.common.model.Account account = (com.ebusiness.cas.client.common.model.Account) request.getSession().getAttribute(
					"USER_OBJ");
			param.setUpdateUser(account.getId());
			String password = param.getPassword()==null?StringUtils.EMPTY:param.getPassword();
			//判断是否修改了密码
			if(StringUtils.isNotBlank(password)){
				param.setPassword(PwdEncryptUtil.encrypt(password));//获取加密密码
			}
			boolean suc = accountService.updateAccount(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateAccount");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update account");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: deleteAccount
	* @Description: 禁用账户
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deleteAccount(Long accountId,Integer status){
		if(log.isDebugEnabled()){
			log.debug("begin to delete account");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = accountService.deleteAccount(accountId,status);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteAccount");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete account");
		}
		return httpReponse;
	}
	
	/**
	 * 
	    * @Title: queryRoleWithAccount
	    * @Description: TODO(根据指定账户查询角色)
	    * @param @param request
	    * @param @return    参数
	    * @return httpReponse    返回类型
	    * @throws
	 */
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr/queryRoleWithAccount"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryRoleWithAccount(Long user_id,HttpServletRequest request){
		if(log.isDebugEnabled()){
			log.debug("begin to query role with accountId");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> result = accountService.queryRoleWithAccount(user_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query role with accountId");
		}
		return httpReponse;
	}
	
	/**
	 * 
	    * @Title: updateAccountRole
	    * @Description: TODO(修改账户角色关系)
	    * @param @param request
	    * @param @return    参数
	    * @return httpReponse    返回类型
	    * @throws
	 */
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr/updateAccountRole"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateAccountRole(@RequestBody Map<String, Object> param,HttpServletRequest
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update accountRole");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = accountService.updateAccountRole(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addAccountRole");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update accountRole");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: updateAccountOrg
	 * @Description: TODO(修改账户组织关系)
	 * @param @param request
	 * @param @return    参数
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr/updateAccountOrg"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateAccountOrg(@RequestBody Map<String, Object> param,HttpServletRequest
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update accountOrg");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = accountService.updateAccountOrg(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addAccountOrg");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update accountOrg");
		}
		return httpReponse;
	}
	
	/**
	 * 通过ID删除用户角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountMgr/removeAccountRoleById"}, method=RequestMethod.DELETE)
    public @ResponseBody HttpReponse deleteAccountRoleById(HttpServletRequest request,HttpServletResponse response){
        if(log.isDebugEnabled()) {
            log.debug("begin to delete user role");
        }
        HttpReponse httpReponse = new HttpReponse();
        try {
            Long accountRoleId = Long.parseLong(request.getParameter("accountRoleId"));
            accountService.deleteAccountRoleById(accountRoleId);
            httpReponse.setError(false);
            httpReponse.setData("deleteUserRole");
        } catch(Exception e) {
            log.error("error found,see:", e);
            httpReponse.setError(true);
            httpReponse.setMessage(e.getMessage());
        }
        if(log.isDebugEnabled()) {
            log.debug("end to delete user role");
        }
        return httpReponse;
    }
}
