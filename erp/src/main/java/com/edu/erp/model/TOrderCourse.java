package com.edu.erp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单课程表
 * 
 * @author wCong
 * 
 */
public class TOrderCourse extends BaseObject {
    private static final long serialVersionUID = -7847011407416330390L;

    // 订单主键
    private Long order_id;
    // 课程ID
    private Long course_id;
    // 课程所属校区
    private Long branch_id;
    // 原单价
    private Double former_unit_price;
    // 原总价
    private Double former_sum_price;
    // 折后单价
    private Double discount_unit_price;
    // 折后总价
    private Double discount_sum_price;
    // 折扣
    private Double discount_rate;
    //优惠立减
    private Double immediate_reduce;
    // 折扣金额
    private Double discount_amount;
    // 预结转
    private Double manage_fee;
    // 课程（课时）总次数
    private Long course_total_count;
    // 剩余可用课时
    private Long course_surplus_count;
    // 剩余可排课时
    private Long course_schedule_count;
    // 剩余费用
    private Double surplus_cost;
    // 退费标识
    private Long quit_flag;
    // 订单类型
    private Long order_type;

    private String old_id;

    private String order_no;

    private String branch_name;

    private String course_name;

    private String course_no;

    private String teacher_name;

    private String teacher_encoding;

    private Long root_course_id;

    private String grade_name;

    private String start_date;

    private String end_date;

    private String remark;

    private Long student_id;

    private Date order_create_date;

    private Long course_attend_count;
    
    private Double hour_len;  // 课时长度

    private TCourse course;
    
    private Long invoice_status;  //发票状态
    
    private Long season_id; //课程季
    
    private Long subject_id; //科目

    private List<TAttendance> tAttendanceList = new ArrayList<TAttendance>();

    private List<TAttendanceHt> attHist = new ArrayList<TAttendanceHt>();

    private List<TCOrderCourse> orderCourseChange = new ArrayList<TCOrderCourse>();
    
    private String premium_type;
    
    private Double root_discount_sum_price;
    
    private Long root_course_total_count;

    public Long getOrder_id() {
        return order_id;
    }

    public Double getImmediate_reduce() {
		return immediate_reduce;
	}

	public void setImmediate_reduce(Double immediate_reduce) {
		this.immediate_reduce = immediate_reduce;
	}

	public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public Double getFormer_unit_price() {
        return former_unit_price;
    }

    public void setFormer_unit_price(Double former_unit_price) {
        this.former_unit_price = former_unit_price;
    }

    public Double getFormer_sum_price() {
        return former_sum_price;
    }

    public void setFormer_sum_price(Double former_sum_price) {
        this.former_sum_price = former_sum_price;
    }

    public Double getDiscount_unit_price() {
        return discount_unit_price;
    }

    public void setDiscount_unit_price(Double discount_unit_price) {
        this.discount_unit_price = discount_unit_price;
    }

    public Double getDiscount_sum_price() {
        return discount_sum_price;
    }

    public void setDiscount_sum_price(Double discount_sum_price) {
        this.discount_sum_price = discount_sum_price;
    }

    public Double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public Double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(Double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public Double getManage_fee() {
        return manage_fee;
    }

    public void setManage_fee(Double manage_fee) {
        this.manage_fee = manage_fee;
    }

    public Long getCourse_total_count() {
        return course_total_count;
    }

    public void setCourse_total_count(Long course_total_count) {
        this.course_total_count = course_total_count;
    }

    public Long getCourse_surplus_count() {
        return course_surplus_count;
    }

    public void setCourse_surplus_count(Long course_surplus_count) {
        this.course_surplus_count = course_surplus_count;
    }

    public Double getSurplus_cost() {
        return surplus_cost;
    }

    public void setSurplus_cost(Double surplus_cost) {
        this.surplus_cost = surplus_cost;
    }

    public Long getQuit_flag() {
        return quit_flag;
    }

    public void setQuit_flag(Long quit_flag) {
        this.quit_flag = quit_flag;
    }

    public Long getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Long order_type) {
        this.order_type = order_type;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public TCourse getCourse() {
        return course;
    }

    public void setCourse(TCourse course) {
        this.course = course;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public final List<TAttendance> gettAttendanceList() {
        return tAttendanceList;
    }

    public final void settAttendanceList(List<TAttendance> tAttendanceList) {
        this.tAttendanceList = tAttendanceList;
    }

    public final List<TAttendanceHt> getAttHist() {
        return attHist;
    }

    public final void setAttHist(List<TAttendanceHt> attHist) {
        this.attHist = attHist;
    }

    public final List<TCOrderCourse> getOrderCourseChange() {
        return orderCourseChange;
    }

    public final void setOrderCourseChange(List<TCOrderCourse> orderCourseChange) {
        this.orderCourseChange = orderCourseChange;
    }

    public final Long getRoot_course_id() {
        return root_course_id;
    }

    public final void setRoot_course_id(Long root_course_id) {
        this.root_course_id = root_course_id;
    }

    public final String getCourse_no() {
        return course_no;
    }

    public final void setCourse_no(String course_no) {
        this.course_no = course_no;
    }

    public final String getTeacher_name() {
        return teacher_name;
    }

    public final void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public final String getTeacher_encoding() {
        return teacher_encoding;
    }

    public final void setTeacher_encoding(String teacher_encoding) {
        this.teacher_encoding = teacher_encoding;
    }

    public final String getOrder_no() {
        return order_no;
    }

    public final void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOld_id() {
        return old_id;
    }

    public void setOld_id(String old_id) {
        this.old_id = old_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCourse_schedule_count() {
        return course_schedule_count;
    }

    public void setCourse_schedule_count(Long course_schedule_count) {
        this.course_schedule_count = course_schedule_count;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Date getOrder_create_date() {
        return order_create_date;
    }

    public void setOrder_create_date(Date order_create_date) {
        this.order_create_date = order_create_date;
    }

    public Long getCourse_attend_count() {
        return course_attend_count;
    }

    public void setCourse_attend_count(Long course_attend_count) {
        this.course_attend_count = course_attend_count;
    }

	public Long getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(Long invoice_status) {
		this.invoice_status = invoice_status;
	}

	public Long getSeason_id() {
		return season_id;
	}

	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public String getPremium_type() {
		return premium_type;
	}

	public void setPremium_type(String premium_type) {
		this.premium_type = premium_type;
	}

	public Double getRoot_discount_sum_price() {
		return root_discount_sum_price;
	}

	public void setRoot_discount_sum_price(Double root_discount_sum_price) {
		this.root_discount_sum_price = root_discount_sum_price;
	}

	public Long getRoot_course_total_count() {
		return root_course_total_count;
	}

	public void setRoot_course_total_count(Long root_course_total_count) {
		this.root_course_total_count = root_course_total_count;
	}

	public Double getHour_len() {
		return hour_len;
	}

	public void setHour_len(Double hour_len) {
		this.hour_len = hour_len;
	}
	
}
