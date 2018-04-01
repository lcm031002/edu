/**  
 * @Title: OrderService.java
 * @Package com.modules.ordermanager.service
 * @Description: 订单管理服务API
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年9月19日 下午1:20:29
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TCourse;
import com.edu.erp.model.TOrder;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.model.TOrderCourseTimesLog;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.model.TabOrderInfoDetail;
import com.github.pagehelper.Page;

/**
 * @ClassName: OrderService
 * @Description: 订单管理服务API，提供与订单相关的服务的操作的能力
 * @author zhuliyong zly@entstudy.com
 * @date 2014年9月19日 下午1:20:29
 * 
 */
public interface OrderService {


	/**
	 * 查询订单信息分页效果
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> queryPage(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 查询一条正式订单的信息
	 * @param orderId
	 *            订单ID
	 * @return 正式订单信息
	 * @throws Exception
	 *             设定文件 TOrder 返回类型
	 * 
	 */
	TOrder queryOrderInfo(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseChangeTimesInfo
	 * @Description: 查询订单详情的批改信息
	 * @param orderDetailId
	 *            详单信息
	 * @return 订单批改信息
	 * @throws Exception
	 *             设定文件 List<TOrderCourseTimesLog> 返回类型
	 * 
	 */
	List<TOrderCourseTimesLog> queryOrderCourseChangeTimesInfo(
			Long orderDetailId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderChangeInfo
	 * @Description: 查询订单批改信息
	 * @param orderId
	 *            订单ID
	 * @return 订单批改信息
	 * @throws Exception
	 *             设定文件 List<TOrderChange> 返回类型
	 * 
	 */
	List<TOrderChange> queryOrderChangeInfo(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseSurplusCount
	 * @Description: 通过详单id查询剩余置空课次信息
	 * @param orderDetailId
	 *            详单id
	 * @return 置空课次信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryOrderCourseSurplusCount(Long orderDetailId)
			throws Exception;
	
	/**
	 * 
	 * @Title: queryStudentOrderCourse
	 * @Description: 查询学员特定业务类型的课程 
	 * @param queryParam 查询条件
	 * @return 返回指定业务类型的课程列表
	 * @throws Exception    设定文件
	 * List<TOrderCourse>    返回类型
	 *
	 */
	List<TOrderCourse> queryStudentOrderCourse(Map<String, Object> queryParam)
			throws Exception;
	
	/**
	 * 查询目前订单的课程详情
	 * @param orderId 订单ID
	 * @return 返回指定订单的课程详情
	 * @throws Exception
	 */
	List<TOrderCourse> queryOrderCoursePage(Long orderId) throws Exception;
	
	String queryTuiFeiByStudentId(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> queryWfdOrderDetails(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> qf_queryWfdOrderDetails(Map<String, Object> param) throws Exception;
	/**
	 * 
	 * @Description: 查询该学生可以选择的课程
	 * @param courseCondition 查询条件
	 * @param @throws Exception 设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
    List<TCourse> queryCourseByBusiness(TCourse tCourse) throws Exception ;
    /**
     * 通过临时订单保存正式单据
     * @param tOrder
     * @return
     * @throws Exception
     */
    int saveOrderInfo(TabOrderInfo orderInfo) throws Exception;
    /**
     * 更新订单状态
     * @param tOrder
     * @return
     * @throws Exception
     */
    int updateOrderStatus(TOrder tOrder) throws Exception;
    
    /**
     * 保存订单课程
     * @param tabOrderInfoDetail   订单明细
     * @param orderInfo 临时订单
     * @return
     * @throws Exception
     */
    int saveOrderCourse(TabOrderInfoDetail tabOrderInfoDetail,TabOrderInfo orderInfo) throws Exception;

    
    /**
     * 查询学生个性化报班总的报班课时
     * @param paramMap 查询参数
     * 条件：create_date 订单创建时间，该时间之后创建的订单课时总和
     * 条件：student_id 学生编号
     * @return 报班总课时
     * @throws Exception
     */
    Integer selectStuYdyTotalCourseCount(Map<String, Object> paramMap) throws Exception;
	/**
	 * 查询晚辅导套餐详情
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryWfdComboOrderDetails(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: attendedOrderTimes
	 * @Description: 结转
	 * @param orderId
	 *            订单ID
	 * @param userId
	 *            用户ID
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	void attendedOrderTimes(Long orderId, Long userId) throws Exception;

}
