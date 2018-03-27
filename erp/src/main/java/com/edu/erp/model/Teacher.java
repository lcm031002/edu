package com.edu.erp.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 教师信息
 * 
 * @author wsf
 * @date 2014-09-11
 */
public class Teacher extends BaseObject {

	private static final long serialVersionUID = 1851513005975302210L;

	private String encoding; // 编码
	private String teacher_name; // 教师姓名
	private Integer is_pluralistic;// 是否兼职, 1.是 2.否
	private String photo; // 教师照片
	private String description;// 描述
	private Long employee_id;// 员工ID
	private String employee_name;// 描述
	private String phone;
	private String invation_code;
	private Integer sex; // 性别
	private String city_name;
	private Integer teacher_age;
	private Integer seniority;
	private Integer teacher_type;
	private String nickname;
	private String subject;
	private Long bu_id;
	private String bu_name;
	private String synchro_dingdang;
	private String synchro_details;
	private String email;
	private String old_id;
	private String subject_name;
	private String team;//团队
    private List<TeacherTeamRel> teacherTeamReList;//教师团队关系表
	private List<TeacherSubject> teacherSubjectList;

	public static enum StatusEnum {
		// delete(0, "删除"),
		formal(1, "正式员工"), temp(2, "试用员工"), back(3, "返聘"), resign(4, "辞职"), dismiss(
				5, "解聘");
		private Integer code;
		private String desc;

		private StatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public static enum PluralisticEnum {
		no(0, "否"), yes(1, "是");
		private Integer code;
		private String desc;

		private PluralisticEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public String getSynchro_dingdang() {
		return synchro_dingdang;
	}

	public void setSynchro_dingdang(String synchro_dingdang) {
		this.synchro_dingdang = synchro_dingdang;
	}

	public String getSynchro_details() {
		return synchro_details;
	}

	public void setSynchro_details(String synchro_details) {
		this.synchro_details = synchro_details;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public Integer getIs_pluralistic() {
		return is_pluralistic;
	}

	public void setIs_pluralistic(Integer is_pluralistic) {
		this.is_pluralistic = is_pluralistic;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TeacherSubject> getTeacherSubjectList() {
		return teacherSubjectList;
	}

	public void setTeacherSubjectList(List<TeacherSubject> teacherSubjectList) {
		this.teacherSubjectList = teacherSubjectList;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	@Override
	public String toString() {
		String contentString = ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
		return contentString;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getPhone() {
		return phone;
	}

	public String getInvation_code() {
		return invation_code;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setInvation_code(String invation_code) {
		this.invation_code = invation_code;
	}

	public final String getCity_name() {
		return city_name;
	}

	public final void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public Integer getTeacher_age() {
		return teacher_age;
	}

	public void setAge(Integer teacher_age) {
		this.teacher_age = teacher_age;
	}

	public Integer getSeniority() {
		return seniority;
	}

	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}

	public Integer getTeacher_type() {
		return teacher_type;
	}

	public void setTeacher_type(Integer teacher_type) {
		this.teacher_type = teacher_type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getBu_id() {
		return bu_id;
	}

	public void setBu_id(Long bu_id) {
		this.bu_id = bu_id;
	}

	public String getBu_name() {
		return bu_name;
	}

	public void setBu_name(String bu_name) {
		this.bu_name = bu_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((city_name == null) ? 0 : city_name.hashCode());
		result = prime
				* result
				+ ((getCreate_user_name() == null) ? 0 : getCreate_user_name()
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((employee_id == null) ? 0 : employee_id.hashCode());
		result = prime * result
				+ ((employee_name == null) ? 0 : employee_name.hashCode());
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result
				+ ((invation_code == null) ? 0 : invation_code.hashCode());
		result = prime * result
				+ ((is_pluralistic == null) ? 0 : is_pluralistic.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime
				* result
				+ ((teacherSubjectList == null) ? 0 : teacherSubjectList
						.hashCode());
		result = prime * result
				+ ((teacher_name == null) ? 0 : teacher_name.hashCode());
		result = prime
				* result
				+ ((getUpdate_user_name() == null) ? 0 : getUpdate_user_name()
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (city_name == null) {
			if (other.city_name != null)
				return false;
		} else if (!city_name.equals(other.city_name))
			return false;
		if (getCreate_user_name() == null) {
			if (other.getCreate_user_name() != null)
				return false;
		} else if (!getCreate_user_name().equals(other.getCreate_user_name()))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (employee_id == null) {
			if (other.employee_id != null)
				return false;
		} else if (!employee_id.equals(other.employee_id))
			return false;
		if (employee_name == null) {
			if (other.employee_name != null)
				return false;
		} else if (!employee_name.equals(other.employee_name))
			return false;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (invation_code == null) {
			if (other.invation_code != null)
				return false;
		} else if (!invation_code.equals(other.invation_code))
			return false;
		if (is_pluralistic == null) {
			if (other.is_pluralistic != null)
				return false;
		} else if (!is_pluralistic.equals(other.is_pluralistic))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (teacherSubjectList == null) {
			if (other.teacherSubjectList != null)
				return false;
		} else if (!teacherSubjectList.equals(other.teacherSubjectList))
			return false;
		if (teacher_name == null) {
			if (other.teacher_name != null)
				return false;
		} else if (!teacher_name.equals(other.teacher_name))
			return false;
		if (getUpdate_user_name() == null) {
			if (other.getUpdate_user_name() != null)
				return false;
		} else if (!getUpdate_user_name().equals(other.getUpdate_user_name()))
			return false;
		return true;
	}

	public String getOld_id() {
		return old_id;
	}

	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTeacher_age(Integer teacher_age) {
        this.teacher_age = teacher_age;
    }

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public List<TeacherTeamRel> getTeacherTeamReList() {
		return teacherTeamReList;
	}

	public void setTeacherTeamReList(List<TeacherTeamRel> teacherTeamReList) {
		this.teacherTeamReList = teacherTeamReList;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
