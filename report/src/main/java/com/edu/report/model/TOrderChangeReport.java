/**  
 * @Title: TPerformanceSum.java
 * @Package com.edu.report.model
 * @author Au yeung ohs@klxuexi.org  
 * @date 2017年5月10日 下午4:42:40
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

/**
 * @ClassName: TOrderChangeReport
 * @Description: 报退冻转
 * @author Au yeung ohs@klxuexi.org  
 * @date 2017年5月10日 下午4:42:40
 *
 */
public class TOrderChangeReport {
	
	private Long id;
	private String task_flow;
	private String bus_date;
	private String order_encoding;
	private String student_encoding;
	private String grade_name;
	private String student_name;
	private String branch_name;
	private String course_branch_name;
	private String course_name;
	private Long order_detail_id;
	private Double baoban;
	private Double bao;
	private Double baobanyujiezhuan;
	private Double yujiezhuan;
	private Double tui;
	private Double bukou;
	private Double tuikeshi;
	private Double zhuanchu;
	private Double zhuanchukeshi;
	private Double zhuanru;
	private Double zhuanrukeshi;
	private Double bujiezhuan;
	private String order_status;
	private String bus_type;
	private String subject_name;
	private String is_textbooks;
	private String short_or_long;
	private Long business_type;
	private Long bu_id;
	private Long branch_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTask_flow() {
		return task_flow;
	}
	public void setTask_flow(String task_flow) {
		this.task_flow = task_flow;
	}
	public String getBus_date() {
		return bus_date;
	}
	public void setBus_date(String bus_date) {
		this.bus_date = bus_date;
	}
	public String getOrder_encoding() {
		return order_encoding;
	}
	public void setOrder_encoding(String order_encoding) {
		this.order_encoding = order_encoding;
	}
	public String getStudent_encoding() {
		return student_encoding;
	}
	public void setStudent_encoding(String student_encoding) {
		this.student_encoding = student_encoding;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getCourse_branch_name() {
		return course_branch_name;
	}
	public void setCourse_branch_name(String course_branch_name) {
		this.course_branch_name = course_branch_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Long getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public Double getBaoban() {
		return baoban;
	}
	public void setBaoban(Double baoban) {
		this.baoban = baoban;
	}
	public Double getBao() {
		return bao;
	}
	public void setBao(Double bao) {
		this.bao = bao;
	}
	public Double getBaobanyujiezhuan() {
		return baobanyujiezhuan;
	}
	public void setBaobanyujiezhuan(Double baobanyujiezhuan) {
		this.baobanyujiezhuan = baobanyujiezhuan;
	}
	public Double getYujiezhuan() {
		return yujiezhuan;
	}
	public void setYujiezhuan(Double yujiezhuan) {
		this.yujiezhuan = yujiezhuan;
	}
	public Double getTui() {
		return tui;
	}
	public void setTui(Double tui) {
		this.tui = tui;
	}
	public Double getBukou() {
		return bukou;
	}
	public void setBukou(Double bukou) {
		this.bukou = bukou;
	}
	public Double getTuikeshi() {
		return tuikeshi;
	}
	public void setTuikeshi(Double tuikeshi) {
		this.tuikeshi = tuikeshi;
	}
	public Double getZhuanchu() {
		return zhuanchu;
	}
	public void setZhuanchu(Double zhuanchu) {
		this.zhuanchu = zhuanchu;
	}
	public Double getZhuanchukeshi() {
		return zhuanchukeshi;
	}
	public void setZhuanchukeshi(Double zhuanchukeshi) {
		this.zhuanchukeshi = zhuanchukeshi;
	}
	public Double getZhuanru() {
		return zhuanru;
	}
	public void setZhuanru(Double zhuanru) {
		this.zhuanru = zhuanru;
	}
	public Double getZhuanrukeshi() {
		return zhuanrukeshi;
	}
	public void setZhuanrukeshi(Double zhuanrukeshi) {
		this.zhuanrukeshi = zhuanrukeshi;
	}
	public Double getBujiezhuan() {
		return bujiezhuan;
	}
	public void setBujiezhuan(Double bujiezhuan) {
		this.bujiezhuan = bujiezhuan;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getBus_type() {
		return bus_type;
	}
	public void setBus_type(String bus_type) {
		this.bus_type = bus_type;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getIs_textbooks() {
		return is_textbooks;
	}
	public void setIs_textbooks(String is_textbooks) {
		this.is_textbooks = is_textbooks;
	}
	public String getShort_or_long() {
		return short_or_long;
	}
	public void setShort_or_long(String short_or_long) {
		this.short_or_long = short_or_long;
	}
	public Long getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}
	public Long getBu_id() {
		return bu_id;
	}
	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	
	
}
