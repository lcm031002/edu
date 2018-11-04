package com.edu.erp.attendance.controller;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import com.edu.common.util.DateUtil;
import com.edu.erp.attendance.service.AttendanceGroupService;
import com.edu.erp.log_operate.LogOperateUtil;
import com.edu.erp.model.AttendTeacherGroup;
import com.edu.erp.model.BaseObject;
import com.edu.erp.util.BaseController;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = { "/attendancegroup" })
public class AttendanceGroupController extends BaseController {

	private static final Logger log = Logger.getLogger(AttendanceGroupController.class);

	@Resource(name = "attendanceGroupService")
	private AttendanceGroupService attendTeacherGroupService;

	/**
	 * 分页表格
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/service", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> page(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryParam = super.initParamMap(request, true);
			Page<AttendTeacherGroup> page = attendTeacherGroupService.queryAttendanceGroupForPage(queryParam);
			setRespDataForPage(request, page, resultMap);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 根据id查询分组详情
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> detail(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Map<String, Object> queryParam = super.initParamMap(request, false);
			AttendTeacherGroup detail = attendTeacherGroupService.listAttendanceGroup(queryParam).get(0);
			resultMap.put("data", detail);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", "查询失败: " + e.getMessage());
		}
		return resultMap;
	} 

	/**
	 * 新增
	 * 
	 * @param pojo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/service", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> toAdd(@RequestBody AttendTeacherGroup pojo, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			OrgModel orgModel = WebContextUtils.genSelectedOriginalOrg(request);
			if (null == orgModel || orgModel.getBuId() == null) {
				throw new Exception("请选择校区或团队！");
			}
			pojo.setCity_id(orgModel.getCityId());
			pojo.setBu_id(orgModel.getBuId());
			// 默认状态
			pojo.setStatus(BaseObject.StatusEnum.VALID.getCode());
			Account account = WebContextUtils.genUser(request);
			pojo.setCreate_user(account.getId());
			pojo.setCreate_time(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			attendTeacherGroupService.toAdd(pojo);
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(pojo.toString());
			LogOperateUtil.getInstance().LogOperate("新增考勤教师组", detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "新增失败: " + e.getMessage());
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(pojo.toString());
			detailInfoStrs.append("，");
			detailInfoStrs.append("错误信息：");
			detailInfoStrs.append(e);
			LogOperateUtil.getInstance().LogOperate("新增考勤教师组", detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}

	
	/**
	 * 修改
	 * 
	 * @param pojo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/service", method = RequestMethod.PUT, headers = "Accept=application/json")
	public Map<String, Object> toUpdate(@RequestBody AttendTeacherGroup pojo,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			Account account = WebContextUtils.genUser(request);
			pojo.setUpdate_user(account.getId());
			attendTeacherGroupService.toUpdate(pojo);
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(pojo.toString());
			LogOperateUtil.getInstance().LogOperate("修改考勤教师组",
					detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			resultMap.put("error", true);
			resultMap.put("message", "修改失败: " + e.getMessage());
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(pojo.toString());
			detailInfoStrs.append("，");
			detailInfoStrs.append("错误信息：");
			detailInfoStrs.append(e);
			LogOperateUtil.getInstance().LogOperate("修改考勤教师组",
					detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}
	/**
	 * 删除
	 * 
	 * @param course
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/service", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public Map<String, Object> toRemove(@RequestParam("ids") String ids,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			attendTeacherGroupService.toChangeStatus(ids,
					AttendTeacherGroup.StatusEnum.DELETE.getCode());
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(ids);
			LogOperateUtil.getInstance().LogOperate("删除考勤教师组",
					detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "成功");
		} catch (Exception e) {
			System.err.println(e);
			resultMap.put("error", true);
			resultMap.put("message", "删除失败: " + e.getMessage());
			StringBuilder detailInfoStrs = new StringBuilder();
			detailInfoStrs.append("访问参数：");
			detailInfoStrs.append(ids);
			detailInfoStrs.append("，");
			detailInfoStrs.append("错误信息：");
			detailInfoStrs.append(e);
			LogOperateUtil.getInstance().LogOperate("删除考勤教师组",
					detailInfoStrs.toString(),
					LogOperateUtil.getInstance().genUserInfo(request), "失败");
		}
		return resultMap;
	}
}
