package com.ebusiness.hrm.employee;

import java.util.List;
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

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.EmployeeExt;

/**
 * @ClassName: EmployeeExtController
 * @Description: 员工档案定义
 * @author WP
 * @date 2016年9月28日 
 * 
 */
@Controller
public class EmployeeExtController {
	private static Logger log = Logger.getLogger(EmployeeExtController.class);
	
	@Resource(name="employeeExtService")
	private EmployeeExtService employeeExtService;
	
	/**
	* 
	* @Title: queryEmployeeExt
	* @Description: 查询员工档案定义项
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmEmployee/hrmEmployeeExt/employee/employeeextservice"}, method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeExt(HttpServletRequest request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query employeeext");
		}
		HttpReponse httpReponse=new HttpReponse();
		try{
			List<Map<String, Object>> result = employeeExtService.queryEmployeeExt();						
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query employeeext");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: queryEmployeeExtField
	 * @Description: 查询员工档案定义项启用的字段信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/hrmEmployee/hrmEmployeeExt/employee/employeeextfieldservice"}, method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeExtField(HttpServletRequest request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query employeeextfield");
		}
		HttpReponse httpReponse=new HttpReponse();
		try{
			List<Map<String, Object>> result = employeeExtService.queryEmployeeExtField();						
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query employeeextfield");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: addEmployeeExt
	* @Description: 新增员工档案定义项
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmEmployee/hrmEmployeeExt/employee/employeeextservice"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployeeExt(@RequestBody EmployeeExt param,HttpServletRequest request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to addEmployeeExt");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeExtService.insertEmployeeExt(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to addEmployeeExt");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: updateEmployeeExt
	* @Description: 更新员工档案定义项
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmEmployee/hrmEmployeeExt/employee/employeeextservice"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeExt(@RequestBody EmployeeExt param,HttpServletRequest request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeExt");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			employeeExtService.updateEmployeeExt(param);
			httpReponse.setError(false);
			httpReponse.setData("update");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeExt");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: removeEmployeeExt
	* @Description: 启用/禁用
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmEmployee/hrmEmployeeExt/employee/employeeextservice"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse removeEmployeeExt(Integer id,HttpServletRequest request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to remove EmployeeExt");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeExtService.removeEmployeeExt(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("delete");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to remove EmployeeExt");
		}
		return httpReponse;
	}
}
