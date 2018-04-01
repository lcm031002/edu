/**  
 * @Title: DetailBusinessInfoFormat.java
 * @Package com.modules.work_flow.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2014年12月11日 下午5:21:55
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.util;

import java.util.Map;

import com.edu.common.util.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.erp.model.OrderCouponRel;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.model.TabOrderInfo;

/**
 * @ClassName: DetailBusinessInfoFormat
 * @Description: 业务详细信息格式化处理类
 * @author zhuliyong zly@entstudy.com
 * @date 2014年12月11日 下午5:21:55
 * 
 */
public class DetailBusinessInfoFormat {

	public static String lipeiString(Map<String,Object> json, Object dynamic_id,
			StudentInfo studentBusiness, OrgModel orgModel) {
		Object student_id = json.get("student_id");
		Object money = json.get("money");
		Object remark = json.get("remark");
		StringBuilder sb = new StringBuilder();
		sb.append("【理赔业务】");
		sb.append("$$$$");
		sb.append("理赔单据流水：");
		sb.append(dynamic_id);
		sb.append("$$$$");
		sb.append("学生：");
		if (studentBusiness.getStudent_name() != null) {
			sb.append(studentBusiness.getStudent_name() + "(" + student_id
					+ ")");
		}

		sb.append("$$$$");
		sb.append("理赔金额：");
		sb.append(money);
		sb.append(".00元");
		sb.append("$$$$");
		sb.append("业务校区：");
		sb.append(orgModel.getText());
		sb.append("$$$$");
		sb.append("描述信息：");
		sb.append(remark);
		sb.append("$$$$");
		return sb.toString();
	}

	public static String tuifeiString(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("【退费业务】");
		sb.append("$$$$");
		sb.append("退费课程：");
		sb.append("$$$$");
		sb.append(params.get("course_name"));
		if (null != params.get("teacher_name")) {
			sb.append("-" + params.get("teacher_name"));
		}
		sb.append("；");
		if (params.get("premium_result") != null) {
			sb.append("$$$$");
			sb.append(params.get("premium_formula"));
			sb.append("，");
			sb.append("$$$$");
			sb.append(params.get("premium_detail"));
			sb.append("；");
			sb.append("$$$$");
		} else {
			sb.append("$$$$");
			sb.append("此单退费金额为 0");
			sb.append("；");
			sb.append("$$$$");
		}

		Long refundAmount = NumberUtils.object2Long(params.get("premium_result"));
		refundAmount = (refundAmount == null) ? 0L : refundAmount;

		Long premiumDeductionAmount = 0L;
		if (params.get("premium_deduction_amount") != null) {
			premiumDeductionAmount = NumberUtils.object2Long(params.get("premium_deduction_amount"));
			premiumDeductionAmount = (premiumDeductionAmount == null) ? 0L : premiumDeductionAmount;

			sb.append("$$$$");
			sb.append("退费补扣：");
			sb.append(params.get("premium_deduction_amount"));
			sb.append(".00元，");
			sb.append("$$$$");
		}

		sb.append("$$$$");
		sb.append("实退金额 = ");
		sb.append((refundAmount + premiumDeductionAmount));
		sb.append(" - ").append(premiumDeductionAmount).append(" = ");
		sb.append(params.get("premium_result"));
		sb.append(".00元");
		sb.append("$$$$");
		return sb.toString();
	}

	public static String menuOrderCancelString(TabOrderInfo orderBusiness,
			String remark) {
		StringBuilder sb = new StringBuilder();
		sb.append("【手工订单作废】");
		sb.append("$$$$");
		sb.append("订单编号：");
		sb.append("$$$$");
		sb.append(orderBusiness.getEncoding());
		sb.append("$$$$");
		sb.append("订单描述：");
		sb.append("$$$$");
		sb.append(orderBusiness.getRemark() == null ? "" : orderBusiness
				.getRemark());
		sb.append("$$$$");
		sb.append("订单作废备注:");
		sb.append("$$$$");
		sb.append(remark);
		sb.append("$$$$");
		return sb.toString();
	}

	public static String orderPromString(TabOrderInfo orderBusiness) {
		// 优惠金额
		Double pro_amt = orderBusiness.getSum_price()
				- orderBusiness.getActual_price()
				- (orderBusiness.getImmediate_reduce() == null ? 0
						: orderBusiness.getImmediate_reduce());
		// 立减金额
		Double pre_amt = orderBusiness.getImmediate_reduce() == null ? 0
				: orderBusiness.getImmediate_reduce();

		StringBuilder sb = new StringBuilder();
		sb.append("【订单优惠】");
		sb.append("$$$$");
		sb.append("订单编号：");
		sb.append(orderBusiness.getEncoding());
		sb.append("$$$$");
		sb.append("原金额：");
		sb.append(orderBusiness.getSum_price());
		sb.append("$$$$");
		sb.append("实际金额：");
		sb.append(orderBusiness.getActual_price());

		if (pro_amt != null && pro_amt > 0) {
			sb.append("$$$$");
			sb.append("优惠金额：");
			sb.append(pro_amt);
		}
		if (pre_amt != null && pre_amt > 0) {
			sb.append("$$$$");
			sb.append("立减金额：");
			sb.append(pre_amt);
		}
		if (!CollectionUtils.isEmpty(orderBusiness.getCoupon_rels())) {
			int count = orderBusiness.getCoupon_rels().size();
			long amount = 0L;
			for (OrderCouponRel rel : orderBusiness.getCoupon_rels()) {
				amount = amount +( rel.getCoupon_amount()==null?0l:rel.getCoupon_amount());
			}
			sb.append("$$$$");
			sb.append("优惠券：");
			sb.append(count + "张");
			sb.append("$$$$");
			sb.append("优惠总金额：");
			sb.append(amount);
		}
		sb.append("$$$$");
		sb.append("订单描述：");
		sb.append(orderBusiness.getRemark() == null ? "" : orderBusiness
				.getRemark());
		if (!StringUtils.isEmpty(orderBusiness.getExtend_column())) {
			sb.append("$$$$");
			sb.append("立减说明：");
			sb.append(orderBusiness.getExtend_column());
		}
		sb.append("$$$$");
		return sb.toString();
	}

	public static String kuaYueZhiKongString(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("【跨月考勤置空】");
		sb.append("$$$$");
		sb.append("学生ID：");
		sb.append(params.get("studentId"));
		sb.append("$$$$");
		sb.append("学生姓名：");
		sb.append(params.get("student_name"));

		if (params.get("COURSE_NAME") != null) {
			sb.append("$$$$");
			sb.append("课程名称：");
			sb.append(params.get("COURSE_NAME"));
		}

		if (params.get("COURSE_TIMES") != null) {
			sb.append("$$$$");
			sb.append("课次：");
			sb.append(params.get("COURSE_TIMES"));
		}

		return sb.toString();
	}

	public static String studentAccountTransfer(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("【转账业务】");
		sb.append("$$$$");
		sb.append("转账单据流水：");
		sb.append(params.get("dynamic_id"));
		sb.append("$$$$");
		sb.append(" 学生：");
		sb.append(params.get("out_student_name"));
		sb.append("$$$$");
		sb.append(" 账户类型：");
		sb.append(params.get("account_type"));
		sb.append("$$$$");
		sb.append(" 转账金额：");
		sb.append(params.get("transfer_money"));
		sb.append("$$$$");
		sb.append(" 业务校区：");
		sb.append(params.get("branch_name"));
		sb.append("$$$$");
		sb.append(" 对方学员：");
		sb.append(params.get("in_student_name"));
		sb.append("$$$$");
		sb.append(" 所在团队：");
		sb.append(params.get("in_bu_name"));
		return sb.toString();
	}
}
