package com.edu.erp.promotion.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.ActivityInfo;
import com.edu.erp.model.CouponInfo;
import com.github.pagehelper.Page;


/**
 * 优惠活动
 * ohs
 *
 */
public interface ActivityInfoService {
	
	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	Page<ActivityInfo> queryActivityInfoForPage(Map<String,Object> params)throws Exception;
	
	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	List<ActivityInfo> queryActivityInfoForList(Map<String,Object> params)throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param activityInfo
	 * @throws Exception
	 */
	void addActivityInfo(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 修改
	 * 
	 * @param activityInfo 动态参数
	 * @throws Exception
	 */
	void updateActivityInfo(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 逻辑删除
	 * 
	 * @param id
	 *            校区
	 * @throws Exception
	 */
	void deleteActivityInfo(String id)
			throws Exception;
	
/*	*//**
	 * 生成券仓库
	 * 
	 * @param couponDepot
	 * @param couponCount
	 * @throws Exception
	 *//*
	void generateCouponDepot(CouponDepot couponDepot, Long couponCount) throws Exception;*/
	/**
	 * 生成券仓库
	 * 
	 * @param couponDepot
	 * @param couponCount
	 * @throws Exception
	 */
	void generateCouponDepot(CouponInfo couponDepot, Long couponCount) throws Exception;

	void changeIsPub(String id, Integer is_pub) throws Exception;
	
	
}
