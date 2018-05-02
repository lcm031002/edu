/**  
 * @Title: OrderChangeServiceImpl.java
 * @Package com.ebusiness.erp.orders.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月13日 下午8:52:22
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.util.NumberUtils;
import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import com.edu.erp.util.*;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.orders.ext.IOrderFrozen;
import com.edu.erp.orders.ext.IOrderRefund;
import com.edu.erp.orders.ext.IOrderYDY;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;

/**
 * @ClassName: OrderChangeServiceImpl
 * @Description: 订单修改服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月13日 下午8:52:22
 * 
 */
@Service(value = "orderChangeService")
public class OrderChangeServiceImpl implements OrderChangeService {
	private static final Logger log = Logger
			.getLogger(OrderChangeServiceImpl.class);
	
	
	@Resource(name = "tOrderChangeDao")
	private TOrderChangeDao tOrderChangeDao;

	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;

	@Resource(name = "tCourseDao")
	private TCourseDao tCourseDao;

	@Resource(name = "tCOrderCourseDao")
	private TCOrderCourseDao tCOrderCourseDao;

	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;
	
	@Resource(name = "iOrderYDY")
	private IOrderYDY iOrderYDY;
	
	@Resource(name = "orderRefund")
	private IOrderRefund orderRefund;
	
	@Resource(name = "orderFrozen")
	private IOrderFrozen orderFrozen;

	@Resource(name = "tabChangeCourseDao")
	private TabChangeCourseDao tabChangeCourseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderChangeService#orderChangeTransfer
	 * (java.util.Map)
	 */
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
// 		Assert.notNull(transferObj.get("p_remark"), "备注必填！");
		transferObj.put("p_encoding", EncodingSequenceUtil.getSequenceNum(3L));

		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("id", transferObj.get("p_order_detail_id"));
		List<TOrderCourse> orderDetailCourse = tOrderCourseDao.queryOrderCourse(queryParam);
		if (CollectionUtils.isEmpty(orderDetailCourse)) {
			log.error("没有找到订单详情！");
			throw new Exception("没有找到订单详情！");
		}
		Long orderId = orderDetailCourse.get(0).getOrder_id();

		// 1对1的转班
//		if(TCourse.BusinessTypeEnum.YDY.getCode() == transferObj.get("businessType")) {
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
			TCOrderCourse outputTcOrderCourse = new TCOrderCourse();
			outputTcOrderCourse.setOrder_id(orderChange.getOrder_id());
			outputTcOrderCourse.setOrder_course_id(orderDetailCourse.get(0).getId());
			outputTcOrderCourse.setCourse_times(Long.valueOf(transferObj.get("P_output_count").toString()));
			BigDecimal outputCourseTimes = new BigDecimal(transferObj.get("P_output_count").toString());    
			BigDecimal outputUnitPrice = new BigDecimal(orderDetailCourse.get(0).getDiscount_unit_price());    
			outputTcOrderCourse.setTotal_amount(outputCourseTimes.multiply(outputUnitPrice).doubleValue());
			outputTcOrderCourse.setTransfer_flag(0L); //转出
			orderChange.getTcOrderCourse().add(outputTcOrderCourse);
			
			//转班子表-转入
			TCOrderCourse intputTcOrderCourse = new TCOrderCourse();
			intputTcOrderCourse.setOrder_id(orderChange.getOrder_id());
			intputTcOrderCourse.setOrder_course_id(orderDetailCourse.get(0).getId());
			intputTcOrderCourse.setCourse_times(Long.valueOf(transferObj.get("P_input_count").toString()));
			BigDecimal inputCourseTimes = new BigDecimal(transferObj.get("P_input_count").toString());    
			intputTcOrderCourse.setTotal_amount(inputCourseTimes.multiply(outputUnitPrice).doubleValue());
			intputTcOrderCourse.setCourse_id(Long.valueOf(transferObj.get("p_input_course_id").toString()));
			intputTcOrderCourse.setBranch_id(orderChange.getBranch_id());
			intputTcOrderCourse.setTransfer_flag(1L); //转入
			orderChange.getTcOrderCourse().add(intputTcOrderCourse);
			
			iOrderYDY.transferOrder(orderChange);
//		} else {
//			tOrderChangeDao.orderChangeTransfer(transferObj);
//
//			if (!transferObj.get("o_err_code").toString().equals("0")) {
//				log.error("error found,see:" + transferObj.get("o_err_desc"));
//				throw new Exception("存储过程异常" + transferObj.get("o_err_desc"));
//			}
//		}
		Map<String, Object> querymMap=new HashMap<String,Object>();
		querymMap.put("times",transferObj.get("p_input_times"));
		querymMap.put("course_id",transferObj.get("p_input_course_id"));
		querymMap.put("order_id",orderId);
//		querymMap.put("root_course_id",transferObj.get("p_order_detail_id"));
		List<TOrderCourse> orderCourseList=tOrderCourseDao.queryOrderCourseByRootTimes(querymMap);
		if(!CollectionUtils.isEmpty(orderCourseList)){
			TCourse courseBusiness=new TCourse();
			courseBusiness.setId(orderCourseList.get(0).getCourse_id());
			TCourse course=tCourseDao.queryCourseById(courseBusiness);
			orderCourseList.get(0).setCourse(course);
			transferObj.put("rollInOrderCourseInfo",orderCourseList.get(0));
			//查询转入的课次ID
			Long o_change_id=Long.parseLong(transferObj.get("o_change_id").toString());
			List<TOrderCourseTimes> tcCourseTimesList=tCOrderCourseDao.queryOrderCourseTimesByChangeId(o_change_id);
			transferObj.put("tcCourseTimesList",tcCourseTimesList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderChangeService#orderChangeRefund
	 * (java.util.Map)
	 */
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
		//String changeNo = new RandomGUID().toString();
		//refundObj.put("change_no", changeNo);
		refundObj.put("change_type", 1);
		Long orderId = Long.parseLong(refundObj.get("order_id") + "");
		TabOrderInfo orderInfo = orderInfoService
				.queryTemporaryOrderInfo(orderId);
		if (orderInfo == null) {
			throw new Exception("订单未找到！orderId is " + orderId);
		}

//		if (orderInfo.getInvoice_status() != null
//				&& orderInfo.getInvoice_status().intValue() == 1) {
//			throw new Exception("订单已经开出发票，请回收发票后再退费！");
//		}

		Long businessType = orderInfo.getBusiness_type();
//		if (Constants.BusinessType.GXH == businessType) {
            this.orderRefund.readyPremium(refundObj, businessType);
//        }
//        else {
//            if ("2".equals(refundObj.get("premiumType"))) {
//                tOrderChangeDao.readyVIPPremium(refundObj);
//            } else {
//                tOrderChangeDao.readyPremium(refundObj);
//            }
//
//            if (!CollectionUtils.isEmpty(refundObj) && !"0".equals(refundObj.get("error_code") + "")) {
//				throw new Exception("存储过程异常" + refundObj.get("error_desc") + "");
//			}
//        }

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

//		if (!WorkflowHelper.isDeployed(processEngine, "erpv5.DXB_tuifei")) {
//			throw new Exception("退费流程尚未发布，请联系管理员发布退费流程!");
//		}
		
//		if (Constants.BusinessType.GXH == businessType) {
		    this.orderRefund.premium(refundObj, businessType);
//		} else {
//		    tOrderChangeDao.premium(refundObj);
//		}

//		if (!CollectionUtils.isEmpty(refundObj)
//				&& "0".equals(refundObj.get("error_code") + "")) {
//			refundObj.put("change_id",
//					Long.parseLong(refundObj.get("v_change_id") + ""));
//			refundObj.put("order_id", orderId);
//
//			Map<String, Object> workflowParam = new HashMap<String, Object>();
//			workflowParam.put("change_id", Long.parseLong(refundObj.get("v_change_id") + ""));
//			workflowParam.put("remark_order", refundObj.get("p_remark"));
//			workflowParam.put("student_id", orderInfo.getStudent_id());
//			workflowParam.put("student_name", orderInfo.getStudent_name());
//			workflowParam.put("student_encoding",
//					orderInfo.getStudent_encoding());
//			workflowParam.put("order_encoding", orderInfo.getEncoding());
//			workflowParam.put("order_id", orderInfo.getId());
//			workflowParam.put("branch_id", orderInfo.getBranch_id());
//
//			Map<String, Object> queryParam = new HashMap<String, Object>();
//			queryParam.put("businessType", orderInfo.getBusiness_type());
//			queryParam.put("studentId", orderInfo.getStudent_id());
//			// 查询订单课程时，应该用当前的登录校区，不能用订单的校区（存在转班的情况）
//			//queryParam.put("branchId", orderInfo.getBranch_id());
//			queryParam.put("branchId", (Long) refundObj.get("branch_id"));
//			queryParam.put("id", refundObj.get("order_detail_id"));
//			queryParam.put("product_line", refundObj.get("product_line"));
//			List<TOrderCourse> orderDetailCourse = tOrderCourseDao
//					.queryStudentOrderCourse(queryParam);
//			if (CollectionUtils.isEmpty(orderDetailCourse)) {
//				log.error("没有找到订单详情！");
//				throw new Exception("没有找到订单详情！");
//			}
//			refundObj.put("course_name", orderDetailCourse.get(0)
//					.getCourse_name());
//			refundObj.put("teacher_name", orderDetailCourse.get(0)
//					.getTeacher_name());
//			refundObj.put("premium_deduction_amount",
//					refundObj.get("p_premium_deduction_amount"));
//
//			workflowParam.put("businessDetailInfo",
//					DetailBusinessInfoFormat.tuifeiString(refundObj));
//			Map<String, Object> userApplication = new HashMap<String, Object>();
//			userApplication.put("APPLICATION_ID", refundObj.get("user_id"));
//			StringBuilder application = new StringBuilder("退费审批：");
//			if (null != orderInfo.getStudent_id()) {
//				application.append("学生ID[");
//				application.append(orderInfo.getStudent_name() + ""
//						+ orderInfo.getStudent_id());
//				application.append("]");
//			}
//
//			application.append("订单[");
//			application.append(orderInfo.getEncoding());
//			application.append("]");
//			application.append("详单ID[");
//			application.append(refundObj.get("order_detail_id"));
//			application.append("]");
//			application.append("[" + refundObj.get("premium_result") + "]");
//
//			userApplication.put("APPLICATION", application.toString());
//			userApplication.put("STATUS", 1L);
//			userApplication.put("CREATETIME",
//					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
//			userApplication.put(
//					"WORKURL",
//					"#/order-detail/" + orderInfo.getBusiness_type() + "/"
//							+ orderInfo.getStudent_id() + "/"
//							+ orderInfo.getId());
//			userApplication.put("REMARK", refundObj.get("premium_remark"));
//			userApplication.put("CURRENT_STATE", "申请已提交");
//			userApplication.put("CURRENT_STEP", "申请提交");
//
//			userApplication.put("CURRENT_MAN", refundObj.get("user_name"));
//
//			userApplication.put("UPDATETIME",
//					DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
//			userTaskService.insertApplication(userApplication);
//			workflowParam.put("application_id", userApplication.get("id"));
//
//			ProcessInstance pi = genProcessInstance(processEngine, orderInfo,
//					workflowParam);
//
//			processEngine.getExecutionService().createVariables(pi.getId(),
//					workflowParam, true);
//
//		} else {
//			throw new Exception(refundObj.get("error_desc") + "");
//		}
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
		String prefix_info = "workflow.tuifei2." + orderBusiness.getBu_id();
		String flag = DxbCityCfg.getInstance().getConfigItem(prefix_info,
				"false");
		if ("true".equals(flag)) {
			processInstance = processEngine.getExecutionService()
					.startProcessInstanceByKey("erpv5.DXB_tuifei2", mapps);
		} else {
			processInstance = processEngine.getExecutionService()
					.startProcessInstanceByKey("erpv5.DXB_tuifei", mapps);
		}
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
				course.put("attendType", 11l);// 挂起操作
			}
			// 批量考勤
			attendanceService.attandanceBatchSubmit(remainCourses,
					processEngine);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.orders.service.OrderChangeService#premiumAudit(java
	 * .util.Map)
	 */
	@Override
	public void premiumAudit(Map<String, Object> refundObj) throws Exception {
		Assert.notNull(refundObj);
		Assert.notNull(refundObj.get("p_change_id"));
		Assert.notNull(refundObj.get("P_input_user"));
		Assert.notNull(refundObj.get("p_change_status"));
		if (refundObj.get("p_remark") == null) {
			refundObj.put("p_remark", "");
		}

		refundObj.put("change_id", refundObj.get("p_change_id"));
		List<TabChangeCourse> changeCourseList = this.tabChangeCourseDao.queryChangeCourseInfo(refundObj);
		if (!CollectionUtils.isEmpty(changeCourseList)) {
			TabChangeCourse changeCourse = changeCourseList.get(0);
			Integer premiumType = changeCourse.getPremium_type();
			if (2 == premiumType) {

			} else {

			}
		}

	}

	@Override
	public void orderChangeCheck(Map<String, Object> params) throws Exception {
		tOrderChangeDao.orderChangeCheck(params);
		
	}

	@Override
	public Map<String, Object> cancelOrder(String remark, Long orderId,Long userId) throws Exception {
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
			paramMap.put("p_encoding", EncodingSequenceUtil.getSequenceNum(23L));

			tOrderChangeDao.cancelOrder(paramMap);

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
		paramMap.put("p_encoding", EncodingSequenceUtil.getSequenceNum(24L));

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
		orderChange.setEncoding(EncodingSequenceUtil.getSequenceNum(24L));
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
			paramMap.put("p_encoding", EncodingSequenceUtil.getSequenceNum(23L));

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
