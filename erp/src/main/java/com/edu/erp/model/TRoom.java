package com.edu.erp.model;

/**
 * 教室模型
 *
 */
public class TRoom extends BaseObject {

	private static final long serialVersionUID = 1L;

	// 教室名称
	private String room_name;
	// 教室编码
	private String room_code;
	// 所属团队
	private Long bu_id;
	// 所属校区
	private Long branch_id;
	// 所属校区名称
	private String branch_name;
	// 教室人数
	private Long room_num;
	// 备注
	private String remark;

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
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

	public Long getRoom_num() {
		return room_num;
	}

	public void setRoom_num(Long room_num) {
		this.room_num = room_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}
