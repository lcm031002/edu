package com.edu.erp.course_manager.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.common.util.EncodingSequenceUtil;
import com.edu.erp.dict.service.OrganizationService;
import com.edu.erp.dict.service.TimeSeasonService;
import com.edu.erp.model.OrganizationInfo;
import com.edu.erp.model.TimeSeason;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.constants.Constants;
import com.edu.erp.course_manager.service.CourseService;
import com.edu.erp.course_manager.service.TempTCourseService;
import com.edu.erp.model.TCourse;
import com.edu.erp.model.TempTCourse;
import com.edu.erp.util.BaseInputController;
import com.edu.common.util.ERPConstants;
import com.edu.common.util.ExcelReadHandler;
import com.edu.erp.util.ExcelWriteHandler;
import com.edu.erp.util.ModelDataUtils;
import com.edu.erp.util.StringUtil;

/**
 * 课程导入
 * 
 * @ClassName: ExcelInputController
 * @Description:
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年3月17日 下午4:31:47
 *
 */
@Controller
@RequestMapping(value = { "/coursemanagerment" })
public class ExcelInputController extends BaseInputController {

	private static final Logger log = Logger.getLogger(ExcelInputController.class);

	@Resource(name = "courseService")
	private CourseService courseService;

	@Resource(name = "tempTCourseService")
	private TempTCourseService tempTCourseService;
	
	@Resource(name = "organizationService")
	private OrganizationService organizationService;

	@Resource(name="timeSeasonService")
	private TimeSeasonService timeSeasonService;

	/**
	 * 上传Excel文件
	 * 
	 * @param fileExcel
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/inputExcel" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> inputExcel(@RequestParam(value = "fileExcel", required = true) MultipartFile fileExcel,
			@RequestParam Integer business_type, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("非法访问！");
			}

			List<Map<String, Object>> rowList = ExcelReadHandler.readExcel(fileExcel.getInputStream());
			if (StringUtil.isEmpty(rowList)) {
				throw new Exception("上传文件没有内容！");
			}
			List<TempTCourse> courseList = new ArrayList<>();
			TempTCourse course = null;
			for (Map<String, Object> row : rowList) {
				// 将excel行数据转换成模板对象
				course = ModelDataUtils.getPojoMatch(TempTCourse.class, row);
				course.setBusiness_type((long) business_type);
				course.setCity_id(orgModel.getCityId());
				course.setBu_id(orgModel.getBuId());
				courseList.add(course);
			}
			resultMap.put(Constants.RespMapKey.DATA, courseList);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "上传失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 数据校验
	 * 
	 * @param paramMap
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/inputDataCheck" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> inputDataCheck(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("非法访问！");
			}

			Integer param_business_type = (Integer) paramMap.get("business_type");
			List<Map<String, Object>> uploadCourseList = (List<Map<String, Object>>) paramMap.get("courseList");
			if (StringUtil.isEmpty(uploadCourseList)) {
				throw new Exception("校验数据不能为空！");
			}
			// 1.导入数据检查预处理（把 编码或名称 转换成 Id）
			List<TempTCourse> tempCourseList = tempTCourseService.prepareInputDataCheck(uploadCourseList);
			// 2.按行号做Hash化处理，便于检索
			Map<Long, TempTCourse> rowNoCourseMap = new HashMap<>();
			for (TempTCourse tempTCourse : tempCourseList) {
				rowNoCourseMap.put(tempTCourse.getRow_no(), tempTCourse);
			}
			// 3.校验导入数据
			// 班级课校验：文档内课程编码重复、系统中已存在此课程编码、地区、校区、年级、课程级、科目、教师、课程目标、产品线、销售类型、状态、教材
			// 一对一校验：地区、校区、销售类型、状态；
			// 晚辅导校验：地区、校区、文档内课程编码重复、系统中已存在此课程编码、销售类型、状态、年级
			// 晚辅导套餐：地区、校区、套餐类型、状态、年级 (暂不支持)
			TempTCourse course = null;
			List<TempTCourse> returnCourseList = new ArrayList<>();
			TempTCourse tempTCourse = null;
			Set<String> courseNoList = new HashSet<String>();
			List<Map<String, String>> rowErrorList = null;
			for (Map<String, Object> uploadCourse : uploadCourseList) {
				rowErrorList = new ArrayList<>();
				course = ModelDataUtils.getPojoMatch(TempTCourse.class, uploadCourse);
				tempTCourse = rowNoCourseMap.get(course.getRow_no());

				switch (param_business_type) {
				case 1: // 班级课
					// 课程编码
					if (StringUtil.isEmpty(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "课程编码必填"));
					} else if (courseNoList.contains(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "文档中已存在此课程编码"));
					} else if (!StringUtil.isEmpty(tempTCourse.getCourse_old_id())) {
						rowErrorList.add(createErrorMsg("course_no", "系统中已存在此课程编码"));
					}
					courseNoList.add(course.getCourse_no());

					// 课程名称
					if (StringUtil.isEmpty(course.getCourse_name())) {
						rowErrorList.add(createErrorMsg("course_name", "课程名称必填"));
					} else if(!StringUtil.isEmpty(course.getCourse_name())){
						TCourse tCourse = new TCourse();
						tCourse.setCourse_name(course.getCourse_name());
						tCourse.setBranch_id(tempTCourse.getBranch_id());
						tCourse.setStart_date(course.getStart_date());
						tCourse.setGrade_id(tempTCourse.getGrade_id());
						tCourse.setTeacher_id(tempTCourse.getTeacher_id());
						tCourse.setSubject_id(tempTCourse.getSubject_id());
						tCourse.setUnit_price(course.getUnit_price());
						if(courseService.queryRepeatedCourses(tCourse)>0){
						rowErrorList.add(createErrorMsg("course_name","课程信息重复，请核实已上架课程!"));
						}
					}
					// 地区
//					if (StringUtil.isEmpty(course.getCity_name())) {
//						rowErrorList.add(createErrorMsg("city_name", "地区必填"));
//					} else if (StringUtil.isEmpty(course.getCity_id())) {
//						if (StringUtil.isEmpty(tempTCourse.getCity_id())) {
//							rowErrorList.add(createErrorMsg("city_name", "地区名称错误"));
//						} else {
//							course.setCity_id(tempTCourse.getCity_id());
//						}
//					}
					// 校区
					if (StringUtil.isEmpty(course.getBranch_name())) {
						rowErrorList.add(createErrorMsg("branch_name", "校区必填"));
					} else if (StringUtil.isEmpty(course.getBranch_id())) {
						if (StringUtil.isEmpty(tempTCourse.getBranch_id())) {
							rowErrorList.add(createErrorMsg("branch_name", "该城市不存在此校区"));
						} else {
							course.setBranch_id(tempTCourse.getBranch_id());
						}
					}
					// 业绩归属类型
					if(StringUtils.isEmpty(course.getPerformance_belong_type_name())) {
						rowErrorList.add(createErrorMsg("performance_belong_type_name", "业绩归属类型必填"));
					} else {
						String getPerformance_belong_type_name = course.getPerformance_belong_type_name();
						if(getPerformance_belong_type_name.equals("代办校区")) {
							course.setPerformance_belong_type(1);
						} else if(getPerformance_belong_type_name.equals("课程校区")) {
							course.setPerformance_belong_type(2);
						} else {
							rowErrorList.add(createErrorMsg("performance_belong_type_name", "业绩归属类型错误"));
						}
					}
					// 年级
					if (StringUtil.isEmpty(course.getGrade_name())) {
						rowErrorList.add(createErrorMsg("grade_name", "年级必填"));
					} else if (StringUtil.isEmpty(course.getGrade_id())) {
						if (StringUtil.isEmpty(tempTCourse.getGrade_id())) {
							rowErrorList.add(createErrorMsg("grade_name", "年级名称错误"));
						} else {
							course.setGrade_id(tempTCourse.getGrade_id());
						}
					}
					// 课程单价
					if (StringUtil.isEmpty(course.getUnit_price())) {
						rowErrorList.add(createErrorMsg("unit_price", "课程单价必填"));
					}
					// 课程总价
					if (StringUtil.isEmpty(course.getSum_price())) {
						rowErrorList.add(createErrorMsg("sum_price", "课程总价必填"));
					}
					// 课时数量
					if (StringUtil.isEmpty(course.getCourse_count())) {
						rowErrorList.add(createErrorMsg("course_count", "课时数量必填"));
					}
					// 科目
					if (StringUtil.isEmpty(course.getSubject_name())) {
						rowErrorList.add(createErrorMsg("subject_name", "科目必填"));
					} else if (StringUtil.isEmpty(course.getSubject_id())) {
						if (StringUtil.isEmpty(tempTCourse.getSubject_id())) {
							rowErrorList.add(createErrorMsg("subject_name", "科目名称错误"));
						} else {
							course.setSubject_id(tempTCourse.getSubject_id());
						}
					}
					// 教师
					if (StringUtil.isEmpty(course.getTeacher_code())) {
						rowErrorList.add(createErrorMsg("teacher_code", "教师必填"));
					} else if (StringUtil.isEmpty(course.getTeacher_id())) {
						if (StringUtil.isEmpty(tempTCourse.getTeacher_id())) {
							rowErrorList.add(createErrorMsg("teacher_code", "该城市不存在此老师"));
						} else {
							course.setTeacher_id(tempTCourse.getTeacher_id());
						}
					}
					// 助教
					if (StringUtil.isEmpty(course.getAssteacher_code())) {
						// 不需要必填
					} else if (StringUtil.isEmpty(course.getAssteacher_id())) {
						if (StringUtil.isEmpty(tempTCourse.getAssteacher_id())) {
							rowErrorList.add(createErrorMsg("assteacher_code", "辅导老师编码错误"));
						} else {
							course.setAssteacher_id(tempTCourse.getAssteacher_id());
						}
					}
					// 课程目标
					if (!StringUtil.isEmpty(course.getTarget_name()) && StringUtil.isEmpty(course.getTarget())) {
						course.setTarget(tempTCourse.getTarget());
					}


					// 课时长度
					if (null == course.getHour_len()) {
						String startTime = course.getStart_time();
						String endTime = course.getEnd_time();
						String[] format = {"HH:mm"};
						Date start = DateUtils.parseDate(startTime,format);
						Date end = DateUtils.parseDate(endTime, format);
						Double Hourlen =((end.getTime()-start.getTime())/60000.0)/60;
						DecimalFormat df = new DecimalFormat("#.00");
						course.setHour_len(Double.parseDouble(df.format(Hourlen)));
						//系统自动计算课时长度，所以不判断格式
//						Pattern pattern = Pattern.compile("[1-9][0-9]*");
//						Matcher matcher = pattern.matcher(course.getHour_len().toString());
//						if (!matcher.matches()) {
//							rowErrorList.add(createErrorMsg("hour_len", "课时长度格式错误"));
//						}
					} else {
						String startTime = course.getStart_time();
						String endTime = course.getEnd_time();
						String[] format = {"HH:mm"};
						Date start = DateUtils.parseDate(startTime,format);
						Date end = DateUtils.parseDate(endTime, format);
						Double Hourlen =((end.getTime()-start.getTime())/60000.0)/60;
						DecimalFormat df = new DecimalFormat("#.00");
						course.setHour_len(Double.parseDouble(df.format(Hourlen)));
						//系统自动计算课时长度，所以不判断格式
//						Pattern pattern = Pattern.compile("[1-9][0-9]*");
//						Matcher matcher = pattern.matcher(course.getHour_len().toString());
//						if (!matcher.matches()) {
//							rowErrorList.add(createErrorMsg("hour_len", "课时长度格式错误"));
//						}
					}

					// 开课日期
					if (StringUtils.isEmpty(course.getStart_date())) {
						rowErrorList.add(createErrorMsg("start_date", "开课日期必填"));
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getStart_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("start_date", "开课日期格式错误"));
						}
					}
					// 结课日期
					if (StringUtils.isEmpty(course.getEnd_date())) {
						TCourse courseone=new TCourse();
						courseone.setAttend_class_period(course.getAttend_class_period());
						courseone.setStart_date(course.getStart_date());
						courseone.setCourse_count(course.getCourse_count());
						Date resultTime=courseService.queryEndTimesByPeriod(courseone);
						SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
						String str = sdf.format(resultTime);
						course.setEnd_date(str);
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getEnd_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("end_date", "结课日期格式错误"));
						}
					}

					String timeRex = "((0[0-9])|(1[0-9])|(2[0-3]))\\:[0-5][0-9]";
					Pattern p = Pattern.compile(timeRex);
					if(StringUtil.isEmpty(course.getStart_time())) {
						rowErrorList.add(createErrorMsg("start_time", "上课时间必填"));
					} else {
						Matcher m = p.matcher(course.getStart_time());
						if(!m.matches()) {
							rowErrorList.add(createErrorMsg("start_time", "上课时间格式错误"));
						}

					}
					if(StringUtil.isEmpty(course.getEnd_time())) {
						rowErrorList.add(createErrorMsg("end_time", "下课时间必填"));
					} else {
						Matcher m = p.matcher(course.getEnd_time());
						if(!m.matches()) {
							rowErrorList.add(createErrorMsg("end_time", "下课时间格式错误"));
						}

					}


					// 上课周期
					if (StringUtil.isEmpty(course.getAttend_class_period())) {
						rowErrorList.add(createErrorMsg("attend_class_period", "上课周期必填"));
					} else {
						Pattern pattern = Pattern.compile("([1-7](~\\d{2}:\\d{2}-\\d{2}:\\d{2})?,)*([1-7](~\\d{2}:\\d{2}-\\d{2}:\\d{2})?)$");
						Matcher matcher = pattern.matcher(course.getAttend_class_period());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("attend_class_period", "上课周期格式错误"));
						}
					}

                   //计划人数
					if (StringUtil.isEmpty(course.getPeople_count())) {
						rowErrorList.add(createErrorMsg("people_count", "计划人数必填"));
					}else{
						if(course.getPeople_count()<0){
						rowErrorList.add(createErrorMsg("people_count", "计划上课人数必须为整数！"));
					}
					}

					// 课程季
					if (StringUtil.isEmpty(course.getSeason_name())) {
						Long branch_id=course.getBranch_id();
						OrganizationInfo organizationInfo = this.organizationService.selectById(branch_id);
						Long productLine = organizationInfo.getProduct_line();
						if(productLine!=11L){
							if(course.getSeason_id()==null){
								rowErrorList.add(createErrorMsg("season_name", "课程季不能为空"));
							}
						}
					} else if (StringUtil.isEmpty(course.getSeason_id())) {
						if (StringUtil.isEmpty(tempTCourse.getSeason_id())) {
							rowErrorList.add(createErrorMsg("season_name", "该城市不存在此课程季"));
						} else {
							TimeSeason timeSeason = timeSeasonService.queryById(tempTCourse.getSeason_id().toString());
							String seasonStartDate = timeSeason.getStart_date();
							String seasonEndDate = timeSeason.getEnd_date();
							String courseStartDate = course.getStart_date();
							String courseEndDate = course.getEnd_date();
							Long compareStartDate = DateUtil.compareDate(courseStartDate, seasonStartDate, 0);
							Long compareEndDate = DateUtil.compareDate(seasonEndDate, courseEndDate, 0);
							if (compareStartDate > 0) {
								rowErrorList.add(createErrorMsg("season_name", "课程的开始日期"+"["+courseStartDate+"]"+"早于课程季开始日期"+"["+seasonStartDate+"]"));
							}
							if (compareEndDate > 0) {
								rowErrorList.add(createErrorMsg("season_name", "课程的结束日期"+"["+courseEndDate+"]"+"晚于课程季结束日期"+"["+seasonEndDate+"]"));
							}
							course.setSeason_id(tempTCourse.getSeason_id());
						}
					}
					// 产品线
//					if (StringUtil.isEmpty(course.getProduct_line_name())) {
//						rowErrorList.add(createErrorMsg("product_line_name", "产品线必填"));
//					} else if (StringUtil.isEmpty(course.getProduct_line())) {
//						if (StringUtil.isEmpty(tempTCourse.getProduct_line())) {
//							rowErrorList.add(createErrorMsg("product_line_name", "产品线错误"));
//						} else {
//							course.setProduct_line(tempTCourse.getProduct_line());
//						}
//					}
					// 是否补课
					if (StringUtil.isEmpty(course.getIs_cramclass())) {
						if ("是".equals(course.getIs_cramclass_name())) {
							course.setIs_cramclass(1l);
						} else if ("否".equals(course.getIs_cramclass_name())) {
							course.setIs_cramclass(0l);
						} else if (StringUtil.isEmpty(course.getIs_cramclass_name())) {
							course.setIs_cramclass(0l);
						} else {
							rowErrorList.add(createErrorMsg("is_cramclass_name", "是否补课状态只能为:是,否,空"));
						}
					}
					// 销售类型
					if (!StringUtil.isEmpty(course.getProduct_type_name())
							&& StringUtil.isEmpty(course.getProduct_type())) {
						if ("正价".equals(course.getProduct_type_name())) {
							course.setProduct_type(1l);
						} else if ("促销".equals(course.getProduct_type_name())) {
							course.setProduct_type(2l);
						} else if ("赠送".equals(course.getProduct_type_name())) {
							course.setProduct_type(3l);
						}
					}
					// 同类课程
					if (!StringUtil.isEmpty(course.getType_name()) && StringUtil.isEmpty(course.getCourse_type())) {
						course.setCourse_type(tempTCourse.getCourse_type());
					}
					// 状态
					if (StringUtil.isEmpty(course.getStatus())) {
						if ("上架".equals(course.getStatus_name())) {
							course.setStatus(1);
						} else if ("下架".equals(course.getStatus_name())) {
							course.setStatus(2);
						} else {
							rowErrorList.add(createErrorMsg("status_name", "状态不存在"));
						}
					}
					// 教材
					if (StringUtil.isEmpty(course.getIs_textbooks())) {
						if ("是".equals(course.getIs_textbooks_name())) {
							course.setIs_textbooks(1l);
						} else if ("否".equals(course.getIs_textbooks_name())) {
							course.setIs_textbooks(0l);
						} else if (StringUtil.isEmpty(course.getIs_textbooks_name())) {
							course.setIs_textbooks(0l);
						} else {
							rowErrorList.add(createErrorMsg("is_textbooks", "教材状态只能为:是,否,空"));
						}
					}



					break;
				case 2: // 一对一：地区、校区、销售类型、状态；
					// 课程编码
					if (StringUtil.isEmpty(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "课程编码必填"));
					} else if (courseNoList.contains(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "文档中已存在此课程编码"));
					} else if (!StringUtil.isEmpty(tempTCourse.getCourse_old_id())) {
						rowErrorList.add(createErrorMsg("course_no", "系统中已存在此课程编码"));
					}
					courseNoList.add(course.getCourse_no());

					// 课程名称
					if (StringUtil.isEmpty(course.getCourse_name())) {
						rowErrorList.add(createErrorMsg("course_name", "必填"));
					}
					// 地区
//					if (StringUtil.isEmpty(course.getCity_name())) {
//						rowErrorList.add(createErrorMsg("city_name", "地区必填"));
//					} else if (StringUtil.isEmpty(course.getCity_id())) {
//						if (StringUtil.isEmpty(tempTCourse.getCity_id())) {
//							rowErrorList.add(createErrorMsg("city_name", "地区名称错误"));
//						} else {
//							course.setCity_id(tempTCourse.getCity_id());
//						}
//					}

					// 应用所有校区
					if (StringUtil.isEmpty(course.getIs_all_name())) {
						rowErrorList.add(createErrorMsg("is_all_name", "是否应用所有校区必填"));
					} else if ("是".equals(course.getIs_all_name())) {
						course.setIs_all(1L);
						course.setBranch_names("");
						course.setBranch_ids("");
					} else {
						course.setIs_all(0L);

						// 校区
						if (StringUtil.isEmpty(course.getBranch_names())) {
							rowErrorList.add(createErrorMsg("branch_names", "校区必填"));
						} else{
							//查询团队下的有权限的校区
							Account account = WebContextUtils.genUser(request);
							List<OrganizationInfo> orgList = organizationService.queryBuBranchs(account.getId(), orgModel.getBuId());
							String[] branchName = course.getBranch_names().split("[,，]");
							String branchIds = "";
							String branchNames ="";
							String errorMsg = "";
							boolean contains = false;
							for(String branch:branchName) {
								contains = false;
								for(OrganizationInfo org : orgList) {
									if(org.getOrg_name().equals(branch)) {
										branchIds = branchIds + "," + org.getId();
										branchNames = branchNames + "," + org.getOrg_name();
										contains = true;
										break;
									}
								}
								if(!contains) {
									errorMsg = errorMsg + ',' + branch;
								}
							}
							if(!StringUtil.isEmpty(errorMsg)) {
								rowErrorList.add(createErrorMsg("branch_names", "错误的校区有：" + errorMsg.substring(1)));
							}
							//course.setBranch_names(StringUtil.isEmpty(branchNames)?"":branchNames.substring(1));
							course.setBranch_ids(StringUtil.isEmpty(branchIds)?"":branchIds.substring(1));
						}
					}
				    // 年级
					if (StringUtil.isEmpty(course.getGrade_name())) {
						rowErrorList.add(createErrorMsg("grade_name", "年级必填"));
					} else if (StringUtil.isEmpty(course.getGrade_id())) {
						if (StringUtil.isEmpty(tempTCourse.getGrade_id())) {
							rowErrorList.add(createErrorMsg("grade_name", "年级名称错误"));
						} else {
							course.setGrade_id(tempTCourse.getGrade_id());
						}
					}
					// 销售类型
//					if (StringUtils.isEmpty(course.getProduct_type_name())) {
//						rowErrorList.add(createErrorMsg("product_type_name", "销售类型编码必填"));} else
					if ("正价".equals(course.getProduct_type_name())) {
						course.setProduct_type(1l);
					} else if ("促销".equals(course.getProduct_type_name())) {
						course.setProduct_type(2l);
					} else if ("赠送".equals(course.getProduct_type_name())) {
						course.setProduct_type(3l);
					}
//					else {
//						rowErrorList.add(createErrorMsg("product_type_name", "销售类型不存在"));
//					}
					// 状态
					if (StringUtils.isEmpty(course.getStatus_name())) {
						rowErrorList.add(createErrorMsg("status_name", "状态必填"));
					} else if ("上架".equals(course.getStatus_name())) {
						course.setStatus(1);
					} else if ("下架".equals(course.getStatus_name())) {
						course.setStatus(2);
					} else {
						rowErrorList.add(createErrorMsg("status_name", "状态不存在"));
					}
					// 开课日期
					if (StringUtils.isEmpty(course.getStart_date())) {
						rowErrorList.add(createErrorMsg("start_date", "开课日期必填"));
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getStart_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("start_date", "开课日期格式错误"));
						}
					}
					// 结课日期
					if (StringUtils.isEmpty(course.getEnd_date())) {
						rowErrorList.add(createErrorMsg("end_date", "结课日期必填"));
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getEnd_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("end_date", "结课日期格式错误"));
						}
					}
					// 课时单价
					if (null == course.getUnit_price()) {
						rowErrorList.add(createErrorMsg("unit_price", "课时单价必填"));
					} else {
						Pattern pattern = Pattern.compile("[1-9][0-9]*([\\.]0{1,2})?");
						Matcher matcher = pattern.matcher(course.getUnit_price().toString());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("unit_price", "课时单价格式错误"));
						}
					}
					// 课时长度
					if (null == course.getHour_len()) {
						rowErrorList.add(createErrorMsg("hour_len", "课时长度必填"));
					} else {
						Pattern pattern = Pattern.compile("[1-9][0-9]*");
						Matcher matcher = pattern.matcher(course.getUnit_price().toString());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("hour_len", "课时长度格式错误"));
						}
					}
					break;
				case 3: // 晚辅导校验：地区、校区、文档内课程编码重复、系统中已存在此课程编码、销售类型、状态、年级
					// 课程编码
					if (StringUtil.isEmpty(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "必填"));
					} else if (courseNoList.contains(course.getCourse_no())) {
						rowErrorList.add(createErrorMsg("course_no", "文档中已存在此课程编码"));
					} else if (!StringUtil.isEmpty(tempTCourse.getCourse_old_id())) {
						rowErrorList.add(createErrorMsg("course_no", "系统中已存在此课程编码"));
					}
					courseNoList.add(course.getCourse_no());

					// 课程名称
					if (StringUtil.isEmpty(course.getCourse_name())) {
						rowErrorList.add(createErrorMsg("course_name", "必填"));
					}
					// 地区
//					if (StringUtil.isEmpty(course.getCity_name())) {
//						rowErrorList.add(createErrorMsg("city_name", "必填"));
//					} else if (StringUtil.isEmpty(course.getCity_id())) {
//						if (StringUtil.isEmpty(tempTCourse.getCity_id())) {
//							rowErrorList.add(createErrorMsg("city_name", "地区名称错误"));
//						} else {
//							course.setCity_id(tempTCourse.getCity_id());
//						}
//					}
					// 校区
					if (StringUtil.isEmpty(course.getBranch_name())) {
						rowErrorList.add(createErrorMsg("branch_name", "必填"));
					} else if (StringUtil.isEmpty(course.getBranch_id())) {
						if (StringUtil.isEmpty(tempTCourse.getBranch_id())) {
							rowErrorList.add(createErrorMsg("branch_name", "校区名称错误"));
						} else {
							course.setBranch_id(tempTCourse.getBranch_id());
						}
					}
					// 年级
					if (StringUtil.isEmpty(course.getGrade_name())) {
						rowErrorList.add(createErrorMsg("grade_name", "必填"));
					} else if (StringUtil.isEmpty(course.getGrade_id())) {
						if (StringUtil.isEmpty(tempTCourse.getGrade_id())) {
							rowErrorList.add(createErrorMsg("grade_name", "年级名称错误"));
						} else {
							course.setGrade_id(tempTCourse.getGrade_id());
						}
					}
					// 开课日期
					if (StringUtils.isEmpty(course.getStart_date())) {
						rowErrorList.add(createErrorMsg("start_date", "开课日期必填"));
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getStart_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("start_date", "开课日期格式错误"));
						}
					}
					// 结课日期
					if (StringUtils.isEmpty(course.getEnd_date())) {
						rowErrorList.add(createErrorMsg("end_date", "结课日期必填"));
					} else {
						Pattern pattern = Pattern.compile("[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}");
						Matcher matcher = pattern.matcher(course.getEnd_date());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("end_date", "结课日期格式错误"));
						}
					}
					// 课时单价
					if (null == course.getUnit_price()) {
						rowErrorList.add(createErrorMsg("unit_price", "课时单价必填"));
					} else {
						Pattern pattern = Pattern.compile("[1-9][0-9]*([\\.]0{1,2})?");
						Matcher matcher = pattern.matcher(course.getUnit_price().toString());
						if (!matcher.matches()) {
							rowErrorList.add(createErrorMsg("unit_price", "课时单价格式错误"));
						}
					}
					// 状态
					if (StringUtil.isEmpty(course.getStatus())) {
						if ("上架".equals(course.getStatus_name())) {
							course.setStatus(1);
						} else if ("下架".equals(course.getStatus_name())) {
							course.setStatus(2);
						} else {
							rowErrorList.add(createErrorMsg("status_name", "状态不存在"));
						}
					}
					break;
				default:
					throw new Exception("未知的业务类型:" + param_business_type);
				}

				course.setErrorList(rowErrorList);
				returnCourseList.add(course);
			}
			resultMap.put(Constants.RespMapKey.DATA, returnCourseList);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "数据校验失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 数据校验(单条数据) 暂只校验，编码唯一性
	 * 
	 * @param paramMap
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/inputDataCheckByOne" }, method = RequestMethod.POST, headers = {
			"Accept=application/json" })
	@ResponseBody
	public Map<String, Object> inputDataCheckByOne(@RequestBody Map<String, Object> paramMap,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("非法访问！");
			}

			Integer param_business_type = (Integer) paramMap.get("business_type");
			Map<String, Object> uploadCourse = (Map<String, Object>) paramMap.get("course");
			if (StringUtil.isEmpty(uploadCourse)) {
				throw new Exception("校验数据不能为空！");
			}
			String course_no = (String) uploadCourse.get("course_no");
			tempTCourseService.checkCourseNoUnique(Long.valueOf(param_business_type), course_no);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "数据校验失败: " + e.getMessage());
			// log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 导入数据
	 * 
	 * @param paramMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/inputData" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> inputData(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
			String relationIds = (String)paramMap.get("branch_ids");
			TCourse course = ModelDataUtils.getPojoMatch(TCourse.class, paramMap);

			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("非法访问！");
			}
			Account account = WebContextUtils.genUser(request);
			course.setCity_id(WebContextUtils.genSelectedOriginalOrg(request).getCityId());
			course.setStatus(TCourse.StatusEnum.VALID.getCode());
			course.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			course.setCreate_user(account.getId());
			// 班级课
			if (TCourse.BusinessTypeEnum.BJK.getCode().intValue() == course.getBusiness_type()) {
				tempTCourseService.addImportBjkCourse(course);
			} else if (TCourse.BusinessTypeEnum.WFD.getCode().intValue() == course.getBusiness_type()) {
				// 新增晚辅导课程
				course.setPerformance_belong_type(orgModel.getProductLine().equals(1L) ?2:1);
				courseService.toAdd(course, null);
			} else if (TCourse.BusinessTypeEnum.YDY.getCode().intValue() == course.getBusiness_type()) {
				// 新增一对一课程
				//一对一课程新增
				course.setProduct_line(orgModel.getProductLine());
				if(null == course.getProduct_type()){
					course.setProduct_type(1l);//设置销售类型1：正价；2：促销；3：赠送
				}
				course.setPerformance_belong_type(1);
				courseService.toAddYdy(course,relationIds );
			}

		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "课程导入失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}
		return resultMap;
	}

	/**
	 * 导出Excel
	 * 
	 * @param paramMap
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/outputExcel" }, method = RequestMethod.POST, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputExcel(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);

		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件路径
		String templateFilePath = rootPath + File.separator + "template";
		// 模板文件名
		String templateFileName = "bjk.xls";
		// 临时文件路径
		String tempFilePath = rootPath + File.separator + "temp";
		// 临时文件名
		String tempFileName = "bjk_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (orgModel == null || orgModel.getBuId() == null) {
				throw new Exception("非法访问！");
			}

			List<Map<String, Object>> courseList = (List<Map<String, Object>>) paramMap.get("courseList");
			if (StringUtil.isEmpty(courseList)) {
				throw new Exception("导出数据不能为空！");
			}

			Integer param_business_type = (Integer) paramMap.get("business_type");
			if (TCourse.BusinessTypeEnum.WFD.getCode() == param_business_type) {
				// 重新指定 晚辅导文件目录
				templateFileName = "wfd.xls";
				tempFileName = "wfd_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
			} else if (TCourse.BusinessTypeEnum.YDY.getCode() == param_business_type) {
				// 重新指定 晚辅导文件目录
				templateFileName = "ydy.xls";
				tempFileName = "ydy_" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xls";
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
	 * 下载Excel
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/downloadExcel" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public void downloadExcel(@RequestParam(value = "fileName", required = true) String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);

		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 临时文件路径
		String tempFilePath = rootPath + File.separator + "temp";
		// 下载文件
		File downloadFile = new File(tempFilePath, fileName);
		try {
			if (!downloadFile.exists()) {
				throw new Exception("下载文件已失效，请重新导出！");
			}
			// 下载文件
			downLoad(downloadFile.getAbsolutePath(), response);
		} catch (Exception e) {
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		} finally {
			// 删除临时文件
			if (downloadFile.exists()) {
				downloadFile.delete();
			}
		}
	}

}
