package com.ebusiness.hrm.employee;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.HttpReponse;

/**
 * 
 * @ClassName: ChangeEventEmployeeCheckController
 * @Description:人事异动 审批流Controller
 * @author:liyj
 * @time:2016年12月30日 下午2:10:09
 */
@Controller
public class ChangeEventEmployeeCheckController {

	private static final Logger log = Logger
			.getLogger(ChangeEventEmployeeCheckController.class);

	@Resource(name = "changeEventEmployeeCheckService")
	private ChangeEventEmployeeCheckService changeEventEmployeeCheckService;

	/**
	 * 发起一个新的流程 存储数据
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @param Map
	 * @return
	 */

	@RequestMapping(value = { "/applynew/service" }, method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody HttpReponse applynew(
			@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to applynew ");
		}
		try {
			// 获取当前登陆的id
			Account account = (Account) request.getSession().getAttribute(
					"USER_OBJ");
			param.put("handler_id", account.getEmployeeId().toString());
			if (!changeEventEmployeeCheckService.insertChangeEvent(param)) {
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			if (!changeEventEmployeeCheckService.insertEventParam(param)) {
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			// 存入人事异动历史表中
			if (!changeEventEmployeeCheckService.insertChangeeventHt(param)) {
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			httpReponse.setError(false);
			httpReponse.setMessage("成功!");
		} catch (Exception e) {
			log.error("错误信息异常：", e);
			httpReponse.setError(true);
			httpReponse.setMessage("申请异常" + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("End to applynew");
		}
		return httpReponse;
	}

	/**
	 * @Descriptiom 提交下一步后开始更改一些字段
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/updateapply/service" }, method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody HttpReponse updateapply(
			@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to updateapply ");
		}
		try {
			Account account=(Account)request.getSession().getAttribute("USER_OBJ");
			param.put("handler_id", account.getEmployeeId());
			//人事异动表修改
			if(!changeEventEmployeeCheckService.updateChangeEvent(param)){
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			//人事异动参数表增加
			if(!changeEventEmployeeCheckService.insertoutcomeParam(param)){
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			//人事历史表增加
			if (!changeEventEmployeeCheckService.insertChangeeventHt(param)) {
				httpReponse.setError(true);
				httpReponse.setMessage("保存失败！");
				return httpReponse;
			}
			httpReponse.setError(false);
			httpReponse.setMessage("成功!");
		} catch (Exception e) {
			log.error("错误信息异常：", e);
			httpReponse.setError(true);
			httpReponse.setMessage("修改异常" + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("end to updateapply ");
		}
		return httpReponse;
	}	
	
	
}
