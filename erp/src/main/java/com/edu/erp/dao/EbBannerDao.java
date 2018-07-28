package com.edu.erp.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.EbTBanner;
import com.github.pagehelper.Page;

/***
 * Description ：电商banner DAO 接口
 * 
 */
@Repository("ebBannerDao")
public interface EbBannerDao {

	/**
	 * 保存banner
	 * @param ebTBanner
	 * @return
	 * @throws Exception
	 */
	void  saveBanner(EbTBanner ebTBanner) throws Exception;

	/**
	 * 更新活动信息
	 * @param tBannerEH
	 * @return
	 * @throws Exception
	 */
	Integer updateBanner(EbTBanner tBannerEH) throws Exception;

	
	/**
	 *  更新活动状态
	 * @param params
	 * @throws Exception
	 */
	void changeStatus(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<EbTBanner> selectForPage(Map<String, Object> paramMap) throws Exception;
	/**
	 * 查询是否有重复的序号
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Integer queryRepeatSortNums(Map<String, Object> paramMap) throws Exception;
	
}
