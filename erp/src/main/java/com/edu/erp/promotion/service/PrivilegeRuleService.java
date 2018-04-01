package com.edu.erp.promotion.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.PrivilegeRule;
import com.github.pagehelper.Page;

public interface PrivilegeRuleService {

	/**
	 * 
	 * @Title: queryOrderRuleId
	 * @Description: 查询订单优惠规则
	 * @param orderId
	 *            订单ID
	 * @return 返回优惠规则
	 * @throws Exception
	 *             设定文件 List<PrivilegeRule> 返回类型
	 * 
	 */
	List<PrivilegeRule> queryOrderRule(Long orderId) throws Exception;

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	Page<PrivilegeRule> queryPrivilegeRuleForPage(Map<String, Object> page)
			throws Exception;

	/**
	 * 多表关联查询
	 * 
	 * @param params
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<PrivilegeRule> queryPrivilegeRuleForRef(Map<String, Object> params)
			throws Exception;

	/**
	 * 传参单表查询
	 * 
	 * @param param
	 * @return
	 */
	List<PrivilegeRule> queryPrivilegeRuleForList(Map<String, Object> param)
			throws Exception;

	/**
	 * 传参单表查询
	 * 
	 * @param param
	 * @return
	 */
	List<PrivilegeRule> queryPrivilegeRuleForListWithoutBranchs(
			Map<String, Object> param) throws Exception;

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	PrivilegeRule queryPrivilegeRuleForOne(Long id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	void addPrivilegeRule(PrivilegeRule privilegeRule, String branch_ids)
			throws Exception;

	/**
	 * 更新
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	void updatePrivilegeRule(PrivilegeRule privilegeRule, String branch_ids)
			throws Exception;

	/**
	 * 逻辑删除
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	void deletePrivilegeRule(String id)
			throws Exception;
	
	/**
	 * 根据ID改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	void changePrivilegeRuleStatus(String ids, Integer status) throws Exception;

	/**
	 * 
	 * @Description: 查询给定校区的优惠规则的信息
	 * @param branchId
	 *            校区ID
	 * @return List<PrivilegeRuleBusiness> 返回类型
	 * @throws Exception
	 *             数据库查询异常
	 */
	List<PrivilegeRule> queryRuleByBranchId(Long branchId) throws Exception;

	/**
	 * 
	 * @Title: queryByRuleId
	 * @Description: 通过优惠规则id查询优惠规则
	 * @param ruleId
	 *            优惠规则
	 * @throws Exception
	 *             设定文件
	 * @return PrivilegeRuleBusiness 返回类型
	 */
	PrivilegeRule queryByRuleId(Long ruleId) throws Exception;

	/**
	 * 获取过期优惠规则
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Long> queryOutPrivilegeRule(Map<String, Object> param)
			throws Exception;

	/**
	 * 
	 * @Title: isOrderValid
	 * @Description: 判定给定的订单使用的优惠规则是否有效
	 * @param orderId
	 *            订单ID
	 * @param detailId
	 *            详单ID
	 * @param ruleId
	 *            规则ID
	 * @throws Exception
	 *             设定文件
	 * @return Map 返回类型
	 */
	Map<String, Object> isOrderRuleValid(Long orderId) throws Exception;

	List<PrivilegeRule> queryRuleByBranchId(Map<String, Object> parms)
			throws Exception;
	
	/**
     * 自动下架到期优惠规则
     * 
     */
    void stopOutPrivilegeRule();

}
