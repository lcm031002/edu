/**  
 * @Title: TPerformanceSum.java
 * @Package com.edu.report.model
 * @author Au yeung ohs@klxuexi.org  
 * @date 2017年5月10日 下午4:42:40
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

/**
 * @ClassName: TDailySummaryPerformance
 * @Description: 业绩总表
 * @author Au yeung ohs@klxuexi.org  
 * @date 2017年5月10日 下午4:42:40
 *
 */
public class TDailySummaryPerformance {
	
	private Long  	bu_id;
	private Long  	branch_id;
	private String  courseasonname;
	private String  datetime;
	private Long 	business1;
	private Long  	business2;
	private Long  	business3;
	private Long  	claimamount;
	private Long  	allamount;
	private Long  	id;
	private String  task_flow;
	
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
	public String getCourseasonname() {
		return courseasonname;
	}
	public void setCourseasonname(String courseasonname) {
		this.courseasonname = courseasonname;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Long getBusiness1() {
		return business1;
	}
	public void setBusiness1(Long business1) {
		this.business1 = business1;
	}
	public Long getBusiness2() {
		return business2;
	}
	public void setBusiness2(Long business2) {
		this.business2 = business2;
	}
	public Long getBusiness3() {
		return business3;
	}
	public void setBusiness3(Long business3) {
		this.business3 = business3;
	}
	public Long getClaimamount() {
		return claimamount;
	}
	public void setClaimamount(Long claimamount) {
		this.claimamount = claimamount;
	}
	public Long getAllamount() {
		return allamount;
	}
	public void setAllamount(Long allamount) {
		this.allamount = allamount;
	}
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
	
}
