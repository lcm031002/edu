package com.edu.erp.model;

import java.io.Serializable;

/**
 * @ClassName: TCourseListening
 * @Description: 试听实体对象
 *
 */
public class TCourseListening implements Serializable, Cloneable{

	private static final long serialVersionUID = 7662034417106005304L;

	private Long id;
	
	private Long branch_id;
	
	private Long student_id;
	
	private Long course_id;
	
	private String course_times;
	
	private String listening_date;
	
	private Long pay_status;
	
	private Long recorder;
	
	private String record_time;
	
	private Long updater;
	
	private String update_time;
	
	private String remark;
	
	/** 以下非DB字段 */
	private String branch_name;
	
	private String student_code;
	
	private String student_name;
	
	private String course_name;
	
	private Long teacher_id;
	
	private String teacher_name;
	
	private Double unit_price;
	
	private String pay_status_name;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getRecorder() {
		return recorder;
	}

	public final void setRecorder(Long recorder) {
		this.recorder = recorder;
	}

	public final Long getUpdater() {
		return updater;
	}

	public final void setUpdater(Long updater) {
		this.updater = updater;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public String getCourse_times() {
		return course_times;
	}

	public void setCourse_times(String course_times) {
		this.course_times = course_times;
	}

	public String getListening_date() {
		return listening_date;
	}

	public void setListening_date(String listening_date) {
		this.listening_date = listening_date;
	}

	public Long getPay_status() {
		return pay_status;
	}

	public void setPay_status(Long pay_status) {
		this.pay_status = pay_status;
	}

	public String getRecord_time() {
		return record_time;
	}

	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
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

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public String getPay_status_name() {
		return pay_status_name;
	}

	public void setPay_status_name(String pay_status_name) {
		this.pay_status_name = pay_status_name;
	}

	public String getUpdate_time() {
		return update_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch_id == null) ? 0 : branch_id.hashCode());
		result = prime * result + ((branch_name == null) ? 0 : branch_name.hashCode());
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_times == null) ? 0 : course_times.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listening_date == null) ? 0 : listening_date.hashCode());
		result = prime * result + ((pay_status == null) ? 0 : pay_status.hashCode());
		result = prime * result + ((pay_status_name == null) ? 0 : pay_status_name.hashCode());
		result = prime * result + ((record_time == null) ? 0 : record_time.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((student_code == null) ? 0 : student_code.hashCode());
		result = prime * result + ((student_id == null) ? 0 : student_id.hashCode());
		result = prime * result + ((student_name == null) ? 0 : student_name.hashCode());
		result = prime * result + ((teacher_id == null) ? 0 : teacher_id.hashCode());
		result = prime * result + ((teacher_name == null) ? 0 : teacher_name.hashCode());
		result = prime * result + ((unit_price == null) ? 0 : unit_price.hashCode());
		result = prime * result + ((update_time == null) ? 0 : update_time.hashCode());
		result = prime * result + ((updater == null) ? 0 : updater.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TCourseListening other = (TCourseListening) obj;
		if (branch_id == null) {
			if (other.branch_id != null)
				return false;
		} else if (!branch_id.equals(other.branch_id))
			return false;
		if (branch_name == null) {
			if (other.branch_name != null)
				return false;
		} else if (!branch_name.equals(other.branch_name))
			return false;
		if (course_id == null) {
			if (other.course_id != null)
				return false;
		} else if (!course_id.equals(other.course_id))
			return false;
		if (course_name == null) {
			if (other.course_name != null)
				return false;
		} else if (!course_name.equals(other.course_name))
			return false;
		if (course_times == null) {
			if (other.course_times != null)
				return false;
		} else if (!course_times.equals(other.course_times))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listening_date == null) {
			if (other.listening_date != null)
				return false;
		} else if (!listening_date.equals(other.listening_date))
			return false;
		if (pay_status == null) {
			if (other.pay_status != null)
				return false;
		} else if (!pay_status.equals(other.pay_status))
			return false;
		if (pay_status_name == null) {
			if (other.pay_status_name != null)
				return false;
		} else if (!pay_status_name.equals(other.pay_status_name))
			return false;
		if (record_time == null) {
			if (other.record_time != null)
				return false;
		} else if (!record_time.equals(other.record_time))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (student_code == null) {
			if (other.student_code != null)
				return false;
		} else if (!student_code.equals(other.student_code))
			return false;
		if (student_id == null) {
			if (other.student_id != null)
				return false;
		} else if (!student_id.equals(other.student_id))
			return false;
		if (student_name == null) {
			if (other.student_name != null)
				return false;
		} else if (!student_name.equals(other.student_name))
			return false;
		if (teacher_id == null) {
			if (other.teacher_id != null)
				return false;
		} else if (!teacher_id.equals(other.teacher_id))
			return false;
		if (teacher_name == null) {
			if (other.teacher_name != null)
				return false;
		} else if (!teacher_name.equals(other.teacher_name))
			return false;
		if (unit_price == null) {
			if (other.unit_price != null)
				return false;
		} else if (!unit_price.equals(other.unit_price))
			return false;
		if (update_time == null) {
			if (other.update_time != null)
				return false;
		} else if (!update_time.equals(other.update_time))
			return false;
		if (updater == null) {
			if (other.updater != null)
				return false;
		} else if (!updater.equals(other.updater))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TCourseListening [id=" + id + ", branch_id=" + branch_id + ", student_id=" + student_id + ", course_id="
				+ course_id + ", course_times=" + course_times + ", listening_date=" + listening_date + ", pay_status="
				+ pay_status + ", recorder=" + recorder + ", record_time=" + record_time + ", updater=" + updater
				+ ", update_time=" + update_time + ", remark=" + remark + ", branch_name=" + branch_name
				+ ", student_code=" + student_code + ", student_name=" + student_name + ", course_name=" + course_name
				+ ", teacher_id=" + teacher_id + ", teacher_name=" + teacher_name + ", unit_price=" + unit_price
				+ ", pay_status_name=" + pay_status_name + "]";
	}

}
