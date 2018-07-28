package com.edu.erp.student.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.erp.util.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.model.TAccountDynamic;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.DetailBusinessInfoFormat;
import com.edu.erp.util.ModelDataUtils;
import com.edu.erp.util.WorkflowHelper;
import com.edu.erp.workflow.service.UserTaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

/**
 * @ClassName: StudentAccountController
 * @Description: 学员账户服务
 *
 */
@Controller
@RequestMapping(value = { "/studentaccount" })
public class StudentAccountController extends BaseController {
	private static final Logger log = Logger
			.getLogger(StudentAccountController.class);

	@Resource(name = "studentAccountService")
	private StudentAccountService studentAccountService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "processEngine")
	private ProcessEngine processEngine;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	// ----add by zenglw start
	@ResponseBody
	@RequestMapping(value = { "/all" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryStudentAccountDynamic(
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Page<TAccountDynamic> page = new Page<TAccountDynamic>();
			Map<String, Object> param = new HashMap<String, Object>();

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getCityId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择查询校区或团队或地区！");
				return resultMap;
			}
			Long city_id = orgModel.getCityId();
			param.put("student_name",
					request.getParameter("queryStudentString"));//查询学生字符串，可以为姓名，编码，ID
			param.put("encoding", request.getParameter("queryOrderString"));
			param.put("dynamic_type", genLongParameter("dynamicType", request));
			param.put("city_id", city_id);

			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);
			if (currentPage == null) {
				currentPage = 1;
			}
			if (pageSize == null) {
				pageSize = 20;
			}
			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);
			page = studentAccountService.pageQueryStudentAccountDynamic(param);
			PageInfo<TAccountDynamic> pageData = new PageInfo<TAccountDynamic>(
					page);
			resultMap.put("error", false);
			resultMap.put("data", pageData.getList());
			resultMap.put("total", pageData.getTotal());
			resultMap.put("totalPage", pageData.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 作废账户单据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.DELETE)
	public Map<String, Object> invalidAccountDynamic(HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		JSONObject json = new JSONObject();
		Account account = WebContextUtils.genUser(request);
		Long dynamicId = genLongParameter("dynamicId", request);
		Long operator = account.getId();
		String remark = request.getParameter("remark");
		json.put("dynamicId", dynamicId);
		json.put("operator", operator);
		json.put("remark", remark);
		try {
			resultMap = studentAccountService
					.invalidAccountDynamic(dynamicId, operator, remark);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("访问参数：");
			detailInfoStr.append(json.toString());
			detailInfoStr.append("，");
			if ("true".equals(resultMap.get("error"))) {
				resultMap.put("error", true);
			    detailInfoStr.append("错误信息：");
	            detailInfoStr.append(resultMap.get("message"));
	            LogOperateUtil.getInstance().LogOperate("账户操作作废",
	                    detailInfoStr.toString(),
	                    LogOperateUtil.getInstance().genUserInfo(request), "失败");
			} else {
				resultMap.put("error", false);
    			detailInfoStr.append("处理结果：");
    			detailInfoStr.append(resultMap);
    			LogOperateUtil.getInstance().LogOperate("账户操作作废",
    					detailInfoStr.toString(),
    					LogOperateUtil.getInstance().genUserInfo(request), "成功");
			}
		} catch (Exception e) {
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("访问参数：");
			detailInfoStr.append(json);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("账户操作作废",
					detailInfoStr.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/queryUpdateBaseInfo" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> updateDialog(HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getCityId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择查询校区或团队！");
				return resultMap;
			}
			Long city_id = orgModel.getCityId();
			// 获取选中充值单ID
			String id = request.getParameter("id");
			// 根据ID拿到充值的转账卡号和所选POS机
			Map<String, Object> cardNum = studentAccountService.getCardNum(id);
			// 获取POS机列表
			List<Map<String, Object>> POSList = studentAccountService
					.getPOSList(city_id + "");
			resultMap.put("error", false);
			resultMap.put("cardNum", cardNum);
			resultMap.put("POSList", POSList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 修改POS机和卡号
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updateCardNum(
			@RequestBody TAccountDynamic tAccountDynamic,
			HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 获取充值记录的ID
			String CZtype = tAccountDynamic.getPay_mode();
			// 获取充值记录的ID
			String dynamicId = tAccountDynamic.getId() + "";
			// 获取公司账户ID
			String accountId = tAccountDynamic.getAccount_id() + "";
			// 获取新转账卡号
			String cardNum = tAccountDynamic.getCard_num();

			if (StringUtils.isEmpty(dynamicId)
					|| StringUtils.isEmpty(accountId)
					|| StringUtils.isEmpty(cardNum)
					| StringUtils.isEmpty(CZtype)) {
				resultMap.put("error", true);
				resultMap.put("message", "参数不正确！");
				return resultMap;
			} else if (!("刷卡".equals(CZtype) || "银行转账".equals(CZtype))) {
				resultMap.put("error", true);
				resultMap.put("message", "该收费方式不支持修改！");
				return resultMap;
			}
			//根据id查询账户变动信息
			BigDecimal pay_mode = (BigDecimal) (studentAccountService.queryStudentAccountDynamic(tAccountDynamic.getId()).get("PAY_MODE"));
			if (!(pay_mode.equals(new BigDecimal(2)) || pay_mode.equals(new BigDecimal(3)))) {
				resultMap.put("error", true);
				resultMap.put("message", "该收费方式不支持修改！");
				return resultMap;
			}
			// 备份原数据至历史表
			// 获取原数据
			Map<String, Object> reChargeInfo = studentAccountService
					.getCardNum(dynamicId);
			Map<String, Object> reChargeInfoHis = new HashMap<String, Object>();
			reChargeInfoHis = reChargeInfo;
			// 当前登录用户ID
			Account account = WebContextUtils.genUser(request);
			Long userId = account.getId();
			reChargeInfoHis.put("creatUser", userId);
			// 储存历史
			Integer num = studentAccountService.addReChargeHis(reChargeInfoHis);
			// 修改数据账号卡号
			// 如果原充值类型为转账，那么不处理POS机的更改
			if (CZtype.contains("转账")) {
				accountId = null;
			}

			num = studentAccountService.updateReCharge(accountId, cardNum,
					dynamicId);
			if (num != 1) {
				resultMap.put("error", true);
				resultMap.put("message", "保存失败");
			} else {
				resultMap.put("error", false);
				String retMsg = "保存成功！";

				if (StringUtils.isEmpty(accountId)) {
					retMsg += "！使用转账的方式充值的单据POS机选项将不会被更改";
				}
				resultMap.put("message", retMsg);
			}
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	// ----add by zenglw end
	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryStudentAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Page<Map<String, Object>> page = new Page<Map<String, Object>>();
			Map<String, Object> param = new HashMap<String, Object>();
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || orgModel.getBuId() == null) {
				resultMap.put(Constants.RespMapKey.ERROR, true);
				resultMap.put(Constants.RespMapKey.MESSAGE, "请选择团队或校区！");
				return resultMap;
			}
			param.put("bu_id", genLongParameter("bu_id", request)==null?orgModel.getBuId():genLongParameter("bu_id", request));
			param.put("student_id", genLongParameter("student_id", request));
			param.put("start_date", request.getParameter("start_date"));
			param.put("end_date", request.getParameter("end_date"));
			param.put("account_type", genLongParameter("account_type", request));
			param.put("change_types", request.getParameter("change_types"));
			// t_account_change 表中的 account_type
			param.put("change_account_type", genLongParameter("change_account_type", request));

			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);

			if (currentPage == null) {
				currentPage = 1;
			}

			if (pageSize == null) {
				pageSize = 20;
			}

			// 获取第1页，10条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);

			page = studentAccountService.page(param);
			PageInfo<Map<String, Object>> pageData = new PageInfo<Map<String, Object>>(
					page);
			resultMap.put(Constants.RespMapKey.ERROR, false);
			resultMap.put(Constants.RespMapKey.DATA,
					ModelDataUtils.lowerMapList(pageData.getList()));
			resultMap.put("total", pageData.getTotal());
			resultMap.put("totalPage", pageData.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = { "/account" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		try {
			Long studentId = genLongParameter("studentId", request);
			Long accountType = genLongParameter("accountType", request);

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				result.put(Constants.RespMapKey.ERROR, true);
				result.put(Constants.RespMapKey.MESSAGE, "请选择团队或校区！");
				return result;
			}

			queryParam.put("studentId", studentId);
			queryParam.put("buId", genLongParameter("bu_id", request)==null?orgModel.getBuId():genLongParameter("bu_id", request));
			queryParam.put("accountType", accountType);
			queryParam.put("userId",null== WebContextUtils.genUser(request)?null :WebContextUtils.genUser(request).getId());
			Map<String, Object> student = studentAccountService
					.queryAccount(queryParam);
			result.put(Constants.RespMapKey.DATA, student);
			result.put(Constants.RespMapKey.ERROR, false);
		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put(Constants.RespMapKey.ERROR, true);
			result.put(Constants.RespMapKey.MESSAGE, "查询失败！" + e.getMessage());
		}
		return result;
	}

	/**
	 * 报班凭证打印
	 * 
	 *            报班单ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/print" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> queryPrint(HttpServletRequest request) throws Exception {
		String printType = request.getParameter("printType");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 报班凭证
			if ("01".equals(printType)) {
				Long orderId = genLongParameter("orderId", request);
				String encoding = request.getParameter("encoding");
				Object data = null;
				if(StringUtils.isNotEmpty(encoding)) {
					data = studentAccountService.queryPrint01(encoding);
				} else {
					data = studentAccountService.queryPrint01(orderId);
				}
				
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 退费凭证
			else if ("02".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint02(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 充值凭证
			else if ("03".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint03(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 取款凭证
			else if ("04".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint04(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 转账凭证
			else if ("05".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint05(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 理赔凭证
			else if ("06".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint06(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			// 转班凭证
			else if ("07".equals(printType)) {
				Long dynamicId = genLongParameter("dynamicId", request);
				Map<String, Object> data = studentAccountService
						.queryPrint07(dynamicId);
				result.put(Constants.RespMapKey.DATA, data);
			}
			result.put(Constants.RespMapKey.ERROR, false);

		} catch (Exception e) {
			log.error("error found,see:", e);
			result.put(Constants.RespMapKey.ERROR, true);
			result.put(Constants.RespMapKey.MESSAGE, e.getMessage());
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/service", headers = "Accept=application/json")
	public @ResponseBody
	Map<String, Object> recharge(@RequestBody Map<String, Object> json,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		if (CollectionUtils.isEmpty(json)) {
			result.put(Constants.RespMapKey.ERROR, true);
			result.put(Constants.RespMapKey.MESSAGE, "参数不正确！");
			return result;
		}
		OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
		if (orgModel == null || !"4".equals(orgModel.getType())) {
			result.put(Constants.RespMapKey.ERROR, true);
			result.put(Constants.RespMapKey.MESSAGE, "请选择校区！");
			return result;
		}

		try {
			Account account = WebContextUtils.genUser(request);
			Map<String, Object> resultData = null;
			if (Constants.AccountOperateType.RECHARGE.equals(json
					.get("accountOperateType"))) {
				resultData = studentAccountService.accountRecharge(json,
						orgModel.getId(), orgModel.getCityId(),
						account.getId(), orgModel.getBuId());
				addLog(request, json, orgModel, "充值", Constants.SUCCESS_TEXT,
						null);
			} else if (Constants.AccountOperateType.TRANSFER.equals(json
					.get("accountOperateType"))) {
				resultData = studentAccountService.accountTransfer(json, orgModel.getId(), account.getId(),
					account.getEmployeeName(), orgModel.getBuId(), processEngine);
				addLog(request, json, orgModel, "转账", Constants.SUCCESS_TEXT,
						null);
			} else if (Constants.AccountOperateType.CLAIM.equals(json
					.get("accountOperateType"))) {
				if (!WorkflowHelper
						.isDeployed(processEngine, "erpv5.DXB_lipei")) {
					result.put(Constants.RespMapKey.ERROR, true);
					result.put(Constants.RespMapKey.MESSAGE,
							"理赔失败，理赔需要审批，联系管理员发布审批流程！");

					addLog(request, json, null, "理赔申请", Constants.FAIL_TEXT,
							"，错误信息：流程尚未发布(erpv5.DXB_lipei)");
					return result;
				}

				resultData = studentAccountService.studentClaim(json,
						orgModel.getId(), orgModel.getCityId(),
						account.getId(), orgModel.getBuId());
				Map<String, Object> queryParam = new HashMap<String, Object>();
				queryParam.put("studentId", json.get("student_id"));
				StudentInfo studentInfo = studentInfoService
						.queryStudentById(queryParam);

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("lp_encoding", resultData.get("lp_encoding"));
				param.put("dynamic_id",
						Integer.parseInt(resultData.get("dynamic_id") + ""));
				param.put("studentId", studentInfo.getId());
				param.put("student_id", studentInfo.getId());
				param.put("student_name", studentInfo.getStudent_name());
				if (!CollectionUtils.isEmpty(resultData)
						&& "false".equals(resultData
								.get(Constants.RespMapKey.ERROR) + "")) {
					param.put("businessDetailInfo", DetailBusinessInfoFormat
							.lipeiString(json, resultData.get("dynamic_id"),
									studentInfo, orgModel));
					param.put("branch_id", orgModel.getId());

					JSONObject jsonMap = JSONObject.fromObject(json);
					Map<String, Object> userApplication = new HashMap<String, Object>();
					userApplication.put("APPLICATION_ID", account.getId());
					StringBuilder application = new StringBuilder("理赔审批：");
					if (null != studentInfo) {
						application.append("学生ID[");
						application.append(studentInfo.getStudent_name() + "("
								+ studentInfo.getId() + ")");
						application.append("]");
					}

					application.append("理赔ID[");
					application.append(param.get("dynamic_id"));
					application.append("]");
					application.append("理赔金额[");
					application.append(jsonMap.get("money"));
					application.append(".00元]");
					application.append("理赔编号：[");
					application.append(resultData.get("lp_encoding"));
					application.append("]");
					userApplication.put("APPLICATION", application.toString());
					userApplication.put("STATUS", 1L);
					userApplication.put("CREATETIME",
							DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
					userApplication.put("REMARK", jsonMap.get("remark"));
					userApplication.put("CURRENT_STATE", "申请已提交");
					userApplication.put("CURRENT_STEP", "申请提交");
					if (null != account) {
						userApplication.put("CURRENT_MAN",
								account.getUserName());
					}
					userApplication.put("UPDATETIME",
							DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
					userApplication.put("WORKURL",
							"#/studentMgr/" + json.get("student_id"));

					userTaskService.insertApplication(userApplication);
					param.put("application_id", userApplication.get("id"));
					param.put("remark", jsonMap.get("remark"));
					param.put("remark_order", jsonMap.get("remark"));

					ProcessInstance processInstance = processEngine
							.getExecutionService().startProcessInstanceByKey(
									"erpv5.DXB_lipei", param);
					processEngine.getExecutionService().createVariables(
							processInstance.getId(), param, true);

					addLog(request, json, null, "理赔申请", Constants.SUCCESS_TEXT,
							"，申请ID：" + processInstance.getId());
				}
			} else if ("withDrawal".equals(json.get("accountOperateType"))) {
				result = studentAccountService.accountDrawing(json,orgModel.getCityId(), orgModel.getBuId(), account.getId(),orgModel.getId());
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("参数对象：");
				detailInfoStr.append(json);
				LogOperateUtil.getInstance()
						.LogOperate(
								"学生取款",
								detailInfoStr.toString(),
								LogOperateUtil.getInstance().genUserInfo(
										request), "成功");
			}
			result.put(Constants.RespMapKey.ERROR, false);
			result.put(Constants.RespMapKey.DATA, resultData);
		} catch (Exception e) {
			addLog(request, json, orgModel, "账户操作", Constants.FAIL_TEXT, null);
			result.put(Constants.RespMapKey.ERROR, true);
			result.put(Constants.RespMapKey.MESSAGE, "接口异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 账户查询操作
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = { "/all" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
//	public @ResponseBody
//	Map<String, Object> queryDynamicAccountInfo(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		Map<String, Object> result = new HashMap<String, Object>();
//		try {
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("encoding", request.getParameter("encoding"));
//			param.put("dynamicType", request.getParameter("dynamicType"));
//			param.put("queryStudentString",
//					request.getParameter("queryStudentString"));
//			param.put("dynamicId", request.getParameter("dynamicId"));
//			studentAccountService.queryDynamicAccountInfo(param);
//			result.put("error", false);
//		} catch (Exception e) {
//			log.error("error found,see:", e);
//			result.put("error", true);
//			result.put("message", e.getMessage());
//		}
//		return result;
//	}

	private void addLog(HttpServletRequest request, Map<String, Object> json,
			OrgModel orgModel, String operation, String result, String extraInfo) {
		StringBuilder logInfo = new StringBuilder();
		logInfo.append(operation + "数据：");
		logInfo.append(json);

		if (StringUtils.isNotEmpty(extraInfo)) {
			logInfo.append(extraInfo);
		}

		if (orgModel != null) {
			logInfo.append("，校区：");
			logInfo.append(orgModel.getText());
			logInfo.append("，校区ID：");
			logInfo.append(orgModel.getId());
		}
		LogOperateUtil.getInstance().LogOperate(operation, logInfo.toString(),
				LogOperateUtil.getInstance().genUserInfo(request), result);
	}

}
