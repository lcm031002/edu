/**  
 * @Title: StudentRelDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月8日 下午6:10:13
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentRel;

/**
 * @ClassName: StudentRelDao
 * @Description: 学员推荐关系列表
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月8日 下午6:10:13
 * 
 */
public interface StudentRelDao {
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
	 * @Title: updateStudentRel
	 * @Description: 更新学员关系
	 * @param rel 学员关系
	 * @return 被更新的学员关系条数
	 * @throws Exception    设定文件
	 * int    返回类型
	 *
	 */
	int updateStudentRel(StudentRel rel) throws Exception;
}
