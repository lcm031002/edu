/**  
 * @Title: TOrderPerformance.java
 * @Package com.edu.report.model
 * @author ohs ohs@klxuexi.org  
 * @date 2017年8月24日 下午4:42:40
 * @version KLXX ERPV4.0  
 */
package com.edu.report.model;

/**
 * @ClassName: TBusinessStatistics
 * @Description: 运营统计指标
 * @author ohs ohs@klxuexi.org
 * @date 2017年10月20日 下午4:42:40
 *
 */
public class TBusinessStatistics {
    private Long id;
	private Long performance_branch_id;
	private String performance_branch_name;
	private Long grade_id;
	private String grade_name;
	private Long subject_id;
	private String subject_name;


	private Double performance;
	private Long people;
	private Long subject;
	private Double xb_performance;
	private Long xb_people;
	private Long xb_subject;
	private Double xs_performance;
	private Long xs_people;
	private Long xs_subject;
	private Double cs_performance;
	private Long cs_people;
	private Long cs_subject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public Long getPeople() {
		return people;
	}

	public void setPeople(Long people) {
		this.people = people;
	}

	public Long getSubject() {
		return subject;
	}

	public void setSubject(Long subject) {
		this.subject = subject;
	}

	public Double getXb_performance() {
		return xb_performance;
	}

	public void setXb_performance(Double xb_performance) {
		this.xb_performance = xb_performance;
	}

	public Long getXb_people() {
		return xb_people;
	}

	public void setXb_people(Long xb_people) {
		this.xb_people = xb_people;
	}

	public Long getXb_subject() {
		return xb_subject;
	}

	public void setXb_subject(Long xb_subject) {
		this.xb_subject = xb_subject;
	}

	public Double getXs_performance() {
		return xs_performance;
	}

	public void setXs_performance(Double xs_performance) {
		this.xs_performance = xs_performance;
	}

	public Long getXs_people() {
		return xs_people;
	}

	public void setXs_people(Long xs_people) {
		this.xs_people = xs_people;
	}

	public Long getXs_subject() {
		return xs_subject;
	}

	public void setXs_subject(Long xs_subject) {
		this.xs_subject = xs_subject;
	}

	public Double getCs_performance() {
		return cs_performance;
	}

	public void setCs_performance(Double cs_performance) {
		this.cs_performance = cs_performance;
	}

	public Long getCs_people() {
		return cs_people;
	}

	public void setCs_people(Long cs_people) {
		this.cs_people = cs_people;
	}

	public Long getCs_subject() {
		return cs_subject;
	}

	public void setCs_subject(Long cs_subject) {
		this.cs_subject = cs_subject;
	}
}
