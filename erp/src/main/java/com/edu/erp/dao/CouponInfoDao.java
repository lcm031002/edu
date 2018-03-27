package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.CouponInfo;
import com.github.pagehelper.Page;

@Repository(value = "couponInfoDao")
public interface CouponInfoDao {

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
	List<CouponInfo> selectList(Map<String, Object> param)
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
	Integer insert(CouponInfo couponInfo) throws Exception;

	/**
	 * 修改
	 * 
	 * @param couponInfo
	 *            动态参数
	 * @throws Exception
	 */
	Integer update(CouponInfo couponInfo) throws Exception;

	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 *            status
	 * @throws Exception
	 */
	Integer changeCouponInfoStatus(List<CouponInfo> couponInfos)
			throws Exception;

	/**
	 * (id:?)(branch_id:?)新增校区关系
	 * 
	 * @param id_branchs
	 * @return
	 */
	Integer addPrivilegeSchoolRef(List<Map<String, Long>> id_branchs)
			throws Exception;

	/**
	 * 通过主键 删除校区关系
	 * 
	 * @param ids
	 * @return
	 */
	Integer deletePrivilegeSchoolRef(List<Long> ids) throws Exception;

	/**
	 * 
	 * @Description: 通过优惠券查询优惠券的优惠信息
	 * 
	 * @param queryParam
	 *            优惠券编码
	 * 
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return CouponInfoBusiness 返回类型
	 */
	CouponInfo queryCouponInfoByCode(Map<String, Object> queryParam) throws Exception;

	/**
	 * 
	 * @Title: 通过订单id查询优惠券信息
	 * @Description: 按照订单id，查询
	 * @param queryParam
	 *            订单id
	 * @param @throws Exception 设定文件
	 * @return List<CouponInfo> 返回类型
	 * @throws 异常信息
	 */
	List<CouponInfo> queryCouponsByOrderId(Map<String, Object> queryParam) throws Exception;

	Map<String, Object> queryCouponInfoByCodeStudentId(
            Map<String, Object> params) throws Exception;

	/**
	 * 消耗v3的优惠券
	 * 
	 * @param couponInfo
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	Integer v3UpdateCouponInfo(CouponInfo couponInfoBusiness) throws Exception;
	
	/**
	 * @param empIdStr
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteByIds(List<String> ids) throws Exception;
}
