package com.edu.cas.client.common.system;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.HttpReponse;

@Controller
public class SystemParamController {
	private static final Logger log = Logger
			.getLogger(SystemParamController.class);

	@RequestMapping(value = { "/common/system_param" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody HttpReponse querySystemParam(
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user employee info");
		}
		HttpReponse httpReponse = new HttpReponse();
		httpReponse.setError(false);
		Map<String, String> casLogoutParam = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> systemEnumeration = request.getSession()
				.getServletContext().getInitParameterNames();
		while (systemEnumeration.hasMoreElements()) {
			String key = systemEnumeration.nextElement();
			casLogoutParam.put(key, request.getSession().getServletContext()
					.getInitParameter(key));
		}
		httpReponse.setData(casLogoutParam);
		return httpReponse;
	}
}
