package com.ebusiness.hrm.dao;

import com.ebusiness.hrm.model.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



/**
 * @ClassName: EmployeeManageDao
 * @Description: 员工档案管理持久层
 * @author WP
 * @date 2016年10月13日 
 * 
 */
@Repository(value="employeeManageDao")
public interface EmployeeManageDao {

	/**
	 * 
	 * @Title: queryEmployeeForPage
	 * @Description: 查询员工档案信息列表
	 * 
	 * @return Page<Employee> 返回类型
	 */
	public Page<Employee> queryEmployeeForPage(Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @Title: queryEmployee
	 * @Description: 根据id查询员工档案
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployee(Map<String,Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: queryFieldKey
	 * @Description: 查询启用的员工信息字段名
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryFieldKey() throws Exception;
	
	/**
	 * 
	 * @Title: inserEmployee
	 * @Description: 新增员工
	 * 
	 * @return Integer 返回类型
	 */
	Integer insertEmployee(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployee
	 * @Description: 更新员工档案
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployee(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: queryEmployeeInfo
	 * @Description: 根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployeeInfo(@Param(value="searchInfo") String searchInfo) throws Exception;
	
	/**
	 * 
	 * @Title: queryEmployeeEdu
	 * @Description: 查询员工教育经历
	 * 
	 * @return List<EmployeeEdu> 返回类型
	 */
	List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmployeeEdu
	 * @Description: 添加员工教育经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer addEmployeeEdu(EmployeeEdu param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeEdu
	 * @Description: 修改员工教育经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeEdu(EmployeeEdu param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteEmployeeEdu
	 * @Description: 删除员工教育经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteEmployeeEdu(Long id) throws Exception;
	
	
	/**
	 * 
	 * @Title: queryEmployeeExp
	 * @Description: 查询员工工作经历
	 * 
	 * @return List<EmployeeExperience> 返回类型
	 */
	List<EmployeeExperience> queryEmployeeExp(Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmployeeExp
	 * @Description: 添加员工工作经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer addEmployeeExp(EmployeeExperience param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeExp
	 * @Description: 修改员工工作经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeExp(EmployeeExperience param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteEmployeeExp
	 * @Description: 删除员工工作经历
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteEmployeeExp(Long id) throws Exception;
	
	
	/**
	 * 
	 * @Title: queryEmployeeSum
	 * @Description: 查询员工工作总结
	 * 
	 * @return List<EmployeeSummarize> 返回类型
	 */
	List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmployeeSum
	 * @Description: 添加员工工作总结
	 * 
	 * @return Integer 返回类型
	 */
	Integer addEmployeeSum(EmployeeSummarize param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeSum
	 * @Description: 修改员工工作总结
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeSum(EmployeeSummarize param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteEmployeeSum
	 * @Description: 删除员工工作总结
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteEmployeeSum(Long id) throws Exception;
	
	
	/**
	 * 
	 * @Title: queryEmployeeRew
	 * @Description: 查询员工奖惩信息
	 * 
	 * @return List<EmployeeReward> 返回类型
	 */
	List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmployeeRew
	 * @Description: 添加员工奖惩信息
	 * 
	 * @return Integer 返回类型
	 */
	Integer addEmployeeRew(EmployeeReward param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeRew
	 * @Description: 修改员工奖惩信息
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeRew(EmployeeReward param) throws Exception;
	
	/**
	 * 
	 * @Title: deleteEmployeeRew
	 * @Description: 删除员工奖惩信息
	 * 
	 * @return Integer 返回类型
	 */
	Integer deleteEmployeeRew(Long id) throws Exception;

	
	/**
	 * 
	 * @Title: updateEmployeeStatic
	 * @Description: 修改员工任职信息
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeStatic(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 
	 * @Title: updateEmployeeImage
	 * @Description: 修改员工任职信息头像
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeImage(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	* @Title: queryEmployeeById
	* @Description: 查询用员工信息
	* @param employeeId 员工ID
	* @return 员工信息对象
	* @throws Exception    设定文件
	* Employee    返回类型
	*
	 */
	Employee queryEmployeeById(Long employeeId) throws Exception;
	
	/**
	 * 
	 * @Title: queryPostByEmpId
	 * @Description: 根据传入的员工id查询该员工绑定的岗位信息
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryPostByEmpId(@Param(value="employee_id")Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmpPost
	 * @Description: 添加员工岗位信息
	 * 
	 * @return Integer 返回类型
	 */
	Integer addEmpPost(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: removeEmpPost
	 * @Description: 启用禁用员工岗位
	 * 
	 * @return Integer 返回类型
	 */
	Integer removeEmpPost(Long id) throws Exception;
	
	/**
	 * 
	 * @Title: setStatus
	 * @Description: 启用禁用员工状态
	 * 
	 * @return Integer 返回类型
	 */
	Integer setStatus(Long id) throws Exception;
	
	/**
	 * 查询员工编码
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	List<Employee> queryEmployeeByEncoding(String encoding);

	/**
	 * 查询员工编码
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	List<Employee> queryOtherEmployeeByParam(Employee employee);
}
