/**  
 * @Title: IOrderYDY.java
 * @Package com.ebusiness.erp.orders.ext
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年3月30日 下午6:26:34
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.ext;

import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TabOrderInfo;

/**
 * @ClassName: IOrderYDY
 * @Description: 1对1业务模型接口
 * @author zhuliyong zly@entstudy.com
 * @date 2017年3月30日 下午6:26:34
 * 
 */
public interface IOrderYDY {
	
	/**
	 * 
	 * @Title: createOrder
	 * @Description: 生成正式的报班单据
	 * @param orderInfo
	 *            临时订单单据信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void createOrder(TabOrderInfo orderInfo) throws Exception;

	/**
	 * 
	 * @Title: cancelOrder
	 * @Description: 正式订单作废
	 * @param orderId
	 *            订单id
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void cancelOrder(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: refundOrder
	 * @Description: 订单退费生效逻辑
	 * @param orderChange
	 *            订单退费生效逻辑
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void refundOrder(TOrderChange orderChange, String changeNo) throws Exception;

	/**
	 * 
	 * @Title: cancelRefundOrder
	 * @Description: 订单退费失效逻辑
	 * @param orderChange
	 *            订单批改信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void cancelRefundOrder(TOrderChange orderChange) throws Exception;

	/**
	 * 
	 * @Title: frozenOrder
	 * @Description: 订单冻结生效逻辑
	 * @param orderChange
	 *            订单批改
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void frozenOrder(TOrderChange orderChange) throws Exception;

	/**
	 * 
	 * @Title: cancelFrozenOrder
	 * @Description: 订单冻结失效逻辑
	 * @param orderChange
	 *            订单批改作废逻辑
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void cancelFrozenOrder(TOrderChange orderChange) throws Exception;

	/**
	 * 
	 * @Title: transferOrder
	 * @Description: 订单转班逻辑
	 * @param orderChange
	 *            订单批改对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void transferOrder(TOrderChange orderChange, Integer businessType) throws Exception;

	/**
	 * 
	 * @Title: attendanceOrder
	 * @Description: 订单考勤
	 * @param attendance
	 *            订单考勤
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void attendanceOrder(TAttendance attendance) throws Exception;

	/**
	 * 校验订单批改课时是否可以批改
	 * @param orderId 订单ID
	 * @param changeTimes 批改课时
	 * @return true-可批改 false-不可批改
	 * @throws Exception
	 */
	boolean checkOrderChangeTimes(Long orderId, Long changeTimes) throws Exception;

}
