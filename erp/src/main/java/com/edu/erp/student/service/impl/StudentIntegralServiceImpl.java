/**  
 * @Title: StudentIntegralServiceImpl.java
 * @Package com.ebusiness.erp.student.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年11月8日 下午5:19:11
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.StudentIntegralDao;
import com.edu.erp.dao.StudentIntegralDetailsDao;
import com.edu.erp.model.StudentIntegral;
import com.edu.erp.model.StudentIntegralDetails;
import com.edu.erp.student.service.StudentIntegralService;
import com.github.pagehelper.Page;

/**
 * @ClassName: StudentIntegralServiceImpl
 * @Description: 学员积分账户服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年11月8日 下午5:19:11
 *
 */
@Service("studentIntegralService")
public class StudentIntegralServiceImpl implements StudentIntegralService{
	
	@Resource(name = "studentIntegralDao")
	private StudentIntegralDao studentIntegralDao;
	
	@Resource(name = "studentIntegralDetailsDao")
	private StudentIntegralDetailsDao studentIntegralDetailsDao;
	
	@Override
	public List<StudentIntegral> queryStudentIntegral(Map<String, Object> param)
			throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		Assert.notNull(param.get("student_id"), "学生ID不能为空！");
		return studentIntegralDao.queryStudentIntegral(param);
	}

	@Override
	public Page<StudentIntegralDetails> queryStudentIntegralDetails(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "参数不能为空！");
		
		//不是按积分账户查询时,必须指定 学生 和 团队
		if(param.get("account_id") == null) {
			Assert.notNull(param.get("student_id"), "学生ID不能为空！");
			Assert.notNull(param.get("bu_id"), "团队ID不能为空！");
		}
		
		return studentIntegralDetailsDao.selectForPage(param);
	}

}
