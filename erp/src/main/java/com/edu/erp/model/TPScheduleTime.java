package com.edu.erp.model;


/**
 * 排课档期表
 *
 */
public class TPScheduleTime extends BaseObject{

	private static final long serialVersionUID = -443749987559427133L;;
	// 名称
	private String name;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	// 校区
	private Long branchId;
	// 团队
	private Long buId;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getBuId() {
		return buId;
	}

	public void setBuId(Long buId) {
		this.buId = buId;
	}
}
