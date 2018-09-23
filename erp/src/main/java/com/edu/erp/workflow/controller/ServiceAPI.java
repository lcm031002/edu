package com.edu.erp.workflow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.dao.OrderInfoDao;
import com.edu.erp.dao.OrderPayCostDao;
import com.edu.erp.dao.TCourseDao;
import com.edu.erp.model.*;
import com.edu.erp.orders.ext.IOrderYDY;
import com.edu.erp.student.service.StudentRecorderService;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.edu.common.util.ApplicationContextUtil;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderPayCostService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.workflow.service.UserTaskService;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @ClassName: ServiceAPI
 * @Description: 工作流服务调用中间类
 *
 */
public class ServiceAPI implements Serializable {

	private static final long serialVersionUID = -8984431341689918432L;

	private static final Logger logger = Logger.getLogger(ServiceAPI.class);

	private OrderPayCostDao orderPayCostDao= (OrderPayCostDao) ApplicationContextUtil
			.getBean("orderPayCostDao");

	private OrderInfoDao orderInfoDao= (OrderInfoDao) ApplicationContextUtil
			.getBean("orderInfoDao");

	private TCourseDao tCourseDao= (TCourseDao) ApplicationContextUtil
			.getBean("tCourseDao");

	private IOrderYDY iOrderYDY=(IOrderYDY) ApplicationContextUtil
			.getBean("iOrderYDY");

	/***
	 * Description ： 订单作废 service 接口
	 * 
	 * @param remark
	 *            : 备注
	 * @param orderId
	 *            ：订单Id
	 * @param userId
	 *            ：操作用户
	 * @return 返回工作流的服务调用结果
	 * @throws Exception
	 */
	public Map<String, Object> doOrderChange_3(String remark, Long orderId,
			Long userId) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("doOrderChange_3:remark is " + remark + ",orderId is "
					+ orderId + ",userId is" + userId);
		}
		Map<String, Object> result = null;
		try {
			OrderInfoService orderInfoService = (OrderInfoService) ApplicationContextUtil
					.getBean("orderInfoService");
			TabOrderInfo orderInfo = orderInfoService
					.queryTemporaryOrderInfo(orderId);
			if (orderInfo.getValid_status() != null
					&& orderInfo.getValid_status().intValue() == 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("begin to doOrderChange_3:remark is " + remark
							+ ",orderId is " + orderId + ",userId is" + userId);
				}
				OrderChangeService orderChangeService = (OrderChangeService) ApplicationContextUtil
						.getBean("orderChangeService");
				result = orderChangeService.doOrderChange_3(remark, orderId,
						userId);
				if (logger.isDebugEnabled()) {
					logger.debug("end to doOrderChange_3:result is " + result);
				}
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("订单ID：");
				detailInfoStr.append(orderId);
				detailInfoStr.append("，");
				detailInfoStr.append("备注：");
				detailInfoStr.append(remark);
				LogOperateUtil.getInstance().LogOperate("订单作废",
						detailInfoStr.toString(), "用户ID：" + userId, "成功");
				// 根据缴费信息，发送mail
				// OrderInfoService =
				// orderInfoService.queryOneOrderInfo(orderId);
				// TabOrderPayCost payCostInfo = orderInfoService
				// .queryOrderPayCostInfoOne(orderId);
				// MailTools.sendMailAfterOrderCancel(orderInfo, payCostInfo);
				OrderPayCostService orderPayCostService = (OrderPayCostService) ApplicationContextUtil
						.getBean("orderPayCostService");
				TabOrderPayCost payCostInfo = orderPayCostService.queryTabOrderPayCost(orderId);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("订单已作废,不需要作废!");
				}
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("订单ID：");
				detailInfoStr.append(orderId);
				detailInfoStr.append("，");
				detailInfoStr.append("备注：");
				detailInfoStr.append(remark);
				detailInfoStr.append("，");
				detailInfoStr.append("订单已作废!");
				LogOperateUtil.getInstance().LogOperate("订单作废",
						detailInfoStr.toString(), "用户ID：" + userId, "成功");
			}
		} catch (Exception ex) {
			logger.error("error found ,see:", ex);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注：");
			detailInfoStr.append(remark);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(ex);
			LogOperateUtil.getInstance().LogOperate("订单作废",
					detailInfoStr.toString(), "用户ID：" + userId, "失败");
		}
		
		return result;
	}

	/**
	 * 
	 * @Title: doOrderUnpass
	 * @Description: 订单审批不通过
	 * @param remark
	 *            备注
	 * @param orderId
	 *            订单id
	 * @param userId
	 *            操作用户
	 * @throws Exception
	 *             设定文件
	 * @return Map<String,Object> 返回类型
	 */
	public Map<String, Object> doOrderUnpass(String remark, Long orderId,
			Long userId) throws Exception {
		try {
			OrderInfoService orderInfoService = (OrderInfoService) ApplicationContextUtil
					.getBean("orderInfoService");

			TabOrderInfo orderInfo = orderInfoService
					.queryTemporaryOrderInfo(orderId);
			if (null != orderInfo) {
				orderInfo.setCheck_status(4L);
				orderInfo.setValid_status(1L);
				if (StringUtils.isNotBlank(remark)) {
					String rnk = orderInfo.getRemark() + "[审批备注]" + remark;
					if (rnk.length() > 100) {
						orderInfo.setRemark(rnk.substring(0, 100));
					} else {
						orderInfo.setRemark(rnk);
					}
				}

				orderInfo.setUpdate_user(userId);
				orderInfo.setUpdate_time(new Date());

				// 保存订单信息
				orderInfoService.updateOrderInfo(orderInfo);
			}
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注：");
			detailInfoStr.append(remark);
			LogOperateUtil.getInstance().LogOperate("订单审批不通过",
					detailInfoStr.toString(), "用户ID：" + userId, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注：");
			detailInfoStr.append(remark);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("订单审批不通过",
					detailInfoStr.toString(), "用户ID：" + userId, "失败");
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 
	 * @Title: doAutoPass
	 * @Description: 自动审批
	 * @param orderId
	 *            订单ID
	 * @param applicationId
	 *            申请ID
	 * @throws Exception
	 *             设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String doAutoPass(Long orderId, String applicationId)
			throws Exception {
		logger.debug("doAutoPass:orderId is" + orderId + ",applicationId is "
				+ applicationId);
		String result = "menu";

		try {
			OrderInfoService orderInfoService = (OrderInfoService) ApplicationContextUtil
					.getBean("orderInfoService");

			TabOrderInfo orderBusiness = orderInfoService
					.queryTemporaryOrderInfo(orderId);
			orderBusiness.setCheck_status(2L);
			orderInfoService.updateOrderApproved(orderBusiness);

			// 对于立减的，需要直接人工审批
			if (null != orderBusiness && orderBusiness.getRule_id() == null
					&& orderBusiness.getImmediate_reduce() != null
					&& orderBusiness.getImmediate_reduce() > 0) {
				logger.debug("order is Immediate_reduce ,so need to be in workflow");
				return result;
			}
			logger.debug("begin to check isOrderRuleValid.");

			PrivilegeRuleService privilegeRuleService = (PrivilegeRuleService) ApplicationContextUtil
					.getBean("privilegeRuleService");

			// 对于详单优惠规则，不符的，需要审批
			Map<String, Object> resultData = privilegeRuleService
					.isOrderRuleValid(orderId);
			logger.debug("result is: " + resultData);
			Boolean error = (Boolean) resultData.get("error");
			if (error) {
				logger.debug("order need to be in workflow");
				// 对于优惠规则，符合的，自动审批通过
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("订单ID：");
				detailInfoStr.append(orderId);
				detailInfoStr.append(";");
				detailInfoStr.append("审批信息：");
				detailInfoStr.append(resultData.get("message"));
				LogOperateUtil.getInstance().LogOperate("订单自动审批",
						detailInfoStr.toString(), "用户ID：System", "失败");
				return result;
			}

			// 对于优惠规则，符合的，自动审批通过
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append(",");
			detailInfoStr.append("订单自动审批成功;");

			LogOperateUtil.getInstance().LogOperate("订单自动审批",
					detailInfoStr.toString(), "用户ID：System", "成功");
			result = "autopass";
			updateStepStatus(applicationId);

			logger.debug("order doAutoPass");
		} catch (Exception e) {
			logger.error("error found ,see : ", e);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("订单自动审批",
					detailInfoStr.toString(), "用户ID：System", "失败");

		}
		return result;
	}

	private void updateStepStatus(String application_id) {
		if (StringUtils.isNotBlank(application_id)
				&& !"null".equals(application_id)) {
			Map<String, Object> userApplication = new HashMap<String, Object>();
			userApplication.put("ID", application_id);
			userApplication.put("CURRENT_STEP", "自动审批");
			userApplication.put("STATUS", 1);
			userApplication.put("CURRENT_STATE", "已通过");
			try {
				UserTaskService userApplicationTaskService = (UserTaskService) ApplicationContextUtil
						.getBean("userTaskService");

				userApplicationTaskService.updateApplication(userApplication);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: doOrderUnpass
	 * @Description: 订单审批通过
	 * @param remark
	 *            备注
	 * @param orderId
	 *            订单id
	 * @param userId
	 *            操作用户
	 * @throws Exception
	 *             设定文件
	 * @return Map<String,Object> 返回类型
	 */
	public Map<String, Object> doOrderPass(String remark, Long orderId,
			Long userId) throws Exception {
		try {
			OrderInfoService orderInfoService = (OrderInfoService) ApplicationContextUtil
					.getBean("orderInfoService");
			StudentRecorderService studentRecorderService = (StudentRecorderService) ApplicationContextUtil
					.getBean("studentRecorderService");
			TabOrderInfo orderInfo = orderInfoService
					.queryTemporaryOrderInfo(orderId);
			if (null != orderInfo) {
				orderInfo.setCheck_status(3L);
				orderInfo.setValid_status(1L);
				if (StringUtils.isNotBlank(remark)) {
					String rnk = (orderInfo.getRemark() == null ? ""
							: orderInfo.getRemark()) + "[审批备注]" + remark;
					if (rnk.length() > 100) {
						orderInfo.setRemark(rnk.substring(0, 100));
					} else {
						orderInfo.setRemark(rnk);
					}
				}

				orderInfo.setUpdate_user(userId);
				orderInfo.setUpdate_time(new Date());

				// 保存订单信息
				orderInfoService.updateOrderInfo(orderInfo);
			}
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注：");
			detailInfoStr.append(remark);
			LogOperateUtil.getInstance().LogOperate("订单审批通过",
					detailInfoStr.toString(), "用户ID：" + userId, "成功");
			//订单金额为0自动缴费
			if(orderInfo.getActual_price()==0L){
				payOrder(orderInfo);
			}
		} catch (Exception e) {
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("订单ID：");
			detailInfoStr.append(orderId);
			detailInfoStr.append("，");
			detailInfoStr.append("备注：");
			detailInfoStr.append(remark);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("订单审批通过",
					detailInfoStr.toString(), "用户ID：" + userId, "失败");
			logger.error(detailInfoStr.toString(),e);
			throw new Exception(detailInfoStr.toString());
		}
		return new HashMap<String, Object>();
	}

	private void payOrder(TabOrderInfo temporaryOrderInfo) throws Exception{
		if (temporaryOrderInfo.getCheck_status() == null
				|| temporaryOrderInfo.getCheck_status().intValue() != 3
				|| temporaryOrderInfo.getValid_status() == null
				|| temporaryOrderInfo.getValid_status().intValue() == 0
				|| (temporaryOrderInfo.getPay_status() != null && temporaryOrderInfo.getPay_status().intValue() == 1)) {
			throw new Exception("订单状态异常，不能支付！请联系管理员处理！");
		}
		// 保存订单支付信息
		TabOrderPayCost tabOrderPayCost = new TabOrderPayCost();
		tabOrderPayCost.setStudentId(temporaryOrderInfo.getStudent_id());
		tabOrderPayCost.setEncoding(temporaryOrderInfo.getEncoding());
		tabOrderPayCost.setSumPrice(temporaryOrderInfo.getSum_price().longValue());
		tabOrderPayCost.setActualPrice(temporaryOrderInfo.getActual_price().longValue());
		tabOrderPayCost.setOrderId(temporaryOrderInfo.getId());
		tabOrderPayCost.setBuyDate(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		tabOrderPayCost.setBuId(temporaryOrderInfo.getBu_id());
		tabOrderPayCost.setBranchId(temporaryOrderInfo.getBranch_id());
		tabOrderPayCost.setCity_id(temporaryOrderInfo.getCity_id());
		tabOrderPayCost.setPaymentType(1L);
		orderPayCostDao.saveOrderPayCost(tabOrderPayCost);
		TabOrderPayCostDetail tabOrderPayCostDetail=new TabOrderPayCostDetail();
		tabOrderPayCostDetail.setOrder_buy_id(tabOrderPayCost.getId());
		tabOrderPayCostDetail.setPayment_way(1L);
		tabOrderPayCostDetail.setStaffappprem(tabOrderPayCost.getActualPrice());
		tabOrderPayCostDetail.setCreate_time(new Date());
		orderPayCostDao.saveOrderPayCostDetail(tabOrderPayCostDetail);

		List<TabOrderPayCostDetail> details = new ArrayList<TabOrderPayCostDetail>(1);
		details.add(tabOrderPayCostDetail);
		tabOrderPayCost.setDetails(details);
		temporaryOrderInfo.setPayment(tabOrderPayCost);

		temporaryOrderInfo.setPay_status(1L);
		temporaryOrderInfo.setUpdate_user(0L);
		temporaryOrderInfo.setUpdate_time(new Date());
		temporaryOrderInfo.setApproved(0);
		updateOrderInfo(temporaryOrderInfo);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("order_id", temporaryOrderInfo.getId() + "");
		param.put("user_id", 0 + "");
		if(temporaryOrderInfo.getBusiness_type()==2){
			iOrderYDY.createOrder(temporaryOrderInfo);
		}else{
			orderInfoDao.createOrder(param);
		}

		// 业绩归属
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_id", temporaryOrderInfo.getId());
		map.put("user_id", 0 + "");
		orderInfoDao.updatePerformanceAttribution(map);

		// 更新课程最新报班人数
		if (!CollectionUtils.isEmpty(temporaryOrderInfo.getDetails())) {
			for (TabOrderInfoDetail orderInfoDetail : temporaryOrderInfo.getDetails()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("course_id", orderInfoDetail.getCourse_id());
				tCourseDao.changeActualPeople(param);
			}
		}
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

	private void updateOrderInfo(TabOrderInfo orderInfo) throws Exception {
		if (StringUtils.isEmpty(orderInfo.getEncoding())) {
			orderInfo.setEncoding(genEncoding(orderInfo.getBusiness_type()));
		}

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

		if (orderInfo.getPay_status() == null) {
			orderInfo.setPay_status(0L);
		}

		orderInfo.setUpdate_user(orderInfo.getCreate_user());
		orderInfo.setUpdate_time(new Date());

		orderInfoDao.updateOrderInfo(orderInfo);
	}

	/**
	 * 
	 * @Title: doOrderChangeAudit
	 * @Description: 学生退费审批处理操作
	 * @param change_id
	 *            退费单号
	 * @param remark
	 *            备注信息
	 * @param change_status
	 *            修改状态id
	 * @param userId
	 *            用户id
	 * @return Map<String,Object> 返回类型
	 */
	public Map<String, Object> doOrderChangeAudit(Long change_id,
			String remark, String change_status, Long userId) {
		String json = "{ " + "\"change_id\":" + change_id + ",\"remark\":\""
				+ remark + "\",\"change_status\":" + change_status + "}";
		try {
			OrderChangeService orderChangeService = (OrderChangeService) ApplicationContextUtil
					.getBean("orderChangeService");
			Map<String, Object> result = new HashMap<String,Object>();
			/*Map<String, Object> result = orderChangeService.doOrderChangeAudit(
					json, userId);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("退费审批变动ID：");
			detailInfoStr.append(change_id);
			detailInfoStr.append("，");
			detailInfoStr.append("修改状态：");
			detailInfoStr.append(change_status);
			detailInfoStr.append("，");
			detailInfoStr.append("备注信息：");
			detailInfoStr.append(remark);
			LogOperateUtil.getInstance().LogOperate("学生退费审批",
					detailInfoStr.toString(), "用户ID：" + userId, "成功");*/
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("退费审批变动ID：");
			detailInfoStr.append(change_id);
			detailInfoStr.append("，");
			detailInfoStr.append("修改状态：");
			detailInfoStr.append(change_status);
			detailInfoStr.append("，");
			detailInfoStr.append("备注信息：");
			detailInfoStr.append(remark);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("学生退费审批",
					detailInfoStr.toString(), "用户ID：" + userId, "失败");
		}
		return null;
	}

}
