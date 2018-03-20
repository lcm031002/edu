/**  
 * @Title: ExtTaskVariable.java
 * @Package com.ebusiness.workflow.modules.index.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月15日 下午4:42:35
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.workflow.modules.index.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ExtTaskVariable
 * @Description: 任务参数对象
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月15日 下午4:42:35
 *
 */
public class ExtTaskVariable   implements Serializable  {
	private Long dbid_;
	private String class_;
	private Long dbversion_;
	private String key_;
	private String converter_;
	private Long hist_;
	private Long execution_;
	private Long task_;
	private Long lob_;
	private Date date_value_; 
	private Double double_value_;
	private String classname_;
	private Long long_value_;
	private String string_value_;
	private String text_value_;
	private Long exesys_;
	public final Long getDbid_() {
		return dbid_;
	}
	public final void setDbid_(Long dbid_) {
		this.dbid_ = dbid_;
	}
	public final String getClass_() {
		return class_;
	}
	public final void setClass_(String class_) {
		this.class_ = class_;
	}
	public final Long getDbversion_() {
		return dbversion_;
	}
	public final void setDbversion_(Long dbversion_) {
		this.dbversion_ = dbversion_;
	}
	public final String getKey_() {
		return key_;
	}
	public final void setKey_(String key_) {
		this.key_ = key_;
	}
	public final String getConverter_() {
		return converter_;
	}
	public final void setConverter_(String converter_) {
		this.converter_ = converter_;
	}
	public final Long getHist_() {
		return hist_;
	}
	public final void setHist_(Long hist_) {
		this.hist_ = hist_;
	}
	public final Long getExecution_() {
		return execution_;
	}
	public final void setExecution_(Long execution_) {
		this.execution_ = execution_;
	}
	public final Long getTask_() {
		return task_;
	}
	public final void setTask_(Long task_) {
		this.task_ = task_;
	}
	public final Long getLob_() {
		return lob_;
	}
	public final void setLob_(Long lob_) {
		this.lob_ = lob_;
	}
	public final Date getDate_value_() {
		return date_value_;
	}
	public final void setDate_value_(Date date_value_) {
		this.date_value_ = date_value_;
	}
	public final Double getDouble_value_() {
		return double_value_;
	}
	public final void setDouble_value_(Double double_value_) {
		this.double_value_ = double_value_;
	}
	public final String getClassname_() {
		return classname_;
	}
	public final void setClassname_(String classname_) {
		this.classname_ = classname_;
	}
	
	public final Long getLong_value_() {
		return long_value_;
	}
	public final void setLong_value_(Long long_value_) {
		this.long_value_ = long_value_;
	}
	public final String getString_value_() {
		return string_value_;
	}
	public final void setString_value_(String string_value_) {
		this.string_value_ = string_value_;
	}
	public final String getText_value_() {
		return text_value_;
	}
	public final void setText_value_(String text_value_) {
		this.text_value_ = text_value_;
	}
	public final Long getExesys_() {
		return exesys_;
	}
	public final void setExesys_(Long exesys_) {
		this.exesys_ = exesys_;
	}
	
	
}
