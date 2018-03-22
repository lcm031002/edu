package com.ebusiness.hrm.employee;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.Employee;
import com.ebusiness.hrm.model.EmployeeEdu;
import com.ebusiness.hrm.model.EmployeeExperience;
import com.ebusiness.hrm.model.EmployeeReward;
import com.ebusiness.hrm.model.EmployeeSummarize;
import com.ebusiness.hrm.model.PageParam;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: EmployeeManageService
 * @Description: 员工档案管理服务
 * @author WP
 * @date 2016年10月13日
 * 
 */
public interface EmployeeManageService {

	/**
	 * 
	 * @Title: queryEmployeeForPage
	 * @Description: 查询员工档案信息列表
	 * 
	 * @return PageInfo<Employee> 返回类型
	 */
	PageInfo<Employee> queryEmployeeForPage(String dept, Integer post,
			Integer enterType, String encoding, String employee_name,
			PageParam pageParam) throws Exception;

	/**
	 * 
	 * @Title: queryEmployee
	 * @Description: 根据id查询员工档案
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployee(Long id) throws Exception;

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
	 * @Title: insertEmployee
	 * @Description: 添加员工档案
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean insertEmployee(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployee
	 * @Description: 更新员工档案
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	void updateEmployee(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @Title: queryEmploiyeeInfo
	 * @Description: 根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployeeInfo(String searchInfo)
			throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeEdu
	 * @Description:查询员工教育经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<EmployeeEdu> 返回类型
	 */
	List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception;

	/**
	 * 
	 * @Title: addEmployeeEdu
	 * @Description:添加员工教育经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addEmployeeEdu(EmployeeEdu param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeEdu
	 * @Description:修改员工教育经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeEdu(EmployeeEdu param) throws Exception;

	/**
	 * 
	 * @Title: deleteEmployeeEdu
	 * @Description:删除员工教育经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteEmployeeEdu(Long id) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeExp
	 * @Description:查询员工工作经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<EmployeeExperience> 返回类型
	 */
	List<EmployeeExperience> queryEmployeeExp(Long employee_id)
			throws Exception;

	/**
	 * 
	 * @Title: addEmployeeExp
	 * @Description:添加员工工作经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addEmployeeExp(EmployeeExperience param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeExp
	 * @Description:修改员工工作经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeExp(EmployeeExperience param) throws Exception;

	/**
	 * 
	 * @Title: deleteEmployeeExp
	 * @Description:删除员工工作经历
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteEmployeeExp(Long id) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeSum
	 * @Description:查询员工工作总结
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<EmployeeSummarize> 返回类型
	 */
	List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception;

	/**
	 * 
	 * @Title: addEmployeeSum
	 * @Description:添加员工工作总结
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addEmployeeSum(EmployeeSummarize param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeSum
	 * @Description:修改员工工作总结
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeSum(EmployeeSummarize param) throws Exception;

	/**
	 * 
	 * @Title: deleteEmployeeSum
	 * @Description:删除员工工作总结
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteEmployeeSum(Long id) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeRew
	 * @Description:查询员工奖惩信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<EmployeeReward> 返回类型
	 */
	List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception;

	/**
	 * 
	 * @Title: addEmployeeRew
	 * @Description:添加员工奖惩信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addEmployeeRew(EmployeeReward param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeRew
	 * @Description:修改员工奖惩信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeRew(EmployeeReward param) throws Exception;

	/**
	 * 
	 * @Title: deleteEmployeeRew
	 * @Description:删除员工奖惩信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean deleteEmployeeRew(Long id) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeStatic
	 * @Description:修改员工任职/固定信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeStatic(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @Title: updateEmployeeImage
	 * @Description:修改员工任职/固定信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean updateEmployeeImage(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeById
	 * @Description: 通过员工ID查询员工信息
	 * @param employeeId
	 *            员工ID
	 * @return 员工对象
	 * @throws Exception
	 *             设定文件 Employee 返回类型
	 * 
	 */
	Employee queryEmployeeById(Long employeeId) throws Exception;
	
	/**
	 * 
	 * @Title: queryPostByEmpId
	 * @Description:按照员工id查询该员工绑定的岗位信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryPostByEmpId(Long employee_id) throws Exception;
	
	/**
	 * 
	 * @Title: addEmpPost
	 * @Description:添加员工岗位信息
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean addEmpPost(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @Title: removeEmpPost
	 * @Description:启用禁用员工岗位
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean removeEmpPost(Long id) throws Exception;
	
	/**
	 * 
	 * @Title: setStatus
	 * @Description:启用禁用员工状态
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean setStatus(Long id) throws Exception;


	/**
	 * 修改员工头像
	 * @param json
	 * @throws Exception
	 */
	String modifyPhoto(Map<String, String> json) throws Exception;
}
