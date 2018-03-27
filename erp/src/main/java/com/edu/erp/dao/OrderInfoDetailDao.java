package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TabOrderInfoDetail;

/***
 * Description ： DAO 接口
 * 
 * Author ：
 * 
 * Date :
 */
@Repository("orderInfoDetailDao")
public interface OrderInfoDetailDao {
	/**
	 * 
	 * @Title: deleteOrderInfoDetail
	 * @Description: 删除订单的详细单据
	 * @param orderInfoDetail 待删除详细单据的订单
	 * @return 已经删除的详细单据条目
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int deleteOrderInfoDetail(TabOrderInfoDetail orderInfoDetail) throws Exception;
	
	/***
	 * 保存信息（2014-09-11）
	 * 
	 * @param orderInfoDetail
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 *             抛出系统异常
	 */
	int saveOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception;

	/***
	 * 保存信息（2014-09-11）
	 * 
	 * @param orderInfoDetail
	 *            信息对象
	 * @return int 影响行数
	 * @throws Exception
	 */
	int updateOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception;

	/**
	 * 
	 * @Description: 查询给定的订单的课程详情
	 * @param order_id
	 *            订单ID
	 * @return List<OrderDetailBusiness> 返回类型
	 * @throws Exception
	 *             设定文件
	 */
	List<TabOrderInfoDetail> queryTabOrderInfoDetail(TabOrderInfoDetail orderInfoDetailParam) throws Exception;

	/**
	 * 
	 * 
	 * @Description: 查询给定的订单的详情信息
	 * @param order_id
	 *            订单ID
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TabOrderInfoDetail> queryOrderDetailInfo(TabOrderInfoDetail param)
			throws Exception;

	/**
	 * 
	 * @Description: 查找给定学生的订单的详情
	 * 
	 * @param student_id
	 *            学生ID
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TabOrderInfoDetail> queryStudentOrderDetailInfo(Long student_id)
			throws Exception;

	/**
	 * 
	 * @Description: 查找给定学生的订单的详情
	 * 
	 * @param detailCondition
	 *            查询条件
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TabOrderInfoDetail> queryStudentOrderDetailInfoBJK(TabOrderInfoDetail detailCondition) throws Exception;

	/**
	 * 
	 * @Description: 查找给定学生的订单的详情
	 * 
	 * @param courseCondition
	 *            查询条件
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TabOrderInfoDetail> queryStudentOrderDetailInfoYDY(TabOrderInfoDetail courseCondition) throws Exception;

	/**
	 * 
	 * @Description: 查找给定学生的订单的详情
	 * 
	 * @param param
	 *            详细信息
	 * @throws Exception
	 *             设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	List<TabOrderInfoDetail> queryStudentOrderDetailInfoWFD(TabOrderInfoDetail param) throws Exception;

	/**
	 * 
	 * @Description: 查询给定的学生ID对应的课程信息
	 * 
	 * @param stuent_id
	 *            学生ID
	 * 
	 * @throws Exception
	 *             设定文件
	 * 
	 * @return List<OrderDetailBusiness> 返回类型
	 * 
	 */
	List<TabOrderInfoDetail> queryCourseInfoByTearcher(Long stuent_id)
			throws Exception;
	
	void updateOrderWfdStardate(Map<String, Object> param)throws Exception;
	/**
	 * 根据课程ID查询出未支付类型的课程信息
	 * @param param course_id 课程ID
	 * @return
	 * @throws Exception
	 */
	List<TabOrderInfoDetail> queryCourseInfoDetailByCourseId(Map<String, Object> param)throws Exception;
}
