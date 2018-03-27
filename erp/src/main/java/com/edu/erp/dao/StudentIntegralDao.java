/**  
 * @Title: StudentIntegralDao.java
 * @Package com.modules.student_manager.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2015年3月24日 下午2:31:02
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.StudentIntegral;

/**
 * @ClassName: StudentIntegralDao
 * @Description: 学生积分账户
 * @author zhuliyong zly@entstudy.com
 * @date 2015年3月24日 下午2:31:02
 *
 */
@Repository(value = "studentIntegralDao")
public interface StudentIntegralDao {

	/**
	 * 
	 * @Title: queryStudentIntegral
	 * 
	 * @Description: 查询学员的积分账户
	 * 
	 * @param param
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return int 返回类型
	 */
	List<StudentIntegral> queryStudentIntegral(Map<String, Object> param) throws Exception;

    StudentIntegral getIntegral(@Param("studentId") Long studentId, @Param("branchId") Long branchId);

    Integer saveIntegral(StudentIntegral studentIntegral);

    Integer updateIntegral(StudentIntegral studentIntegral);
}
