/**  
 * @Title: CourseController.java
 * @Package com.ebusiness.erp.course_manager.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月17日 下午6:08:16
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.course_manager.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.constants.ProductLine;
import com.edu.erp.dict.service.TimeSeasonService;
import com.edu.erp.model.TimeSeason;
import com.edu.erp.util.ExcelWriteHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.EncodingSequenceUtil;
import com.edu.common.util.NumberUtils;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.common.constants.Constants;
import com.edu.erp.course_manager.business.SchedulingAssist;
import com.edu.erp.course_manager.service.CourseSchedulingService;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.model.CourseScheduling;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TCourseSchedulingAssist;
import com.edu.erp.util.BaseController;
import com.edu.common.util.ERPConstants;
import com.edu.erp.util.ModelDataUtils;
import com.edu.erp.util.StringUtil;
import com.github.pagehelper.Page;

/**
 * @ClassName: CourseController
 * @Description: 课程控制器服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月17日 下午6:08:16
 * 
 */
@Controller
@RequestMapping(value = { "/coursemanagerment" })
public class CourseController extends BaseController {
	private static final Logger log = Logger.getLogger(CourseController.class);

	@Resource(name = "courseService")
	private CourseService courseService;
	
	@Resource(name = "courseSchedulingService")
	private CourseSchedulingService courseSchedulingService;
	
	@Resource(name = "attendanceService")
	private AttendanceService attendanceService;

	@Resource(name="timeSeasonService")
	private TimeSeasonService timeSeasonService;

	/**
	 *
	 * @Title: queryCourses
	 * @Description: 查询课程服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryCourses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null ) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择团队或校区!");
				return resultMap;
			}
			Map<String, Object> queryParam = initParamMap(request, false);
			queryParam.put("business_type", genLongParameter("business_type", request));
			queryParam.put("branch_id", genLongParameter("branch_id", request));
			if(genLongParameter("branch_id", request) != null && genLongParameter("branch_id", request)==-1L ){
				queryParam.put("per_branch_ids",getAllowedBranchByBuId(request));
			} else {
				queryParam.remove("per_branch_ids");
			}
			setPageInfo(request);
			queryParam.put("season_id", genLongParameter("season_id", request));
			queryParam.put("course_id", genLongParameter("course_id", request));
			queryParam.put("grade_id", genLongParameter("grade_id", request));
			queryParam.put("subject_id", genLongParameter("subject_id", request));
			queryParam.put("teacher_id",request.getParameter("teacher_id"));
			queryParam.put("assteacher_id",request.getParameter("assteacher_id"));
			queryParam.put("course_name", request.getParameter("course_name"));
			String searchInfo = request.getParameter("search_info");
			queryParam.put("search_info", searchInfo != null ? searchInfo.toLowerCase() : searchInfo); //查询课程控件需要的条件
			queryParam.put("status", genLongParameter("status", request));
			queryParam.put("unit_price", NumberUtils.object2Double(request.getParameter("unit_price")));
			queryParam.put("ydyLadder", request.getParameter("ydyLadder"));
			String onlyShow = request.getParameter("onlyshow");
			Page<TCourse> page = null;
			Calendar calendar=Calendar.getInstance();
            //获得当前时间的月份，月份从0开始所以结果要加1
			int month=calendar.get(Calendar.MONTH)+1;
			int year = calendar.get(Calendar.YEAR);
			if(genIntParameter("business_type", request) ==TCourse.BusinessTypeEnum.YDY.getCode()) {
				page = courseService.queryYdyPage(queryParam);
			} else{
				//是否只显示本月有排课课程的判断
				if(("true").equals(onlyShow)){
					queryParam.put("month",month);
					queryParam.put("year",year);
					page = courseService.queryPage(queryParam);
				}
				else {
					page = courseService.queryPage(queryParam);
				}
			}

			setRespDataForPage(request, page, resultMap);
		} catch(Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		
		return resultMap;
	}

	/**
	 * 
	 * @Title: queryCourseTimes
	 * @Description: 查询课程课次服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/coursetimes" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryCourseTimes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CourseScheduling queryCondition = new CourseScheduling();
		queryCondition.setCourse_id(genLongParameter("courseId", request));
		queryCondition.setStudent_id(genLongParameter("studentId", request));
		try {
			List<CourseScheduling> result = courseSchedulingService.queryConfirmCourseScheduling(queryCondition);
			//查询对应的课次
			courseSchedulingService.fillPeopleCountIntoCourseScheduling(result);
			resultMap.put("error", false);
			resultMap.put("data", result);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: updateCourseTimes
	 * @Description: 修改课程课次服务
	 * @param courseScheduling
	 *            待修改课程课次信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/coursetimes" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateCourseTimes(@RequestBody CourseScheduling courseScheduling,HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (courseScheduling == null || courseScheduling.getId() == null) {
				log.error("error found ,see:要修改的课次为空！");
				throw new Exception("要修改的课次为空！");
			}
			//记录当前courseId
			Long courseId = courseScheduling.getCourse_id();
			Account account = WebContextUtils.genUser(request);
			courseScheduling.setUpdate_user(account.getId());
			courseSchedulingService.updateCourseTimes(courseScheduling);

			resultMap.put("error", false);
			resultMap.put("data", courseScheduling);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: updateCourse
	 * @Description: 修改课程服务
	 * @param course
	 *            待修改课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateCourse(@RequestBody TCourse course,HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (course == null || course.getId() == null) {
				log.error("非法访问！排课时的课程不能为空！");
				throw new Exception("非法访问！");
			}
			Account account = WebContextUtils.genUser(request);
			course.setUpdate_user(account.getId());
			course.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			//记录当前courseId
			Long courseId = course.getId();
			if ("schedule".equals(request.getParameter("updateType"))) {
				courseService.updateCourseScheduling(course);
			} else {
				if(course.getBusiness_type() != null && course.getBusiness_type() == (long)TCourse.BusinessTypeEnum.WFD.getCode()) {
					 //晚辅导课程修改
					courseService.toUpdateWfd(course,null);
				} else if(course.getBusiness_type() != null && course.getBusiness_type() == (long)TCourse.BusinessTypeEnum.YDY.getCode()) {
					// 一对一课程修改
					courseService.toUpdateYdy(course);
				} else {
					int result=courseService.queryRepeatedCourses(course);
					if(result>0){
						throw new Exception("课程" + course.getCourse_name() + "已存在，请核实已上架课程");
					}
					//判断课程季时间是否正确
					if(course.getSeason_id()!=null) {
						TimeSeason timeSeason = timeSeasonService.queryById(course.getSeason_id().toString());
						String seasonStartDate = timeSeason.getStart_date();
						String seasonEndDate = timeSeason.getEnd_date();
						String courseStartDate = course.getStart_date();
						String courseEndDate = course.getEnd_date();
						Long compareStartDate = DateUtil.compareDate(courseStartDate, seasonStartDate, 0);
						Long compareEndDate = DateUtil.compareDate(seasonEndDate, courseEndDate, 0);
						if (compareStartDate > 0) {
							throw new Exception("课程的开始日期"+"["+courseStartDate+"]"+"早于课程季开始日期"+"["+seasonStartDate+"]");
						}
						if (compareEndDate > 0) {
							throw new Exception("课程的结束日期"+"["+courseEndDate+"]"+"晚于课程季结束日期"+"["+seasonEndDate+"]");
						}
					}
					courseService.updateTCourse(course);
				}
			}
			resultMap.put("error", false);
			resultMap.put("data", course);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: changeStatus
	 * @Description: 课程上架、下架服务
	 * @param param
	 *            课程对象
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/changeStatus" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> changeStatus(@RequestBody Map<String,Object> param,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (StringUtil.isEmpty(String.valueOf(param.get("ids"))) ) {
				log.error("非法访问！未选中课程！");
				throw new Exception("未选中课程！");
			}
			if (StringUtil.isEmpty(param.get("status"))) {
				log.error("待修改的课程的状态不能为空！课程id：" + param.get("ids"));
				throw new Exception("待修改的课程的状态不能为空！课程id：" + param.get("ids"));
			}
			Account account = WebContextUtils.genUser(request);
			courseService.toChangeStatus(String.valueOf(param.get("ids")), String.valueOf(param.get("status")),account.getId());
			resultMap.put("error", false);
			resultMap.put("data", param);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 自动排课
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/schedulingAssist", headers = "Accept=application/json")
	public Map<String, Object> toAutoCourseScheduling(@RequestBody Map<String,String> param,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String courseIds = param.get("ids");
			if (StringUtil.isEmpty(courseIds)) {
				log.error("error found ,see:要查询的课程为空！");
				throw new Exception("访问非法！要查询的课程为空");
			}
//			MenuAutoScheduling.getInstance().run();
			String[]  courseIdList=courseIds.split(",");
			for(String courseId:courseIdList){
				courseService.schedulingCourse(Long.parseLong(courseId));
			}
			result.put("error", false);
		} catch (Exception e) {
			StringBuffer buff = new StringBuffer();
			buff.append("失败信息：");
			buff.append(e);
			result.put("error", true);
			result.put("message", e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @Title: querySchedulingAssistList
	 * @Description: 查询课程高级参数服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/schedulingAssist" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> querySchedulingAssistList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long courseId = genLongParameter("courseId", request);
			Long courseTime = genLongParameter("courseTime", request);
			if (courseId == null) {
				log.error("error found ,see:要查询的课程为空！");
				throw new Exception("访问非法！");
			}
			if (courseTime != null) {
				List<TCourseSchedulingAssist> result = courseService.queryCourseTimeSchedulingAssist(courseId, courseTime);
				resultMap.put("data", result);
			} else {
				List<TCourseSchedulingAssist> result = courseService
						.queryCourseSchedulingAssist(courseId);

				resultMap.put("data", result);
			}

			resultMap.put("error", false);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @Title: updateSchedulingAssistList
	 * @Description: 修改课程高级参数服务
	 * @param schedulingAssist
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/schedulingAssist" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateSchedulingAssistList(
			@RequestBody SchedulingAssist schedulingAssist,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (schedulingAssist == null|| schedulingAssist.getCourseId() == null|| CollectionUtils.isEmpty(schedulingAssist.getSchedulingAssistList())) {
				log.error("error found ,see:要修改的课程为空！");
				throw new Exception("访问非法！");
			}
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			if(null == orgModel.getProductLine()) {
				throw new Exception("请初始化组织产品线!");
			}

			Account account = WebContextUtils.genUser(request);
			Long courseId = schedulingAssist.getCourseId();
			if(ProductLine.JIA_YIN.getId().equals(orgModel.getProductLine())) {//佳音的培英班考勤
				TCourse tCourse = courseService.queryHourlenBycourseId(courseId);
				String startTime = tCourse.getStart_time();
				String endTime = tCourse.getEnd_time();
				String[] format = {"HH:mm"};
				Date start = DateUtils.parseDate(startTime,format);
				Date end = DateUtils.parseDate(endTime, format);
				Double Hourlen =((end.getTime()-start.getTime())/60000.0);
				if(Hourlen<0 || Hourlen==0){
					throw new Exception("课程缺失课时长度，请补充课程信息！");
				}
				List<TCourseSchedulingAssist> CourseSchedulingAssistList = schedulingAssist.getSchedulingAssistList();
				Double cnCourseTimes = null;
				Double enCourseTimes = null;
				Double SpokenTimes = null;
				for(TCourseSchedulingAssist CourseSchedulingAssist:CourseSchedulingAssistList){
					if(CourseSchedulingAssist.getCourseName() != null && CourseSchedulingAssist.getCourseName().equals("中文老师")){
						if(CourseSchedulingAssist.getCourseVal()==null){
							cnCourseTimes=0.00;
							CourseSchedulingAssist.setExtandVal1(0.00);
						}else{
							cnCourseTimes = CourseSchedulingAssist.getExtandVal1()==null?0:CourseSchedulingAssist.getExtandVal1();
						}
					}else if(CourseSchedulingAssist.getCourseName() != null&&CourseSchedulingAssist.getCourseName().equals("外文老师")){
						if(CourseSchedulingAssist.getCourseVal()==null){
							enCourseTimes=0.00;
							CourseSchedulingAssist.setExtandVal1(0.00);
						}else{
							enCourseTimes =CourseSchedulingAssist.getExtandVal1()==null?0:CourseSchedulingAssist.getExtandVal1();
						}
						if(CourseSchedulingAssist.getExtandVal2()!=null){
							SpokenTimes = CourseSchedulingAssist.getExtandVal2();
						}
					}
				}
					if((cnCourseTimes+enCourseTimes)*60!=Hourlen){
						throw  new Exception("保存失败：中教时长("+cnCourseTimes+")加上外教时长("+enCourseTimes+")不等于课时长度（" +Hourlen/60+ "）,请重新设置高级参数！");
					}
				}

			courseService.updateCourseSchedulingAssist(
					schedulingAssist.getSchedulingAssistList(),
					account.getId(), courseId);
			resultMap.put("error", false);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 新增
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	public Map<String, Object> addCourse(@RequestBody Map<String, Object> param,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		TCourse course=null;
		try {
			Account account = WebContextUtils.genUser(request);
			Object relationIds = param.get("relationIds");//一对一课程关联的校区id组合，逗号分隔
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			course = ModelDataUtils.getPojoMatch(TCourse.class, param);
			course.setStatus(TCourse.StatusEnum.VALID.getCode());
			course.setCity_id(orgModel.getCityId());
			course.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			course.setCreate_user(account.getId());
			course.setProduct_line(orgModel.getProductLine());
			Long business_type = course.getBusiness_type();
			if(business_type == (long)TCourse.BusinessTypeEnum.BJK.getCode()) {
				int result=courseService.queryRepeatedCourses(course);
				if(result>0){
					 throw new Exception("课程" + course.getCourse_name() + "已存在，请核实已上架课程");
				}
				//判断课程季时间是否正确
				if(course.getSeason_id()!=null) {
					TimeSeason timeSeason = timeSeasonService.queryById(course.getSeason_id().toString());
					String seasonStartDate = timeSeason.getStart_date();
					String seasonEndDate = timeSeason.getEnd_date();
					String courseStartDate = course.getStart_date();
					String courseEndDate = course.getEnd_date();
					Long compareStartDate = DateUtil.compareDate(courseStartDate, seasonStartDate, 0);
					Long compareEndDate = DateUtil.compareDate(seasonEndDate, courseEndDate, 0);
					if (compareStartDate > 0) {
						throw new Exception("课程的开始日期"+"["+courseStartDate+"]"+"早于课程季开始日期"+"["+seasonStartDate+"]");
					}
					if (compareEndDate > 0) {
						throw new Exception("课程的结束日期"+"["+courseEndDate+"]"+"晚于课程季结束日期"+"["+seasonEndDate+"]");
					}
				}
				course.setCourse_no(EncodingSequenceUtil.getSequenceNum(9L));
				courseService.toAdd(course);
			} else if(business_type == (long)TCourse.BusinessTypeEnum.WFD.getCode()) {
				course.setCourse_no(EncodingSequenceUtil.getSequenceNum(11L));
				course.setPerformance_belong_type(orgModel.getProductLine().equals(1L) ?2:1);
				courseService.toAdd(course,null);
			} else if(business_type == (long)TCourse.BusinessTypeEnum.YDY.getCode()) {
				//一对一课程新增
				if(null == course.getProduct_type()){
					course.setProduct_type(1L);//设置销售类型1：正价；2：促销；3：赠送
				}
				course.setPerformance_belong_type(1);
				course.setCourse_no(EncodingSequenceUtil.getSequenceNum(10L));
				courseService.toAddYdy(course,relationIds.toString() );
			}
			resultMap.put("error", false);
			resultMap.put("message", "新增成功!");
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/service" }, method = RequestMethod.DELETE, headers = { "Accept=application/json" })
	public Map<String, Object> toRemove(@RequestParam("ids") String ids,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			courseService.toChangeStatus(ids, TCourse.StatusEnum.DELETE.getCode().toString(),account.getId());
			StringBuffer buff  = new StringBuffer();
			buff.append("ID：");
			buff.append(ids);
			resultMap.put("error", false);
		} catch (Exception e) {
			StringBuffer buff  = new StringBuffer();
			buff.append("ID：");
			buff.append(ids);
			buff.append("，");
			buff.append("失败信息：");
			buff.append(e);
			resultMap.put("error", true);
			resultMap.put("message", buff.toString());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 场景：报班页面，课程信息查询
	 * 
	 * @Description: 查询给定的学生的报班信息
	 * @param request
	 *            请求对象
	 * 
	 * @param response
	 *            响应对象
	 * @return List<TeacherBusiness> 返回类型
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/queryCourseByStudentId", headers = "Accept=application/json")
	public  Map<String, Object> queryCourseByStudentId(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区!");
				return resultMap;
			}
			Map<String, Object> queryParam = initParamMap(request, true);
			queryParam.put("business_type", genLongParameter("business_type", request));
			queryParam.put("branch_id", genLongParameter("branch_id", request));
			if(genLongParameter("branch_id", request)!=null){
				queryParam.remove("per_branch_ids");
			}
			queryParam.put("studentId", genLongParameter("studentId", request));
			queryParam.put("season_id", genLongParameter("season_id", request));
			queryParam.put("course_id", genLongParameter("course_id", request));
			queryParam.put("grade_id", genLongParameter("grade_id", request));
			queryParam.put("subject_id", genLongParameter("subject_id", request));
			queryParam.put("teacher_id",request.getParameter("teacher_id"));
			queryParam.put("course_name", request.getParameter("course_name"));
			queryParam.put("status", genLongParameter("status", request));
			List<TCourse> tCourse  = this.courseService.queryCourseByBusiness(queryParam);
			resultMap.put("error", false);
			resultMap.put("data", tCourse);
		} catch (Exception e) {
			log.error("error found,see: ", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 批量修改课程课次服务
	 * @param params
	 *            课程课次标题和备注 格式如下
	 * <pre>
	 * 		1：课次   标题   备注
	 * 		2：课次   标题
	 * </pre>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = { "/coursetimes/batch" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateCourseTimes(@RequestBody Map<String,String> params ,HttpServletRequest request, HttpServletResponse response)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			Long course_id = Long.valueOf(params.get("course_id"));
			String courseSchedulingDetails = (String)params.get("courseSchedulingDetails");
			if (course_id == null) {
				log.error("error found ,see:缺失参数-课程id！");
				throw new Exception("缺失参数-课程id！");
			} 
			if(courseSchedulingDetails == null || StringUtils.isEmpty(courseSchedulingDetails.trim())) {
				log.error("error found ,课次标题必填！");
				throw new Exception("课次标题必填！");
			}
			Account account = WebContextUtils.genUser(request);
			courseSchedulingService.batchUpdateCourseTimesTitle(course_id,courseSchedulingDetails, account.getId());
			resultMap.put("error", false);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/outputExcelForCourse" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputExcelForCourse(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);

		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件路径
		String templateFilePath = rootPath + File.separator + "template";
		// 模板文件名
		String templateFileName = "course.xlsx";
		// 临时文件路径
		String tempFilePath = rootPath + File.separator + "temp";
		// 临时文件名
		String tempFileName = "course_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null ) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择团队或校区!");
				return resultMap;
			}
			param.put("city_id", orgModel.getCityId());
			param.put("bu_id", orgModel.getBuId());
			if(param.get("branch_id") == null || param.get("branch_id").equals(-1L)|| param.get("branch_id").equals(-1)){
				param.put("per_branch_ids",getAllowedBranchByBuId(request));
			} else {
				param.remove("per_branch_ids");
			}
			List<Map<String, Object>> courseList = courseService.queryListForExcel(param);
			if (StringUtil.isEmpty(courseList)) {
				throw new Exception("导出数据不能为空！");
			}
			// 导出文件目录
			String outFilePath = ExcelWriteHandler.writeDataToExcel(courseList, templateFilePath, templateFileName,
					tempFilePath, tempFileName);
			log.debug("生成导出临时文件：" + outFilePath);
			resultMap.put(Constants.RespMapKey.DATA, tempFileName);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "课程导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 查询佳音考勤单个课次的高级参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/schedulingTimeAssist" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> querySchedulingTimeAssistList(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Long schedulingId = genLongParameter("schedulingId", request);
			if (schedulingId == null) {
				log.error("error found ,see:要查询的课次为空！");
				throw new Exception("访问非法！");
			}
			List<TCourseSchedulingAssist> result = courseService
					.queryCourseSchedulingTimeAssist(schedulingId);
			resultMap.put("data", result);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 设置佳音考勤单个课次的高级参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/schedulingTimeAssist" }, method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public Map<String, Object> updateSchedulingTimeAssistList(
			@RequestBody SchedulingAssist schedulingAssist,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || (!"4".equals(orgModel.getType()))) {
				log.error("没有选择校区！");
				throw new Exception("请选择校区!");
			}
			if(null == orgModel.getProductLine()) {
				throw new Exception("请初始化组织产品线!");
			}

			if(ProductLine.JIA_YIN.getId().equals(orgModel.getProductLine())) {//佳音的培英班考勤
				Long id = schedulingAssist.getSchedulingId();
				String startTime = courseSchedulingService.queryOne(id).getStart_time();
				String endTime = courseSchedulingService.queryOne(id).getEnd_time();
				String[] format = {"HH:mm"};
				Date start = DateUtils.parseDate(startTime,format);
				Date end = DateUtils.parseDate(endTime, format);
				Double Hourlen =((end.getTime()-start.getTime())/60000.0);
				if(Hourlen<0 || Hourlen==0){
					throw new Exception("课程缺失课时长度，请补充课程信息！");
				}
				List<TCourseSchedulingAssist> CourseSchedulingAssistList = schedulingAssist.getSchedulingAssistList();
				Double cnCourseTimes = null;
				Double enCourseTimes = null;
				Double SpokenTimes = null;
				for(TCourseSchedulingAssist CourseSchedulingAssist:CourseSchedulingAssistList){
					if(CourseSchedulingAssist.getCourseName() != null && CourseSchedulingAssist.getCourseName().equals("中文老师")){
						if(CourseSchedulingAssist.getCourseVal()==null){
							cnCourseTimes=0.00;
							CourseSchedulingAssist.setExtandVal1(0.00);
						}else{
							cnCourseTimes = CourseSchedulingAssist.getExtandVal1()==null?0:CourseSchedulingAssist.getExtandVal1();
						}
					}else if(CourseSchedulingAssist.getCourseName() != null&&CourseSchedulingAssist.getCourseName().equals("外文老师")){
						if(CourseSchedulingAssist.getCourseVal()==null){
							enCourseTimes=0.00;
							CourseSchedulingAssist.setExtandVal1(0.00);
						}else{
							enCourseTimes =CourseSchedulingAssist.getExtandVal1()==null?0:CourseSchedulingAssist.getExtandVal1();
						}
						if(CourseSchedulingAssist.getExtandVal2()!=null){
							SpokenTimes = CourseSchedulingAssist.getExtandVal2();
						}
					}
				}

					if((cnCourseTimes+enCourseTimes)*60!=Hourlen){
						throw  new Exception("保存失败：中教时长("+cnCourseTimes+")加上外教时长（"+enCourseTimes+"）不等于课时长度（" +Hourlen/60+ "）,请重新设置高级参数！");
					}

			}
			courseService.updateCourseSchedulingTimeAssist(schedulingAssist);
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

    @ResponseBody
    @RequestMapping(value = { "/queryEndTimesByPeriod" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
    public Map<String, Object> queryEndTimesByPeriod(
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("error", false);
        try {
            Long course_count = genLongParameter("course_count", request);
            String attend_class_period = request.getParameter("attend_class_period");;
            String start_date = request.getParameter("start_date");
            TCourse course=new TCourse();
            course.setAttend_class_period(attend_class_period);
            course.setStart_date(start_date);
            course.setCourse_count(course_count);
            Date resultTime=courseService.queryEndTimesByPeriod(course);
            resultMap.put("end_date", resultTime);
        } catch (Exception e) {
            log.error("error found ,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

	/**
	 * 校验添加课程的重复
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/checkAll" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> checkAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		try {
			TCourse course = new TCourse();
			course.setStart_date(request.getParameter("start_date"));
			course.setCourse_name(request.getParameter("course_name"));
			course.setBranch_id(genLongParameter("branch_id", request));
			course.setGrade_id(genLongParameter("grade_id", request));
			course.setTeacher_id(genLongParameter("teacher_id", request));
			course.setSubject_id(genLongParameter("subject_id", request));
			course.setUnit_price(genLongParameter("unit_price", request));
			int result=courseService.queryRepeatedCourses(course);
			if(result>0){
				throw new Exception("课程" + course.getCourse_name() + "已存在，请核实已上架课程");
			}else{
			  resultMap.put("error",false);
			}
		} catch (Exception e) {
			log.error("error found ,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = {"/queryHourLen"},method = RequestMethod.GET,headers ={"Accept=application/json" })
	public Map<String,Object> queryHourLen(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("error", false);
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
		DecimalFormat df = new DecimalFormat("#.00");
		Double hourLen = courseService.queryHourLen(startTime,endTime);
        resultMap.put("hour_len",df.format(hourLen));
        return resultMap;
	}

}
