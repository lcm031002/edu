/**  
 * @Title: OrderInfoService.java
 * @Package com.ebusiness.erp.orders.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:37:56
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service;

import java.util.Map;

import org.jbpm.api.ProcessEngine;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.erp.model.TabOrderInfo;
import com.github.pagehelper.Page;

/**
 * @ClassName: OrderInfoService
 * @Description: 订单服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:37:56
 * 
 */
public interface OrderInfoService {
	/**
	 * 
	 * @Title: saveOrderInfo
	 * @Description: 添加订单信息
	 * @param orderInfo
	 *            订单信息
	 * @return 返回添加完成添加的订单信息
	 * @throws Exception
	 *             设定文件 TabOrderInfo 返回类型
	 * 
	 */
	int saveOrderInfo(TabOrderInfo orderInfo, Account account,
			OrgModel orgModel, ProcessEngine processEngine) throws Exception;
	
	
	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 查询一条正式订单信息
	 * @param orderId
	 *            订单ID
	 * @return 正式订单信息
	 * @throws Exception 
	 * 
	 */
	TabOrderInfo queryOrderInfo(Long orderId) throws Exception;
	
	/**
	 * 
	 * @Title: cancelOrderInfo
	 * @Description: 取消订单单据
	 * @param orderId 订单ID
	 * @return 订单信息
	 * @throws Exception 
	 * 
	 */
	Map<String, Object> cancelOrderInfo(Long orderId,String remark, String operater,Account account, OrgModel orgModel,Map<String, Object> resultMap,ProcessEngine processEngine) throws Exception;
	/**
	 * 
	 * @Title: cancelOrderInfo
	 * @Description: 订单锁定或者解锁
	 * @param orderId 订单ID
	 * @return 订单信息
	 * @throws Exception 
	 * 
	 */
	Map<String, Object> lockOrder(String status,Long orderId,String remark, String operater,Account account, OrgModel orgModel,Map<String, Object> resultMap,ProcessEngine processEngine) throws Exception;

	/**
	 * 
	 * @Title: saveOrderInfo
	 * @Description: 添加订单信息
	 * @param orderInfo
	 *            订单信息
	 * @return 返回添加完成添加的订单信息
	 * @throws Exception
	 *             设定文件 TabOrderInfo 返回类型
	 * 
	 */
	int updateOrderInfo(TabOrderInfo orderInfo, Account account,
			OrgModel orgModel, ProcessEngine processEngine) throws Exception;
	int updateOrderInfo(TabOrderInfo orderInfo) throws Exception;
	/**
	 * 
	 * @Title: payOrderInfo
	 * @Description: 订单支付
	 * @param orderInfo
	 *            支付订单
	 * @param userId
	 *            支付操作人
	 * @return 完成支付的订单
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	TabOrderInfo payOrderInfo(TabOrderInfo orderInfo, Long userId)
			throws Exception;

	/**
	 * 
	 * @Title: startProcess
	 * @Description: 发起审批工作流
	 * @param account
	 *            当前访问的账户
	 * @param orgModel
	 *            当前选中校区
	 * @param orderInfo
	 *            订单对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void startProcess(Account account, OrgModel orgModel,
			TabOrderInfo orderInfo, ProcessEngine processEngine)
			throws Exception;

	/**
	 * 
	 * @Title: unpassOrder
	 * @Description: 不通过对应的订单信息
	 * @param orderId
	 *            订单id
	 * @param userId
	 *            用户id
	 * @param remark
	 *            备注信息
	 * @return 更新的订单个数
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	int unpassOrder(Long orderId, Long userId, String remark) throws Exception;

	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 查询暂存订单信息
	 * @param orderId
	 *            订单ID
	 * @return 订单信息
	 * @throws Exception
	 *             设定文件 TabOrderInfo 返回类型
	 * 
	 */
	TabOrderInfo queryTemporaryOrderInfo(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: deleteTemporaryOrderInfo
	 * @Description: 作废暂存单据
	 * @param orderId
	 *            订单id
	 * @param userId
	 *            修改人id
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void deleteTemporaryOrderInfo(Long orderId, Long userId) throws Exception;

	/**
	 * 
	 * @Title: createOrder
	 * @Description: 生成正式的订单
	 * @param orderId
	 *            订单ID
	 * @param userId
	 *            操作用户ID
	 * @return 操作结果
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	Map<String, Object> createOrder(Long orderId, Long userId) throws Exception;

	/**
	 * 
	 * @Title: setUser
	 * @Description: 对订单的业绩进行归属
	 * @param order_id
	 *            订单ID
	 * @param user_id
	 *            用户ID
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updatePerformanceAttribution(String order_id, String user_id)
			throws Exception;

	/***
	 * 更新是否订单是否走了审批流程
	 * 
	 * @param params
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	int updateOrderApproved(TabOrderInfo params) throws Exception;

	/**
	 * 
	 * @Title: queryUserOrderList
	 * @Description: 查询当前用户的报班单
	 * @param param
	 *            userId，必填项；pay_status，付款状态；有效状态，check_status;
	 * @return 返回用户的报班单据
	 * @throws Exception
	 *             设定文件 Page<Map<String,Object>> 返回类型
	 * 
	 */
	Page<Map<String, Object>> queryUserOrderList(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 更新订单发票状态
	 * @param id 订单编号
	 * @param invoiceStatus 发票状态
	 * @throws Exception
	 */
	void updateInvoiceStatus(Long id, Long invoiceStatus) throws Exception;


	/**
	 * 查询欠费订单
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Page<TabOrderInfo> selectUnpay(Map<String, Object> params)throws Exception;

	/**
	 * 查询是否需要发送到暑期双师接口
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Long needSynToDouble(Long orderId) throws Exception ;

	/**
	 * 更新订单备注
	 * @param params 订单信息
	 * @throws Exception
	 */
	void updateOrderRemark(TabOrderInfo params) throws Exception;

	/**
	 *  解锁订单
	 * @param params
	 * @throws Exception
	 */
	void updateOrderLockStatus(Map<String, Object> params) throws Exception;

	void updateOrderStatusById(Long id, Integer validStatus);

}
