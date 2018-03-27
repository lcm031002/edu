package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabOrderInfo;
import com.github.pagehelper.Page;

/***
 * Description ： DAO 接口
 * 
 * Author ：
 * 
 * Date :
 */
@Repository("orderInfoDao")
public interface OrderInfoDao {

	/**
	 * @Description: 通过给定的订单ID查询对应的订单信息
	 * @param id
	 *            订单ID
	 * @param @throws Exception 设定文件
	 * @return OrderBusiness 返回类型
	 */
	TabOrderInfo queryTemporaryOrderInfo(TabOrderInfo orderInfo)
			throws Exception;

	/***
	 * 保存信息（2014-09-11）
	 * 
	 * @param orderInfo
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 *             抛出系统异常
	 */
	int saveOrderInfo(TabOrderInfo orderInfo) throws Exception;

	/***
	 * 保存信息（2014-09-11）
	 * 
	 * @param orderInfo
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 *             抛出系统异常
	 */
	int deleteOrderInfo(TabOrderInfo orderInfo) throws Exception;

	/***
	 * 保存信息（2014-09-11）
	 * 
	 * @param orderInfo
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	int updateOrderInfo(TabOrderInfo orderInfo) throws Exception;

	/***
	 * 更新是否订单是否走了审批流程
	 * 
	 * @param orderInfo
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	int updateOrderApproved(TabOrderInfo params) throws Exception;

	/**
	 * 
	 * 
	 * @Description: 查询给定的学生的订单信息
	 * @param studentId
	 *            学生id
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            截止时间
	 * @param product_line
	 *            产品线
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderBusiness> 返回类型
	 */
	List<TabOrderInfo> queryStudentOrderInfo(TabOrderInfo orderInfo)
			throws Exception;

	/**
	 * 生成正式订单表数据
	 * 
	 * @param map
	 * @throws Exception
	 */
	Map<String, Object> createOrder(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * @Title: updatePerformanceAttribution
	 * @Description: 订单业绩归属设置
	 * @param map
	 *            ：order_id 订单ID ，user_id 用户ID
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void updatePerformanceAttribution(Map<String, Object> map) throws Exception;

	String queryTuiFeiByStudentId(String sqlID, Map<String, Object> Map)
			throws Exception;
	
	/**
	 * 
	 * @Title: queryUserOrderList
	 * @Description: 查询用户的报班单据信息
	 * @param userId,必填
	 * @return 查询用户的订单信息
	 * @throws Exception    设定文件
	 * List<Map<String,Object>>    返回类型
	 *
	 */
	Page<Map<String, Object>> queryUserOrderList(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 更新发票状态
	 * @param tabOrderInfo 封装订单编号及发票状态信息
	 */
	void updateInvoiceStatus(TabOrderInfo tabOrderInfo);
	
	/**
	 * 佳音续单判断
	 * @param paramMap
	 * @return 1-续单 0-新单
	 */
	int getJyRenewalFlag(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 个性化续单判断
	 * @param paramMap
	 * @return 1-续单 0-新单
	 * @throws Exception
	 */
	int getGxhRenewalFlag(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询欠费订单
	 * <pre>
	 * 	订单表（TAB_ORDER_INFO）中，有效状态（VALID_STATUS为1），审核状态（CHECK_STATUS为3），缴费状态（PAY_STATUS为0）的所有订单都是欠费订单
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Page<TabOrderInfo> selectUnpay(Map<String, Object> params) throws Exception;
	
	/**
	 * 查询是否需要发送到暑期双师接口
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Long needSynToDouble(Long orderId) throws Exception ;


	/**
	 * 更新备注信息
	 * @param tabOrderInfo 封装订单编号及发票状态信息
	 */
	void updateOrderRemark(TabOrderInfo tabOrderInfo);

	/**
	 * 更新订单二维码信息
	 * @param tabOrderInfo
	 */
	void updateOrderQrInfo(TabOrderInfo tabOrderInfo);

	/**
	 * 订单解锁
	 * @param params
	 */
	void updateOrderLockStatus(Map<String, Object> params);

}
