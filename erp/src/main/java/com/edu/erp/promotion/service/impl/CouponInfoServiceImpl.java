/**  
 * @Title: CouponInfoServiceImpl.java
 * @Package com.ebusiness.erp.promotion.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月2日 下午2:57:46
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.promotion.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.erp.dao.CouponInfoDao;
import com.edu.erp.model.CouponInfo;
import com.edu.erp.promotion.service.CouponInfoService;
import com.edu.erp.util.PromotionUtils;
import com.github.pagehelper.Page;

/**
 * @ClassName: CouponInfoServiceImpl
 * @Description: 优惠券
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月2日 下午2:57:46
 * 
 */
@Service("couponInfoService")
public class CouponInfoServiceImpl implements CouponInfoService {

	@Resource(name = "couponInfoDao")
	private CouponInfoDao couponInfoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponInfoForPage
	 * (java.util.Map)
	 */
	@Override
	public Page<CouponInfo> queryCouponInfoForPage(Map<String, Object> page)
			throws Exception {
		Assert.notNull(page);
		return couponInfoDao.queryCouponInfoForPage(page);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponInfoForRef
	 * (java.util.Map)
	 */
	@Override
	public List<CouponInfo> queryCouponInfoForRef(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponInfoForList
	 * (java.util.Map)
	 */
	@Override
	public List<CouponInfo> queryCouponInfoForList(Map<String, Object> params)
			throws Exception {
		Assert.notNull(params);
		return couponInfoDao.selectList(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponInfoForOne
	 * (java.lang.Long)
	 */
	@Override
	public CouponInfo queryCouponInfoForOne(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#addCouponInfo(com
	 * .ebusiness.erp.model.CouponInfo)
	 */
	@Override
	public void addCouponInfo(CouponInfo couponInfo) throws Exception {
		// 生成编号
		PromotionUtils.generateCouponEncoding(couponInfo,1);
		
		Integer ret = couponInfoDao.insert(couponInfo);
		if (ret < 0) {
			throw new Exception();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#updateCouponInfo
	 * (com.ebusiness.erp.model.CouponInfo)
	 */
	@Override
	public void updateCouponInfo(CouponInfo couponInfo) throws Exception {
		Integer ret = couponInfoDao.update(couponInfo);
		if (ret < 0) {
			throw new Exception();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#changeCouponInfoStatus
	 * (java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeCouponInfoStatus(String ids, Integer status)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponInfoByCode
	 * (java.lang.String)
	 */
	@Override
	public CouponInfo queryCouponInfoByCode(String encoding) throws Exception {
		Assert.notNull(encoding,"请输入优惠码");
		Map<String,Object> queryParam = new HashMap<String,Object>();
		queryParam.put("encoding", encoding);
		
		return couponInfoDao.queryCouponInfoByCode(queryParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#queryCouponsByOrderId
	 * (java.lang.Long)
	 */
	@Override
	public List<CouponInfo> queryCouponsByOrderId(Long orderId)
			throws Exception {
		Assert.notNull(orderId);
		Map<String,Object> queryParam = new HashMap<String,Object>();
		queryParam.put("orderId", orderId);
		return couponInfoDao.queryCouponsByOrderId(queryParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.CouponInfoService#
	 * queryCouponInfoByCodeStudentId(java.lang.String, java.lang.Long)
	 */
	@Override
	public BigDecimal queryCouponInfoByCodeStudentId(String encoding,
			Long studentId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.CouponInfoService#v3UpdateCouponInfo
	 * (com.ebusiness.erp.model.CouponInfo, java.util.Map, java.lang.String,
	 * com.ebusiness.cas.client.common.model.OrgModel)
	 */
	@Override
	public Map<String, Object> v3UpdateCouponInfo(
			CouponInfo couponInfoBusiness, Map<String, Object> result,
			String encoding, OrgModel branch) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCouponInfo(String ids) throws Exception {
		// 将数据ID字符串转换为List
		List<String> idList = new ArrayList<String>();
		String[] idArray = ids.split(",");
		idList = Arrays.asList(idArray);
		couponInfoDao.deleteByIds(idList);
		
	}
}
