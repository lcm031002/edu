package com.edu.erp.orders.service;

import java.util.List;

import com.edu.erp.model.TabOrderInfoDetail;

/**
 * @ClassName: OrderInfoDetailService
 * @Description: 订单详情
 *
 */
public interface OrderInfoDetailService {
	/**
	 * 
	 * @Title: saveOrderInfoDetail
	 * @Description: 保存订单详情
	 * @param orderInfoDetail
	 * @return 返回已经添加的订单详情
	 * @throws Exception
	 *             设定文件 TabOrderInfoDetail 返回类型
	 * 
	 */
	int saveOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception;

	/**
	 * 
	 * @Title: deleteOrderInfoDetail
	 * @Description: 删除报班详单单据
	 * @param orderInfoDetail
	 *            详细单据
	 * @return 删除的详细单据数目
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	int deleteOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception;

	/**
	 * 
	 * @Title: queryTabOrderInfoDetail
	 * @Description: 查询订单详情
	 * @param orderId
	 *            订单ID
	 * @return 返回订单详情信息
	 * @throws Exception
	 *             设定文件 List<TabOrderInfoDetail> 返回类型
	 * 
	 */
	List<TabOrderInfoDetail> queryTabOrderInfoDetail(Long orderId)
			throws Exception;
}
