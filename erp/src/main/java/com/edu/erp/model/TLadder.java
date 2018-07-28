package com.edu.erp.model;

/**
 * 阶梯定义表
 * @ClassName: TLadder
 * @Description: 
 *
 */
public class TLadder extends BaseObject{

	private static final long serialVersionUID = -1587782534223534508L;

	private Long course_ladder_id;  // 课程阶梯ID
    
    private String ladder_level_name;  // 阶梯名称
  		
    private Long level_condition;  //达成条件
  		
    private Long level_price;  // 达成单价
    
    private String img_path; // 图片路径
    
    /** 非数据库字段 */
    private String img_src; //base64的图片内容

	public Long getCourse_ladder_id() {
		return course_ladder_id;
	}

	public void setCourse_ladder_id(Long course_ladder_id) {
		this.course_ladder_id = course_ladder_id;
	}

	public String getLadder_level_name() {
		return ladder_level_name;
	}

	public void setLadder_level_name(String ladder_level_name) {
		this.ladder_level_name = ladder_level_name;
	}

	public Long getLevel_condition() {
		return level_condition;
	}

	public void setLevel_condition(Long level_condition) {
		this.level_condition = level_condition;
	}

	public Long getLevel_price() {
		return level_price;
	}

	public void setLevel_price(Long level_price) {
		this.level_price = level_price;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	
}