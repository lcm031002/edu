/**  
 * @Title: OrderPayCostDetailService.java
 * @Package com.ebusiness.erp.orders.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:48:34
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service;

import com.edu.erp.model.TabOrderPayCostDetail;

/**
 * @ClassName: OrderPayCostDetailService
 * @Description: 订单缴费详情对象
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:48:34
 * 
 */
public interface OrderPayCostDetailService {
	/**
	 * 
	 * @Title: saveOrderPayCostDetail
	 * @Description: 缴费详细信息
	 * @param orderPayCostDetail
	 *            缴费信息详情对象
	 * @return 已经完成添加的缴费详情对象
	 * @throws Exception
	 *             设定文件 TabOrderPayCostDetail 返回类型
	 * 
	 */
	int saveOrderPayCostDetail(
			TabOrderPayCostDetail orderPayCostDetail) throws Exception;
	int deleteOrderPayCostDetail(
			TabOrderPayCostDetail orderPayCostDetail) throws Exception;
	
	void deleteDetailByOrderId(Long orderId) throws Exception;
}
