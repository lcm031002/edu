package com.edu.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 推荐人
 * @author zenglw
 * @Timestamp 20170726
 */
public class ReferenceStudent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private Long id;
	 private Long student_id;
	 private Long reference_student_id;
	 private String reference_student_name;
	 private Long create_user;
	 private String operator;
	 private Timestamp create_time;
	 
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
	public Long getReference_student_id() {
		return reference_student_id;
	}
	public void setReference_student_id(Long reference_student_id) {
		this.reference_student_id = reference_student_id;
	}
	public Long getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getReference_student_name() {
		return reference_student_name;
	}
	public void setReference_student_name(String reference_student_name) {
		this.reference_student_name = reference_student_name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
