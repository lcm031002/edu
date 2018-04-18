/**  
 * @Title: PrivilegeRuleServiceImpl.java
 * @Package com.ebusiness.erp.promotion.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月1日 下午3:03:58
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.promotion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.util.ERPConstants;
import com.edu.erp.model.*;
import com.edu.erp.student.service.StudentRelService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.PrivilegeRuleDao;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.promotion.service.PrivilegeRuleService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.PromotionUtils;
import com.github.pagehelper.Page;

/**
 * @ClassName: PrivilegeRuleServiceImpl
 * @Description: 优惠规则服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月1日 下午3:03:58
 * 
 */
@Service("privilegeRuleService")
public class PrivilegeRuleServiceImpl implements PrivilegeRuleService {
    
    private static final Logger log = Logger.getLogger(PrivilegeRuleServiceImpl.class);

	@Resource(name = "privilegeRuleDao")
	private PrivilegeRuleDao privilegeRuleDao;

	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Resource(name = "studentRelService")
	private StudentRelService studentRelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryPrivilegeRuleForPage(java.util.Map)
	 */
	@Override
	public Page<PrivilegeRule> queryPrivilegeRuleForPage(
			Map<String, Object> page) throws Exception {

		Assert.notNull(page);
		return privilegeRuleDao.queryPrivilegeRuleForPage(page);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryPrivilegeRuleForRef(java.util.Map)
	 */
	@Override
	public List<PrivilegeRule> queryPrivilegeRuleForRef(
			Map<String, Object> params) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryPrivilegeRuleForList(java.util.Map)
	 */
	@Override
	public List<PrivilegeRule> queryPrivilegeRuleForList(
			Map<String, Object> param) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryPrivilegeRuleForListWithoutBranchs(java.util.Map)
	 */
	@Override
	public List<PrivilegeRule> queryPrivilegeRuleForListWithoutBranchs(
			Map<String, Object> param) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryPrivilegeRuleForOne(java.lang.Long)
	 */
	@Override
	public PrivilegeRule queryPrivilegeRuleForOne(Long id) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#addPrivilegeRule
	 * (com.ebusiness.erp.model.PrivilegeRule, java.lang.String)
	 */
	@Override
	public void addPrivilegeRule(PrivilegeRule privilegeRule, String branch_ids)
			throws Exception {
		Integer ret = privilegeRuleDao.insert(privilegeRule);
		if (ret < 0) {
			throw new Exception();
		}

		Long rule_id = privilegeRule.getId();
		List<Map<String, Long>> rule_branch = PromotionUtils.get_P_ORG_Ref(
				rule_id, branch_ids);
		if (rule_branch.size() > 0)
			privilegeRuleDao.addPrivilegeSchoolRef(rule_branch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#updatePrivilegeRule
	 * (com.ebusiness.erp.model.PrivilegeRule, java.lang.String)
	 */
	@Override
	public void updatePrivilegeRule(PrivilegeRule privilegeRule,
			String branch_ids) throws Exception {
		Integer r = privilegeRuleDao.update(privilegeRule);
		if (r < 1) {
			throw new Exception();
		}

		Long rule_id = privilegeRule.getId();
		List<Map<String, Long>> rule_branch = PromotionUtils.get_P_ORG_Ref(
				rule_id, branch_ids);

		List<Long> ruleIds = new ArrayList<Long>(1);
		ruleIds.add(rule_id);
		privilegeRuleDao.deletePrivilegeSchoolRef(ruleIds);
		if (rule_branch.size() > 0)
			privilegeRuleDao.addPrivilegeSchoolRef(rule_branch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * changePrivilegeRuleStatus(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changePrivilegeRuleStatus(String ids, Integer status)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#queryRuleByBranchId
	 * (java.lang.Long)
	 */
	@Override
	public List<PrivilegeRule> queryRuleByBranchId(Long branchId)
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#queryByRuleId
	 * (java.lang.Long)
	 */
	@Override
	public PrivilegeRule queryByRuleId(Long ruleId) throws Exception {
		Assert.notNull(ruleId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", ruleId);
		return privilegeRuleDao.queryRuleById(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.promotion.service.PrivilegeRuleService#
	 * queryOutPrivilegeRule(java.util.Map)
	 */
	@Override
	public List<Long> queryOutPrivilegeRule(Map<String, Object> param)
			throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#isOrderRuleValid
	 * (java.lang.Long)
	 */
	@Override
	public Map<String, Object> isOrderRuleValid(Long orderId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", false);
		TabOrderInfo orderBusiness = orderInfoService
				.queryTemporaryOrderInfo(orderId);
		List<TabOrderInfoDetail> details = orderBusiness.getDetails();
		StudentInfo studentBusiness = orderBusiness.getStudentInfo();
		Long integral = 0L;
		if (studentBusiness != null
				&& !CollectionUtils.isEmpty(studentBusiness
						.getStudentIntegralList())) {
			for (StudentIntegral studentIntegral : studentBusiness
					.getStudentIntegralList()) {
				integral = integral + studentIntegral.getCrrent_integral();
			}
		}

		long count = 0;
		long courseTimes = 0;

		// 查询学生团队下剩余总课时
		Map<String, Object> resultQuery = new HashMap<String, Object>();
		resultQuery.put("student_id", orderBusiness.getStudent_id());
		resultQuery.put("bu_id", orderBusiness.getBu_id());
		Map<String, Object> studentCourseTimes = studentInfoService
				.queryStudentAllCourseTimes(resultQuery);

		if (!CollectionUtils.isEmpty(studentCourseTimes)) {
			if (null != studentCourseTimes.get("COURSE_SURPLUS_COUNT")) {
				courseTimes = Long.parseLong(""
						+ studentCourseTimes.get("COURSE_SURPLUS_COUNT"));
			}
		}

		// 判断详单的优惠规则的有效性
		if (!CollectionUtils.isEmpty(details)) {

			for (TabOrderInfoDetail orderDetailBusiness : details) {
				count = count + orderDetailBusiness.getCourse_total_count();
			}
			for (TabOrderInfoDetail orderDetailBusiness : details) {
				Long course_total_count = orderDetailBusiness
						.getCourse_total_count();
				Long discount_sum_price = orderDetailBusiness
						.getDiscount_sum_price();
				Long extend_column3 = orderDetailBusiness.getExtend_column3();
				Double immediate_reduce = orderDetailBusiness
						.getImmediate_reduce();
				discount_sum_price = (long) (discount_sum_price
						+ (extend_column3 == null ? 0 : extend_column3) + (immediate_reduce == null ? 0
						: immediate_reduce));

				if (orderDetailBusiness.getRule_id() != null) {
					PrivilegeRule privilegeRuleBusiness = this
							.queryByRuleId(orderDetailBusiness.getRule_id());
					List<PrivilegeCriteria> privilegeCriterias = privilegeRuleBusiness
							.getPrivilegeCriteria();
					if (!CollectionUtils.isEmpty(privilegeCriterias)) {
						for (PrivilegeCriteria privilegeCriteriaBusiness : privilegeCriterias) {
							Long sum_hour = privilegeCriteriaBusiness
									.getSum_hour();
							Long sum_price = privilegeCriteriaBusiness
									.getSum_price();
							Long sum_integral = privilegeCriteriaBusiness
									.getSum_integral();
							Long grade_id = privilegeCriteriaBusiness
									.getGrade_id();

							if (privilegeCriteriaBusiness.getStatus() == null
									|| privilegeCriteriaBusiness.getStatus() == 0) {
								continue;
							}
							// 总课时
							if (null != sum_hour) {
								if (course_total_count < sum_hour) {
									result.put("error", true);
									result.put("message", "当前报班课程("
											+ orderDetailBusiness
													.gettCourseInfo()
													.getCourse_name() + ")课时数("
											+ course_total_count
											+ "课时)不满足优惠规则要求(" + sum_hour
											+ "课时)");
									return result;
								}
							}
							// 总价格
							if (null != sum_price) {
								if (discount_sum_price < sum_price) {
									result.put("error", true);
									result.put("message", "当前报班课程("
											+ orderDetailBusiness
													.gettCourseInfo()
													.getCourse_name()
											+ ")实际总价(" + discount_sum_price
											+ ")不满足优惠规则总价要求(" + sum_price + ")");
									return result;
								}
							}
							// 总积分
							if (null != sum_integral && sum_integral > 0) {
								if (integral == null || integral < sum_integral) {
									result.put("error", true);
									result.put("message", "当前报班课程("
											+ orderDetailBusiness
													.gettCourseInfo()
													.getCourse_name()
											+ ")折扣要求学生积分(" + integral
											+ ")不满足优惠规则要求积分(" + sum_integral
											+ ")");
									return result;
								}
							}

							// 年级
							if (grade_id != null) {
								Long student_gradeId = studentBusiness
										.getGrade_id();
								if (student_gradeId != null
										&& grade_id.intValue() != student_gradeId
												.intValue()) {
									result.put("error", true);
									result.put(
											"message",
											"当前报班课程("
													+ orderDetailBusiness
															.gettCourseInfo()
															.getCourse_name()
													+ ")折扣要求学生年级("
													+ studentBusiness
															.getGrade_name()
													+ ")不满足优惠规则年级");
									return result;
								}
							}

						}

					}

					isMatchSubOrder(orderDetailBusiness, studentBusiness,
							privilegeRuleBusiness, result, courseTimes);
				}
			}
		}

		// 判断主单的有效性
		if (orderBusiness.getRule_id() != null) {
			PrivilegeRule privilegeRuleBusinessOrder = this
					.queryByRuleId(orderBusiness.getRule_id());
			long order_actual_price = orderBusiness.getSum_price();
			if (!CollectionUtils.isEmpty(privilegeRuleBusinessOrder
					.getPrivilegeCriteria())) {
				for (PrivilegeCriteria criteriaBusiness : privilegeRuleBusinessOrder
						.getPrivilegeCriteria()) {
					Long sum_hour = criteriaBusiness.getSum_hour();
					Long sum_price = criteriaBusiness.getSum_price();
					Long sum_integral = criteriaBusiness.getSum_integral();

					Long grade_id = criteriaBusiness.getGrade_id();

					if (criteriaBusiness.getStatus() == null
							|| criteriaBusiness.getStatus() == 0) {
						continue;
					}
					// 总课时
					if (null != sum_hour) {
						if (count < sum_hour) {
							result.put("error", true);
							result.put("message",
									"当前订单(" + orderBusiness.getEncoding()
											+ ")总课时数(" + count
											+ "课时)不满足优惠规则要求(" + sum_hour
											+ "课时)");
							return result;
						}
					}
					// 总价格
					if (null != sum_price) {
						if (order_actual_price < sum_price) {
							result.put("error", true);
							result.put("message",
									"当前订单(" + orderBusiness.getEncoding()
											+ ")总价(" + order_actual_price
											+ ")不满足优惠规则总价要求(" + sum_price + ")");
							return result;
						}
					}
					// 总积分
					if (null != sum_integral && sum_integral > 0) {
						if (integral == null || integral < sum_integral) {
							result.put("error", true);
							result.put("message",
									"当前订单(" + orderBusiness.getEncoding()
											+ ")要求学生积分(" + integral
											+ ")不满足优惠规则要求积分(" + sum_integral
											+ ")");
							return result;
						}
					}

					// 年级
					if (grade_id != null) {
						Long student_gradeId = studentBusiness.getGrade_id();
						if (student_gradeId != null
								&& grade_id.intValue() != student_gradeId
										.intValue()&&grade_id.intValue()!=-1) {
							result.put("error", true);
							result.put(
									"message",
									"当前订单(" + orderBusiness.getEncoding()
											+ ")要求学生年级("
											+ studentBusiness.getGrade_name()
											+ ")不满足优惠规则年级");
							return result;
						}
					}

				}
			}

			isMatchOrder(orderBusiness, studentBusiness,
					privilegeRuleBusinessOrder, result, courseTimes);
			return result;
		}
		return result;
	}

	private void isMatchOrder(TabOrderInfo orderBusiness,
			StudentInfo studentBusiness, PrivilegeRule privilegeRuleBusiness,
			Map<String, Object> result, long courseTimes) throws Exception {
		// 适用人群
		Integer use_scope = privilegeRuleBusiness.getUse_scope();
		Integer is_old_student = studentBusiness.getIs_old_student();
		// 剩余课时要求
		if (StringUtils.isNotBlank(privilegeRuleBusiness
				.getCourse_surplus_count())
				&& !"0".equals(privilegeRuleBusiness.getCourse_surplus_count())) {
			try {
				if (Long.parseLong(privilegeRuleBusiness
						.getCourse_surplus_count()) >= courseTimes) {
					result.put("error", true);
					result.put(
							"message",
							"当前订单("
									+ orderBusiness.getEncoding()
									+ ")要求学生剩余课时"
									+ privilegeRuleBusiness
											.getCourse_surplus_count()
									+ ",而实际剩余课时为" + courseTimes);
					return;
				}
			} catch (Exception e) {
				if (Long.parseLong(privilegeRuleBusiness
						.getCourse_surplus_count()) >= courseTimes) {
					result.put("error", true);
					result.put(
							"message",
							"当前订单("
									+ orderBusiness.getEncoding()
									+ ")要求学生剩余课时"
									+ privilegeRuleBusiness
											.getCourse_surplus_count()
									+ ",而实际剩余课时为" + courseTimes);
					return;
				}
			}
		}

		// 老学员
		//if (use_scope != null && use_scope == 2 && null != is_old_student
		//		&& is_old_student != 1) {
		//	result.put("error", true);
		//	result.put("message", "当前订单(" + orderBusiness.getEncoding()
		//			+ ")要求学生为新学员");
		//	return;
		//}
		// 新学员
		if (use_scope != null && use_scope == 3 && null != is_old_student
				&& is_old_student != 0) {
			result.put("error", true);
			result.put("message", "当前订单(" + orderBusiness.getEncoding()
					+ ")要求学生为新学员");
			return;
		}

		// 推荐人
		if (use_scope != null && use_scope == 4) {
			Map<String, Object> queryData = new HashMap<String, Object>();
			queryData.put("STUDENT_ID_OLD", studentBusiness.getId());
			List<StudentRel> dataInfo = studentBusiness.getMyRels();
			boolean match = false;
			if (!CollectionUtils.isEmpty(dataInfo)) {
				for (StudentRel rel : dataInfo) {
					if (rel.getOldUsed() != null) {
						Long old_used = rel.getOldUsed();
						if (old_used.intValue() == 0) {
							match = true;
							rel.setOldUsed(1L);
							studentRelService.updateStudentRel(rel);
							break;
						}
					}
				}
			}
			if (!match) {
				result.put("error", true);
				result.put("message", "当前订单(" + orderBusiness.getEncoding()
						+ ")要求学生为推荐人");
			}

		}
		// 被推荐人
		if (use_scope != null && use_scope == 5) {
			List<StudentRel> rels = studentBusiness.getRelsMy();
			boolean match = false;
			if (!CollectionUtils.isEmpty(rels)) {
				for (StudentRel rel : rels) {
					if (rel.getNewUsed() != null) {
						Long old_used = rel.getNewUsed();
						if (old_used.intValue() == 0) {
							match = true;
							rel.setNewUsed(1L);
							studentRelService.updateStudentRel(rel);
							break;
						}
					}
				}
			}
			if (!match) {
				result.put("error", true);
				result.put("message", "当前订单(" + orderBusiness.getEncoding()
						+ ")要求学生为被推荐人");
			}
		}
	}

	private void isMatchSubOrder(TabOrderInfoDetail orderDetailBusiness,
			StudentInfo studentBusiness, PrivilegeRule privilegeRuleBusiness,
			Map<String, Object> result, long courseTimes) throws Exception {
		// 适用人群
		Integer use_scope = privilegeRuleBusiness.getUse_scope();
		Integer is_old_student = studentBusiness.getIs_old_student();
		// 剩余课时要求
		if (StringUtils.isNotBlank(privilegeRuleBusiness
				.getCourse_surplus_count())
				&& !"0".equals(privilegeRuleBusiness.getCourse_surplus_count())) {
			try {
				if (Long.parseLong(privilegeRuleBusiness
						.getCourse_surplus_count()) >= courseTimes) {
					result.put("error", true);
					result.put("message", "当前报班课程("
							+ orderDetailBusiness.gettCourseInfo()
									.getCourse_name() + ")要求学生剩余课时"
							+ privilegeRuleBusiness.getCourse_surplus_count()
							+ ",而实际剩余课时为" + courseTimes);
					return;
				}
			} catch (Exception e) {
				if (Long.parseLong(privilegeRuleBusiness
						.getCourse_surplus_count()) >= courseTimes) {
					result.put("error", true);
					result.put("message", "当前报班课程("
							+ orderDetailBusiness.gettCourseInfo()
									.getCourse_name() + ")要求学生剩余课时"
							+ privilegeRuleBusiness.getCourse_surplus_count()
							+ ",而实际剩余课时为" + courseTimes);
					return;
				}
			}
		}

		// 老学员
		//if (use_scope != null && use_scope == 2 && null != is_old_student
		//		&& is_old_student != 1) {
		//	result.put("error", true);
		//	result.put("message", "当前报班课程("
		//			+ orderDetailBusiness.gettCourseInfo().getCourse_name()
		//			+ ")要求学生为新学员");
		//	return;
		//}

		// 新学员
		if (use_scope != null && use_scope == 3 && null != is_old_student
				&& is_old_student != 0) {
			result.put("error", true);
			result.put("message", "当前报班课程("
					+ orderDetailBusiness.gettCourseInfo().getCourse_name()
					+ ")要求学生为老学员");
			return;
		}
		// 推荐人
		if (use_scope != null && use_scope == 4) {

			List<StudentRel> dataInfo = studentBusiness.getMyRels();
			boolean match = false;
			if (!CollectionUtils.isEmpty(dataInfo)) {
				for (StudentRel rel : dataInfo) {
					if (rel.getOldUsed() != null) {
						Long old_used = rel.getOldUsed();
						if (old_used.intValue() == 0) {
							match = true;
							rel.setOldUsed(1L);
							studentRelService.updateStudentRel(rel);
							break;
						}
					}
				}
			}
			if (!match) {
				result.put("error", true);
				result.put("message", "当前报班课程("
						+ orderDetailBusiness.gettCourseInfo().getCourse_name()
						+ ")要求学生为推荐人");
			}

		}
		// 被推荐人
		if (use_scope != null && use_scope == 4) {
			Map<String, Object> queryData = new HashMap<String, Object>();
			queryData.put("STUDENT_ID_NEW", studentBusiness.getId());
			List<StudentRel> dataInfo = studentBusiness.getRelsMy();
			boolean match = false;
			if (!CollectionUtils.isEmpty(dataInfo)) {
				for (StudentRel rel : dataInfo) {
					if (rel.getNewUsed() != null) {
						Long old_used = rel.getNewUsed();
						if (old_used.intValue() == 0) {
							match = true;
							rel.setNewUsed(1L);
							studentRelService.updateStudentRel(rel);
							break;
						}
					}
				}
			}
			if (!match) {
				result.put("error", true);
				result.put("message", "当前报班课程("
						+ orderDetailBusiness.gettCourseInfo().getCourse_name()
						+ ")要求学生为被推荐人");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#queryRuleByBranchId
	 * (java.util.Map)
	 */
	@Override
	public List<PrivilegeRule> queryRuleByBranchId(Map<String, Object> params)
			throws Exception {
		Assert.notNull(params);
		return privilegeRuleDao.queryRuleByBranchId(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.promotion.service.PrivilegeRuleService#queryOrderRuleId
	 * (java.lang.Long)
	 */
	@Override
	public List<PrivilegeRule> queryOrderRule(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return privilegeRuleDao.queryOrderRule(orderId);
	}

	@Override
	public void deletePrivilegeRule(String ids) throws Exception {
		// 将数据ID字符串转换为List
		List<String> idList = new ArrayList<String>();
		String[] idArray = ids.split(",");
		idList = Arrays.asList(idArray);
		privilegeRuleDao.deleteByIds(idList);
		
	}

	/**
     * 自动下架到期优惠规则
     * 
     */
    @Override
    public void stopOutPrivilegeRule() {
        Map<String, Object> params = new HashMap<String, Object>();
        String strPrivilegeRuleIds = null;
        try {
            params.put("cur_date", DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_3));
            List<Long> privilegeRuleIdList = this.privilegeRuleDao.queryOutPrivilegeRule(params);
            
            if (!CollectionUtils.isEmpty(privilegeRuleIdList)) {
                strPrivilegeRuleIds = privilegeRuleIdList.toString().replace("[", StringUtils.EMPTY).replace("]", StringUtils.EMPTY);
            }

            if (strPrivilegeRuleIds != null) {
                params.put("ids", strPrivilegeRuleIds);
                params.put("status", BaseObject.StatusEnum.INVALID.getCode());
                this.privilegeRuleDao.changeStatus(params);
                
                StringBuffer buff = new StringBuffer();
                buff.append("优惠规则ID：");
                buff.append(strPrivilegeRuleIds);
            }
        } catch (Exception e) {
            log.error(e);
            StringBuffer buff = new StringBuffer();
            buff.append("优惠规则ID：");
            buff.append(strPrivilegeRuleIds);
            buff.append("，");
            buff.append("失败信息：");
            buff.append(e);
        }
    }

}
