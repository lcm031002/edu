package com.edu.erp.model;

/**
 * 教师信息
 * @author wsf
 * @date 2014-09-11
 */
public class TEDocTemplateTeacherInfo extends BaseObject{

	private static final long serialVersionUID = 1851513005975302210L;
	
	private String encoding;	//编码
	
	private String teacher_name; //教师姓名
	
	private String is_pluralistic;// 是否兼职, 1.是  2.否
	
	private String description;//描述
	
	private Long employee_id;//员工ID
	
	private String employee_encoding;//员工编码
	
    private String err_desc; // ERROR_INFO
    
    private String err_code; // ERROR_INFO

	private Long edoc_id; // 文档ID
	
	private Long seq; // 序
	
	private Long row_no; //所在文档内行号
	
	private String status_name; //类型
	
	private String city_name;//城市
	
	private String phone;//电话
	
	public static enum StatusEnum{
//		delete(0, "删除"),
		formal(1, "正式员工"),
		temp(2, "试用员工"),
		back(3, "返聘"),
		resign(4, "辞职"),
		dismiss(5, "解聘");
		private Integer code;
		private String desc;
		private StatusEnum(Integer code, String desc){
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
	public static enum PluralisticEnum{
		no(0, "否"),
		yes(1, "是");
		private Integer code;
		private String desc;
		private PluralisticEnum(Integer code, String desc){
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	
	@Override
	public String toString() {
		return "Teacher [encoding=" + encoding + ", teacher_name="
				+ teacher_name + ", is_pluralistic=" + is_pluralistic +", description=" + description+"]";
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getErr_desc() {
		return err_desc;
	}
	public String getErr_code() {
		return err_code;
	}
	public Long getEdoc_id() {
		return edoc_id;
	}
	public Long getSeq() {
		return seq;
	}
	public Long getRow_no() {
		return row_no;
	}
	public void setErr_desc(String err_desc) {
		this.err_desc = err_desc;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public void setEdoc_id(Long edoc_id) {
		this.edoc_id = edoc_id;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public void setRow_no(Long row_no) {
		this.row_no = row_no;
	}
	public String getEmployee_encoding() {
		return employee_encoding;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setEmployee_encoding(String employee_encoding) {
		this.employee_encoding = employee_encoding;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getIs_pluralistic() {
		return is_pluralistic;
	}
	public void setIs_pluralistic(String is_pluralistic) {
		this.is_pluralistic = is_pluralistic;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
