package com.ebusiness.hrm.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.PageParam;
import com.ebusiness.hrm.model.TabHrmChangeEvent;
import com.ebusiness.hrm.model.TabHrmEventtype;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: ChangeEventController
 * @Description:人事异动 Contrller
 * @author:liyj
 * @time:2016年12月19日 下午4:50:46
 */
@Controller
public class ChangeEventController {
	private static Logger log = Logger.getLogger(ChangeEventController.class);

	@Resource(name = "changeEventService")
	private ChangeEventService changeEventService;

	/**
	 * @Description 查询异动信息列表
	 * @param date
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = { "/changeevent/service" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryChangeEventPage(
			  HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to query changeevent");
		}
		try {
			Map<String, Object> data=new HashMap<String, Object>();
			PageParam param = new PageParam();
			String pageNum=request.getParameter("pageNum");
			String pageSize=request.getParameter("pageSize");
			String bu_id=request.getParameter("bu_id");
			String branch_id=request.getParameter("branch_id");
			String type_id=request.getParameter("type_id");
			String serach=request.getParameter("serach");
			
			if(StringUtils.isBlank(pageNum)){
				pageNum="1";
			}
			if(StringUtils.isBlank(pageSize)){
				pageSize="10";
			}
			if(StringUtils.isBlank(bu_id)){
				bu_id="";
			}
			if(StringUtils.isBlank(branch_id)){
				branch_id="";
			}
			if(StringUtils.isBlank(type_id)){
				type_id="";
			}
			if(StringUtils.isBlank(serach)){
				serach="";
			}
			param.setPageNum(Integer.valueOf(pageNum));
			param.setSize(Integer.valueOf(pageSize));
			data.put("pageParam", param);
			data.put("bu_id", bu_id);
			data.put("branch_id", branch_id);
			data.put("type_id", type_id);
			data.put("serach", serach);
			PageInfo<TabHrmChangeEvent> page = new PageInfo<TabHrmChangeEvent>();
			page = changeEventService.queryChangeevent(data, param);
			if (page.getList().size() == 0) {
				result.put("nodata", true);
			} else {
				result.put("nodata", false);
				result.put("data", page.getList());
			}
			result.put(
					"pageParam",
					new PageParam(page.getPageNum(), page.getPageSize(), page
							.getSize(), page.getTotal(), page.getPages()));
		} catch (Exception e) {
			log.error("查询异动列表异常：", e);
			result.put("error", "查询异动列表异常！");
		}

		if (log.isDebugEnabled()) {
			log.debug("End to query changeevent");
		}
		return result;
	}

	/**
	 * @Description 关注与取消关注
	 * @param date
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/changeeventfollow/service" }, method = RequestMethod.POST)
	public @ResponseBody HttpReponse updateFollow(
			@RequestBody Map<String, Object> data, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to update Follow");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			if (changeEventService.updateFollow(data)) {
				httpReponse.setError(false);
				httpReponse.setMessage("更改成功！");
			} else {
				httpReponse.setError(false);
				httpReponse.setMessage("更改失败！");
			}
		} catch (Exception e) {
			log.error("更改异常：", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("End to update Follow");
		}
		return httpReponse;
	}

	/**
	 * @Description 员工详情查询
	 * @param data
	 *            通过异动ID 来查询所有信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/changeevent/service/detail" }, method = RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeInfo(
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query EmployeeInfo");
		}
		HttpReponse httpReponse = new HttpReponse();
		try {
			String event_id = request.getParameter("event_id");
			if (StringUtils.isBlank(event_id)) {
				httpReponse.setError(true);
				httpReponse.setMessage("详情id未传递");
				return httpReponse;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("event_id", event_id);
			Map<String, Object> result = changeEventService
					.queryChangeeventInfo(param);
			if (result.size() == 0) {
				httpReponse.setError(true);
				httpReponse.setMessage("没有数据");
				return httpReponse;
			}
			httpReponse.setError(false);
			httpReponse.setMessage("查询结束");
			httpReponse.setData(result);
		} catch (Exception e) {
			log.error("异常信息", e);
			httpReponse.setError(true);
			httpReponse.setMessage("点击详情出现异常" + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("End to query EmployeeInfo");
		}
		return httpReponse;
	}

	/**
	 * @Description 异动类型
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/changeevent_type/service" }, method = RequestMethod.GET)
	public @ResponseBody HttpReponse queryChangeeventtype(
			HttpServletRequest request, HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to queryChangeeventType ");
		}
		try {
			List<TabHrmEventtype> tabHrmEventType = changeEventService
					.queryEventtype();
			httpReponse.setError(false);
			httpReponse.setData(tabHrmEventType);
		} catch (Exception e) {
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("End to queryChangeeventType");
		}
		return httpReponse;
	}

}
