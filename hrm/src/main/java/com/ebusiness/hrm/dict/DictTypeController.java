package com.ebusiness.hrm.dict;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.DictType;
import com.ebusiness.hrm.model.DictTypeSub;

/**
 * 
 * @author WP
 *字典控制器
 */
@Controller
public class DictTypeController {
	private static Logger log = Logger.getLogger(DictTypeController.class);
	
	@Resource(name = "dictService")
	private DictService dictService;
	
	/**
	* 
	* @Title: queryDicTypes
	* @Description: 查询数据字典定义项
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
			@RequestMapping(value = "/hrmSystemSettings/hrmDicttype/dictservice",method = RequestMethod.GET)
			public @ResponseBody HttpReponse queryDicTypes( ) {
				if (log.isDebugEnabled()) {
					log.debug("begin to query user Dic info");
				}
				HttpReponse httpReponse = new HttpReponse();				
			
				try {
					List<Map<String,Object>> resultMap = dictService.queryDicTypes();
					httpReponse.setData(resultMap);
					httpReponse.setError(false);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to query user Dic info");
				}
				return httpReponse;
			}
			
			/**
			* 
			* @Title: addDictTypeData
			* @Description: 新增数据字典定义项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping( value = "/hrmSystemSettings/hrmDicttype/dictservice",method = RequestMethod.POST)
			public @ResponseBody HttpReponse addDicTypes(@RequestBody DictType item,
					HttpServletRequest reuest,HttpServletResponse response) {
				if (log.isDebugEnabled()) {
					log.debug("begin to add user Dic info");
				}
				HttpReponse httpReponse = new HttpReponse();
				try {					
					boolean suc = dictService.insertDicTypes(item);
					httpReponse.setError(suc?false:true);
					httpReponse.setData(item.getId());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to add user Dic info");
				}
				return httpReponse;
			}
			
			/**
			* 
			* @Title: queryDicDatas
			* @Description: 查询数据字典子项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping( value = "/hrmSystemSettings/hrmDicttype/dictservice/sub",method=RequestMethod.GET)
			public @ResponseBody HttpReponse queryDicDatas(Integer dictTypeId) {
				if (log.isDebugEnabled()) {
					log.debug("begin to query user DicSub info");
				}
				HttpReponse httpReponse = new HttpReponse();
				try {
					List<Map<String,Object>> resultMap = dictService.queryDicChildDatas(dictTypeId);
					httpReponse.setData(resultMap);
					httpReponse.setError(false);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to query user DicSub info");
				}
				return httpReponse;
			}		
			
			/**
			* 
			* @Title: updateDicData
			* @Description: 更新数据字典子项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping( value = "/hrmSystemSettings/hrmDicttype/dictservice/sub",method = RequestMethod.PUT)
			public @ResponseBody HttpReponse updateDicData(@RequestBody Map<String,Object> item) {
				if (log.isDebugEnabled()) {
					log.debug("begin to update user DicSub info");
				}
				HttpReponse httpReponse = new HttpReponse();
				try {
					boolean suc = dictService.updateDicData(item);
					httpReponse.setError(suc?false:true);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to update user DicSub info");
				}
				return httpReponse;
			}
			
			/**
			* 
			* @Title: addDicData
			* @Description: 添加数据字典子项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping( value = "/hrmSystemSettings/hrmDicttype/dictservice/sub",method = RequestMethod.POST)
			public @ResponseBody HttpReponse addDicData(@RequestBody DictTypeSub item,HttpServletRequest request,
					HttpServletResponse response) {
				if (log.isDebugEnabled()) {
					log.debug("begin to add user DicSub info");
				}
				HttpReponse httpReponse = new HttpReponse();
				try {
					
					boolean suc = dictService.insertDicData(item);
					httpReponse.setError(suc?false:true);
					httpReponse.setData(item.getId());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to add user DicSub info");
				}
				return httpReponse;
			}
			
			/**
			* 
			* @Title: deleteDicData
			* @Description: 删除数据字典子项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping( value = "/hrmSystemSettings/hrmDicttype/dictservice/sub",method = RequestMethod.DELETE)
			public @ResponseBody HttpReponse deleteDicData(Integer id) {
				if (log.isDebugEnabled()) {
					log.debug("begin to delete user DicSub info");
				}
				HttpReponse httpReponse = new HttpReponse();
				try {
					boolean suc = dictService.deleteDicData(id);
					httpReponse.setError(suc?false:true);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("error found ,see:", e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if (log.isDebugEnabled()) {
					log.debug("end to delete user DicSub info");
				}
				return httpReponse;
			}
			
			
			/**
			* 
			* @Title: queryDictSubAll
			* @Description: 查询数据字典所有子项
			* @param     设定文件
			* @return httpReponse    返回类型
			* @throws
			*/
			@RequestMapping(value={"/hrmSystemSettings/hrmDicttype/dictservice/suball"},method=RequestMethod.GET)
			public @ResponseBody HttpReponse queryDictSubAll(HttpServletRequest request,
					HttpServletResponse response){
				if(log.isDebugEnabled()){
					log.debug("begin to query DictSubAll");
				}
				HttpReponse httpReponse = new HttpReponse();
				try{
					Map<String, Object> result = dictService.queryDictSubAll();
					httpReponse.setData(result);
					httpReponse.setError(false);
				}catch(Exception e){
					e.printStackTrace();
					log.error("error found,see:",e);
					httpReponse.setError(true);
					httpReponse.setMessage(e.getMessage());
				}
				if(log.isDebugEnabled()){
					log.debug("end to query DictSubAll");
				}
				return httpReponse;
			}
}