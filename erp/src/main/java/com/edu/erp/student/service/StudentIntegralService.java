package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentIntegral;
import com.edu.erp.model.StudentIntegralDetails;
import com.github.pagehelper.Page;

/**
 * @ClassName: StudentIntegralService
 * @Description: 学员积分账户服务
 *
 */
public interface StudentIntegralService {
	/**
	 * 
	 * @Title: queryStudentIntegral
	 * @Description: 学员积分账户
	 * @param param
	 *            student_id:学员id
	 * @return 返回学员积分账户
	 * @throws Exception
	 *             设定文件 List<StudentIntegral> 返回类型
	 * 
	 */
	List<StudentIntegral> queryStudentIntegral(Map<String, Object> param) throws Exception;
	
	Page<StudentIntegralDetails> queryStudentIntegralDetails(Map<String, Object> param) throws Exception;
}
