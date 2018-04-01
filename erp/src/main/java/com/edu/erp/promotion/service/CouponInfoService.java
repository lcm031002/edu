package com.edu.erp.promotion.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.erp.model.CouponInfo;
import com.github.pagehelper.Page;

public interface CouponInfoService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            动态参数
	 * @return
	 */
	Page<CouponInfo> queryCouponInfoForPage(Map<String, Object> param)
			throws Exception;

	/**
	 * 关联查询：查询关联表数据
	 * 
	 * @param params
	 *            ：动态参数
	 * @return
	 */
	List<CouponInfo> queryCouponInfoForRef(Map<String, Object> params)
			throws Exception;

	/**
	 * 传参查询
	 * 
	 * @param param
	 *            ：动态参数
	 * @return
	 */
	List<CouponInfo> queryCouponInfoForList(Map<String, Object> param)
			throws Exception;

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CouponInfo queryCouponInfoForOne(Long id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param couponInfo
	 * @throws Exception
	 */
	void addCouponInfo(CouponInfo couponInfo) throws Exception;

	/**
	 * 修改
	 * 
	 * @param couponInfo
	 *            动态参数
	 * @throws Exception
	 */
	void updateCouponInfo(CouponInfo couponInfo) throws Exception;

	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 *            status
	 * @throws Exception
	 */
	void changeCouponInfoStatus(String ids, Integer status) throws Exception;

	/**
	 * 
	 * @Description: 通过优惠券查询优惠券的优惠信息
	 * 
	 * @param encoding
	 *            优惠券编码
	 * 
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return CouponInfoBusiness 返回类型
	 */
	CouponInfo queryCouponInfoByCode(String encoding) throws Exception;

	/**
	 * @Title: queryCouponsByOrderId
	 * @Description: 通过订单id查询优惠券的信息
	 * @param orderId
	 *            订单id
	 * @throws Exception
	 *             设定文件
	 * @return List<CouponInfo> 返回类型
	 */
	List<CouponInfo> queryCouponsByOrderId(Long orderId) throws Exception;

	BigDecimal queryCouponInfoByCodeStudentId(String encoding, Long studentId)
			throws Exception;

	/**
	 * 消耗V3的优惠券
	 * 
	 * @param couponInfo
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> v3UpdateCouponInfo(CouponInfo couponInfoBusiness,
			Map<String, Object> result, String encoding, OrgModel branch)
			throws Exception;
	
	/**
	 * 逻辑删除
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	void deleteCouponInfo(String id)
			throws Exception;

}
