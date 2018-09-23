package com.edu.erp.orders.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.OrderCourseTimesInfoDao;
import com.edu.erp.dao.SortNumInfoDao;
import com.edu.erp.model.TCChangeCourseTimes;
import com.edu.erp.model.TCCourseTimes;
import com.edu.erp.model.TabOrderCourseTimesInfo;
import com.edu.erp.orders.service.OrderCourseTimesInfoService;

/**
 * @ClassName: OrderCourseTimesInfoServiceImpl
 * @Description: 报班订单课程课次信息服务
 *
 */
@Service(value = "orderCourseTimesInfoService")
public class OrderCourseTimesInfoServiceImpl implements
		OrderCourseTimesInfoService {

	@Resource(name = "orderCourseTimesInfoDao")
	private OrderCourseTimesInfoDao orderCourseTimesInfoDao;
	@Resource(name = "sortNumInfoDao")
	private SortNumInfoDao sortNumInfoDao;

	@Override
	public int saveOrderCourseTimesInfo(
			TabOrderCourseTimesInfo orderCourseTimesInfo) throws Exception {
		Assert.notNull(orderCourseTimesInfo);
		Assert.notNull(orderCourseTimesInfo.getStudent_id());
		Assert.notNull(orderCourseTimesInfo.getOrder_id());
		Assert.notNull(orderCourseTimesInfo.getOrder_detail_id());
		Assert.notNull(orderCourseTimesInfo.getCourse_times());

		return orderCourseTimesInfoDao
				.saveOrderCourseTimesInfo(orderCourseTimesInfo);
	}

	@Override
	public int deleteOrderCourseTimesInfo(
			TabOrderCourseTimesInfo orderCourseTimesInfo) throws Exception {
		Assert.notNull(orderCourseTimesInfo);
		Assert.notNull(orderCourseTimesInfo.getOrder_id());
		return orderCourseTimesInfoDao
				.deleteOrderCourseTimesInfo(orderCourseTimesInfo);
	}

	@Override
	public List<TabOrderCourseTimesInfo> queryTabOrderCourseTimesInfo(
			Long orderDetailId) throws Exception {
		Assert.notNull(orderDetailId);
		TabOrderCourseTimesInfo orderCourseTimesInfoParam = new TabOrderCourseTimesInfo();
		orderCourseTimesInfoParam.setOrder_detail_id(orderDetailId);
		return orderCourseTimesInfoDao
				.queryOrderCourseTimesInfo(orderCourseTimesInfoParam);
	}

	@Override
	public List<TabOrderCourseTimesInfo> queryOrderTimesInfo(Long orderId)
			throws Exception {
		Assert.notNull(orderId);
		return orderCourseTimesInfoDao.queryOrderTimesInfo(orderId);
	}

	@Override
	public void updateChangeIdOnChangeTimes(Map<String, Object> map) {
		orderCourseTimesInfoDao.updateChangeIdOnChangeTimes(map);
	}

	@Override
	public List<Map<String, Object>> queryCourseTimesAttendType(
			Map<String, Object> map) throws Exception {
		return orderCourseTimesInfoDao.queryCourseTimesAttendType(map);
	}

	@Override
	public void updateValidOrderCourseTimes(Map<String, Object> map)
			throws Exception {
		orderCourseTimesInfoDao.updateValidOrderCourseTimes(map);
	}

	@Override
	public void addOrderCourseTimesByChangeId(Long changeId, Long orderCourseId) throws Exception {
		orderCourseTimesInfoDao.addOrderCourseTimesByChangeId(changeId, orderCourseId);
	}

	@Override
	public List<TCChangeCourseTimes> queryTabChangeCourseTimesInfo(
			Map<String, Object> map) throws Exception {
		return orderCourseTimesInfoDao.queryTabChangeCourseTimesInfo(map);
	}

	@Override
	public void saveTcOrderCourseTimes(TCCourseTimes tCCourseTimes)
			throws Exception {
		orderCourseTimesInfoDao.saveTcOrderCourseTimes(tCCourseTimes);
		
	}
	/**
	 * 查询正式订单的课次人员列表
	 * 查询待确定订单课次的人员列表
	 * @param map
	 * order_detail_id
	 * course_times
	 * type  1:正式订单对应课次人员；2:待确认订单对应课次人员
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> queryCheckPeopleByCourseTimes(Map<String, Object> map) throws Exception {
		if("1".equals(map.get("type").toString())){
			return orderCourseTimesInfoDao.queryCheckPeopleByCourseTimes(map);
		}
		return orderCourseTimesInfoDao.queryUncheckPeopleByCourseTimes(map);
	}

	@Override
	public void updateInValidOrderCourseTimes(Long orderId) {
		this.orderCourseTimesInfoDao.updateInValidOrderCourseTimes(orderId);
	}

}
