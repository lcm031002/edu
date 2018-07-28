package com.edu.erp.model;

import java.util.ArrayList;
import java.util.List;


/***
 * Description : POJO
 *
 */
public class TabOrderInfoDetail extends BaseObject {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 239983190203373741L;

	private Long v3_mark; //

	private String old_id; // V3中的主键

	private Double immediate_reduce; //

	private Long order_id; //

	private Long branch_id;

	private String branch_name;

	private String encoding; //

	private Long student_id; //

	private Long course_id; //
	
	private String course_name;//课程名称

	private Long business_type; // 班级课 一对一 晚辅导

	private Long former_unit_price; //

	private Long former_sum_price; //

	private Long discount_unit_price; //

	private Long discount_sum_price; //

	private Long pre_forward; // 记录打折后总价差 直接结转掉 如果退费 需要反结转

	private String start_date; //

	private String end_date; //

	private String start_time; //

	private String end_time; //

	private Long course_total_count; //

	private Long teacher_id; //

	private Long course_surplus_count; //

	private String extend_column; //

	private String extend_column2; //

	private Long extend_column3; //

	private Long extend_column4; //
	
	private Long rule_id;
	
	private String rule_name;
	
	private Long course_pack_type;
	
	private Long course_pack_price;
	
	private Long surplus_cost;
	
	private Long quit_flag;
	
	private Long is_send;
	
	private Long order_course_type;
	
	//报班选择的课次信息
	private List<TabOrderCourseTimesInfo> orderCourseTimes = new ArrayList<TabOrderCourseTimesInfo>();

	private List<TOrderCourseTimes> tOrderCourseTimes= new ArrayList<TOrderCourseTimes>();
	
	//报班的课程定义信息
	private TCourse tCourseInfo = new TCourse();
	
	//科目级优惠券信息
	private PrivilegeRule privilegeRule = new PrivilegeRule();
	
	public Long getBusiness_type() {
		return business_type;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public Long getCourse_surplus_count() {
		return course_surplus_count;
	}

	public Long getCourse_total_count() {
		return course_total_count;
	}

	public Long getDiscount_sum_price() {
		return discount_sum_price;
	}

	public Long getDiscount_unit_price() {
		return discount_unit_price;
	}

	public final Long getQuit_flag() {
		return quit_flag;
	}

	public final void setQuit_flag(Long quit_flag) {
		this.quit_flag = quit_flag;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getEnd_time() {
		return end_time;
	}

	public String getExtend_column() {
		return extend_column;
	}

	public String getExtend_column2() {
		return extend_column2;
	}

	public Long getExtend_column3() {
		return extend_column3;
	}

	public Long getExtend_column4() {
		return extend_column4;
	}

	public Long getFormer_sum_price() {
		return former_sum_price;
	}

	public Long getFormer_unit_price() {
		return former_unit_price;
	}


	public Double getImmediate_reduce() {
		return immediate_reduce;
	}

	public String getOld_id() {
		return old_id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public Long getPre_forward() {
		return pre_forward;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public Long getV3_mark() {
		return v3_mark;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public void setCourse_surplus_count(Long course_surplus_count) {
		this.course_surplus_count = course_surplus_count;
	}

	public void setCourse_total_count(Long course_total_count) {
		this.course_total_count = course_total_count;
	}

	public void setDiscount_sum_price(Long discount_sum_price) {
		this.discount_sum_price = discount_sum_price;
	}

	public void setDiscount_unit_price(Long discount_unit_price) {
		this.discount_unit_price = discount_unit_price;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void setExtend_column(String extend_column) {
		this.extend_column = extend_column;
	}

	public void setExtend_column2(String extend_column2) {
		this.extend_column2 = extend_column2;
	}

	public void setExtend_column3(Long extend_column3) {
		this.extend_column3 = extend_column3;
	}

	public void setExtend_column4(Long extend_column4) {
		this.extend_column4 = extend_column4;
	}

	public void setFormer_sum_price(Long former_sum_price) {
		this.former_sum_price = former_sum_price;
	}

	public void setFormer_unit_price(Long former_unit_price) {
		this.former_unit_price = former_unit_price;
	}

	public void setImmediate_reduce(Double immediate_reduce) {
		this.immediate_reduce = immediate_reduce;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public void setPre_forward(Long pre_forward) {
		this.pre_forward = pre_forward;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public void setV3_mark(Long v3_mark) {
		this.v3_mark = v3_mark;
	}

	public Long getRule_id() {
		return rule_id;
	}

	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public Long getCourse_pack_type() {
		return course_pack_type;
	}

	public void setCourse_pack_type(Long course_pack_type) {
		this.course_pack_type = course_pack_type;
	}

	public Long getCourse_pack_price() {
		return course_pack_price;
	}

	public void setCourse_pack_price(Long course_pack_price) {
		this.course_pack_price = course_pack_price;
	}

	public final List<TabOrderCourseTimesInfo> getOrderCourseTimes() {
		return orderCourseTimes;
	}

	public final void setOrderCourseTimes(
			List<TabOrderCourseTimesInfo> orderCourseTimes) {
		this.orderCourseTimes = orderCourseTimes;
	}

	public final Long getSurplus_cost() {
		return surplus_cost;
	}

	public final void setSurplus_cost(Long surplus_cost) {
		this.surplus_cost = surplus_cost;
	}

	public final Long getIs_send() {
		return is_send;
	}

	public final void setIs_send(Long is_send) {
		this.is_send = is_send;
	}

	public final Long getOrder_course_type() {
		return order_course_type;
	}

	public final void setOrder_course_type(Long order_course_type) {
		this.order_course_type = order_course_type;
	}

	public final TCourse gettCourseInfo() {
		return tCourseInfo;
	}

	public final void settCourseInfo(TCourse tCourseInfo) {
		this.tCourseInfo = tCourseInfo;
	}

	public final PrivilegeRule getPrivilegeRule() {
		return privilegeRule;
	}

	public final void setPrivilegeRule(PrivilegeRule privilegeRule) {
		this.privilegeRule = privilegeRule;
	}

	public final String getCourse_name() {
		return course_name;
	}

	public final void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public List<TOrderCourseTimes> gettOrderCourseTimes() {
		return tOrderCourseTimes;
	}

	public void settOrderCourseTimes(List<TOrderCourseTimes> tOrderCourseTimes) {
		this.tOrderCourseTimes = tOrderCourseTimes;
	}
}