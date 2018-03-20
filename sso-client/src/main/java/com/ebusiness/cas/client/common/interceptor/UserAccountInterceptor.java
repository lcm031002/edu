/**  
 * @Title: UserAccountInterceptor.java
 * @Package com.ebusiness.cas.client.common.interceptor
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午12:09:39
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ebusiness.cas.client.common.account.SSOAccountService;
import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.util.ApplicationContextUtil;
import com.ebusiness.cas.client.common.util.WebContextUtils;

/**
 * @ClassName: UserAccountInterceptor
 * @Description: 账户拦截器
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午12:09:39
 */
public class UserAccountInterceptor implements HandlerInterceptor {
	
	private static final Logger log = Logger
			.getLogger(UserAccountInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String name = AssertionHolder.getAssertion().getPrincipal().getName();
		if (StringUtils.hasText(null != name ? name : null)) {
			HttpSession httpSession = request.getSession();
			if (httpSession.getAttribute(WebContextUtils.USER) == null) {
				SSOAccountService accountService = (SSOAccountService) ApplicationContextUtil
						.getBean("accountSSOService");
				Account account = accountService.queryAccount(name);
				httpSession.setAttribute(WebContextUtils.USER, account);
			}
		} else {
			log.error("error found,see:url is " + request.getRequestURL());
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
