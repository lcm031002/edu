package com.edu.erp.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edu.erp.dao.TeacherImportDao;


/**
 * @Description 前端发起保存数据时的校验
 */
@Scope("prototype")
@Component("teacherExcelCheckerForHtml")
public class TeacherExcelCheckerForHtml {

	private TeacherExcel teacherExcel;
	
	private ExcelHtmlRow excelHtmlRow;
	
	@Autowired
	private TeacherImportDao teacherImportDao;
	
	public ExcelHtmlRow checkTeacherExcelForHtml(TeacherExcel teacherExcel,Long city_id) {
		this.teacherExcel = teacherExcel;
		this.excelHtmlRow = new ExcelHtmlRow();
		excelHtmlRow.getCells().add(checkTeacher_encoding(city_id));
		excelHtmlRow.getCells().add(checkPhone(city_id));
		return this.excelHtmlRow;
	}
 
	public ExcelHtmlCell<String> checkTeacher_encoding(Long city_id) {
		String teacherEncoding = teacherExcel.getTeacher_encoding();
		ExcelHtmlCell<String> cell = new ExcelHtmlCell<>();
		cell.setCellName("teacher_encoding");
		cell.setDisplayValue(teacherEncoding);
		cell.setHoldValue(teacherEncoding);
		if(StringUtils.isEmpty(teacherEncoding)) {
			cell.setCheckErrorMessage("教师编码必填");
		} else {
			int ret = teacherImportDao.isEncodingExisted(teacherEncoding,city_id);
			cell.setCheckErrorMessage(ret>0?"所属地区已存在该教师编码":null);
		}
		return cell;
	}
	
	public ExcelHtmlCell<String> checkPhone(Long city_id) {
		//是否在归属city已经存在电话号码
		String phone = teacherExcel.getPhone();
		ExcelHtmlCell<String> cell = new ExcelHtmlCell<>();
		cell.setCellName("phone");
		cell.setDisplayValue(phone);
		cell.setHoldValue(phone);
		if(StringUtils.isEmpty(phone)) {
			cell.setCheckErrorMessage("电话号码必填");
		} else {
			int ret = teacherImportDao.isPhoneExisted(phone,city_id);
			cell.setCheckErrorMessage(ret>0?"所属地区已存在该电话号码":null);
		}
		return cell;
	}

	public TeacherExcel getTeacherExcel() {
		return teacherExcel;
	}

	public void setTeacherExcel(TeacherExcel teacherExcel) {
		this.teacherExcel = teacherExcel;
	}

	
}
