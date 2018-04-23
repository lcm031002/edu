/**  
 * @Title: OrderInfoServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月3日 下午6:39:27
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.orders.ext.IOrderYDY;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderCouponRelService;
import com.edu.erp.orders.service.OrderInfoDetailService;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderPayCostService;
import com.edu.erp.orders.service.OrderRenewalService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.student.service.TCourseListeningService;
import com.edu.erp.teacher_manager.service.TeacherInfoService;
import com.edu.erp.util.DetailBusinessInfoFormat;
import com.edu.erp.util.DxbCityCfg;
import com.edu.erp.util.WorkflowHelper;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;

import jxl.common.Logger;

/**
 * 
 * @ClassName: OrderInfoServiceImpl
 * @Description: 订单服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:39:27
 * 
 */
@Service(value = "orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
	private static final Logger log = Logger
			.getLogger(OrderInfoServiceImpl.class);

	@Resource(name = "iOrderYDY")
	private IOrderYDY iOrderYDY;
	
	@Resource(name = "orderInfoDao")
	private OrderInfoDao orderInfoDao;
	@Resource(name = "tOrderLockDao")
	private TOrderLockDao tOrderLockDao;

	@Resource(name = "orderInfoDetailService")
	private OrderInfoDetailService orderInfoDetailService;

	@Resource(name = "orderPayCostService")
	private OrderPayCostService orderPayCostService;

	@Resource(name = "orderCouponRelService")
	private OrderCouponRelService orderCouponRelService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "privilegeRuleService")
	private PrivilegeRuleService privilegeRuleService;

	@Resource(name = "studentAttendanceService")
	private StudentAttendanceService attendanceService;

	@Resource(name = "tCourseListeningService")
	private TCourseListeningService tCourseListeningService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	
	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "orderChangeService")
	private OrderChangeService orderChangeService;
	
	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;
	
	@Resource(name = "teacherInfoService")
    private TeacherInfoService teacherInfoService;
	
	@Resource(name = "gcOrderDao")
	private GcOrderDao gcOrderDao;

	@Resource(name = "studentInfoDao")
	private StudentInfoDao studentInfoDao;

	@Resource(name = "tOrderCourseTimesDao")
	private TOrderCourseTimesDao tOrderCourseTimesDao;

	@Resource(name = "couponInfoDao")
	private CouponInfoDao couponInfoDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#saveOrderInfo(com.ebusiness
	 * .erp.model.TabOrderInfo)
	 */
	@Override
	public int saveOrderInfo(TabOrderInfo orderInfo, Account account,
			OrgModel orgModel, ProcessEngine processEngine) throws Exception {
		Assert.notNull(orderInfo);

		Assert.notNull(orderInfo.getStudent_id());
		Assert.notNull(orderInfo.getBusiness_type());
		Assert.notNull(orderInfo.getSum_price());
		Assert.notNull(orderInfo.getActual_price());
		Assert.notNull(orderInfo.getBranch_id());
		Assert.notNull(orderInfo.getBu_id());
		Assert.notNull(orderInfo.getCity_id());
		Assert.notNull(orderInfo.getCreate_user());

		orderInfo.setEncoding(genEncoding(orderInfo.getBusiness_type()));
		orderInfo.setReturn_premium_status(0L);
		orderInfo.setCheck_status(1L);
		orderInfo.setValid_status(1L);
		orderInfo.setStart_status(0L);
		orderInfo.setOrder_type(1L);
		orderInfo.setCreate_time(new Date());
		orderInfo.setPay_status(0L);

		// 续单处理
//		OrderRenewalService.processRenewal(orderInfo);

		//校验是否可以报班，是否已经满班
		List<TabOrderInfoDetail> orderDeatils=orderInfo.getDetails();
		for(TabOrderInfoDetail tabOrderInfoDetail:orderDeatils){
			CourseScheduling queryCondition = new CourseScheduling();
			queryCondition.setCourse_id(tabOrderInfoDetail.getCourse_id());
			queryCondition.setStudent_id(tabOrderInfoDetail.getStudent_id());
			List<TabOrderCourseTimesInfo> courseTimeList=tabOrderInfoDetail.getOrderCourseTimes();
			//查询排课信息
			List<CourseScheduling> result = courseSchedulingService.queryConfirmCourseScheduling(queryCondition);
			//查询对应的课次是否有满班的情况
//			courseSchedulingService.fillPeopleCountIntoCourseScheduling(result);
			
			for(CourseScheduling courseScheduling:result){
				for(TabOrderCourseTimesInfo tabOrderCourseTimesInfo:courseTimeList){
					if(courseScheduling.getCourse_times()==tabOrderCourseTimesInfo.getCourse_times()&&courseScheduling.getIs_ordered()!=0L){
						throw new Exception("不好意思，(课程:"+tabOrderInfoDetail.getCourse_name()+";课次:"+courseScheduling.getCourse_times()+";状态:"+CourseScheduling.BMStatuEnum.getDesc(courseScheduling.getIs_ordered())+"),请重新报名!");
					}
				}
			}
		}
		
		int count = orderInfoDao.saveOrderInfo(orderInfo);
		// 判断是否存在资源信息记录表ID,如果存在，将订单信息回填到CRM线索跟踪
		if(orderInfo.getResource_rec_id() != null) {
		    //先去gc_order 中查询一下
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("resource_rec_id", orderInfo.getResource_rec_id());
			//查询是否已经存在成单
			List<GcOrder> list=	gcOrderDao.queryGcOrderForAdd(map);
			if(CollectionUtils.isEmpty(list)){
				GcOrder gcOrder = new GcOrder();
				gcOrder.setCreate_user(orderInfo.getCreate_user());
				gcOrder.setResource_rec_id(orderInfo.getResource_rec_id());
				gcOrder.setOrder_no(orderInfo.getEncoding());
				gcOrder.setFee_amount(orderInfo.getActual_price().doubleValue());
				gcOrder.setCreate_time(orderInfo.getCreate_time());
				gcOrder.setOrder_time(orderInfo.getCreate_time());
				//查询订单总课时
				Long totalCourseTimes = 0L;
				for (TabOrderInfoDetail tabOrderInfoDetail : orderDeatils) {
					totalCourseTimes+=tabOrderInfoDetail.getCourse_total_count();
				}
				gcOrder.setCourse_count(totalCourseTimes.intValue());
    			//订单回填
				gcOrderDao.insertGcOrder(gcOrder);
				//查询线索跟踪记录
				Long channelId = gcOrderDao.queryChannelIdByGcResourceRecId(orderInfo.getResource_rec_id());
				//查询学员姓名
				StudentInfo stu = studentInfoDao.queryById(orderInfo.getStudent_id());
				//查询资源id
				Long resourceId = gcOrderDao.queryResourceIdByResourceRecId(orderInfo.getResource_rec_id());
				//更新资源的学员姓名
				gcOrderDao.updateGcResource(resourceId,stu.getStudent_name());
				//更新为成单
				gcOrderDao.updateResourceRecToSuccess(channelId, orderInfo.getResource_rec_id());
				//记录线索跟踪处理记录
				GcResourceRecProc gcResourceRecProc = new GcResourceRecProc();
				gcResourceRecProc.setResource_rec_id(orderInfo.getResource_rec_id());
				gcResourceRecProc.setOpt_type(GcResourceRecProc.optTypeEnum.ORDER.getOptType());
				gcResourceRecProc.setBusi_id(gcOrder.getId());
				gcResourceRecProc.setCreate_user(gcOrder.getCreate_user());
				gcResourceRecProc.setCreate_time(gcOrder.getCreate_time());
				gcResourceRecProc.setBusi_content(gcOrder.toString());
				gcOrderDao.insertResourceProcessRec(gcResourceRecProc);
				//将学员ID保存到线索记录表(GC_RESOURCE_REC)中
				gcOrderDao.updateStudentIdToGcResourceRec(orderInfo.getResource_rec_id(), orderInfo.getStudent_id());
				log.info("线索成功转成成单" + gcOrder.toString());
			}else{
				log.error("该线索跟踪记录已经存在成单 : "+list.toString());
				throw new RuntimeException("该线索跟踪记录已经存在成单" +list.toString() );
			}
		}
		
		if (count != 0) {
			if (!CollectionUtils.isEmpty(orderInfo.getDetails())) {
				for (TabOrderInfoDetail detail : orderInfo.getDetails()) {
					detail.setOrder_id(orderInfo.getId());
					detail.setEncoding(orderInfo.getEncoding());
					detail.setCreate_user(orderInfo.getCreate_user());
					orderInfoDetailService.saveOrderInfoDetail(detail);
				}
			}

			if (!CollectionUtils.isEmpty(orderInfo.getCoupon_rels())) {
				for (OrderCouponRel rel : orderInfo.getCoupon_rels()) {
					rel.setOrder_id(orderInfo.getId());
					orderCouponRelService.saveOrderCouponRel(rel);
					CouponInfo couponInfo = new CouponInfo();
					couponInfo.setId(rel.getCoupon_id());
					couponInfo.setStatus(0);//优惠券已使用
					couponInfoDao.update(couponInfo);
				}
			}

		}

		// 如果不需要审批，则直接通过
		if ("saveOrder".equals(orderInfo.getSaveType())) {
			startProcess(account, orgModel, orderInfo, processEngine);
		}
		return count;
	}

	private String genEncoding(Long businessType) {
		if (businessType == 1L) {
			return EncodingSequenceUtil.getSequenceNum(9L);
		} else if (businessType == 2L) {
			return EncodingSequenceUtil.getSequenceNum(10L);
		} else if (businessType == 3L) {
			return EncodingSequenceUtil.getSequenceNum(11L);
		} else {
			throw new IllegalArgumentException("error business type");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#startProcess(com.ebusiness
	 * .cas.client.common.model.Account,
	 * com.ebusiness.cas.client.common.model.OrgModel,
	 * com.ebusiness.erp.model.TabOrderInfo, org.jbpm.api.ProcessEngine)
	 */
	@Override
	public void startProcess(Account account, OrgModel orgModel,
			TabOrderInfo orderInfo, ProcessEngine processEngine)
			throws Exception {
		// 判断是否需要走工作流
		if (isNeedWorkflow(orderInfo, processEngine)) {
			startProcessAfterPassOrder(account, orgModel, orderInfo,
						processEngine);
		} else {
			// 不需要走工作流，直接通过
			orderInfo.setApproved(0);
			orderInfo.setCheck_status(3L);
			updateOrderApproved(orderInfo);
		}
	}

	private void startProcessAfterPassOrder(Account account, OrgModel orgModel,
			TabOrderInfo orderInfo, ProcessEngine processEngine)
			throws Exception {

		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setBu_id(orgModel.getBuId());
		studentInfo.setBranch_id(orgModel.getId());
		studentInfo.setId(orderInfo.getStudent_id());

		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("studentId", orderInfo.getStudent_id());
		StudentInfo studentBusiness = studentInfoService
				.queryStudentById(queryParam);
		
		orderInfo.setStudent_name(studentBusiness.getStudent_name());

		Map<String, Object> mapps = new HashMap<String, Object>();
		mapps.put("order_id", orderInfo.getId());
		mapps.put("student_id", orderInfo.getStudent_id());
		mapps.put("remark", orderInfo.getRemark());
		mapps.put("wf_remark", orderInfo.getRemark());
		mapps.put("branch_id", orderInfo.getBranch_id());
		mapps.put("bu_id", orderInfo.getBu_id());
		mapps.put("student_name", studentBusiness.getStudent_name());
		mapps.put("order_encoding", orderInfo.getEncoding());
		mapps.put("student_encoding", studentBusiness.getEncoding());

		mapps.put("laststep_user_id", account.getId());
		mapps.put(
				"workbenchURL",
				"/apps/student_index/index-student#/order-detail/"
						+ orderInfo.getBusiness_type() + "/"
						+ orderInfo.getStudent_id() + "/" + orderInfo.getId());

		mapps.put("businessDetailInfo",
				DetailBusinessInfoFormat.orderPromString(orderInfo));

		// 优惠规则
		if (null != orderInfo.getRule_id() && orderInfo.getRule_id() > 0) {
			if (orderInfo != null && orderInfo.getCheck_status() != null
					&& orderInfo.getCheck_status() != 1) {
				log.error("订单状态异常，不能提交审批！id:" + orderInfo.getId());
				throw new Exception("订单状态异常，不能再次提交审批!");
			} else {
				orderInfo.setApproved(1); // 走了审批
				orderInfo.setCheck_status(2L);
				updateOrderApproved(orderInfo);
				PrivilegeRule privilegeRuleBusiness = privilegeRuleService
						.queryByRuleId(orderInfo.getRule_id());
				if (null == privilegeRuleBusiness) {
				    log.error("优惠规则不存在，不能提交审批！id:" + orderInfo.getId());
	                throw new Exception("优惠规则不存在，不能提交审批!");
				}
				Map<String, Object> userApplication = new HashMap<String, Object>();
				userApplication.put("APPLICATION_ID",
						orderInfo.getCreate_user());
				StringBuilder application = new StringBuilder("订单优惠审批：");
				application.append("学生ID[");
				application.append(orderInfo.getStudent_name()
						+ orderInfo.getStudent_id());
				application.append("]");
				application.append("订单[");
				application.append(orderInfo.getEncoding());
				application.append("]");
				application.append("订单总价[");
				application.append(orderInfo.getActual_price());
				application.append(".00元]");
				application.append("订单优惠信息[");
				String detailInfo = genDetailReduceInfo(orderInfo);
				application.append(detailInfo);

				application.append("订单使用优惠：");
				application.append(privilegeRuleBusiness.getRule_name());
				application.append("；]");

				userApplication.put("APPLICATION", application.toString());
				userApplication.put("STATUS", 1L);
				userApplication.put("CREATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put("REMARK", orderInfo.getRemark());
				userApplication.put("CURRENT_STATE", "申请已提交");
				userApplication.put("CURRENT_STEP", "申请提交");
				if (null != account) {
					userApplication.put("CURRENT_MAN", account.getUserName());
				}
				userApplication.put("UPDATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put(
						"WORKURL",
						"#/order-detail/" + orderInfo.getBusiness_type() + "/"
								+ orderInfo.getStudent_id() + "/"
								+ orderInfo.getId());
				userTaskService.insertApplication(userApplication);
				mapps.put("application_id", userApplication.get("id") + "");
				mapps.put("sponsor", account.getId());
				// 报班流程
				// workflow.baoban2.11 = true; erpv5.DXB_enter_class_03
				ProcessInstance processInstance = genProcessInstance(orderInfo,
						mapps, processEngine);

				processEngine.getExecutionService().createVariables(
						processInstance.getId(), mapps, true);
			}
		}
		// 立减
		else if (orderInfo.getRule_id() == null
				&& null != orderInfo.getImmediate_reduce()
				&& orderInfo.getImmediate_reduce() > 0) {
			if (orderInfo != null && orderInfo.getCheck_status() != null
					&& orderInfo.getCheck_status() != 1) {
				log.error("订单状态异常，不能提交审批！id:" + orderInfo.getId());
				throw new Exception("订单状态异常，不能再次提交审批!");
			} else {
				orderInfo.setApproved(1); // 走了审批
				orderInfo.setCheck_status(2L);
				updateOrderApproved(orderInfo);
				Map<String, Object> userApplication = new HashMap<String, Object>();
				userApplication.put("APPLICATION_ID",
						orderInfo.getCreate_user());
				StringBuilder application = new StringBuilder("订单优惠立减审批：");
				application.append("学生ID[");
				application.append(orderInfo.getStudent_name()
						+ orderInfo.getStudent_id());
				application.append("]");
				application.append("订单[");
				application.append(orderInfo.getEncoding());
				application.append("]");
				application.append("订单总价[");
				application.append(orderInfo.getActual_price());
				application.append(".00元]");
				application.append("优惠信息[");
				String detailInfo = genDetailReduceInfo(orderInfo);
				application.append(detailInfo);

				application.append("订单整体立减金额：");
				application.append(orderInfo.getImmediate_reduce());
				application.append("元；]");

				userApplication.put("APPLICATION", application.toString());
				userApplication.put("STATUS", 1L);
				userApplication.put("CREATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put("REMARK", orderInfo.getRemark());
				userApplication.put("CURRENT_STATE", "申请已提交");
				userApplication.put("CURRENT_STEP", "申请提交");
				userApplication.put("UPDATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put(
						"WORKURL",
						"#/order-detail/" + orderInfo.getBusiness_type() + "/"
								+ orderInfo.getStudent_id() + "/"
								+ orderInfo.getId());
				userTaskService.insertApplication(userApplication);
				mapps.put("application_id", userApplication.get("id") + "");
				mapps.put("sponsor", account.getId());
				ProcessInstance processInstance = genProcessInstance(orderInfo,
						mapps, processEngine);
				processEngine.getExecutionService().createVariables(
						processInstance.getId(), mapps, true);
			}

		} else {

			// 查询详情
			String detailInfo = genDetailReduceInfo(orderInfo);
			if (StringUtils.isNotBlank(detailInfo)) {
				orderInfo.setApproved(1); // 走了审批
				orderInfo.setCheck_status(2L);
				updateOrderApproved(orderInfo);
				Map<String, Object> userApplication = new HashMap<String, Object>();
				userApplication.put("APPLICATION_ID",
						orderInfo.getCreate_user());
				StringBuilder application = new StringBuilder("订单优惠审批：");
				application.append("学生ID[");
				application.append(orderInfo.getStudent_name()
						+ orderInfo.getStudent_id());
				application.append("]");
				application.append("订单[");
				application.append(orderInfo.getEncoding());
				application.append("]");
				application.append("订单总价[");
				application.append(orderInfo.getActual_price());
				application.append(".00元]");
				application.append("订单优惠信息[");
				application.append(detailInfo);
				application.append("]");

				userApplication.put("APPLICATION", application.toString());
				userApplication.put("STATUS", 1L);
				userApplication.put("CREATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put("REMARK", orderInfo.getRemark());
				userApplication.put("CURRENT_STATE", "申请已提交");
				userApplication.put("CURRENT_STEP", "申请提交");
				userApplication.put("UPDATETIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				userApplication.put(
						"WORKURL",
						"#/order-detail/" + orderInfo.getBusiness_type() + "/"
								+ orderInfo.getStudent_id() + "/"
								+ orderInfo.getId());
				userTaskService.insertApplication(userApplication);
				mapps.put("application_id", userApplication.get("id") + "");
				mapps.put("sponsor", account.getId());
				ProcessInstance processInstance = genProcessInstance(orderInfo,
						mapps, processEngine);

				processEngine.getExecutionService().createVariables(
						processInstance.getId(), mapps, true);
			} else {
				orderInfo.setApproved(0);// 不走审批
				orderInfo.setCheck_status(3L);
				StringBuilder application = new StringBuilder("订单优惠审批：");
				application.append("学生ID[");
				application.append(orderInfo.getStudent_name()
						+ orderInfo.getStudent_id());
				application.append("]");
				application.append("订单[");
				application.append(orderInfo.getEncoding());
				application.append("]");
				application.append("订单原总价[");
				application.append(orderInfo.getSum_price());
				application.append(".00元]");
				application.append("订单总价[");
				application.append(orderInfo.getActual_price());
				application.append(".00元]");

			}
		}
	}

	private ProcessInstance genProcessInstance(TabOrderInfo orderBusiness,
			Map<String, Object> mapps, ProcessEngine processEngine) {
		ProcessInstance processInstance = null;
		String prefix_info = "workflow.baoban2." + orderBusiness.getBu_id();
		String flag = DxbCityCfg.getInstance().getConfigItem(prefix_info,
				"false");
		if ("true".equals(flag)) {
			processInstance = processEngine.getExecutionService()
					.startProcessInstanceByKey("erpv5.DXB_enter_class_03",
							mapps);
		} else {
			processInstance = processEngine.getExecutionService()
					.startProcessInstanceByKey("erpv5.DXB_enter_class_02",
							mapps);
		}
		return processInstance;
	}

	private String genDetailReduceInfo(TabOrderInfo orderInfo) throws Exception {
		StringBuilder application = new StringBuilder("");
		if (!CollectionUtils.isEmpty(orderInfo.getDetails())) {
			for (TabOrderInfoDetail orderDetailBusiness : orderInfo
					.getDetails()) {
				if (orderDetailBusiness.getRule_id() != null) {
					PrivilegeRule privilegeRuleBusiness = privilegeRuleService
							.queryByRuleId(orderDetailBusiness.getRule_id());
					TCourse course = new TCourse();
					course.setId(orderDetailBusiness.getCourse_id());
					course = courseService.queryCourseByID(course);
					application.append("课程：" + course.getCourse_name());
					if (null == privilegeRuleBusiness) {
						application.append(",警告：优惠规则可能已经下架。");
					} else {
						application.append(",使用优惠规则："
								+ privilegeRuleBusiness.getRule_name());
					}

					application.append("");
				} else if (null != orderDetailBusiness.getImmediate_reduce()
						&& orderDetailBusiness.getImmediate_reduce() > 0) {
					TCourse course = new TCourse();
					course.setId(orderDetailBusiness.getRule_id());
					course = courseService.queryCourseByID(course);
					application.append("课程：" + course.getCourse_name());
					application.append(",立减金额："
							+ orderDetailBusiness.getImmediate_reduce());
					application.append("元");
				}
			}
		}

		return application.toString();
	}

	private boolean isNeedWorkflow(TabOrderInfo tabOrderInfo,
			ProcessEngine processEngine) throws Exception {
		// 立减和优惠规则需要走审批
		if (tabOrderInfo.getRule_id() != null
				|| (null != tabOrderInfo.getImmediate_reduce() && tabOrderInfo
						.getImmediate_reduce() > 0)) {
			String prefix_info = "workflow.baoban2." + tabOrderInfo.getBu_id();
			String flag = DxbCityCfg.getInstance().getConfigItem(prefix_info,
					"false");

			if ((!"true".equals(flag))
					&& !WorkflowHelper.isDeployed(processEngine,
							"erpv5.DXB_enter_class_02")) {
				throw new Exception("流程erpv5.DXB_enter_class_02尚未发布！");
			}

			if (("true".equals(flag))
					&& !WorkflowHelper.isDeployed(processEngine,
							"erpv5.DXB_enter_class_03")) {
				throw new Exception("流程erpv5.DXB_enter_class_03尚未发布！");
			}

			return true;

		} else {
			// 判断是否有单科优惠存在 
			if (!CollectionUtils.isEmpty(tabOrderInfo.getDetails())) {
				for (TabOrderInfoDetail orderDetailBusiness : tabOrderInfo.getDetails()) {
					if (orderDetailBusiness.getRule_id() != null) {
						return true;
					} else if (null != orderDetailBusiness.getImmediate_reduce()
							&& orderDetailBusiness.getImmediate_reduce() > 0) {
						return true;
					}
				}
			}
			
			// 没有优惠
			tabOrderInfo.setCheck_status(3L);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#updateOrderInfo(com
	 * .ebusiness.erp.model.TabOrderInfo)
	 */
	@Override
	public int updateOrderInfo(TabOrderInfo orderInfo) throws Exception {
		Assert.notNull(orderInfo);

		Assert.notNull(orderInfo.getStudent_id());
		Assert.notNull(orderInfo.getBusiness_type());
		Assert.notNull(orderInfo.getSum_price());
		Assert.notNull(orderInfo.getActual_price());
		Assert.notNull(orderInfo.getBranch_id());
		Assert.notNull(orderInfo.getBu_id());
		Assert.notNull(orderInfo.getCity_id());
		Assert.notNull(orderInfo.getCreate_user());

		if (orderInfo.getReturn_premium_status() == null) {
			orderInfo.setReturn_premium_status(0L);
		}
		if (orderInfo.getCheck_status() == null) {
			orderInfo.setCheck_status(1L);
		}
		if (orderInfo.getValid_status() == null) {
			orderInfo.setValid_status(1L);
		}
		if (orderInfo.getStart_status() == null) {
			orderInfo.setStart_status(0L);
		}

		orderInfo.setOrder_type(1L);

		// 续单处理 FIXME
		OrderRenewalService.processRenewal(orderInfo);

		if (orderInfo.getPay_status() == null) {
			orderInfo.setPay_status(0L);
		}

		orderInfo.setUpdate_user(orderInfo.getCreate_user());
		orderInfo.setUpdate_time(new Date());

		int count = orderInfoDao.updateOrderInfo(orderInfo);
		if (count != 0) {
			if (orderInfo.getPay_status().longValue() != 1) { // 订单支付，不再重新生成订单明细
				TabOrderInfoDetail orderInfoDetail = new TabOrderInfoDetail();
				orderInfoDetail.setOrder_id(orderInfo.getId());
				orderInfoDetailService.deleteOrderInfoDetail(orderInfoDetail);

				if (!CollectionUtils.isEmpty(orderInfo.getDetails())) {
					for (TabOrderInfoDetail detail : orderInfo.getDetails()) {
						detail.setOrder_id(orderInfo.getId());
						detail.setEncoding(orderInfo.getEncoding());
						detail.setCreate_user(orderInfo.getCreate_user());
						orderInfoDetailService.saveOrderInfoDetail(detail);
					}
				}
			}

			OrderCouponRel orderCouponRel = new OrderCouponRel();
			orderCouponRel.setOrder_id(orderInfo.getId());
			orderCouponRelService.deleteOrderCouponRel(orderCouponRel);

			if (!CollectionUtils.isEmpty(orderInfo.getCoupon_rels())) {
				for (OrderCouponRel rel : orderInfo.getCoupon_rels()) {
					rel.setOrder_id(orderInfo.getId());
					orderCouponRelService.saveOrderCouponRel(rel);
				}
			}

		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#updateOrderInfo(com
	 * .ebusiness.erp.model.TabOrderInfo,
	 * com.ebusiness.cas.client.common.model.Account,
	 * com.ebusiness.cas.client.common.model.OrgModel,
	 * org.jbpm.api.ProcessEngine)
	 */
	@Override
	public int updateOrderInfo(TabOrderInfo orderInfo, Account account,
			OrgModel orgModel, ProcessEngine processEngine) throws Exception {
		int count = updateOrderInfo(orderInfo);

		// 如果不需要审批，则直接通过
		if ("saveOrder".equals(orderInfo.getSaveType())) {
			startProcess(account, orgModel, orderInfo, processEngine);
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#queryTemporaryOrderInfo
	 * (java.lang.Long)
	 */
	@Override
	public TabOrderInfo queryTemporaryOrderInfo(Long orderId) throws Exception {
		Assert.notNull(orderId);
		TabOrderInfo orderInfoParam = new TabOrderInfo();
		orderInfoParam.setId(orderId);
		TabOrderInfo orderInfo = orderInfoDao
				.queryTemporaryOrderInfo(orderInfoParam);
		if (orderInfo != null) {
			Map<String, Object> queryStudentParam = new HashMap<String, Object>();
			queryStudentParam.put("studentId", orderInfo.getStudent_id());
			// 查询学员信息
			StudentInfo studentInfo = studentInfoService
					.queryStudentById(queryStudentParam);
			orderInfo.setStudentInfo(studentInfo);

			// 查询订单详情信息
			List<TabOrderInfoDetail> details = orderInfoDetailService
					.queryTabOrderInfoDetail(orderId);
			if (!CollectionUtils.isEmpty(details)) {
			    List<TCourse> courseList = this.courseService.queryOrderCourseList(orderId);
			    for (TabOrderInfoDetail detail : details) {
			        TCourse course = genTCourse(courseList, detail.getCourse_id());
					CourseScheduling condition=new CourseScheduling();
					condition.setCourse_id(detail.getCourse_id());
					List<CourseScheduling> courseSchedulingList=courseSchedulingService.queryCourseScheduling(condition);
					course.setCourseSchedulingList(courseSchedulingList);
					Teacher teacher = this.teacherInfoService.queryOne(course.getTeacher_id());
			        if (teacher != null) {
			            course.setTeacher(teacher);
			        }
			    }
			    
				orderInfo.setDetails(details);
			}

			// 订单级优惠规则
			if (orderInfo.getRule_id() != null) {
				PrivilegeRule privilegeRule = privilegeRuleService
						.queryByRuleId(orderInfo.getRule_id());
				orderInfo.setPrivilegeRule(privilegeRule);
			}

			// 订单级优惠券
			OrderCouponRel rel = new OrderCouponRel();
			rel.setOrder_id(orderId);
			List<OrderCouponRel> couponRels = orderCouponRelService
					.queryByOrderId(rel);

			if (!CollectionUtils.isEmpty(couponRels)) {
				orderInfo.setCoupon_rels(couponRels);
			}

			// 查询支付信息
			TabOrderPayCost orderPayCost = orderPayCostService
					.queryTabOrderPayCost(orderId);
			orderInfo.setPayment(orderPayCost);
		}

		return orderInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#createOrder(java.lang
	 * .Long, java.lang.Long)
	 */
	@Override
	public Map<String, Object> createOrder(Long orderId, Long userId)
			throws Exception {
		Assert.notNull(orderId);
		Assert.notNull(userId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("order_id", orderId + "");
		param.put("user_id", userId + "");
		Map<String, Object> result = orderInfoDao.createOrder(param);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.orders.service.OrderInfoService#
	 * updatePerformanceAttribution(java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePerformanceAttribution(String order_id, String user_id)
			throws Exception {
		Assert.notNull(order_id);
		Assert.notNull(user_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_id", order_id);
		map.put("user_id", user_id);

		orderInfoDao.updatePerformanceAttribution(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#updateOrderApproved
	 * (java.util.Map)
	 */
	@Override
	public int updateOrderApproved(TabOrderInfo params) throws Exception {
		Assert.notNull(params);
		Assert.notNull(params.getId());
		Assert.notNull(params.getApproved());
		return orderInfoDao.updateOrderApproved(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#unpassOrder(java.lang
	 * .Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public int unpassOrder(Long orderId, Long userId, String remark)
			throws Exception {
		TabOrderInfo orderBusiness = this.queryTemporaryOrderInfo(orderId);
		if (null != orderBusiness) {
			// 更新订单为初始暂存状态
			orderBusiness.setCheck_status(1L);
			orderBusiness.setValid_status(1L);
			if (StringUtils.isNotBlank(remark)) {
				String rnk = orderBusiness.getRemark() + "[审批备注]" + remark;
				if (rnk.length() > 100) {
					orderBusiness.setRemark(rnk.substring(0, 100));
				} else {
					orderBusiness.setRemark(rnk);
				}
			}
			orderBusiness.setUpdate_user(userId);
			orderBusiness.setUpdate_time(new Date());

			// 保存订单信息
			return this.orderInfoDao.updateOrderInfo(orderBusiness);
		}

		return 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#deleteTemporaryOrderInfo
	 * (java.lang.Long,java.lang.Long)
	 */
	@Override
	public void deleteTemporaryOrderInfo(Long orderId, Long userId)
			throws Exception {
		Assert.notNull(orderId);
		Assert.notNull(userId);

		TabOrderInfo orderInfo = queryTemporaryOrderInfo(orderId);

		if (null == orderInfo) {
			throw new IllegalArgumentException("非法的订单！");
		}
		// 该订单正在审核中
		if (orderInfo.getCheck_status() != null
				&& orderInfo.getCheck_status().intValue() == 2) {
			throw new IllegalArgumentException("订单正在审核，无法作废！");
		}
		// 该订单已审核通过
		if (orderInfo.getCheck_status() != null
				&& orderInfo.getCheck_status().intValue() == 3
				&& (orderInfo.getPay_status() != null && orderInfo
						.getPay_status().intValue() == 1)) {
			throw new IllegalArgumentException("异常操作，该订单已经生效，请走作废流程！");
		}
		// 该订单已审核不通过
		if (orderInfo.getCheck_status() != null
				&& orderInfo.getCheck_status().intValue() == 4) {
			throw new IllegalArgumentException("该订单审核不通过，已经是作废单据！");
		}
		orderInfo.setValid_status(0L);
		orderInfo.setUpdate_user(userId);
		orderInfo.setUpdate_time(new Date());
		orderInfoDao.updateOrderInfo(orderInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#queryUserOrderList(
	 * java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryUserOrderList(
			Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("userId"));
		return orderInfoDao.queryUserOrderList(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderInfoService#payOrderInfo(com.ebusiness
	 * .erp.model.TabOrderInfo, java.lang.Long)
	 */
	@Override
	public TabOrderInfo payOrderInfo(TabOrderInfo orderInfo, Long userId)
			throws Exception {
		Assert.notNull(orderInfo);
		Assert.notNull(orderInfo.getId());
		Assert.notNull(orderInfo.getPayment());
		TabOrderInfo temporaryOrderInfo = queryTemporaryOrderInfo(orderInfo
				.getId());
		if (temporaryOrderInfo == null) {
			log.error("error found！要支付的订单不存在:" + orderInfo.getId() + ",制单人："
					+ orderInfo.getCreate_user() + ",修改人："
					+ orderInfo.getUpdate_user());
			throw new Exception("要支付的订单不存在！");
		}

		if (temporaryOrderInfo.getCheck_status() == null
				|| temporaryOrderInfo.getCheck_status().intValue() != 3
				|| temporaryOrderInfo.getValid_status() == null
				|| temporaryOrderInfo.getValid_status().intValue() == 0
				|| (temporaryOrderInfo.getPay_status() != null && temporaryOrderInfo
						.getPay_status().intValue() == 1)) {
			log.error("error found！要支付的订单状态异常，不能做支付！订单ID：" + orderInfo.getId()
					+ ",制单人：" + orderInfo.getCreate_user() + ",修改人："
					+ orderInfo.getUpdate_user());
			throw new Exception("订单状态异常，不能支付！请联系管理员处理！");
		}

		// 保存订单支付信息
        TabOrderPayCost tabOrderPayCost = orderInfo.getPayment();
        if (tabOrderPayCost.getActualPrice() == null
                || temporaryOrderInfo.getActual_price() == null
                || tabOrderPayCost.getActualPrice().intValue() != temporaryOrderInfo
                        .getActual_price().intValue()) {
            log.error("error found！支付金额不正确，不能做支付！订单ID：" + orderInfo.getId()
                    + ",制单人：" + orderInfo.getCreate_user() + ",修改人："
                    + orderInfo.getUpdate_user());
            throw new Exception("支付金额不正确，不能支付！请联系管理员处理！");
        }

        tabOrderPayCost.setOrderId(temporaryOrderInfo.getId());
        tabOrderPayCost.setBuId(temporaryOrderInfo.getBu_id());
        tabOrderPayCost.setBranchId(temporaryOrderInfo.getBranch_id());
        tabOrderPayCost.setCity_id(temporaryOrderInfo.getCity_id());
        tabOrderPayCost.setEncoding(temporaryOrderInfo.getEncoding());
        
		orderPayCostService.deleteOrderPayCost(tabOrderPayCost);
		//添加在线支付的订单号
        List<TabOrderPayCostDetail>  TabOrderPayCostDetail=tabOrderPayCost.getDetails();
        for(TabOrderPayCostDetail tabOrderPayCostDetail:TabOrderPayCostDetail){
            if(tabOrderPayCostDetail.getPayment_way()==11){
                tabOrderPayCost.setOnlinePrice(tabOrderPayCostDetail.getStaffappprem());
                tabOrderPayCostDetail.setEb_no(temporaryOrderInfo.getBillNo());
            }
        }
		orderPayCostService.saveOrderPayCost(tabOrderPayCost);

		temporaryOrderInfo.setPay_status(1L);
		temporaryOrderInfo.setUpdate_user(userId);
		temporaryOrderInfo.setUpdate_time(new Date());
		temporaryOrderInfo.setApproved(0);
		temporaryOrderInfo.setPayment(tabOrderPayCost);
		updateOrderInfo(temporaryOrderInfo);

		// TODO 生成正式订单(目前仅有个性化做了存储过程改造，其他的暂时保留不变)
//		if(orderInfo.getBusiness_type()==2){
			TabOrderInfo temporaryTabOrderInfo = queryTemporaryOrderInfo(orderInfo.getId());
			iOrderYDY.createOrder(temporaryTabOrderInfo);
//		}else{
//			createOrder(temporaryOrderInfo.getId(), userId);
//		}

		// 业绩归属
		updatePerformanceAttribution(
				String.valueOf(temporaryOrderInfo.getId()),
				String.valueOf(userId));

		// 完成试听考勤、更新课程最新报班人数
		if (!CollectionUtils.isEmpty(temporaryOrderInfo.getDetails())) {
			for (TabOrderInfoDetail orderInfoDetail : temporaryOrderInfo
					.getDetails()) {
				updateCoursePeople(orderInfoDetail);
				completeListening(orderInfoDetail.getCourse_id(),
						temporaryOrderInfo.getStudent_id(),
						temporaryOrderInfo.getRemark(), userId,
						temporaryOrderInfo.getBranch_id());
			}
		}
		//双师2.0需要，增加查询正式订单产生的课程课次ID
		List<TabOrderInfoDetail> tabOrderInfoDetailList= temporaryOrderInfo.getDetails();
		for(TabOrderInfoDetail tabOrderInfoDetail:tabOrderInfoDetailList){
			List<TOrderCourseTimes> tOrderCourseTimesList=tOrderCourseTimesDao.queryOrderCourseTimes(tabOrderInfoDetail.getId());
			tabOrderInfoDetail.settOrderCourseTimes(tOrderCourseTimesList);
		}
		return temporaryOrderInfo;
	}

	private void completeListening(Long course_id, Long student_id,
			String remark, Long userId, Long branchId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		// query_t_course_listening_sc;查询到对应的课次，并进行考勤处理

		try {
			param.put("COURSE_ID", course_id);
			param.put("STUDENT_ID", student_id);
			param.put("PAY_STATUS", 1L);
			param.put("REMARK", remark);
			param.put("UPDATER", userId);
			param.put("UPDATE_TIME",
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			List<Map<String, Object>> resultList = tCourseListeningService
					.queryTCourseListeningSc(param);

			if (!CollectionUtils.isEmpty(resultList)) {
				for (Map<String, Object> listening : resultList) {
					if (null != listening.get("LISTENING_DATE")
							&& !"".equals(listening.get("LISTENING_DATE"))) {
						Map<String, Object> attend = new HashMap<String, Object>();
						attend.put("attendanceId", null);
						if (null == listening.get("SCHEDULING_ID")) {
							continue;
						}
						attend.put(
								"schedulingId",
								Long.parseLong(listening.get("SCHEDULING_ID")
										+ "")); // 排课表ID
						attend.put("userId", userId);
						attend.put("attendType", 12L);
						attend.put("remark", "试听课次，补考勤。");
						attend.put("branchId", branchId);
						attend.put("courseDate",
								DateUtil.stringToDate(
										"" + listening.get("LISTENING_DATE"),
										"yyyy-MM-dd"));

						attend.put("studentId", student_id);
						attendanceService.attandanceSubmit(attend);
					}
				}
				tCourseListeningService.updateTCourseListening(param);
			}

		} catch (Exception e) {
			throw new Exception("更新学员试听记录失败！" + e.getMessage());
		}

	}

	private void updateCoursePeople(TabOrderInfoDetail orderDetailBusiness)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("course_id", orderDetailBusiness.getCourse_id());
		courseService.toChangeActualPeople(params);
	}

	@Override
	public TabOrderInfo queryOrderInfo(Long orderId) throws Exception {
		TabOrderInfo orderInfo=new TabOrderInfo();
		orderInfo.setId(orderId);
		return orderInfoDao.queryTemporaryOrderInfo(orderInfo);
	}

	@Override
	public Map<String, Object> cancelOrderInfo(Long orderId,String remark, String operater,Account account, OrgModel orgModel,Map<String, Object> resultMap,ProcessEngine processEngine) throws Exception {
		try {
			Assert.notNull(remark,"备注不允许为空!");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("order_id", orderId);
			TabOrderInfo tabOrderInfo = orderInfoService.queryOrderInfo(orderId);
			//如果是线上订单不允许作废
			if(tabOrderInfo.getOn_line()!=null&&tabOrderInfo.getOn_line()==1l){
				resultMap.put("error", "true");
				resultMap.put("message", "线上订单不允许作废操作！");
				return resultMap;
			}
			
			//查询临时订单的状态	
			if (tabOrderInfo != null&& null != tabOrderInfo.getInvoice_status()&& tabOrderInfo.getInvoice_status().intValue() == 1) {
				resultMap.put("error", "true");
				resultMap.put("message", "订单已开发票，需要先回收发票，才能作废！");
				return resultMap;
			}
			param.put("order_encoding", tabOrderInfo.getEncoding());
			param.put("student_id", tabOrderInfo.getStudent_id());
			param.put("student_name", tabOrderInfo.getStudent_name());
			param.put("change_type", 3);// 订单作废
			// 校验是否可以做订单作废
			orderChangeService.orderChangeCheck(param);

			if (param.get("err_code")!=null&&!"0".equals(param.get("err_code").toString())) {
				resultMap.put("error", "true");
				resultMap.put("message",param.get("err_desc") );
				return resultMap;
			}

			// 进行作废操作，当天的直接作废
			String createDate = DateUtil.dateToString(tabOrderInfo.getCreate_time(), "yyyy-MM-dd");
			String today = DateUtil.getCurrDate("yyyy-MM-dd");
			if (today.endsWith(createDate)) {
				resultMap = orderChangeService.cancelOrder(remark, orderId,account.getId());
				if ("false".equals(resultMap)) {
					resultMap.put("message", "手工作废订单成功！");
					StringBuilder detailInfoStr = new StringBuilder();
					detailInfoStr.append("订单为当日订单，时间为：");
					detailInfoStr.append(today);
					detailInfoStr.append("处理结果：");
					detailInfoStr.append(resultMap);
					TabOrderPayCost payCostInfo = orderPayCostService.queryTabOrderPayCost(orderId);
				}

				return resultMap;
			}

			// 获取当前登录地区ID
			String city_id =orgModel.getCityId().toString();
			String buid_id = orgModel.getBuId().toString();

			// 开始处理个别地区需要走两层审批的工作流

			String flag = DxbCityCfg.getInstance().getConfigItem("workflow.zuofei.shenpi.2c." + city_id, "-1");

			boolean shenpi_2c = false;

			if (flag.equals("true")) {
				if (!WorkflowHelper.isDeployed(processEngine,"erpv5.DXB_order_menucancel_2C")) {
					resultMap.put("error", "true");
					resultMap.put("message", "订单手工作废审批流程尚未发布，请联系管理员发布流程！");

					StringBuilder detailInfoStr = new StringBuilder();
					detailInfoStr.append("失败原因：订单手工作废审批流程尚未发布！");
					return resultMap;
				} else {
					shenpi_2c = true;
				}
			} else if (!WorkflowHelper.isDeployed(processEngine,"erpv5.DXB_order_menucancel")) {
				resultMap.put("error", "true");
				resultMap.put("message", "订单手工作废审批流程尚未发布，请联系管理员发布流程！");

				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("失败原因：订单手工作废审批流程尚未发布！");
				return resultMap;
			}
			param.put("remark", remark);
			param.put("remark_order", remark);
			param.put("branch_id", tabOrderInfo.getBranch_id());
			param.put("workbenchURL",
					"/apps/student_index/index-student#/order-detail/"
							+ tabOrderInfo.getBusiness_type() + "/"
							+ tabOrderInfo.getStudent_id() + "/"
							+ tabOrderInfo.getId());
			param.put("businessDetailInfo", DetailBusinessInfoFormat.menuOrderCancelString(tabOrderInfo, remark));

			Map<String, Object> userApplication = new HashMap<String, Object>();
			userApplication.put("APPLICATION_ID",account.getId());
			StringBuilder application = new StringBuilder("订单手工作废审批：");
			application.append("学生ID[");
			application.append(tabOrderInfo.getStudent_id());
			application.append("]");
			application.append("订单ID[");
			application.append(tabOrderInfo.getId());
			application.append("]");
			application.append("订单总价[");
			application.append(tabOrderInfo.getActual_price());
			application.append(".00元]");

			userApplication.put("APPLICATION", application.toString());
			userApplication.put("STATUS", 1L);
			userApplication.put("CREATETIME",DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			userApplication.put("REMARK", remark);
			userApplication.put("CURRENT_STATE", "申请已提交");
			userApplication.put("CURRENT_STEP", "申请提交");
			if (null != account) {
				userApplication.put("CURRENT_MAN", account.getEmployeeName());
			}
			userApplication.put("UPDATETIME",
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			userApplication.put("WORKURL",
					"#/order-detail/" + tabOrderInfo.getBusiness_type() + "/"
							+ tabOrderInfo.getStudent_id() + "/"
							+ tabOrderInfo.getId());
			userTaskService.insertApplication(userApplication);
			param.put("application_id", userApplication.get("id"));
			param.put("sponsor", account.getId());

			// 处理佳音意外情况
			if (buid_id.equals("100000042")) {
				// 佳音跨月走两层，不跨月原样
				String createMonth = DateUtil.dateToString(tabOrderInfo.getCreate_time(), "yyyy-MM");
				String Month = DateUtil.getCurrDate("yyyy-MM");
				if (createMonth.equals(Month)) {
					ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_menucancel", param);
					processEngine.getExecutionService().createVariables(pi.getId(), param, true);
				} else {
					ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_menucancel_2C", param);
					processEngine.getExecutionService().createVariables(pi.getId(), param, true);
				}
			} else {
				// shenpi标识为ture则走两层审批
				if (shenpi_2c) {
					ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_menucancel_2C", param);
					processEngine.getExecutionService().createVariables(pi.getId(), param, true);
				} else {
					ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_menucancel", param);
					processEngine.getExecutionService().createVariables(pi.getId(), param, true);
				}
			}
			resultMap.put("error", "false");
			resultMap.put("message", "手工作废订单审批申请已提交！");
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append(application.toString());
			return resultMap;
		} catch (Exception e) {
			resultMap.put("error", "true");
			resultMap.put("message",  e.getMessage());
			return resultMap;
		}
	}
	
	

	@Override
	public void updateInvoiceStatus(Long id, Long invoiceStatus)
			throws Exception {
		Assert.notNull(id);
		Assert.notNull(invoiceStatus);
		TabOrderInfo tabOrderInfo = new TabOrderInfo();
		tabOrderInfo.setId(id);
		tabOrderInfo.setInvoice_status(invoiceStatus);
		this.orderInfoDao.updateInvoiceStatus(tabOrderInfo);
	}
	
	@Override
	public Map<String, Object> lockOrder(String status, Long orderId,
			String remark, String operater, Account account, OrgModel orgModel,
			Map<String, Object> result, ProcessEngine processEngine)
			throws Exception {
		Assert.notNull(remark,"备注不允许为空!");
		try {
			TabOrderInfo orderBusiness = orderInfoService.queryOrderInfo(orderId);
			TOrderLock tOrderLock=new TOrderLock();
			//厦门佳音意外事件处理
			String bu_id =orgModel.getBuId().toString();
			String flag = DxbCityCfg.getInstance().getConfigItem("workflow.order_lock."+bu_id, null);
			if(null!=flag&&flag.equals("true")){
				System.err.println("佳音地区特殊事件");
				//锁定分支
				if(null == orderBusiness.getLock_status()||orderBusiness.getLock_status() == 3){
					if (!WorkflowHelper.isDeployed(processEngine,"erpv5.DXB_order_lock")) {
						result.put("error", "true");
						result.put("message", "订单锁定审批流程尚未发布，请联系管理员发布流程！");

						StringBuilder detailInfoStr = new StringBuilder();
						detailInfoStr.append("失败原因：订单锁定审批流程尚未发布！");
						return result;
					}
					tOrderLock.setOrderId(orderId);
					tOrderLock.setCreater(account.getId());
					tOrderLock.setCreateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
					tOrderLock.setUpdateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
					tOrderLock.setUpdater(account.getId());
					tOrderLock.setRemark(remark);
					tOrderLock.setStatus(1l);
					//如果已经有锁定数据不插入
					Map<String, Object> orderIdMap = new HashMap<>();
					orderIdMap.put("orderId", orderId);
					String orderLockId = "";
					List<TOrderLock> lock = tOrderLockDao.queryLockInfoByOrderId(orderIdMap);
					if(lock != null && lock.size()>=1){
						tOrderLock=lock.get(0);
						tOrderLock.setCreater(account.getId());
						tOrderLock.setCreateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
						tOrderLock.setUpdateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
						tOrderLock.setUpdater(account.getId());
						tOrderLock.setRemark(remark);
						tOrderLock.setStatus(1l);
						tOrderLockDao.updateOrderLockStatus(tOrderLock);
					}else{
						tOrderLockDao.insertOrderLock(tOrderLock);
						orderLockId =tOrderLock.getId().toString();
					}
					
					tOrderLockDao.insertOrderLockHt(tOrderLock);
					StringBuilder detailInfoStr = new StringBuilder();
					detailInfoStr.append("订单ID：");
					detailInfoStr.append(orderId);
					detailInfoStr.append("，");
					detailInfoStr.append("备注信息：");
					detailInfoStr.append(remark);
					detailInfoStr.append("，");
					detailInfoStr.append("订单状态：");
					detailInfoStr.append(status);
					result.put("error", false);
					
						//流程数据构造
						Map<String, Object> param = new HashMap<String, Object>();
						
						param.put("order_encoding", orderBusiness.getEncoding());
						param.put("student_id", orderBusiness.getStudent_id());
						Map<String, Object> queryParam=new HashMap<String,Object>();
						queryParam.put("studentId", orderBusiness.getStudent_id());
						StudentInfo studentBusiness = studentInfoService.queryStudentById(queryParam);
						param.put("student_name", studentBusiness.getStudent_name());
						param.put("student_encoding", studentBusiness.getEncoding());
						
						param.put("workbenchURL","/apps/student_index/index-student#/order-detail/"+ orderBusiness.getBusiness_type() + "/"+ orderBusiness.getStudent_id() + "/"
										+ orderBusiness.getId());
						Map<String, Object> userApplication = new HashMap<String, Object>();
						userApplication.put("APPLICATION_ID",account.getId());
						StringBuilder application = null;
						if(null == orderBusiness.getLock_status()||orderBusiness.getLock_status() == 3){
							application = new StringBuilder("订单锁定审批：");
						}else{
							application = new StringBuilder("订单解锁审批：");
						}
						application.append("学生ID[");
						application.append(orderBusiness.getStudent_id());
						application.append("]");
						application.append("订单ID[");
						application.append(orderBusiness.getId());
						application.append("]");
						application.append("订单总价[");
						application.append(orderBusiness.getActual_price());
						application.append(".00元]");

						userApplication.put("APPLICATION", application.toString());
						userApplication.put("STATUS", 1L);
						userApplication.put("CREATETIME",
								DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
						userApplication.put("REMARK", remark);
						userApplication.put("CURRENT_STATE", "申请已提交");
						userApplication.put("CURRENT_STEP", "申请提交");
						if (null != account) {
							userApplication.put("CURRENT_MAN", account.getEmployeeName());
						}
						userApplication.put("UPDATETIME",DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
						userApplication.put("WORKURL","#/order-detail/" + orderBusiness.getBusiness_type() + "/"+ orderBusiness.getStudent_id() + "/"+ orderBusiness.getId());
						//储存流程锁定/解锁唯一标识
						userApplication.put("BUSI_ID", orderLockId);
						//锁定表示为10解锁为9
						userApplication.put("BUSI_TYPE", "10");
						userTaskService.insertApplication(userApplication);
						param.put("application_id", userApplication.get("id"));
						param.put("sponsor", account.getId());
						param.put("branch_id", orgModel.getId());
						param.put("userId", account.getId());
						param.put("order_id", orderId);
						param.put("status","1");
						param.put("remark",remark);
						
						if(null == orderBusiness.getLock_status()||orderBusiness.getLock_status() == 3){
							ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_lock",param);
							processEngine.getExecutionService().createVariables(pi.getId(),param, true);
						}else{
							ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_unlock",param);
							processEngine.getExecutionService().createVariables(pi.getId(),param, true);
							result.put("error", false);
						return result;
					}
				}
				//解锁分支
				else if(orderBusiness.getLock_status() == 1){
					if (!WorkflowHelper.isDeployed(processEngine,"erpv5.DXB_order_unlock")) {
						result.put("error", "true");
						result.put("message", "订单锁定审批流程尚未发布，请联系管理员发布流程！");

						StringBuilder detailInfoStr = new StringBuilder();
						detailInfoStr.append("失败原因：订单锁定审批流程尚未发布！");
						return result;
					}
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("order_encoding", orderBusiness.getEncoding());
					param.put("student_id", orderBusiness.getStudent_id());
					Map<String, Object> queryParam=new HashMap<String,Object>();
					queryParam.put("studentId",orderBusiness.getStudent_id());
					StudentInfo studentBusiness = studentInfoService.queryStudentById(queryParam);
					param.put("student_name", studentBusiness.getStudent_name());
					param.put("workbenchURL","/apps/student_index/index-student#/order-detail/"
									+ orderBusiness.getBusiness_type() + "/"
									+ orderBusiness.getStudent_id() + "/"
									+ orderBusiness.getId());
					Map<String, Object> userApplication = new HashMap<String, Object>();
					userApplication.put("APPLICATION_ID",account.getId());
					StringBuilder application = null;
					
					Map<String, Object> orderIdMap = new HashMap<>();
					orderIdMap.put("orderId", orderId);
					String orderLockId = "";
					List<TOrderLock> lock = tOrderLockDao.queryLockInfoByOrderId(orderIdMap);
					if(lock != null && lock.size()>=1){
						orderLockId = lock.get(0).getId().toString();
					}
					
					if(orderBusiness.getLock_status() == 3){
						application = new StringBuilder("订单锁定审批：");
					}else{
						application = new StringBuilder("订单解锁审批：");
					}
					application.append("学生ID[");
					application.append(orderBusiness.getStudent_id());
					application.append("]");
					application.append("订单ID[");
					application.append(orderBusiness.getId());
					application.append("]");
					application.append("订单总价[");
					application.append(orderBusiness.getActual_price());
					application.append(".00元]");

					userApplication.put("APPLICATION", application.toString());
					userApplication.put("STATUS", 1L);
					userApplication.put("CREATETIME",
							DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
					userApplication.put("REMARK", remark);
					userApplication.put("CURRENT_STATE", "申请已提交");
					userApplication.put("CURRENT_STEP", "申请提交");
					if (null != account) {
						userApplication.put("CURRENT_MAN", account.getEmployeeName());
					}
					userApplication.put("UPDATETIME",
							DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
					userApplication.put("WORKURL",
							"#/order-detail/" + orderBusiness.getBusiness_type() + "/"
									+ orderBusiness.getStudent_id() + "/"
									+ orderBusiness.getId());
					//储存流程锁定/解锁唯一标识
					userApplication.put("BUSI_ID", orderLockId);
					//锁定表示为10解锁为9
					userApplication.put("BUSI_TYPE", "9");
					userTaskService.insertApplication(userApplication);
					param.put("application_id", userApplication.get("id"));
					param.put("sponsor", account.getId());
					param.put("branch_id", orgModel.getId());
					param.put("userId", account.getId().toString());
					param.put("order_id", orderId);
					param.put("status",orderBusiness.getLock_status().toString());
					param.put("remark",remark);
					
					if(orderBusiness.getLock_status() == 3){
						ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_lock",param);
						processEngine.getExecutionService().createVariables(pi.getId(),param, true);
					}else{
						ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_order_unlock",param);
						processEngine.getExecutionService().createVariables(pi.getId(),
								param, true);
						result.put("error", false);
					}
				}
				return result;
			}
			if (orderBusiness.getLock_status() == null) {
				tOrderLock.setOrderId(orderId);
				tOrderLock.setCreater(account.getId());
				tOrderLock.setCreateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
				tOrderLock.setUpdateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
				tOrderLock.setUpdater(account.getId());
				tOrderLock.setRemark(remark);
				tOrderLock.setStatus(1l);
				tOrderLockDao.insertOrderLock(tOrderLock);
			} else {
				tOrderLock.setOrderId(orderId);
				tOrderLock.setCreater(account.getId());
				tOrderLock.setCreateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
				tOrderLock.setUpdateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
				tOrderLock.setUpdater(account.getId());
				tOrderLock.setRemark(remark);
				tOrderLock.setStatus( Long.parseLong(status));
				Map<String,Object> rs=tOrderLockDao.countOrderCourse(tOrderLock);
				tOrderLock.setSurplus_cost(Double.valueOf(rs.get("SURPLUS_COST").toString()));
				tOrderLock.setCourse_surplus_count(Long.parseLong(rs.get("COURSE_SURPLUS_COUNT").toString()));
				tOrderLockDao.updateOrderLockStatus(tOrderLock);
			}
		
			//如果已经有锁定数据不插入
			Map<String, Object> orderIdMap = new HashMap<>();
			orderIdMap.put("orderId", orderId);
			List lock =tOrderLockDao.queryLockInfoByOrderId(orderIdMap);
			if(lock==null){
				tOrderLockDao.insertOrderLock(tOrderLock);
			}
		StringBuilder detailInfoStr = new StringBuilder();
		detailInfoStr.append("订单ID：");
		detailInfoStr.append(orderId);
		detailInfoStr.append("，");
		detailInfoStr.append("备注信息：");
		detailInfoStr.append(remark);
		detailInfoStr.append("，");
		detailInfoStr.append("订单状态：");
		detailInfoStr.append(status);
		result.put("error", false);
		} catch (Exception e) {
			
			result.put("data", false);
			result.put("error", true);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("被锁定订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注信息：");
			detailInfoStr.append(remark);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			log.error(detailInfoStr.toString()+e.getMessage(),e);
		}
		return result;
	}

	@Override
	public Page<TabOrderInfo> selectUnpay(Map<String, Object> params) throws Exception {
		return orderInfoDao.selectUnpay(params);
	}

	@Override
	public Long needSynToDouble(Long orderId) throws Exception {
		return orderInfoDao.needSynToDouble(orderId);
	}

	@Override
	public void updateOrderRemark(TabOrderInfo params) throws Exception {
		orderInfoDao.updateOrderRemark(params);
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

	@Override
	public void updateOrderLockStatus(Map<String, Object> params) throws Exception {
		orderInfoDao.updateOrderLockStatus(params);
	}
}
