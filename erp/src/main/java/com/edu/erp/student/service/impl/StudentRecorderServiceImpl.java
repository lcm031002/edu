package com.edu.erp.student.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.erp.model.StudentGradeReviseNote;
import com.edu.erp.model.StudentNameReviseNote;
import com.edu.erp.model.StudentStatusReviseNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.StudentRecorderDao;
import com.edu.erp.model.StudentRecorder;
import com.edu.erp.student.service.StudentRecorderService;
import com.github.pagehelper.Page;

@Service("studentRecorderService")
public class StudentRecorderServiceImpl implements StudentRecorderService {

	@Autowired
	private StudentRecorderDao studentRecorderDao;
	
	@Override
	public void insertStudentRecorder(StudentRecorder studentRecorder) {
		//1.查询最近的报备人信息
		StudentRecorder newestRecorder = studentRecorderDao.queryNewestRecorder(studentRecorder.getStudentId());
		//2.判断是否存在历史报备人。存在：判断时间是否重叠；不存在：直接添加报备人
		if(null != newestRecorder) {
			Date currentDate = studentRecorder.getStartDate();
			Date prevEndDate = newestRecorder.getEndDate();
			if(currentDate.after(prevEndDate)) {
				//当前时间在上个报备人的失效时间之后，直接添加报备人
				studentRecorderDao.insertStudentRecorder(studentRecorder);
			} else {
				//时间有重叠时，去除有效报备人判断，直接改变之前报备人时间。最近两个报备人时间前后衔接
//				throw new RuntimeException("当前存在有效报备人，不允许添加");
				Date prevStartDate = newestRecorder.getStartDate();
				if(currentDate.after(prevStartDate)) {//如果当前时间在上个报备人的开始时间之后，将上个报备人的结束时间设置为当前时间-1，并新增报备人信息
					//更新上个报备人的失效时间
					newestRecorder.setEndDate(DateUtil.addDate(currentDate, -1));
					newestRecorder.setUpdateUser(studentRecorder.getCreateUser());
					newestRecorder.setUpdateTime(studentRecorder.getCreateTime());
					studentRecorderDao.updateStudentRecorder(newestRecorder);
					//插入新的报备人信息
					studentRecorderDao.insertStudentRecorder(studentRecorder);
				} else {
					throw new RuntimeException("新增报备人的开始时间早于或等于上个报备人的开始时间");
				}
			}
		} else {
			studentRecorderDao.insertStudentRecorder(studentRecorder);
		}
	}

	@Override
	public Page<StudentRecorder> queryStudentRecorderList(Map<String,Object> studentRecorder) {
		Assert.notNull(studentRecorder.get("studentId"),"学员ID不能为空");
		return studentRecorderDao.queryStudentRecorderPage(studentRecorder);
	}
	

	@Override
	public List<Map<String, Object>> queryRecorder(String nameOrEncoding,Long city_id) {
		return studentRecorderDao.queryRecorder(nameOrEncoding,city_id);
	}


	@Override
	public List<StudentNameReviseNote> queryStudentNameRecord(Long studentId) {
		return studentRecorderDao.queryStudentNameRecord(studentId);
	}

	@Override
	public List<StudentGradeReviseNote> queryStudentGradeRecord(Long studentId) {
		return studentRecorderDao.queryStudentGradeRecord(studentId);
	}

	@Override
	public List<StudentStatusReviseNote> queryStudentStatusRecord(Map<String, Object> queryMap) {
		Long productLine = studentRecorderDao.queryProductLine(queryMap);
		List<StudentStatusReviseNote> studentStatusReviseNoteList = studentRecorderDao.queryStudentStatusRecord(queryMap);
        for(StudentStatusReviseNote studentStatusReviseNote : studentStatusReviseNoteList){
        	Integer beforeStatus = studentStatusReviseNote.getBeforeStatus();
            String beforeStatusName = studentRecorderDao.queryStatusName(beforeStatus,productLine);
            studentStatusReviseNote.setBeforeStatusName(beforeStatusName);
            Integer afterStatus = studentStatusReviseNote.getAfterStatus();
            String  afterStatusName = studentRecorderDao.queryStatusName(afterStatus,productLine);
            studentStatusReviseNote.setAfterStatusName(afterStatusName);
		}
        return  studentStatusReviseNoteList;
	}
}
