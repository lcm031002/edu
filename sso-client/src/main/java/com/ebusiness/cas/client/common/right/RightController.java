/**  
 * @Title: RightController.java
 * @Package com.ebusiness.cas.client.common.right
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月14日 下午8:20:03
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.common.right;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.RightModel;
import com.ebusiness.cas.client.common.util.WebContextUtils;

/**
 * @ClassName: RightController
 * @Description: 权限服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月14日 下午8:20:03
 *
 */
@Controller
public class RightController {
	private static final Logger log = Logger.getLogger(RightController.class);
	
	@RequestMapping(value = { "/common/rightservice" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	RightModel queryRights(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user rights info");
		}
		RightModel rightModel = WebContextUtils.genUserRights(request);
		if (log.isDebugEnabled()) {
			log.debug("end to query user rights info");
		}
		return rightModel;
	}
}
