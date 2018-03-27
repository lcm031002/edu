package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.PrivilegeRule;
import com.github.pagehelper.Page;

@Repository(value = "privilegeRuleDao")
public interface PrivilegeRuleDao {

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
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	Page<PrivilegeRule> queryPrivilegeRuleForPage(Map<String, Object> params)
			throws Exception;

	/**
	 * 多表关联查询
	 * 
	 * @param param
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
	List<PrivilegeRule> queryPrivilegeRuleForList(Map<String, Object> params)
			throws Exception;

	/**
	 * 传参单表查询
	 * 
	 * @param param
	 * @return
	 */
	List<PrivilegeRule> queryPrivilegeRuleForListWithoutBranchs(
            Map<String, Object> params) throws Exception;

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
	Integer insert(PrivilegeRule privilegeRule) throws Exception;

	/**
	 * 更新
	 * 
	 * @param privilegeRule
	 * @param branch_ids
	 *            校区
	 * @throws Exception
	 */
	Integer update(PrivilegeRule privilegeRule) throws Exception;
	
	
	/**
	 * @param empIdStr
	 *            ID数组
	 * @return 空
	 * @throws Exception
	 */

	void deleteByIds(List<String> ids) throws Exception;

	/**
	 * 新增校区关系
	 * 
	 * @param id_branchs
	 * @return
	 * @throws Exception
	 */
	Integer addPrivilegeSchoolRef(List<Map<String, Long>> id_branchs)
			throws Exception;

	/**
	 * 删除校区关系
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	Integer deletePrivilegeSchoolRef(List<Long> ids) throws Exception;

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
	PrivilegeRule queryRuleById(Map<String, Object> params) throws Exception;

	/**
	 * 获取过期优惠规则
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Long> queryOutPrivilegeRule(Map<String, Object> params)
			throws Exception;

	/**
	 * 
	 * @Title: queryRuleByBranchId
	 * @Description: 按照校区ID查询规则
	 * @param parms
	 *            查询参数
	 * @return 查询规则
	 * @throws Exception
	 *             设定文件 List<PrivilegeRule> 返回类型
	 * 
	 */
	List<PrivilegeRule> queryRuleByBranchId(Map<String, Object> params) throws Exception;
	
	/**
	 * 更新优惠规则状态
	 * @param params 更新数据
	 * status：更新的状态值
	 * ids：要更新记录编号，多个用逗号分隔
	 * @throws Exception
	 */
	void changeStatus(Map<String, Object> params) throws Exception;
}
