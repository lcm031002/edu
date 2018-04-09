package com.edu.erp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edu.erp.dao.TeacherImportDao;

/**
 * @Description 用户校验Excel数据正确性的数据集合
 * @Author zenglw
 */
@Scope("prototype")
@Component("teacherExcelCheckHelperForWrap")
public class TeacherExcelCheckHelperForWrap {

	/**
	 * 数据库中已存在的教师编码集合
	 */
	private Set<String> existEncodings;
	
	/**
	 * 数据库中已存在的电话集合
	 */
	private Set<String> existPhones;
	
	/**
	 * 重复的教师编码集合
	 */
	private Set<String> repeatEncodings;
	
	/**
	 * 重复的电话集合
	 */
	private Set<String> repeatPhones;
	
	/**
	 * 员工姓名，编码，id对象集合
	 */
	private Set<NameEncodingId> employeeNameEncodingId;
	
	/**
	 * 科目
	 */
	private Set<TPSubject> subjectList;

	/**
	 * 该地区下的所有团队
	 */
	private Set<OrganizationInfo> buList;

	/**
	 * excel行记录集合
	 */
	private List<TeacherExcel> teacherExcels;
	
	@Autowired
	private TeacherImportDao teacherImportDao;

	/**
	 * 准备数据
	 * @param city_id
	 * @param bu_id
	 */
	public void initCheckBaseDataForExcel(Long city_id,Long bu_id) {
		this.existEncodings = teacherImportDao.queryExistEncoding(this.getAllTeacherEncodingsFromTeacherExcels(), city_id);
		this.existPhones = teacherImportDao.queryExistPhone(this.getAllPhonesFromTeacherExcels(), city_id);
		this.employeeNameEncodingId = teacherImportDao.queryEmployeeNameEncodingId(this.getAllEmployeeEncodingsFromTeacherExcels());
		this.subjectList = teacherImportDao.querySubjectOfCity(city_id);
		this.buList = teacherImportDao.queryBuOfCity(city_id);
		this.repeatEncodings = getAllRepeatEncodingsFromTeacherExcels();
		this.repeatPhones = getAllRepeatPhonesFromTeacherExcels();
	}
	
	public void initCheckBaseDataForExcel(List<TeacherExcel> teacherExcels,Long city_id,Long bu_id) {
		this.teacherExcels = teacherExcels;
		initCheckBaseDataForExcel( city_id, bu_id);
	}
	
	
	//----------提取excel对象中的数据---- start ----------
	/**
	 * 获取员工的编码用于获取数据库中改员工对应的id
	 * @return
	 */
	private Set<String> getAllEmployeeEncodingsFromTeacherExcels() {
		Set<String> allEmployeeEncoding = new HashSet<>();
		for (TeacherExcel var : teacherExcels) {
			allEmployeeEncoding.add(var.getEmployee_encoding());
		}
		return allEmployeeEncoding;
	}
	
	/**
	 * 获取所有的教师编码
	 * @return
	 */
	private Set<String> getAllTeacherEncodingsFromTeacherExcels() {
		Set<String> allEncodings = new HashSet<>();
		for (TeacherExcel var : teacherExcels) {
			allEncodings.add(var.getTeacher_encoding());
		}
		return allEncodings;
	}
	
	/**
	 * 获取excel中重复的教师编码
	 * @return
	 */
	private Set<String> getAllRepeatEncodingsFromTeacherExcels(){
		Set<String> var1 = new HashSet<>();
		Set<String> ret = new HashSet<>();
		for (TeacherExcel var : teacherExcels) {
			String encoding = var.getTeacher_encoding();
			if(var1.contains(encoding)){
				ret.add(encoding);
			} else {
				var1.add(encoding);
			}
		}
		return ret;
	}
	/**
	 * 获取所有的电话号码
	 * @return
	 */
	private Set<String> getAllPhonesFromTeacherExcels() {
		Set<String> allPhones = new HashSet<>();
		for (TeacherExcel var : teacherExcels) {
			allPhones.add(var.getPhone());
		}
		return allPhones;
	}
	/**
	 * 获取excel中重复的电话号码
	 * @return
	 */
	private Set<String> getAllRepeatPhonesFromTeacherExcels(){
		Set<String> var1 = new HashSet<>();
		Set<String> ret = new HashSet<>();
		for (TeacherExcel var : teacherExcels) {
			String phone = var.getPhone();
			if(var1.contains(phone)){
				ret.add(phone);
			} else {
				var1.add(phone);
			}
		}
		return ret;
	}

	//-----end--------------

	public Set<String> getExistEncodings() {
		return existEncodings;
	}

	public void setExistEncodings(Set<String> existEncodings) {
		this.existEncodings = existEncodings;
	}


	public Set<String> getRepeatEncodings() {
		return repeatEncodings;
	}

	public void setRepeatEncodings(Set<String> repeatEncodings) {
		this.repeatEncodings = repeatEncodings;
	}

	public List<TeacherExcel> getTeacherExcels() {
		return teacherExcels;
	}

	public void setTeacherExcels(List<TeacherExcel> teacherExcels) {
		this.teacherExcels = teacherExcels;
	}

	public Set<String> getExistPhones() {
		return existPhones;
	}

	public void setExistPhones(Set<String> existPhones) {
		this.existPhones = existPhones;
	}

	public Set<String> getRepeatPhones() {
		return repeatPhones;
	}

	public void setRepeatPhones(Set<String> repeatPhones) {
		this.repeatPhones = repeatPhones;
	}

	public Set<NameEncodingId> getEmployeeNameEncodingId() {
		return employeeNameEncodingId;
	}

	public void setEmployeeNameEncodingId(Set<NameEncodingId> employeeNameEncodingId) {
		this.employeeNameEncodingId = employeeNameEncodingId;
	}

	public Set<TPSubject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(Set<TPSubject> subjectList) {
		this.subjectList = subjectList;
	}

	public Set<OrganizationInfo> getBuList() {
		return buList;
	}

	public void setBuList(Set<OrganizationInfo> buList) {
		this.buList = buList;
	}
}
