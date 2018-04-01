/**  
 * @Title: OrderServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午6:18:35
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import com.edu.erp.dao.TCourseDao;
import com.edu.erp.orders.util.OrderCourseVerifyComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.dao.AttendanceDao;
import com.edu.erp.dao.OrderInfoDetailDao;
import com.edu.erp.dao.TCOrderCourseDao;
import com.edu.erp.dao.TOrderChangeDao;
import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.dao.TOrderCourseTimesDao;
import com.edu.erp.dao.TOrderDao;
import com.edu.erp.dao.TOrderLockDao;
import com.edu.erp.model.CourseScheduling;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TAttendanceHt;
import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TOrder;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.model.TOrderCourseTimesLog;
import com.edu.erp.model.TOrderLock;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.model.TabOrderInfoDetail;
import com.edu.erp.model.Teacher;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.teacher_manager.service.TeacherInfoService;
import com.github.pagehelper.Page;

import jxl.common.Logger;

/**
 * 
 * @ClassName: OrderServiceImpl
 * @Description: 正式订单服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月19日 下午6:18:35
 * 
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);

	@Resource(name = "tOrderDao")
	private TOrderDao tOrderDao;

	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;

	@Resource(name = "tOrderChangeDao")
	private TOrderChangeDao tOrderChangeDao;

	@Resource(name = "tOrderCourseTimesDao")
	private TOrderCourseTimesDao tOrderCourseTimesDao;

	@Resource(name = "tCOrderCourseDao")
	private TCOrderCourseDao tCOrderCourseDao;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "attendanceDao")
	private AttendanceDao attendanceDao;

	@Resource(name = "tOrderLockDao")
	private TOrderLockDao tOrderLockDao;

	@Resource(name = "orderInfoDetailDao")
	private OrderInfoDetailDao orderInfoDetailDao;

	@Resource(name = "tCourseDao")
	private TCourseDao tCourseDao;

	@Resource(name = "teacherInfoService")
	private TeacherInfoService teacherInfoService;

	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;

	@Resource(name = "studentAttendanceService")
	private StudentAttendanceService studentAttendanceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderService#queryOrderInfo(java.lang
	 * .Long)
	 */
	@Override
	public TOrder queryOrderInfo(Long orderId) throws Exception {
		Assert.notNull(orderId, "非法访问,订单id不能为空！");

		// 订单主单信息
		TOrder tOrder = tOrderDao.queryOrderInfo(orderId);

		if (tOrder == null) {
			log.error("order not found!orderId is " + orderId);
			throw new Exception("异常:订单不存在!");
		}
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("studentId", tOrder.getStudent_id());
		StudentInfo studentInfo = studentInfoService.queryStudentById(queryParam);

		if (studentInfo == null) {
			log.error("student not found!orderId is " + orderId);
			throw new Exception("异常:学生不存在!");
		}
		tOrder.setStudentInfo(studentInfo);

		// 订单详情信息
		List<TOrderCourse> orderDetails = tOrderCourseDao.queryOrderDetailInfo(orderId);
		if (CollectionUtils.isEmpty(orderDetails)) {
			log.error("order course not found!orderId is " + orderId);
			throw new Exception("异常:订单明细不存在!");
		} else {
			// 查询订单详情的课程信息
			List<TCourse> tCourseList = courseService.queryOrderCourseList(orderId);
			// 查询教师信息
			List<Teacher> teacherList = teacherInfoService.queryOrderTeacher(orderId);

			// 查询排课信息
			List<CourseScheduling> courseSchedulingList = courseSchedulingService.queryOrderCourseScheduling(orderId);

			// 查询订单考勤当前结果值
            List<TAttendance> attendanceList=new ArrayList<TAttendance>();
            if(tOrder.getBusiness_type()==2l){
                attendanceList = attendanceDao.queryYDYOrderAttendance(orderId);
			}else{
				attendanceList = attendanceDao.queryOrderAttendance(orderId);
			}
			// 查询订单考勤历史结果值
			List<TAttendanceHt> attendanceHtList = attendanceDao.queryOrderAttendanceHistory(orderId);

			// 查询订单批改记录
			List<TCOrderCourse> ordersChangeCourseList = tOrderChangeDao.queryOrderChangeTimes(orderId);
			for (TOrderCourse tOrderCourse : orderDetails) {
				// 查询课程定义信息
				TCourse tCourse = genTCourse(tCourseList, tOrderCourse.getCourse_id());

				if (tCourse != null) {
					// 设置课程老师
					Teacher teacher = genTeacher(teacherList, tCourse.getTeacher_id());
					tCourse.setTeacher(teacher);

					// 设置课程课次信息
					List<CourseScheduling> curCourseSchedulingList = genCourseSchedulingList(courseSchedulingList,
							tOrderCourse.getCourse_id());
					tCourse.setCourseSchedulingList(curCourseSchedulingList);

					tOrderCourse.setCourse(tCourse);
				}

				List<TAttendance> atts = genTAttendance(attendanceList, tOrderCourse.getId());
				tOrderCourse.settAttendanceList(atts);

				List<TAttendanceHt> attHist = genTAttendanceHt(attendanceHtList, tOrderCourse.getId());
				tOrderCourse.setAttHist(attHist);

				List<TCOrderCourse> ordersChangeList = genTCOrderCourse(ordersChangeCourseList, tOrderCourse.getId());
				tOrderCourse.setOrderCourseChange(ordersChangeList);
			}

		}
		tOrder.setOrderDetails(orderDetails);

		List<Map<String, Object>> premiumAuditList = tOrderChangeDao.queryPremiumAudit(orderId);
		tOrder.setPremiumAuditList(premiumAuditList);

		List<Map<String, Object>> unForwardList = tOrderChangeDao.queryOrderUnForward(orderId);
		tOrder.setUnForwardList(unForwardList);

		List<Map<String, Object>> forwardList = tOrderChangeDao.queryOrderForward(orderId);
		tOrder.setForwardList(forwardList);

		TabOrderInfo orderInfo = orderInfoService.queryTemporaryOrderInfo(orderId);

		if (orderInfo == null) {
			log.error("temporaryOrderInfo not found!orderId is " + orderId);
			throw new Exception("异常:临时订单不存在!");
		}
		tOrder.setTemporaryOrder(orderInfo);

		TOrderLock tOrderLock = tOrderLockDao.queryOrderLockStatus(orderId);
		tOrder.settOrderLock(tOrderLock);
		return tOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.OrderService#
	 * queryOrderCourseChangeTimesInfo (java.lang.Long)
	 */
	@Override
	public List<TOrderCourseTimesLog> queryOrderCourseChangeTimesInfo(Long orderDetailId) throws Exception {
		Assert.notNull(orderDetailId);

		List<TOrderCourseTimesLog> changeTimesLog = tOrderChangeDao.queryOrderChangeCourseTimes(orderDetailId);

		return changeTimesLog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.OrderService#
	 * queryOrderCourseSurplusCount (java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> queryOrderCourseSurplusCount(Long orderDetailId) throws Exception {
		Assert.notNull(orderDetailId);
		List<Map<String, Object>> courseTimes = new ArrayList<Map<String, Object>>();
		courseTimes = tOrderCourseTimesDao.queryOrderCourseTimesInfo(orderDetailId);
		return courseTimes;
	}

	private TCourse genTCourse(List<TCourse> tCourseList, Long courseId) {
		TCourse tCourseReturn = null;
		if (!CollectionUtils.isEmpty(tCourseList)) {
			for (TCourse tCourse : tCourseList) {
				if (tCourse != null && courseId != null && tCourse.getId() != null
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
				if (teacher != null && teacherId != null && teacher.getId() != null
						&& teacher.getId().intValue() == teacherId.intValue()) {
					teacherReturn = teacher;
				}
			}
		}
		return teacherReturn;
	}

	private List<TAttendance> genTAttendance(List<TAttendance> attendanceList, Long orderDetailId) {
		List<TAttendance> tAttendanceReturn = new ArrayList<TAttendance>();
		if (!CollectionUtils.isEmpty(attendanceList)) {
			for (TAttendance tAttendance : attendanceList) {
				if (tAttendance != null && orderDetailId != null && tAttendance.getOrder_course_id() != null
						&& tAttendance.getOrder_course_id().intValue() == orderDetailId.intValue()) {
					tAttendanceReturn.add(tAttendance);
				}
			}
		}
		return tAttendanceReturn;
	}

	private List<TCOrderCourse> genTCOrderCourse(List<TCOrderCourse> tCOrderCourseList, Long orderDetailId) {
		List<TCOrderCourse> tCOrderCourseReturn = new ArrayList<TCOrderCourse>();
		if (!CollectionUtils.isEmpty(tCOrderCourseList)) {
			for (TCOrderCourse tCOrderCourse : tCOrderCourseList) {
				if (tCOrderCourse != null && orderDetailId != null && tCOrderCourse.getOrder_course_id() != null
						&& tCOrderCourse.getOrder_course_id().intValue() == orderDetailId.intValue()) {
					tCOrderCourseReturn.add(tCOrderCourse);
				}
			}
		}
		return tCOrderCourseReturn;
	}

	private List<TAttendanceHt> genTAttendanceHt(List<TAttendanceHt> attendanceHtList, Long orderDetailId) {
		List<TAttendanceHt> tAttendanceReturn = new ArrayList<TAttendanceHt>();
		if (!CollectionUtils.isEmpty(attendanceHtList)) {
			for (TAttendanceHt tAttendanceHt : attendanceHtList) {
				if (tAttendanceHt != null && orderDetailId != null && tAttendanceHt.getOrder_course_id() != null
						&& tAttendanceHt.getOrder_course_id().intValue() == orderDetailId.intValue()) {
					tAttendanceReturn.add(tAttendanceHt);
				}
			}
		}
		return tAttendanceReturn;
	}

	private List<CourseScheduling> genCourseSchedulingList(List<CourseScheduling> courseSchedulingList, Long courseId) {

		List<CourseScheduling> courseSchedulingReturn = new ArrayList<CourseScheduling>();
		if (!CollectionUtils.isEmpty(courseSchedulingReturn)) {
			for (CourseScheduling courseScheduling : courseSchedulingReturn) {
				if (courseScheduling != null && courseId != null && courseScheduling.getCourse_id() != null
						&& courseScheduling.getCourse_id().intValue() == courseId.intValue()) {
					courseSchedulingReturn.add(courseScheduling);
				}
			}
		}

		return courseSchedulingReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderService#queryOrderChangeInfo(java.
	 * lang.Long)
	 */
	@Override
	public List<TOrderChange> queryOrderChangeInfo(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return tOrderChangeDao.queryOrderChange(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderService#queryStudentOrderCourse(
	 * java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TOrderCourse> queryStudentOrderCourse(Map<String, Object> queryParam)
			throws Exception {
		Assert.notNull(queryParam.get("studentId"));
		Assert.notNull(queryParam.get("businessType"));
		return tOrderCourseDao.queryStudentOrderCourse(queryParam);
	}

	@Override
	public Page<Map<String, Object>> queryPage(Map<String, Object> param) throws Exception {
		return tOrderDao.selectForPage(param);
	}

	/**
	 * 主订单课程商品
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public List<TOrderCourse> queryOrderCoursePage(Long orderId) throws Exception {
		return tOrderCourseDao.queryOrderCoursePage(orderId);
	}

	@Override
	public String queryTuiFeiByStudentId(Map<String, Object> param) throws Exception {
		String sum = "0";
		Object obj = tOrderDao.queryTuiFeiByStudentId(param);
		if (null != obj) {
			sum = obj.toString();
		}
		return sum;
	}

	@Override
	public List<Map<String, Object>> queryWfdOrderDetails(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return tOrderDao.queryWfdOrderDetails(param);
	}

	@Override
	public List<Map<String, Object>> qf_queryWfdOrderDetails(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return tOrderDao.qf_queryWfdOrderDetails(param);
	}

	/**
	 * 
	 * @Description: 查询该学生可以选择的课程
	 * @param tCourse
	 *            查询条件
	 * @param @throws
	 *            Exception 设定文件
	 * @return List<OrderDetailBusiness> 返回类型
	 */
	@Override
	public List<TCourse> queryCourseByBusiness(TCourse tCourse) throws Exception {
		return tOrderDao.queryCourseByBusiness(tCourse);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param tOrder
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateOrderStatus(TOrder tOrder) throws Exception {
		return tOrderDao.updateOrderStatus(tOrder);
	}

	@Override
	public int saveOrderInfo(TabOrderInfo orderInfo) throws Exception {
		TOrder tOrder = new TOrder();
		tOrder.setId(orderInfo.getId());
		tOrder.setOrder_no(orderInfo.getEncoding());
		tOrder.setStudent_id(orderInfo.getStudent_id());
		tOrder.setCity_id(orderInfo.getCity_id());
		tOrder.setBu_id(orderInfo.getBu_id());
		tOrder.setBranch_id(orderInfo.getBranch_id());
		tOrder.setAgent_id(orderInfo.getCreate_user());
		tOrder.setBusiness_type(orderInfo.getBusiness_type());
		tOrder.setFee_amount(Double.valueOf(orderInfo.getSum_price()));
		tOrder.setActural_amount(Double.valueOf(orderInfo.getActual_price()));
		tOrder.setCreate_date(orderInfo.getCreate_time());
		tOrder.setCreate_user(orderInfo.getCreate_user());
		tOrder.setAudit_date(DateUtil.stringToDate(orderInfo.getApprove_time(), "yyyy-mm-dd"));
		tOrder.setAudit_user(orderInfo.getLast_approver());
		tOrder.setOrder_type(orderInfo.getOrder_type());
		tOrder.setRemark(orderInfo.getRemark());
		tOrder.setImmediate_reduce(orderInfo.getImmediate_reduce());
		tOrder.setOrder_status(Long.valueOf(orderInfo.getStatus()));
		tOrder.setSource_order(orderInfo.getSource_order());
		tOrder.setFin_confirm_date(new Date());
		tOrder.setFin_confirm_user(orderInfo.getCreate_user());
		tOrder.setResource_rec_id(orderInfo.getResource_rec_id());
		return tOrderDao.saveOrderInfo(tOrder);
	}

	@Override
	public int saveOrderCourse(TabOrderInfoDetail tabOrderInfoDetail, TabOrderInfo orderInfo) throws Exception {
		TOrderCourse tOrderCourse = new TOrderCourse();
		tOrderCourse.setId(tabOrderInfoDetail.getId());
		tOrderCourse.setOrder_id(tabOrderInfoDetail.getOrder_id());
		tOrderCourse.setCourse_id(tabOrderInfoDetail.getCourse_id());
		tOrderCourse.setBranch_id(tabOrderInfoDetail.getBranch_id());
		tOrderCourse.setFormer_unit_price(Double.valueOf(tabOrderInfoDetail.getFormer_unit_price()));
		tOrderCourse.setFormer_sum_price(Double.valueOf(tabOrderInfoDetail.getFormer_sum_price()));
		tOrderCourse.setDiscount_unit_price(Double.valueOf(tabOrderInfoDetail.getDiscount_unit_price()));
		tOrderCourse.setDiscount_sum_price(Double.valueOf(tabOrderInfoDetail.getDiscount_sum_price()));
		tOrderCourse.setDiscount_rate(null);
		tOrderCourse.setDiscount_amount(Double.valueOf(tabOrderInfoDetail.getFormer_sum_price() - tabOrderInfoDetail.getDiscount_sum_price()));
		tOrderCourse.setManage_fee(Double.valueOf(tabOrderInfoDetail.getPre_forward()));
		tOrderCourse.setCourse_total_count(tabOrderInfoDetail.getCourse_total_count());
		tOrderCourse.setCourse_surplus_count(tabOrderInfoDetail.getCourse_surplus_count());
		tOrderCourse.setCourse_schedule_count(tabOrderInfoDetail.getCourse_surplus_count());
		tOrderCourse.setCourse_attend_count(0l);
		tOrderCourse.setCreate_user(tabOrderInfoDetail.getCreate_user());
		tOrderCourse.setCreate_time(tabOrderInfoDetail.getCreate_time());
		tOrderCourse.setUpdate_user(tabOrderInfoDetail.getUpdate_user());
		tOrderCourse.setUpdate_time(tabOrderInfoDetail.getUpdate_time());
		tOrderCourse.setQuit_flag(0l);
		tOrderCourse.setSurplus_cost(Double.valueOf(tabOrderInfoDetail.getDiscount_unit_price() * tabOrderInfoDetail.getCourse_surplus_count()));
		tOrderCourse.setOrder_type(orderInfo.getOrder_type());
		if (tabOrderInfoDetail.getOrder_course_type() != null
				&& tabOrderInfoDetail.getOrder_course_type() != orderInfo.getOrder_type()) {
			tOrderCourse.setOrder_type((tabOrderInfoDetail.getOrder_course_type()));// 合肥续单规则取订单详情里的订单科目类型
																					// Modified
																					// by
																					// lyk
		}
		return tOrderDao.saveOrderCourse(tOrderCourse);
	}

	@Override
	public Integer selectStuYdyTotalCourseCount(Map<String, Object> paramMap) throws Exception {
		return this.tOrderDao.selectStuYdyTotalCourseCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> queryWfdComboOrderDetails(Map<String, Object> param) throws Exception {
		return tOrderDao.queryWfdComboOrderDetails(param);
	}

	@Override
	public void attendedOrderTimes(Long orderId, Long userId) throws Exception {
		try {
			TabOrderInfo orderInfo = orderInfoService.queryOrderInfo(orderId);
			TabOrderInfoDetail detailQueryParam = new TabOrderInfoDetail();
			detailQueryParam.setOrder_id(orderInfo.getId());
			if (orderInfo.getBusiness_type().intValue() == 1) {
				List<TabOrderInfoDetail> orderDetailList = orderInfoDetailDao.queryOrderDetailInfo(detailQueryParam);
				for (TabOrderInfoDetail orderDetailBusiness : orderDetailList) {
					if (orderDetailBusiness.getCourse_surplus_count().intValue() > 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderDetailId", orderDetailBusiness.getId());
						List<Map<String, Object>> list = tOrderLockDao.queryCourseTimesByOrderDetail(map);

						CourseScheduling courseScheduling = new CourseScheduling();
						courseScheduling.setCourse_id(orderDetailBusiness.getCourse_id());

						List<CourseScheduling> data = courseSchedulingService.queryCourseScheduling(courseScheduling);
						Map<String, CourseScheduling> dataCourseSchedulingMap = new HashMap<String, CourseScheduling>();
						for (CourseScheduling courseSchedulingData : data) {
							dataCourseSchedulingMap.put("" + courseSchedulingData.getCourse_times(),
									courseSchedulingData);
						}

						for (Map<String, Object> mapObj1 : list) {
							String is_valid = mapObj1.get("is_valid") + "";
							String attend_type = mapObj1.get("attend_type") + "";
							String course_times = mapObj1.get("course_times") + "";
							if ("1".equals(is_valid) && "10".equals(attend_type)) {
								CourseScheduling courseSchedulingData = dataCourseSchedulingMap.get(course_times);
								Map<String, Object> attend = new HashMap<String, Object>();
								attend.put("attendanceId", null);
								attend.put("schedulingId", courseSchedulingData.getId()); // 排课表ID
								attend.put("userId", userId);
								attend.put("attendType", 12L);
								attend.put("remark", "冻结订单，到期考勤结转。");
								attend.put("branchId", orderInfo.getBranch_id());
								attend.put("courseDate",
										DateUtil.stringToDate("" + courseSchedulingData.getCourse_date(), "yyyyMMdd"));

								attend.put("studentId", orderInfo.getStudent_id());
								studentAttendanceService.attandanceSubmit(attend);
							}
						}
					}
				}
			} else if (orderInfo.getBusiness_type().intValue() == 3) {
				List<TabOrderInfoDetail> orderDetailList = orderInfoDetailDao.queryOrderDetailInfo(detailQueryParam);
				for (TabOrderInfoDetail orderDetailBusiness : orderDetailList) {
					if (orderDetailBusiness.getCourse_surplus_count().intValue() > 0) {
						for (int index = 0; index < orderDetailBusiness.getCourse_surplus_count(); index++) {
							Map<String, Object> courseScheduling = new HashMap<String, Object>();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("orderDetailId", orderDetailBusiness.getId());
							List<Map<String, Object>> list = (List<Map<String, Object>>) tOrderLockDao
									.queryCourseScheduling_wfd(map);
							if (!CollectionUtils.isEmpty(list)) {
								courseScheduling = list.get(0);
							}
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("p_order_course_id", orderDetailBusiness.getCourse_id());
							paramMap.put("p_attend_id", null);
							paramMap.put("p_student_id", orderInfo.getStudent_id());
							paramMap.put("p_attend_type", "31");
							paramMap.put("p_scheduling_id", courseScheduling.get("ID"));
							paramMap.put("p_course_date", DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
							paramMap.put("p_remark", "冻结订单，到期考勤结转");
							paramMap.put("p_input_user", userId);
							paramMap.put("p_branch_id", orderInfo.getBranch_id());
							paramMap.put("o_err_code", null);
							paramMap.put("o_err_desc", null);
							attendanceDao.wfdAttend(paramMap);
						}
					}
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 比较erp和双师订单课程数据
	 * @param erpOrderCourseMap erp订单课程数据
	 * @param mtOrderCourseMap 双师系统订单课程数据
	 * @param returnAllData 是否返回所有数据 false-只返回差异数据 true-返回所有数据
	 * @return 比较结果
	 * @throws Exception
	 */
	private List<Map<String, Object>> compareErpMtOrderCourseData(Map<String, Map<String, Object>> erpOrderCourseMap,
		Map<String, Map<String, Object>> mtOrderCourseMap, boolean returnAllData) throws Exception {
		if (CollectionUtils.isEmpty(erpOrderCourseMap) && CollectionUtils.isEmpty(mtOrderCourseMap)) {
			throw new Exception("没有订单课程数据，校验失败！");
		}

		List<Map<String, Object>> verifyResultMap = new ArrayList<Map<String, Object>>();
		Set<String> erpKeySet = null;
		Set<String> mtKeySet = null;
		if (CollectionUtils.isEmpty(erpOrderCourseMap) && !CollectionUtils.isEmpty(mtOrderCourseMap)) {
			mtKeySet = mtOrderCourseMap.keySet();
		} else if (!CollectionUtils.isEmpty(erpOrderCourseMap) && CollectionUtils.isEmpty(mtOrderCourseMap)) {
			erpKeySet = erpOrderCourseMap.keySet();
		} else {
			erpKeySet = erpOrderCourseMap.keySet();
			mtKeySet = mtOrderCourseMap.keySet();

			Set<String> sameKeySet = new HashSet<String>();
			sameKeySet.addAll(erpKeySet);
			sameKeySet.retainAll(mtKeySet); // erp和双师系统一致的数据
			if (!sameKeySet.isEmpty()) {
				if (returnAllData) {
					for (String key : sameKeySet) {
						Map<String, Object> erpCourseSeqMap = erpOrderCourseMap.get(key);
						Map<String, Object> mtCourseSeqMap = mtOrderCourseMap.get(key);
						erpCourseSeqMap.put("mtStudentId", mtCourseSeqMap.get("mtStudentId"));
						erpCourseSeqMap.put("mtStudentEncoding", mtCourseSeqMap.get("mtStudentEncoding"));
						erpCourseSeqMap.put("mtStudentName", mtCourseSeqMap.get("mtStudentName"));
						erpCourseSeqMap.put("mtOrderNo", mtCourseSeqMap.get("mtOrderNo"));
						erpCourseSeqMap.put("verifyResult", 0);
						verifyResultMap.add(erpCourseSeqMap);
					}
				}
				erpKeySet.removeAll(sameKeySet); // erp系统多出的数据
				mtKeySet.removeAll(sameKeySet); // 双师系统多出的数据
			}
		}

		if (!CollectionUtils.isEmpty(erpKeySet)) {
			for (String key : erpKeySet) {
				Map<String, Object> orderCourseMap = erpOrderCourseMap.get(key);
				orderCourseMap.put("verifyResult", 1);
				orderCourseMap.put("setColor", "true");
				verifyResultMap.add(orderCourseMap);
			}
		}

		if (!CollectionUtils.isEmpty(mtKeySet)) {
			for (String key : mtKeySet) {
				Map<String, Object> orderCourseMap = mtOrderCourseMap.get(key);
				orderCourseMap.put("verifyResult", 2);
				orderCourseMap.put("setColor", "true");
				verifyResultMap.add(orderCourseMap);
			}
		}
		return verifyResultMap;
	}

	private Map<String, Map<String, Object>> handleOrderCoruseList(List<Map<String, Object>> erpOrderCourseList) {
		if (CollectionUtils.isEmpty(erpOrderCourseList)) {
			return null;
		}

		Map<String, Map<String, Object>> orderCourseMap = new HashMap<String, Map<String, Object>>();
		StringBuilder keyBuilder = new StringBuilder();
		for (Map<String, Object> erpOrderCourseMap : erpOrderCourseList) {
			keyBuilder.delete(0, keyBuilder.length());
			keyBuilder.append(erpOrderCourseMap.get("courseId").toString()).append(erpOrderCourseMap.get("seq").toString())
				.append(erpOrderCourseMap.get("erpStudentId").toString()).append(erpOrderCourseMap.get("erpOrderNo").toString());
			orderCourseMap.put(keyBuilder.toString(), erpOrderCourseMap);
		}
		return orderCourseMap;
	}

}
