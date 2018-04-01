package com.edu.erp.student.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.erp.dao.TOrderCourseDao;
import com.edu.erp.model.TOrderCourse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.dao.StudentScheduleDao;
import com.edu.erp.model.TAttendance;
import com.edu.erp.student.service.StudentScheduleService;

/**
 * 学生课程服务
 * @ClassName: StudentScheduleServiceImpl
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年4月18日 下午3:21:04
 *
 */
@Service("studentScheduleService")
public class StudentScheduleServiceImpl implements StudentScheduleService {
	
	@Resource(name = "studentScheduleDao")
	private StudentScheduleDao studentScheduleDao;

	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;

	@Override
	public List<TAttendance> queryYDYSchedule(Map<String, Object> queryParam) throws Exception {
		Assert.notEmpty(queryParam);
		Assert.notNull(queryParam.get("student_id"));
		//Assert.notNull(queryParam.get("branch_id"));
		Assert.notNull(queryParam.get("start_date"));
		Assert.notNull(queryParam.get("end_date"));
		
		return studentScheduleDao.queryYDYSchedule(queryParam);
	}

	@Override
	public List<TAttendance> queryBJKSchedule(Map<String, Object> queryParam) throws Exception {
		Assert.notEmpty(queryParam);
		Assert.notNull(queryParam.get("student_id"));
		//Assert.notNull(queryParam.get("branch_id"));
		Assert.notNull(queryParam.get("start_date"));
		Assert.notNull(queryParam.get("end_date"));
		
		return studentScheduleDao.queryBJKSchedule(queryParam);
	}

	@Override
	public int queryScheduleCourseTimes(Long orderCourseId,String startTime,String endTime) throws Exception {
		int courseTimes;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Long totalMiniutes= ((format.parse(endTime).getTime() - format.parse(startTime).getTime()) % 86400000) / 60000;  //换算成分钟,一次课排课多少分钟
		TOrderCourse tOrderCourse = tOrderCourseDao.queryOrderCourseById(orderCourseId);
		if(tOrderCourse.getHour_len()==null){
			throw new Exception("课程的课时长度不允许为空!");
		}
		if(totalMiniutes%tOrderCourse.getHour_len()==0){
			courseTimes=(int) (totalMiniutes/tOrderCourse.getHour_len());
		}else{
			throw new Exception("请确保课时长度对应课程长度可有效计算!");
		}

		return courseTimes;
	}

}
