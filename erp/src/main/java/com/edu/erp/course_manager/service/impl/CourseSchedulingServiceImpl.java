package com.edu.erp.course_manager.service.impl;

import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.course_manager.business.CourseTimeDate;
import com.edu.erp.course_manager.service.CourseService;

import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;

import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import com.edu.erp.util.CourseSchedulingUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.model.CourseScheduling.BMStatuEnum;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

@Service(value = "courseSchedulingService")
public class CourseSchedulingServiceImpl implements CourseSchedulingService {

	@Resource(name = "courseSchedulingDao")
	private CourseSchedulingDao courseSchedulingDao;
	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;
	@Resource(name = "orderInfoDetailDao")
	private OrderInfoDetailDao orderInfoDetailDao;
	@Resource(name = "tOrderCourseTimesDao")
	private TOrderCourseTimesDao tOrderCourseTimesDao;
	@Resource(name = "orderCourseTimesInfoDao")
	private OrderCourseTimesInfoDao orderCourseTimesInfoDao;
	@Resource(name = "tCourseDao")
	private TCourseDao tCourseDao;
	@Resource(name = "attendanceDao")
	private AttendanceDao attendanceDao;
	@Resource(name = "sortNumInfoDao")
	private SortNumInfoDao sortNumInfoDao;

	@Resource(name = "tCourseListeningDao")
	private TCourseListeningDao tCourseListeningDao;

    @Resource(name = "attendanceService")
    private AttendanceService attendanceService;
    @Resource(name = "courseService")
    private CourseService courseService;

	@Resource(name = "tCourseSchedulingAssistDao")
	private TCourseSchedulingAssistDao tCourseSchedulingAssistDao;

	/**
	 * 分页表格
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<CourseScheduling> page(Page<CourseScheduling> page)
			throws Exception {
		return courseSchedulingDao.page(page);
	}

	/**
	 * 新增
	 * 
	 * @param courseScheduling
	 * @throws Exception
	 */
	@Override
	public void toAdd(CourseScheduling courseScheduling) throws Exception {
		Integer ret = courseSchedulingDao.toAdd(courseScheduling);
		if (ret < 1) {
			throw new RuntimeException("toAdd_error");
		}
	}

	/**
	 * 根据ID修改
	 *
	 * @param param
	 *            部门IDS
	 * @throws Exception
	 */
	@Override
	public void toUpdate(Map<String, Object> param) throws Exception {
		courseSchedulingDao.toUpdate(param);
	}

	/**
	 * 根据IDS字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void toChangeStatus(String ids, Integer status) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", status);
		courseSchedulingDao.toChangeStatus(param);
	}

	/**
	 * 获取需要排课的课程
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Long> getSchedulingCourseID() throws Exception {
		return courseSchedulingDao.getSchedulingCourseID();
	}

	@Override
	public List<CourseScheduling> queryCourseScheduling(
			CourseScheduling condition) throws Exception {
		Assert.notNull(condition);
		Assert.notNull(condition.getCourse_id(),"查询不到课程ID");
		return courseSchedulingDao.queryCourseScheduling(condition);
	}

	@Override
	public CourseScheduling queryOne(Long id) throws Exception {
		Assert.notNull(id);
		return courseSchedulingDao.queryOne(id);
	}

	@Override
	public int update(CourseScheduling courseScheduling) throws Exception {
		Assert.notNull(courseScheduling.getId());
		CourseScheduling courseSchedulingObj = queryOne(courseScheduling
				.getId());
		courseSchedulingObj.setTeacher_id(courseScheduling.getTeacher_id());
		courseSchedulingObj.setAssteacher_id(courseScheduling.getAssteacher_id());
		courseSchedulingObj.setCourse_date(courseScheduling.getCourse_date());
		courseSchedulingObj.setStart_time(courseScheduling.getStart_time());
		courseSchedulingObj.setEnd_time(courseScheduling.getEnd_time());
		courseSchedulingObj.setRemark(courseScheduling.getRemark());
		return courseSchedulingDao.update(courseSchedulingObj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseSchedulingService#
	 * queryOrderCourseScheduling(java.lang.Long)
	 */
	@Override
	public List<CourseScheduling> queryOrderCourseScheduling(Long orderId)
			throws Exception {
		Assert.notNull(orderId);
		return courseSchedulingDao.queryOrderCourseScheduling(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseSchedulingService#
	 * schedulingCourse(java.lang.Long)
	 */
//	@Override
//	public void schedulingCourse(Long courseId) throws Exception {
////		Assert.notNull(courseId);
////		Map<String, Object> map = new HashMap<String, Object>();
////		map.put("course_id", courseId);
////		courseSchedulingDao.schedulingCourse(map);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ebusiness.erp.course_manager.service.CourseSchedulingService#
	 * queryCourseSchedulingStudents(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> queryCourseSchedulingStudents(
			Long courseId, Long courseTime) throws Exception {
		Assert.notNull(courseId);
		Assert.notNull(courseTime);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		params.put("courseTime", courseTime);
		return courseSchedulingDao.queryCourseSchedulingStudents(params);
	}

	/* (non-Javadoc)
	 * @see com.ebusiness.erp.course_manager.service.CourseSchedulingService#querySchedulingAttendanceMakeup(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> querySchedulingAttendanceMakeup(
			Long courseId, Long courseTime,String queryInfo) throws Exception {
		Assert.notNull(courseId);
		Assert.notNull(courseTime);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		params.put("courseTime", courseTime);
		params.put("queryInfo", queryInfo);
		return courseSchedulingDao.querySchedulingAttendanceMakeup(params);
	}

	@Override
	public void fillPeopleCountIntoCourseScheduling(List<CourseScheduling> courseScheduleList) throws Exception {
		Map<String,Object> queryParam=new HashMap<String,Object>();
		if(courseScheduleList!=null&&courseScheduleList.size()>0){
			Long courseId=courseScheduleList.get(0).getCourse_id();
			courseScheduleList.get(0).getPeople_count();
			queryParam.put("course_id", courseId);
			List<TOrderCourse> tOrderCourseList=tOrderCourseDao.queryOrderCourse(queryParam);
			List<TabOrderInfoDetail> tabOrderInfoDetailList=orderInfoDetailDao.queryCourseInfoDetailByCourseId(queryParam);
			
			queryParam.put("id", courseId);
			List<TCourse> tCourseList=tCourseDao.selectList(queryParam);
			if(StringUtil.isEmpty(tCourseList.get(0).getPeople_count())||courseScheduleList.get(0).getStudent_id()==null){
				return;
			}
			String times="";
			//查询对应课次的排号人数
			SortNumInfo sortNumInfo=new SortNumInfo();
			sortNumInfo.setCourseId(courseId);
			//查询排号的人数统计
			List<SortNumInfo> countSortNumInfoList=sortNumInfoDao.queryCountSortNumInfoBySeq(sortNumInfo);
			List<SortNumInfo> sortNumInfoList=sortNumInfoDao.querySortNumInfo(sortNumInfo);
			Map<Long,List<SortNumInfo>> sortNumInfoMapList=new HashMap<Long,List<SortNumInfo>>();
			for(SortNumInfo sortNumInfoTmp:sortNumInfoList){
				if(CollectionUtils.isEmpty(sortNumInfoMapList.get(sortNumInfoTmp.getSeq()))){
					List<SortNumInfo> sortNumInfoTmpList=new ArrayList<SortNumInfo>();
					sortNumInfoTmpList.add(sortNumInfoTmp);
					sortNumInfoMapList.put(sortNumInfoTmp.getSeq(), sortNumInfoTmpList);
				}else{
					sortNumInfoMapList.get(sortNumInfoTmp.getSeq()).add(sortNumInfoTmp);
				}
			}
			 for (Map.Entry<Long,List<SortNumInfo>> entry : sortNumInfoMapList.entrySet()) {
				 Collections.sort(entry.getValue(), new Comparator<SortNumInfo>(){
						@Override
						public int compare(SortNumInfo o1,SortNumInfo o2) {
							return o1.getNum().compareTo(o2.getNum());
						}
			    });
			}
			for(CourseScheduling courseScheduling:courseScheduleList){
				for(SortNumInfo sortNumMap:countSortNumInfoList ){
					if(sortNumMap.getSeq()==courseScheduling.getCourse_times()){
						courseScheduling.setTotalSortNum(sortNumMap.getNum().toString());
						break;
					}
				}
				courseScheduling.setPeople_count(tCourseList.get(0).getPeople_count());
				courseScheduling.setPeople_checkCount(0l);
				courseScheduling.setPeople_unCheckCount(0l);
				times+=","+courseScheduling.getCourse_times();
			}
			//根据已经交费生成正式订单的统计人数
			for(TOrderCourse tOrderCourse:tOrderCourseList){
				queryParam.put("orderDetailId", tOrderCourse.getId());
				queryParam.put("times", times.substring(1));
				queryParam.put("is_valid", 1);
				List<TOrderCourseTimes> tOrderCourseTimesList=tOrderCourseTimesDao.queryOrderCourseTimesByMap(queryParam);
				if(tOrderCourseTimesList!=null&&tOrderCourseTimesList.size()>0){
					for(CourseScheduling courseScheduleTmp:courseScheduleList){
						Long peopleCount=0l;
						for(TOrderCourseTimes tOrderCourseTimesTmp:tOrderCourseTimesList){
							if(courseScheduleTmp.getCourse_times()==tOrderCourseTimesTmp.getTimes()){
								peopleCount++;
							}
						}
						courseScheduleTmp.setPeople_checkCount(courseScheduleTmp.getPeople_checkCount()+peopleCount);
					}
				}
			}
			//统计暂存，审核中，未缴费状态的人数
			
			for(TabOrderInfoDetail tabOrderInfoDetail:tabOrderInfoDetailList){
				queryParam.put("order_detail_id", tabOrderInfoDetail.getId());
				queryParam.put("course_times", times.substring(1));
				List<TabOrderCourseTimesInfo> tabOrderCourseTimesInfoList=orderCourseTimesInfoDao.queryOrderCourseTimesInfoByMap(queryParam);
				if(tabOrderCourseTimesInfoList!=null&&tabOrderCourseTimesInfoList.size()>0){
					for(CourseScheduling courseScheduleTmp:courseScheduleList){
						Long unCheckPeopleCount=0l;
						for(TabOrderCourseTimesInfo tabOrderCourseTimesInfo:tabOrderCourseTimesInfoList){
							if(courseScheduleTmp.getCourse_times()==tabOrderCourseTimesInfo.getCourse_times()){
								unCheckPeopleCount++;
							}
						}
						courseScheduleTmp.setPeople_unCheckCount(courseScheduleTmp.getPeople_unCheckCount()+unCheckPeopleCount);
					}
				}
			}
			//判断下该学生是否可以报名
			for(CourseScheduling courseScheduleTmp:courseScheduleList){
				Long people_count=courseScheduleTmp.getPeople_count()==null?10000:courseScheduleTmp.getPeople_count();//计划人数
				Long people_checkCount=courseScheduleTmp.getPeople_checkCount();//已报人数
				Long people_unCheckCount=courseScheduleTmp.getPeople_unCheckCount();//待确认人数
				Long suplusCout=people_count-people_checkCount-people_unCheckCount;//可报名人数
				if(suplusCout<0){
					suplusCout=0l;
				}
				Long totalSortNum=Long.parseLong(courseScheduleTmp.getTotalSortNum());//目前排号的人数
				List<SortNumInfo> sortNumInfoTmpList=sortNumInfoMapList.get(courseScheduleTmp.getCourse_times());
				if(courseScheduleTmp.getIs_ordered()!=null&&courseScheduleTmp.getIs_ordered()==1l)
					continue;
				if(suplusCout==0l&&totalSortNum==0l){
					courseScheduleTmp.setIs_ordered(BMStatuEnum.YME.getCode());
					continue;
				}
				if(totalSortNum>0){
					if(suplusCout>totalSortNum){
						courseScheduleTmp.setIs_ordered(BMStatuEnum.KBM.getCode());
					}
					if(suplusCout<=totalSortNum){
						courseScheduleTmp.setIs_ordered(BMStatuEnum.YME.getCode());   //满额，请排号
						//检验是否该学生是在目前的排号中的
						int i=0;
						Boolean flag=false;//判断是否当前的学生有排号
						for(SortNumInfo sortNumInfoTmp:sortNumInfoTmpList){
							i++;
							if(sortNumInfoTmp.getStudentId().toString().equals(courseScheduleTmp.getStudent_id().toString())){
								flag=true;
								if(i>suplusCout){ //表示此学生并不在排号中，设置该课次为不可报名的状态
									courseScheduleTmp.setIs_ordered(BMStatuEnum.PHZ.getCode());  //排号中
								}else{
									courseScheduleTmp.setIs_ordered(BMStatuEnum.KBM.getCode());  //可报名
								}
								break;
							}
						}
						if(flag){
							courseScheduleTmp.setWaitPeople(i-1);
						}else{
							courseScheduleTmp.setWaitPeople(i);
							courseScheduleTmp.setIs_ordered(BMStatuEnum.YME.getCode());
						}
					}
				}
			}
		}
	}

	@Override
	public void updateCourseTimes(CourseScheduling courseScheduling) throws Exception {
		Integer count = this.tCourseDao.isMtSubCourse(courseScheduling.getCourse_id());
		if (count > 0) {
			throw new Exception("双师分场不能修改课次信息！");
		}

		List<CourseScheduling> data = queryCourseScheduling(courseScheduling);
		//	判断是否有此课程的挂起和考勤记录
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("scheduling_id", courseScheduling.getId());
		if(attendanceDao.countAttendBySchedulingId(params)>0){
			throw new Exception("已存在挂起或正常上课考勤，不能修改！");
		}
		boolean isConflict=ckCourseDateIsConflict(data, courseScheduling);
		if(isConflict)
		{
			throw new Exception("该课次与其他课次的排课日期存在冲突，不能修改！");
		}

		Map<String,Object> queryParam=new HashMap<String,Object>();
		queryParam.put("course_id", courseScheduling.getCourse_id());
		queryParam.put("course_times", courseScheduling.getCourse_times());
		List<TCourseTimesTitle> tCourseTimesTitleList=courseSchedulingDao.queryCourseTimeTitleInfo(queryParam);
		if(!CollectionUtils.isEmpty(tCourseTimesTitleList)){
			TCourseTimesTitle tCourseTimesTitle=tCourseTimesTitleList.get(0);
			tCourseTimesTitle.setTitle(courseScheduling.getTitle());
			tCourseTimesTitle.setUpdate_user(courseScheduling.getUpdate_user());
			courseSchedulingDao.updateCourseTimeTitleInfo(tCourseTimesTitle);
		}else{
			TCourseTimesTitle tCourseTimesTitle=new TCourseTimesTitle();
			tCourseTimesTitle.setId(courseScheduling.getId());
			tCourseTimesTitle.setCourse_id(courseScheduling.getCourse_id());
			tCourseTimesTitle.setCourse_times(courseScheduling.getCourse_times());
			tCourseTimesTitle.setTitle(courseScheduling.getTitle());
			tCourseTimesTitle.setCreate_user(courseScheduling.getUpdate_user());
			tCourseTimesTitle.setUpdate_user(courseScheduling.getUpdate_user());
			courseSchedulingDao.addCourseTimeTitleInfo(tCourseTimesTitle);
		}
		update(courseScheduling);

		//更新结课日期 start
		//获取最大的上课日期
		Long maxCourseDate = 0L;
		for (CourseScheduling datum : data) {
			if(datum.getCourse_times().longValue() != courseScheduling.getCourse_times().longValue() && maxCourseDate<datum.getCourse_date()) {
				maxCourseDate = datum.getCourse_date();
			}
		}
		if(maxCourseDate < courseScheduling.getCourse_date()) {
			maxCourseDate = courseScheduling.getCourse_date();
		}
		//更新该课程的结课时间
		updateCourseEndDate(courseScheduling.getCourse_id(),DateUtils.parseDate(String.valueOf(maxCourseDate),Constants.DATE_PATTERN),courseScheduling.getUpdate_user());
		//更新结课日期 end

		//排课完成后，更新试听列表的时间
		tCourseListeningDao.updateListeningDateByCourseSchedule(queryParam);
	}

	/**
	 * 更新课程结课日期
	 * @param courseId
	 * @param endDate
	 */
	public void updateCourseEndDate(Long courseId,Date endDate,Long updateUser) {
		try {
			TCourse tCourse = tCourseDao.getCourseById(courseId);
			if(tCourse.getEnd_date().equals(DateFormatUtils.format(endDate,"yyyy-MM-dd"))) {
				return;
			}
			tCourse.setEnd_date(DateFormatUtils.format(endDate,"yyyy-MM-dd"));
			tCourseDao.updateTCourse(tCourse);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isTimeChanged(List<CourseScheduling> courseSchedulingList, CourseScheduling courseScheduling) {
		if (CollectionUtils.isEmpty(courseSchedulingList)) {
			return false;
		}

		boolean isTimeChanged = false;
		for (CourseScheduling courseSched : courseSchedulingList) {
			if (courseSched.getId().longValue() != courseScheduling.getId().longValue()) {
				continue;
			}
			if (courseSched.getCourse_date().longValue() != courseScheduling.getCourse_date().longValue() || !courseSched.getStart_time()
				.equals(courseScheduling.getStart_time()) || !courseSched.getEnd_time().equals(courseScheduling.getEnd_time())) {
				isTimeChanged = true;
				break;
			}
		}
		return isTimeChanged;
	}
	
	/**
	 * 校验排课的上课日期是否与排课记录表里的排课存在时间上的冲突
	 * @param data
	 * @param courseScheduling 排课ID
	 * @return
	 */
	protected boolean ckCourseDateIsConflict(List<CourseScheduling> data,CourseScheduling courseScheduling )
	{
		boolean isConfilct=false;
		if(null!=data&&data.size()>0)
		{
			for(CourseScheduling csd:data)
			{
				if(courseScheduling.getId().longValue()!=csd.getId()&&courseScheduling.getCourse_date()==csd.getCourse_date().longValue()
						&&courseScheduling.getStart_time().equals(csd.getStart_time())&&courseScheduling.getEnd_time().equals(csd.getEnd_time())){
					isConfilct=true;
					break;
				}
			}
		}
		return isConfilct;
		
	}

	@Override
	public List<CourseScheduling> queryConfirmCourseScheduling(
			CourseScheduling condition) throws Exception {
		List<TOrderCourseTimes> tOrderCourseTimesList=courseSchedulingDao.queryCourseSchedulingTimesByStudent(condition);
		List<CourseScheduling> result=courseSchedulingDao.queryConfirmCourseScheduling(condition);
		for(CourseScheduling courseScheduling:result){
			for(TOrderCourseTimes tOrderCourseTimes:tOrderCourseTimesList){
				if((courseScheduling.getCourse_times()==tOrderCourseTimes.getTimes())&&tOrderCourseTimes.getIs_valid()==0l){
					courseScheduling.setIs_ordered(0l);
					break;
				}
			}
		}
		//查询转班的课次
		List<TOrderCourseTimes> transferTimes = courseSchedulingDao.queryTransferTimesByCoursesStudent(condition);
		for(CourseScheduling courseScheduling:result){
			for(TOrderCourseTimes tOrderCourseTimes:transferTimes){
				if((courseScheduling.getCourse_times()==tOrderCourseTimes.getTimes())){
					courseScheduling.setIs_ordered(1l);
					break;
				}
			}
		}
		//查找出待确定的课次
		List<TOrderCourseTimes> confirmTimes = courseSchedulingDao.queryConfirmTimesByCoursesStudent(condition);
		for(CourseScheduling courseScheduling:result){
			for(TOrderCourseTimes tOrderCourseTimes:confirmTimes){
				if((courseScheduling.getCourse_times()==tOrderCourseTimes.getTimes())){
					courseScheduling.setIs_ordered(1l);
					break;
				}
			}
		}
		return result;
	}

	@Override
	public List<TOrderCourseTimes> queryCourseSchedulingTimesByStudent(CourseScheduling condition) throws Exception {
		return courseSchedulingDao.queryCourseSchedulingTimesByStudent(condition);
	}

	@Override
	public List<TOrderCourseTimes> queryConfirmTimesByCoursesStudent(
			CourseScheduling condition) throws Exception {
		return courseSchedulingDao.queryConfirmTimesByCoursesStudent(condition);
	}

	@Override
	public List<TOrderCourseTimes> queryTransferTimesByCoursesStudent(
			CourseScheduling condition) throws Exception {
		return courseSchedulingDao.queryTransferTimesByCoursesStudent(condition);
	}

	@Override
	public void batchUpdateCourseTimesTitle(Long course_id, String courseSchedulingTitles, Long create_user) throws Exception {
		Integer count = this.tCourseDao.isMtSubCourse(course_id);
		if (count > 0) {
			throw new Exception("双师分场不能修改课次信息！");
		}

		String[] courseSchedulingTitleArrayResource = courseSchedulingTitles.split("\n");
		ArrayList<TCourseTimesTitle> params = new ArrayList<>();
		Date create_time = new Date();
		
		ArrayList<String> processedResource = new ArrayList<String>();//courseSchedulingTitleArrayResource去除空行后的数据
		//1.过滤空行
		for (String courseSchedulingTitle : courseSchedulingTitleArrayResource) {
			if(!StringUtils.isEmpty(courseSchedulingTitle.trim())){//1.非空行
				processedResource.add(courseSchedulingTitle.trim());
			}
		}
		//2.判断格式：1>数字空格标题 ,2>标题
		boolean titleStrategy = false;
		boolean numberTitleStrategy = false;
		for (String t : processedResource) {
			String[] splitTemp = t.split("\\s");
			if(splitTemp.length>1) {
				try {
					Integer.valueOf(splitTemp[0]);
					numberTitleStrategy = true;
					continue;
				} catch(NumberFormatException e) {
					titleStrategy = true;
				}
			}
			titleStrategy = true;
		}
		if(titleStrategy && numberTitleStrategy) {
			throw new RuntimeException("粘贴内容格式错误");
		}
		
		//生成课次标题
		long courseTimesTemp = -1;
		String titleTemp = "";
		int courseTimesGenetateNumber = 1;
		StringBuilder courseTimesBuilder = new StringBuilder();
		for (String courseSchedulingTitle : processedResource) {
			if(numberTitleStrategy) {//格式：数字空格标题 
				String[] split = courseSchedulingTitle.split("\\s");
				courseTimesTemp = Integer.valueOf(split[0]);
				titleTemp = courseSchedulingTitle.substring(courseSchedulingTitle.indexOf(split[1]));
			} else {//格式：标题
				courseTimesTemp = courseTimesGenetateNumber;
				titleTemp = courseSchedulingTitle;
				courseTimesGenetateNumber ++ ;
			}
			courseTimesBuilder.append(",").append(courseTimesTemp);
			TCourseTimesTitle courseTimesTitle = new TCourseTimesTitle();
			courseTimesTitle.setCourse_id(course_id);
			courseTimesTitle.setCourse_times(courseTimesTemp);
			courseTimesTitle.setCreate_time(create_time);
			courseTimesTitle.setCreate_user(create_user);
			courseTimesTitle.setTitle(titleTemp);
			params.add(courseTimesTitle);
		}
		Collections.sort(params, new Comparator<TCourseTimesTitle>() {
			@Override
			public int compare(TCourseTimesTitle o1, TCourseTimesTitle o2) {
				return o1.getCourse_times() > o2.getCourse_times() ? 1 : -1;
			}
		});
		courseSchedulingDao.batchUpdateCourseTimesTitle(params);

	}


	/**
	 * 删除课次标题
	 * @param courseId
	 * @param courseTime
	 */
	public void deleteCourseTimeTitle (Long courseId,Long courseTime) {
		Assert.notNull(courseId);
		Assert.notNull(courseTime);
		courseSchedulingDao.deleteCourseTimeTitle(courseId,courseTime);
	}

	/**
	 * 删除排课信息
	 * @param schedulingIds
	 */
	public void batchDeleteCoruseScheduling(String schedulingIds) {
		Assert.hasText(schedulingIds);
		courseSchedulingDao.batchDeleteCoruseScheduling(schedulingIds);
	}

	//排课
	@Override
	public void scheduleCourse (Long courseId,Long updateUser) throws Exception {
		//查询课程
		TCourse tCourse =tCourseDao.getCourseById(courseId);
		//判断课程校区是否存在
		Assert.notNull(tCourse,"课程不存在");
		//判断课程校区是否存在
		Assert.notNull(tCourse.getBranch_id(),"课程校区不存在");

		if(!tCourse.getBusiness_type().equals(Long.valueOf(TCourse.BusinessTypeEnum.BJK.getCode()))){//如果是培英班
			throw new RuntimeException("业务类型不支持排课");
		}
		//判断上下课时间是否合法
		Assert.isTrue(CourseSchedulingUtil.isCoursePeriodLegal(tCourse.getAttend_class_period()),"课程上下课周期格式异常");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtils.parseDate(tCourse.getStart_date(),CourseSchedulingUtil.dateFormats);
			endDate = DateUtils.parseDate(tCourse.getEnd_date(),CourseSchedulingUtil.dateFormats);
		} catch (ParseException e) {
			throw  new RuntimeException(e.getMessage());
		}
		Long scheduledCourseTime = 1L;//当前排课课次
		CourseScheduling courseSchedulingProto = new CourseScheduling();
		courseSchedulingProto.setCourse_id(tCourse.getId());
		courseSchedulingProto.setBranch_id(tCourse.getBranch_id());
		courseSchedulingProto.setGrade_id(tCourse.getGrade_id());
		courseSchedulingProto.setSubject_id(tCourse.getSubject_id());
		courseSchedulingProto.setTeacher_id(tCourse.getTeacher_id());
		courseSchedulingProto.setAssteacher_id(tCourse.getAssteacher_id());
		courseSchedulingProto.setBusiness_type(tCourse.getBusiness_type());
		courseSchedulingProto.setStart_time(tCourse.getStart_time());
		courseSchedulingProto.setEnd_time(tCourse.getEnd_time());
		courseSchedulingProto.setCourse_cnt(1L);
		courseSchedulingProto.setAttended("N");
		courseSchedulingProto.setValid_status(1L);
		courseSchedulingProto.setCreate_user(tCourse.getCreate_user());
		courseSchedulingProto.setCreate_time(tCourse.getCreate_time());
		courseSchedulingProto.setUpdate_user(null ==updateUser?tCourse.getUpdate_user():updateUser);
		courseSchedulingProto.setUpdate_time(new Date());

		//查找该课程的所有课次
		List<CourseScheduling> allSchedulings = courseSchedulingDao.queryCourseSchedulingByCourseId(courseId);

		//查找该课程已经考勤的课次
		List<CourseScheduling> allAttendedSchedulings = null;
		if(null!=allSchedulings && allSchedulings.size()>0) allAttendedSchedulings = courseSchedulingDao.queryCourseSchedulingAttended(courseId);

		String strategy = "新增";

		if(allSchedulings!=null && allSchedulings.size()>0) strategy ="重排";

		Date baseDate = DateUtils.addDays(startDate,-1);
		if(strategy.equals("重排")) {//如果课次不存在新增课次，如果课次存在变更课次时间
			CourseScheduling reScheduling = null;
			//查找课程关联的订单
			Boolean isOrdered = courseService.existOrderCourse(courseId);
			//已经重排的课次
			List<CourseScheduling> reSchedulinged = new ArrayList<>();
			CourseTimeDate nextCourseDate = null;
			while(scheduledCourseTime<=tCourse.getCourse_count() && baseDate.before(endDate)) {

				//获取下次课上课时间
 				nextCourseDate = CourseSchedulingUtil.getNextCourseDate(tCourse.getAttend_class_period(), baseDate);
				baseDate = nextCourseDate.getCourseTimeDate();

				final long currentCourseTime= scheduledCourseTime.longValue();
				if(null!= allAttendedSchedulings && allAttendedSchedulings.size()>0 && null != findCourseScheduling(allAttendedSchedulings, currentCourseTime)) {
					//已经存在正常上课或挂起的考勤不重排
					scheduledCourseTime++;
					continue;
				}
				//重排，初始化信息
				reScheduling = new CourseScheduling();
				if(findCourseScheduling(allSchedulings, currentCourseTime)!=null) {//重排
					BeanUtils.copyProperties(findCourseScheduling(allSchedulings, currentCourseTime),reScheduling);
					reScheduling.setTeacher_id(tCourse.getTeacher_id());//主讲
					reScheduling.setAssteacher_id(tCourse.getAssteacher_id());//辅讲
				} else {//新增
					BeanUtils.copyProperties(courseSchedulingProto,reScheduling);
					reScheduling.setCourse_times(scheduledCourseTime);
				}

				reScheduling.setCourse_date(Long.valueOf(nextCourseDate.getCourseTimeDateString().replaceAll("[\\-]","")));//上课日期
				if(null != nextCourseDate.getStartTime()){
					reScheduling.setStart_time(nextCourseDate.getStartTime());//上课时间
					reScheduling.setEnd_time(nextCourseDate.getEndTime());//下课时间
				} else {
					reScheduling.setStart_time(tCourse.getStart_time());//上课时间
					reScheduling.setEnd_time(tCourse.getEnd_time());//下课时间
				}
				reScheduling.setWeek_number(Long.valueOf(nextCourseDate.getWeekNum()));//第几周
				if(null != reScheduling.getId()) {
					//更新
					courseSchedulingDao.updateCouseScheduling(reScheduling);
					reSchedulinged.add(reScheduling);//添加到已经重排列表
				} else if(!isOrdered) {
					//新增
					courseSchedulingDao.insert(reScheduling);
				}
				scheduledCourseTime++;
			}
			if(!isOrdered) {//删除多余的课次，及课次标题，高级参数
				allSchedulings.removeAll(reSchedulinged);
				if(null!=allAttendedSchedulings&&allAttendedSchedulings.size()>0)allSchedulings.removeAll(allAttendedSchedulings);
				if(allSchedulings.size()>0) {
					String delSchedulingIds = "";
					//获取所有的id
					for (CourseScheduling cs:allSchedulings ) {
						delSchedulingIds +=(","+cs.getId());
					}
					delSchedulingIds = delSchedulingIds.substring(1);
					//开始删除
					//删除高级参数
					tCourseSchedulingAssistDao.batchDeleteCourseSchedulingTimeAssist(delSchedulingIds);
					//删除课次标题
					for (CourseScheduling cs:allSchedulings ) {
						deleteCourseTimeTitle(cs.getCourse_id(),cs.getCourse_times());
					}
					//删除课次
					batchDeleteCoruseScheduling(delSchedulingIds);
				}
			}
		} else {
			CourseScheduling newScheduling = null;
			//新增
			CourseTimeDate nextCourseDate = null;
			TCourseTimesTitle courseTimesTitle = null;
			while(scheduledCourseTime<=tCourse.getCourse_count() && baseDate.before(endDate)) {
				//获取下次课上课时间
				nextCourseDate = CourseSchedulingUtil.getNextCourseDate(tCourse.getAttend_class_period(), baseDate);
				baseDate = nextCourseDate.getCourseTimeDate();

				newScheduling = new CourseScheduling();
				BeanUtils.copyProperties(courseSchedulingProto,newScheduling);


				newScheduling.setCourse_times(scheduledCourseTime);
				newScheduling.setCourse_date(Long.valueOf(nextCourseDate.getCourseTimeDateString().replaceAll("[\\-]","")));
				newScheduling.setWeek_number(Long.valueOf(nextCourseDate.getWeekNum()));
				if(null != nextCourseDate.getStartTime()){
					newScheduling.setStart_time(nextCourseDate.getStartTime());
					newScheduling.setEnd_time(nextCourseDate.getEndTime());
				}
				newScheduling.setWeek_number(Long.valueOf(nextCourseDate.getWeekNum()));
				//新增
				courseSchedulingDao.insert(newScheduling);
				scheduledCourseTime++;

			}
		}
	}

	public CourseScheduling findCourseScheduling(List<CourseScheduling> list,final Long courseTime) {
		return (CourseScheduling)CollectionUtils.find(list, new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				CourseScheduling o1 = (CourseScheduling) o;
				return o1.getCourse_times().longValue() == courseTime.longValue();
			}
		});
	}

	@Override
	public void updateSchedulingTime(Long courseId,int courseTime, String courseDate,  String startTime,  String endTime) {
		Assert.notNull(courseId);
		Assert.notNull(courseTime);
		Assert.notNull(courseDate);
		Assert.notNull(startTime);
		Assert.notNull(endTime);
		courseSchedulingDao.updateSchedulingTime(courseId,courseTime,courseDate,startTime,endTime);
	};
}
