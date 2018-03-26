package com.edu.erp.model;


import java.util.Date;


public class StudentInfo extends BaseObject {
	    
	// 学生code
        private String encoding;
    
	// 姓名
        private String studentName;
    
	// 性别(1=男 2=女)
        private Integer sex;
    
	// 出生日期
        private String birthday;
    
	// 学生状态1:正常 2:重复 3:在读 4:沉睡  5:停课   6:结课
        private Integer studentStatus;
    
	// 家庭地址
        private String address;
    
	// QQ号码
        private String qq;
    
	// 手机号
        private String phone;
    
	// 电子邮箱
        private String email;
    
	// 年级
        private Long gradeId;
    
	// 头像
        private String headPic;
    
	// 就读学校
        private Long attendSchoolId;
    
	// 开户校区
        private Long branchId;
    
	// 1：是  0：不是
        private Integer isOldStudent;
    
	// 九宫格密码
        private String password;
    
	// 备注
        private String remark;
    
	// 学生联系方式ID
        private Long contactId;
    
	// 学生联系人称谓
        private String relationName;
    
	// 1：阳历；2：阴历
        private Integer birthdayType;
    
	// 学生昵称
        private String nickName;
    
	// APP登录名
        private String loginNo;
    
	// 创建用户
        
	// 创建时间
        
	// 修改用户
        
	// 修改时间
        

	    /** 设置 学生code,对应字段 tab_student_info.encoding */
    public void setEncoding(String encoding){
    	this.encoding = encoding;
    }
        /** 获取 学生code,对应字段 tab_student_info.encoding */
    public String getEncoding(){
    	return this.encoding;
    }
	    /** 设置 姓名,对应字段 tab_student_info.student_name */
    public void setStudentName(String studentName){
    	this.studentName = studentName;
    }
        /** 获取 姓名,对应字段 tab_student_info.student_name */
    public String getStudentName(){
    	return this.studentName;
    }
	    /** 设置 性别(1=男 2=女),对应字段 tab_student_info.sex */
    public void setSex(Integer sex){
    	this.sex = sex;
    }
        /** 获取 性别(1=男 2=女),对应字段 tab_student_info.sex */
    public Integer getSex(){
    	return this.sex;
    }
	    /** 设置 出生日期,对应字段 tab_student_info.birthday */
    public void setBirthday(String birthday){
    	this.birthday = birthday;
    }
        /** 获取 出生日期,对应字段 tab_student_info.birthday */
    public String getBirthday(){
    	return this.birthday;
    }
	    /** 设置 学生状态1:正常 2:重复 3:在读 4:沉睡  5:停课   6:结课,对应字段 tab_student_info.student_status */
    public void setStudentStatus(Integer studentStatus){
    	this.studentStatus = studentStatus;
    }
        /** 获取 学生状态1:正常 2:重复 3:在读 4:沉睡  5:停课   6:结课,对应字段 tab_student_info.student_status */
    public Integer getStudentStatus(){
    	return this.studentStatus;
    }
	    /** 设置 家庭地址,对应字段 tab_student_info.address */
    public void setAddress(String address){
    	this.address = address;
    }
        /** 获取 家庭地址,对应字段 tab_student_info.address */
    public String getAddress(){
    	return this.address;
    }
	    /** 设置 QQ号码,对应字段 tab_student_info.qq */
    public void setQq(String qq){
    	this.qq = qq;
    }
        /** 获取 QQ号码,对应字段 tab_student_info.qq */
    public String getQq(){
    	return this.qq;
    }
	    /** 设置 手机号,对应字段 tab_student_info.phone */
    public void setPhone(String phone){
    	this.phone = phone;
    }
        /** 获取 手机号,对应字段 tab_student_info.phone */
    public String getPhone(){
    	return this.phone;
    }
	    /** 设置 电子邮箱,对应字段 tab_student_info.email */
    public void setEmail(String email){
    	this.email = email;
    }
        /** 获取 电子邮箱,对应字段 tab_student_info.email */
    public String getEmail(){
    	return this.email;
    }
	    /** 设置 年级,对应字段 tab_student_info.grade_id */
    public void setGradeId(Long gradeId){
    	this.gradeId = gradeId;
    }
        /** 获取 年级,对应字段 tab_student_info.grade_id */
    public Long getGradeId(){
    	return this.gradeId;
    }
	    /** 设置 头像,对应字段 tab_student_info.head_pic */
    public void setHeadPic(String headPic){
    	this.headPic = headPic;
    }
        /** 获取 头像,对应字段 tab_student_info.head_pic */
    public String getHeadPic(){
    	return this.headPic;
    }
	    /** 设置 就读学校,对应字段 tab_student_info.attend_school_id */
    public void setAttendSchoolId(Long attendSchoolId){
    	this.attendSchoolId = attendSchoolId;
    }
        /** 获取 就读学校,对应字段 tab_student_info.attend_school_id */
    public Long getAttendSchoolId(){
    	return this.attendSchoolId;
    }
	    /** 设置 开户校区,对应字段 tab_student_info.branch_id */
    public void setBranchId(Long branchId){
    	this.branchId = branchId;
    }
        /** 获取 开户校区,对应字段 tab_student_info.branch_id */
    public Long getBranchId(){
    	return this.branchId;
    }
	    /** 设置 1：是  0：不是,对应字段 tab_student_info.is_old_student */
    public void setIsOldStudent(Integer isOldStudent){
    	this.isOldStudent = isOldStudent;
    }
        /** 获取 1：是  0：不是,对应字段 tab_student_info.is_old_student */
    public Integer getIsOldStudent(){
    	return this.isOldStudent;
    }
	    /** 设置 九宫格密码,对应字段 tab_student_info.password */
    public void setPassword(String password){
    	this.password = password;
    }
        /** 获取 九宫格密码,对应字段 tab_student_info.password */
    public String getPassword(){
    	return this.password;
    }
	    /** 设置 备注,对应字段 tab_student_info.remark */
    public void setRemark(String remark){
    	this.remark = remark;
    }
        /** 获取 备注,对应字段 tab_student_info.remark */
    public String getRemark(){
    	return this.remark;
    }
	    /** 设置 学生联系方式ID,对应字段 tab_student_info.contact_id */
    public void setContactId(Long contactId){
    	this.contactId = contactId;
    }
        /** 获取 学生联系方式ID,对应字段 tab_student_info.contact_id */
    public Long getContactId(){
    	return this.contactId;
    }
	    /** 设置 学生联系人称谓,对应字段 tab_student_info.relation_name */
    public void setRelationName(String relationName){
    	this.relationName = relationName;
    }
        /** 获取 学生联系人称谓,对应字段 tab_student_info.relation_name */
    public String getRelationName(){
    	return this.relationName;
    }
	    /** 设置 1：阳历；2：阴历,对应字段 tab_student_info.birthday_type */
    public void setBirthdayType(Integer birthdayType){
    	this.birthdayType = birthdayType;
    }
        /** 获取 1：阳历；2：阴历,对应字段 tab_student_info.birthday_type */
    public Integer getBirthdayType(){
    	return this.birthdayType;
    }
	    /** 设置 学生昵称,对应字段 tab_student_info.nick_name */
    public void setNickName(String nickName){
    	this.nickName = nickName;
    }
        /** 获取 学生昵称,对应字段 tab_student_info.nick_name */
    public String getNickName(){
    	return this.nickName;
    }
	    /** 设置 APP登录名,对应字段 tab_student_info.login_no */
    public void setLoginNo(String loginNo){
    	this.loginNo = loginNo;
    }
        /** 获取 APP登录名,对应字段 tab_student_info.login_no */
    public String getLoginNo(){
    	return this.loginNo;
    }
}