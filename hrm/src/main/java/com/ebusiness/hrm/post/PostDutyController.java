package com.ebusiness.hrm.post;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.cas.client.common.util.WebContextUtils;
import com.ebusiness.hrm.model.PostDuty;
import com.klxx.common.util.PkUtil;

/**
 * 岗位档案
 * @ClassName: PostDutyController
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年8月16日 下午4:52:14
 *
 */
@Controller
public class PostDutyController {
	
	private static Logger log = Logger.getLogger(PostDutyController.class);
	
	@Resource(name="postDutyService")
	private PostDutyService postDutyService;

	@RequestMapping(value={"/common/postdutyservice"}, method=RequestMethod.GET)
	@ResponseBody
	public HttpReponse queryPostDuty(HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				List<PostDuty> list = postDutyService.queryPostDuty();
				httpReponse.setData(list);
			} else {
				log.error("user has not login ,and username not found.");
				httpReponse.setError(true);
				httpReponse.setMessage("user not found.");
			}
		}catch(Exception e){
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		return httpReponse;
	}
	
	@RequestMapping(value={"/common/postdutyservice/simple"}, method=RequestMethod.GET)
	@ResponseBody
	public HttpReponse querySimplePostDuty(HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				List<PostDuty> list = postDutyService.querySimplePostDuty();
				httpReponse.setData(list);
			} else {
				log.error("user has not login ,and username not found.");
				httpReponse.setError(true);
				httpReponse.setMessage("user not found.");
			}
		}catch(Exception e){
			log.error("error found,see:", e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		return httpReponse;
	} 
	
	@RequestMapping(value={"/common/postdutyservice"},method=RequestMethod.POST)
	@ResponseBody
	public HttpReponse addPostDuty(@RequestBody PostDuty param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setId(PkUtil.getUuid());
				param.setCreate_user(account.getId());
				param.setCreate_time(new Date());
				
				postDutyService.addPostDuty(param);;
			} else {
				log.error("user has not login ,and username not found.");
				httpReponse.setError(true);
				httpReponse.setMessage("user not found.");
			}
		}catch(Exception e){
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		return httpReponse;
	}
	
	@RequestMapping(value={"/common/postdutyservice"},method=RequestMethod.PUT)
	@ResponseBody
	public HttpReponse updatePostDuty(@RequestBody PostDuty param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setUpdate_user(account.getId());
				param.setUpdate_time(new Date());
				
				postDutyService.updatePostDuty(param);
			} else {
				log.error("user has not login ,and username not found.");
				httpReponse.setError(true);
				httpReponse.setMessage("user not found.");
			}
		}catch(Exception e){
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		return httpReponse;
	}
}
