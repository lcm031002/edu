/**  
 * @Title: EmployeeDao.java
 * @Package com.ebusiness.cas.client.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月13日 上午11:54:27
 * @version KLXX ERPV4.0  
 */
package com.ebusiness.cas.client.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.cas.client.common.model.Employee;
import com.github.pagehelper.Page;

/**
 * @ClassName: EmployeeDao
 * @Description: 员工信息持久层
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月13日 上午11:54:27
 * 
 */
@Repository("employeeDao")
public interface EmployeeDao {
	
	/**
	 * 
	 * @Title: addEmployee
	 * @Description: 新增员工
	 * @param employee
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 */
	Employee addEmployee(Employee employee) throws Exception;

	/**
	 * 
	 * @Title: deleteEmployee
	 * @Description: 删除员工
	 * @param employee
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 */
	Employee deleteEmployee(Employee employee) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeById
	 * @Description: 通过id查询员工信息
	 * @param employee
	 *            员工信息
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 */
	Employee queryEmployeeById(Employee employee) throws Exception;

	/**
	 * 
	 * @Title: updateEmployee
	 * @Description: 更新员工信息
	 * @param employee
	 * @throws Exception
	 *             设定文件
	 * @return Employee 返回类型
	 * @throws
	 */
	void updateEmployee(Employee employee) throws Exception;
	
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

	
}
