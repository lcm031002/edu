package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.ActivityInfo;
import com.edu.erp.model.CouponInfo;
import com.github.pagehelper.Page;
/**
 * 优惠活动
 * 
 * @author wCong
 *
 */
public interface ActivityInfoDao {
	
	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	Page<ActivityInfo> queryActivityInfoForPage(Map<String,Object> params) throws Exception;
	
	/**
	 * 新增
	 * 
	 * @param activityInfo
	 * @throws Exception
	 */
	Integer insert(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 修改
	 * 
	 * @param activityInfo 动态参数
	 * @throws Exception
	 */
	Integer update(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 生成券仓库
	 *
	 * @param couponDepots
	 * @throws Exception
	 */
	Integer generateCouponDepot(List<CouponInfo> couponDepots) throws Exception;

	void changeIsPub(String id, Integer is_pub) throws Exception;

	List<ActivityInfo> queryActivityInfoForList(Map<String, Object> params) throws Exception;
	
	/**
	 * @param ids
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteByIds(List<String> ids) throws Exception;
	
//	Integer generateCouponDepot(List<CouponDepot> couponDepots) throws Exception;
}
