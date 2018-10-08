package com.edu.report.model;

/**
 * @ClassName: TPerformanceDetails
 * @Description: 业绩明细表
 *
 */
public class TPerformanceDetails {
    private Long id;
    private String order_number;
    private String business_date;
    private String order_no;
    private Long branch_id;
    private String branch_name;
    private String student_code;
    private String student_name;
    private Long operate_type;
    private String operate_type_name;
    private Double performance;
    private Double cash;
    private Double card;
    private Double transfer;
    private Double account;
    private Double eb_price;
    private Double online_price;
    private Double frozen_account;
    private Double refund_account;
    private Double transfer_out;
    private Double transfer_in;
    private Double cancel_order_amount;
    private Double refund;
    private Double cancel_refund_amount;
    private Double claim;
    private Long course_time;
    private String customer_account;
    private String company_account_name;
    private String company_account;
    private Long course_id;
    private String course_name;
    private Long is_textbooks;
    private String is_textbooks_name;
    private Long grade_id;
    private String grade_name;
    private Long subject_id;
    private String subject_name;
    private Double duration;
    private Long teacher_id;
    private String teacher_encoding;
    private String teacher_name;
    private Long assteacher_id;
    private String assteacher_encoding;
    private String assteacher_name;
    private Long performance_branch_id;
    private String performance_branch_name;
    private Long season_id;
    private String season_name;
    private Long agent_id;
    private String agent_name;
    private Long original_create_user;
    private String original_create_user_name;
    private Long is_new_continued;
    private String is_new_continued_name;
    private Double order_discount;
    private Double order_amount_payable;
    private String order_explain;
    private Long short_or_long;
    private String short_or_long_name;
    private Long business_type;
    private String business_type_name;
    private Long bu_id;
    private String bu_name;
    private Long city_id;
    private String city_name;
    private String task_flow;
    private Long fee_type;
    private String fee_type_name;
    private Long operate_no;
    private String order_rule_name;
    private String course_rule_name;
    private Double settlement_amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getBusiness_date() {
        return business_date;
    }

    public void setBusiness_date(String business_date) {
        this.business_date = business_date;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Long getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(Long operate_type) {
        this.operate_type = operate_type;
    }

    public String getOperate_type_name() {
        return operate_type_name;
    }

    public void setOperate_type_name(String operate_type_name) {
        this.operate_type_name = operate_type_name;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getCard() {
        return card;
    }

    public void setCard(Double card) {
        this.card = card;
    }

    public Double getTransfer() {
        return transfer;
    }

    public void setTransfer(Double transfer) {
        this.transfer = transfer;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Double getFrozen_account() {
        return frozen_account;
    }

    public void setFrozen_account(Double frozen_account) {
        this.frozen_account = frozen_account;
    }

    public Double getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(Double refund_account) {
        this.refund_account = refund_account;
    }

    public Double getTransfer_out() {
        return transfer_out;
    }

    public void setTransfer_out(Double transfer_out) {
        this.transfer_out = transfer_out;
    }

    public Double getTransfer_in() {
        return transfer_in;
    }

    public void setTransfer_in(Double transfer_in) {
        this.transfer_in = transfer_in;
    }

    public Double getCancel_order_amount() {
        return cancel_order_amount;
    }

    public void setCancel_order_amount(Double cancel_order_amount) {
        this.cancel_order_amount = cancel_order_amount;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Double getCancel_refund_amount() {
        return cancel_refund_amount;
    }

    public void setCancel_refund_amount(Double cancel_refund_amount) {
        this.cancel_refund_amount = cancel_refund_amount;
    }

    public Double getClaim() {
        return claim;
    }

    public void setClaim(Double claim) {
        this.claim = claim;
    }

    public Long getCourse_time() {
        return course_time;
    }

    public void setCourse_time(Long course_time) {
        this.course_time = course_time;
    }

    public String getCustomer_account() {
        return customer_account;
    }

    public void setCustomer_account(String customer_account) {
        this.customer_account = customer_account;
    }

    public String getCompany_account_name() {
        return company_account_name;
    }

    public void setCompany_account_name(String company_account_name) {
        this.company_account_name = company_account_name;
    }

    public String getCompany_account() {
        return company_account;
    }

    public void setCompany_account(String company_account) {
        this.company_account = company_account;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Long getIs_textbooks() {
        return is_textbooks;
    }

    public void setIs_textbooks(Long is_textbooks) {
        this.is_textbooks = is_textbooks;
    }

    public String getIs_textbooks_name() {
        return is_textbooks_name;
    }

    public void setIs_textbooks_name(String is_textbooks_name) {
        this.is_textbooks_name = is_textbooks_name;
    }

    public Long getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Long grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_encoding() {
        return teacher_encoding;
    }

    public void setTeacher_encoding(String teacher_encoding) {
        this.teacher_encoding = teacher_encoding;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Long getAssteacher_id() {
		return assteacher_id;
	}

	public void setAssteacher_id(Long assteacher_id) {
		this.assteacher_id = assteacher_id;
	}

	public String getAssteacher_encoding() {
		return assteacher_encoding;
	}

	public void setAssteacher_encoding(String assteacher_encoding) {
		this.assteacher_encoding = assteacher_encoding;
	}

	public String getAssteacher_name() {
		return assteacher_name;
	}

	public void setAssteacher_name(String assteacher_name) {
		this.assteacher_name = assteacher_name;
	}

	public Long getPerformance_branch_id() {
        return performance_branch_id;
    }

    public void setPerformance_branch_id(Long performance_branch_id) {
        this.performance_branch_id = performance_branch_id;
    }

    public String getPerformance_branch_name() {
        return performance_branch_name;
    }

    public void setPerformance_branch_name(String performance_branch_name) {
        this.performance_branch_name = performance_branch_name;
    }

    public Long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(Long season_id) {
        this.season_id = season_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Long agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public Long getOriginal_create_user() {
        return original_create_user;
    }

    public void setOriginal_create_user(Long original_create_user) {
        this.original_create_user = original_create_user;
    }

    public String getOriginal_create_user_name() {
        return original_create_user_name;
    }

    public void setOriginal_create_user_name(String original_create_user_name) {
        this.original_create_user_name = original_create_user_name;
    }

    public Long getIs_new_continued() {
        return is_new_continued;
    }

    public void setIs_new_continued(Long is_new_continued) {
        this.is_new_continued = is_new_continued;
    }

    public String getIs_new_continued_name() {
        return is_new_continued_name;
    }

    public void setIs_new_continued_name(String is_new_continued_name) {
        this.is_new_continued_name = is_new_continued_name;
    }

    public Double getOrder_discount() {
        return order_discount;
    }

    public void setOrder_discount(Double order_discount) {
        this.order_discount = order_discount;
    }

    public Double getOrder_amount_payable() {
        return order_amount_payable;
    }

    public void setOrder_amount_payable(Double order_amount_payable) {
        this.order_amount_payable = order_amount_payable;
    }

    public String getOrder_explain() {
        return order_explain;
    }

    public void setOrder_explain(String order_explain) {
        this.order_explain = order_explain;
    }

    public Long getShort_or_long() {
        return short_or_long;
    }

    public void setShort_or_long(Long short_or_long) {
        this.short_or_long = short_or_long;
    }

    public String getShort_or_long_name() {
        return short_or_long_name;
    }

    public void setShort_or_long_name(String short_or_long_name) {
        this.short_or_long_name = short_or_long_name;
    }

    public Long getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(Long business_type) {
        this.business_type = business_type;
    }

    public String getBusiness_type_name() {
        return business_type_name;
    }

    public void setBusiness_type_name(String business_type_name) {
        this.business_type_name = business_type_name;
    }

    public Long getBu_id() {
        return bu_id;
    }

    public void setBu_id(Long bu_id) {
        this.bu_id = bu_id;
    }

    public String getBu_name() {
        return bu_name;
    }

    public void setBu_name(String bu_name) {
        this.bu_name = bu_name;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTask_flow() {
        return task_flow;
    }

    public void setTask_flow(String task_flow) {
        this.task_flow = task_flow;
    }

    public Long getFee_type() {
        return fee_type;
    }

    public void setFee_type(Long fee_type) {
        this.fee_type = fee_type;
    }

    public String getFee_type_name() {
        return fee_type_name;
    }

    public void setFee_type_name(String fee_type_name) {
        this.fee_type_name = fee_type_name;
    }

    public Long getOperate_no() {
        return operate_no;
    }

    public void setOperate_no(Long operate_no) {
        this.operate_no = operate_no;
    }

	public Double getEb_price() {
		return eb_price;
	}

	public void setEb_price(Double eb_price) {
		this.eb_price = eb_price;
	}

    public Double getOnline_price() {
        return online_price;
    }

    public void setOnline_price(Double online_price) {
        this.online_price = online_price;
    }

    public String getOrder_rule_name() {return order_rule_name;}

    public void setOrder_rule_name(String order_rule_name) {this.order_rule_name = order_rule_name;}

    public String getCourse_rule_name() {return course_rule_name;}

    public void setCourse_rule_name(String course_rule_name) {this.course_rule_name = course_rule_name;}

    public Double getSettlement_amount() { return settlement_amount; }

    public void setSettlement_amount(Double settlement_amount) { this.settlement_amount = settlement_amount; }
}
