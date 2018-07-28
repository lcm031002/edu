package com.edu.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabOrderPayCost;
import com.edu.erp.model.TabOrderPayCostDetail;

/**
 * @ClassName: OrderPayCostDao
 * @Description: 订单缴费信息dao
 * 
 */
@Repository(value = "orderPayCostDao")
public interface OrderPayCostDao {
	/**
	 * 
	 * @Description: 查询给定订单的缴费信息
	 * @param order_id
	 *            订单id
	 * @return List<OrderPayCostBusiness> 返回类型
	 * @throws Exception
	 *             设定文件
	 */
	List<TabOrderPayCost> selectList(Long order_id) throws Exception;

	TabOrderPayCost selectOne(Long order_id) throws Exception;

	/**
	 * 
	 * @Description: 付款详情
	 * @param orderPayCostBusiness
	 *            付款对象信息
	 * @throws Exception
	 *             设定文件
	 * @return int 返回类型
	 */
	int saveOrderPayCost(TabOrderPayCost orderPayCost) throws Exception;

	void deleteOrderPayCost(TabOrderPayCost orderPayCost) throws Exception;
	
	void deleteByOrderId(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryTabOrderPayCost
	 * @Description: 根据订单id查询支付信息
	 * @param orderId
	 *            订单id
	 * @return 支付信息
	 * @throws Exception
	 *             设定文件 TabOrderPayCost 返回类型
	 * 
	 */
	TabOrderPayCost queryTabOrderPayCost(Long orderId) throws Exception;

	/**
	 * 
	 * @Description: 付款详情
	 * 
	 * @param tabOrderPayCostDetail
	 *            付款详情
	 * 
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return int 返回类型
	 * 
	 */
	int saveOrderPayCostDetail(TabOrderPayCostDetail orderPayCostDetail)
			throws Exception;

	int deleteOrderPayCostDetail(TabOrderPayCostDetail orderPayCostDetail)
			throws Exception;
	
	void deleteDetailByOrderId(Long orderId) throws Exception;

	List<TabOrderPayCostDetail> queryTabOrderPayCostDetail(Long orderId)
			throws Exception;

}
