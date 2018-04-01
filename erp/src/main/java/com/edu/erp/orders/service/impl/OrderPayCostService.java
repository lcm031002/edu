/**  
 * @Title: OrderPayCostService.java
 * @Package com.ebusiness.erp.orders.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:48:18
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service;

import com.edu.erp.model.TabOrderPayCost;

/**
 * @ClassName: OrderPayCostService
 * @Description: 缴费信息服务对象
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:48:18
 * 
 */
public interface OrderPayCostService {
	/**
	 * 
	 * @Title: saveOrderPayCost
	 * @Description: 保存缴费信息
	 * @param orderPayCost
	 *            缴费信息
	 * @return 返回已经保存的缴费信息对象
	 * @throws Exception
	 *             设定文件 TabOrderPayCost 返回类型
	 * 
	 */
	int saveOrderPayCost(TabOrderPayCost orderPayCost) throws Exception;

	void deleteOrderPayCost(TabOrderPayCost orderPayCost) throws Exception;

	/**
	 * 
	 * @Title: queryTabOrderPayCost
	 * @Description: 查询订单的支付的信息
	 * @param orderId
	 *            订单id
	 * @return 支付信息
	 * @throws Exception
	 *             设定文件 TabOrderPayCost 返回类型
	 * 
	 */
	TabOrderPayCost queryTabOrderPayCost(Long orderId) throws Exception;
}
