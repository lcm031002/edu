package com.ebusiness.hrm.employee;

import java.util.HashMap;
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
import com.ebusiness.hrm.model.TabHrmChangeEventHt;
import com.github.pagehelper.PageInfo;
import com.squareup.okhttp.Request;

/**
 * 
 *@ClassName: ChangeEventHtController 
 * @Description:TODO 人事异动列表历史
 * @author:liyj
 * @time:2016年12月29日 上午9:57:16
 */
@Controller
public class ChangeEventHtController {

	private static Logger log =Logger.getLogger(ChangeEventHtController.class);
	
	@Resource(name = "changeEventHtService")
	private ChangeEventHtService ChangeEventHtService;
	
	/**
	 * @Description 异动历史信息查询
	 * @param Long
	 *            taskid 通过任务id 来查询异动历史信息
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/changeevent_ht/service"},method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryChangeEventHtPage(
		 HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result=new HashMap<String, Object>();
		if (log.isDebugEnabled()) {
			log.debug("begin to query changeeventHt");
		}
		try {
			PageParam param = new PageParam();		
			Map<String, Object> data=new HashMap<String, Object>();
			String pageNum=request.getParameter("pageNum");
			String pageSize=request.getParameter("pageSize");
			String bu_id=request.getParameter("bu_id");
			String branch_id=request.getParameter("branch_id");
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
			if(StringUtils.isBlank(serach)){
				serach="";
			}
			param.setPageNum(Integer.valueOf(pageNum));
			param.setSize(Integer.valueOf(pageSize));
			data.put("pageParam", param);
			data.put("bu_id", bu_id);
			data.put("branch_id", branch_id);
			data.put("serach", serach);
			PageInfo<TabHrmChangeEventHt> page = new PageInfo<TabHrmChangeEventHt>();
			page = ChangeEventHtService.queryChangeeventHt(data, param);
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
			log.error("查询异动历史列表异常：", e);
			result.put("error", "查询异动历史列表异常！");
		}
		if (log.isDebugEnabled()) {
			log.debug("begin to query changeeventHt");
		}
		return result;
	}
	/**
	 * @Description 人事异动列表历史表详情 查询
	 * @param request 传递异动id event_id
	 * @param response
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value={"/changeht/service/detail"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse queryEventHt(
			@RequestBody Map<String,Object> param,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
			if (log.isDebugEnabled()) {
				log.debug("begin to query EventHt");
			}
			HttpReponse httpReponse =new HttpReponse();
			Map<String,Object> result=new HashMap<String, Object>(); 
			try {
				if(param.get("step")==null&&param.get("event_id")==null){
					httpReponse.setError(true);
					httpReponse.setMessage("未传递参数");
					return httpReponse;
				}
				result=ChangeEventHtService.queryeventht(param);
				httpReponse.setData(result);
				httpReponse.setError(false);
			} catch (Exception e) {
				httpReponse.setError(true);
				httpReponse.setMessage("查询异动历史详情异常："+e);
			}
			if (log.isDebugEnabled()) {
				log.debug("End to query EventHt");
			}
		return httpReponse;
	}
	
}
