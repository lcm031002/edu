package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCChangeCourseTimes;
import com.edu.erp.model.TCCourseTimes;
import com.edu.erp.model.TabOrderCourseTimesInfo;

/**
 * @ClassName: OrderCourseTimesInfoDao
 * @Description: 报班单课次服务
 * 
 */
@Repository(value = "orderCourseTimesInfoDao")
public interface OrderCourseTimesInfoDao {
	/**
	 * 
	 * @Title: saveOrderCourseTimesInfo
	 * @Description: 保存报班单课程课次信息
	 * @param orderCourseTimesInfo
	 *            报班单课次信息
	 * @return 返回保存的数目
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	int saveOrderCourseTimesInfo(TabOrderCourseTimesInfo orderCourseTimesInfo)
			throws Exception;

	/**
	 * 
	 * @Title: deleteOrderCourseTimesInfo
	 * @Description: 删除报班单课程课次信息
	 * @param orderCourseTimesInfo
	 *            课程课次信息
	 * @return 删除的课次数目
	 * @throws Exception
	 *             设定文件 int 返回类型
	 * 
	 */
	int deleteOrderCourseTimesInfo(TabOrderCourseTimesInfo orderCourseTimesInfo)
			throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseTimesInfo
	 * @Description: 查询给定课程的选课信息
	 * @param orderCourseTimesInfo
	 *            详单id
	 * @return 课程选课信息
	 * @throws Exception
	 *             设定文件 List<TabOrderCourseTimesInfo> 返回类型
	 * 
	 */
	List<TabOrderCourseTimesInfo> queryOrderCourseTimesInfo(
            TabOrderCourseTimesInfo orderCourseTimesInfo) throws Exception;
	/**
	 * 查询出目前给定的课程选课信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<TabOrderCourseTimesInfo> queryOrderCourseTimesInfoByMap(Map<String, Object> map) throws Exception;

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
	void updateChangeIdOnChangeTimes(Map<String, Object> map);
	/**
	 * 查询课次的考勤状态
	 * @return map change_course_id
	 * @throws Exception
	 */
	List<Map<String,Object>> queryCourseTimesAttendType(Map<String, Object> map) throws Exception;
	/**
	 * 更新课次的状态
	 * @param map
	 * @throws Exception
	 */
	void updateValidOrderCourseTimes(Map<String, Object> map) throws Exception;

	/**
	 * 转班，根据变动id添加转入课次信息
	 * @param changeId 变动ID
	 * @param orderCourseId 转入订单课程ID
	 */
	void addOrderCourseTimesByChangeId(@Param("changeId") Long changeId, @Param("orderCourseId") Long orderCourseId);

	/**
	 * 查询退费课次信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<TCChangeCourseTimes> queryTabChangeCourseTimesInfo(Map<String, Object> map) throws Exception;
	/**
	 * 保存退费课次信息
	 * @param tCCourseTimes
	 * @throws Exception
	 */
	void saveTcOrderCourseTimes(TCCourseTimes tCCourseTimes) throws Exception;
	/**
	 * 查询正式订单人员
	 * @param map
	 * order_detail_id 课程ID
	 * course_times  课次
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryCheckPeopleByCourseTimes(Map<String, Object> map) throws Exception;
	/**
	 * 查询待确认的人员
	 * @param map
	 * order_detail_id 课程ID
	 * course_times  课次
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> queryUncheckPeopleByCourseTimes(Map<String, Object> map) throws Exception;

	void updateInValidOrderCourseTimes(Long orderId);
}
