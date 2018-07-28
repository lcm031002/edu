package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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
 * @ClassName: DictionaryDao
 * @Description: 数据字典查询服务
 * 
 */
@Repository(value = "dictionaryDao")
public interface DictionaryDao {
	/**
	 * 
	 * @Title: selectCourseModel
	 * @Description: 课程模型
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPCourseModel>    返回类型
	 *
	 */
	List<TPCourseModel> selectCourseModel(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: selectStudentAttendType
	 * @Description: 学生考勤状态
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPStudentAttendType>    返回类型
	 *
	 */
	List<TPStudentAttendType> selectStudentAttendType(Map<String, Object> param)
			throws Exception;

	/**
	 * 
	 * @Title: selectInvoiceType
	 * @Description: 发票类型 
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPInvoiceType>    返回类型
	 *
	 */
	List<TPInvoiceType> selectInvoiceType(Map<String, Object> param)
			throws Exception;

	/**
	 * 
	 * @Title: selectProductLine
	 * @Description: 团队（产品线）
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPProductLine>    返回类型
	 *
	 */
	List<TPProductLine> selectProductLine(Map<String, Object> param)
			throws Exception;

	/**
	 * 
	 * @Title: selectSchoolType
	 * @Description: 学校类型
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPSchoolType>    返回类型
	 *
	 */
	List<TPSchoolType> selectSchoolType(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: selectTeacherAttendType
	 * @Description: 教师考勤状态
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPTeacherAttendType>    返回类型
	 *
	 */
	List<TPTeacherAttendType> selectTeacherAttendType(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: selectCourseGoal
	 * @Description: 课程目标
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPCourseGoal>    返回类型
	 *
	 */
	List<TPCourseGoal> selectCourseGoal(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: selectSubject
	 * @Description: 课程科目
	 * @param param
	 * @return
	 * @throws Exception    设定文件
	 * List<TPSubject>    返回类型
	 *
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
