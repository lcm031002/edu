package com.edu.erp.student.service.impl;

import com.edu.common.qinniu.QiNiuCoreCall;
import java.util.*;

import javax.annotation.Resource;

import com.edu.erp.model.StudentTraceAttach;
import com.edu.erp.model.StudentTraceDetail;
import com.edu.erp.model.StudentTracePlan;
import com.edu.erp.student.service.StudentTraceAttachService;
import com.edu.erp.student.service.StudentTraceDetailService;
import com.edu.erp.student.service.StudentTracePlanService;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.StudentTraceInfoDao;
import com.edu.erp.model.StudentTraceInfo;
import com.edu.erp.student.service.StudentTraceInfoService;
import com.github.pagehelper.Page;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Encoder;

@Service("studentTraceInfoService")
public class StudentTraceInfoServiceImpl implements StudentTraceInfoService {

	@Resource(name = "studentTraceAttachService")
	private StudentTraceAttachService studentTraceAttachService;

	@Resource(name = "studentTraceDetailService")
	private StudentTraceDetailService studentTraceDetailService;

	@Resource(name = "studentTracePlanService")
	private StudentTracePlanService studentTracePlanService;

	@Resource(name = "studentTraceInfoDao")
	private StudentTraceInfoDao studentTraceInfoDao;

	@Override
	public Page<StudentTraceInfo> selectForPage(Map<String, Object> paramMap) throws Exception {
		return studentTraceInfoDao.selectForPage(paramMap);
	}

	@Override
	public StudentTraceInfo queryById(Long id) throws Exception {
		return studentTraceInfoDao.queryById(id);
	}

	@Override
	public void add(StudentTraceInfo studentTraceInfo) throws Exception {
		studentTraceInfoDao.add(studentTraceInfo);
		handleSubTraceInfo(studentTraceInfo, studentTraceInfo.getCreate_user(), studentTraceInfo.getCreate_time());
	}

	@Override
	public void update(StudentTraceInfo studentTraceInfo) throws Exception {
		studentTraceInfoDao.update(studentTraceInfo);
		handleSubTraceInfo(studentTraceInfo, studentTraceInfo.getUpdate_user(), studentTraceInfo.getUpdate_time());
	}

	private void handleSubTraceInfo(StudentTraceInfo studentTraceInfo, Long createUser, Date createTime) throws Exception {
		Long traceId = studentTraceInfo.getId();
		if (!CollectionUtils.isEmpty(studentTraceInfo.getStudentTraceDetailList())) {
			this.studentTraceDetailService.deleteByTraceId(traceId);
			for (StudentTraceDetail studentTraceDetail : studentTraceInfo.getStudentTraceDetailList()) {
				studentTraceDetail.setTraceId(traceId);
				studentTraceDetail.setCreate_time(createTime);
				studentTraceDetail.setCreate_user(createUser);
			}
			this.studentTraceDetailService.addList(studentTraceInfo.getStudentTraceDetailList());
		}

		if (!CollectionUtils.isEmpty(studentTraceInfo.getStudentTracePlanList())) {
			this.studentTracePlanService.deleteByTraceId(traceId);
			for (StudentTracePlan studentTracePlan : studentTraceInfo.getStudentTracePlanList()) {
				studentTracePlan.setTraceId(traceId);
				studentTracePlan.setCreate_time(createTime);
				studentTracePlan.setCreate_user(createUser);
			}
			this.studentTracePlanService.addList(studentTraceInfo.getStudentTracePlanList());
		}
	}

	@Override
	public Map<String, Object> queryCourseInfo(Map<String, Object> paramMap) throws Exception {
		Assert.notNull(paramMap.get("studentId"), "学生编码不能为空！");
		Assert.notNull(paramMap.get("start_date"), "起始日期不能为空！");
		Assert.notNull(paramMap.get("end_date"), "学生编码不能为空！");

		List<Map<String, Object>> courseInfoList = this.studentTraceInfoDao.queryCourseInfo(paramMap);
		if (CollectionUtils.isEmpty(courseInfoList)) {
			throw new Exception("没有教师课堂反馈信息！");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.putAll(paramMap);
		int idx = 0;
		Set<Object> subjectNameSet = new HashSet<Object>();
		for (Map<String, Object> courseInfoMap : courseInfoList) {
			if (idx == 0) {
				resultMap.put("branchName", courseInfoMap.get("branchName"));
				resultMap.put("studentName", courseInfoMap.get("studentName"));
				resultMap.put("counselorName", courseInfoMap.get("counselorName"));
			}
			subjectNameSet.add(courseInfoMap.get("subjectName"));
			idx++;
		}
		String subjecNames = subjectNameSet.toString();
		subjecNames = subjecNames.substring(1, subjecNames.length() - 1);
		resultMap.put("subjectNames", subjecNames);
		resultMap.put("courseTimes", courseInfoList.size());
		resultMap.put("courseInfoList", courseInfoList);

		return resultMap;
	}

	@Override
	public void uploadAttach(CommonsMultipartFile[] studentTraceAttachs, Long traceId, Long userId) throws Exception {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		List<StudentTraceAttach> studentTraceAttachList = new ArrayList<StudentTraceAttach>(studentTraceAttachs.length);
		StudentTraceAttach studentTraceAttach = null;
		for (CommonsMultipartFile file : studentTraceAttachs) {
			String fileName = file.getFileItem().getName();
			String fileData = base64Encoder.encode(file.getBytes());
			String fileUrl = QiNiuCoreCall.getInstance().uploadFileBase64(fileData, fileName);
			studentTraceAttach = new StudentTraceAttach();
			studentTraceAttach.setFileUrl(fileUrl);
			studentTraceAttach.setTraceId(traceId);
			studentTraceAttach.setFileName(fileName);
			studentTraceAttach.setFileType(fileName.substring(fileName.lastIndexOf(".")));
			studentTraceAttachList.add(studentTraceAttach);
		}
		this.studentTraceAttachService.addList(studentTraceAttachList);
	}

	@Override
	public void deleteAttach(Long id) throws Exception {
		this.studentTraceAttachService.deleteById(id);
	}

}
