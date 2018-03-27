/**  
 * @Title: IndexController.java
 * @Package com.ebusiness.klxxedu.index
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月6日 下午5:32:19
 * @version KLXX ERPV4.0  
 */
package com.edu.edu.index;

import com.edu.common.util.PropertiesTools;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: IndexController
 * @Description: 全局控制器
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月6日 下午5:32:19
 * 
 */
@Controller
public class IndexController {
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/*" })
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		开发时使用 index-debug,正式发布时，需要先用gulp进行前端代码打包，再将首页改为index
		String env = PropertiesTools.getPropertiesByKey("env");
		ModelAndView modelAndView = null;
		if (StringUtils.isEmpty(env) || "dev".equals(env)) {
			modelAndView = new ModelAndView("index-debug");
		} else {
			modelAndView = new ModelAndView("index");
		}
		//ModelAndView modelAndView = new ModelAndView("index");
		Enumeration<String> enumeration = request.getParameterNames();
		List<Map<String,String>> paramList = new ArrayList<Map<String,String>>();
		while(enumeration.hasMoreElements()){
			String paramName = enumeration.nextElement();
			Map<String,String> paramObj = new HashMap<String,String>();
			paramObj.put("name", paramName);
			paramObj.put("value", request.getParameter(paramName));
			paramList.add(paramObj);
		}
		request.setAttribute("paramList", paramList);
		return modelAndView;
	}
}
