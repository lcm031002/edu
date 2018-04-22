package com.edu.erp.orders.controller;

import com.edu.common.constants.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.model.*;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.log_operate.service.LogOperateService;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.student.service.StudentAttendanceService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.student.service.TCourseListeningService;
import com.edu.erp.util.BaseController;
import com.edu.erp.util.ReportWriteExcelHandler;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;

/**
 * @ClassName: OrderInfoController
 * @Description: 订单信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月3日 下午6:35:51
 * 
 */
@Controller
@RequestMapping(value = { "/ordermanager" })
public class OrderInfoController extends BaseController {
	private static final Logger log = Logger
			.getLogger(OrderInfoController.class);

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "tCourseListeningService")
	private TCourseListeningService tCourseListeningService;

	@Resource(name = "studentAttendanceService")
	private StudentAttendanceService attendanceService;

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "orderService")
	private OrderService orderService;

	@Resource(name = "privilegeRuleService")
	private PrivilegeRuleService privilegeRuleService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;
	
	@Resource(name = "logOperateService")
	private LogOperateService logOperateService;

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> saveOrder(
			@RequestBody TabOrderInfo tabOrderInfo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to save order.");
		}
		try {
			TabOrderInfo resultOrderInfo = null;
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

//			boolean isStuActive = this.studentInfoService.isStudentActive(tabOrderInfo.getStudent_id());
//			if (!isStuActive) {
//				result.put("error", true);
//				result.put("message", "非活跃学员，不能报班！");
//				return result;
//			}

			Account account = WebContextUtils.genUser(request);
			tabOrderInfo.setBranch_id(orgModel.getId());
			tabOrderInfo.setBu_id(orgModel.getBuId());
			tabOrderInfo.setCity_id(orgModel.getCityId());
			tabOrderInfo.setCreate_user(account.getId());

			if (tabOrderInfo.getId() != null) {
				orderInfoService.updateOrderInfo(tabOrderInfo, account,
						orgModel, processEngine);
			} else {
				orderInfoService.saveOrderInfo(tabOrderInfo, account, orgModel,
						processEngine);
			}

			if (tabOrderInfo.getId() != null) {
				resultOrderInfo = orderInfoService
						.queryTemporaryOrderInfo(tabOrderInfo.getId());
			}
			result.put("error", false);
			result.put("data", resultOrderInfo);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save order.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/pay" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> payOrder(@RequestBody TabOrderInfo tabOrderInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to pay order.");
		}
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}

			Account account = WebContextUtils.genUser(request);
			TabOrderInfo resultOrderInfo=orderInfoService.payOrderInfo(tabOrderInfo, account.getId());
			result.put("error", false);
			result.put("data", tabOrderInfo);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		if (log.isDebugEnabled()) {
			log.debug("end to save order.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryOrderInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long orderId = genLongParameter("order_id", request);
			String orderType = request.getParameter("orderType");
			TabOrderInfo orderInfo = null;
			// 查询暂存订单信息
			if ("temporaryOrder".equals(orderType)) {
				orderInfo = orderInfoService.queryOrderInfo(orderId);
				if (orderInfo.getOrder_status().longValue() == 1) { // 已支付订单
					TOrder tOrder = orderService.queryOrderInfo(orderId);
					result.put("data", tOrder);
				} else {
					orderInfo = orderInfoService.queryTemporaryOrderInfo(orderId);
					result.put("data", orderInfo);
				}
			}
			// 查询正式订单信息
			else {
				TOrder tOrder = orderService.queryOrderInfo(orderId);
				result.put("data", tOrder);
			}

			result.put("error", false);

		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/orderChangeCourseTimes" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryOrderChangeCourseTimeInfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long orderDetailId = genLongParameter("orderDetailId", request);
			List<TOrderCourseTimesLog> orderCourseTimesLog = orderService
					.queryOrderCourseChangeTimesInfo(orderDetailId);
			result.put("data", orderCourseTimesLog);
			result.put("error", false);

		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/orderChangeInfo" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryOrderChangeInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Long orderId = genLongParameter("orderId", request);
			if (orderId == null) {
				throw new Exception("非法访问,订单id不能为空！");
			}
			List<TOrderChange> orderChangeList = orderService
					.queryOrderChangeInfo(orderId);
			result.put("data", orderChangeList);
			result.put("error", false);

		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/surplusCount" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryCourseTimeInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long orderDetailId = genLongParameter("orderDetailId", request);
			List<Map<String, Object>> orderCourseSurplusCount = orderService
					.queryOrderCourseSurplusCount(orderDetailId);
			result.put("data", orderCourseSurplusCount);
			result.put("error", false);

		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/service", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> deleteTemporaryOrder(@RequestParam Long orderId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to delete Temporary Order, id is :" + orderId);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			orderInfoService.deleteTemporaryOrderInfo(orderId, account.getId());
			result.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}

		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/unpay", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> unpay(HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> params = initParamMap(request, true);
			params.put("bu_id", genLongParameter("p_bu_id", request));
			params.put("check_status",genLongParameter("p_check_status", request));
			Page<TabOrderInfo> unpayList = orderInfoService.selectUnpay(params);
			setRespDataForPage(request, unpayList, result);
			result.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/exportUnpayExcel", headers = "Accept=application/json")
    public Map<String, Object> exportUnpayExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	Map<String, Object> queryParam = super.initParamMap(request, false);
        	queryParam.put("bu_id", genLongParameter("p_bu_id", request));
			List<TabOrderInfo> data = orderInfoService.selectUnpay(queryParam);
            if (CollectionUtils.isEmpty(data)) {
                throw new Exception("导出失败：没有导出数据！");
            }

            String rootPath = request.getSession().getServletContext().getRealPath("//");
            // 模板文件名
            String templateFileName = "unpayStudent.xlsx";
            // 临时文件名
            String tempFileName = "欠费订单表_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
            ReportWriteExcelHandler.writeDataToExcel(data, rootPath, templateFileName,
                    tempFileName);
            resultMap.put(Constants.RespMapKey.DATA, tempFileName);
        } catch (Exception e) {
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

	@RequestMapping(method = RequestMethod.POST, value = "/updateOrderInfo", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> updateOrderInfo(@RequestBody TabOrderInfo tabOrderInfo,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			orderInfoService.updateOrderRemark(tabOrderInfo);
			result.put("error", false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
}
