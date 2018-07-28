package com.edu.erp.dict.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.CourseType;
import com.edu.erp.model.TPCourseGoal;
import com.edu.erp.model.TPCourseModel;
import com.edu.erp.model.TPInvoiceType;
import com.edu.erp.model.TPProductLine;
import com.edu.erp.model.TPSchoolType;
import com.edu.erp.model.TPStudentAttendType;
import com.edu.erp.model.TPSubject;
import com.edu.erp.model.TPTeacherAttendType;

/**
 * 数据字典(原本是单表,现如今拆分成8张表)
 *
 */
public interface DictionaryService {
	
	/**
	 * 课程模型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPCourseModel> selectCourseModel(Map<String, Object> param) throws Exception;
	
	/**
	 * 学生考勤状态List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPStudentAttendType> selectStudentAttendType(Map<String, Object> param) throws Exception;
	
	/**
	 * 发票类型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPInvoiceType> selectInvoiceType(Map<String, Object> param) throws Exception;
	
	/**
	 * 团队产品线List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPProductLine> selectProductLine(Map<String, Object> param) throws Exception;
	
	/**
	 * 学校类型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPSchoolType> selectSchoolType(Map<String, Object> param) throws Exception;
	
	/**
	 * 教师考勤状态List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPTeacherAttendType> selectTeacherAttendType(Map<String, Object> param) throws Exception;
	
	/**
	 * 课程目标List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPCourseGoal> selectCourseGoal(Map<String, Object> param) throws Exception;
	
	/**
	 * 课程科目List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	List<TPSubject> selectSubject(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询课程商品类型
	 * 
	 * @return
	 * @throws Exception
	 */
    List<CourseType> selectCourseTypeList(Map<String, Object> param) throws Exception;
	
}
