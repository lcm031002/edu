package com.edu.erp.model;

import java.util.HashMap;

import com.edu.erp.model.BaseObject.StatusEnum;


/**
 * 课程排课表 
 * 
 * @author wCong
 *
 */
public class CourseScheduling extends BaseObject{
	private static final long serialVersionUID = 3652466991374547964L;
	
	// 课程商品Id
	private Long course_id;
	// 学生ID
	private Long student_id;
	// 教师ID
	private Long teacher_id;
	
	private Long assteacher_id;
	// 商品类型:1班级课,2一对一,3晚辅导
	private Long business_type; 
	// 上课日期
	private Long course_date;
	// 一年第几周
	private Long week_number;
	// 上课时间
	private String start_time;
	// 下课时间
	private String end_time;
	// 课次
	private Long course_times;
	
	private Long branch_id;
	private Long grade_id;
	private Long subject_id;
	// 是否已考勤:Y是,N否
	private String attended;
	// 有效状态:0失效,1有效
	private Long valid_status;
	private Long is_ordered;
    /**消耗课时数量**/
	private Long course_cnt;
	/**非DB字段**/
	private String course_name;
	private String student_name;
	private String teacher_name;
	private String teacher_code;
	private String assteacher_name;
	private String assteacher_code;
	
	private Boolean checked;
	
	private Long people_count;
	
	private Long people_checkCount;
	
	private Long people_unCheckCount;
	
	private String remark;
	
	private String title;
	
	private String sort_num;
	
	private String totalSortNum="0";
	
	private int waitPeople;

	private Long class_room_id;
	
	public static enum BMStatuEnum{
		KBM(0l, "可报名"),
		YBM(1l, "已报名"),
		PHZ(2l, "排号中"),
		YME(3l, "已满额");
		private Long code;
		private String desc;
		private BMStatuEnum(Long code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public Long getCode() {
			return code;
		}
		public void setCode(Long code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public static String getDesc(Long code){
			BMStatuEnum[] vals=BMStatuEnum.values();
			for(BMStatuEnum v : vals){
				if(v.getCode()==code){
					return v.getDesc();
				}
			}
			return "";
		}
	}

    public Long getCourse_cnt() {
        return course_cnt;
    }

    public void setCourse_cnt(Long course_cnt) {
        this.course_cnt = course_cnt;
    }

    public int getWaitPeople() {
		return waitPeople;
	}

	public void setWaitPeople(int waitPeople) {
		this.waitPeople = waitPeople;
	}


	public String getTotalSortNum() {
		return totalSortNum;
	}


	public void setTotalSortNum(String totalSortNum) {
		this.totalSortNum = totalSortNum;
	}


	public String getTitle() {
		return title;
	}


	public String getSort_num() {
		return sort_num;
	}


	public void setSort_num(String sort_num) {
		this.sort_num = sort_num;
	}


	public Boolean getChecked() {
		return checked;
	}


	public void setChecked(Boolean checked) {
		this.checked = checked;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Long getPeople_checkCount() {
		return people_checkCount;
	}


	public void setPeople_checkCount(Long people_checkCount) {
		this.people_checkCount = people_checkCount;
	}


	public Long getPeople_unCheckCount() {
		return people_unCheckCount;
	}


	public void setPeople_unCheckCount(Long people_unCheckCount) {
		this.people_unCheckCount = people_unCheckCount;
	}


	public Long getPeople_count() {
		return people_count;
	}


	public void setPeople_count(Long people_count) {
		this.people_count = people_count;
	}


	public Long getCourse_id() {
		return course_id;
	}


	public String getEnd_time() {
		return end_time;
	}


	public String getStart_time() {
		return start_time;
	}

	public Long getStudent_id() {
		return student_id;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Long getBusiness_type() {
		return business_type;
	}


	public final Long getIs_ordered() {
		return is_ordered;
	}


	public final void setIs_ordered(Long is_ordered) {
		this.is_ordered = is_ordered;
	}


	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public Long getCourse_date() {
		return course_date;
	}


	public void setCourse_date(Long course_date) {
		this.course_date = course_date;
	}


	public Long getCourse_times() {
		return course_times;
	}


	public void setCourse_times(Long course_times) {
		this.course_times = course_times;
	}


	public String getCourse_name() {
		return course_name;
	}


	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}


	public String getStudent_name() {
		return student_name;
	}


	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}


	public String getTeacher_name() {
		return teacher_name;
	}


	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}


	public Long getWeek_number() {
		return week_number;
	}


	public void setWeek_number(Long week_number) {
		this.week_number = week_number;
	}


	public Long getBranch_id() {
		return branch_id;
	}


	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}


	public Long getGrade_id() {
		return grade_id;
	}


	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}


	public Long getSubject_id() {
		return subject_id;
	}


	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}


	public String getAttended() {
		return attended;
	}


	public void setAttended(String attended) {
		this.attended = attended;
	}


	public Long getValid_status() {
		return valid_status;
	}


	public void setValid_status(Long valid_status) {
		this.valid_status = valid_status;
	}
	
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public final String getTeacher_code() {
		return teacher_code;
	}


	public final void setTeacher_code(String teacher_code) {
		this.teacher_code = teacher_code;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("课程商品ID： ");
		buff.append(getCourse_id());
		buff.append("，");
		buff.append("一年第几周：");
		buff.append(getWeek_number());
		buff.append("，");
		buff.append("上课日期：");
		buff.append(getCourse_date());
		buff.append("，");
		buff.append("上课时间：");
		buff.append(getStart_time());
		buff.append("，");
		buff.append("下课时间：");
		buff.append(getEnd_time());
		buff.append("，");
		buff.append("课次：");
		buff.append(getCourse_times());
		return buff.toString();
	}

	public String getAssteacher_name() {
		return assteacher_name;
	}

	public void setAssteacher_name(String assteacher_name) {
		this.assteacher_name = assteacher_name;
	}

	public String getAssteacher_code() {
		return assteacher_code;
	}

	public void setAssteacher_code(String assteacher_code) {
		this.assteacher_code = assteacher_code;
	}

	public Long getAssteacher_id() {
		return assteacher_id;
	}

	public void setAssteacher_id(Long assteacher_id) {
		this.assteacher_id = assteacher_id;
	}

	public Long getClass_room_id() {
		return class_room_id;
	}

	public void setClass_room_id(Long class_room_id) {
		this.class_room_id = class_room_id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CourseScheduling that = (CourseScheduling) o;
		if (this.getId().longValue() ==  that.getId().longValue()) return true;
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		if(this.getId()!=null) return this.getId().intValue();
		return super.hashCode();
	}
}
