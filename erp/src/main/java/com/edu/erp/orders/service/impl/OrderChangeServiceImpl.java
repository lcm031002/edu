package com.edu.erp.orders.service.impl;

import com.edu.erp.workflow.service.UserTaskService;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import com.edu.erp.orders.service.*;
import com.edu.erp.orders.service.OrderPayCostService;
import com.edu.erp.role.service.AccountService;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.util.*;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.orders.ext.IOrderFrozen;
import com.edu.erp.orders.ext.IOrderRefund;
import com.edu.erp.orders.ext.IOrderYDY;
import com.github.pagehelper.Page;

@Service(value = "orderChangeService")
public class OrderChangeServiceImpl implements OrderChangeService {
	private static final Logger log = Logger
			.getLogger(OrderChangeServiceImpl.class);

	@Resource(name = "tOrderChangeDao")
	private TOrderChangeDao tOrderChangeDao;

	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;

	@Resource(name = "tLockDao")
	private TLockDao tLockDao;

	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "orderCourseTimesInfoService")
	private OrderCourseTimesInfoService orderCourseTimesInfoService;

	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "iOrderYDY")
	private IOrderYDY iOrderYDY;
	
	@Resource(name = "orderRefund")
	private IOrderRefund orderRefund;
	
	@Resource(name = "orderFrozen")
	private IOrderFrozen orderFrozen;

	@Resource(name = "tabChangeCourseDao")
	private TabChangeCourseDao tabChangeCourseDao;

	@Resource(name = "orderChangeCheckService")
	private OrderChangeCheckService orderChangeCheckService;

	@Resource(name = "feeService")
	private FeeService feeService;

	@Resource(name = "feeDetailService")
	private FeeDetailService feeDetailService;

	@Resource(name = "encoderService")
	private EncoderService encoderService;

	@Resource(name = "orderPayCostService")
	private OrderPayCostService orderPayCostService;

	@Resource(name = "finFeeService")
	private FinFeeService finFeeService;

	@Resource(name = "accountService")
	private AccountService accountService;

	@Resource(name = "studentAccountService")
	private StudentAccountService studentAccountService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	@Resource(name = "tCOrderCourseDao")
	private TCOrderCourseDao tcOrderCourseDao;

	@Override
	public void orderChangeTransfer(Map<String, Object> transferObj)
			throws Exception {
		Assert.notNull(transferObj);
		Assert.notNull(transferObj.get("p_order_detail_id"));
		Assert.notNull(transferObj.get("P_output_count"));
		Assert.notNull(transferObj.get("p_output_times"));
		Assert.notNull(transferObj.get("p_input_course_id"));
		Assert.notNull(transferObj.get("p_input_branch_id"));
		Assert.notNull(transferObj.get("P_input_count"));
		Assert.notNull(transferObj.get("p_input_times"));
		Assert.notNull(transferObj.get("P_input_user"));
		Assert.notNull(transferObj.get("businessType"));
		transferObj.put("p_encoding", EncodingSequenceUtil.getSequenceNum(3L));

		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("id", transferObj.get("p_order_detail_id"));
		List<TOrderCourse> orderDetailCourse = tOrderCourseDao.queryOrderCourse(queryParam);
		if (CollectionUtils.isEmpty(orderDetailCourse)) {
			log.error("没有找到订单详情！");
			throw new Exception("没有找到订单详情！");
		}
		Long orderId = orderDetailCourse.get(0).getOrder_id();
		Integer businessType = NumberUtils.object2Integer(transferObj.get("businessType"));

		//转班主表
		TOrderChange orderChange = new TOrderChange();
		orderChange.setOrder_id(orderId);
		orderChange.setChange_type(2L);  //转班
		orderChange.setEncoding((String)transferObj.get("p_encoding"));
		orderChange.setBranch_id(Long.valueOf(transferObj.get("p_input_branch_id").toString()));
		orderChange.setApply_user(Long.valueOf(transferObj.get("P_input_user").toString()));
		orderChange.setApply_time(new Date());
		orderChange.setChange_status(2L); //录入
		orderChange.setCreate_user(Long.valueOf(transferObj.get("P_input_user").toString()));
		orderChange.setCreate_time(new Date());
		orderChange.setUpdate_user(Long.valueOf(transferObj.get("P_input_user").toString()));
		orderChange.setUpdate_time(new Date());
		orderChange.setRemark((String)transferObj.get("p_remark"));

		//转班子表-转出
		Long outputOrderCourseId = orderDetailCourse.get(0).getId();
		TCOrderCourse outputTcOrderCourse = new TCOrderCourse();
		outputTcOrderCourse.setOrder_id(orderChange.getOrder_id());
		outputTcOrderCourse.setOrder_course_id(outputOrderCourseId);
		outputTcOrderCourse.setCourse_times(Long.valueOf(transferObj.get("P_output_count").toString()));
		BigDecimal outputCourseTimes = new BigDecimal(transferObj.get("P_output_count").toString());
		BigDecimal outputUnitPrice = new BigDecimal(orderDetailCourse.get(0).getDiscount_unit_price());
		outputTcOrderCourse.setTotal_amount(outputCourseTimes.multiply(outputUnitPrice).doubleValue());
		outputTcOrderCourse.setTransfer_flag(0L); //转出
		orderChange.getTcOrderCourse().add(outputTcOrderCourse);

		//转班子表-转入
		TCOrderCourse intputTcOrderCourse = new TCOrderCourse();
		intputTcOrderCourse.setOrder_id(orderChange.getOrder_id());
		intputTcOrderCourse.setCourse_times(Long.valueOf(transferObj.get("P_input_count").toString()));
		BigDecimal inputCourseTimes = new BigDecimal(transferObj.get("P_input_count").toString());
		intputTcOrderCourse.setTotal_amount(inputCourseTimes.multiply(outputUnitPrice).doubleValue());
		intputTcOrderCourse.setCourse_id(Long.valueOf(transferObj.get("p_input_course_id").toString()));
		intputTcOrderCourse.setBranch_id(orderChange.getBranch_id());
		intputTcOrderCourse.setTransfer_flag(1L); //转入
		orderChange.getTcOrderCourse().add(intputTcOrderCourse);

		if (Constants.BusinessType.BJK.intValue() == businessType) {
			String strOutputTimes = transferObj.get("p_output_times").toString();
			String strInputTimes = transferObj.get("p_input_times").toString();
			String[] outputTimesArr = strOutputTimes.split(",");
			String[] inputTimesArr = strInputTimes.split(",");
			if (inputTimesArr.length != outputTimesArr.length) {
				throw new Exception("转出、转入课次数量不一致，转出失败！");
			}

			List<TCCourseTimes> outputTcCourseTimesList = new ArrayList<TCCourseTimes>(outputTimesArr.length);
			List<TCCourseTimes> inutputTcCourseTimesList = new ArrayList<TCCourseTimes>(outputTimesArr.length);
			TCCourseTimes tcCourseTimes = null;
			for (int i = 0; i < outputTimesArr.length; i++) {
				tcCourseTimes = new TCCourseTimes();
				tcCourseTimes.setOrderCourseId(outputOrderCourseId);
				tcCourseTimes.setCourseTimes(Long.parseLong(outputTimesArr[i]));
				tcCourseTimes.setOrderId(orderId);
				outputTcCourseTimesList.add(tcCourseTimes);
			}

			for (int i = 0; i < inputTimesArr.length; i++) {
				tcCourseTimes = new TCCourseTimes();
				tcCourseTimes.setCourseTimes(Long.parseLong(inputTimesArr[i]));
				tcCourseTimes.setOrderId(orderId);
				inutputTcCourseTimesList.add(tcCourseTimes);
			}
			outputTcOrderCourse.setTcCourseTimes(outputTcCourseTimesList);
			intputTcOrderCourse.setTcCourseTimes(inutputTcCourseTimesList);
		}

		iOrderYDY.transferOrder(orderChange, businessType);
	}

	@Override
	public void orderChangeRefund(Map<String, Object> refundObj,
			ProcessEngine processEngine) throws Exception {
		Assert.notNull(refundObj);
		Assert.notNull(refundObj.get("premiumType"));
		refundObj.put("total_amount", 0);
		refundObj.put("attend_amount", 0);
		refundObj.put("pre_amount", 0);
		refundObj.put("error_code", 0);
		refundObj.put("error_desc", "");
		String changeNo = UUID.randomUUID().toString();
		changeNo = changeNo.replaceAll("-", "");
		refundObj.put("change_no", changeNo);
		refundObj.put("change_type", 1);
		Long orderId = Long.parseLong(refundObj.get("order_id") + "");
		TabOrderInfo orderInfo = orderInfoService
				.queryTemporaryOrderInfo(orderId);
		if (orderInfo == null) {
			throw new Exception("订单未找到！orderId is " + orderId);
		}

		Long businessType = orderInfo.getBusiness_type();
		this.orderRefund.readyPremium(refundObj, businessType);

		refundObj.put("p_encoding", EncodingSequenceUtil.getSequenceNum(4L));
		refundObj.put("v_change_id", 0);

		// 退费负值
		if (refundObj.get("premium_result_val") != null && orderInfo != null
				&& orderInfo.getBusiness_type() != null
				&& orderInfo.getBusiness_type().intValue() == 1) {
			// 退费金额不是负数
			boolean negativePremium = false;
			Map<String, Object> rs = new HashMap<String, Object>();
			try {
				negativePremium = negativePremium(
						Long.parseLong(StringUtil.nullToBlank(refundObj
								.get("order_detail_id"))),
						Integer.parseInt(StringUtil.nullToBlank(refundObj
								.get("course_times"))), // 退费总课次要等于课次剩余课次
						Double.parseDouble(StringUtil.nullToBlank(refundObj
								.get("premium_result_val"))), // 退费金额
						(Long) refundObj.get("branch_id"),
						(Long) refundObj.get("user_id"), processEngine);
			} catch (Exception e) {
				log.error("error found,see", e);
				throw e;
			}
			if (negativePremium) {
				rs.put("error_code", false);
				rs.put("message", "退费金额为负值，退费成功！涉及到的退费课次已全部挂起！");
				return;
			}
		}
		// 更新订单课程剩余可排课时信息
		this.orderRefund.updateOrderCourseScheduleCount(refundObj);

		// 判断课时退费是否需要审批
//		String isApprove = orderInfo.getApproved() + "";
		// 课时退费，根据配置判断报班没有走审批流程，退费是否需要走审批流程
//        if (!"1".equals(isApprove)
//                && "true".equals(DxbCityCfg.getInstance().getConfigItem("return.apply.audit", "false"))
//                && "true".equals(DxbCityCfg.getInstance().getConfigItem("return.audit." + refundObj.get("cityId"),
//                        "false"))) {
//		    if (Constants.BusinessType.GXH == businessType) {
//	            this.orderRefund.premium(refundObj, businessType);
//	        } else {
//	            tOrderChangeDao.premium(refundObj);
//
//	            if (!CollectionUtils.isEmpty(refundObj) && !"0".equals(refundObj.get("error_code") + "")) {
//					throw new Exception("存储过程异常" + refundObj.get("error_desc") + "");
//				}
//	        }
//
//			refundObj.put("p_change_id",
//					Long.parseLong(refundObj.get("v_change_id") + ""));
//			refundObj.put("P_input_user", refundObj.get("user_id"));
//			refundObj.put("p_change_status", "1");
//
//			tOrderChangeDao.premiumAudit(refundObj);
//
//			if (!CollectionUtils.isEmpty(refundObj) && !"0".equals(refundObj.get("o_err_code") + "")) {
//				throw new Exception("存储过程异常" + refundObj.get("o_err_desc") + "");
//			}
//			return;
//		}

		this.orderRefund.premium(refundObj, businessType);

		if (!WorkflowHelper.isDeployed(processEngine, "tuifei_shenpi")) {
			throw new Exception("退费流程尚未发布，请联系管理员发布退费流程!");
		}
		if (!CollectionUtils.isEmpty(refundObj)
				&& "0".equals(refundObj.get("error_code") + "")) {
			refundObj.put("change_id",
					Long.parseLong(refundObj.get("v_change_id") + ""));
			refundObj.put("order_id", orderId);

			Map<String, Object> workflowParam = new HashMap<String, Object>();
			workflowParam.put("change_id", Long.parseLong(refundObj.get("v_change_id") + ""));
			workflowParam.put("remark_order", refundObj.get("p_remark"));
			workflowParam.put("student_id", orderInfo.getStudent_id());
			workflowParam.put("student_name", orderInfo.getStudent_name());
			workflowParam.put("student_encoding",
					orderInfo.getStudent_encoding());
			workflowParam.put("order_encoding", orderInfo.getEncoding());
			workflowParam.put("order_id", orderInfo.getId());
			workflowParam.put("branch_id", orderInfo.getBranch_id());
			workflowParam.put("laststep_user_id", orderInfo.getStudent_id());

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("businessType", orderInfo.getBusiness_type());
			queryParam.put("studentId", orderInfo.getStudent_id());
			// 查询订单课程时，应该用当前的登录校区，不能用订单的校区（存在转班的情况）
			//queryParam.put("branchId", orderInfo.getBranch_id());
			queryParam.put("branchId", (Long) refundObj.get("branch_id"));
			queryParam.put("id", refundObj.get("order_detail_id"));
			queryParam.put("product_line", refundObj.get("product_line"));
			List<TOrderCourse> orderDetailCourse = tOrderCourseDao
					.queryStudentOrderCourse(queryParam);
			if (CollectionUtils.isEmpty(orderDetailCourse)) {
				log.error("没有找到订单详情！");
				throw new Exception("没有找到订单详情！");
			}
			refundObj.put("course_name", orderDetailCourse.get(0)
					.getCourse_name());
			refundObj.put("teacher_name", orderDetailCourse.get(0)
					.getTeacher_name());
			refundObj.put("premium_deduction_amount",
					refundObj.get("p_premium_deduction_amount"));

			workflowParam.put("businessDetailInfo",
					DetailBusinessInfoFormat.tuifeiString(refundObj));
			Map<String, Object> userApplication = new HashMap<String, Object>();
			userApplication.put("APPLICATION_ID", refundObj.get("user_id"));
			StringBuilder application = new StringBuilder("退费审批：");
			if (null != orderInfo.getStudent_id()) {
				application.append("学生ID[");
				application.append(orderInfo.getStudent_name() + ""
						+ orderInfo.getStudent_id());
				application.append("]");
			}

			application.append("订单[");
			application.append(orderInfo.getEncoding());
			application.append("]");
			application.append("详单ID[");
			application.append(refundObj.get("order_detail_id"));
			application.append("]");
			application.append("[" + refundObj.get("premium_result") + "]");

			userApplication.put("APPLICATION", application.toString());
			userApplication.put("STATUS", 1L);
			userApplication.put("CREATETIME",
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			userApplication.put(
					"WORKURL",
					"#/order-detail/" + orderInfo.getBusiness_type() + "/"
							+ orderInfo.getStudent_id() + "/"
							+ orderInfo.getId());
			userApplication.put("REMARK", refundObj.get("premium_remark"));
			userApplication.put("CURRENT_STATE", "申请已提交");
			userApplication.put("CURRENT_STEP", "申请提交");

			userApplication.put("CURRENT_MAN", refundObj.get("user_name"));

			userApplication.put("UPDATETIME",
					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			userTaskService.insertApplication(userApplication);
			workflowParam.put("application_id", userApplication.get("id"));

			ProcessInstance pi = genProcessInstance(processEngine, orderInfo,
					workflowParam);

			processEngine.getExecutionService().createVariables(pi.getId(),
					workflowParam, true);

		} else {
			throw new Exception(refundObj.get("error_desc") + "");
		}
	}
	
	@Override
	public void orderChangeFrozen(Map<String, Object> refundObj,
			ProcessEngine processEngine) throws Exception {
		long start = System.currentTimeMillis();
		Assert.notNull(refundObj);
		Assert.notNull(refundObj.get("premiumType"));
		refundObj.put("total_amount", 0);
		refundObj.put("attend_amount", 0);
		refundObj.put("pre_amount", 0);
		refundObj.put("error_code", 0);
		refundObj.put("error_desc", "");
		refundObj.put("change_type", 1);
		refundObj.put("p_encoding", EncodingSequenceUtil.getSequenceNum(4L));
		refundObj.put("change_no", refundObj.get("p_encoding"));
		Long orderId = Long.parseLong(refundObj.get("order_id") + "");
		TabOrderInfo orderInfo = orderInfoService.queryTemporaryOrderInfo(orderId);
		if (orderInfo == null) {
			throw new Exception("订单未找到！orderId is " + orderId);
		}
		
		if (refundObj.get("p_premium_deduction_amount") != null &&Double.parseDouble( refundObj.get("p_premium_deduction_amount").toString())<0){
			throw new Exception("补扣金额不允许为负值！");
		}

		this.orderRefund.readyPremium(refundObj, orderInfo.getBusiness_type());

		long readyPremiumTime = System.currentTimeMillis();
		log.debug("==============准备退费数据，存储过程耗时：" + (readyPremiumTime - start) + " 毫秒.");
		refundObj.put("v_change_id", 0);

		// 退费负值t
		if (refundObj.get("premium_result_val") != null && orderInfo != null
				&& orderInfo.getBusiness_type() != null
				&& orderInfo.getBusiness_type().intValue() == 1) {
			// 退费金额不是负数
			boolean negativePremium = false;
			Map<String, Object> rs = new HashMap<String, Object>();
			try {
				negativePremium = negativePremium(
						Long.parseLong(StringUtil.nullToBlank(refundObj.get("order_detail_id"))),
						Integer.parseInt(StringUtil.nullToBlank(refundObj.get("course_times"))), // 退费总课次要等于课次剩余课次
						Double.parseDouble(StringUtil.nullToBlank(refundObj.get("premium_result_val"))), // 退费金额
						(Long) refundObj.get("branch_id"),
						(Long) refundObj.get("user_id"), processEngine);
			} catch (Exception e) {
				log.error("error found,see", e);
				throw e;
			}
			if (negativePremium) {
				rs.put("error_code", false);
				rs.put("message", "退费金额为负值，退费成功！涉及到的退费课次已全部挂起！");
				return;
			}
		}
		long negativePremiumTime = System.currentTimeMillis();
		Long orderChangeId = null;
		log.debug("==============退费负值处理(审批流), 耗时：" + (negativePremiumTime - readyPremiumTime) + " 毫秒.");

		TOrderChange orderChange=new TOrderChange();
		orderChange.setBranch_id(Long.valueOf(refundObj.get("branch_id")+""));
		orderChange.setCreate_user(Long.valueOf(refundObj.get("user_id")+""));
		orderChange.setOrder_id(orderId);
		orderChange.setChange_no((String) refundObj.get("change_no"));
		orderChange.setFee_deduction_amount(NumberUtils.object2Double(refundObj.get("p_premium_deduction_amount")!=null?refundObj.get("p_premium_deduction_amount").toString():"0"));
		orderChange.setEncoding(refundObj.get("p_encoding").toString());
		orderChange.setRemark(refundObj.get("p_remark").toString());
		iOrderYDY.frozenOrder(orderChange);
	}

	private ProcessInstance genProcessInstance(ProcessEngine processEngine,
			TabOrderInfo orderBusiness, Map<String, Object> mapps) {
		ProcessInstance processInstance = null;

		processInstance = processEngine.getExecutionService()
			.startProcessInstanceByKey("tuifei_shenpi", mapps);

		return processInstance;
	}

	private boolean negativePremium(long orderCourseId, Integer returnTimes,
			double returnAmount, Long branchId, Long userId,
			ProcessEngine processEngine) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("OrderChangeController.doOrderChange : returnAmount="
					+ returnAmount);
		}
		// 判断退费金额是否为负数，
		if (returnAmount >= 0) {// 不是负数不做处理
			return false;
		} else {
			// 校验退费课次是否等于课程的剩余课次，如果不是返回错误
			Integer remainCourseTimes = tOrderChangeDao
					.getRemainCourseTimesCount(orderCourseId);
			if (remainCourseTimes.compareTo(returnTimes) != 0) {
				// 剩余课次与退费课次不相等则报错
				log.error("退费金额为负数时，要退费课次与剩余课次不相等，不允许退费！");
				throw new Exception("退费金额为负数时，要退费课次与剩余课次不相等，不允许退费！");
			}
			// 如果为负数则对剩余所有课次进行挂起处理
			// 获取课次并循环考勤
			List<Map<String, Object>> remainCourses = tOrderChangeDao
					.getRemainUnAttendanceInfo(orderCourseId);
			for (Map<String, Object> course : remainCourses) {// 进行挂起考勤
				course.put("courseDate", null);
				course.put("userId", userId);
				course.put("branchId", branchId);
				course.put("schedulingId",
						((BigDecimal) course.get("schedulingId")).longValue());
				course.put("studentId",
						((BigDecimal) course.get("studentId")).longValue());
				course.put("forQuit", new Integer(1));// 考勤退费挂起标示
				course.put("attendType", 11L);// 挂起操作
			}
			// 批量考勤
			attendanceService.attandanceBatchSubmit(remainCourses,
					processEngine);
			return true;
		}
	}

	@Override
	public void premiumAudit(Map<String, Object> refundObj) throws Exception {
		Assert.notNull(refundObj);
		Assert.notNull(refundObj.get("p_change_id"));
		Assert.notNull(refundObj.get("p_input_user"));
		Assert.notNull(refundObj.get("p_change_status"));

		Long changeId = NumberUtils.object2Long(refundObj.get("p_change_id"));
		if (orderChangeCheckService.checkOrderRefunded(changeId)) {
			throw new Exception("该订单已作废,不能再退费！");
		}

		Integer changeStatus = NumberUtils.object2Integer(refundObj.get("p_change_status"));
		if (changeStatus == 0) {
			premiumNoPass(refundObj);
		} else {
			premiumPass(refundObj);
		}
	}

	/**
	 * 退费审批不通过
	 */
	private void premiumNoPass(Map<String, Object> refundObj) throws Exception {
		refundObj.put("changeStatus", 8);
		tOrderChangeDao.updateAuditInfo(refundObj);

		Long changeId = NumberUtils.object2Long(refundObj.get("p_change_id"));
		List<TCOrderCourse> tcOrderCourseList = tcOrderCourseDao.queryTcOrderCourseByChangeId(changeId);
		if (!CollectionUtils.isEmpty(tcOrderCourseList)) {
			TOrderCourse tOrderCourse = null;
			for (TCOrderCourse tcOrderCourse : tcOrderCourseList) {
				tOrderCourse = new TOrderCourse();
				tOrderCourse.setId(tcOrderCourse.getOrder_course_id());
				tOrderCourse.setCourse_schedule_count(tcOrderCourse.getCourse_schedule_count() + tcOrderCourse.getCourse_times());
				tOrderCourse.setUpdate_user(NumberUtils.object2Long(refundObj.get("p_input_user")));
				tOrderCourseDao.updateOrderCourse(tOrderCourse);
			}
		}
		TLock tLock = new TLock();
		tLock.setBusiType(5L);
		tLock.setBusiId(changeId);
		tLockDao.releaseLock(tLock);
	}

	/**
	 * 退费审批通过
	 */
	private void premiumPass(Map<String, Object> refundObj) throws Exception {
		tOrderChangeDao.updateAuditInfo(refundObj);
		Long changeId = NumberUtils.object2Long(refundObj.get("p_change_id"));
		Long userId = NumberUtils.object2Long(refundObj.get("p_input_user"));

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("change_id", changeId);
		List<TabChangeCourse> tabChangeCourseList = tabChangeCourseDao.queryChangeCourseInfo(queryMap);
		if (!CollectionUtils.isEmpty(tabChangeCourseList)) {
			TabChangeCourse tabChangeCourse = tabChangeCourseList.get(0);
			List<TCOrderCourse> tcOrderCourseList = tcOrderCourseDao.queryTcOrderCourseByChangeId(changeId);
			if (!CollectionUtils.isEmpty(tcOrderCourseList)) {
				// vip退费
				if (tabChangeCourse.getPremium_type() != null && tabChangeCourse.getPremium_type().intValue() == 2) {
					vipPremiumFee(tcOrderCourseList, changeId, userId);
				} else { // 标准退费

				}
				// 财务确认
				premiumFinConfirm(changeId, userId);
				premiumValidate(changeId, userId, tabChangeCourse.getPremium_type());

				for (TCOrderCourse tcOrderCourse : tcOrderCourseList) {
					TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(tcOrderCourse.getOrder_course_id());
					tOrderCourse.setCourse_surplus_count(tOrderCourse.getCourse_surplus_count() - tcOrderCourse.getCourse_times());
					tOrderCourse.setQuit_flag(1L);
					tOrderCourse.setSurplus_cost((tOrderCourse.getCourse_surplus_count() - tcOrderCourse.getCourse_times()) * tOrderCourse.getDiscount_unit_price());
					tOrderCourseDao.updateOrderCourse(tOrderCourse);
				}

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("change_id", changeId);
				orderCourseTimesInfoService.updateValidOrderCourseTimes(paramMap);

				TOrderChange tOrderChange = new TOrderChange();
				tOrderChange.setId(changeId);
				tOrderChange.setUpdate_user(NumberUtils.object2Long(userId));
				tOrderChange.setChange_status(5L);
				tOrderChange.setValidate_time(DateUtil.getCurrDateTime());
				tOrderChangeDao.updateOrderChange(tOrderChange);
			}
		}
	}

	private void premiumFinConfirm(Long changeId, Long userId) throws Exception {
		TEncoder tEncoder = new TEncoder();
		tEncoder.setBusi_id(changeId);
		tEncoder.setBusi_type(5L);
		tEncoder = encoderService.queryEncoderInfo(tEncoder);

		TOrder tOrder = orderService.queryOrderInfo(tEncoder.getOrder_id());
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("studentId", tOrder.getStudent_id());
		TAccount tAccount = studentAccountService.queryAccountInfo(queryMap);
		if (tAccount == null) {
			studentAccountService.createAccount(tOrder.getStudent_id(), tOrder.getBu_id(), userId);
			tAccount = studentAccountService.queryAccountInfo(queryMap);
		}

		TAccountChange tAccountChange = new TAccountChange();
		tAccountChange.setAccount_id(tAccount.getId());
		tAccountChange.setOrder_id(tOrder.getId());
		tAccountChange.setEncoder_id(tEncoder.getId());
		tAccountChange.setChange_flag(0L);
		tAccountChange.setChange_type(2L);
		tAccountChange.setChange_amount(tEncoder.getFee_amount());
		Double preAmount = tAccount.getFee_amount() == null ? 0d : tAccount.getFee_amount();
		tAccountChange.setPre_amount(preAmount);
		tAccountChange.setNext_amount(preAmount + tEncoder.getFee_amount());
		tAccountChange.setPay_mode(1L);
		tAccountChange.setAccount_type(3L);
		studentAccountService.saveAccountChange(tAccountChange);

		studentAccountService.updateRefundAccount(tAccount.getId(), tAccountChange.getNext_amount());
		TFee tFee = new TFee();
		tFee.setOrder_id(tOrder.getId());
		tFee.setFee_type(32L);
		tFee.setFee_flag(2L);
		tFee.setFee_amount(tEncoder.getFee_amount());
		tFee.setInsert_time(DateUtil.getCurrDateTime());
		tFee.setFinish_time(DateUtil.getCurrDateTime());
		tFee.setFee_status(1L);
		tFee.setEncoder_id(tEncoder.getId());
		tFee.setOperate_type(5L);
		tFee.setOperate_no(String.valueOf(changeId));
		feeService.saveFee(tFee);

		tEncoder.setConfirm_time(DateUtil.getCurrDateTime());
		tEncoder.setUpdate_time(DateUtil.getCurrDateTime());
		tEncoder.setUpdate_user(userId);
		tEncoder.setStatus(1);
		encoderService.updateEncoderById(tEncoder);

		HashMap<String, Object> feeDataMap = new HashMap<>();
		feeDataMap.put("encoder_id", tEncoder.getId());
		feeDataMap.put("fee_status", 1);
		feeService.updateFeeStatusByEncoderId(feeDataMap);
	}

	private void premiumValidate(Long changeId, Long userId, Integer premiumType) throws Exception {
		TOrderChange tOrderChange = tOrderChangeDao.queryOrderChangeByChangId(changeId);
		List<TCOrderCourse> tcOrderCourseList = tcOrderCourseDao.queryTcOrderCourseByChangeId(changeId);
		Map<String, Object> paramMap = new HashMap<>();
		for (TCOrderCourse tcOrderCourse : tcOrderCourseList) {
			TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(tcOrderCourse.getOrder_course_id());
			paramMap.put("order_course_id", tOrderCourse.getId());
			paramMap.put("root_course_id", tOrderCourse.getRoot_course_id());

			Double manageFee = 0d;
			Long transOutOrderCourseId = null;
			List<TOrderCourse> tOrderCourseList = tOrderCourseDao.queryManageFee(paramMap);
			manageFee = CollectionUtils.isEmpty(tOrderCourseList) ? 0d : tOrderCourseList.get(0).getManage_fee();
			transOutOrderCourseId = CollectionUtils.isEmpty(tOrderCourseList) ? null : tOrderCourseList.get(0).getId();

			Long courseSurplusCount = 0L;
			tOrderCourseList = tOrderCourseDao.queryCourseSurplusCount(paramMap);
			courseSurplusCount = CollectionUtils.isEmpty(tOrderCourseList) ? 0L : tOrderCourseList.get(0).getCourse_surplus_count();

			if (premiumType != null && premiumType.intValue() == 2) {
				tOrderCourse.setManage_fee(tOrderCourse.getManage_fee() - Math.floor(tOrderCourse.getManage_fee() * tcOrderCourse.getCourse_times() / tOrderCourse.getCourse_surplus_count()));
				tOrderCourse.setCourse_surplus_count(tOrderCourse.getCourse_surplus_count() - tcOrderCourse.getCourse_times());
				tOrderCourse.setQuit_flag(1L);
				tOrderCourse.setSurplus_cost(tOrderCourse.getCourse_surplus_count() * tOrderCourse.getDiscount_unit_price());
				tOrderCourse.setUpdate_user(userId);
				tOrderCourse.setUpdate_time(DateUtil.getCurrDateTime());
				tOrderCourseDao.updateOrderCourse(tOrderCourse);

				if (tOrderCourse.getRoot_course_id() != null) {
					TOrderCourse rootOrderCourse = tOrderCourseDao.queryOrderCourseById(tOrderCourse.getRoot_course_id());
					Long rootOrderCourseSurplusCount = rootOrderCourse.getCourse_surplus_count() == null ? 0L : rootOrderCourse.getCourse_surplus_count();
					rootOrderCourse.setManage_fee(rootOrderCourse.getManage_fee() - Math.floor(rootOrderCourse.getManage_fee() * tcOrderCourse.getCourse_times() / (courseSurplusCount == 0 ? 1 : courseSurplusCount)));
					rootOrderCourse.setQuit_flag(1L);
					rootOrderCourse.setSurplus_cost(rootOrderCourseSurplusCount * rootOrderCourse.getDiscount_unit_price());
					rootOrderCourse.setUpdate_time(DateUtil.getCurrDateTime());
					rootOrderCourse.setUpdate_user(userId);
					tOrderCourseDao.updateOrderCourse(rootOrderCourse);
				}
			} else {
				if (tOrderCourse.getDiscount_sum_price() != null && tOrderCourse.getDiscount_sum_price() > 0) {
					tOrderCourse.setDiscount_unit_price(tOrderCourse.getFormer_unit_price());
				}
				tOrderCourse.setManage_fee(0d);
				tOrderCourse.setCourse_surplus_count(tOrderCourse.getCourse_surplus_count() - tcOrderCourse.getCourse_times());
				tOrderCourse.setCourse_schedule_count(tOrderCourse.getCourse_schedule_count() - tcOrderCourse.getCourse_times());
				tOrderCourse.setQuit_flag(1L);
				tOrderCourse.setSurplus_cost(tOrderCourse.getCourse_surplus_count() * tOrderCourse.getFormer_unit_price());
				tOrderCourse.setUpdate_user(userId);
				tOrderCourse.setUpdate_time(DateUtil.getCurrDateTime());
				tOrderCourseDao.updateOrderCourse(tOrderCourse);

				if (tOrderCourse.getRoot_course_id() != null) {
					TOrderCourse rootOrderCourse = tOrderCourseDao.queryOrderCourseById(tOrderCourse.getRoot_course_id());
					if (rootOrderCourse.getDiscount_sum_price() != null && rootOrderCourse.getDiscount_sum_price() > 0) {
						rootOrderCourse.setDiscount_unit_price(rootOrderCourse.getFormer_unit_price());
					}
					Long rootOrderCourseSurplusCount = rootOrderCourse.getCourse_surplus_count() == null ? 0L : rootOrderCourse.getCourse_surplus_count();
					rootOrderCourse.setManage_fee(0d);
					rootOrderCourse.setQuit_flag(1L);
					rootOrderCourse.setSurplus_cost(rootOrderCourseSurplusCount * rootOrderCourse.getFormer_unit_price());
					rootOrderCourse.setUpdate_time(DateUtil.getCurrDateTime());
					rootOrderCourse.setUpdate_user(userId);
					tOrderCourseDao.updateOrderCourse(rootOrderCourse);
				}
			}
		}

		paramMap.put("change_id", changeId);
		orderCourseTimesInfoService.updateValidOrderCourseTimes(paramMap);
		paramMap.put("change_stuatus", 5);
		tOrderChangeDao.updateOrderChangeStatus(paramMap);
	}

	/**
	 * vip退费费用处理
	 */
	private void vipPremiumFee(List<TCOrderCourse> tcOrderCourseList, Long changeId, Long userId) throws Exception {
		Long orderId = null;
		Long orderCourseId = null;
		Map<String, Object> queryMap = new HashMap<>();
		for (TCOrderCourse tcOrderCourse : tcOrderCourseList) {
			orderCourseId = tcOrderCourse.getOrder_course_id();
			orderId = tcOrderCourse.getOrder_id();
			TFeeDetail tFeeDetail = new TFeeDetail();
			tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
			tFeeDetail.setOrder_detail_id(orderCourseId);
			tFeeDetail.setOperate_type(5L);
			tFeeDetail.setOperate_no(changeId);
			tFeeDetail.setFee_amount(tcOrderCourse.getTotal_amount() < 0 ? 0 : tcOrderCourse.getTotal_amount());
			tFeeDetail.setFee_type(51L);
			tFeeDetail.setFee_flag(2L);
			tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
			feeDetailService.saveFeeDetail(tFeeDetail);

			if (tcOrderCourse.getPre_amount() != null && tcOrderCourse.getPre_amount() > 0) {
				queryMap.put("orderCourseId", orderCourseId);
				queryMap.put("rootCourseId", tcOrderCourse.getRoot_course_id());
				Map<String, Object> manageFeeMap = tOrderCourseDao.queryTotalManageFee(queryMap);
				Double manageFee = NumberUtils.object2Double(manageFeeMap.get("manage_fee"));

				tFeeDetail = new TFeeDetail();
				tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
				tFeeDetail.setOrder_detail_id(orderCourseId);
				tFeeDetail.setOperate_type(5L);
				tFeeDetail.setOperate_no(changeId);
				// 返预结转时收付费标识记为收费,费用金额记为负数
				tFeeDetail.setFee_amount(-1 * manageFee);
				tFeeDetail.setFee_type(42L);
				tFeeDetail.setFee_flag(1L);
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);

				tFeeDetail = new TFeeDetail();
				tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
				tFeeDetail.setOrder_detail_id(orderCourseId);
				tFeeDetail.setOperate_type(5L);
				tFeeDetail.setOperate_no(changeId);
				// 返预结转时收付费标识记为收费,费用金额记为负数
				tFeeDetail.setFee_amount(manageFee - tcOrderCourse.getPre_amount());
				tFeeDetail.setFee_type(42L);
				tFeeDetail.setFee_flag(1L);
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);

				queryMap.put("order_course_id", orderCourseId);
				queryMap.put("root_course_id", tcOrderCourse.getRoot_course_id());
				List<TOrderCourse> transOutOrderCourseList  = tOrderCourseDao.queryManageFee(queryMap);
				if (!CollectionUtils.isEmpty(transOutOrderCourseList)) {
					TOrderCourse transOutOrderCourse = transOutOrderCourseList.get(0);
					if (transOutOrderCourse.getId() != null && transOutOrderCourse.getId().longValue() != orderCourseId) {
						// 从退费子单中转出一笔
						tFeeDetail = new TFeeDetail();
						tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
						tFeeDetail.setOrder_detail_id(transOutOrderCourse.getId());
						tFeeDetail.setOperate_type(5L);
						tFeeDetail.setOperate_no(changeId);
						tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
						tFeeDetail.setFee_type(52L);
						tFeeDetail.setFee_flag(2L);
						tFeeDetail.setCourse_sum(0L);
						feeDetailService.saveFeeDetail(tFeeDetail);

						// 从退费子单中转出一笔
						tFeeDetail = new TFeeDetail();
						tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
						tFeeDetail.setOrder_detail_id(transOutOrderCourse.getId());
						tFeeDetail.setOperate_type(5L);
						tFeeDetail.setOperate_no(changeId);
						tFeeDetail.setFee_amount(-1 * tcOrderCourse.getPre_amount());
						tFeeDetail.setFee_type(42L);
						tFeeDetail.setFee_flag(2L);
						tFeeDetail.setCourse_sum(0L);
						feeDetailService.saveFeeDetail(tFeeDetail);

						// 转入到回复原价的子单中,转入的金额就是补结转的金额
						tFeeDetail = new TFeeDetail();
						tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
						tFeeDetail.setOrder_detail_id(orderCourseId);
						tFeeDetail.setOperate_type(5L);
						tFeeDetail.setOperate_no(changeId);
						tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
						tFeeDetail.setFee_type(42L);
						tFeeDetail.setFee_flag(1L);
						tFeeDetail.setCourse_sum(0L);
						feeDetailService.saveFeeDetail(tFeeDetail);

						tFeeDetail = new TFeeDetail();
						tFeeDetail.setOrder_id(tcOrderCourse.getOrder_id());
						tFeeDetail.setOrder_detail_id(orderCourseId);
						tFeeDetail.setOperate_type(5L);
						tFeeDetail.setOperate_no(changeId);
						tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
						tFeeDetail.setFee_type(52L);
						tFeeDetail.setFee_flag(1L);
						tFeeDetail.setCourse_sum(0L);
						feeDetailService.saveFeeDetail(tFeeDetail);
					}
				}
			}
		}

		TOrderChange tOrderChange = tOrderChangeDao.queryOrderChangeByChangId(changeId);
		Double feeDeductionAmount = tOrderChange.getFee_deduction_amount() == null ? 0 : tOrderChange.getFee_deduction_amount();
		if (feeDeductionAmount > 0) {
			TFeeDetail tFeeDetail = new TFeeDetail();
			tFeeDetail.setFee_type(54L);
			tFeeDetail.setFee_flag(2L);
			tFeeDetail.setFee_amount(-1 * feeDeductionAmount);
			tFeeDetail.setCourse_sum(0L);
			tFeeDetail.setOrder_detail_id(orderCourseId);
			feeDetailService.saveFeeDetail(tFeeDetail);
		}

		TFeeDetail queryDetail = new TFeeDetail();
		queryDetail.setOperate_no(changeId);
		queryDetail.setOperate_type(5L);
		List<TFeeDetail> feeDetailList = feeDetailService.queryFeeDetailByChangeId(queryDetail);
		if (!CollectionUtils.isEmpty(feeDetailList)) {
			for (TFeeDetail tFeeDetail : feeDetailList) {
				TFee tFee = new TFee();
				tFee.setFee_type(tFeeDetail.getFee_type());
				tFee.setOrder_id(tFeeDetail.getOrder_id());
				tFee.setFee_flag(tFeeDetail.getFee_flag());
				tFee.setFee_amount(tFeeDetail.getFee_amount());
				tFee.setOperate_no(String.valueOf(changeId));
				tFee.setOperate_type(5L);
				tFee.setInsert_time(DateUtil.getCurrDateTime());
				tFee.setFee_status(0L);
				feeService.saveFee(tFee);
				tFeeDetail.setFee_id(tFee.getId());
				tFeeDetail.setOperate_no(changeId);
				feeDetailService.updateFeeIdByFeeChangeId(tFeeDetail);
			}

			queryMap.clear();
			queryMap.put("operate_type", 5);
			queryMap.put("fee_type", "51,54");
			queryMap.put("operate_no", changeId);
			TFee tFee = feeService.queryFeeAmountByChangeId(queryMap);
			if (tFee != null && tFee.getFee_amount() != null) {
				Double feeAmount = tFee.getFee_amount() < 0 ? 0 : tFee.getFee_amount();
				TEncoder tEncoder = new TEncoder();
				tEncoder.setEncoder_no("OC" + changeId);
				tEncoder.setEncoder_type(7L);
				tEncoder.setFee_flag(2L);
				tEncoder.setOrder_id(orderId);
				tEncoder.setBusi_type(5L);
				tEncoder.setBusi_id(changeId);
				tEncoder.setStatus(0);
				tEncoder.setFee_amount(feeAmount);
				encoderService.saveTEncoder(tEncoder);
				queryMap.put("encoder_id", tEncoder.getId());
				feeService.updateFeeEncoderIdByChangeId(queryMap);

				queryMap.clear();
				queryMap.put("premium_deduction_amount", feeDeductionAmount);
				queryMap.put("fee_return_amount", feeAmount + feeDeductionAmount);
				queryMap.put("fee_amount", feeAmount);
				queryMap.put("change_id", changeId);
				tOrderChangeDao.updateAmountsByChangeId(queryMap);
			}
		}
	}

	@Override
	public void orderChangeCheck(Map<String, Object> params) throws Exception {
		Long orderId = NumberUtils.object2Long(params.get("order_id"));
		this.orderChangeCheckService.isOrderLock(params, "当前订单被锁定，不能订单作废!");
		this.orderChangeCheckService.hasAttendedCourseTimes(orderId, "订单包含有效考勤，不能作废，如要作废需将考勤置空！");
		this.orderChangeCheckService.checkOrderChangeCount(orderId, "该订单已有批改操作，请撤销做作废批改操作后，进行作废处理！");
		this.orderChangeCheckService.isOrderCanceled(orderId, "该订单已作废！");
		this.orderChangeCheckService.hasTransfer(orderId, "该订单存在跨校区转班，且转出去的课时没有转回来，需要转班回原校区才能作废！");
	}

	@Override
	public Map<String, Object> cancelOrder(TabOrderInfo orderInfo, String remark, Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");
		
		try {
			Long orderId = orderInfo.getId();
			if (orderId == null || userId == null) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}

			Date curDateTime = DateUtil.getCurrDateTime();
			TOrderChange orderChange = new TOrderChange();
			orderChange.setOrder_id(orderId);
			orderChange.setChange_type(3L);
			orderChange.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.DD_ZF_PREFIX));
			orderChange.setApply_user(userId);
			orderChange.setApply_time(curDateTime);
			orderChange.setBranch_id(orderInfo.getBranch_id());
			orderChange.setCreate_user(userId);
			orderChange.setCreate_time(curDateTime);
			orderChange.setRemark(remark);
			orderChange.setInput_time(curDateTime);
			orderChange.setInput_user(userId);
			tOrderChangeDao.saveOrderChange(orderChange);

//			TLock tLock = new TLock();
//			tLock.setType(1L);
//			tLock.setResourceId(orderId);
//			tLock.setBusiType(5L);
//			tLock.setBusiId(orderChange.getId());
//			this.tLockDao.saveLock(tLock);

			TFee oldFee = feeService.queryFeeByOrderIdAndFeeType(orderInfo.getId(), 11);

			TEncoder encoder = new TEncoder();
			encoder.setEncoder_type(11L);
			encoder.setOrder_id(orderId);
			encoder.setBusi_type(5L);
			encoder.setBusi_id(orderChange.getId());
			encoder.setCreate_time(curDateTime);
			encoder.setCreate_user(userId);
			encoder.setUpdate_time(curDateTime);
			encoder.setUpdate_user(userId);
			encoder.setStatus(1);
			encoder.setFee_amount(oldFee.getFee_amount());
			encoder.setFee_flag(2L);
			encoderService.saveTEncoder(encoder);

			encoder.setEncoder_no("ENCODER" + encoder.getId());
			encoderService.updateEncoderNo(encoder);

			TFee fee = new TFee();
			fee.setOrder_id(orderId);
			fee.setFee_type(53L);
			fee.setFee_flag(2L);
			fee.setFee_amount(oldFee.getFee_amount());
			fee.setPay_mode(oldFee.getPay_mode());
			fee.setInsert_time(curDateTime);
			fee.setFinish_time(curDateTime);
			fee.setFee_status(1L);
			fee.setEncoder_id(encoder.getId());
			fee.setOperate_type(5L);
			fee.setOperate_no(orderChange.getId().toString());
			feeService.saveFee(fee);

			List<TOrderCourse> orderCourseList = tOrderCourseDao.queryOrderCoursePage(orderId);
			if (!CollectionUtils.isEmpty(orderCourseList)) {
				TFeeDetail feeDetail = null;
				for (TOrderCourse orderCourse : orderCourseList) {
					feeDetail = new TFeeDetail();
					feeDetail.setFee_id(fee.getId());
					feeDetail.setOrder_id(orderId);
					feeDetail.setOrder_detail_id(orderCourse.getId());
					feeDetail.setFee_type(53L);
					feeDetail.setFee_flag(2L);
					feeDetail.setFee_amount(orderCourse.getSurplus_cost() + orderCourse.getManage_fee());
					feeDetail.setCourse_sum(orderCourse.getCourse_surplus_count());
					feeDetail.setOperate_type(5L);
					feeDetail.setOperate_no(orderChange.getId());
					feeDetailService.saveFeeDetail(feeDetail);
				}
			}

			map.put("fee_amount", -1 * oldFee.getFee_amount());
			map.put("change_id", orderChange.getId());
			tOrderChangeDao.updateFeeAmountByChangeId(map);

			// 生成应退费用
			TFee refundFee = new TFee();
			refundFee.setOrder_id(orderId);
			refundFee.setFee_type(32L);
			refundFee.setFee_flag(2L);
			refundFee.setFee_amount(oldFee.getFee_amount());
			refundFee.setInsert_time(curDateTime);
			refundFee.setFinish_time(curDateTime);
			refundFee.setFee_status(1L);
			refundFee.setEncoder_id(encoder.getId());
			refundFee.setOperate_type(5L);
			refundFee.setOperate_no(orderChange.getId().toString());
			feeService.saveFee(fee);

			// 生成资金用途
			TFinFee finFee = null;
			TFinFeeUse finFeeUse = null;

			TBankAccount bankAccount = null;
			TAccountChange accountChange = null;

			TabOrderPayCost orderPayCost = this.orderPayCostService.queryTabOrderPayCost(orderId);
			if (orderPayCost != null && !CollectionUtils.isEmpty(orderPayCost.getDetails())) {
				for (TabOrderPayCostDetail orderPayCostDetail : orderPayCost.getDetails()) {
					finFee = new TFinFee();
					finFee.setPay_flag(2L);

					finFeeUse = new TFinFeeUse();
					finFeeUse.setUse_type(11L);
					finFeeUse.setOrder_id(orderId);
					finFeeUse.setEncoder_id(encoder.getId());

					bankAccount = new TBankAccount();

					long paymentWay = orderPayCostDetail.getPayment_way();
					if (paymentWay == 1 || paymentWay == 2 || paymentWay == 3) {
						finFee.setStudent_id(orderPayCost.getStudentId());
						finFee.setPay_mode(orderPayCostDetail.getPayment_way());
						finFee.setFee_amount(orderPayCostDetail.getStaffappprem().doubleValue());
						finFeeService.saveTFinFee(finFee);

						finFeeUse.setFin_fee_id(finFee.getId());
						finFeeUse.setFee_amount(orderPayCostDetail.getStaffappprem().doubleValue());
						finFeeService.saveTFinFeeUse(finFeeUse);

						if (paymentWay == 2 || paymentWay == 3) {
							bankAccount.setFin_fee_id(finFee.getId());
							bankAccount.setAccount_owner(orderPayCostDetail.getClient_name());
							bankAccount.setAccount_no(orderPayCostDetail.getClient_card_no());
							finFeeService.saveTBankAccount(bankAccount);
						}
					} else if (paymentWay == 4) { // 储值账户
						TAccount account = accountService.queryByStudentIdAndBuId(orderInfo.getStudent_id(), orderInfo.getBu_id());
						if (account != null) {
							accountChange = new TAccountChange();
							accountChange.setChange_flag(0L);
							accountChange.setChange_type(11L);
							accountChange.setPay_mode(1L);
							accountChange.setAccount_id(account.getId());
							accountChange.setOrder_id(orderId);
							accountChange.setEncoder_id(encoder.getId());
							accountChange.setChange_amount(orderPayCostDetail.getStaffappprem().doubleValue());
							accountChange.setPre_amount(account.getFee_amount());
							accountChange.setNext_amount(account.getFee_amount() + orderPayCostDetail.getStaffappprem());
							accountChange.setAccount_type(1L);
							studentAccountService.saveAccountChange(accountChange);

							studentAccountService.updateFeeAccount(account.getId(), accountChange.getNext_amount());
						}
					}
				}
			}

			TOrder order = new TOrder();
			order.setId(orderId);
			order.setOrder_status(0L);
			orderService.updateOrderStatus(order);
			orderCourseTimesInfoService.updateInValidOrderCourseTimes(orderId);
			orderInfoService.updateOrderStatusById(orderId, 0);
//			tLockDao.releaseLock(tLock);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			return map;
		}
	}
	
	/**
	 * 退费单据-分页查询
	 */
	@Override
	public Page<TOrderChange> queryRefundForPage(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		return tOrderChangeDao.queryRefundForPage(param);
	}

	/**
     * 退费单据-详细的分页查询
     * @param param
     * @return
     * @throws Exception
     */
	@Override
	public Page<TCOrderCourse> queryRefundDetailForPage(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		return tOrderChangeDao.queryRefundDetailForPage(param);
	}

	/**
     * 退费单据-作废
     * @param remark
     * @param changeId
     * @param userId
     * @return
     * @throws Exception
     */
	@Override
	public void cancelRefund(String remark, Long changeId, Long userId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("p_change_id", changeId);
		paramMap.put("P_input_user", userId);
		paramMap.put("p_remark", remark);
		paramMap.put("p_encoding", EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.TF_ZF_PREFIX));

		tOrderChangeDao.cancelRefund(paramMap);
		if (!paramMap.get("o_err_code").toString().equals("0")) {
            throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
        }

	}
	
	/**
     * 冻结单据-作废
     * @param remark
     * @param changeId
     * @param userId
     * @return
     * @throws Exception
     */
	@Override
	public void cancelFrozen(String remark, Long changeId, Long userId) throws Exception {
		TOrderChange orderChange=tOrderChangeDao.queryOrderChangeByChangId(changeId);
		orderChange.setRemark(remark);
		orderChange.setInput_user(userId);
		orderChange.setEncoding(EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.TF_ZF_PREFIX ));
		orderFrozen.cancelFrozenOrder(orderChange, 1L);

	}

	/**
     * 退费单据-标记已取款
     * @param changeId
     * @param userId
     * @throws Exception
     */
	@Override
	public void drawRefund(Long changeId, Long userId) throws Exception {
		Assert.notNull(changeId);
		Assert.notNull(userId);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("change_id", changeId);
		paramMap.put("update_user", userId);
		
		tOrderChangeDao.drawRefund(paramMap);
	}

	@Override
	public List<TOrderCourse> queryOrderCourseByChangeNo(String change_no)
			throws Exception {
		return tOrderCourseDao.queryOrderCourseByChangeNo(change_no);
	}

	@Override
	public Integer queryChangeCourseNum(Map<String, Object> param) throws Exception {
		return tOrderChangeDao.queryChangeCourseNum(param);
	}

	@Override
	public int saveOrderChange(TOrderChange tOrderChange) throws Exception {
		return tOrderChangeDao.saveOrderChange(tOrderChange);
	}

	@Override
	public void updateChangeIdOnChangeCourse(Map<String, Object> param)
			throws Exception {
		tOrderChangeDao.updateChangeIdOnChangeCourse(param);
	}

	@Override
	public TOrderChange queryOrderChangeByChangId(Long change_id)
			throws Exception {
		return tOrderChangeDao.queryOrderChangeByChangId(change_id);
	}

	@Override
	public void updateAmountByChangeId(Map<String, Object> param)
			throws Exception {
		tOrderChangeDao.updateAmountByChangeId(param);
		
	}

	@Override
	public void updateOrderChangeStatus(Map<String, Object> param)
			throws Exception {
		tOrderChangeDao.updateOrderChangeStatus(param);
		
	}
    
	/***
	 * Description ： 订单作废 service 接口
	 * 
	 * @param remark
	 *            : 备注
	 * @param orderId
	 *            ：订单Id
	 * @param userId
	 *            ：操作用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> doOrderChange_3(String remark, Long orderId,
			Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");
		
		try {
			if (StringUtil.isEmpty(orderId) || StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_order_id", orderId);
			paramMap.put("P_input_user", userId);
			paramMap.put("p_remark", remark);
			paramMap.put("p_encoding", EncodingSequenceUtil.getSequenceNum(Constants.EncodingPrefixSeq.DD_ZF_PREFIX));

			tOrderChangeDao.doOrderChange_3(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				map.put("message", paramMap.get("o_err_desc"));
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			return map;
		}
	}
	

}
