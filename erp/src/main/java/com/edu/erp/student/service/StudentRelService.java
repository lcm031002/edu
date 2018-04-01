/**  
 * @Title: StudentRelService.java
 * @Package com.ebusiness.erp.student.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月8日 下午6:13:01
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentRel;

/**
 * @ClassName: StudentRelService
 * @Description: 学员推荐关系服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月8日 下午6:13:01
 *
 */
public interface StudentRelService {
	/**
	 * 
	 * @Title: queryStudentRel
	 * @Description: 查询学员推荐关系
	 * @param param
	 *            STUDENT_ID_OLD：老学员，STUDENT_ID_NEW：新学员
	 * @return 学员推荐关系列表
	 * @throws Exception
	 *             设定文件 List<StudentRel> 返回类型
	 * 
	 */
	List<StudentRel> queryStudentRel(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 
	 * @Title: updateOldStudentRelUsed
	 * @Description: 更新学员关系
	 * @param rel 学员关系
	 * @return 被更新的关系条数
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int updateStudentRel(StudentRel rel)throws Exception;
}
