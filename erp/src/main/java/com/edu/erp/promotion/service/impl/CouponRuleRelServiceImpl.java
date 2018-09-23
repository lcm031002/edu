package com.edu.erp.promotion.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.CouponRuleRelDao;
import com.edu.erp.model.CouponRuleRel;
import com.edu.erp.promotion.service.CouponRuleRelService;
import com.edu.erp.util.PromotionUtils;

/**
 * @ClassName: CouponInfoServiceImpl
 * @Description: 优惠券
 *
 */
@Service("couponRuleRelService")
public class CouponRuleRelServiceImpl implements CouponRuleRelService {

	@Resource(name = "couponRuleRelDao")
	private CouponRuleRelDao couponRuleRelDao;

	@Override
	public void addCouponRuleRel(CouponRuleRel couponRuleRel) throws Exception {
		List<Map<String, Long>> one_much = PromotionUtils.getStudentGiftInfoAdd(couponRuleRel.getEncoding(), couponRuleRel.getStudy_ids());
		couponRuleRelDao.addCouponRuleRel(one_much);
	}
}
