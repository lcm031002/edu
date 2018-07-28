package com.edu.erp.attendance.business;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: RequestParam
 * @Description: 请求参数
 *
 */
public class RequestParam implements Serializable{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -897968237766210588L;
	
	private List<AttendanceBusiness> submitAttendanceList;

	public final List<AttendanceBusiness> getSubmitAttendanceList() {
		return submitAttendanceList;
	}

	public final void setSubmitAttendanceList(
			List<AttendanceBusiness> submitAttendanceList) {
		this.submitAttendanceList = submitAttendanceList;
	}

}
