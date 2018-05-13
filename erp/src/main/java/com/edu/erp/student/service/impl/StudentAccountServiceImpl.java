package com.edu.erp.student.service.impl;

import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.message.service.MessageRequirementService;
import com.edu.erp.model.OrganizationInfo;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.util.DetailBusinessInfoFormat;
import com.edu.erp.util.TemplateUtil;
import com.edu.erp.workflow.service.UserTaskService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.constants.Constants;
import com.edu.erp.dao.StudentAccountDao;
import com.edu.erp.dao.StudentInfoDao;
import com.edu.erp.dao.TAccountDynamicDao;
import com.edu.erp.model.TAccount;
import com.edu.erp.model.TAccountChange;
import com.edu.erp.model.TAccountDynamic;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

/***
 * Description：学生账户管理 Service 接口 Author: JunliZhang Date:2014-11-10
 */
@Service("studentAccountService")
public class StudentAccountServiceImpl implements StudentAccountService {

	private static final Logger log = Logger.getLogger(StudentAccountServiceImpl.class);
	
	@Resource(name = "studentAccountDao")
	private StudentAccountDao studentAccountDao;

	@Resource(name = "studentInfoDao")
	private StudentInfoDao studentInfoDao;
	
	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	// --add by zenglw start
	@Resource(name = "tAccountDynamicDao")
	private TAccountDynamicDao tAccountDynamicDao;

	@Resource(name = "organizationService")
	private OrganizationService organizationService;

	@Resource(name = "userTaskService")
	private UserTaskService userTaskService;

	@Resource(name = "messageRequirementService")
	private MessageRequirementService messageRequirementService;

	@Override
	public Page<TAccountDynamic> pageQueryStudentAccountDynamic(
			Map<String, Object> pageParam) throws Exception {
		return tAccountDynamicDao.pageQueryStudentAccountDynamic(pageParam);
	}

	@Override
	public Map<String, Object> queryStudentAccountDynamic(Long dynamicId)
			throws Exception {

		return tAccountDynamicDao.queryInfoByDynamicId(dynamicId);
	}

	@Override
	public Map<String, Object> invalidAccountDynamic(Long dynamicId,
			Long operator, String remark) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		if (dynamicId == null || operator == null) {
			map.put("error", "true");
			map.put("message", "参数不能为空！");
			return map;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("p_dynamic_id", dynamicId);
		paramMap.put("p_remark", StringUtils.isEmpty(remark) ? "作废"
				: remark);
		Map<String, Object> dynamicInfo = tAccountDynamicDao
				.queryInfoByDynamicId(dynamicId);
		int status =  ((BigDecimal)dynamicInfo.get("STATUS")).intValue();

		int dynamic_type =  ((BigDecimal)dynamicInfo.get("DYNAMIC_TYPE")).intValue();
		if (!(status != 4 || dynamic_type == 1 || dynamic_type == 3 || dynamic_type == 4)) {
			map.put("error", "true");
			map.put("message", "该类型的账户变动类型支持修改");
			return map;
		}
		// 根据账户变动 类型 生成 作废 编号
		Long dynamicType = 0L;
		if (dynamic_type==1) {
			dynamicType = 20L;
		} else if (dynamic_type== 3) {
			dynamicType = 21L;
		} else if (dynamic_type== 4) {
			dynamicType = 22L;
		} else {
			map.put("error", "true");
			map.put("message", "dynamic_type 账户变动类型不能为空！");
			return map;
		}
		String encoding = EncodingSequenceUtil.getSequenceNum(dynamicType);
		paramMap.put("p_encoding", encoding);
		paramMap.put("p_input_user", operator);
		tAccountDynamicDao.accountDynamicCancel(paramMap);
		if (!paramMap.get("o_err_code").toString().equals("0")) {
			throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
		}
		return map;
	}

	@Override
	public Integer addReChargeHis(Map<String, Object> His) throws Exception {
		return tAccountDynamicDao.addReChargeHis(His);
	}

	@Override
	public Integer addAccountRecharge(Map<String, Object> accountRecharge) throws Exception {
		return tAccountDynamicDao.addAccountCharge(accountRecharge);
	}

	@Override
	public int updateReCharge(String accountId, String cardNum, String id)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", accountId);
		params.put("cardNum", cardNum);
		params.put("id", id);
		return tAccountDynamicDao.updateReCharge(params);
	}

	// --add by zenglw end

	/***
	 * 学生账户充值
	 * 
	 * @param param
	 *            {student_id:"",product_line :"团队类型（产品线）",
	 *            money:"",pay_mode:"充值类型1现金2刷卡3转账"
	 *            ,stu_card:"",company_card_id:"",remark:"备注"}
	 * @param branchId
	 *            校区ID
	 * @param cityId
	 *            地区Id
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> accountRecharge(Map<String, Object> param,
			Long branchId, Long cityId, Long userId, Long buId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			if (CollectionUtils.isEmpty(param)
					|| param.get("studentId") == null
					|| StringUtil.isEmpty(branchId)
					|| StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				throw new Exception("参数不能为空！");
			}

			Long studentId = NumberUtils.object2Long(param.get("studentId"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_student_id", param.get("studentId"));
			paramMap.put("p_branch_id", branchId);
			paramMap.put("p_bu_id", buId);
			paramMap.put("p_pay_mode", param.get("pay_mode"));
			paramMap.put("p_money", param.get("money"));
			// 获取上限值
			String limit = "99999";
			// 做比较
			Double limitMoney = Double.parseDouble(limit);
			if (limitMoney
					.compareTo(Double.parseDouble(param.get("money") + "")) < 0) {
				throw new Exception("最大单笔充值不能超过" + limit + "元，超过的请分多笔充值！");
			}

			// 查询是否存在账户，如果没有的话，先创建账户
			Map<String, Object> queryAccountMap = new HashMap<String, Object>();
			queryAccountMap.put("studentId", param.get("studentId"));
			queryAccountMap.put("buId", param.get("buId"));
			queryAccountMap.put("accountType", 0);
			Map<String, Object> queryAccount = studentAccountDao
					.queryAccount(queryAccountMap);
			TAccount tAccount = new TAccount();

			if (CollectionUtils.isEmpty(queryAccount)) {
				tAccount.setStudent_id(NumberUtils.object2Long(paramMap.get("p_student_id")));
				tAccount.setBu_id(NumberUtils.object2Long(paramMap.get("p_bu_id")));
				tAccount.setFee_amount(Double.parseDouble( "0"));
				tAccount.setCreater_id(userId);
				studentAccountDao.createAccount(tAccount);
			}else{
				tAccount.setId(NumberUtils.object2Long(queryAccount.get("id")));
				tAccount.setFee_amount( Double.parseDouble(queryAccount.get("fee_amount") + ""));
			}

			TAccountDynamic tAccountDynamic  = new TAccountDynamic();
			tAccountDynamic.setAccount_id(tAccount.getId());
			tAccountDynamic.setDynamic_type(1L);//充值
			tAccountDynamic.setCity_id(cityId);
			tAccountDynamic.setBranch_id(branchId);
			tAccountDynamic.setBu_id(buId);
			tAccountDynamic.setStudent_id(studentId);
			tAccountDynamic.setStatus(3);
			tAccountDynamic.setAccount_id(tAccount.getId());
			tAccountDynamic.setMoney(Double.parseDouble(param.get("money") + ""));
			tAccountDynamic.setPay_flag(1L);
			tAccountDynamic.setPay_mode(param.get("pay_mode") + "");
			tAccountDynamic.setCreate_user(userId);
			tAccountDynamic.setEncoding(EncodingSequenceUtil.getSequenceNum((long) 1));

			studentAccountDao.saveAccountDynamic(tAccountDynamic);

    		TAccountChange tAccountChange = new TAccountChange();
			tAccountChange.setAccount_id(tAccount.getId());
			tAccountChange.setChange_flag(0L);
			tAccountChange.setChange_type(0L);
			tAccountChange.setChange_amount(Double.parseDouble(param.get("money") + ""));
			tAccountChange.setPre_amount(tAccount.getFee_amount());
			tAccountChange.setNext_amount(Double.parseDouble(param.get("money") + "") + tAccount.getFee_amount());
			tAccountChange.setDynamic_id(tAccountDynamic.getId());
			tAccountChange.setPay_mode(NumberUtils.object2Long(param.get("pay_mode")));
			studentAccountDao.saveAccountChange(tAccountChange);

			if(NumberUtils.object2Long(param.get("pay_mode")) != 1){
				HashMap<String,Object> paramAccountRechargeMap=new HashMap<String,Object>();
				paramAccountRechargeMap.put("cardNum",param.get("stu_card"));
				paramAccountRechargeMap.put("companyAccount",param.get("company_card_id"));
				paramAccountRechargeMap.put("dynamicId",tAccountDynamic.getId());

				addAccountRecharge(paramAccountRechargeMap);
			}

			HashMap<String,Object> paramAccountMap=new HashMap<String,Object>();
			paramAccountMap.put("accountId",tAccount.getId());
			paramAccountMap.put("amount",Double.parseDouble(param.get("money") + "") + tAccount.getFee_amount());
			studentAccountDao.updateFeeAccount(paramAccountMap);

			// 返回充值记录Id
			map.put("dynamic_id", paramMap.get("o_dynamic_id"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常");
			throw new Exception(e);
		}
	}

	/***
	 * 学生开户
	 * 
	 * @param studentId
	 *            学生Id
	 * @param buId
	 *            团队Id
	 * @param userId
	 *            操作人Id
	 * @return
	 * @throws
	 */
	@Override
	public Map<String, Object> createAccount(Long studentId, Long buId,
			Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {

			if (StringUtil.isEmpty(studentId) || StringUtil.isEmpty(buId)
					|| StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			TAccount tAccount = new TAccount();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			tAccount.setStudent_id(studentId);
			tAccount.setBu_id(buId);
			tAccount.setCreater_id(userId);
			paramMap.put("creater_id", userId);
			studentAccountDao.createAccount(tAccount);

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常：" + e.getMessage());
			return map;
		}
	}

	/***
	 * 学生账户转账
	 * 
	 * @param paramMap
	 * @param branchId 当前校区
	 * @param userId 操作人登录号
	 * @param employeeName 操作人
	 * @param buId 当前团队
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> accountTransfer(Map<String, Object> paramMap, Long branchId, Long userId,
		String employeeName, Long buId, ProcessEngine processEngine) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);

		try {
			if (CollectionUtils.isEmpty(paramMap)
					|| paramMap.get("p_input_student_id") == null
					|| StringUtil.isEmpty(branchId)
					|| StringUtil.isEmpty(userId)
					|| paramMap.get("p_transfer_money") == null)	{
				throw new Exception("参数不能为空！");
			}

			Long inputBuId = NumberUtils.object2Long(paramMap.get("p_input_bu_id"));
			Long outputBuId = NumberUtils.object2Long(paramMap.get("p_output_bu_id"));
			Long inputBranchId = NumberUtils.object2Long(paramMap.get("p_input_branch_id"));
			Long outputBranchId = NumberUtils.object2Long(paramMap.get("p_output_branch_id"));
			Long inStudentId = NumberUtils.object2Long(paramMap.get("p_input_student_id"));
			Long outStudentId = NumberUtils.object2Long(paramMap.get("p_output_student_id"));

			// 团队内互转
			if ((inputBuId == null && outputBuId == null) || (outputBuId.longValue() == inputBuId.longValue())) {
				inputBuId = outputBuId = buId;
				inputBranchId = outputBranchId = branchId;
				paramMap.put("p_input_bu_id", buId);
				paramMap.put("p_output_bu_id", buId);
				paramMap.put("p_input_branch_id", branchId);
				paramMap.put("p_output_branch_id", branchId);
			}

			paramMap.put("p_input_user", userId);
			paramMap.put("p_approve_user", userId);
			paramMap.put("p_confirm_user", userId);
			paramMap.put("p_encoding", EncodingSequenceUtil
					.getSequenceNum(Constants.EncodingPrefixSeq.ZZDJ_PREFIX));
			//studentAccountDao.accountTransfer(paramMap);

			// 查询是否存在账户，如果没有的话，先创建账户
			Map<String, Object> queryAccountMap = new HashMap<String, Object>();
			queryAccountMap.put("studentId", outStudentId);
			queryAccountMap.put("buId", inputBuId);
			queryAccountMap.put("accountType", 0);
			Map<String, Object> queryOutAccount = studentAccountDao
					.queryAccount(queryAccountMap);

			TAccountDynamic outTAccountDynamic  = new TAccountDynamic();
			outTAccountDynamic.setAccount_id(NumberUtils.object2Long(queryOutAccount.get("id")));
			outTAccountDynamic.setDynamic_type(2L);//转账
			outTAccountDynamic.setBranch_id(inputBranchId);
			outTAccountDynamic.setBu_id(inputBuId);
			outTAccountDynamic.setStudent_id(outStudentId);
			outTAccountDynamic.setStatus(3);
			outTAccountDynamic.setPay_flag(0L);
			outTAccountDynamic.setPay_mode("4");
			outTAccountDynamic.setMoney(Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			outTAccountDynamic.setCreate_user(userId);
			outTAccountDynamic.setEncoding(EncodingSequenceUtil.getSequenceNum((long) 7));

			studentAccountDao.saveAccountDynamic(outTAccountDynamic);

			TAccountChange outTAccountChange = new TAccountChange();
			outTAccountChange.setAccount_id(NumberUtils.object2Long(queryOutAccount.get("id")));
			outTAccountChange.setChange_flag(1L);
			outTAccountChange.setChange_type(5L);
			outTAccountChange.setChange_amount(Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			outTAccountChange.setPre_amount(Double.parseDouble(queryOutAccount.get("fee_amount") + ""));
			outTAccountChange.setNext_amount(Double.parseDouble(queryOutAccount.get("fee_amount") + "") - Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			outTAccountChange.setDynamic_id(outTAccountDynamic.getId());
			outTAccountChange.setPay_mode(1L);//内部转账
			outTAccountChange.setAccount_type(1L);

			studentAccountDao.saveAccountChange(outTAccountChange);

			Map<String, Object> queryInAccountMap = new HashMap<String, Object>();
			queryInAccountMap.put("studentId", inStudentId);
			queryInAccountMap.put("buId", inputBuId);
			queryInAccountMap.put("accountType", 0);
			Map<String, Object> queryInAccount = studentAccountDao
					.queryAccount(queryInAccountMap);

			Map<String, Object> transferMap = new HashMap<String, Object>();
			transferMap.put("student_id", inStudentId);
			transferMap.put("account_id", NumberUtils.object2Long(queryInAccount.get("id")));
			transferMap.put("money", Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			transferMap.put("dynamic_id", outTAccountDynamic.getId());

			studentAccountDao.saveTransferInput(transferMap);

			TAccountChange inTAccountChange = new TAccountChange();
			inTAccountChange.setAccount_id(NumberUtils.object2Long(queryInAccount.get("id")));
			inTAccountChange.setChange_flag(0L);
			inTAccountChange.setChange_type(5L);
			inTAccountChange.setChange_amount(Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			inTAccountChange.setPre_amount(Double.parseDouble(queryInAccount.get("fee_amount") + ""));
			inTAccountChange.setNext_amount(Double.parseDouble(queryInAccount.get("fee_amount") + "") + Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			inTAccountChange.setDynamic_id(outTAccountDynamic.getId());
			inTAccountChange.setPay_mode(1L);//内部转账
			inTAccountChange.setAccount_type(1L);

			studentAccountDao.saveAccountChange(inTAccountChange);

			HashMap<String,Object> paramOutAccountMap=new HashMap<String,Object>();
			paramOutAccountMap.put("accountId",NumberUtils.object2Long(queryOutAccount.get("id")));
			paramOutAccountMap.put("amount",Double.parseDouble(queryOutAccount.get("fee_amount") + "") + Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			studentAccountDao.updateFeeAccount(paramOutAccountMap);

			HashMap<String,Object> paramInAccountMap=new HashMap<String,Object>();
			paramInAccountMap.put("accountId",NumberUtils.object2Long(queryInAccount.get("id")));
			paramInAccountMap.put("amount",Double.parseDouble(queryInAccount.get("fee_amount") + "") + Double.parseDouble(paramMap.get("p_transfer_money") + ""));
			studentAccountDao.updateFeeAccount(paramInAccountMap);

			// 返回转账记录Id
			Long dynamicId = NumberUtils.object2Long(paramMap.get("o_dynamic_id"));
			resultMap.put("dynamic_id", dynamicId);

		} catch (Exception e) {
			throw new Exception("转账失败:" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 触发转账工作流，走审批流程
	 * @param paramMap
	 * @param dynamicId
	 * @param userId
	 * @param employeeName
	 * @param processEngine
	 * @throws Exception
	 */
	private void invokeAccountTransferWorkflow(Map<String, Object> paramMap, Long dynamicId, Long userId,
			String employeeName, ProcessEngine processEngine) throws Exception {
		Map<String, Object> userApplication = new HashMap<String, Object>();
		userApplication.put("APPLICATION_ID", userId);
		String workFlowName = "学员跨团队账户互转";
		StringBuilder application = new StringBuilder(workFlowName + "：");
		application.append("学生ID[");
		paramMap.put("studentId", paramMap.get("p_output_student_id"));
		StudentInfo outStudentInfo = studentInfoDao.queryStudentById(paramMap);
		Long outStudentId = outStudentInfo.getId();
		application.append(outStudentInfo.getStudent_name() + "" + outStudentId);
		application.append(workFlowName);
		application.append("]");

		userApplication.put("APPLICATION", application.toString());
		userApplication.put("STATUS", 1L);
		userApplication.put("CREATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		userApplication.put("CURRENT_STATE", "申请已提交");
		userApplication.put("CURRENT_STEP", "申请提交");
		userApplication.put("BUSI_ID", outStudentId);
		userApplication.put("CURRENT_MAN", employeeName);
		userApplication.put("UPDATETIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		userApplication.put("WORKURL", "#/personal-attend/" + outStudentId);
		userTaskService.insertApplication(userApplication);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", userId);
		param.put("branch_id", paramMap.get("p_output_branch_id"));
		param.put("application_id", userApplication.get("id"));
		param.put("student_id", outStudentId);
		param.put("student_name", outStudentInfo.getStudent_name() + workFlowName);
		param.put("student_encoding", outStudentInfo.getEncoding());
		param.put("order_encoding", param.get("order_encoding"));

		param.put("dynamic_id", dynamicId);
		param.put("out_student_name", outStudentInfo.getStudent_name() + "(" + outStudentId + ")");

		paramMap.put("studentId", paramMap.get("p_input_student_id"));
		StudentInfo inStudentInfo = studentInfoDao.queryStudentById(paramMap);
		param.put("in_student_name", inStudentInfo.getStudent_name());

		param.put("in_bu_name", paramMap.get("in_bu_name"));
		param.put("transfer_money", paramMap.get("p_transfer_money"));
		Integer accountType = NumberUtils.object2Integer(paramMap.get("p_input_account_type"));
		param.put("account_type", accountType == 3 ? "退费账户" : (accountType == 2 ? "冻结账户" : "储值账户"));
		param.put("branch_name", paramMap.get("output_branch_name"));

		param.put("businessDetailInfo", DetailBusinessInfoFormat.studentAccountTransfer(param));
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("erpv5.DXB_student_account_transfer",
			param);
		processEngine.getExecutionService().createVariables(pi.getId(), param, true);
	}

	/***
	 * 学生账户取款
	 * 
	 * @param json
	 *            {student_id:"",money:"",pay_mode:"1现金 3转账"，student_card:"",
	 *            accountType:"", remark:""}
	 * @param buId
	 *            团队Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> accountDrawing(Map<String, Object> json,Long cityId, Long buId,
			Long userId, Long branchId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (StringUtil.isEmpty(json) || StringUtil.isEmpty(buId)
					|| StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			Assert.notNull( jsonObj.getDouble("money"));
			Assert.notNull( jsonObj.getLong("student_id"));
			Assert.notNull( jsonObj.getLong("pay_mode"));
			paramMap.put("p_bu_id",  jsonObj.get("bu_id")==null?buId: jsonObj.getLong("bu_id"));
			paramMap.put("p_branch_id", branchId);
			paramMap.put("p_student_id", jsonObj.getLong("student_id"));
			paramMap.put("p_money",jsonObj.getDouble("money"));
			paramMap.put("p_accountType", jsonObj.getInt("accountType"));
			if (jsonObj.containsKey("moneyfee")) {
				paramMap.put("p_money_fee", jsonObj.getDouble("moneyfee"));
			} else {
				paramMap.put("p_money_fee", 0.00);
			}
			paramMap.put("p_change_no", jsonObj.getString("encoding"));
			
			paramMap.put("p_pay_mode", jsonObj.getLong("pay_mode"));
			paramMap.put("p_input_user", userId);
			paramMap.put("p_student_card", null);
			if("3".equals(paramMap.get("p_pay_mode").toString())){
				paramMap.put("p_student_card", jsonObj.getInt("student_card"));
			}
			paramMap.put("p_company_card", null);
			paramMap.put("p_input_user", userId);
			paramMap.put("p_remark", StringUtil.isEmpty(jsonObj.get("remark")) ? ""
					: jsonObj.get("remark"));
			paramMap.put("p_encoding",
					EncodingSequenceUtil.getSequenceNum((long) 5));
			//studentAccountDao.accountDrawing(paramMap); 取现存储过程

			// 查询是否存在账户，如果没有的话，先创建账户
			Map<String, Object> queryAccountMap = new HashMap<String, Object>();
			queryAccountMap.put("studentId", paramMap.get("p_student_id"));
			queryAccountMap.put("buId", paramMap.get("p_bu_id"));
			queryAccountMap.put("accountType", 0);
			Map<String, Object> queryAccount = studentAccountDao
					.queryAccount(queryAccountMap);

			if (CollectionUtils.isEmpty(queryAccount)) {
				throw new Exception("学生未开账户！" );
			}

			if (Double.parseDouble(queryAccount.get("fee_amount") + "") - Double.parseDouble(paramMap.get("p_money") + "") < 0 ) {
				throw new Exception("储值账户余额不足！" );
			}

			TAccountDynamic tAccountDynamic  = new TAccountDynamic();
			tAccountDynamic.setAccount_id(Long.parseLong(queryAccount.get("id") + ""));
			tAccountDynamic.setDynamic_type(4L);//取现
			tAccountDynamic.setCity_id(cityId);
			tAccountDynamic.setBranch_id(branchId);
			tAccountDynamic.setBu_id(buId);
			tAccountDynamic.setStudent_id(Long.parseLong(paramMap.get("p_student_id") + ""));
			tAccountDynamic.setStatus(3);
			tAccountDynamic.setMoney(Double.parseDouble(paramMap.get("p_money") + ""));
			tAccountDynamic.setPay_flag(2L);//付费
			tAccountDynamic.setPay_mode(paramMap.get("p_pay_mode")+"");
			tAccountDynamic.setCreate_user(userId);
			tAccountDynamic.setEncoding(EncodingSequenceUtil.getSequenceNum((long) 1));

			studentAccountDao.saveAccountDynamic(tAccountDynamic);

			//更新退费单据为已取款

			//转账
			if(Long.parseLong(paramMap.get("p_pay_mode") +"") != 1){
				HashMap<String,Object> paramAccountRechargeMap=new HashMap<String,Object>();
				paramAccountRechargeMap.put("cardNum",paramMap.get("p_student_card"));
				paramAccountRechargeMap.put("companyAccount",paramMap.get("p_company_card"));
				paramAccountRechargeMap.put("dynamicId",tAccountDynamic.getId());

				addAccountRecharge(paramAccountRechargeMap);
			}

			TAccountChange tAccountChange = new TAccountChange();
			tAccountChange.setAccount_id(Long.parseLong(queryAccount.get("id") + ""));
			tAccountChange.setChange_flag(1L);//0:存入  1:取出
			tAccountChange.setChange_type(3L);//变更类型:0客户充值,1订单收费取出,2订单退费存入,3客户取出
			tAccountChange.setChange_amount(Double.parseDouble(paramMap.get("p_money") + ""));
			tAccountChange.setPre_amount(Double.parseDouble(queryAccount.get("fee_amount") + ""));
			tAccountChange.setNext_amount(Double.parseDouble(queryAccount.get("fee_amount") + "") - Double.parseDouble(paramMap.get("p_money") + ""));
			tAccountChange.setDynamic_id(tAccountDynamic.getId());
			tAccountChange.setPay_mode(NumberUtils.object2Long(paramMap.get("p_pay_mode")));
			studentAccountDao.saveAccountChange(tAccountChange);

			HashMap<String,Object> paramAccountMap=new HashMap<String,Object>();
			paramAccountMap.put("accountId",queryAccount.get("id"));
			paramAccountMap.put("amount",Double.parseDouble(queryAccount.get("fee_amount") + "") - Double.parseDouble(paramMap.get("p_money") + ""));
			studentAccountDao.updateFeeAccount(paramAccountMap);

			// 返回转账记录Id
			map.put("dynamic_id", paramMap.get("o_dynamic_id"));
			return map;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception(e.getMessage(),e);
		}
	}

	/***
	 * 学生理赔申请
	 * 
	 * @param json
	 *            {product_line:"团队（产品线）",money:"",remark:"备注"}
	 * @param branchId
	 *            校区ID
	 * @param cityId
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> studentClaim(Map<String, Object> json,
			Long branchId, Long cityId, Long userId, Long buId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (CollectionUtils.isEmpty(json) || json.get("student_id") == null
					|| branchId == null || userId == null || buId == null) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_bu_id", buId);
			paramMap.put("p_student_id", json.get("student_id"));
			paramMap.put("p_money", jsonObj.get("money"));
			paramMap.put("p_branch_id", branchId);
			paramMap.put("p_input_user", userId);
			paramMap.put("p_approve_user", userId);
			paramMap.put("p_confirm_user", userId);
			paramMap.put("p_remark", jsonObj.get("remark") == null ? ""
					: jsonObj.get("remark"));
			paramMap.put("o_dynamic_id", null);
			paramMap.put("p_encoding",
					EncodingSequenceUtil.getSequenceNum((long) 6));
			studentAccountDao.studentClaim(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			// 返回理赔申请记录Id
			map.put("dynamic_id", paramMap.get("o_dynamic_id"));
			map.put("lp_encoding", paramMap.get("p_encoding"));
			return map;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/***
	 * 学生理赔审批
	 * 
	 * @param json
	 *            {dynamic_id:"理赔申请ID",status:"审批结果"}
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> studentClaimVerify(String json, Long userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (StringUtil.isEmpty(json) || StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_dynamic_id", jsonObj.get("dynamic_id"));
			paramMap.put("p_status", jsonObj.get("status"));
			paramMap.put("p_approve_user", userId);
			paramMap.put("p_confirm_user", userId);

			studentAccountDao.studentClaimVerify(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常");
			return map;
		}
	}

	/***
	 * 一元转校
	 * 
	 * @param json
	 *            {student_id:"",input_branch_id:"",trans_count:""}
	 * @param userId
	 *            操作人
	 * @param branchId
	 *            操作校区
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> unitTransfer(String json, Long userId,
			Long branchId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (StringUtil.isEmpty(json) || StringUtil.isEmpty(userId)
					|| StringUtil.isEmpty(branchId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_student_id", jsonObj.getLong("student_id"));
			paramMap.put("p_output_branch_id", branchId);
			paramMap.put("p_input_branch_id",
					jsonObj.getLong("input_branch_id"));
			paramMap.put("p_trans_count", jsonObj.getLong("trans_count"));
			paramMap.put("p_input_user", userId);
			paramMap.put("p_encoding",
					EncodingSequenceUtil.getSequenceNum((long) 25));

			studentAccountDao.unitTransfer(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常");
			return map;
		}
	}

	/***
	 * 一元结转
	 * 
	 * @param json
	 *            {student_id:"",branch_id:"",count:"",remark:""}
	 * @param userId
	 *            操作用户ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> unitSettleAccounts(String json, Long userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (StringUtil.isEmpty(json) || StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_student_id", jsonObj.getLong("student_id"));
			paramMap.put("p_branch_id", jsonObj.getLong("branch_id"));
			paramMap.put("p_remark", "一元账户直接结转");
			paramMap.put("p_count", jsonObj.getLong("count"));
			paramMap.put("p_input_user", userId);
			paramMap.put("p_encoding",
					EncodingSequenceUtil.getSequenceNum((long) 26));

			studentAccountDao.unitSettleAccounts(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常");
			return map;
		}
	}

	/*************************** 系统打印 ******************************************************/

	/***
	 * 查询报班 信息
	 * 
	 * @param orderId
	 *            报班单ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint01(Long orderId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);

		// 查询主单信息
		List<Map<String, Object>> orderList = studentAccountDao.queryOrder(map);
		// 查询子单信息
		List<Map<String, Object>> orderDetailList = studentAccountDao
				.queryOrderDetail(map);
		// 查询缴费信息
		List<Map<String, Object>> payDetailList = studentAccountDao
				.queryPayDetail(map);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("orderInfo", StringUtil.isEmpty(orderList) ? ""
				: orderList.get(0));
		returnMap.put("orderDetailList",
				StringUtil.isEmpty(orderDetailList) ? "" : orderDetailList);
		returnMap.put("payDetailList", StringUtil.isEmpty(payDetailList) ? ""
				: payDetailList);

		return returnMap;
	}

	/***
	 * 查询退费信息
	 * 
	 * @param dynamicId
	 *            批改-退费ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint02(Long dynamicId) throws Exception {
		return null;
	}

	/***
	 * 查询充值信息
	 * 
	 * @param dynamicId
	 *            充值单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint03(Long dynamicId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicId", dynamicId);

		// 查询充值信息
		List<Map<String, Object>> rechangeInfo = studentAccountDao
				.queryRechange(map);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rechargeInfo", StringUtil.isEmpty(rechangeInfo) ? ""
				: rechangeInfo.get(0));

		return returnMap;
	}

	/***
	 * 查询取款信息
	 * 
	 * @param dynamicId
	 *            取款单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint04(Long dynamicId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicId", dynamicId);

		// 查询充值信息
		List<Map<String, Object>> withdrawingInfo = studentAccountDao
				.queryWithdrwaingInfo(map);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("withdrawing", StringUtil.isEmpty(withdrawingInfo) ? ""
				: withdrawingInfo.get(0));

		return returnMap;
	}

	/***
	 * 查询转账信息
	 * 
	 * @param dynamicId
	 *            转账单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint05(Long dynamicId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicId", dynamicId);

		// 查询充值信息
		List<Map<String, Object>> transferInfo = studentAccountDao
				.queryTransfer(map);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("transferInfo", StringUtil.isEmpty(transferInfo) ? ""
				: transferInfo.get(0));

		return returnMap;
	}

	/***
	 * 查询理赔信息
	 * 
	 * @param dynamicId
	 *            理赔单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint06(Long dynamicId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dynamicId", dynamicId);

		// 查询充值信息
		List<Map<String, Object>> claimInfo = studentAccountDao
				.queryClaimInfo(map);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("claimInfo", StringUtil.isEmpty(claimInfo) ? ""
				: claimInfo.get(0));

		return returnMap;
	}

	/***
	 * 查询转班信息
	 * 
	 * @param dynamicId
	 *            转班单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryPrint07(Long dynamicId) throws Exception {
		return null;
	}

	/***
	 * 学生充值 理赔 取款 作废
	 * 
	 * @param json
	 *            {"dynamic_id":"动户记录ID","dynamic_type":"动户类型","remark":"备注"}
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> accountDynamicCancel(String json, Long userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", "false");
		map.put("message", "");

		try {
			JSONObject jsonObj = null;

			if (StringUtil.isEmpty(json) || StringUtil.isEmpty(userId)) {
				map.put("error", "true");
				map.put("message", "参数不能为空！");
				return map;
			}
			jsonObj = JSONObject.fromObject(json);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_dynamic_id", jsonObj.get("dynamic_id"));
			paramMap.put("p_remark",
					StringUtil.isEmpty(jsonObj.get("dynamic_id")) ? "作废"
							: jsonObj.getString("dynamic_id"));
			// 根据账户变动 类型 生成 作废 编号
			Long dynamicType = 0L;
			if (jsonObj.getString("dynamic_type").equals("1")) {
				dynamicType = 20L;
			} else if (jsonObj.getString("dynamic_type").equals("3")) {
				dynamicType = 21L;
			} else if (jsonObj.getString("dynamic_type").equals("4")) {
				dynamicType = 22L;
			} else {
				map.put("error", "true");
				map.put("message", "dynamic_type 账户变动类型不能为空！");
				return map;
			}
			String encoding = EncodingSequenceUtil.getSequenceNum(dynamicType);
			paramMap.put("p_encoding", encoding);
			paramMap.put("p_input_user", userId);

			studentAccountDao.accountDynamicCancel(paramMap);

			if (!paramMap.get("o_err_code").toString().equals("0")) {
				throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", "true");
			map.put("message", "接口异常");
			return map;
		}
	}

	@Override
	public Page<Map<String, Object>> page(Map<String, Object> pageParam)
			throws Exception {
		Assert.notNull(pageParam);
		Assert.notNull(pageParam.get("bu_id"));
		Assert.notNull(pageParam.get("student_id"));
		Assert.notNull(pageParam.get("start_date"));
		Assert.notNull(pageParam.get("end_date"));
		if (null != pageParam.get("change_types")) {
			Pattern p = Pattern.compile("^[0-9,]+$");
			Matcher m = p.matcher(pageParam.get("change_types") + "");

			if (!m.matches()) {
				throw new IllegalArgumentException("非法参数！");
			}
		}
		return studentAccountDao.page(pageParam);
	}

	@Override
	public List<TAccount> list(Map<String, Object> param) throws Exception {
		return studentAccountDao.list(param);
	}

	@Override
	public Map<String, Object> queryAccount(Map<String, Object> query)
			throws Exception {
		Assert.notNull(query.get("buId"));
		Assert.notNull(query.get("studentId"));
		Assert.notNull(query.get("accountType"));
		Map<String, Object> ret = studentAccountDao.queryAccount(query);
		if(CollectionUtils.isEmpty(ret)) {
			//创建账户
			createAccount((Long)query.get("studentId"), (Long)query.get("buId"),(Long)query.get("userId"));
			ret = new HashMap<String, Object>();
			ret.put("FEE_AMOUNT",0);
			ret.put("STUDENT_ID",query.get("studentId"));
		}
		return ret;
	}

	@Override
	public Map<String, Object> getCardNum(String id) throws Exception {

		return studentAccountDao.getCardNum(id);
	}

	@Override
	public List<Map<String, Object>> getPOSList(String cityId) throws Exception {
		return studentAccountDao.getPOSList(cityId);
	}

	@Override
	public int addReChargeHis(String sqlId, Map<String, Object> His)
			throws Exception {
		return studentAccountDao.addReChargeHis(sqlId, His);
	}

	@Override
	public int updateReCharge(String sqlId, String accountId, String cardNum,
			String id) throws Exception {
		return studentAccountDao.updateReCharge(sqlId, accountId, cardNum, id);
	}

	@Override
	public Map<String, Object> queryDynamicAccountInfo(Map<String, Object> param)
			throws Exception {
		return studentAccountDao.queryDynamicAccountInfo(param);
	}

	@Override
	public Integer queryAccountInfoCount(Map<String, Object> param)
			throws Exception {
		return studentAccountDao.queryAccountInfoCount(param);
	}

	@Override
	public TAccount queryAccountInfo(Map<String, Object> param)
			throws Exception {
		return studentAccountDao.queryAccountInfo(param);
	}

	@Override
	public int saveAccountChange(TAccountChange tAccountChange)
			throws Exception {
		return studentAccountDao.saveAccountChange(tAccountChange);
	}

	@Override
	public int updateFeeAccount(Long accountId, Double amount) throws Exception {
		HashMap<String,Object> param=new HashMap<String,Object>();
		 param.put("amount", amount);
		 param.put("accountId", accountId);
		return studentAccountDao.updateFeeAccount(param);
	}

	@Override
	public int updateFrozenAccount(Long accountId, Double amount)
			throws Exception {
		HashMap<String,Object> param=new HashMap<String,Object>();
		 param.put("amount", amount);
		 param.put("accountId", accountId);
		return studentAccountDao.updateFrozenAccount(param);
	}

	@Override
	public int updateRefundAccount(Long accountId, Double amount)
			throws Exception {
		HashMap<String,Object> param=new HashMap<String,Object>();
		 param.put("amount", amount);
		 param.put("accountId", accountId);
		return studentAccountDao.updateRefundAccount(param);
	}

	@Override
	public TabOrderInfo queryPrint01(String encoding) throws Exception {
		//跟据单据编号查询订单id
		Long orderId = studentAccountDao.queryIdByOrderEncoding(encoding);
		TabOrderInfo orderInfo = orderInfoService.queryTemporaryOrderInfo(orderId);
		return orderInfo;
	}

	@Override
	public void transStuAccount(Long dynamicId, String auditStatus) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("p_dynamic_id", dynamicId);
		paramMap.put("p_audit_status", auditStatus);
		paramMap.put("o_err_code", null);
		paramMap.put("o_err_desc", null);
		this.studentAccountDao.transStuAccount(paramMap);
		if (!paramMap.get("o_err_code").toString().equals("0")) {
			throw new Exception("存储过程异常" + paramMap.get("o_err_desc"));
		}
	}

	/**
	 * 跨团队转账发送短信通知
	 * @param paramMap
	 * @throws Exception
	 */
	private void accountTransferSendMsg(Map<String, Object> paramMap) throws Exception {
		String content = TemplateUtil.getInstance().getContent("accountTransferMsg.ftl", paramMap);
		if (StringUtils.isNotEmpty(content)) {
			String mobile = paramMap.get("mobile") == null ? null : paramMap.get("mobile").toString();
			if (StringUtils.isNotEmpty(mobile) && mobile.length() == 11) {
				this.messageRequirementService.sendMessage(content, mobile, null);
			}
		}
	}
}
