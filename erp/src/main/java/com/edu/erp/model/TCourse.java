package com.edu.erp.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/***
 * Description : 课程商品 POJO
 * 
 * Author ： junli.zhang
 * 
 * Date : 2014-09-01
 */
public class TCourse extends BaseObject{
	
    /**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -8764541158587877058L;

	private String course_no;  // 课程编号
  		
    private String course_name;  // 课程名称
  		
    private Long business_type;  // 业务模型  1：班级课  2：一对一 3：晚辅导
  		
    private Long branch_id;  // 所属校区
  	
    private String branch_name;  // 校区名称

	private Integer branch_kind; // 校区类型 1：培英精品班 2：小学部 3：中厦合作 4：个性化 5：承诺班 6：艺考班 7：【南昌】小学部 8：合作项目 9：雅思项目 10：产品线测试 11：佳音 12：双师
    
    private Long season_id;  // 课程季
  	
    private String season_name;  // 课程季
    
    private Long grade_id;  // 年级Id
    
    private String updateType;//修改状态
  	
    private String grade_name;  // 年级名称
    
    private Long subject_id;  // 科目
  	
    private String subject_name;  // 科目名称
    
    private Long teacher_id;  // 教师ID
    
    private String teacher_code;//教师编码
     
    private String teacher_name;  // 教师名称
    
    private Long assteacher_id;  // 教师ID
    
    private String assteacher_code;//教师编码
    
    private String assteacher_name;  // 教师名称

    private Long unit_price;  // 课时单价
  		
    private Long sum_price;  // 课程总价
  		
    private Double hour_len;  // 课时长度
  		
    private Long course_count;  // 课时数量
  		
    private Long product_type;  // 销售类型 1：正价 2：促销 3：赠送
    
    private Long product_type_name;  // 销售类型名称
  		
    private Long verify_status;  // 审批状态 0：未审批  1：审批中  2：已通过 3：未通过
  		
    private String verify_no;  // 审批编号
  		
    private Date verify_time;  // 审批时间
  		
    private Long verify_user;  // 最后审批人
  		
    private String validaty_date;  // 上架时间:同审批通过时间
  		
    private String invalidity_date;  // 下架日期
  		
    private String start_date;  // 开课日期
  		
    private String end_date;  // 结课日期
  		
    private String start_time;  // 上课时间
  		
    private String end_time;  // 下课时间
  		
    private Long hour_len2;  // 课时长度(分钟)
  		
    private Long course_surplus;  // 剩余课时
  		
    private Long people_count;  // 计划上课人数
  		
    private Long actual_people_count;  // 实际上课人数
  		
    private String attend_class_period;  // 上课周期(1,3,5)
  		
    private Long course_type;  // 同类课程商品
    
    private String type_name;  // 同类课程商品
  		
    private String description;  // 描述
  		
    private Long proceed_status;  // 进行状态 1：未开始  2：进行中  3：已结束
  	
    private Long product_line; // 产品线
    
    private String product_line_name; // 产品线名称
    
    private Long target; // 课程目标
    
    private String target_name; // 课程目标名称 
    
    private String yk_lastEndDate;
    
    private Long studentId; 
    
    private Teacher teacher;
    
    private Long is_textbooks;
    
    private Long is_cramclass;   //是否补课（1：是；0：否）
    
    private String is_cramclass_name;   //是否补课 名称 （导入时使用）
    
    private String is_textbooks_name; //教材状态 名称
    
    private Long num_textbooks;
    
    private Long more_teacher_courseid;

	private String more_teacher_courseName;
    
    private Long student_num;
    
    private int short_or_long;

	private Long is_all;
    
    private String status_name; // 状态名称
    
    private Integer performance_belong_type;//业绩归属类型
    
    private String performance_belong_type_name;//业绩归属类型

	private Integer org; // 机构编号

	private Integer season; // 季节

	private Long course_id;

	private String first_start_time; // 第一次开课时间
	private String last_end_time; // 最后一次结课时间
	private String operation_type; // 运营类型 0-直营 1-直营+加盟

	private Long lecturer_id; // 主讲老师ID
	private String more_teacher_course_type; // 双师课程类型
    
    private List<CourseScheduling> courseSchedulingList = new ArrayList<CourseScheduling>();

	private Set<RoomClass> roomClassSet;//课次教室去重集合

    public final String getYk_lastEndDate() {
		return yk_lastEndDate;
	}

	public final void setYk_lastEndDate(String yk_lastEndDate) {
		this.yk_lastEndDate = yk_lastEndDate;
	}

	//	是否需要审批
	private Integer is_approve;
	private String teacherPhoto;

    
    public Integer getIs_approve() {
		return is_approve;
	}

	public void setIs_approve(Integer is_approve) {
		this.is_approve = is_approve;
	}

	// 课程校区权限关系表
    private List<TCourseSchool> courseSchoolRefs ;
    
    // 课程关联的校区
    private List<OrganizationInfo> branchInfos;
    
    // 课程关联的校区的id的逗号分隔的字符串
    private String relationIds;

    private Long next_course_time_id;

    private Long next_class_room_id;

    //是否包含订单课程，临时订单课程，作废订单课程等
    private Boolean hasOrderCourse;
    
	public static enum isApproveEnum{
		NOTNEEDAPPROVE(0, "不需要审批"),
		NEEDAPPROVE(1, "需要审批");
		private Integer code;
		private String desc;
		private isApproveEnum(Integer code, String desc){
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
    
    // 业务模式
    public static enum BusinessTypeEnum{
		BJK(1, "班级课"),
		YDY(2, "一对一"),
		WFD(3, "晚辅导");
		private Integer code;
		private String desc;
		private BusinessTypeEnum(Integer code, String desc){
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
    // 销售类型
    public static enum ProductTypeEnum{
    	NORMAL(1, "正常"),
    	PROMOTION(2, "促销"),
    	GIVE(3, "赠送");
    	private Integer code;
    	private String desc;
    	private ProductTypeEnum(Integer code, String desc){
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
    // 进行状态
    public static enum ProceedStatusEnum{
    	NOT(1, "未开始"),
    	ING(2, "进行中"),
    	OVER(3, "已结束");
    	private Integer code;
    	private String desc;
    	private ProceedStatusEnum(Integer code, String desc){
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
    
	public String getCourse_no() {
		return course_no;
	}

	public void setCourse_no(String course_no) {
		this.course_no = course_no;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Long business_type) {
		this.business_type = business_type;
	}

	public final Long getStudentId() {
		return studentId;
	}

	public final void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Long getSeason_id() {
		return season_id;
	}

	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
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

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Long getUnit_price() {
		return unit_price;
	}


	public Long getSum_price() {
		return sum_price;
	}

	public void setSum_price(Long sum_price) {
		this.sum_price = sum_price;
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

	public Long getCourse_count() {
		return course_count;
	}

	public void setCourse_count(Long course_count) {
		this.course_count = course_count;
	}

	public Long getProduct_type() {
		return product_type;
	}

	public void setProduct_type(Long product_type) {
		this.product_type = product_type;
	}

	public Long getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(Long verify_status) {
		this.verify_status = verify_status;
	}

	public String getVerify_no() {
		return verify_no;
	}

	public void setVerify_no(String verify_no) {
		this.verify_no = verify_no;
	}

	public Date getVerify_time() {
		return verify_time;
	}

	public void setVerify_time(Date verify_time) {
		this.verify_time = verify_time;
	}

	public Long getVerify_user() {
		return verify_user;
	}

	public void setVerify_user(Long verify_user) {
		this.verify_user = verify_user;
	}

	public String getValidaty_date() {
		return validaty_date;
	}

	public void setValidaty_date(String validaty_date) {
		this.validaty_date = validaty_date;
	}

	public String getInvalidity_date() {
		return invalidity_date;
	}

	public void setInvalidity_date(String invalidity_date) {
		this.invalidity_date = invalidity_date;
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

	public Long getHour_len2() {
		return hour_len2;
	}

	public void setHour_len2(Long hour_len2) {
		this.hour_len2 = hour_len2;
	}

	public Long getCourse_surplus() {
		return course_surplus;
	}

	public void setCourse_surplus(Long course_surplus) {
		this.course_surplus = course_surplus;
	}

	public Long getPeople_count() {
		return people_count;
	}

	public void setPeople_count(Long people_count) {
		this.people_count = people_count;
	}

	public Long getActual_people_count() {
		return actual_people_count;
	}

	public void setActual_people_count(Long actual_people_count) {
		this.actual_people_count = actual_people_count;
	}

	public String getAttend_class_period() {
		return attend_class_period;
	}

	public void setAttend_class_period(String attend_class_period) {
		this.attend_class_period = attend_class_period;
	}

	public Long getCourse_type() {
		return course_type;
	}

	public void setCourse_type(Long course_type) {
		this.course_type = course_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProceed_status() {
		return proceed_status;
	}

	public void setProceed_status(Long proceed_status) {
		this.proceed_status = proceed_status;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public Integer getBranch_kind() {
		return branch_kind;
	}

	public void setBranch_kind(Integer branch_kind) {
		this.branch_kind = branch_kind;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public List<TCourseSchool> getCourseSchoolRefs() {
		return courseSchoolRefs;
	}

	public void setCourseSchoolRefs(List<TCourseSchool> courseSchoolRefs) {
		this.courseSchoolRefs = courseSchoolRefs;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public Long getProduct_line() {
		return product_line;
	}

	public void setProduct_line(Long product_line) {
		this.product_line = product_line;
	}

	public Long getTarget() {
		return target;
	}

	public void setTarget(Long target) {
		this.target = target;
	}


	public final String getTeacherPhoto() {
		return teacherPhoto;
	}

	public final void setTeacherPhoto(String teacherPhoto) {
		this.teacherPhoto = teacherPhoto;
	}

	public Integer getOrg() {
		return org;
	}

	public void setOrg(Integer org) {
		this.org = org;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public String toBjkString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("课程编号： ");
		buff.append(getCourse_no());
		buff.append("，");
		buff.append("课程名称：");
		buff.append(getCourse_name());
		buff.append("，");
		buff.append("年级ID：");
		buff.append(getGrade_id());
		buff.append("，");
		buff.append("科目ID：");
		buff.append(getSubject_id());
		buff.append("，");
		buff.append("课程季ID：");
		buff.append(getSeason_id());
		buff.append("，");
		buff.append("教师ID：");
		buff.append(getTeacher_id());
		buff.append("，");
		buff.append("开课日期：");
		buff.append(getStart_date());
		buff.append("，");
		buff.append("结课日期：");
		buff.append(getEnd_date());
		buff.append("，");
		buff.append("单价：");
		buff.append(getUnit_price());
		buff.append("，");
		buff.append("上课时间：");
		buff.append(getStart_time());
		buff.append("，");
		buff.append("下课时间：");
		buff.append(getEnd_time());
		buff.append("，");
		buff.append("课时长度：");
		buff.append(getHour_len2());
		buff.append("，");
		buff.append("上课周期：");
		buff.append(getAttend_class_period());
		buff.append("，");
		buff.append("课时数量：");
		buff.append(getCourse_count());
		buff.append("，");
		buff.append("剩余课时：");
		buff.append(getCourse_surplus());
		buff.append("，");
		buff.append("计划人数：");
		buff.append(getPeople_count());
		buff.append("，");
		buff.append("实际人数：");
		buff.append(getActual_people_count());
		buff.append("，");
		buff.append("销售类型：");
		buff.append(getProduct_type());
		buff.append("，");
		buff.append("课程类型：");
		buff.append(getCourse_type());
		buff.append("，");
		buff.append("课程目标ID：");
		buff.append(getTarget());
		buff.append("，");
		buff.append("产品线ID：");
		buff.append(getProduct_line());
		buff.append("，");
		buff.append("校区ID：");
		buff.append(getBranch_id());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}

	public String toWfdString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("课程编号： ");
		buff.append(getCourse_no());
		buff.append("，");
		buff.append("课程名称：");
		buff.append(getCourse_name());
		buff.append("，");
		buff.append("开课日期：");
		buff.append(getStart_date());
		buff.append("，");
		buff.append("结课日期：");
		buff.append(getEnd_date());
		buff.append("，");
		buff.append("单价：");
		buff.append(getUnit_price());
		buff.append("，");
		buff.append("总价：");
		buff.append(getSum_price());
		buff.append("，");
		buff.append("课时数量：");
		buff.append(getCourse_count());
		buff.append("，");
		buff.append("有效天数：");
		buff.append(getCourse_surplus());
		buff.append("，");
		buff.append("校区ID：");
		buff.append(getBranch_id());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}
	
	public String toYdyString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("课程编号： ");
		buff.append(getCourse_no());
		buff.append("，");
		buff.append("课程名称：");
		buff.append(getCourse_name());
		buff.append("，");
		buff.append("年级ID：");
		buff.append(getGrade_id());
		buff.append("，");
		buff.append("开课日期：");
		buff.append(getStart_date());
		buff.append("，");
		buff.append("结课日期：");
		buff.append(getEnd_date());
		buff.append("，");
		buff.append("单价：");
		buff.append(getUnit_price());
		buff.append("，");
		buff.append("课时长度：");
		buff.append(getHour_len2());
		buff.append("，");
		buff.append("销售类型：");
		buff.append(getProduct_type());
		buff.append("，");
		buff.append("校区ID：");
		buff.append(getBranch_id());
		buff.append("，");
		buff.append("描述：");
		buff.append(getDescription());
		return buff.toString();
	}

	public final List<CourseScheduling> getCourseSchedulingList() {
		return courseSchedulingList;
	}

	public final void setCourseSchedulingList(
			List<CourseScheduling> courseSchedulingList) {
		this.courseSchedulingList = courseSchedulingList;
	}

	public final Teacher getTeacher() {
		return teacher;
	}

	public final void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public final String getTeacher_code() {
		return teacher_code;
	}

	public final void setTeacher_code(String teacher_code) {
		this.teacher_code = teacher_code;
	}

	public final Long getIs_textbooks() {
		return is_textbooks;
	}

	public final void setIs_textbooks(Long is_textbooks) {
		this.is_textbooks = is_textbooks;
	}

	public final Long getNum_textbooks() {
		return num_textbooks;
	}

	public final void setNum_textbooks(Long num_textbooks) {
		this.num_textbooks = num_textbooks;
	}

	public final Long getMore_teacher_courseid() {
		return more_teacher_courseid;
	}

	public final void setMore_teacher_courseid(Long more_teacher_courseid) {
		this.more_teacher_courseid = more_teacher_courseid;
	}

	public final String getUpdateType() {
		return updateType;
	}

	public final void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public final Long getStudent_num() {
		return student_num;
	}

	public final void setStudent_num(Long student_num) {
		this.student_num = student_num;
	}

	public int getShort_or_long() {
		return short_or_long;
	}

	public void setShort_or_long(int short_or_long) {
		this.short_or_long = short_or_long;
	}

	public Long getIs_cramclass() {
		return is_cramclass;
	}

	public void setIs_cramclass(Long is_cramclass) {
		this.is_cramclass = is_cramclass;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public String getProduct_line_name() {
		return product_line_name;
	}

	public void setProduct_line_name(String product_line_name) {
		this.product_line_name = product_line_name;
	}

	public Long getProduct_type_name() {
		return product_type_name;
	}

	public void setProduct_type_name(Long product_type_name) {
		this.product_type_name = product_type_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getIs_textbooks_name() {
		return is_textbooks_name;
	}

	public void setIs_textbooks_name(String is_textbooks_name) {
		this.is_textbooks_name = is_textbooks_name;
	}

	public String getIs_cramclass_name() {
		return is_cramclass_name;
	}

	public void setIs_cramclass_name(String is_cramclass_name) {
		this.is_cramclass_name = is_cramclass_name;
	}

	public List<OrganizationInfo> getBranchInfos() {
		return branchInfos;
	}

	public void setBranchInfos(List<OrganizationInfo> branchInfos) {
		this.branchInfos = branchInfos;
	}

	public String getRelationIds() {
		return relationIds;
	}

	public void setRelationIds(String relationIds) {
		this.relationIds = relationIds;
	}

	public Long getAssteacher_id() {
		return assteacher_id;
	}

	public void setAssteacher_id(Long assteacher_id) {
		this.assteacher_id = assteacher_id;
	}

	public String getAssteacher_code() {
		return assteacher_code;
	}

	public void setAssteacher_code(String assteacher_code) {
		this.assteacher_code = assteacher_code;
	}

	public String getAssteacher_name() {
		return assteacher_name;
	}

	public void setAssteacher_name(String assteacher_name) {
		this.assteacher_name = assteacher_name;
	}

	public Integer getPerformance_belong_type() {
		return performance_belong_type;
	}

	public void setPerformance_belong_type(Integer performance_belong_type) {
		this.performance_belong_type = performance_belong_type;
	}

	public String getPerformance_belong_type_name() {
		return performance_belong_type_name;
	}

	public void setPerformance_belong_type_name(String performance_belong_type_name) {
		this.performance_belong_type_name = performance_belong_type_name;
	}

	public Long getIs_all() {
		return is_all;
	}

	public void setIs_all(Long is_all) {
		this.is_all = is_all;
	}

	public String getMore_teacher_courseName() {
		return more_teacher_courseName;
	}

	public void setMore_teacher_courseName(String more_teacher_courseName) {
		this.more_teacher_courseName = more_teacher_courseName;
	}

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getNext_course_time_id() {
		return next_course_time_id;
	}

	public void setNext_course_time_id(Long next_course_time_id) {
		this.next_course_time_id = next_course_time_id;
	}

	public Long getNext_class_room_id() {
		return next_class_room_id;
	}

	public void setNext_class_room_id(Long next_class_room_id) {
		this.next_class_room_id = next_class_room_id;
	}

	public String getFirst_start_time() {
		return first_start_time;
	}

	public void setFirst_start_time(String first_start_time) {
		this.first_start_time = first_start_time;
	}

	public String getLast_end_time() {
		return last_end_time;
	}

	public void setLast_end_time(String last_end_time) {
		this.last_end_time = last_end_time;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public Set<RoomClass> getRoomClassSet() {
		return roomClassSet;
	}

	public void setRoomClassSet(Set<RoomClass> roomClassSet) {
		this.roomClassSet = roomClassSet;
	}

	public Long getLecturer_id() {
		return lecturer_id;
	}

	public void setLecturer_id(Long lecturer_id) {
		this.lecturer_id = lecturer_id;
	}

	public String getMore_teacher_course_type() {
		return more_teacher_course_type;
	}

	public void setMore_teacher_course_type(String more_teacher_course_type) {
		this.more_teacher_course_type = more_teacher_course_type;
	}

	public Boolean getHasOrderCourse() {
		return hasOrderCourse;
	}

	public void setHasOrderCourse(Boolean hasOrderCourse) {
		this.hasOrderCourse = hasOrderCourse;
	}
}