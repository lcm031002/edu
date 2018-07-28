package com.edu.cas.client.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.edu.cas.client.common.model.RightModel;
import com.edu.cas.client.common.util.RightUtils;
import com.edu.cas.client.common.util.WebContextUtils;

/**
 * @ClassName: UserRightInterceptor
 * @Description: 用户权限拦截器
 *
 */
public class UserRightInterceptor implements HandlerInterceptor {

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
		String requestURL = request.getRequestURI();
		if (!WebContextUtils.isAdmin(request)
				&& RightUtils.getInstance().isRightPath(requestURL)) {
			if (RightUtils.getInstance().isRightIgnorePath(requestURL)) {
				return true;
			}

			RightModel rm = WebContextUtils.genUserRights(request);
			if (!hasRight(rm, requestURL)) {
				response.setStatus(403);
				return false;
			}
		}
		return true;
	}

	private boolean hasRight(RightModel rm, String requestURL) {
		if (StringUtils.isNotBlank(rm.getHref())
				&& requestURL.endsWith(rm.getHref())) {
			return true;
		} else if (rm.getServicesSet().contains(requestURL)) {
			return true;
		} else {
			if (!CollectionUtils.isEmpty(rm.getMenus())) {
				for (RightModel child : rm.getMenus()) {
					if (hasRight(child, requestURL)) {
						return true;
					}
				}
			}
			return false;
		}
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
