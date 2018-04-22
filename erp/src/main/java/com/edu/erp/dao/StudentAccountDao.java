package com.edu.erp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.erp.model.TAccountDynamic;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TAccount;
import com.edu.erp.model.TAccountChange;
import com.github.pagehelper.Page;

/***
 * Description：学生账户管理 DAO 接口
 * Author: JunliZhang
 * Date:2014-11-10
 */
@Repository("studentAccountDao")
public interface StudentAccountDao{
	
	/**
	 * 根据地区Id  和产品线查询 地区下团队Id
	 * @param map {"cityId":"","productLineId":""}
	 * @return 
	 * @throws Exception
	 */
	Long queryBuIdByProductLine(HashMap<String, Object> map)throws Exception;
	/**
	 * 学生账户充值
	 * @param param 
	 *  入参：p_student_id       :学生Id
     *       p_branch_id        :校区ID
     *       p_bu_id     		:团队   
     *       p_pay_mode         :收付费方式 1：现金  2：刷卡  3：转账                 
     *       p_money            :充值金额
     *       p_student_card     :学生卡号
     *       p_company_card     :公司账户，或设备号
     *       p_input_user       :操作人
     *       p_approve_user     :审批人
     *       p_confirm_user     :确认人
     *       p_remark			:备注
     *       p_encoding			:编号
     *  出参：o_dynamic_id		:充值记录Id
     *  	 o_err_code         :错误代码
     *       o_err_desc         :错误描述 
	 * @throws Exception
	 */
	void accountRecharge(Map<String, Object> param) throws Exception;
	
	/**
	 * 创建学生账户
	 * @param  tAccount
	 * 入参:p_student_id              :学生ID
	 *     :p_bu_id                   :团队Id                        
	 *     :p_input_user              :创建人
	 * 出参:o_err_code                :错误编号
	 *     :o_err_desc                :错误描述
	 * @throws Exception
	 */
	void createAccount(TAccount tAccount)throws Exception;
	
	Map<String, Object> queryAccount(Map<String, Object> param)throws Exception;
	
	Map<String, Object> queryDynamicAccountInfo(Map<String, Object> param)throws Exception;
	
	/**
	 * 学生账户转账
	 * @param param
	 *  入参：p_input_student_id       :转入学生ID
	 *       p_output_student_id      :转出学生ID
	 *       p_input_bu_id     		  :转入学生团队  
	 *       p_output_bu_id           :转出学生团队
	 *       p_branch_id              :校区ID        
	 *       p_transfer_money         :转账金额
	 *       p_input_user             :操作人
	 *       p_approve_user           :审批人
	 *       p_confirm_user           :确认人
	 *       p_remark				  :备注
	 *       p_encoding			      :编号
	 *  出参：o_dynamic_id			  :转账记录Id
	 *  	 o_err_code               :错误代码
	 *       o_err_desc               :错误描述 
	 * @throws Exception
	 */
	void accountTransfer(Map<String, Object> param) throws Exception;
	
	/***
	 * 学生 取款
	 * 入参：  p_student_id             :转入学生ID
	 *       p_bu_id           	      :团队
	 *       p_money                  :取款金额
	 *       p_pay_mode               :取款方式  1：现金  2：刷卡  3：转账    4储值账户转账  5：一元账户
	 *       p_student_card           :学生卡号
	 *       p_company_card           :公司卡号
	 *       p_input_user             :操作人
	 *       p_remark                 :备注
	 *       p_encoding               :编号
	 * 出参：  o_dynamic_id             :取款记录 ID
	 *       o_err_code               :错误代码
	 *       o_err_desc               :错误描述	    
	 * @throws Exception
	 */
	void accountDrawing(Map<String, Object> param) throws Exception;
	/**
	 * 学生理赔申请
	 * @param param
	 * 入参： p_student_id             :转入学生ID
	 *       p_bu_id           		  :转出学生团队  
	 *       p_branch_id              :校区ID 
	 *       p_money                  :理赔金额
	 *       p_input_user             :操作人
	 *       p_approve_user           :审批人
	 *       p_confirm_user           :确认人
	 *       p_remark				  :备注
	 *       p_encoding				  :编号
	 * 出参：  o_dynamic_id              :理赔申请记录ID
	 *       o_err_code               :错误代码  
	 *       o_err_desc               :错误描述 
	 * @throws Exception
	 */
	void studentClaim(Map<String, Object> param) throws Exception;
	
	/**
	 * 学生理赔审批
	 * @param param
	 *  入参：p_change_id              :动户记录ID
	 *       p_status                 :审批结果  -- 1待审批  2未通过 3生效     
	 *       p_approve_user           :审批人
	 *       p_confirm_user           :确认人
	 * 出参：  o_err_code               :错误代码  
	 *       o_err_desc               :错误描述 
	 * @throws Exception
	 */
	void studentClaimVerify(Map<String, Object> param) throws Exception;
	
	/***
	 * 一元转校
	 * @param map
	 * 入参 :  p_student_id                :学生ID
	 *        p_output_branch_id          :转出校区                          
	 *        p_input_branch_id           :转入校区
	 *        p_trans_count               :转入数量
	 *        p_input_user                :操作人
	 *        p_encoding                  :编号
	 * 出参 :  o_err_code                  :错误编号
	 *        o_err_desc                  :错误描述
	 * @throws Exception
	 */
	void unitTransfer(Map<String, Object> map)throws Exception;
	
	/***
	 * 一元结算
	 * @param map
	 * 入参 :  p_student_id                :学生ID
	 *        p_output_branch_id          :转出校区                          
	 *        p_input_branch_id           :转入校区
	 *        p_trans_count               :转入数量
	 *        p_input_user                :操作人
	 *        p_encoding                  :编号
	 * 出参 :  o_err_code                  :错误编号
	 *        o_err_desc                  :错误描述
	 * @throws Exception
	 */
	void unitSettleAccounts(Map<String, Object> map)throws Exception;
	
	/***
	 * 查询打印 信息内容
	 * @param 	  param						查询信息参数
	 * @param 	  sqlId						查询 SQL Id
	 * @return    List<Map<String,Object>>	结果结合
	 * @return
	 */
	List<Map<String,Object>> queryOrder(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryOrderDetail(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryPayDetail(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryRechange(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryWithdrwaingInfo(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryTransfer(Map<String, Object> param)throws Exception;
	List<Map<String,Object>> queryClaimInfo(Map<String, Object> param)throws Exception;
	
	/***
	 * 学生 充值 理赔 取款作废
	 * @param param
	 *  入参：p_dynamic_id        :账户变动记录Id
	 *       p_encoding          :记录编号
	 *       p_input_user        :操作人
	 *       p_remark            :备注
	 *  出参：o_err_code          :错误编号
	 *       o_err_desc          :错误描述
	 * @throws Exception
	 */
	void accountDynamicCancel(Map<String, Object> param)throws Exception;
	
	/**
	 * 分页
	 * 
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> page(Map<String, Object> pageParam) throws Exception;
	List<TAccount> list(Map<String, Object> param) throws Exception;
	
	/***
	 * 获取单个学生的刷卡账号
	 */
	
	Map<String,Object> getCardNum(String id)throws Exception;
	
	/**
	 * 根据城市获取POS机列表
	 */
	List<Map<String,Object>> getPOSList(String cityId)throws Exception;
	
	/**
	 * 新增修改历史
	 */
	
	int addReChargeHis(String sqlId, Map<String, Object> His)throws Exception;
	
	/**
	 * 修改POS机和转账卡号
	 */
	
	int updateReCharge(String sqlId, String accountId, String cardNum, String id)throws Exception;
	
	/**
	 * 查询账户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	TAccount queryAccountInfo(Map<String, Object> param)throws Exception;

	Integer queryAccountInfoCount(Map<String, Object> param)throws Exception;
	/**
	 * 保存账户变动信息
	 * @param tAccountChange
	 * @return
	 * @throws Exception
	 */
	int saveAccountChange(TAccountChange tAccountChange) throws Exception;

	/**
	 * 保存账户流水信息
	 * @param tAccountDynamic
	 * @return
	 * @throws Exception
	 */
	int saveAccountDynamic(TAccountDynamic tAccountDynamic) throws Exception;

	/**
	 * 更新储蓄账户金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int updateFeeAccount(HashMap<String, Object> param) throws Exception;
	/**
	 * 更新退费账户金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int updateFrozenAccount(HashMap<String, Object> param) throws Exception;
	/**
	 * 更新冻结账户金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int updateRefundAccount(HashMap<String, Object> param) throws Exception;
	
	/**
	 * 根据单据编码查询订单id
	 * @param encoding 单据编码
	 * @return
	 * @throws Exception
	 */
	Long queryIdByOrderEncoding(String encoding)throws Exception;

	void transStuAccount(Map<String, Object> paramMap) throws Exception;

	Map<String, Object> queryStuAccountInfo(Long dynamicId);

}
