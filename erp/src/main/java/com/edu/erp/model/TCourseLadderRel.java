package com.edu.erp.model;

/**
 * 课程阶梯关系表
 * @ClassName: TCourseLadderRel
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年4月6日 下午8:41:30
 *
 */
public class TCourseLadderRel  extends BaseObject{

	private static final long serialVersionUID = -3441511864645287840L;

    private Long course_id;  // 课程ID
    
    private Long course_ladder_id;  // 课程阶梯ID
  		
    /*非DB字段*/
    private String course_code;  // 课程编码
    
    private String course_name;  // 课程名称
    
    private Long is_course_book;	//课程是否已报班（1：是；0：否）

	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}

	public Long getCourse_ladder_id() {
		return course_ladder_id;
	}

	public void setCourse_ladder_id(Long course_ladder_id) {
		this.course_ladder_id = course_ladder_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Long getIs_course_book() {
		return is_course_book;
	}

	public void setIs_course_book(Long is_course_book) {
		this.is_course_book = is_course_book;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
    
}