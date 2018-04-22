package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TAccount;
import com.edu.erp.model.TAccountChange;
import com.edu.erp.model.TAccountDynamic;
import com.edu.erp.model.TabOrderInfo;
import com.github.pagehelper.Page;
import org.jbpm.api.ProcessEngine;

/***
 * Description：学生账户管理Service 接口 Author: JunliZhang Date:2014-11-10
 */
public interface StudentAccountService {

	/***
	 * 学生账户充值
	 * 
	 * @param param
	 *            学生Id
	 * @param branchId
	 *            校区ID
	 * @param cityId
	 *            地区Id
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> accountRecharge(Map<String, Object> param,
			Long branchId, Long cityId, Long userId, Long buId)
			throws Exception;

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
	 * @throws Exception
	 */
	Map<String, Object> createAccount(Long studentId, Long buId, Long userId)
			throws Exception;
	/**
	 * 查询账户总额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryAccount(Map<String, Object> param)throws Exception;
	/**
	 * 查询账户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Integer queryAccountInfoCount(Map<String, Object> param)throws Exception;
	/**
	 * 查询账户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	TAccount queryAccountInfo(Map<String, Object> param)throws Exception;
	/**
	 * 保存账户变动信息
	 * @param tAccountChange
	 * @return
	 * @throws Exception
	 */
	int saveAccountChange(TAccountChange tAccountChange) throws Exception;
	/**
	 * 更新储蓄账户金额
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	int updateFeeAccount(Long accountId,Double amount) throws Exception;
	/**
	 * 更新退费账户金额
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	int updateFrozenAccount(Long accountId,Double amount) throws Exception;
	/**
	 * 更新冻结账户金额
	 * @param accountId
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	int updateRefundAccount(Long accountId,Double amount) throws Exception;

	/***
	 * 学生账户转账
	 * 
	 * @param param
	 *            {output_product_line
	 *            :"转出团队类型（产品线）",input_product_line:"转入团队类型（产品线）",
	 *            input_student_id:"转入学生Id",money:"转账金额",remark:"备注"}
	 * @param userId
	 *            学生Id
	 * @param branchId
	 *            校区ID
	 * @param buId
	 *            地区Id
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> accountTransfer(Map<String, Object> param, Long branchId, Long userId, String userName,
		Long buId, ProcessEngine processEngine) throws Exception;

	/***
	 * 学生账户取款
	 * 
	 * @param json
	 *            {p_student_id:"",p_money:"",p_pay_mode:"1现金 3转账"，
	 *            p_student_card:"",p_remark:""}
	 * @param buId
	 *            团队Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> accountDrawing(Map<String, Object> json, Long buId, Long userId,
			Long branchId) throws Exception;

	/***
	 * 学生理赔申请
	 * 
	 * @param json
	 *            {product_line:"团队（产品线）",money:"",remark:"备注"}
	 * @param branchId
	 *            校区ID
	 * @param cityId
	 *            地区Id
	 * @param userId
	 *            操作员
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> studentClaim(Map<String, Object> json, Long branchId,
			Long cityId, Long userId, Long buId) throws Exception;

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
	Map<String, Object> studentClaimVerify(String json, Long userId)
			throws Exception;

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
	Map<String, Object> unitTransfer(String json, Long userId, Long branchId)
			throws Exception;

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
	Map<String, Object> unitSettleAccounts(String json, Long userId)
			throws Exception;

	/*************************** 系统打印 ******************************************************/

	/***
	 * 查询报班 打印信息
	 * 
	 * @param encoding
	 *            报班单编码
	 * @return TabOrderInfo 报班打印内容
	 * @throws Exception
	 */
	TabOrderInfo queryPrint01(String encoding) throws Exception;
	
	/***
	 * 查询报班 信息
	 * 
	 * @param orderId
	 *            报班单ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint01(Long orderId) throws Exception;

	/***
	 * 查询退费信息
	 * 
	 * @param dynamicId
	 *            批改-退费ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint02(Long dynamicId) throws Exception;

	/***
	 * 查询充值信息
	 * 
	 * @param dynamicId
	 *            充值单据记录ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint03(Long dynamicId) throws Exception;

	/***
	 * 查询取款信息
	 * 
	 * @param dynamicId
	 *            取款单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint04(Long dynamicId) throws Exception;

	/***
	 * 查询转账信息
	 * 
	 * @param dynamicId
	 *            转账单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint05(Long dynamicId) throws Exception;

	/***
	 * 查询理赔信息
	 * 
	 * @param dynamicId
	 *            理赔单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint06(Long dynamicId) throws Exception;

	/***
	 * 查询转班信息
	 * 
	 * @param dynamicId
	 *            转班单据ID
	 * @return Map<String,Object> 报班打印内容
	 * @throws Exception
	 */
	Map<String, Object> queryPrint07(Long dynamicId) throws Exception;

	/***
	 * 学生充值 理赔 取款 作废
	 * 
	 * @param json
	 *            {"dynamic_id":"动户记录ID","dynamic_type":"动户类型","remark":"备注"}
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> accountDynamicCancel(String json, Long userId)
			throws Exception;

	/**
	 * 分页
	 * 
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> page(Map<String, Object> pageParam)
			throws Exception;

	List<TAccount> list(Map<String, Object> param) throws Exception;

	/***
	 * 获取单个学生的刷卡账号
	 */

	Map<String, Object> getCardNum(String id) throws Exception;
	
	/**
	 * 根据城市获取POS机列表
	 */

	List<Map<String, Object>> getPOSList(String cityId) throws Exception;

	/**
	 * 新增修改历史
	 */

	int addReChargeHis(String sqlId, Map<String, Object> His) throws Exception;
	/**
	 * 新增修改历史
	 */

	Integer addReChargeHis(Map<String, Object> accountRecharge) throws Exception;

	Integer addAccountRecharge(Map<String, Object> accountRecharge) throws Exception;

	/**
	 * 修改POS机和转账卡号
	 */

	int updateReCharge(String sqlId, String accountId, String cardNum, String id)
			throws Exception;
	

	/**
	 * 分页查询账户变动
	 * 
	 * @param param
	 * @return
	 */
	Page<TAccountDynamic> pageQueryStudentAccountDynamic(
			Map<String, Object> param) throws Exception;
	//add by zenglw start
	/**
	 * 学生充值 理赔 取款 作废
	 * @param dynamicId 账户动态单据id
	 * @param operator 操作者
	 * @param p_remark 备注信息
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> invalidAccountDynamic(Long dynamicId,Long operator,String p_remark) throws Exception;

	/**
	 * 修改POS机和转账卡号
	 */
	int updateReCharge(String accountId, String cardNum, String id)
			throws Exception;
	//add by zenglw end
	/**
	 * 查询动态账户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryDynamicAccountInfo(Map<String, Object> param) throws Exception ;

	Map<String, Object> queryStudentAccountDynamic(Long dynamicId)
			throws Exception;

	/**
	 * 跨团队学员账户转账审批
	 * @param  dynamicId 转账动态ID
	 * @param auditStatus 审批状态 1-同意 0-拒绝
	 */
	void transStuAccount(Long dynamicId, String auditStatus) throws Exception;
	
}
