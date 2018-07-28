package com.edu.cas.client.common.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: UserController
 * @Description: 登出服务
 *
 */
@Controller
public class UserController {
	private static final Logger log = Logger.getLogger(UserController.class);

	@RequestMapping(value = { "/common/logout" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> logout(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to logout.");
		}
		Map<String, Object> result = new HashMap<String, Object>();

		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		if (log.isDebugEnabled()) {
			log.debug("end to logout.");
		}
		return result;
	}
}
