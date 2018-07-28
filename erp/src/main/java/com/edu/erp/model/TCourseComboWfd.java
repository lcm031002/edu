package com.edu.erp.model;


/**
 * 晚辅导套餐
 *
 */
public class TCourseComboWfd extends BaseObject {
	
	private static final long serialVersionUID = -3419838348397235552L;
	
	// 课程名称
	private String combo_name;
	// 套餐类型
	private Long combo_type;
	// 时长(月数)
	private Double month_count;
	// 课次
	private Long course_count;
	// 价格
	private Double price;
	
	private String img_path;
	
	private String img_src; //base64的图片内容
	
	private Long  branch_id;
	
	private String branch_name;
	
	private Long grade_id;
	
	private String grade_name;
	
	public Long getCombo_type() {
		return combo_type;
	}
	public void setCombo_type(Long combo_type) {
		this.combo_type = combo_type;
	}
	public Double getMonth_count() {
		return month_count;
	}
	public void setMonth_count(Double month_count) {
		this.month_count = month_count;
	}
	public Long getCourse_count() {
		return course_count;
	}
	public void setCourse_count(Long course_count) {
		this.course_count = course_count;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getImg_path() {
		return img_path;
	}
	
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getCombo_name() {
		return combo_name;
	}
	public void setCombo_name(String combo_name) {
		this.combo_name = combo_name;
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
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("套餐名称 ");
		buff.append(getCombo_name());
		buff.append("，");
		buff.append("时长(月数)：");
		buff.append(getMonth_count());
		buff.append("校区：");
		buff.append(getBranch_id());
		buff.append("，");
		buff.append("课次：");
		buff.append(getCourse_count());
		buff.append("，");
		buff.append("价格：");
		buff.append(getPrice());
		buff.append("，");
		buff.append("套餐类型：");
		buff.append(getCombo_type());
		buff.append("，");
		buff.append("图片：");
		buff.append(getImg_path());
		return buff.toString();
	}
}
