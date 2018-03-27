package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository(value = "couponRuleRelDao")
public interface CouponRuleRelDao {

	/**
	 * 新增
	 * 
	 * @param one_much
	 * @throws Exception
	 */
	Integer addCouponRuleRel(List<Map<String, Long>> one_much) throws Exception;
}
