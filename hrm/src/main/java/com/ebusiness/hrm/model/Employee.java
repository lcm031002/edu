package com.ebusiness.hrm.model;

import java.io.Serializable;
import java.util.Date;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Employee implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//员工id
	private Long id;
	//部门
	private String dept;
	
	//岗位
	private Integer post;
	
	//职位
	private String position;
	
	//等级
	private Integer poslevel;
	
	// 企业邮箱
    private String email;
	
	//在职类型
	private Integer enterType;
	
	//姓名
	private String employee_name;
	
	//员工号
	private String emp_id;
	
	//员工号
	private String encoding;
	
	//组织
	private Long org_id;
	
	// 性别
    private Integer sex;
    
    //联系电话
   	private String phone;
   	
   	//业务线
   	private String business;
   	
   	//员工头像
   	private String staff_head;
	
	//分页对象
	private PageParam pageParam;
	
	//岗位名称
	private String postName;
	
	//等级名称
	private String levelName;
	
	//在职类型名称
	private String enterTypeName;
	
	//身份证号
	private String ID_ID;
	
	//地址
	private String address;
	
	//状态
	private Integer status;


	//入职时间
	private String entryDate;


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getID_ID() {
		return ID_ID;
	}

	public void setID_ID(String iD_ID) {
		ID_ID = iD_ID;
	}

	public String getEnterTypeName() {
		return enterTypeName;
	}

	public void setEnterTypeName(String enterTypeName) {
		this.enterTypeName = enterTypeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getPoslevel() {
		return poslevel;
	}

	public void setPoslevel(Integer poslevel) {
		this.poslevel = poslevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public Integer getEnterType() {
		return enterType;
	}

	public void setEnterType(Integer enterType) {
		this.enterType = enterType;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getStaff_head() {
		return staff_head;
	}

	public void setStaff_head(String staff_head) {
		this.staff_head = staff_head;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
}
