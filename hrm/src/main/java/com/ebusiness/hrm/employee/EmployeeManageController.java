package com.ebusiness.hrm.employee;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebusiness.cas.client.common.model.Account;
import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.model.Employee;
import com.ebusiness.hrm.model.EmployeeEdu;
import com.ebusiness.hrm.model.EmployeeExperience;
import com.ebusiness.hrm.model.EmployeeReward;
import com.ebusiness.hrm.model.EmployeeSummarize;
import com.ebusiness.hrm.model.PageParam;
import com.github.pagehelper.PageInfo;



/**
 * @ClassName: EmployeeManageController
 * @Description: 员工档案管理
 * @author WP
 * @date 2016年10月13日 
 * 
 */
@Controller
public class EmployeeManageController {
	private static Logger log = Logger.getLogger(EmployeeManageController.class);
	
	@Resource(name="employeeManageService")
	private EmployeeManageService employeeManageService;
	
	/**
	* 
	* @Title: queryEmployeeForPage
	* @Description: 查询员工档案列表
	* @param     设定文件
	* @return result    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/page"},method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> queryEmployeeForPage(HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query Employeepage");
		}
		Map<String, Object> result = new HashMap<String,Object>();
		PageInfo<Employee> pageInfo = new PageInfo<Employee>();
		PageParam pageParam = new PageParam();
		try{
			Employee params=new Employee();
			
			params.setDept(request.getParameter("dept"));
			params.setPost(Integer.valueOf(request.getParameter("post").trim()));
			params.setEnterType(Integer.valueOf(request.getParameter("enterType").trim()));
			params.setEmployee_name(request.getParameter("employee_name"));
			pageParam.setPageNum(Integer.valueOf(request.getParameter("pageNum").trim()));
			pageParam.setPageSize(Integer.valueOf(request.getParameter("pageSize").trim()));
			params.setPageParam(pageParam);
			
			String dept = params.getDept();
			Integer post = params.getPost();
			Integer enterType = params.getEnterType();
			String encoding = params.getEncoding();
			String employee_name = params.getEmployee_name();
			
			pageInfo = employeeManageService.queryEmployeeForPage(dept,post,
					enterType,encoding,employee_name,params.getPageParam());
			result.put("error", false);
			result.put("data", pageInfo.getList());
			if(pageInfo.getPages()==0){
				result.put("nodata", false);
			}else{
				result.put("nodata", true);
			}
			result.put("pageParam",
					new PageParam(pageInfo.getPageNum(),
							pageInfo.getPageSize(),pageInfo.getSize(),
							pageInfo.getTotal(),pageInfo.getPages()));			
		}catch(Exception e){
			log.error("Exception:"+e);
			result.put("error", true);
			result.put("msg", "查询员工信息失败");
		}
		if(log.isDebugEnabled()){
			log.debug("end to query Employeepage");
		}
		return result;
	}
	
	/**
	* 
	* @Title: queryEmployeeForPage
	* @Description: 查询员工档案列表
	* @param     设定文件
	* @return result    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/service"},method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryEmployeeById(HttpServletRequest request){
		if(log.isDebugEnabled()){
			log.debug("begin to query Employeepage");
		}
		Map<String, Object> result = new HashMap<String,Object>();
		
		try{
			String employeeIdString = request.getParameter("employeeId");
			if(StringUtils.isEmpty(employeeIdString)){
				log.error("非法查询,employeeId不能为空！");
				throw new Exception("非法查询！");
			}
			Employee employee = employeeManageService.queryEmployeeById(Long.parseLong(employeeIdString));
			result.put("error", false);
			result.put("data", employee);
		}catch(Exception e){
			log.error("error found,",e);
			result.put("error", true);
			result.put("message", "查询员工信息失败");
		}
		if(log.isDebugEnabled()){
			log.debug("end to query Employeepage");
		}
		return result;
	}
	
	
	/**
	* 
	* @Title: queryEmployee
	* @Description: 根据id查询员工档案
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployee(Long id){
		if(log.isDebugEnabled()){
			log.debug("begin to query Employee");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> list = employeeManageService.queryEmployee(id);
			httpReponse.setData(list);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query Employee");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: queryFieldKey
	* @Description: 查询启用的员工信息字段名
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/queryFieldKey/employeeservice"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryFieldKey(){
		if(log.isDebugEnabled()){
			log.debug("begin to query fieldKey");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String,Object>> list = employeeManageService.queryFieldKey();
			httpReponse.setData(list);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query fieldKey");
		}
		return httpReponse;
	}
	/**
	* 
	* @Title: addEmployee
	* @Description: 新增员工
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployee(@RequestBody Map<String, Object> param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to addEmployee");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.insertEmployee(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to addEmployee");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: updateEmployee
	* @Description: 更新员工档案
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployee(@RequestBody Map<String, Object> param,HttpServletRequest 
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update Employee");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			employeeManageService.updateEmployee(param);
			httpReponse.setError(false);
			httpReponse.setData("update");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update Employee");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: queryEmployeeInfo
	* @Description: 根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/employeeInfo"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeInfo(String searchInfo,HttpServletRequest
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query employeeInfo");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> result = employeeManageService.queryEmployeeInfo(searchInfo);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query employeeInfo");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: queryEmployeeEdu
	* @Description: 查询员工教育经历
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/edu"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeEdu(Long employee_id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query EmployeeEdu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<EmployeeEdu> result = employeeManageService.queryEmployeeEdu(employee_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query EmployeeEdu");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: addEmployeeEdu
	* @Description: 添加员工教育经历
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/edu"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployeeEdu(@RequestBody EmployeeEdu param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to add EmployeeEdu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.addEmployeeEdu(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addEmployeeEdu");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add EmployeeEdu");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: updateEmployeeEdu
	* @Description: 修改员工教育经历
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/edu"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeEdu(@RequestBody EmployeeEdu param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeEdu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeEdu(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeEdu");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeEdu");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: deleteEmployeeEdu
	* @Description: 删除员工教育经历
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/edu"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deleteEmployeeEdu(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete EmployeeEdu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.deleteEmployeeEdu(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteEmployeeEdu");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete EmployeeEdu");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: queryEmployeeExp
	 * @Description: 查询员工工作经历
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/exp"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeExp(Long employee_id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query EmployeeExp");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<EmployeeExperience> result = employeeManageService.queryEmployeeExp(employee_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query EmployeeExp");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: addEmployeeExp
	 * @Description: 添加员工工作经历
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/exp"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployeeExp(@RequestBody EmployeeExperience param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to add EmployeeEdu");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.addEmployeeExp(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addEmployeeExp");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add EmployeeExp");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: updateEmployeeExp
	 * @Description: 修改员工工作经历
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/exp"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeExp(@RequestBody EmployeeExperience param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeExp");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeExp(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeExp");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeExp");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: deleteEmployeeExp
	 * @Description: 删除员工工作经历
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/exp"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deleteEmployeeExp(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete EmployeeExp");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.deleteEmployeeExp(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteEmployeeExp");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete EmployeeExp");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: queryEmployeeSum
	 * @Description: 查询员工工作总结
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/sum"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeSum(Long employee_id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query EmployeeSum");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<EmployeeSummarize> result = employeeManageService.queryEmployeeSum(employee_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query EmployeeSum");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: addEmployeeSum
	 * @Description: 添加员工工作总结
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/sum"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployeeSum(@RequestBody EmployeeSummarize param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to add EmployeeSum");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.addEmployeeSum(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addEmployeeSum");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add EmployeeSum");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: updateEmployeeSum
	 * @Description: 修改员工工作总结
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/sum"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeSum(@RequestBody EmployeeSummarize param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeSum");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeSum(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeSum");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeSum");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: deleteEmployeeSum
	 * @Description: 删除员工工作总结
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/sum"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deleteEmployeeSum(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete EmployeeSum");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.deleteEmployeeSum(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteEmployeeSum");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete EmployeeSum");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: queryEmployeeRew
	 * @Description: 查询员工奖惩信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/rew"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryEmployeeRew(Long employee_id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<EmployeeReward> result = employeeManageService.queryEmployeeRew(employee_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query EmployeeRew");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: addEmployeeSum
	 * @Description: 添加员工奖惩信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/rew"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmployeeRew(@RequestBody EmployeeReward param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to add EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.addEmployeeRew(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addEmployeeRew");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add EmployeeRew");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: updateEmployeeRew
	 * @Description: 修改员工奖惩信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/rew"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeRew(@RequestBody EmployeeReward param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeRew(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeRew");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeRew");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: deleteEmployeeRew
	 * @Description: 删除员工奖惩信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/rew"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse deleteEmployeeRew(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.deleteEmployeeRew(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteEmployeeRew");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete EmployeeRew");
		}
		return httpReponse;
	}
	
	/**
	 * 
	 * @Title: updateEmployeeStatic
	 * @Description: 修改员工任职/固定信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/static"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeStatic(@RequestBody Map<String, Object> param,HttpServletRequest
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeStatic");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeStatic(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeStatic");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeStatic");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: updateEmployeeImage
	 * @Description: 修改员工任职/固定信息头像
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/image"},method=RequestMethod.PUT)
	public @ResponseBody HttpReponse updateEmployeeImage(@RequestBody Map<String, Object> param,HttpServletRequest
			request,HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to update EmployeeStatic");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.updateEmployeeImage(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("updateEmployeeStatic");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to update EmployeeStatic");
		}
		return httpReponse;
	}
	
	
	/**
	 * 图像上传重定向
	 * 
	 * @return
	 */
	@RequestMapping("/templates/hrm/uploadImage/headPic")
	public ModelAndView headPic(HttpServletRequest request){
		try{
			Long employee_id = Long.valueOf(request.getParameter("selectedEmpId").toString());
			request.setAttribute("employee_id", employee_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ModelAndView("templates/hrm/uploadImage/HeadPic");
	}
	
	
	/**
	 * 
	 * @Title: queryPostByEmpId
	 * @Description: 按照员工id查询该员工绑定的岗位信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/post"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryPostByEmpId(Long employee_id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to query EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> result = employeeManageService.queryPostByEmpId(employee_id);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query EmployeeRew");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: addEmpPost
	 * @Description: 添加员工岗位信息
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/post"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addEmpPost(@RequestBody Map<String, Object> param,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to add EmployeeRew");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			// 获取当前登陆的id
			Account account = (Account) request.getSession().getAttribute(
					"USER_OBJ");
			param.put("create_user", account.getId());
			boolean suc = employeeManageService.addEmpPost(param);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("addEmpPost");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to add EmployeeRew");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: removeEmpPost
	 * @Description: 启用禁用员工岗位
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/post"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse removeEmpPost(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to delete EmpPost");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.removeEmpPost(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("deleteEmployeeRew");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to delete EmpPost");
		}
		return httpReponse;
	}
	
	
	/**
	 * 
	 * @Title: setStatus
	 * @Description: 启用禁用员工状态
	 * @param     设定文件
	 * @return httpReponse    返回类型
	 * @throws
	 */
	@RequestMapping(value={"/employee/employeeservice/employeeInfo/status"},method=RequestMethod.DELETE)
	public @ResponseBody HttpReponse setStatus(Long id,HttpServletRequest request,
			HttpServletResponse response){
		if(log.isDebugEnabled()){
			log.debug("begin to set status");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			boolean suc = employeeManageService.setStatus(id);
			httpReponse.setError(suc?false:true);
			httpReponse.setData("setStatus");
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to set status");
		}
		return httpReponse;
	}

	@RequestMapping(value={"/employee/employeeservice/employeeInfo/img"},method=RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> modifyImg(@RequestBody Map<String, String> json,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", false);
		try {
			String modifyPhoto = employeeManageService.modifyPhoto(json);
			resultMap.put("url", modifyPhoto);
		} catch (Exception e) {
			log.error("error found,see:", e);
			resultMap.put("error", true);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
}
