package com.edu.erp.student.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.StudentContactDao;
import com.edu.erp.model.StudentContact;
import com.edu.erp.student.service.StudentContactService;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.SQLUtils;
import com.github.pagehelper.Page;

@Service(value = "studentContactService")
public class StudentContactServiceImpl extends StudentInfoServiceImpl implements
		StudentContactService {

	@Resource(name = "studentContactDao")
	private StudentContactDao studentContactDao;

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	@Override
	public Page<StudentContact> page(Map<String, Object> queryParam)
			throws Exception {
		
		return studentContactDao.selectForPage(queryParam);
	}

	/**
	 * 查询List
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 */
	@Override
	public List<StudentContact> list(Map<String, Object> param)
			throws Exception {
		return studentContactDao.selectList(param);
	}

	/**
	 * 新增
	 * 
	 * @param studentContact
	 * @throws Exception
	 */
	@Override
	public void toAdd(StudentContact studentContact) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		// param.put("student_id", studentContact.getStudent_id());
		// param.put("phone", studentContact.getLink_phone());
		//
		// StudentBusiness studentInfo = new StudentBusiness();
		// studentInfo.setId(studentContact.getStudent_id());
		// StudentBusiness studentBusiness = studentInfoService
		// .queryStudentInfo(studentInfo);
		// if (StringUtils.isNotBlank(studentContact.getLink_phone())
		// && StringUtils.isNotBlank(studentBusiness.getPhone())
		// && !studentBusiness.getPhone().equals(
		// studentContact.getLink_phone())) {
		// if (!checkStudentPhone(param)) {
		// throw new Exception("学生个人手机号码不能与联系人号码重复！");
		// }
		// } else {
		// if (!checkStudentPhone(param)) {
		// throw new Exception("学生个人手机号码不能与联系人号码重复！");
		// }
		// }
		param.clear();
		param.put("student_id", studentContact.getStudent_id());
		param.put("phone", studentContact.getLink_phone());

		if (!checkStudentContactPhone(param)) {
			throw new Exception("联系人手机号码重复");
		}
		//如果联系人姓名不存在，则默认设置为关系名
		if (StringUtils.isEmpty(studentContact.getLink_name())) {
			studentContact.setLink_name(studentContact.getRelation_name());
	    }
		Integer ret = studentContactDao.insert(studentContact);
		if (ret < 1)
			throw new RuntimeException("toAdd_error");
	}

	/**
	 * 根据ID修改
	 * 
	 * 
	 * @param studentContact
	 * @throws Exception
	 */
	@Override
	public void toUpdate(StudentContact studentContact) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("student_id", studentContact.getStudent_id());
		// param.put("phone", studentContact.getLink_phone());
		// if (!checkStudentPhone(param)) {
		// throw new Exception("手机号码重复");
		// }
		param.clear();
		param.put("student_id", studentContact.getStudent_id());
		param.put("phone", studentContact.getLink_phone());
		param.put("id", studentContact.getId());
		if (!checkStudentContactPhone(param)) {
			throw new Exception("手机号码重复");
		}

		Integer ret = studentContactDao.update(studentContact);
		//更新学员的默认联系方式
		studentContactDao.updateDefaultPhone(param);
		if (ret < 1)
			throw new RuntimeException("toUpdate_error");
	}

	/**
	 * 根据ID修改状态
	 * 
	 * 
	 * @param contact_ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void toChangeStatus(String contact_ids, Integer status)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("contact_ids", contact_ids);
		param.put("status", status);
		studentContactDao.changeStatus(param);
	}

}
