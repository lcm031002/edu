package com.edu.erp.model;

import java.util.List;

/**
 * 个性化课程阶梯定义表
 * @ClassName: TCourseLadder
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年4月6日 下午8:41:30
 *
 */
public class TCourseLadder  extends BaseObject{

	private static final long serialVersionUID = -3441511864645287840L;

	private String ladder_name;  // 阶梯名称
  		
    private Long branch_id;  // 校区ID
  		
    private Long grade_id;  // 年级ID
  		
    private Long ladder_type;  // 阶梯类型:1.课程累计课时
    
    private Long ladder_algorithm;  // 阶梯算法:1-通用算法
  		
    /*非DB字段*/
    private String branch_name;  // 校区名称
    
    private String grade_name;  // 年级名称
    
    private List<TLadder> ladderList;  //阶梯列表
    
    private List<TCourseLadderRel> courseLadderRelList;  //适用课程

	public String getLadder_name() {
		return ladder_name;
	}

	public void setLadder_name(String ladder_name) {
		this.ladder_name = ladder_name;
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

	public Long getLadder_type() {
		return ladder_type;
	}

	public void setLadder_type(Long ladder_type) {
		this.ladder_type = ladder_type;
	}

	public Long getLadder_algorithm() {
		return ladder_algorithm;
	}

	public void setLadder_algorithm(Long ladder_algorithm) {
		this.ladder_algorithm = ladder_algorithm;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	
	public List<TLadder> getLadderList() {
		return ladderList;
	}

	public void setLadderList(List<TLadder> ladderList) {
		this.ladderList = ladderList;
	}

	public List<TCourseLadderRel> getCourseLadderRelList() {
		return courseLadderRelList;
	}

	public void setCourseLadderRelList(List<TCourseLadderRel> courseLadderRelList) {
		this.courseLadderRelList = courseLadderRelList;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("ID：");
		buff.append(getId());
		buff.append("，");
		buff.append("阶梯名称： ");
		buff.append(getLadder_name());
		buff.append("，");
		buff.append("年级ID：");
		buff.append(getGrade_id());
		buff.append("，");
		buff.append("校区ID：");
		buff.append(getBranch_id());
		buff.append("，");
		buff.append("阶梯类型：");
		buff.append(getLadder_type());
		buff.append("，");
		buff.append("阶梯算法：");
		buff.append(getLadder_algorithm());
		return buff.toString();
	}
}