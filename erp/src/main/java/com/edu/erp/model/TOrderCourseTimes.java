package com.edu.erp.model;

import java.io.Serializable;

/**
 * @ClassName: TOrderCourseTimes
 * @Description: 课次信息
 *
 */
public class TOrderCourseTimes implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -5990920239201991740L;

	private Long id;

    private Long ocid;  // 订单课程ID
  		
    private Long times;  // 课次
    
    private Long is_valid;//有效状态
    
    private Long old_id;//V3id
    
	public final Long getOcid() {
		return ocid;
	}

	public final void setOcid(Long ocid) {
		this.ocid = ocid;
	}

	public final Long getTimes() {
		return times;
	}

	public final void setTimes(Long times) {
		this.times = times;
	}

	public final Long getIs_valid() {
		return is_valid;
	}

	public final void setIs_valid(Long is_valid) {
		this.is_valid = is_valid;
	}

	public final Long getOld_id() {
		return old_id;
	}

	public final void setOld_id(Long old_id) {
		this.old_id = old_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
