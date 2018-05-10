/**  
 * @Title: CourseServiceImpl.java
 * @Package com.ebusiness.erp.course_manager.service.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月17日 下午5:47:17
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.course_manager.service.impl;

import com.edu.common.util.NumberUtils;
import com.edu.common.constants.Constants;
import com.edu.common.constants.Constants.OrgKind;
import com.edu.erp.model.TMoreTeacherCourse.MoreTeacherCourseTypeEnum;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.edu.erp.course_manager.business.SchedulingAssist;
import com.edu.erp.dao.TCourseListeningDao;
import com.edu.erp.model.*;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.util.ThreadLocalContainer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edu.common.util.DateUtil;
import com.edu.common.util.ERPConstants;
import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.dao.CourseSchedulingDao;
import com.edu.erp.dao.TCourseDao;
import com.edu.erp.dao.TCourseSchedulingAssistDao;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.dict.util.OrganizationUtils;
import com.edu.erp.teacher_manager.service.TeacherInfoService;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

/**
 * @ClassName: CourseServiceImpl
 * @Description: 课程查询服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月17日 下午5:47:17
 * 
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

	private static final Logger log = Logger.getLogger(CourseServiceImpl.class);

	@Resource(name = "tCourseDao")
	private TCourseDao tCourseDao;

	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;

	@Resource(name = "teacherInfoService")
	private TeacherInfoService teacherInfoService;

	@Resource(name = "tCourseSchedulingAssistDao")
	private TCourseSchedulingAssistDao tCourseSchedulingAssistDao;

	@Resource(name = "organizationService")
	private OrganizationService organizationService;

	@Resource(name = "courseSchedulingDao")
	private CourseSchedulingDao courseSchedulingDao;

	@Resource(name = "tCourseListeningDao")
	private TCourseListeningDao tCourseListeningDao;

	@Resource(name = "orderService")
	private OrderService orderService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#page(com.github
	 * .pagehelper.Page)
	 */
	@Override
	public Page<TCourse> page(Page<TCourse> page) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#list(java.util
	 * .Map)
	 */
	@Override
	public List<TCourse> list(Map<String, Object> param) throws Exception {
		return tCourseDao.selectList(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#toAdd(com.
	 * ebusiness .erp.model.TCourse, java.lang.String)
	 */
	@Override
	public void toAdd(TCourse course) throws Exception {
		Assert.notNull(course);
		Assert.notNull(course.getCourse_name(),"课程名称必填!");
		Assert.notNull(course.getBusiness_type());
		Assert.notNull(course.getCity_id());
		Assert.notNull(course.getBranch_id(),"校区必填!");
		Assert.notNull(course.getGrade_id(),"年级必填!");
		Assert.notNull(course.getSubject_id(),"科目必填!");
		Assert.notNull(course.getTeacher_id(),"上课老师必填!");
		Assert.notNull(course.getUnit_price(),
			"单价必填!");
		Assert.notNull(course.getCourse_count(),"课时数量必填!");
		Assert.notNull(course.getStart_date(),"开课日期必填!");
		Assert.notNull(course.getEnd_date(),"结课日期必填!");
		Assert.notNull(course.getStart_time(),"上课时间必填!");
		Assert.notNull(course.getEnd_time(),"下课时间必填!");
		Assert.notNull(course.getAttend_class_period(),"上课周期必填!");
		Assert.notNull(course.getPerformance_belong_type(),"业绩校区类型必填!");
		Assert.notNull(course.getPeople_count(), "计划人数必填!");
		//通过校区得到产品线
		Long branch_id=course.getBranch_id();
		OrganizationInfo organizationInfo = this.organizationService.selectById(branch_id);
		Long productLine = organizationInfo.getProduct_line();
        course.setProduct_line(organizationInfo.getProduct_line());
		// 新增班级课
		Integer ret = tCourseDao.saveTCourse(course);
		if (ret < 1) {
			throw new RuntimeException("toAdd_error");
		}
		// 默认进行排课
		schedulingCourse(course.getId());

		checkCourseTimes(course);

		Long isTextBooks = course.getIs_textbooks();
		// 由于课程预售场景，该校验暂时去掉
//		if (isTextBooks == null || isTextBooks.longValue() != 1) {
//			checkTeacherSchedule(course.getId());
//		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#toUpdate(com.
	 * ebusiness .erp.model.TCourse, java.lang.String)
	 */
	@Override
	public void toUpdate(TCourse course) throws Exception {
		Assert.notNull(course);
		Assert.notNull(course.getCourse_name());
		Assert.notNull(course.getBusiness_type());
		Assert.notNull(course.getCity_id());
		Assert.notNull(course.getBranch_id());
		Assert.notNull(course.getGrade_id());
		Assert.notNull(course.getStart_date());
		Assert.notNull(course.getSubject_id());
		Assert.notNull(course.getTeacher_id());
		Assert.notNull(course.getUnit_price());
		Assert.notNull(course.getCourse_count());
		Assert.notNull(course.getStart_date());
		Assert.notNull(course.getEnd_date());
		Assert.notNull(course.getStart_time());
		Assert.notNull(course.getEnd_time());
		Assert.notNull(course.getAttend_class_period());
		//通过校区得到产品线
		Long branch_id=course.getBranch_id();
		OrganizationInfo organizationInfo = this.organizationService.selectById(branch_id);
		Long productLine = organizationInfo.getProduct_line();
		if(productLine!=11L){
			if(course.getSeason_id()==null){
				throw new Exception("课程季不能为空");
			}
		}
		tCourseDao.updateTCourse(course);
		schedulingCourse(course.getId());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#toChangeStatus
	 * (java.lang.String, java.lang.Integer)
	 */
	@Override
	public void toChangeStatus(String ids, String status, Long userId) throws Exception {
		Assert.hasText(ids,"未选中记录");
		Assert.hasText(status);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("status", status);
		params.put("updateUser", userId);
		tCourseDao.changeStatus(params);
		// 删除状态 则删除关联表数据
		if (status.equals(TCourse.StatusEnum.DELETE.getCode())) {
			tCourseDao.removeOrgRef(ids);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#toChgIsApprove
	 * (java.lang.String, java.lang.Integer)
	 */
	@Override
	public void toChgIsApprove(String ids, Integer status) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#toChangePrice(
	 * java.lang.Long, java.lang.Double, java.lang.Double)
	 */
	@Override
	public void toChangePrice(Long course_id, Double unit_price, Double total_price) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#queryCourseByPack
	 * (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public List<TCourse> queryCourseByPack(TCourse courseBusiness) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCourseByTearcher (java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TCourse> queryCourseByTearcher(Long tearcherId, Long studentId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCourseByBusiness (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public List<TCourse> queryCourseByBusiness(Map<String, Object> courseCondition) throws Exception {
		Assert.notNull(courseCondition);
		Assert.notNull(courseCondition.get("business_type"));
		return tCourseDao.queryCourseByBusiness(courseCondition);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#schedulingCourse
	 * (java.lang.Long)
	 */
	@Override
	public Map<String, Object> schedulingCourse(Long courseId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("course_id", courseId);
		map.put("error_code", 0);
		map.put("error_desc", "");

		TCourse tCourse =tCourseDao.getCourseById(courseId);
		//tCourseDao.schedulingCourse(map);
		//添加班级课才排课判断
		if(tCourse.getBusiness_type().equals(Long.valueOf(TCourse.BusinessTypeEnum.BJK.getCode()))){
		     courseSchedulingService.scheduleCourse(courseId,null);
		}
		Map<String, Object> queryAfterMap = new HashMap<String, Object>();
		queryAfterMap.put("courseId", courseId.toString());

		String errorCode = map.get("error_code").toString();
		if (!"0".equals(errorCode)) {
			throw new Exception(map.get("error_desc").toString());
		}

		//排课完成后，更新试听列表的时间
		tCourseListeningDao.updateListeningDateByCourseSchedule(map);

		return map;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * deleteSchedulingCourse (java.lang.Long)
	 */
	@Override
	public Map<String, Object> deleteSchedulingCourse(Long courseId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#updateCoursePeople
	 * (int, java.lang.Long)
	 */
	@Override
	public int updateCoursePeople(int number, Long course_id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#queryCourseByID
	 * (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public TCourse queryCourseByID(TCourse courseBusiness) throws Exception {
		Assert.notNull(courseBusiness);
		Assert.notNull(courseBusiness.getId());
		TCourse tCourse = tCourseDao.queryCourseById(courseBusiness);

		if (null != tCourse) {
			CourseScheduling courseScheduling = new CourseScheduling();
			courseScheduling.setCourse_id(tCourse.getId());
			courseScheduling.setStudent_id(tCourse.getStudentId());
			// 查询课次定义信息
			List<CourseScheduling> courseSchedulingList = courseSchedulingService
					.queryCourseScheduling(courseScheduling);
			tCourse.setCourseSchedulingList(courseSchedulingList);

			if (tCourse.getTeacher_id() != null) {
				Teacher teacher = teacherInfoService.queryOne(tCourse.getTeacher_id());
				tCourse.setTeacher(teacher);
			}

		} else {
			log.error("课程未找到！课程id：" + courseBusiness.getId());
			throw new Exception("课程未找到！");
		}

		return tCourse;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCobnflictCourse (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public List<TCourse> queryCobnflictCourse(TCourse condition) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#queryYDYCourse
	 * (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public List<TCourse> queryYDYCourse(TCourse condition) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * toChangeActualPeople (java.util.Map)
	 */
	@Override
	public Integer toChangeActualPeople(Map<String, Object> param) throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("course_id"));
		return tCourseDao.changeActualPeople(param);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#queryOutCourse
	 * (java.util.Map)
	 */
	@Override
	public List<Long> queryOutCourse(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#getAsyn(java.util
	 * .Map)
	 */
	@Override
	public List<Map<String, Object>> getAsyn(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryOrderCourseList (java.lang.Long)
	 */
	@Override
	public List<TCourse> queryOrderCourseList(Long orderId) throws Exception {
		Assert.notNull(orderId);
		return tCourseDao.queryOrderCourseList(orderId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ebusiness.erp.course_manager.service.CourseService#updateTCourse(
	 * com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public void updateTCourse(TCourse tCourse) throws Exception {
		Assert.notNull(tCourse);
		Assert.notNull(tCourse.getId());
		Assert.notNull(tCourse.getCourse_name());
		Assert.notNull(tCourse.getBusiness_type());
		Assert.notNull(tCourse.getCity_id());
		Assert.notNull(tCourse.getBranch_id());
		//Assert.notNull(tCourse.getSeason_id(),"课程季必填!");
		Assert.notNull(tCourse.getGrade_id(),"年级必填!");
		Assert.notNull(tCourse.getSubject_id(),"科目必填!");
		Assert.notNull(tCourse.getTeacher_id(),"教师必填!");
		Assert.notNull(tCourse.getUnit_price(),"单价必填!");
		Assert.notNull(tCourse.getCourse_count(),"课时数量必填!");
		Assert.notNull(tCourse.getStart_date(),"开课日期必填!");
		Assert.notNull(tCourse.getEnd_date(),"结课日期必填!");
		Assert.notNull(tCourse.getStart_time(),"上课时间必填!");
		Assert.notNull(tCourse.getEnd_time(),"下课时间必填!");
		Assert.notNull(tCourse.getAttend_class_period(),"上课周期必填!");
		Assert.notNull(tCourse.getPerformance_belong_type(),"业绩归属类型必填!");

		TCourse tCourseObj = queryCourseByID(tCourse);

		if (tCourseObj == null) {
			log.error("课程未找到，课程id：" + tCourse.getId());
			throw new Exception("课程未找到，不能修改！");
		}

		//判断课程是否存在关联的订单
		Boolean orderCourses=existOrderCourse(tCourse.getCourse_id());
		if(orderCourses){//存在订单：校验课次是否发生改变
            //课程名称，年级，科目，课时数量，课程季，单价，校区，业绩归属，是否教材不能修改

			if(!tCourseObj.getCourse_name().equals(tCourse.getCourse_name())) throw new RuntimeException("该课程(包)存在报班记录，课程名称不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getGrade_id(), tCourse.getGrade_id())) throw new RuntimeException("该课程(包)存在报班记录，年级不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getSubject_id(),tCourse.getSubject_id())) throw new RuntimeException("该课程(包)存在报班记录，科目不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getCourse_count(),tCourse.getCourse_count())) throw new RuntimeException("该课程(包)存在报班记录，课时数量不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getSeason_id(),tCourse.getSeason_id())) throw new RuntimeException("该课程(包)存在报班记录，课程季不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getUnit_price(),tCourse.getUnit_price())) throw new RuntimeException("该课程(包)存在报班记录，单价不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getBranch_id(), tCourse.getBranch_id())) throw new RuntimeException("该课程(包)存在报班记录，校区不允许改变");
			if(!NumberUtils.isIntegerValueEqual(tCourseObj.getPerformance_belong_type(),tCourse.getPerformance_belong_type())) throw new RuntimeException("该课程(包)存在报班记录，业绩归属不允许改变");
			if(!NumberUtils.isLongValueEqual(tCourseObj.getIs_textbooks(),tCourse.getIs_textbooks())) throw new RuntimeException("该课程(包)存在报班记录，是否教材不允许改变");
		}

		boolean isMtCourse = (Constants.MtCourseType.MT.equals(tCourse.getMore_teacher_course_type()) && tCourse.getMore_teacher_courseid() != null);
		boolean isMainCourse = (isMtCourse && (tCourse.getTeacher_id().longValue() == tCourse.getLecturer_id()));


		if (!isMainCourse && isMtCourse) { // 双师分场，只能修改
			tCourseObj.setCourse_name(tCourse.getCourse_name());
			tCourseObj.setTeacher_id(tCourse.getTeacher_id());
			tCourseObj.setAssteacher_id(tCourse.getAssteacher_id());
			tCourseObj.setPeople_count(tCourse.getPeople_count());
			tCourseObj.setBranch_id(tCourse.getBranch_id());
		} else {
			BeanUtils.copyProperties(tCourse, tCourseObj);
			if (isMainCourse) { // 双师主场不允许修改教师
				tCourseObj.setTeacher_id(null);
			}
		}
		tCourseDao.updateTCourse(tCourseObj);

		Long isTextBooks = tCourse.getIs_textbooks();
		// 由于课程预售场景，该校验暂时去掉
//		if (isTextBooks == null || isTextBooks.longValue() != 1) {
//			checkTeacherSchedule(tCourseObj.getId());
//		}

		if (isMainCourse) {
			updateSubMtCourse(tCourseObj, tCourse.getTeacher_id());
		}

		if (tCourse.getBusiness_type().intValue() == 1) {
			SyncBusinessLog log = new SyncBusinessLog(SyncBusinessLog.BusinessType.UPDATE_COURSE,tCourse.getId(),tCourse.getUpdate_user());
			ThreadLocalContainer.SYNC_BUSINESS_LOG_THREAD_LOCAL.set(log);
			// 排课
			schedulingCourse(tCourseObj.getId());

		}
	}

	/**
	 * 双师主场课程信息修改，同步更新所有分场课程信息
	 * @param mainCourse
	 */
	private void updateSubMtCourse(TCourse mainCourse, Long teacherId) throws Exception {
		List<Long> courseIdList = this.tCourseDao.queryAllSubMtCourseId(mainCourse.getId());
		if (!CollectionUtils.isEmpty(courseIdList)) {
			TCourse subCourse = null;
			for (Long courseId : courseIdList) {
				subCourse = new TCourse();
				subCourse.setId(courseId);
				subCourse.setGrade_id(mainCourse.getGrade_id());
				subCourse.setSeason_id(mainCourse.getSeason_id());
				subCourse.setSubject_id(mainCourse.getSubject_id());
				subCourse.setStart_date(mainCourse.getStart_date());
				subCourse.setEnd_date(mainCourse.getEnd_date());
				subCourse.setStart_time(mainCourse.getStart_time());
				subCourse.setEnd_time(mainCourse.getEnd_time());
				subCourse.setAttend_class_period(mainCourse.getAttend_class_period());
				subCourse.setCourse_count(mainCourse.getCourse_count());
				subCourse.setPeople_count(mainCourse.getPeople_count());
				subCourse.setCourse_surplus(mainCourse.getCourse_surplus());
				subCourse.setUpdate_user(mainCourse.getUpdate_user());
				subCourse.setUnit_price(mainCourse.getUnit_price());
				subCourse.setDescription(mainCourse.getDescription());
				subCourse.setHour_len(mainCourse.getHour_len());
				subCourse.setPerformance_belong_type(mainCourse.getPerformance_belong_type());
				tCourseDao.updateTCourse(subCourse);
				// 排课
				schedulingCourse(courseId);
			}
		}
		// 同步更新双师包数据
		mainCourse.setTeacher_id(teacherId);
	}

	/**
	 * 校验生成的课次数量
	 * @param
	 * @throws Exception
	 */
	private void checkCourseTimes(TCourse tCourseObj) throws Exception {
		CourseScheduling condition=new CourseScheduling();
		condition.setCourse_id(tCourseObj.getId());
		List<CourseScheduling> schedulingsList = this.courseSchedulingDao.queryCourseScheduling(condition);
		if(schedulingsList!=null&&schedulingsList.size()!=tCourseObj.getCourse_count()){
			throw new Exception("实际要生成的课时数量小于您填写的课时数量值，不允许添加课程，请再次检查课程信息!");
		}
	}

	private void checkTeacherSchedule(Long courseId) throws Exception {
		Integer duplicateSchedCount = this.courseSchedulingDao.queryDuplicateTeacherSched(courseId);
		if (duplicateSchedCount > 0) {
			throw new Exception("该老师其他课程排课与当前课程排课存在冲突，课程保存失败！");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * updateCourseScheduling (com.ebusiness.erp.model.TCourse)
	 */
	@Override
	public void updateCourseScheduling(TCourse tCourse) throws Exception {
		Assert.notNull(tCourse);

		Assert.notNull(tCourse.getId());

		TCourse tCourseObj = queryCourseByID(tCourse);
		if (tCourseObj == null) {
			log.error("课程未找到，课程id：" + tCourse.getId());
			throw new Exception("课程未找到，不能修改！");
		}

		tCourseObj.setTeacher_id(tCourse.getTeacher_id());
		tCourseObj.setStart_date(tCourse.getStart_date());
		tCourseObj.setEnd_date(tCourse.getEnd_date());
		tCourseObj.setStart_time(tCourse.getStart_time());
		tCourseObj.setEnd_time(tCourse.getEnd_time());
		tCourseObj.setAttend_class_period(tCourse.getAttend_class_period());
		tCourseObj.setCourse_count(tCourse.getCourse_count());
		tCourseObj.setPeople_count(tCourse.getPeople_count());
		tCourseObj.setUpdate_user(tCourse.getUpdate_user());
		updateTCourse(tCourseObj);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCourseSchedulingAssist(java.lang.Long)
	 */
	@Override
	public List<TCourseSchedulingAssist> queryCourseSchedulingAssist(Long courseId) throws Exception {
		Assert.notNull(courseId);
		return tCourseSchedulingAssistDao.queryCourseSchedulingAssist(courseId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * updateCourseSchedulingAssist(java.util.List, java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public void updateCourseSchedulingAssist(List<TCourseSchedulingAssist> schedulingAssistList, Long userId,
			Long courseId) throws Exception {
		Assert.notNull(userId);
		Assert.notNull(courseId);

		tCourseSchedulingAssistDao.deleteCourseSchedulingAssist(courseId);

		if (!CollectionUtils.isEmpty(schedulingAssistList)) {
			for (TCourseSchedulingAssist schedulingAssist : schedulingAssistList) {
				tCourseSchedulingAssistDao.addCourseSchedulingAssist(schedulingAssist);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCourseTimesAttendance(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> queryCourseTimesAttendance(Long courseId) throws Exception {
		Assert.notNull(courseId);
		return tCourseDao.queryCourseTimesAttendance(courseId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseService#
	 * queryCourseTimeSchedulingAssist(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TCourseSchedulingAssist> queryCourseTimeSchedulingAssist(Long courseId, Long courseTime)
			throws Exception {
		Assert.notNull(courseId);
		Assert.notNull(courseTime);
		Map<String, Object> queryObj = new HashMap<String, Object>();
		queryObj.put("courseId", courseId);
		queryObj.put("courseTime", courseTime);
		return tCourseSchedulingAssistDao.queryCourseTimeSchedulingAssist(queryObj);
	}

	@Override
	public Page<TCourse> queryPage(Map<String, Object> param) throws Exception {
		Page<TCourse> tCoursePage= tCourseDao.selectForPage(param);
		for(TCourse tCourse : tCoursePage){
			//获取课程是否存在关联的订单
			tCourse.setHasOrderCourse(existOrderCourse(tCourse.getCourse_id()));
//			Long courseId = tCourse.getCourse_id();
//			List<RoomClass> roomClassList = tCourseDao.queryRoomClass(courseId);
//			List<RoomClass> sameRoomClass = new ArrayList<RoomClass>();
//			for(RoomClass roomClass:roomClassList){
//				String strCourseDate = roomClass.getCourseDate().toString();
//				String strDate = strCourseDate.substring(0, 4) + "-" + strCourseDate.substring(4, 6) + "-" + strCourseDate.substring(6, 8);
//				Date courseDate = DateUtil.stringToDate(String.valueOf(strDate), "yyyy-MM-dd");
//				String weekday = DateUtil.getDayOfWeek(courseDate, 1);
//				int weekNumber = Integer.parseInt(DateUtil.getDayOfWeek(courseDate, 0));
//				roomClass.setWeekday(weekday);
//				roomClass.setWeekNumber(weekNumber);
//			}
//			Set<RoomClass> roomClassSet = new HashSet<RoomClass>();
//			roomClassSet.addAll(roomClassList);
//			tCourse.setRoomClassSet(roomClassSet);
		}
		return tCoursePage;
	}

	@Override
	public Long getDoubleCourseId(Long courseId) throws Exception {
		// TODO Auto-generated method stub
		return tCourseDao.getDoubleCourseId(courseId);
	}

	@Override
	public void toAdd(TCourse course, String org_ids) throws Exception {
		Assert.notNull(course.getBusiness_type(),"业务类型必填");
		Assert.hasText(course.getCourse_no(),"课程编码生成错误");
		Assert.hasText(course.getCourse_name(),"课程名称必填");
		Assert.notNull(course.getGrade_id(),"年级必填");
		Assert.notNull(course.getBranch_id(),"校区必填");
		Assert.hasText(course.getStart_date(),"开课日期必填");
		Assert.hasText(course.getEnd_date(),"结课日期必填");
		Assert.notNull(course.getUnit_price(),"单价必填");
		Integer ret = tCourseDao.saveTCourse(course);
		if (ret < 1)
			throw new RuntimeException("toAdd_error");
		schedulingCourse(course.getId());
		List<Map<String, Long>> refs = refMajorForeignOrgRecursion(course.getId(), org_ids);
		if (refs.size() > 0) {
			tCourseDao.insertOrgRef(refs);
		}
	}

	@Override
	public void toUpdateWfd(TCourse course, String org_ids) throws Exception {
		Assert.hasText(course.getCourse_no(),"课程编码生成错误");
		Assert.hasText(course.getCourse_name(),"课程名称必填");
		Assert.notNull(course.getGrade_id(),"年级必填");
		Assert.notNull(course.getBranch_id(),"校区必填");
		Assert.hasText(course.getStart_date(),"开课日期必填");
		Assert.hasText(course.getEnd_date(),"结课日期必填");
		Assert.notNull(course.getUnit_price(),"单价必填");
		Integer ret = tCourseDao.updateTCourse(course);
		if (ret < 1)
			throw new RuntimeException("toUpdate_error");
		schedulingCourse(course.getId());// 课程排课
		tCourseDao.removeOrgRef(course.getId().toString());// 删除校区关系
		List<Map<String, Long>> refs = refMajorForeignOrgRecursion(course.getId(), org_ids);// 递归组织机构组装数据
		if (refs.size() > 0) {
			tCourseDao.insertOrgRef(refs);// 新增校区关系
		}
	}

	/**
	 * 递归组织机构组装数据
	 * 
	 * @param major_id
	 * @param foreign_ids
	 * @return
	 * @throws Exception
	 */
	public synchronized List<Map<String, Long>> refMajorForeignOrgRecursion(Long major_id, String foreign_ids)
			throws Exception {
		if (foreign_ids == null)
			foreign_ids = "";
		String[] foreign_idArr = foreign_ids.split(",");
		Set<Long> ids = new HashSet<Long>();

		for (String foreign_id : foreign_idArr) {
			if (foreign_id.trim().length() == 0)
				continue;
			ids.add(Long.parseLong(foreign_id));
			for (Long id : OrganizationUtils.getChildIds(Long.parseLong(foreign_id))) {
				ids.add(id);
			}
		}

		List<Map<String, Long>> result = new ArrayList<Map<String, Long>>(ids.size());
		Map<String, Long> obj = null;

		for (Long foreign_id : ids) {
			obj = new HashMap<String, Long>();
			obj.put("major_id", major_id);
			obj.put("foreign_id", foreign_id);
			result.add(obj);
		}
		return result;
	}

	@Override
	public void toAddYdy(TCourse course, String org_ids) throws Exception {
		
		Assert.notNull(course.getBusiness_type(),"业务类型必填");
		Assert.hasText(course.getCourse_no(),"课程编码生成错误");
		Assert.hasText(course.getCourse_name(),"课程名称必填");
		Assert.notNull(course.getGrade_id(),"年级必填");
		Assert.notNull(course.getUnit_price(),"单价必填");
		Assert.hasText(course.getStart_date(),"开课日期必填");
		Assert.hasText(course.getEnd_date(),"结课日期必填");
		Assert.notNull(course.getHour_len(),"课时长度必填");
		Integer ret = tCourseDao.saveTCourse(course);
		if (ret < 1)
			throw new RuntimeException("toAddYdy_error");
		List<Map<String, Long>> refs = refMajorForeignOrgRecursion(course.getId(), org_ids);// 递归组织机构组装数据
		if (refs.size() > 0) {
			tCourseDao.insertOrgRef(refs);// 新增校区关系
		}
	}

	@Override
	public void toUpdateYdy(TCourse course)  throws Exception {
		Assert.notNull(course.getBusiness_type(),"业务类型必填");
		Assert.hasText(course.getCourse_no(),"课程编码生成错误");
		Assert.hasText(course.getCourse_name(),"课程名称必填");
		Assert.notNull(course.getGrade_id(),"年级必填");
		Assert.hasText(course.getRelationIds(),"校区必填");
		Assert.notNull(course.getUnit_price(),"单价必填");
		Assert.hasText(course.getStart_date(),"开课日期必填");
		Assert.hasText(course.getEnd_date(),"结课日期必填");
		Assert.notNull(course.getHour_len(),"课时长度必填");
		
		Integer ret = tCourseDao.updateYdyCourse(course);
		if (ret < 1)
			throw new RuntimeException("toUpdateYdy_error");
		tCourseDao.removeOrgRef(course.getId().toString());// 删除校区关系
		List<Map<String, Long>> refs = refMajorForeignOrgRecursion(course.getId(), course.getRelationIds());// 递归组织机构组装数据
		if (refs.size() > 0) {
			tCourseDao.insertOrgRef(refs);// 新增校区关系
		}

	}

	@Override
	public Page<TCourse> queryYdyPage(Map<String, Object> queryParam) throws Exception {
		//一对一课程信息
		Page<TCourse> ydyCourseList = tCourseDao.selectYDYForPage(queryParam);
		//获取一对一课程信息中的所有的course_id并将其拼接成逗号分隔的查询条件
		String courseIds = "";
		for(TCourse ydyCourse : ydyCourseList ) {
			courseIds += (","+ ydyCourse.getId());
		}
		//查询课程关联的校区
		if(!StringUtil.isEmpty(courseIds)) {
			courseIds = courseIds.substring(1);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("courseIds", courseIds);
			List<Map<String,Object>> branchList = tCourseDao.selectBranchByCourseId(param);
			//将校区和课程关联起来
			for(TCourse ydyCourse : ydyCourseList ) {
				ydyCourse.setBranchInfos(new ArrayList<OrganizationInfo>());
				if(ydyCourse.getIs_all()!=null&&ydyCourse.getIs_all()==1l){
					List<OrganizationInfo> organizationInfoList=organizationService.queryBuBranchs(Long.parseLong(queryParam.get("bu_id").toString()));
					ydyCourse.getBranchInfos().addAll(organizationInfoList);
				}else {
					for (Map<String, Object> branch : branchList) {
						if (ydyCourse.getId().longValue() == NumberUtils.object2Long(branch.get("course_id"))) {
							OrganizationInfo brandhInfo = new OrganizationInfo();
							if (branch.get("org_name") != null) {
								brandhInfo.setOrg_name(branch.get("org_name").toString());
								brandhInfo.setId(NumberUtils.object2Long(branch.get("id")));
								ydyCourse.getBranchInfos().add(brandhInfo);
							}
						}
					}
				}
			}
		}
		return ydyCourseList;
	}

	/**
     * 自动下架到期课程
     * 
     */
    @Override
    public void stopOutCourse() {
        Map<String, Object> param = new HashMap<String, Object>();
        String strCourseIds = null;
        try {
            param.put("cur_date", DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_3));
            List<Long> courseIdList = this.tCourseDao.queryOutCourse(param);

            if (!CollectionUtils.isEmpty(courseIdList)) {
                strCourseIds = courseIdList.toString().replace("[", StringUtils.EMPTY).replace("]", StringUtils.EMPTY);
            }
            if (strCourseIds != null) {
                param.put("ids", strCourseIds);
                param.put("status", BaseObject.StatusEnum.INVALID.getCode());
                param.put("updateUser", 0);
                this.tCourseDao.changeStatus(param);

                StringBuffer buff = new StringBuffer();
                buff.append("课程ID：");
                buff.append(strCourseIds);
            }
        } catch (Exception e) {
            log.error(e);
            StringBuffer buff = new StringBuffer();
            buff.append("课程ID：");
            buff.append(strCourseIds);
            buff.append("，");
            buff.append("失败信息：");
            buff.append(e);
        }
    }

	@Override
	public List<Map<String, Object>> queryListForExcel(Map<String, Object> queryParam) throws Exception {
		return tCourseDao.queryListForExcel(queryParam);
	}

	@Override
	public List<TCourseSchedulingAssist> queryCourseSchedulingTimeAssist(Long schedulingId) {
		return tCourseSchedulingAssistDao.queryCourseSchedulingTimeAssist(schedulingId);
	}

	@Override
	public void updateCourseSchedulingTimeAssist(SchedulingAssist schedulingAssist) {
		Assert.notNull(schedulingAssist.getSchedulingId());
		//判断数据库中是否存在该课次
		List<CourseScheduling> courseSchedulings = tCourseDao.queryCourseSchedulingById(schedulingAssist.getSchedulingId());
		if(courseSchedulings.isEmpty()) {
			throw new RuntimeException("该排课信息不存在");
		}
		//删除课次高级参数
		tCourseSchedulingAssistDao.deleteCourseSchedulingTimeAssist(schedulingAssist.getSchedulingId());
		//插入新的高级参数
		tCourseSchedulingAssistDao.insertCourseSchedulingTimeAssist(schedulingAssist.getSchedulingAssistList());
	}

	public boolean isNumeric(String s) {
		if(s != null && !"".equals(s.trim())){
			return s.matches("^[1-7]*$");
		}
		else{
			return false;
		}
	}

	/**
     * 根据开课日期、上课周期、课时数量自动计算出结课日期
     * @param course
     * @return
     * @throws Exception
     *
     */
    @Override
    public Date queryEndTimesByPeriod(TCourse course) throws Exception {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = sdf2.parse(course.getStart_date()); //开课日期
        String attend_class_period = course.getAttend_class_period(); //上课周期
        Long scheduleTimes = course.getCourse_count(); // 总的计划排课的课时
		int courseTotal_num = 0;

		String[] classPeriods = attend_class_period.split(",");
		attend_class_period = "";
		for(String classPeriod : classPeriods){
			String  str = classPeriod.substring(0,1);
			if(!isNumeric(str)){
				throw new RuntimeException("上课周期格式错误,请再次确认!");
			}

			attend_class_period = attend_class_period + str + ",";
		}

        Calendar c = Calendar.getInstance();
        while (courseTotal_num < scheduleTimes) {
            // 查询当前时间是第几周，第几天
            c.setTime(start_date);
            int week_num = c.get(Calendar.WEEK_OF_YEAR); // 当前时间是第几周
            long day_num = c.get(Calendar.DAY_OF_WEEK); // 1=Sunday,2=Monday,,,7=Saturday。
            // 当前时间是本周第几天
            boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
            // 获取周几,若一周第一天为星期天，则-1
            if (isFirstSunday) {
                day_num = day_num - 1;
                if (day_num == 0) {
                    day_num = 7;
                }
            }

            // 是否当天属于排课周期范围
            if(	attend_class_period.contains(String.valueOf(day_num))
					) {
				courseTotal_num += 1;
				if(courseTotal_num < scheduleTimes){
					c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
					start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
				}
			}else{
				c.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
				start_date = c.getTime(); // 这个时间就是日期往后推一天的结果
			}
		}
		return start_date;
    }

	/**
	 *根据导入的课程信息判断课程是否有重复
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Override
	public int queryRepeatedCourses(TCourse course) throws Exception {
		return tCourseDao.queryRepeatedCourses(course);
	}

	/**
	 * 根据课程id搜索课时长度
	 * @param courseId
	 * @return
	 */
	@Override
	public TCourse queryHourlenBycourseId(Long courseId) {
		return tCourseDao.queryHourlenBycourseId(courseId);
	}

	@Override
	public Double queryHourLen(String startDate, String endDate) throws Exception{
		String[] format = {"HH:mm"};
		Date start = DateUtils.parseDate(startDate,format);
		Date end = DateUtils.parseDate(endDate, format);
		double startTime = start.getTime()/60000.0/60;
		double endTime = end.getTime()/60000.0/60;
		if(endTime-startTime<0){
			endTime = (endTime+24);
		}
		Double Hourlen =endTime-startTime;
		return Hourlen;
	}

	@Override
	public Boolean existOrderCourse(Long courseId) {
		Assert.notNull(courseId);
		return null == tCourseDao.existOrderCourse(courseId)?false:true;
	}

}
