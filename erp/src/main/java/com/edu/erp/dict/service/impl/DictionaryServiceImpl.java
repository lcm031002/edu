package com.edu.erp.dict.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edu.erp.dao.DictionaryDao;
import com.edu.erp.dict.service.DictionaryService;
import com.edu.erp.model.CourseType;
import com.edu.erp.model.TPCourseGoal;
import com.edu.erp.model.TPCourseModel;
import com.edu.erp.model.TPInvoiceType;
import com.edu.erp.model.TPProductLine;
import com.edu.erp.model.TPSchoolType;
import com.edu.erp.model.TPStudentAttendType;
import com.edu.erp.model.TPSubject;
import com.edu.erp.model.TPTeacherAttendType;

@Service(value = "dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	
	@Resource(name = "dictionaryDao")
	private DictionaryDao dictionaryDao;
	
	/**
	 * 课程模型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPCourseModel> selectCourseModel(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectCourseModel(param);
	}
	
	/**
	 * 学生考勤状态List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPStudentAttendType> selectStudentAttendType(
			Map<String, Object> param) throws Exception {
		return dictionaryDao.selectStudentAttendType(param);
	}
	
	/**
	 * 发票类型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPInvoiceType> selectInvoiceType(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectInvoiceType(param);
	}
	
	/**
	 * 团队产品线List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPProductLine> selectProductLine(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectProductLine(param);
	}
	
	/**
	 * 学校类型List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPSchoolType> selectSchoolType(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectSchoolType(param);
	}
	
	/**
	 * 教师考勤状态List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPTeacherAttendType> selectTeacherAttendType(
			Map<String, Object> param) throws Exception {
		return dictionaryDao.selectTeacherAttendType(param);
	}
	
	/**
	 * 课程目标List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPCourseGoal> selectCourseGoal(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectCourseGoal(param);
	}
	
	/**
	 * 课程科目List
	 * 
	 * @param param 动态参数组装动态SQL
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TPSubject> selectSubject(Map<String, Object> param)
			throws Exception {
		return dictionaryDao.selectSubject(param);
	}

	/**
	 * 查询课程商品类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CourseType> selectCourseTypeList(Map<String, Object> param) throws Exception {
		return dictionaryDao.selectCourseTypeList(param);
	}
}
