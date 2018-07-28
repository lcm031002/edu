package com.edu.erp.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.model.TCourseListening;
import com.edu.erp.student.service.TCourseListeningService;
import com.edu.erp.util.BaseController;
import com.edu.common.util.ERPConstants;
import com.edu.erp.util.ReportWriteExcelHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: TCourseListeningController
 * @Description:学员试听服务
 *
 */
@Controller
@RequestMapping(value = { "/courselistening" })
public class TCourseListeningController extends BaseController {
	private static final Logger log = Logger
			.getLogger(TCourseListeningController.class);

	@Resource(name = "tCourseListeningService")
	private TCourseListeningService tCourseListeningService;

	@ResponseBody
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> page(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("student_id",
					genLongParameter("student_id", request));
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || !"4".equals(orgModel.getType())) {
				resultMap.put("error", true);
				resultMap.put("message", "请选择校区！");
				return resultMap;
			}

			queryParam.put("bu_id", orgModel.getBuId());
			//queryParam.put("branch_id", orgModel.getId());
			queryParam.put("business_type",
					genLongParameter("business_type", request));
			queryParam.put("start_date", request.getParameter("start_date"));
			queryParam.put("end_date", request.getParameter("end_date"));
			// 是否分页
			Integer isPage = genIntParameter("isPage", request);  //1:分页；0：不分页
			// 当前页数
			Integer currentPage = genIntParameter("currentPage", request);
			// 每页显示记录数
			Integer pageSize = genIntParameter("pageSize", request);

			if (currentPage == null) {
				currentPage = 1;
			}

			if (pageSize == null) {
				pageSize = 20;
			}

			// 获取第1页，10条内容，默认查询总数count
			if(isPage==null||isPage==1)
			PageHelper.startPage(currentPage, pageSize);

			Page<Map<String,Object>> page = tCourseListeningService
					.queryListeningPage(queryParam);

			PageInfo<Map<String,Object>> pageData = new PageInfo<Map<String,Object>>(
					page);
			resultMap.put("error", false);
			resultMap.put("data", pageData.getList());
			resultMap.put("total", pageData.getTotal());
			resultMap.put("totalPage", pageData.getPages());
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSize", pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createListeningAttends(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		String course_id = null != params.get("course_id") ? params
				.get("course_id") + "" : null;
		String student_id = null != params.get("student_id") ? params
				.get("student_id") + "" : null;
		String course_times = null != params.get("course_times") ? params
				.get("course_times") + "" : null;
		String course_date = null != params.get("course_date") ? params
				.get("course_date") + "" : null;

		try {
			Account account = WebContextUtils.genUser(request);
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if(!"4".equals(orgModel.getType())){
				result.put("error", true);
				result.put("message", "请选择校区！");
				return result;
			}
			
			if (StringUtils.isNotBlank(course_id)
					&& StringUtils.isNotBlank(student_id)
					&& StringUtils.isNotBlank(course_times)) {
				param.put("BRANCH_ID", orgModel.getId());
				param.put("COURSE_ID", Long.parseLong(course_id));
				param.put("STUDENT_ID", Long.parseLong(student_id));
				param.put("COURSE_TIMES", Long.parseLong(course_times));
				if (StringUtils.isBlank(course_date)) {
					param.put("LISTENING_DATE",
							DateUtil.getCurrDate("yyyy-MM-dd"));
				} else {
					param.put("LISTENING_DATE", DateUtil.dateToString(
							DateUtil.stringToDate(course_date, "yyyyMMdd"),"yyyy-MM-dd"));
				}

				param.put("PAY_STATUS", null);
				param.put("RECORDER", account.getId());
				param.put("RECORD_TIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
				param.put("UPDATER", account.getId());
				param.put("UPDATE_TIME",
						DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));

				tCourseListeningService.insertTCourseListening(param);
				
				result.put("error", false);
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("试听学生ID：");
				detailInfoStr.append(student_id);
				detailInfoStr.append("，");
				detailInfoStr.append("试听课程ID：");
				detailInfoStr.append(course_id);
				detailInfoStr.append("，");
				detailInfoStr.append("试听课次：");
				detailInfoStr.append(course_times);
				LogOperateUtil.getInstance()
						.LogOperate(
								"试听考勤",
								detailInfoStr.toString(),
								LogOperateUtil.getInstance().genUserInfo(
										request), "成功");
			} else {
				StringBuilder detailInfoStr = new StringBuilder();
				detailInfoStr.append("试听学生ID：");
				detailInfoStr.append(student_id);
				detailInfoStr.append("，");
				detailInfoStr.append("试听课程ID：");
				detailInfoStr.append(course_id);
				detailInfoStr.append("，");
				detailInfoStr.append("试听课次：");
				detailInfoStr.append(course_times);
				LogOperateUtil.getInstance()
						.LogOperate(
								"试听考勤",
								detailInfoStr.toString(),
								LogOperateUtil.getInstance().genUserInfo(
										request), "失败");
				throw new Exception("Error found!see:course_id is " + course_id
						+ ",student_id is " + student_id + ",course_times is "
						+ course_times);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", e.getMessage());
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("试听学生ID：");
			detailInfoStr.append(student_id);
			detailInfoStr.append("，");
			detailInfoStr.append("试听课程ID：");
			detailInfoStr.append(course_id);
			detailInfoStr.append("，");
			detailInfoStr.append("试听课次：");
			detailInfoStr.append(course_times);
			detailInfoStr.append("，");
			detailInfoStr.append("错误信息：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("试听考勤",
					detailInfoStr.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}

		return result;
	}
	
	
	/**
	 * 更新试听
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = { "/service" }, headers = { "Accept=application/json" }, method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, Object> updateListeningAttends(
			@RequestBody Map<String, Object> paramMap,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Account account = WebContextUtils.genUser(request);
			paramMap.put("UPDATER", account.getId());
			paramMap.put("UPDATE_TIME",
					DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_1));
			paramMap.put("REMARK", paramMap.get("remark"));
			paramMap.put("PAY_STATUS", paramMap.get("pay_status"));
			paramMap.put("ID", paramMap.get("id"));
			paramMap.put("COURSE_ID", paramMap.get("course_id"));
			paramMap.put("STUDENT_ID", paramMap.get("student_id"));
			
			tCourseListeningService.updateTCourseListening(paramMap);
			result.put("error", false);
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("访问参数：");

			
			detailInfoStr.append(paramMap);
			LogOperateUtil.getInstance().LogOperate("试听拒缴",
					detailInfoStr.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", e.getMessage());
			StringBuilder detailInfoStr = new StringBuilder();
			detailInfoStr.append("访问参数：");
			detailInfoStr.append(paramMap);
			detailInfoStr.append("，");
			detailInfoStr.append("失败原因：");
			detailInfoStr.append(e);
			LogOperateUtil.getInstance().LogOperate("试听拒缴",
					detailInfoStr.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/all" }, headers = { "Accept=application/json" }, method = RequestMethod.GET)
	public Map<String, Object> queryList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TCourseListening> dataList = tCourseListeningService.queryListeningList(queryParam);

			resultMap.put("data", dataList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(value = { "/all_output" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	@ResponseBody
	public Map<String, Object> outputList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		String rootPath = request.getSession().getServletContext().getRealPath("//");
		// 模板文件名
		String templateFileName = "course_listening.xlsx";
		// 临时文件名
		String tempFileName = "试听详情" + DateUtil.getCurrDate(ERPConstants.DATE_FORMAT_2) + ".xlsx";
		try {
			Map<String, Object> queryParam = WebUtils.getParametersStartingWith(request, "p_");
			List<TCourseListening> queryResult = tCourseListeningService.queryListeningList(queryParam);
			
			if(CollectionUtils.isEmpty(queryResult)) {
				throw new Exception("没有数据可导出！");
			}

			// 导出文件目录
			String outFilePath = ReportWriteExcelHandler.writeDataToExcel(queryResult, rootPath, 
					templateFileName, tempFileName); 

			log.debug("生成导出临时文件：" + outFilePath);

			resultMap.put("data", tempFileName);
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "导出失败: " + e.getMessage());
			log.error("error found ,see:", e);
		}

		return resultMap;
	}
	
}
