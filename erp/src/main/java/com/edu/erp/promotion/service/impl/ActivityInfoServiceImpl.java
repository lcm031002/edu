package com.edu.erp.promotion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.ActivityInfoDao;
import com.edu.erp.model.ActivityInfo;
import com.edu.erp.model.CouponInfo;
import com.edu.erp.promotion.service.ActivityInfoService;
import com.edu.erp.util.PromotionUtils;
import com.github.pagehelper.Page;

@Service(value = "activityInfoService")
public class ActivityInfoServiceImpl implements ActivityInfoService {
	
	@Resource(name = "activityInfoDao")
	private ActivityInfoDao activityInfoDao;
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<ActivityInfo> queryActivityInfoForPage(Map<String,Object> params)throws Exception{
		return activityInfoDao.queryActivityInfoForPage(params);
	}
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public List<ActivityInfo> queryActivityInfoForList(Map<String,Object> params)throws Exception{
		return activityInfoDao.queryActivityInfoForList(params);
	}
	
	/**
	 * 新增
	 * 
	 * @param activityInfo
	 * @throws Exception
	 */
	@Override
	public void addActivityInfo(ActivityInfo activityInfo) throws Exception {
		Integer ret = activityInfoDao.insert(activityInfo);
		if(ret < 1)
			throw new RuntimeException();
	}
	
	/**
	 * 修改
	 * 
	 * @param activityInfo 动态参数
	 * @throws Exception
	 */
	@Override
	public void updateActivityInfo(ActivityInfo activityInfo) throws Exception {
		Integer ret = activityInfoDao.update(activityInfo);
		if(ret < 1)
			throw new RuntimeException();
	}
	
/*	*//**
	 * 生成券仓库
	 * 
	 * @param couponDepot
	 * @param couponCount
	 * @throws Exception
	 *//*
	@Override
	public void generateCouponDepot(CouponDepot couponDepot, Long couponCount) throws Exception {
		List<CouponDepot> couponDepotList = new ArrayList<CouponDepot>(couponCount.intValue());
		// test
//		couponDepot.setBranch_id(getCityId(request.getSession()));
		couponDepot.setStatus(CouponDepot.StatusEnum.VALID.getCode());
		for(int i=0;i<couponCount.intValue();i++){
			CouponDepot clone = (CouponDepot) couponDepot.clone();
			clone.setEncoding(couponDepot.getCoupon_prefix()+i);
			PromotionUtils.generateDepotCouponEncoding(clone);
			couponDepotList.add(clone);
		}
		activityInfoDao.generateCouponDepot(couponDepotList);
	}*/
	/**
	 * 生成券仓库
	 * 
	 * @param couponDepot
	 * @param couponCount
	 * @throws Exception
	 */
	@Override
	public void generateCouponDepot(CouponInfo couponDepot, Long couponCount) throws Exception {
		List<CouponInfo> couponDepotList = new ArrayList<CouponInfo>(couponCount.intValue());
		// test
//		couponDepot.setBranch_id(getCityId(request.getSession()));
		couponDepot.setStatus(CouponInfo.StatusEnum.VALID.getCode());
		for(int i=1;i<=couponCount.intValue();i++){
			CouponInfo clone = (CouponInfo) couponDepot.clone();
			clone.setSort_num(i);
			PromotionUtils.generateCouponName(clone,couponCount.toString().length());
			PromotionUtils.generateCouponEncoding(clone,couponCount.toString().length());
			couponDepotList.add(clone);
		}
		
		activityInfoDao.generateCouponDepot(couponDepotList);
	}

	@Override
	public void changeIsPub(String id, Integer is_pub)throws Exception{
		// TODO Auto-generated method stub
		activityInfoDao.changeIsPub(id,is_pub);
	}
	
	@Override
	public void deleteActivityInfo(String ids) throws Exception {
		// 将数据ID字符串转换为List
		List<String> idList = new ArrayList<String>();
		String[] idArray = ids.split(",");
		idList = Arrays.asList(idArray);
		activityInfoDao.deleteByIds(idList);
		
	}
	
}
