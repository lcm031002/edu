package com.edu.report.model;

import java.math.BigDecimal;

/**
 * @ClassName: TBusinessAttendanceMonthReport
 * @Description: 月度分类考勤表
 *
 */
public class TBusinessAttendanceMonthReport {
	
	private String task_flow; 				//任务批次

	private Long id;						//主键ID
	
	private Long student_id;				//学生ID
	
	private String student_code;			//学生编号
	
	private String student_name;			//学生姓名
	
	private Long bu_id; 					//业务团队
	
	private String bu_name; 				//业务团队名称
	
	private BigDecimal sum_amount;			//合计金额
	
	private Long sum_count;					//合计课时
    
    private BigDecimal bjk_amount;			//大小班金额
    
	private Long bjk_count;					//大小班课时
    
    private BigDecimal ydy_amount;			//1对1金额
    
    private Long ydy_count;					//1对1课时
    
    private BigDecimal wfd_amount;			//晚辅导金额
    
	private Long wfd_count;					//晚辅导课时
    
    private String busi_date;				//业务日期

	public String getTask_flow() {
		return task_flow;
	}

	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
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

	public BigDecimal getSum_amount() {
		return sum_amount;
	}

	public void setSum_amount(BigDecimal sum_amount) {
		this.sum_amount = sum_amount;
	}

	public Long getSum_count() {
		return sum_count;
	}

	public void setSum_count(Long sum_count) {
		this.sum_count = sum_count;
	}

	public BigDecimal getBjk_amount() {
		return bjk_amount;
	}

	public void setBjk_amount(BigDecimal bjk_amount) {
		this.bjk_amount = bjk_amount;
	}

	public Long getBjk_count() {
		return bjk_count;
	}

	public void setBjk_count(Long bjk_count) {
		this.bjk_count = bjk_count;
	}

	public BigDecimal getYdy_amount() {
		return ydy_amount;
	}

	public void setYdy_amount(BigDecimal ydy_amount) {
		this.ydy_amount = ydy_amount;
	}

	public Long getYdy_count() {
		return ydy_count;
	}

	public void setYdy_count(Long ydy_count) {
		this.ydy_count = ydy_count;
	}

	public BigDecimal getWfd_amount() {
		return wfd_amount;
	}

	public void setWfd_amount(BigDecimal wfd_amount) {
		this.wfd_amount = wfd_amount;
	}

	public Long getWfd_count() {
		return wfd_count;
	}

	public void setWfd_count(Long wfd_count) {
		this.wfd_count = wfd_count;
	}

	public String getBusi_date() {
		return busi_date;
	}

	public void setBusi_date(String busi_date) {
		this.busi_date = busi_date;
	}

}
