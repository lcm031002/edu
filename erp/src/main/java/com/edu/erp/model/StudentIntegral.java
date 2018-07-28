package com.edu.erp.model;


/**
 * @ClassName: StudentIntegral
 * @Description: 学生积分账户
 *
 */
public class StudentIntegral extends BaseObject {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 3067713693184768121L;

	private Long student_id;

	private Long crrent_integral;

	private Long last_integral;
	
	private Long attend_amount;
	
	private Long last_attend_amount;
	
	private Long branch_id;
	
	/******非DB字段********/
	private String branch_name; //校区名称

	public Long getCrrent_integral() {
		return crrent_integral;
	}

	public Long getLast_integral() {
		return last_integral;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setCrrent_integral(Long crrent_integral) {
		this.crrent_integral = crrent_integral;
	}

	public void setLast_integral(Long last_integral) {
		this.last_integral = last_integral;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public final Long getAttend_amount() {
		return attend_amount;
	}

	public final void setAttend_amount(Long attend_amount) {
		this.attend_amount = attend_amount;
	}
	
	public final Long getLast_attend_amount() {
		return last_attend_amount;
	}

	public final void setLast_attend_amount(Long last_attend_amount) {
		this.last_attend_amount = last_attend_amount;
	}
	
	public final Long getBranch_id() {
		return branch_id;
	}

	public final void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}
