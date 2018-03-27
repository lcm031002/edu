/**  
 * @Title: StudentRel.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月8日 下午6:07:28
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

/**
 * @ClassName: StudentRel
 * @Description: 学员关系
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月8日 下午6:07:28
 *
 */
public class StudentRel extends BaseObject{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -2532436578627163852L;

	private Long id;
	
	private Long studentIdNew;
	
	private Long studentIdOld;
	
	private Long newUsed;
	
	private Long oldUsed;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getStudentIdNew() {
		return studentIdNew;
	}

	public final void setStudentIdNew(Long studentIdNew) {
		this.studentIdNew = studentIdNew;
	}

	public final Long getStudentIdOld() {
		return studentIdOld;
	}

	public final void setStudentIdOld(Long studentIdOld) {
		this.studentIdOld = studentIdOld;
	}

	public final Long getNewUsed() {
		return newUsed;
	}

	public final void setNewUsed(Long newUsed) {
		this.newUsed = newUsed;
	}

	public final Long getOldUsed() {
		return oldUsed;
	}

	public final void setOldUsed(Long oldUsed) {
		this.oldUsed = oldUsed;
	}
	
	
}
