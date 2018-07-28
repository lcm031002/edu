package com.edu.erp.promotion.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.erp.model.CouponRuleRel;
import com.edu.erp.promotion.service.CouponRuleRelService;
import com.edu.erp.util.BaseController;

/**
 * @ClassName: CouponRuleRelController
 * @Description: 优惠券发放服务
 *
 */
@Controller
@RequestMapping(value = { "/couponrulerel" })
public class CouponRuleRelController extends BaseController {
	private static final Logger log = Logger
			.getLogger(CouponRuleRelController.class);

	@Resource(name = "couponRuleRelService")
	private CouponRuleRelService couponRuleRelService;

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> addCouponRuleRel(
			@RequestBody CouponRuleRel couponRuleRel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save Privilege Rule.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

			couponRuleRelService.addCouponRuleRel(couponRuleRel);
			
			result.put("error", false);
			result.put("data", couponRuleRel);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save Privilege Rule.");
		}
		return result;
	}

}
