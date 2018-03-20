/**  
 * @Title: AccountService.java
 * @Package com.ebusiness.cas.client.common.account
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 上午11:39:46
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.account;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.cas.client.common.util.PwdEncryptUtil;
import com.ebusiness.cas.client.common.util.WebContextUtils;

/**
 * @ClassName: AccountService
 * @Description: 账户服务类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 上午11:39:46
 * 
 */
@Controller
public class AccountServiceController {
	private static Logger log = Logger.getLogger(AccountServiceController.class);

	@Resource(name = "accountSSOService")
	private SSOAccountService accountService;

	/**
	 * 
	 * @Title: queryAccount @Description: 查询当前会话中的账户信息 @param request @param
	 * response @param 设定文件 @return HttpReponse 返回类型 @throws
	 */
	@RequestMapping(value = {
			"/common/accountservice" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody HttpReponse queryAccount(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user account info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			httpReponse.setError(false);
			httpReponse.setData(account);
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user account info");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: updateAccount @Description: 更新账户 @param account @param
	 * request @param response @return 设定文件 @return HttpReponse 返回类型 @throws
	 */
	@RequestMapping(value = {
			"/common/accountservice" }, method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody HttpReponse updateAccount(@RequestBody Account account, HttpServletRequest request,
			HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to update user account.");
		}
		try {
			
			accountService.updateAccount(account);
			// FIXME 系统审计日志
			httpReponse.setError(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to update user account.");
		}
		return httpReponse;
	}

	/**
	 * 
	 * @Title: JudgePassword 
	 * @Description: 判断账户密码
	 *  @param account 
	 *  @param request 
	 *  @param response
	 *  @return 设定文件
	 *  @return HttpReponse 返回类型 
	 *  @throws
	 */
	
	@RequestMapping(value = {
			"/common/accountservice/judgePassword" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody HttpReponse JudgePassword(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to judgePassword account info");
		}
		String  nowPassword =request.getParameter("nowPassword");
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			httpReponse.setError(false);
			String originalPassword=account.getPassword();
			nowPassword=PwdEncryptUtil.encrypt(nowPassword);
			String nextJudgePassword="false";
			if(originalPassword.equals(nowPassword)){
				nextJudgePassword="true";
			}else{
				nextJudgePassword="false";
			}
			httpReponse.setData(nextJudgePassword);

		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user account info");
		}
		return httpReponse;

	}

}
