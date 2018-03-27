package com.edu.erp.model;

import java.util.List;

public class TMoreTeacherCourse extends BaseObject {
	private static final long serialVersionUID = -7989387383128158303L;
	private String course_name; //课程名称
	private Long teacher_id;//教师ID
	private String teacher_name;  // 教师名称
	private Long course_count;// 课时数量
	private Long unit_price;// 课程单价
	private Double hour_len;// 课时长度(分钟)
	private String attend_class_period;//上课期间
	private String start_date;// 开课日期
	private String end_date;// 结课日期
	private String start_time;// 上课时间
	private String end_time;// 下课时间
	private Long is_sended; //同步状态'0-未同步过，1-同步成功,2：同步失败';
	private Long type;// 课程类型 '0，其他 1.线上+线下双师课程,2.线下双师课程';
	private Long branch_id;//组织校区ID
	private String branch_name; //组织校区名称
	private Long course_id;//新增主讲老师的主讲课程ID
	private String description; // 课程介绍
	private String operation_type; // 运营类型 0-直营 1-直营+加盟
	private String syn_exception;//同步异常信息

	private List<TCourse> courseRel;

	   // 课程类型
    public static enum MoreTeacherCourseTypeEnum{
    	o2o(1, "线上+线下课程"),
    	offline(2, "线下双师课程"),
    	online(3, "线上补课课程"),
    	mt(4, "暑期双师课程");
    	private Integer code;
    	private String desc;
    	private MoreTeacherCourseTypeEnum(Integer code, String desc){
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
    
    //同步状态
    public static enum IsSendedEnum{
		no(0, "未同步"),
		success(1, "同步成功"),
		fail(2, "同步失败");
		private Integer code;
		private String desc;
		private IsSendedEnum(Integer code, String desc){
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
    
    //课程状态
    public static enum MoreTeacherCourseStatusEnum{
    	other(0, "删除"),
		on(1, "上架"),
		off(2, "下架");
		private Integer code;
		private String desc;
		private MoreTeacherCourseStatusEnum(Integer code, String desc){
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
    
    public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Long getCourse_count() {
		return course_count;
	}

	public void setCourse_count(Long course_count) {
		this.course_count = course_count;
	}

	public Long getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Long unit_price) {
		this.unit_price = unit_price;
	}

	public Double getHour_len() {
		return hour_len;
	}

	public void setHour_len(Double hour_len) {
		this.hour_len = hour_len;
	}

	public String getAttend_class_period() {
		return attend_class_period;
	}

	public void setAttend_class_period(String attend_class_period) {
		this.attend_class_period = attend_class_period;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Long getIs_sended() {
		return is_sended;
	}

	public void setIs_sended(Long is_sended) {
		this.is_sended = is_sended;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public List<TCourse> getCourseRel() {
		return courseRel;
	}

	public void setCourseRel(List<TCourse> courseRel) {
		this.courseRel = courseRel;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getSyn_exception() {
		return syn_exception;
	}

	public void setSyn_exception(String syn_exception) {
		this.syn_exception = syn_exception;
	}
}
