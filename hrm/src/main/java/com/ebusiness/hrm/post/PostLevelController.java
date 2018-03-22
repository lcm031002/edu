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
import com.ebusiness.hrm.model.PostLevel;
import com.klxx.common.util.PkUtil;

@Controller
public class PostLevelController {
	
	private static Logger log = Logger.getLogger(PostLevelController.class);
	
	@Resource(name="postLevelService")
	private PostLevelService postLevelService;

	@RequestMapping(value={"/common/postlevelservice"}, method=RequestMethod.GET)
	@ResponseBody
	public HttpReponse queryPostLevel(HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				List<PostLevel> list = postLevelService.queryPostLevel();
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
	
	@RequestMapping(value={"/common/postlevelservice"},method=RequestMethod.POST)
	@ResponseBody
	public HttpReponse addPostLevel(@RequestBody PostLevel param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setId(PkUtil.getUuid());
				param.setCreate_user(account.getId());
				param.setCreate_time(new Date());
				
				postLevelService.addPostLevel(param);
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
	
	@RequestMapping(value={"/common/postlevelservice"},method=RequestMethod.PUT)
	@ResponseBody
	public HttpReponse updatePostLevel(@RequestBody PostLevel param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setUpdate_user(account.getId());
				param.setUpdate_time(new Date());
				
				postLevelService.updatePostLevel(param);
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
