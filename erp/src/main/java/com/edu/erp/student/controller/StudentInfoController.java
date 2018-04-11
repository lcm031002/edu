/**  
 * @Title: StudentInfoController.java
 * @Package com.ebusiness.erp.student.controller
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:44:27
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.controller;

import com.edu.common.constants.Constants.RespMapKey;
import com.edu.erp.model.*;

import com.edu.erp.student.business.StudentAccount;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.edu.cas.client.common.util.PwdEncryptUtil;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.common.util.NumberUtils;
import com.edu.common.constants.Constants;
import com.edu.erp.student.service.StudentInfoService;
import com.edu.erp.util.GeneratorWechatPictrue;
import com.edu.erp.util.ImageUploadController;
import com.edu.common.util.PropertiesTools;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: StudentInfoController
 * @Description: 学生信息控制类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:44:27
 *
 */
@Controller
@RequestMapping(value = { "/studentservice" })
public class StudentInfoController extends ImageUploadController {
	private static final Logger log = Logger
			.getLogger(StudentInfoController.class);

	@Resource(name = "studentInfoService")
	private StudentInfoService studentInfoService;

	private static final String PATTERNS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 学生信息List
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/students" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> students(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getCityId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			String searchInfo = request.getParameter("searchInfo");

			Long cityId = orgModel.getCityId();
			//如果界面有传递学生的团队信息，则说明本次查询操作来自于账户单据中的转账查询，而转户单据中的转账单据查询仅需根据student_id查询学生信息，而不需要bu_id
			Long buId = StringUtils.isEmpty(request.getParameter("buId"))?orgModel.getBuId():null;
			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);
			Long row_num = genLongParameter("row_num", request);
			Long searchType = genLongParameter("searchType", request);
			Long studentId = genLongParameter("studentId", request);
			Long temporaryOrderId = genLongParameter("temporaryOrderId", request);
			if (null == row_num) {
				row_num = 20L;
			}

			if (currentPage == null) {
				currentPage = 1;
			}
			if (pageSize == null || StringUtils.isEmpty(searchInfo)) {
				pageSize = 20;
			}

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("searchInfo", searchInfo);
			queryParam.put("city_id", cityId);
			queryParam.put("buId", buId);
			queryParam.put("row_num", row_num);
			queryParam.put("searchType", searchType);
			queryParam.put("studentId", studentId);
			queryParam.put("temporaryOrderId", temporaryOrderId);

			// 获取第1页，20条内容，默认查询总数count
			PageHelper.startPage(currentPage, pageSize);

			Page<StudentInfo> page = studentInfoService
					.queryStudents(queryParam);

			PageInfo<StudentInfo> pageData = new PageInfo<StudentInfo>(page);
			if(page != null && pageData.getList() != null && pageData.getList().size() == 1) {
				queryParam.put("studentId", pageData.getList().get(0).getId());
				StudentInfo oneStudentInfo = studentInfoService.queryStudentById(queryParam);
				//设置积分
				pageData.getList().get(0).setIntegral(oneStudentInfo.getIntegral());
			}

			result.put("error", false);
			result.put("data", pageData.getList());
			if (StringUtils.isEmpty(searchInfo)) {
				result.put("total", 20);
				result.put("totalPage", 1);
			} else {
				result.put("total", pageData.getTotal());
				result.put("totalPage", pageData.getPages());
			}

			result.put("currentPage", currentPage);
			result.put("pageSize", pageSize);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}



	@ResponseBody
	@RequestMapping(value = "/students", headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updateStudent(@RequestBody StudentInfo student,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			if (null != student) {
				Account account = WebContextUtils.genUser(request);
				OrgModel orgModel = WebContextUtils
						.genSelectedOriginalOrg(request);
				student.setUpdate_user(account.getId());
				student.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
				student.setBu_id(orgModel.getBuId());
				studentInfoService.updateStudentInfo(student);
				if (null == orgModel || null == orgModel.getCityId()) {
					resp.put("error", true);
					resp.put("message", "请选择查询校区或团队！");
					return resp;
				}
				Long cityId = orgModel.getCityId();
				Long row_num = genLongParameter("row_num", request);
				Long searchType = genLongParameter("searchType", request);
				if (null == row_num) {
					row_num = 20L;
				}

				Map<String, Object> queryParam = new HashMap<String, Object>();
				queryParam.put("city_id", cityId);
				queryParam.put("bu_id", orgModel.getBuId());
				queryParam.put("row_num", row_num);
				queryParam.put("searchType", searchType);
				queryParam.put("studentId", student.getId());

				List<StudentInfo> studentList = studentInfoService
						.queryStudents(queryParam);
				if (!CollectionUtils.isEmpty(studentList)) {
					resp.put("error", false);
					resp.put("data", studentList.get(0));
				} else {
					resp.put("error", true);
					resp.put("message", "学员信息已标记为重复，不再可见，点击确认切换到学员搜索界面");
				}

				resp.put("data", student);
			} else {
				resp.put("error", true);
				resp.put("message", "修改失败，学员信息不能为空!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.put("error", true);
			resp.put("message", "修改失败，see:" + e.getMessage());
		}
		return resp;
	}

	/**
	 * 更新学员的默认联系方式和电话号码校验状态
	 */
	@ResponseBody
	@RequestMapping(value = "/studentsDefaultContact", headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public Map<String, Object> updateStudentsDefaultContact(@RequestBody StudentInfo student,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
				OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
				if (null == orgModel || null == orgModel.getCityId()) {
					resp.put("error", true);
					resp.put("message", "请选择查询校区或团队！");
					return resp;
				}
				Account account = WebContextUtils.genUser(request);
				student.setUpdate_user(account.getId());
				student.setUpdate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd"));
				studentInfoService.updateStudentContact(student);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("error", true);
			resp.put("message", "修改失败，see:" + e.getMessage());
		}
		return resp;
	}

	/**
	 * 二维码生成/下载
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/student/weChat" })
	public void qrGenOrDown(@RequestParam String encoding, String isDown,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取数据
		OutputStream os = null;
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			StudentInfo sb = new StudentInfo();
			sb.setEncoding(new String(PwdEncryptUtil.decodeBase64(encoding)));

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("encoding", encoding);

			queryParam.put("city_id", orgModel.getCityId());
			queryParam.put("bu_id", orgModel.getBuId());
			queryParam.put("row_num", 20);
			queryParam.put("searchType", 1);

			List<StudentInfo> studentList = studentInfoService
					.queryStudents(queryParam);
			if (!CollectionUtils.isEmpty(studentList)) {
				sb = studentList.get(0);
			} else {
				log.error("student not found!");
			}
			StringBuffer file_name = new StringBuffer();
			file_name.append(sb.getBranch_name());
			file_name.append("_");
			file_name.append(sb.getStudent_name());
			file_name.append(sb.getEncoding());
			if ("".equals(file_name.toString())) {
				file_name.append("学生二维码");
			}

			response.reset();
			os = response.getOutputStream();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("image/png");
			if (!StringUtils.isEmpty(isDown)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ new String(file_name.toString().getBytes(
										"GBK"), "ISO-8859-1") + ".png");
				response.setContentType("application/octet-stream");// 八进制流
																	// 与文件类型无关
			}
			// 二维码值为公众号引导入口
			String content = PropertiesTools.getPropertiesByKey("wx_domain")
					+ "/" + encoding;
			GeneratorWechatPictrue.createQRCode(content, os, "logo.jpg");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @title 验证学生信息
	 * @description 学生号码 学生姓名 学生联系人号码
	 *
	 * @param studentInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/students/check", headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public Map<String, Object> checkStudent(@RequestBody StudentInfo studentInfo,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			if (StringUtils.isEmpty(studentInfo.getStudent_name())) {
				throw new IllegalArgumentException("学生姓名必填！");
			}

			StringBuilder phoneBuilder = new StringBuilder();
			if(CollectionUtils.isEmpty(studentInfo.getStudentContactList())) {
				throw new IllegalArgumentException("请至少添加一条联系人信息！");
			} else {
				for (StudentContact stuContact : studentInfo.getStudentContactList()) {
					if (StringUtils.isEmpty(stuContact.getLink_name())) {
						stuContact.setLink_name(stuContact.getRelation_name());
					}
					if (StringUtils.isEmpty(stuContact.getLink_phone())) {
						throw new IllegalArgumentException("联系人电话必填");
					}
					phoneBuilder.append(",'").append(stuContact.getLink_phone()).append("'");
				}
			}

			List<StudentInfo> studentInfoList = studentInfoService.queryStudentByNameAndPhone(studentInfo.getStudent_name(), phoneBuilder.substring(1), studentInfo.getId());
			if (!CollectionUtils.isEmpty(studentInfoList)) {
				resultMap.put("error", true);
				resultMap.put("message", "学生已存在！");
				resultMap.put("data", studentInfoList.get(0));
				return resultMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 学生首页的指标查询
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/student/indexAccount" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentIndexAccount(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("buId", buId);

			Map<String, Object> resultInfo = studentInfoService
					.queryStudentIndexAccount(queryParam);
			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}

	/**
	 *
	 * @Title: studentIndexCounselors
	 * @Description: 学员首页咨询学管查询
	 * @param request
	 * @param response
	 * @return 设定文件 Map<String,Object> 返回类型
	 *
	 */
	@ResponseBody
	@RequestMapping(value = { "/student/indexCounselors" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentIndexCounselors(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("buId", buId);
			queryParam.put("branchId", orgModel.getId());

			Map<String, Object> resultInfo = studentInfoService
					.queryStudentCurrCounselors(queryParam);
			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}

	/**
	 *
	 * @Title: studentOrders
	 * @Description: 学员订单查询
	 * @param request
	 * @param response
	 * @return 设定文件 Map<String,Object> 返回类型
	 *
	 */
	@ResponseBody
	@RequestMapping(value = { "/student/orders" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentOrders(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("buId", buId);
			String month = request.getParameter("month");
			Date[] dates = genMonthTime(genMonth(month));
			queryParam.put("beginDate", dates[0]);
			queryParam.put("endDate", dates[1]);

			List<Map<String, Object>> resultInfo = studentInfoService
					.queryStudentOrders(queryParam);
			List<Map<String, Object>> resultDetailInfo = studentInfoService
					.queryStudentOrdersDetail(queryParam);
			fillDetail(resultInfo, resultDetailInfo);

			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/student/orders/bjk" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentOrdersBJK(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("seasonId", genLongParameter("season", request));
			queryParam.put("subjectId", genLongParameter("subject", request));
			queryParam.put("buId", buId);

			List<Map<String, Object>> resultInfo = studentInfoService
					.queryStudentOrdersBJK(queryParam);

			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/student/orders/wfd" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentOrdersWFD(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("buId", buId);
			String month = request.getParameter("month");
			Date[] dates = genMonthTime(genMonth(month));
			queryParam.put("beginDate", dates[0]);
			queryParam.put("endDate", dates[1]);

			List<Map<String, Object>> resultInfo = studentInfoService
					.queryStudentOrdersWFD(queryParam);

			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/student/course" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentCourse(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = initParamMap(request, false);
			queryParam.put("buId", queryParam.get("bu_id"));
			List<Map<String, Object>> resultInfo = studentInfoService
					.queryStudentCourse(queryParam);

			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = { "/student/courseTimes" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> studentCourseTimes(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				result.put("error", true);
				result.put("message", "请选择查询校区或团队！");
				return result;
			}
			Long studentId = genLongParameter("studentId", request);
			Long buId = orgModel.getBuId();
			Long courseId = genLongParameter("courseId", request);

			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("studentId", studentId);
			queryParam.put("buId", buId);
			queryParam.put("courseId", courseId);

			List<Map<String, Object>> resultInfo = studentInfoService
					.queryStudentCourseTimes(queryParam);

			result.put("error", false);
			result.put("data", resultInfo);

		} catch (Exception e) {
			log.error("error found ,see:", e);
			result.put("error", true);
			result.put("message", "查询失败，请联系管理员！" + e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private void fillDetail(List<Map<String, Object>> resultInfo,
			List<Map<String, Object>> resultDetailInfo) {
		Long course_surplus_count = 0l;  //总的剩余课时
		Long course_surplus_count_detail = 0l;  //单的课程的剩余课时
		if (!CollectionUtils.isEmpty(resultDetailInfo)) {
			for (Map<String, Object> resultDetail : resultDetailInfo) {
				String orderId = resultDetail.get("ORDER_ID") + "";
				for (Map<String, Object> order : resultInfo) {
					String id = order.get("ID") + "";
					if (!StringUtils.isBlank(id) && orderId.equals(id)) {
						List<Map<String, Object>> details = (List<Map<String, Object>>) order
								.get("details");
						if (details == null) {
							details = new ArrayList<Map<String, Object>>();
							order.put("details", details);
						}
						details.add(resultDetail);

						if(order.get("COURSE_SURPLUS_COUNT") == null) {
							order.put("COURSE_SURPLUS_COUNT", 0l);
						}
						course_surplus_count = NumberUtils.object2Long(order.get("COURSE_SURPLUS_COUNT"));

						if(resultDetail.get("COURSE_SURPLUS_COUNT_ING") == null) {
							resultDetail.put("COURSE_SURPLUS_COUNT_ING", 0l);
						}
						course_surplus_count_detail = NumberUtils.object2Long(resultDetail.get("COURSE_SURPLUS_COUNT_ING"));

						course_surplus_count += course_surplus_count_detail;
						order.put("COURSE_SURPLUS_COUNT", course_surplus_count);
					}
				}
			}
		}
	}

	/**
	 * 学员主页-学员考勤-晚辅导 订单信息列表
	 * @param request
	 * @param response
	 * @return 订单信息列表
	 */
	@ResponseBody
    @RequestMapping(value = { "/student/course/wfd" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
    public Map<String, Object> studentCourseWfd(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paramMap = initParamMap(request, false);

            List<Map<String, Object>> wfdOrderList = studentInfoService.queryStudentCourseWfd(paramMap);
            resultMap.put(Constants.RespMapKey.ERROR, false);
            resultMap.put(Constants.RespMapKey.DATA, wfdOrderList);
        } catch (Exception e) {
            log.error("error found ,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", "查询失败，请联系管理员！" + e.getMessage());
        }
        return resultMap;
    }

	private int genMonth(String month) {
		try {
			return Integer.parseInt(month);
		} catch (Exception e) {
			return 0;
		}
	}

	private Date[] genMonthTime(Integer monthCount) {
		Date[] dates = new Date[2];
		if (monthCount <= 0) {
			Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
			Date today = calendar.getTime();
			// 过去10年
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR) - 10;
			String beginDate = year + "-" + (month) + "-01" + " " + "00:00:00";
			Date beginD = DateUtil.stringToDate(beginDate, PATTERNS);
			dates[0] = beginD;
			dates[1] = today;
		} else if (monthCount <= 1 && monthCount > 0) {
			Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
			Date today = calendar.getTime();
			// 最近一个月
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			String beginDate = year + "-" + (month) + "-01" + " " + "00:00:00";
			Date beginD = DateUtil.stringToDate(beginDate, PATTERNS);
			dates[0] = beginD;
			dates[1] = today;
		} else {
			Date[] lastTime = genMonthTime(monthCount - 1);
			Date beginDateTime = lastTime[0];
			long endDate2Times = beginDateTime.getTime() - 1000;
			Calendar calendar2 = Calendar.getInstance(Locale.ENGLISH);
			calendar2.setTimeInMillis(endDate2Times);
			Date end2Date = calendar2.getTime();
			int month = calendar2.get(Calendar.MONTH) + 1;
			int year = calendar2.get(Calendar.YEAR);
			String begin2Date = year + "-" + (month) + "-01" + " " + "00:00:00";
			dates[0] = DateUtil.stringToDate(begin2Date, PATTERNS);
			dates[1] = end2Date;
		}
		return dates;
	}

	/**
	 * 新增学生信息
	 *
	 * @param studentInfo
	 * @return
	 */
	@RequestMapping(value = { "/students" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toAdd(@RequestBody StudentInfo studentInfo,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}

			resultMap = checkStudent(studentInfo, response);
			if ("true".equals(resultMap.get("error").toString())) {
				return resultMap;
			}

			String head_pic = studentInfo.getHead_pic();
			if(StringUtils.isNotEmpty(head_pic)) {
				String imagePath = uploadBase64Image(head_pic);
				studentInfo.setHead_pic(imagePath);
			}

			studentInfoService.toAdd(studentInfo,request);

			resultMap.put("data", studentInfo);
		} catch (Exception e) {
			log.error("error found ,see:", e);
            resultMap.put("error", true);
            resultMap.put("message", "新增失败！" + e.getMessage());
		}
		return resultMap;
	}

	/**
	 *
	 * 设置学生图像
	 *
	 * @param json
	 * @return
	 */
	@RequestMapping(value = { "/student/modifyPhoto" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyImg(@RequestBody Map<String, String> json,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			String modifyPhoto = studentInfoService.modifyPhoto(json);
			resultMap.put("url", modifyPhoto);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 *
	 * 查询学员的历史推荐人
	 *
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = { "/student/referenceht" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryReferenceStudentHt(Long studentId,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			List<ReferenceStudent> list = studentInfoService.queryReferenceStudentHt(studentId);
			resultMap.put("data", list);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 *
	 * 通过学员姓名和联系方式查询学员信息
	 *
	 * @param studentName
	 * @return
	 */
	@RequestMapping(value = { "/student/byNameAndPhones" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryStudentByNameAndPhone(String studentName,String phones,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || null == orgModel.getBuId()) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择查询校区或团队！");
				return resultMap;
			}
			List<StudentInfo> list = null;
			if(!StringUtils.isEmpty(phones)) {
				list = studentInfoService.queryStudentByNameAndPhone(studentName, phones, null);
			}
			resultMap.put("data", list);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 跨团队学员账户互转，查询学员账户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/student/transAccount" }, method = RequestMethod.GET)
	public Map<String, Object> queryStudentAccounts(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(RespMapKey.ERROR, false);
		try {
			Map<String, Object> paramMap = this.initParamMap(request, false, StringUtils.EMPTY);
			Integer transType = genIntParameter("transType", request);
			Long transOutStudentId = genLongParameter("transOutStudentId", request);

			if (transType == null && transOutStudentId == null) {
				throw new IllegalArgumentException("没有转出学员ID，无法查询转出学员！");
			}
			Long transOutBuId = genLongParameter("transOutBuId", request);
			String stuSearchInfo = request.getParameter("stuSearchInfo");
			if (transType != null && (transOutStudentId == null || transOutBuId == null)) {
				throw new IllegalArgumentException("没有转出学员ID及团队ID，无法查询转入学员！");
			}

			boolean isActive = this.studentInfoService.isStudentActive(transOutStudentId);
			if (transType != null && transType == 1 && !isActive) {
				throw new Exception("转出学员为非活跃学员，只能转到其他活跃学员账户！");
			}

			if (transType != null && transType == 2 && StringUtils.isEmpty(stuSearchInfo)) {
				throw new IllegalArgumentException("请输入转入学员查询条件！");
			}
			List<StudentAccount> studentAccountList = this.studentInfoService.queryStudentAccounts(paramMap);
			resultMap.put(RespMapKey.DATA, studentAccountList);
		} catch (Exception e) {
			resultMap.put(RespMapKey.ERROR, true);
			resultMap.put(RespMapKey.MESSAGE, e.getMessage());
		}
		return resultMap;
	}

}
