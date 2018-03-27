/**  
 * @Title: StudentInfo.java
 * @Package com.ebusiness.erp.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:19:21
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: StudentInfo
 * @Description: 学员信息对象
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:19:21
 * 
 */
public class StudentInfo implements Serializable, Cloneable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -3593132206963628068L;
	// 编号
	private String encoding;
	// 姓名
	private String student_name;
	// 性别
	private Integer sex;
	// 出生日期
	private String birthday;
	// 学生状态
	/* 1：正常 2：重复 3：在读 4：沉睡 5：停课 6：结课 */
	private Integer student_status;
	// 家庭地址
	private String address;
	// QQ号码
	private String qq;
	// 手机号
	private String phone;
	// 电子邮箱
	private String email;
	// 年级
	private Long grade_id;
	// 头像
	private String head_pic;
	// 就读学校
	private Long attend_school_id;
	// 开户校区
	private Long branch_id;
	// 开户地区
	private Long city_id;
	// 开会团队
	private Long bu_id;
	// 是否老学员
	/* 1：是 0：不是 */
	private Integer is_old_student;
	// 所属城市
	private Long r_city;
	// 邮编
	private Long code;
	// 手机验证
	private Long phone_verify;
	// 地区
	private String city_name;
	// 团队名称
	private String bu_name;
	// 校区
	private String branch_name;
	// 年级
	private String grade_name;
	// 城市名
	private String r_city_name;
	// 积分
	private Long integral;
	// 九宫格密码
	private String password;
	// 备注
	private String remark;
	// 就读学校名称
	private String attend_school_name;
	// 关联的旧系统的id
	private Long student_id_old;
	// 主键
	private Long id;
	// 状态
	private Integer status;
	// 创建用户
	private Long create_user;
	// 创建时间
	private Date create_time;
	// 更新用户
	private Long update_user;
	// 更新时间
	private Date update_time;
	// 新增用户名
	private String create_user_name;
	// 修改用户名
	private String update_user_name;

	private Long contact_id;

	private String relation_name;
	
	private Long product_line;
	
	//学员积分账户
	private List<StudentIntegral> studentIntegralList = new ArrayList<StudentIntegral>();
	
	//我推荐的人
	private List<StudentRel> myRels = new ArrayList<StudentRel>();
	
	//推荐我的关系
	private List<StudentRel> relsMy = new ArrayList<StudentRel>();
	
	private List<StudentContact> studentContactList;
	
	private StudentCounselorInfo counselor; // 有效咨询师
	
	private StudentCounselorInfo govern; // 有效学管师
	
	private String syn_exception;
	
	private String login_no;
	//推荐人姓名
	private String referrals;
	
	//推荐人id
	private Long referrals_id;

	//学管师ID
	private  Long counselor_id;

	//学管师姓名
	private  String counselor_name;

	//咨询师ID
	private  Long learningmgr_id;

	//咨询师姓名
	private String learningmgr_name;

	// 剩余可排课时
	private Long course_schedule_count;

	// 活跃标识 1-活跃 0或者空-不活跃
	private String active;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentInfo other = (StudentInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (attend_school_id == null) {
			if (other.attend_school_id != null)
				return false;
		} else if (!attend_school_id.equals(other.attend_school_id))
			return false;
		if (attend_school_name == null) {
			if (other.attend_school_name != null)
				return false;
		} else if (!attend_school_name.equals(other.attend_school_name))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (branch_id == null) {
			if (other.branch_id != null)
				return false;
		} else if (!branch_id.equals(other.branch_id))
			return false;
		if (branch_name == null) {
			if (other.branch_name != null)
				return false;
		} else if (!branch_name.equals(other.branch_name))
			return false;
		if (bu_id == null) {
			if (other.bu_id != null)
				return false;
		} else if (!bu_id.equals(other.bu_id))
			return false;
		if (city_id == null) {
			if (other.city_id != null)
				return false;
		} else if (!city_id.equals(other.city_id))
			return false;
		if (city_name == null) {
			if (other.city_name != null)
				return false;
		} else if (!city_name.equals(other.city_name))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (contact_id == null) {
			if (other.contact_id != null)
				return false;
		} else if (!contact_id.equals(other.contact_id))
			return false;
		if (create_time == null) {
			if (other.create_time != null)
				return false;
		} else if (!create_time.equals(other.create_time))
			return false;
		if (create_user == null) {
			if (other.create_user != null)
				return false;
		} else if (!create_user.equals(other.create_user))
			return false;
		if (create_user_name == null) {
			if (other.create_user_name != null)
				return false;
		} else if (!create_user_name.equals(other.create_user_name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (grade_id == null) {
			if (other.grade_id != null)
				return false;
		} else if (!grade_id.equals(other.grade_id))
			return false;
		if (grade_name == null) {
			if (other.grade_name != null)
				return false;
		} else if (!grade_name.equals(other.grade_name))
			return false;
		if (head_pic == null) {
			if (other.head_pic != null)
				return false;
		} else if (!head_pic.equals(other.head_pic))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (integral == null) {
			if (other.integral != null)
				return false;
		} else if (!integral.equals(other.integral))
			return false;
		if (is_old_student == null) {
			if (other.is_old_student != null)
				return false;
		} else if (!is_old_student.equals(other.is_old_student))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (phone_verify == null) {
			if (other.phone_verify != null)
				return false;
		} else if (!phone_verify.equals(other.phone_verify))
			return false;
		if (qq == null) {
			if (other.qq != null)
				return false;
		} else if (!qq.equals(other.qq))
			return false;
		if (r_city == null) {
			if (other.r_city != null)
				return false;
		} else if (!r_city.equals(other.r_city))
			return false;
		if (r_city_name == null) {
			if (other.r_city_name != null)
				return false;
		} else if (!r_city_name.equals(other.r_city_name))
			return false;
		if (relation_name == null) {
			if (other.relation_name != null)
				return false;
		} else if (!relation_name.equals(other.relation_name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (student_id_old == null) {
			if (other.student_id_old != null)
				return false;
		} else if (!student_id_old.equals(other.student_id_old))
			return false;
		if (student_name == null) {
			if (other.student_name != null)
				return false;
		} else if (!student_name.equals(other.student_name))
			return false;
		if (student_status == null) {
			if (other.student_status != null)
				return false;
		} else if (!student_status.equals(other.student_status))
			return false;
		if (update_time == null) {
			if (other.update_time != null)
				return false;
		} else if (!update_time.equals(other.update_time))
			return false;
		if (update_user == null) {
			if (other.update_user != null)
				return false;
		} else if (!update_user.equals(other.update_user))
			return false;
		if (update_user_name == null) {
			if (other.update_user_name != null)
				return false;
		} else if (!update_user_name.equals(other.update_user_name))
			return false;
		return true;
	}

	public final String getAddress() {
		return address;
	}

	public final Long getAttend_school_id() {
		return attend_school_id;
	}

	public final String getAttend_school_name() {
		return attend_school_name;
	}

	public final List<StudentRel> getMyRels() {
		return myRels;
	}

	public final void setMyRels(List<StudentRel> myRels) {
		this.myRels = myRels;
	}

	public final List<StudentRel> getRelsMy() {
		return relsMy;
	}

	public final void setRelsMy(List<StudentRel> relsMy) {
		this.relsMy = relsMy;
	}

	public final String getBirthday() {
		return birthday;
	}

	public final Long getBranch_id() {
		return branch_id;
	}

	public final String getBranch_name() {
		return branch_name;
	}

	public final Long getBu_id() {
		return bu_id;
	}

	public final Long getCity_id() {
		return city_id;
	}

	public final String getCity_name() {
		return city_name;
	}

	public final Long getCode() {
		return code;
	}

	public final Long getContact_id() {
		return contact_id;
	}

	public final Date getCreate_time() {
		return create_time;
	}

	public final Long getCreate_user() {
		return create_user;
	}

	public final String getCreate_user_name() {
		return create_user_name;
	}

	public final String getEmail() {
		return email;
	}

	public final String getEncoding() {
		return encoding;
	}

	public final Long getGrade_id() {
		return grade_id;
	}

	public final String getGrade_name() {
		return grade_name;
	}

	public final String getHead_pic() {
		return head_pic;
	}

	public final Long getId() {
		return id;
	}

	public final Long getIntegral() {
		return integral;
	}

	public final Integer getIs_old_student() {
		return is_old_student;
	}

	public final String getPassword() {
		return password;
	}

	public final String getPhone() {
		return phone;
	}

	public final Long getPhone_verify() {
		return phone_verify;
	}

	public final String getQq() {
		return qq;
	}

	public final Long getR_city() {
		return r_city;
	}

	public final String getR_city_name() {
		return r_city_name;
	}

	public final String getRelation_name() {
		return relation_name;
	}

	public final String getRemark() {
		return remark;
	}

	public final Integer getSex() {
		return sex;
	}

	public final Integer getStatus() {
		return status;
	}

	public final Long getStudent_id_old() {
		return student_id_old;
	}

	public final String getStudent_name() {
		return student_name;
	}

	public final Integer getStudent_status() {
		return student_status;
	}

	public final Date getUpdate_time() {
		return update_time;
	}

	public final Long getUpdate_user() {
		return update_user;
	}

	public final String getUpdate_user_name() {
		return update_user_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime
				* result
				+ ((attend_school_id == null) ? 0 : attend_school_id.hashCode());
		result = prime
				* result
				+ ((attend_school_name == null) ? 0 : attend_school_name
						.hashCode());
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result
				+ ((branch_id == null) ? 0 : branch_id.hashCode());
		result = prime * result
				+ ((branch_name == null) ? 0 : branch_name.hashCode());
		result = prime * result + ((bu_id == null) ? 0 : bu_id.hashCode());
		result = prime * result + ((city_id == null) ? 0 : city_id.hashCode());
		result = prime * result
				+ ((city_name == null) ? 0 : city_name.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((contact_id == null) ? 0 : contact_id.hashCode());
		result = prime * result
				+ ((create_time == null) ? 0 : create_time.hashCode());
		result = prime * result
				+ ((create_user == null) ? 0 : create_user.hashCode());
		result = prime
				* result
				+ ((create_user_name == null) ? 0 : create_user_name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result
				+ ((grade_id == null) ? 0 : grade_id.hashCode());
		result = prime * result
				+ ((grade_name == null) ? 0 : grade_name.hashCode());
		result = prime * result
				+ ((head_pic == null) ? 0 : head_pic.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((integral == null) ? 0 : integral.hashCode());
		result = prime * result
				+ ((is_old_student == null) ? 0 : is_old_student.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((phone_verify == null) ? 0 : phone_verify.hashCode());
		result = prime * result + ((qq == null) ? 0 : qq.hashCode());
		result = prime * result + ((r_city == null) ? 0 : r_city.hashCode());
		result = prime * result
				+ ((r_city_name == null) ? 0 : r_city_name.hashCode());
		result = prime * result
				+ ((relation_name == null) ? 0 : relation_name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((student_id_old == null) ? 0 : student_id_old.hashCode());
		result = prime * result
				+ ((student_name == null) ? 0 : student_name.hashCode());
		result = prime * result
				+ ((student_status == null) ? 0 : student_status.hashCode());
		result = prime * result
				+ ((update_time == null) ? 0 : update_time.hashCode());
		result = prime * result
				+ ((update_user == null) ? 0 : update_user.hashCode());
		result = prime
				* result
				+ ((update_user_name == null) ? 0 : update_user_name.hashCode());
		return result;
	}

	public final void setAddress(String address) {
		this.address = address;
	}

	public final void setAttend_school_id(Long attend_school_id) {
		this.attend_school_id = attend_school_id;
	}

	public final void setAttend_school_name(String attend_school_name) {
		this.attend_school_name = attend_school_name;
	}

	public final void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public final void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public final void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public final void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public final void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public final void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public final void setCode(Long code) {
		this.code = code;
	}

	public final void setContact_id(Long contact_id) {
		this.contact_id = contact_id;
	}

	public final void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public final void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}

	public final void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public final void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}

	public final void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public final void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final void setIntegral(Long integral) {
		this.integral = integral;
	}

	public final void setIs_old_student(Integer is_old_student) {
		this.is_old_student = is_old_student;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final void setPhone(String phone) {
		this.phone = phone;
	}

	public final void setPhone_verify(Long phone_verify) {
		this.phone_verify = phone_verify;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	public final void setQq(String qq) {
		this.qq = qq;
	}

	public final void setR_city(Long r_city) {
		this.r_city = r_city;
	}

	public final void setR_city_name(String r_city_name) {
		this.r_city_name = r_city_name;
	}

	public final void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final void setSex(Integer sex) {
		this.sex = sex;
	}

	public final void setStatus(Integer status) {
		this.status = status;
	}

	public final void setStudent_id_old(Long student_id_old) {
		this.student_id_old = student_id_old;
	}

	public final void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public final void setStudent_status(Integer student_status) {
		this.student_status = student_status;
	}

	public final List<StudentIntegral> getStudentIntegralList() {
		return studentIntegralList;
	}

	public final void setStudentIntegralList(
			List<StudentIntegral> studentIntegralList) {
		this.studentIntegralList = studentIntegralList;
	}

	public final void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public final void setUpdate_user(Long update_user) {
		this.update_user = update_user;
	}

	public final void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public List<StudentContact> getStudentContactList() {
		return studentContactList;
	}

	public void setStudentContactList(List<StudentContact> studentContactList) {
		this.studentContactList = studentContactList;
	}

	public StudentCounselorInfo getCounselor() {
		return counselor;
	}

	public void setCounselor(StudentCounselorInfo counselor) {
		this.counselor = counselor;
	}

	public StudentCounselorInfo getGovern() {
		return govern;
	}

	public void setGovern(StudentCounselorInfo govern) {
		this.govern = govern;
	}

	@Override
	public String toString() {
		return "StudentInfo [encoding=" + encoding + ", student_name=" + student_name + ", sex=" + sex + ", birthday="
				+ birthday + ", student_status=" + student_status + ", address=" + address + ", qq=" + qq + ", phone="
				+ phone + ", email=" + email + ", grade_id=" + grade_id + ", head_pic=" + head_pic
				+ ", attend_school_id=" + attend_school_id + ", branch_id=" + branch_id + ", city_id=" + city_id
				+ ", bu_id=" + bu_id + ", is_old_student=" + is_old_student + ", r_city=" + r_city + ", code=" + code
				+ ", phone_verify=" + phone_verify + ", city_name=" + city_name + ", bu_name=" + bu_name
				+ ", branch_name=" + branch_name + ", grade_name=" + grade_name + ", r_city_name=" + r_city_name
				+ ", integral=" + integral + ", password=" + password + ", remark=" + remark + ", attend_school_name="
				+ attend_school_name + ", student_id_old=" + student_id_old + ", id=" + id + ", status=" + status
				+ ", create_user=" + create_user + ", create_time=" + create_time + ", update_user=" + update_user
				+ ", update_time=" + update_time + ", create_user_name=" + create_user_name + ", update_user_name="
				+ update_user_name + ", contact_id=" + contact_id + ", relation_name=" + relation_name
				+ ", studentIntegralList=" + studentIntegralList + ", myRels=" + myRels + ", relsMy=" + relsMy
				+ ", studentContactList=" + studentContactList + ", counselor=" + counselor + ", govern=" + govern
				+ "]";
	}

	public String getSyn_exception() {
		return syn_exception;
	}

	public void setSyn_exception(String syn_exception) {
		this.syn_exception = syn_exception;
	}

    public Long getProduct_line() {
        return product_line;
    }

    public void setProduct_line(Long product_line) {
        this.product_line = product_line;
    }

	public String getLogin_no() {
		return login_no;
	}

	public void setLogin_no(String login_no) {
		this.login_no = login_no;
	}

	public String getReferrals() {
		return referrals;
	}

	public void setReferrals(String referrals) {
		this.referrals = referrals;
	}

	public Long getReferrals_id() {
		return referrals_id;
	}

	public void setReferrals_id(Long referrals_id) {
		this.referrals_id = referrals_id;
	}

	public Long getCounselor_id() {return counselor_id;}

	public void setCounselor_id(Long counselor_id) {this.counselor_id = counselor_id;}

	public String getCounselor_name() {return counselor_name;}

	public void setCounselor_name(String counselor_name) {this.counselor_name = counselor_name;}

	public Long getLearningmgr_id() {return learningmgr_id;}

	public void setLearningmgr_id(Long learningmgr_id) {this.learningmgr_id = learningmgr_id;}

	public String getLearningmgr_name() {return learningmgr_name;}

	public void setLearningmgr_name(String learningmgr_name) {this.learningmgr_name = learningmgr_name;}

	public Long getCourse_schedule_count() {
		return course_schedule_count;
	}

	public void setCourse_schedule_count(Long course_schedule_count) {
		this.course_schedule_count = course_schedule_count;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
