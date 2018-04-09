package com.edu.erp.model;

import java.util.List;


/**
 * 员工信息
 * 
 * @author wCong
 *
 */
public class EmployeeInfo extends BaseObject {
	private static final long serialVersionUID = -4622009327158529384L;

	// 编号
	private String encoding;
	// 姓名
    private String employee_name; 
    // 性别
    private Integer sex; 
    // 身份证
    private String id_card;
    // 邮箱
    private String email; 
    // 手机
    private String phone;
    // 地址
    private String address; 
    // 备注
    private String description; 
    // 员工类型
    private Long user_type;

    private String user_type_name;
    
    private String branch_post_names; //所属校区/职位（多个以,分隔）
    
    private String tel;
    
    private String counselor_type;
    
    //员工部门关系
    private List<EmployeeDepartmentRef> edrList;

	public String getEncoding() {
      return encoding;
    }
	
    public void setEncoding(String encoding) {
      this.encoding = encoding;
    }
  		
    public String getEmployee_name() {
      return employee_name;
    }
	
    public void setEmployee_name(String employee_name) {
      this.employee_name = employee_name;
    }
  		
    public Integer getSex() {
      return sex;
    }
	
    public void setSex(Integer sex) {
      this.sex = sex;
    }
  		
  		
    public String getEmail() {
      return email;
    }
	
    public void setEmail(String email) {
      this.email = email;
    }
  		
    public String getPhone() {
      return phone;
    }
	
    public void setPhone(String phone) {
      this.phone = phone;
    }
  		
    public String getAddress() {
      return address;
    }
	
    public void setAddress(String address) {
      this.address = address;
    }
  		
    public String getDescription() {
      return description;
    }
	
    public void setDescription(String description) {
      this.description = description;
    }

	public List<EmployeeDepartmentRef> getEdrList() {
		return edrList;
	}

	public void setEdrList(List<EmployeeDepartmentRef> edrList) {
		this.edrList = edrList;
	}

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public Long getUser_type() {
		return user_type;
	}

	public void setUser_type(Long user_type) {
		this.user_type = user_type;
	}

	public String getBranch_post_names() {
		return branch_post_names;
	}

	public void setBranch_post_names(String branch_post_names) {
		this.branch_post_names = branch_post_names;
	}

	public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCounselor_type() {
        return counselor_type;
    }

    public void setCounselor_type(String counselor_type) {
        this.counselor_type = counselor_type;
    }

    public String getUser_type_name() {
        return user_type_name;
    }

    public void setUser_type_name(String user_type_name) {
        this.user_type_name = user_type_name;
    }

    public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("编号：");
		buff.append(getEncoding());
		buff.append("姓名：");
		buff.append(getEmployee_name());
		buff.append("身份证：");
		buff.append(getId_card());
		buff.append("性别：");
		buff.append(getSex());
		buff.append("手机：");
		buff.append(getPhone());
		buff.append("邮箱：");
		buff.append(getEmail());
		buff.append("地址：");
		buff.append(getAddress());
		buff.append("备注：");
		buff.append(getDescription());
		return buff.toString();
	}
}	
