/**  
 * @Title: OrderCouponRelDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午8:22:21
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;

import com.edu.erp.model.OrderCouponRel;

/**
 * @ClassName: OrderCouponRelDao
 * @Description: 订单优惠券服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午8:22:21
 *
 */
public interface OrderCouponRelDao {
	/**
	 * 
	 * @Title: saveOrderCouponRel
	 * @Description: 保存订单优惠券
	 * @param rel 待保存的优惠券
	 * @return 返回已经保存的优惠券
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int saveOrderCouponRel(OrderCouponRel rel) throws Exception;
	
	/**
	 * 
	 * @Title: deleteOrderCouponRel
	 * @Description: 删除优惠券
	 * @param rel 优惠券
	 * @return 删除的优惠券的个数
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int deleteOrderCouponRel(OrderCouponRel rel) throws Exception;
	
	/**
	 * 
	 * @Title: queryByOrderId
	 * @Description: 通过订单ID查询优惠券
	 * @param rel 查询订单使用的优惠券
	 * @return 订单使用的优惠券
	 * @throws Exception    设定文件
	 * List<OrderCouponRel>    返回类型
	 *
	 */
	List<OrderCouponRel> queryByOrderId(OrderCouponRel rel) throws Exception;
}
