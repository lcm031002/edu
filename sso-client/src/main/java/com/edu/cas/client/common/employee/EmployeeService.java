/**  
 * @Title: EmployeeService.java
 * @Package com.edu.cas.client.common.employee
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 上午11:40:40
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.employee;

import java.util.Map;

import com.edu.cas.client.common.model.Employee;
import com.github.pagehelper.Page;

/**
 * @ClassName: EmployeeService
 * @Description: 员工服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 上午11:40:40
 * 
 */
public interface EmployeeService {

	/**
	 * 
	 * @Title: queryById
	 * @Description: 通过员工id查询员工信息
	 * @param id
	 *            员工id
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 */
	Employee queryById(Long id) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeList
	 * @Description: 查询员工列表
	 * @param param
	 *            查询参数
	 * @return 返回符合条件的员工列表
	 * @throws Exception
	 *             设定文件 Page<Employee> 返回类型
	 * 
	 */
	Page<Employee> queryEmployeeList(Map<String, Object> param)
			throws Exception;

	/**
	 * 
	 * @Title: update
	 * @Description: 更新员工信息
	 * @param employee
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 */
	Employee update(Employee employee) throws Exception;
}
