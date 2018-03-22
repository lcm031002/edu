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
import com.ebusiness.hrm.model.PostRank;
import com.klxx.common.util.PkUtil;

@Controller
public class PostRankController {
	
	private static Logger log = Logger.getLogger(PostRankController.class);
	
	@Resource(name="postRankService")
	private PostRankService postRankService;

	@RequestMapping(value={"/common/postrankservice"}, method=RequestMethod.GET)
	@ResponseBody
	public HttpReponse queryPostRank(HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				List<PostRank> list = postRankService.queryPostRank();
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
	
	@RequestMapping(value={"/common/postrankservice/simple"}, method=RequestMethod.GET)
	@ResponseBody
	public HttpReponse querySimplePostRank(HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				List<PostRank> list = postRankService.querySimplePostRank();
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
	
	@RequestMapping(value={"/common/postrankservice"},method=RequestMethod.POST)
	@ResponseBody
	public HttpReponse addPostRank(@RequestBody PostRank param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setId(PkUtil.getUuid());
				param.setCreate_user(account.getId());
				param.setCreate_time(new Date());
				
				postRankService.addPostRank(param);
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
	
	@RequestMapping(value={"/common/postrankservice"},method=RequestMethod.PUT)
	@ResponseBody
	public HttpReponse updatePostRank(@RequestBody PostRank param, HttpServletRequest request){
		HttpReponse httpReponse = new HttpReponse();
		try{
			Account account = WebContextUtils.genUser(request);
			httpReponse.setError(false);
			if (null != account) {
				param.setUpdate_user(account.getId());
				param.setUpdate_time(new Date());
				
				postRankService.updatePostRank(param);
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
