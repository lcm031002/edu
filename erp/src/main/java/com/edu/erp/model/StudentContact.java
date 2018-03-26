package com.edu.erp.model;


import java.util.Date;


public class StudentContact extends BaseObject {
	    
	// 学生ID
        private Long studentId;
    
	// 联系人姓名
        private String linkName;
    
	// 联系人电话
        private String linkPhone;
    
	// 1父亲  2母亲  3亲戚 4自己 5其他
        private Integer relation;
    
	// 是否有效
        private Integer isValid;
    
	// 备注
        private String remark;
    
	// 创建用户
        
	// 创建时间
        
	// 修改用户
        
	// 修改时间
        

	    /** 设置 学生ID,对应字段 tab_student_contact.student_id */
    public void setStudentId(Long studentId){
    	this.studentId = studentId;
    }
        /** 获取 学生ID,对应字段 tab_student_contact.student_id */
    public Long getStudentId(){
    	return this.studentId;
    }
	    /** 设置 联系人姓名,对应字段 tab_student_contact.link_name */
    public void setLinkName(String linkName){
    	this.linkName = linkName;
    }
        /** 获取 联系人姓名,对应字段 tab_student_contact.link_name */
    public String getLinkName(){
    	return this.linkName;
    }
	    /** 设置 联系人电话,对应字段 tab_student_contact.link_phone */
    public void setLinkPhone(String linkPhone){
    	this.linkPhone = linkPhone;
    }
        /** 获取 联系人电话,对应字段 tab_student_contact.link_phone */
    public String getLinkPhone(){
    	return this.linkPhone;
    }
	    /** 设置 1父亲  2母亲  3亲戚 4自己 5其他,对应字段 tab_student_contact.relation */
    public void setRelation(Integer relation){
    	this.relation = relation;
    }
        /** 获取 1父亲  2母亲  3亲戚 4自己 5其他,对应字段 tab_student_contact.relation */
    public Integer getRelation(){
    	return this.relation;
    }
	    /** 设置 是否有效,对应字段 tab_student_contact.is_valid */
    public void setIsValid(Integer isValid){
    	this.isValid = isValid;
    }
        /** 获取 是否有效,对应字段 tab_student_contact.is_valid */
    public Integer getIsValid(){
    	return this.isValid;
    }
	    /** 设置 备注,对应字段 tab_student_contact.remark */
    public void setRemark(String remark){
    	this.remark = remark;
    }
        /** 获取 备注,对应字段 tab_student_contact.remark */
    public String getRemark(){
    	return this.remark;
    }
}