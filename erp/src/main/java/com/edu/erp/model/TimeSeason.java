package com.edu.erp.model;

public class TimeSeason extends BaseObject
{
	
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -673252857654945249L;

	private String course_season_name;//课程季名称
	
	private String encoding;//编号
	
	private Long last_season_id;//编号
	
	private Long season;//季节
	
	private String start_date;//开始时间
	
	private String end_date;//结束时间
	
	private Long business_type;//业务类型
	
	private String description;//描述
	
	private String last_course_season_name; // 上一课程季名称
	
	private String business_type_name; // 业务类型名称
	
	private String season_name; // 季节名称
	
	/**
	 * @author lpe
	 * 状态枚举 季节的值 
	 */
	public static enum StatusEnum
	{	
			SPRING(1, "春季"),
			SUMMER(2, "夏季"),
			AUTUMN(3, "秋季"),
			WINTER(4, "冬季");
			
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
	
	public String getCourse_season_name() {
		return course_season_name;
	}

	public void setCourse_season_name(String course_season_name) {
		this.course_season_name = course_season_name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Long getLast_season_id() {
		return last_season_id;
	}

	public void setLast_season_id(Long last_season_id) {
		this.last_season_id = last_season_id;
	}

	public Long getSeason() {
		return season;
	}

	public void setSeason(Long season) {
		this.season = season;
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

	public Long getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLast_course_season_name() {
		return last_course_season_name;
	}

	public void setLast_course_season_name(String last_course_season_name) {
		this.last_course_season_name = last_course_season_name;
	}

	public String getBusiness_type_name() {
		return business_type_name;
	}

	public void setBusiness_type_name(String business_type_name) {
		this.business_type_name = business_type_name;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("名称： ");
		buff.append(getCourse_season_name());
		buff.append("，");
		buff.append("季节：");
		buff.append(getSeason());
		buff.append("，");
		buff.append("上一个课程季ID：");
		buff.append(getLast_season_id());
		buff.append("，");
		buff.append("开始时间：");
		buff.append(getStart_date());
		buff.append("，");
		buff.append("结束时间：");
		buff.append(getEnd_date());
		buff.append("，");
		buff.append("业务模型：");
		buff.append(getBusiness_type());
		return buff.toString();
	}
}
