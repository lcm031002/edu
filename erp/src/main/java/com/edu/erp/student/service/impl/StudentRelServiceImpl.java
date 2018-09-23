package com.edu.erp.student.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.StudentRelDao;
import com.edu.erp.model.StudentRel;
import com.edu.erp.student.service.StudentRelService;

/**
 *
 * @ClassName: StudentRelServiceImpl
 * @Description: 学员推荐关系服务
 *
 */
@Service("studentRelService")
public class StudentRelServiceImpl implements StudentRelService {

	@Resource(name = "studentRelDao")
	private StudentRelDao studentRelDao;

	@Override
	public List<StudentRel> queryStudentRel(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		return studentRelDao.queryStudentRel(param);
	}

	@Override
	public int updateStudentRel(StudentRel rel) throws Exception {
		Assert.notNull(rel);
		Assert.notNull(rel.getId());
		return studentRelDao.updateStudentRel(rel);
	}

}
