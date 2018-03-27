package com.edu.erp.model;


/**
 * 学生联系方式
 * 
 * @author wCong
 *
 */
public class StudentContact extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	// 学生ID
	private Long student_id;
	// 联系电话
	private String link_phone;
	// 联系姓名
	private String link_name;
	// 关系
	private String relation;
	// 是否有效 0无效 1有效
	private Integer is_valid;
	// 备注
	private String remark;
	
	private String relation_name;
	
	//是否为学员默认电话号码  1默认电话 0非默认电话
	private Byte default_phone; 
	
	public static enum RelationEnum{
		father(1, "父亲"),
		mother(2, "母亲"),
		relative(3, "亲戚"),
		other(4, "其他"),
			self(5, "学员");
		private Integer code;
		private String desc;
		private RelationEnum(Integer code, String desc){
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
	
	public static enum ValidEnum{
		VALID(1, "有效"),
		INVALID(0, "无效");
		private Integer code;
		private String desc;
		private ValidEnum(Integer code, String desc){
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
	
	
	public Long getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public String getLink_phone() {
		return link_phone;
	}
	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Integer getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLink_name() {
		return link_name;
	}
	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}
	
	public Byte getDefault_phone() {
		return default_phone;
	}
	public void setDefault_phone(Byte default_phone) {
		this.default_phone = default_phone;
	}
	public String getRelation_name() {
		for(RelationEnum relationEnum : RelationEnum.values()){
			if(relationEnum.getCode().toString().equalsIgnoreCase(getRelation())){
				return relationEnum.getDesc();
			}
		}
		return relation_name;
	}
	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}
	
}
