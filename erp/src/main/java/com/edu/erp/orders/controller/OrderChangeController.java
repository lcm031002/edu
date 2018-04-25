/**  
 * @Title: OrderChangeController.java
 * @Package com.ebusiness.erp.orders.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年2月13日 下午8:59:27
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.orders.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.model.TCourse;
import com.edu.erp.orders.service.TcOrderCourseService;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.constants.Constants;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TLadder;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

/**
 * @ClassName: OrderChangeController
 * @Description: 学员批改服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年2月13日 下午8:59:27
 * 
 */
@Controller
@RequestMapping(value = { "/ordermanager" })
public class OrderChangeController extends BaseController{
	private static final Logger log = Logger.getLogger(OrderChangeController.class);

	@Resource(name = "orderChangeService")
	private OrderChangeService orderChangeService;

	@Resource(name = "tcOrderCourseService")
	private TcOrderCourseService tcOrderCourseService;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/transfer", headers = "Accept=application/json")
	public @ResponseBody
	Map transferClass(@RequestBody Map<String, Object> transfer,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (CollectionUtils.isEmpty(transfer)) {
				throw new Exception("非法访问！");
			}

			Account account = WebContextUtils.genUser(request);
			Map<String, Object> transferObj = new HashMap<String, Object>();
			transferObj.put("P_input_user", account.getId());

			if (transfer.get("orderDetailId") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferOutCount") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferOutCourseTimes") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferInCourseId") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferInBranchId") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferInCount") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("transferInCourseTimes") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("studentId") == null) {
				throw new Exception("非法访问！");
			}
			if (transfer.get("businessType") == null) {
				throw new Exception("非法访问！");
			}
			transferObj.put("businessType", transfer.get("businessType"));
			transferObj.put("p_order_detail_id", transfer.get("orderDetailId"));
			transferObj.put("P_output_count", transfer.get("transferOutCount"));
			transferObj.put("p_output_times",
					transfer.get("transferOutCourseTimes"));
			transferObj.put("transferOutCourseTimesList",
					transfer.get("transferOutCourseTimesList"));
			transferObj.put("transferInCourseTimesList",
					transfer.get("transferInCourseTimesList"));
			transferObj.put("p_input_course_id",
					transfer.get("transferInCourseId"));
			transferObj.put("p_input_branch_id",
					transfer.get("transferInBranchId"));
			transferObj.put("P_input_count", transfer.get("transferInCount"));
			transferObj.put("p_input_times",
					transfer.get("transferInCourseTimes"));
			transferObj.put("p_remark", transfer.get("remark"));
			orderChangeService.orderChangeTransfer(transferObj);

			StringBuilder strs = new StringBuilder();
			strs.append("转班学生ID：");
			strs.append(transfer.get("studentId"));
			strs.append("，");
			strs.append("转班参数：");
			strs.append(transferObj.toString());
			strs.append("，");
			strs.append("转班结果参数：");
			strs.append(map);
			LogOperateUtil.getInstance().LogOperate("转班", strs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
			map.put("error", false);
			map.put("data", transferObj);
		} catch (Exception e) {
			StringBuilder strs = new StringBuilder();
			strs.append("转班参数：");
			strs.append(transfer);
			strs.append("，");
			strs.append("转班结果参数：");
			strs.append(map);
			strs.append("转班失败原因：");
			strs.append(e);
			LogOperateUtil.getInstance().LogOperate("转班", strs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
			log.error("error found,see:", e);
			map.put("error", true);
			map.put("message", e.getMessage());
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/refund", headers = "Accept=application/json")
	public @ResponseBody
	Map refundClass(@RequestBody Map<String, Object> refund,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (CollectionUtils.isEmpty(refund)) {
				throw new Exception("非法访问！");
			}

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				throw new Exception("请选择校区!");
			}

			Map<String, Object> refundObj = new HashMap<String, Object>();

			if (refund.get("orderDetailId") == null) {
				throw new Exception("没有订单编号信息，退费失败！");
			}
			if (refund.get("premiumType") == null) {
				throw new Exception("没有选择退费公式，退费失败！");
			}
			if (refund.get("courseCnt") == null) {
				throw new Exception("没有退费课时，退费失败！");
			}
			if (refund.get("courseTimes") == null) {
				throw new Exception("没有退费课次，退费失败！");
			}
			if (refund.get("remark") == null) {
				throw new Exception("没有输入备注信息，退费失败！");
			}
			if (refund.get("premiumDeductionAmount") == null) {
				throw new Exception("没有补扣金额，退费失败！");
			}
			Account account = WebContextUtils.genUser(request);
			refundObj.put("product_line", orgModel.getProductLine());
			refundObj.put("user_id", account.getId());
			refundObj.put("user_name", account.getEmployeeName());
			refundObj.put("premiumType", refund.get("premiumType"));
			refundObj.put("order_detail_id", refund.get("orderDetailId"));
			refundObj.put("course_cnt", refund.get("courseCnt"));
			refundObj.put("course_times", refund.get("courseTimes"));
			refundObj.put("branch_id", orgModel.getId());
			refundObj.put("cityId", orgModel.getCityId());
			refundObj.put("order_id", refund.get("orderId"));
			
			refundObj.put("p_remark", refund.get("remark")==null?"":refund.get("remark"));
			refundObj.put("p_premium_deduction_amount",
					refund.get("premiumDeductionAmount"));
			refundObj.put("premium_result",
					refund.get("premium_result"));
			refundObj.put("premium_formula",
					refund.get("premium_formula"));
			refundObj.put("premium_detail",
					refund.get("premium_detail"));
			
			refundObj.put("premium_result_val", refund.get("premiumResultVal"));
			refundObj.put("is_approve",
					refund.get("is_approve") != null ? refund.get("is_approve")
							+ "" : "0");
			orderChangeService.orderChangeRefund(refundObj, processEngine);
			StringBuilder strs = new StringBuilder();
			strs.append("退费ID：");
			strs.append(refund.get("orderDetailId"));
			strs.append("，");
			strs.append("退费参数：");
			strs.append(refund.toString());
			strs.append("，");
			strs.append("退费结果参数：");
			strs.append(refundObj);
			LogOperateUtil.getInstance().LogOperate("退费", strs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
			map.put("error", false);
			map.put("data", refundObj);
		} catch (Exception e) {
			StringBuilder strs = new StringBuilder();
			strs.append("退费参数：");
			strs.append(refund);
			strs.append("，");
			strs.append("退费结果参数：");
			strs.append(map);
			strs.append("退费失败原因：");
			strs.append(e);
			LogOperateUtil.getInstance().LogOperate("退费", strs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
			log.error("error found,see:", e);
			map.put("error", true);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/frozen", headers = "Accept=application/json")
	public  Map frozenClass(@RequestBody Map<String, Object> frozen,HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (CollectionUtils.isEmpty(frozen)) {
				throw new Exception("非法访问！");
			}

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || !"4".endsWith(orgModel.getType())) {
				throw new Exception("请选择校区!");
			}

			Map<String, Object> frozenObj = new HashMap<String, Object>();

			if (frozen.get("orderDetailId") == null) {
				throw new Exception("非法访问！");
			}
			if (frozen.get("premiumType") == null) {
				throw new Exception("冻结公式不允许为空！");
			}
			if (frozen.get("courseCnt") == null) {
				throw new Exception("非法访问！");
			}
			if (frozen.get("courseTimes") == null) {
				throw new Exception("冻结课时不能为空！");
			}
			if (frozen.get("remark") == null) {
				throw new Exception("备注不能为空！");
			}
			if (frozen.get("premiumDeductionAmount") == null) {
				throw new Exception("补扣金额不能空！");
			}
			Account account = WebContextUtils.genUser(request);
			frozenObj.put("user_id", account.getId());
			frozenObj.put("businessType", frozen.get("businessType"));
			frozenObj.put("user_name", account.getEmployeeName());
			frozenObj.put("premiumType", frozen.get("premiumType"));
			frozenObj.put("order_detail_id", frozen.get("orderDetailId"));
			frozenObj.put("course_cnt", frozen.get("courseCnt"));
			frozenObj.put("course_times", frozen.get("courseTimes"));
			frozenObj.put("branch_id", orgModel.getId());
			frozenObj.put("cityId", orgModel.getCityId());
			frozenObj.put("order_id", frozen.get("orderId"));
			
			frozenObj.put("p_remark", frozen.get("remark")==null?"":frozen.get("remark"));
			frozenObj.put("p_premium_deduction_amount",
					frozen.get("premiumDeductionAmount"));
			frozenObj.put("premium_result",
					frozen.get("premium_result"));
			frozenObj.put("premium_formula",
					frozen.get("premium_formula"));
			frozenObj.put("premium_detail",
					frozen.get("premium_detail"));
			
			frozenObj.put("premium_result_val", frozen.get("premiumResultVal"));
			frozenObj.put("is_approve",frozen.get("is_approve") != null ? frozen.get("is_approve")+ "" : "0");
			orderChangeService.orderChangeFrozen(frozenObj, processEngine);
			StringBuilder strs = new StringBuilder();
			strs.append("冻结ID：");
			strs.append(frozen.get("orderDetailId"));
			strs.append("，");
			strs.append("冻结参数：");
			strs.append(frozen.toString());
			strs.append("，");
			strs.append("冻结结果参数：");
			strs.append(frozenObj);
			LogOperateUtil.getInstance().LogOperate("冻结", strs.toString(),LogOperateUtil.getInstance().genUserInfo(request), "成功");
			map.put("error", false);
			map.put("data", frozenObj);
		} catch (Exception e) {
			StringBuilder strs = new StringBuilder();
			strs.append("冻结参数：");
			strs.append(frozen);
			strs.append("，");
			strs.append("冻结结果参数：");
			strs.append(map);
			strs.append("冻结失败原因：");
			strs.append(e);
			LogOperateUtil.getInstance().LogOperate("冻结", strs.toString(),LogOperateUtil.getInstance().genUserInfo(request), "失败");
			log.error("error found,see:", e);
			map.put("error", true);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 查询退费单据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/refund" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryRefund(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			queryParam.put("order_no", request.getParameter("queryOrderString")); //匹配 订单编码或订单ID
			queryParam.put("encoding", request.getParameter("refundEncoding")); //匹配 退费单据编码
			queryParam.put("studentInfo", request.getParameter("studentInfo")); //匹配 退费单据编码
			queryParam.put("change_type", "1"); //订单类型
			Page<TOrderChange> page = orderChangeService.queryRefundForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	/**
	 * 查询冻结单据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/frozen" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryFrozen(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			queryParam.put("order_no", request.getParameter("queryOrderString")); //匹配 订单编码或订单ID
			queryParam.put("encoding", request.getParameter("frozenEncoding")); //匹配 冻结单据编码
			queryParam.put("change_type", "5"); //订单类型 冻结退费
			Page<TOrderChange> page = orderChangeService.queryRefundForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	/**
	 * 查询退费单据的详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/refundDetail" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryRefundDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			queryParam.put("change_id", request.getParameter("change_id")); 
			Page<TCOrderCourse> page = orderChangeService.queryRefundDetailForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	/**
	 * 查询冻结单据的详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/frozenDetail" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> queryfrozenDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			queryParam.put("change_id", request.getParameter("change_id")); 
			Page<TCOrderCourse> page = orderChangeService.queryRefundDetailForPage(queryParam);
			super.setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	/**
	 * 退费作废
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/refund" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> deleteRefund(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		Long changeId = genLongParameter("changeId", request);
		try {
			String remark = request.getParameter("remark");
			Account account = WebContextUtils.genUser(request);
			Long loginUserId = account.getId();
			if (StringUtil.isEmpty(changeId) || StringUtil.isEmpty(loginUserId)) {
				throw new Exception("参数不能为空！");
			}
			orderChangeService.cancelRefund(remark, changeId, loginUserId);
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("退费作废ID：");
			detailInfoString.append(changeId);
			detailInfoString.append("，");
			detailInfoString.append("备注：");
			detailInfoString.append(remark);
			LogOperateUtil.getInstance().LogOperate("退费作废",
					detailInfoString.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "作废失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("退费作废ID：");
			detailInfoString.append(changeId);
			detailInfoString.append("，");
			detailInfoString.append("失败信息：");
			detailInfoString.append(e);
			LogOperateUtil.getInstance().LogOperate("退费作废",
					detailInfoString.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}
	
	/**
	 * 冻结作废
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/frozen" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> deleteFrozen(HttpServletRequest request, HttpServletRequest response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		Long changeId = genLongParameter("change_id", request);
		try {
			String remark = request.getParameter("remark");
			Account account = WebContextUtils.genUser(request);
			Long loginUserId = account.getId();
			if (StringUtil.isEmpty(changeId) || StringUtil.isEmpty(loginUserId)) {
				throw new Exception("参数不能为空！");
			}
			orderChangeService.cancelFrozen(remark, changeId, loginUserId);
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("冻结作废ID：");
			detailInfoString.append(changeId);
			detailInfoString.append("，");
			detailInfoString.append("备注：");
			detailInfoString.append(remark);
			LogOperateUtil.getInstance().LogOperate("冻结作废",detailInfoString.toString(),LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "作废失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("冻结作废ID：");
			detailInfoString.append(changeId);
			detailInfoString.append("，");
			detailInfoString.append("失败信息：");
			detailInfoString.append(e);
			LogOperateUtil.getInstance().LogOperate("冻结作废",
					detailInfoString.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}
	
	/**
	 * 退费-标记已取款
	 * @param changeId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/refund" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> drawRefund(@RequestBody Long changeId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Account account = WebContextUtils.genUser(request);
			orderChangeService.drawRefund(changeId, account.getId());
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("退费单据ID：");
			detailInfoString.append(changeId);
			LogOperateUtil.getInstance().LogOperate("退费-标记已取款",
					detailInfoString.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "取现失败: "+ e.getMessage());
			log.error("error found ,see:", e);
			
			StringBuilder detailInfoString = new StringBuilder();
			detailInfoString.append("退费单据ID：");
			detailInfoString.append(changeId);
			LogOperateUtil.getInstance().LogOperate("退费-标记已取款",
					detailInfoString.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}
	
	/**
	 * 验证课程是否可进行批改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/changeCheck" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> changeCheck(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			Long orderCourseId = genLongParameter("orderCourseId", request);
			this.tcOrderCourseService.checkOrderChange(orderCourseId);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}
	
}
