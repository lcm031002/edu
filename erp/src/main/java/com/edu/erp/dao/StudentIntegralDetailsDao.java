package com.edu.erp.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.StudentIntegralDetails;
import com.github.pagehelper.Page;

/**
 * @ClassName: StudentIntegralDetailsDao
 * @Description: 学生积分账户流水
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年5月18日 下午4:59:26
 *
 */
@Repository(value = "studentIntegralDetailsDao")
public interface StudentIntegralDetailsDao {

	Page<StudentIntegralDetails> selectForPage(Map<String, Object> param) throws Exception;

	Integer saveStudentIntegralHt(StudentIntegralDetails studentIntegralDetails);
}
