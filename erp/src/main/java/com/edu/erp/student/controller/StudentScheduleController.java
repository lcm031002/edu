package com.edu.erp.student.controller;

import com.edu.common.constants.Constants;
import com.edu.erp.model.TAttendance;
import com.edu.erp.student.service.StudentScheduleService;
import com.edu.erp.util.BaseController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/studentservice" })
public class StudentScheduleController extends BaseController {
	
	private static final Logger log = Logger.getLogger(StudentScheduleController.class);
	
	@Resource(name = "studentScheduleService")
	private StudentScheduleService studentScheduleService;

	/**
	 * 查询个性化的排课信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/scheduleYDY" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryYDYSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
//			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
//			if (orgModel == null || !"4".equals(orgModel.getType())) {
//				log.error("未选择校区!");
//				throw new Exception("请选择校区！");
//			}
			Map<String, Object> queryParam = super.initParamMap(request, false);
			//queryParam.put("branch_id", orgModel.getId());
			List<TAttendance> scheduleList = studentScheduleService.queryYDYSchedule(queryParam);
			resultMap.put(Constants.RespMapKey.DATA, scheduleList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
	
	/**
	 * 查询班级课的排课信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/scheduleBJK" }, method = RequestMethod.GET, headers = { "Accept=application/json" })
	public Map<String, Object> queryBJKSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.RespMapKey.ERROR, false);
		try {
//			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
//			if (orgModel == null || !"4".equals(orgModel.getType())) {
//				log.error("未选择校区!");
//				throw new Exception("请选择校区！");
//			}
			Map<String, Object> queryParam = super.initParamMap(request, false);
//			queryParam.put("branch_id", orgModel.getId());
			//queryParam.put("branch_id", 32);  //test
			List<TAttendance> scheduleList = studentScheduleService.queryBJKSchedule(queryParam);
			resultMap.put(Constants.RespMapKey.DATA, scheduleList);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put(Constants.RespMapKey.ERROR, true);
			resultMap.put(Constants.RespMapKey.MESSAGE, "查询失败: "+ e.getMessage());
		}

		return resultMap;
	}
}
