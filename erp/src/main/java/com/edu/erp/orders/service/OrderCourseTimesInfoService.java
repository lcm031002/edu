package com.edu.erp.orders.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TCChangeCourseTimes;
import com.edu.erp.model.TCCourseTimes;
import com.edu.erp.model.TabOrderCourseTimesInfo;

/**
 * @ClassName: OrderCourseTimesInfoService
 * @Description: 课程课次服务
 * 
 */
public interface OrderCourseTimesInfoService {
	/**
	 * 
	 * @Title: saveOrderCourseTimesInfo
	 * @Description: 添加订单课程课次对象
	 * @param orderCourseTimesInfo
	 *            订单课程报班课次对象
	 * @return 返回已经添加报班课程课次
	 * @throws Exception
	 *             设定文件 TabOrderCourseTimesInfo 返回类型
	 * 
	 */
	int saveOrderCourseTimesInfo(
			TabOrderCourseTimesInfo orderCourseTimesInfo) throws Exception;
	/**
	 * 
	 * @Title: deleteOrderCourseTimesInfo
	 * @Description: 删除报班单课程课次信息
	 * @param orderCourseTimesInfo 报班单课程信息
	 * @return 返回删除的数目
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int deleteOrderCourseTimesInfo(TabOrderCourseTimesInfo orderCourseTimesInfo)
			throws Exception;
	/**
	 * 
	 * @Title: queryTabOrderCourseTimesInfo
	 * @Description: 查询给定详单的报班课次信息
	 * @param orderDetailId 详单id
	 * @return 返回报班课程的选课信息
	 * @throws Exception    设定文件
	 * List<TabOrderCourseTimesInfo>    返回类型
	 *
	 */
	List<TabOrderCourseTimesInfo> queryTabOrderCourseTimesInfo(Long orderDetailId) throws Exception;
	
	/**
	 * 
	 * @Title: queryOrderTimesInfo
	 * @Description: 查询订单的课次信息
	 * @param orderId
	 *            订单id
	 * @return 课程课次信息
	 * @throws Exception
	 *             设定文件 List<TabOrderCourseTimesInfo> 返回类型
	 * 
	 */
	List<TabOrderCourseTimesInfo> queryOrderTimesInfo(Long orderId)
			throws Exception;
	
	/**
	 * 根据单据编码更新change_id到订单课次表
	 * @param change_no
	 */
	void updateChangeIdOnChangeTimes(Map<String,Object> map) throws Exception;
	/**
	 * 查询课次的考勤状态
	 * @return map change_course_id
	 * @throws Exception
	 */
	List<Map<String,Object>> queryCourseTimesAttendType(Map<String,Object> map) throws Exception;
	/**
	 * 更新课次的状态
	 * @param map
	 * @throws Exception
	 */
	void updateValidOrderCourseTimes(Map<String,Object> map) throws Exception;

	/**
	 * 转班，根据变动id添加转入课次信息
	 * @param changeId 变动ID
	 * @param orderCourseId 转入订单课程ID
	 * @throws Exception
	 */
	void addOrderCourseTimesByChangeId(Long changeId, Long orderCourseId) throws Exception;
	/**
	 * 查询退费课次信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<TCChangeCourseTimes> queryTabChangeCourseTimesInfo(Map<String,Object> map) throws Exception;
	
	/**
	 * 保存退费课次信息
	 * @param tCChangeCourseTimes
	 * @throws Exception
	 */
	void saveTcOrderCourseTimes(TCCourseTimes tCCourseTimes) throws Exception;
	/**
	 * 查询正式订单的课次人员列表
	 * 查询待确定订单课次的人员列表
	 * @param map
	 * order_detail_id 订单详情列表ID
	 * course_times 课次
	 * type  1:正式订单对应课次人员；2:待确认订单对应课次人员
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryCheckPeopleByCourseTimes(Map<String, Object> map) throws Exception;

	/**
	 * 订单作废，更新订单课程课次为无效
	 * @param orderId
	 */
	void updateInValidOrderCourseTimes(Long orderId);
}

