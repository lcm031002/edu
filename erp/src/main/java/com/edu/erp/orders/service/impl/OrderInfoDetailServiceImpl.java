/**  
 * @Title: OrderInfoDetailServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:39:47
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.dao.OrderInfoDetailDao;
import com.edu.erp.model.CourseScheduling;
import com.edu.erp.model.PrivilegeRule;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TabOrderCourseTimesInfo;
import com.edu.erp.model.TabOrderInfoDetail;
import com.edu.erp.model.Teacher;
import com.edu.erp.orders.service.OrderCourseTimesInfoService;
import com.edu.erp.orders.service.OrderInfoDetailService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.teacher_manager.service.TeacherInfoService;

/**
 * @ClassName: OrderInfoDetailServiceImpl
 * @Description: 订单详情服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:39:47
 * 
 */
@Service(value = "orderInfoDetailService")
public class OrderInfoDetailServiceImpl implements OrderInfoDetailService {

	@Resource(name = "orderInfoDetailDao")
	private OrderInfoDetailDao orderInfoDetailDao;

	@Resource(name = "orderCourseTimesInfoService")
	private OrderCourseTimesInfoService orderCourseTimesInfoService;

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "privilegeRuleService")
	private PrivilegeRuleService privilegeRuleService;

	@Resource(name = "teacherInfoService")
	private TeacherInfoService teacherInfoService;

	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoDetailService#saveOrderInfoDetail
	 * (com.ebusiness.erp.model.TabOrderInfoDetail)
	 */
	@Override
	public int saveOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception {
		Assert.notNull(orderInfoDetail);
		Assert.notNull(orderInfoDetail.getOrder_id());
		Assert.notNull(orderInfoDetail.getEncoding());
		Assert.notNull(orderInfoDetail.getStudent_id());
		Assert.notNull(orderInfoDetail.getCourse_id());
		Assert.notNull(orderInfoDetail.getBusiness_type());
		Assert.notNull(orderInfoDetail.getFormer_sum_price());
		Assert.notNull(orderInfoDetail.getFormer_unit_price());
		Assert.notNull(orderInfoDetail.getDiscount_sum_price());
		Assert.notNull(orderInfoDetail.getDiscount_unit_price());
		Assert.notNull(orderInfoDetail.getCreate_user());
		orderInfoDetail.setCreate_time(new Date());
		if (orderInfoDetail.getFormer_sum_price() > 0
				&& orderInfoDetail.getDiscount_sum_price() != null
				&& orderInfoDetail.getDiscount_sum_price().intValue() == 0) {
			orderInfoDetail.setIs_send(1L);
		}
		int count = orderInfoDetailDao.saveOrderInfoDetail(orderInfoDetail);
		if (count > 0) {
			if (!CollectionUtils.isEmpty(orderInfoDetail.getOrderCourseTimes())) {
				for (TabOrderCourseTimesInfo orderCourseTimesInfo : orderInfoDetail
						.getOrderCourseTimes()) {
					orderCourseTimesInfo.setOrder_id(orderInfoDetail
							.getOrder_id());
					orderCourseTimesInfo.setOrder_detail_id(orderInfoDetail
							.getId());
					orderCourseTimesInfoService
							.saveOrderCourseTimesInfo(orderCourseTimesInfo);
				}
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoDetailService#deleteOrderInfoDetail
	 * (com.ebusiness.erp.model.TabOrderInfoDetail)
	 */
	@Override
	public int deleteOrderInfoDetail(TabOrderInfoDetail orderInfoDetail)
			throws Exception {
		Assert.notNull(orderInfoDetail);
		Assert.notNull(orderInfoDetail.getOrder_id());
		int count = orderInfoDetailDao.deleteOrderInfoDetail(orderInfoDetail);
		if (count > 0) {
			TabOrderCourseTimesInfo orderCourseTimesInfo = new TabOrderCourseTimesInfo();
			orderCourseTimesInfo.setOrder_id(orderInfoDetail.getOrder_id());
			orderCourseTimesInfoService
					.deleteOrderCourseTimesInfo(orderCourseTimesInfo);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.OrderInfoDetailService#
	 * queryTabOrderInfoDetail(java.lang.Long)
	 */
	@Override
	public List<TabOrderInfoDetail> queryTabOrderInfoDetail(Long orderId)
			throws Exception {
		Assert.notNull(orderId);
		TabOrderInfoDetail orderInfoDetailParam = new TabOrderInfoDetail();
		orderInfoDetailParam.setOrder_id(orderId);
		List<TabOrderInfoDetail> result = orderInfoDetailDao
				.queryTabOrderInfoDetail(orderInfoDetailParam);
		if (!CollectionUtils.isEmpty(result)) {
			// 查询订单详情的课程信息
			List<TCourse> tCourseList = courseService
					.queryOrderCourseList(orderId);
			// 查询教师信息
			List<Teacher> teacherList = teacherInfoService
					.queryOrderTeacher(orderId);
			// 查询排课信息
			List<CourseScheduling> courseSchedulingList = courseSchedulingService
					.queryOrderCourseScheduling(orderId);
			// 查询优惠规则
			List<PrivilegeRule> orderRuleList = privilegeRuleService
					.queryOrderRule(orderId);
			// 查询报名课次信息
			List<TabOrderCourseTimesInfo> orderCourseTimesList = orderCourseTimesInfoService
					.queryOrderTimesInfo(orderId);

			for (TabOrderInfoDetail detail : result) {
				// 查询报班课次信息
				List<TabOrderCourseTimesInfo> courseTimes = genTabOrderCourseTimesInfo(orderCourseTimesList,detail.getId());
				detail.setOrderCourseTimes(courseTimes);
				
				TCourse queryParam = new TCourse();
				queryParam.setId(detail.getCourse_id());
				queryParam.setStudentId(detail.getStudent_id());

				// 查询课程定义信息
				TCourse tCourse = genTCourse(tCourseList, detail.getCourse_id());
				
				// 设置课程老师
				Teacher teacher = genTeacher(teacherList,
						detail.getTeacher_id());
				if (tCourse != null) {
					tCourse.setTeacher(teacher);
				}
				
				//设置课程课次信息
				List<CourseScheduling> curCourseSchedulingList = genCourseSchedulingList(courseSchedulingList,detail.getCourse_id());
				if (tCourse != null) {
					tCourse.setCourseSchedulingList(curCourseSchedulingList);
				}
				
				detail.settCourseInfo(tCourse);

				if (detail.getRule_id() != null) {
					PrivilegeRule privilegeRule = genPrivilegeRule(orderRuleList ,detail.getRule_id());
					detail.setPrivilegeRule(privilegeRule);
				}

			}
		}
		return result;
	}
	
	private PrivilegeRule genPrivilegeRule(List<PrivilegeRule> orderRuleList ,Long ruleId)  {
		PrivilegeRule privilegeRuleReturn = null;
		if (!CollectionUtils.isEmpty(orderRuleList)) {
			for (PrivilegeRule privilegeRule: orderRuleList) {
				if (privilegeRule != null
						&& ruleId != null
						&& privilegeRule.getId() != null
						&& privilegeRule.getId().intValue() == ruleId.intValue()) {
					privilegeRuleReturn = privilegeRule;
				}
			}
		}
		return privilegeRuleReturn;
	}
	
	private List<TabOrderCourseTimesInfo> genTabOrderCourseTimesInfo(List<TabOrderCourseTimesInfo> orderCourseTimesList,Long orderDetailId){
		List<TabOrderCourseTimesInfo> courseTimesReturn = new ArrayList<TabOrderCourseTimesInfo>();
		if (!CollectionUtils.isEmpty(orderCourseTimesList)) {
			for (TabOrderCourseTimesInfo courseTime : orderCourseTimesList) {
				if (courseTime != null
						&& orderDetailId != null
						&& courseTime.getOrder_detail_id() != null
						&& courseTime.getOrder_detail_id().intValue() == orderDetailId.intValue()) {
					courseTimesReturn.add(courseTime);
				}
			}
		}
		
		return courseTimesReturn;
	}

	private TCourse genTCourse(List<TCourse> tCourseList, Long courseId) {
		TCourse tCourseReturn = null;
		if (!CollectionUtils.isEmpty(tCourseList)) {
			for (TCourse tCourse : tCourseList) {
				if (tCourse != null && courseId != null
						&& tCourse.getId() != null
						&& tCourse.getId().intValue() == courseId.intValue()) {
					tCourseReturn = tCourse;
				}
			}
		}

		return tCourseReturn;
	}

	private Teacher genTeacher(List<Teacher> teacherList, Long teacherId) {
		Teacher teacherReturn = null;
		if (!CollectionUtils.isEmpty(teacherList)) {
			for (Teacher teacher : teacherList) {
				if (teacher != null && teacherId != null
						&& teacher.getId() != null
						&& teacher.getId().intValue() == teacherId.intValue()) {
					teacherReturn = teacher;
				}
			}
		}
		return teacherReturn;
	}
	
	private List<CourseScheduling> genCourseSchedulingList(List<CourseScheduling> courseSchedulingList,Long courseId){
		
		List<CourseScheduling> courseSchedulingReturn = new ArrayList<CourseScheduling>();
		if (!CollectionUtils.isEmpty(courseSchedulingReturn)) {
			for (CourseScheduling courseScheduling : courseSchedulingReturn) {
				if (courseScheduling != null && courseId != null
						&& courseScheduling.getCourse_id() != null
						&& courseScheduling.getCourse_id().intValue() == courseId.intValue()) {
					courseSchedulingReturn.add(courseScheduling);
				}
			}
		}
		
		return courseSchedulingReturn;
	}

}
