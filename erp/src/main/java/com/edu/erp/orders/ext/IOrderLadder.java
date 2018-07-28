package com.edu.erp.orders.ext;

import com.edu.erp.model.TOrder;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TabOrderInfo;

/**
 * @ClassName: IOrderLadder
 * @Description: 1对1阶梯算法接口
 *
 */
public interface IOrderLadder {
	/**
	 * 
	 * @Title: orderUseLadder
	 * @Description: 1对1课程绑定了阶梯算法的，报班课时按照阶梯进行计算；
	 * @param orderInfo
	 *            临时订单
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void orderUseLadder(TabOrderInfo orderInfo) throws Exception;
	
	/**
	 * 
	 * @Title: refundUseLadder
	 * @Description: 退费、以及退费作废后，按照阶梯重新调整订单
	 * @param order 正式订单
	 * @param change 订单批改
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void refundUseLadder(TOrder order,TOrderChange change) throws Exception;
	
	/**
	 * 
	 * @Title: frozenUseLadder
	 * @Description: 冻结以及冻结作废时，按照阶梯重新调整订单
	 * @param order 正式订单
	 * @param change 订单批改
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void frozenUseLadder(TOrder order,TOrderChange change) throws Exception;
}
