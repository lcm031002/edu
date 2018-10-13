package com.edu.erp.course_manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.erp.course_manager.controller.ExcelInputController;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.course_manager.service.TempTCourseService;
import com.edu.erp.dao.TCourseDao;
import com.edu.erp.dao.TempTCourseDao;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TempTCourse;
import com.edu.erp.util.ModelDataUtils;
import com.edu.erp.util.StringUtil;

@Service("tempTCourseService")
public class TempTCourseServiceImpl implements TempTCourseService {

	private static final Logger log = Logger.getLogger(TempTCourseServiceImpl.class);
	
	@Resource(name = "tempTCourseDao")
	private TempTCourseDao tempTCourseDao;
	
	@Resource(name = "tCourseDao")
	private TCourseDao tCourseDao;
	
	@Resource(name = "courseService")
	private CourseService courseService;
	
	@Override
	public List<TempTCourse> prepareInputDataCheck(List<Map<String, Object>> dataList) throws Exception {
		//1.设置批次编码
		String batchNo = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, Object> tempRowMap = new HashMap<>();
		TempTCourse tempCourse = null;
		List<TempTCourse> tempTCourseList = new ArrayList<>();
		for (Map<String, Object> rowMap : dataList) {
			rowMap.put("batch_no", batchNo);
			tempRowMap.clear();
			tempRowMap.putAll(rowMap);
			tempCourse = ModelDataUtils.getPojoMatch(TempTCourse.class, tempRowMap);
			tempTCourseList.add(tempCourse);
		}
		try{
			//2.数据插入临时表 
			tempTCourseDao.insertList(tempTCourseList);
			//3.根据Excel提供的信息设置相对应的Id值
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("batch_no", batchNo);
			tempTCourseDao.setIdByCode(paramMap);
			//4.获取数据库最新的数据
			tempTCourseList = tempTCourseDao.selectForList(paramMap);
		} catch (Exception e) {
			throw e;
		} finally {
			//5.删除临时数据
			tempTCourseDao.deleteByBatchNo(batchNo);
		}
		
		return tempTCourseList;
	}

	@Override
	public void checkCourseNoUnique(Long business_type, String course_no) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("business_type", business_type);
		param.put("course_no", course_no);
		List<TCourse> result = tCourseDao.checkExist(param);
		if(StringUtil.isEmpty(result)) {
			return;
		}
		throw new Exception("系统中已存在此课程编码");
	}

	@Override
	public void addImportBjkCourse(TCourse course) throws Exception {
		courseService.toAdd(course);
	}

}
