/**  
 * @Title: EmployeeServiceImpl.java
 * @Package com.edu.cas.client.common.employee.impl
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月13日 上午11:54:01
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.employee.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.edu.cas.client.common.employee.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.cas.client.common.employee.EmployeeService;
import com.edu.cas.client.common.model.Employee;
import com.edu.cas.client.dao.EmployeeDao;
import com.github.pagehelper.Page;

/**
 * @ClassName: EmployeeServiceImpl
 * @Description: 员工服务处理类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月13日 上午11:54:01
 * 
 */
@Service(value = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger log = Logger
			.getLogger(EmployeeServiceImpl.class);

	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.cas.client.common.employee.EmployeeService#queryById(java
	 * .lang.Long)
	 */
	public Employee queryById(Long id) throws Exception {
		Assert.notNull(id);
		Employee employee = new Employee();
		employee.setId(id);
		return employeeDao.queryEmployeeById(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.cas.client.common.employee.EmployeeService#update(com.edu
	 * .cas.client.common.model.Employee)
	 */
	public Employee update(Employee employee) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("begin to update employee,see :" + employee.toString());
		}

		Assert.notNull(employee);
		Assert.notNull(employee.getId());

		Employee employeeSrc = new Employee();
		employeeSrc.setId(employee.getId());

		employeeSrc = employeeDao.queryEmployeeById(employeeSrc);
		if (log.isDebugEnabled()) {
			log.debug("found employee info ,see :" + employee.toString());
		}
		if (null == employeeSrc) {
			log.error("error found,see:" + employee.toString());
			throw new IllegalArgumentException("error employee obj.see:"
					+ employee.toString());
		}

		// 员工编号是必填字典
		if (null != employee.getEncoding()
				&& !employee.getEncoding().equals(employeeSrc.getEncoding())) {
			employeeSrc.setEncoding(employeeSrc.getEncoding());
		}

		employeeSrc.setAddress(employee.getAddress());
		employeeSrc.setDescription(employee.getDescription());
		employeeSrc.setEmail(employee.getEmail());
		employeeSrc.setPhone(employee.getPhone());

		// 员工名称
		if (null != employee.getEmployeeName()
				&& !employee.getEmployeeName().equals(
						employeeSrc.getEmployeeName())) {
			employeeSrc.setEmployeeName(employee.getEmployeeName());
		}

		employeeSrc.setIdentificationCard(employee.getIdentificationCard());
		employeeSrc.setSex(employee.getSex());
		employeeSrc.setStatus(employee.getStatus());
		employeeSrc.setUpdateUser(employee.getUpdateUser());

		if (log.isDebugEnabled()) {
			log.debug("begin to update employee.");
		}
		employeeDao.updateEmployee(employeeSrc);
		if (log.isDebugEnabled()) {
			log.debug("end to update employee.");
		}
		return employeeSrc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.edu.cas.client.common.employee.EmployeeService#queryEmployeeList
	 * (java.util.Map)
	 */
	public Page<Employee> queryEmployeeList(Map<String, Object> param)
			throws Exception {
		Assert.notNull(param);
		Assert.notNull(param.get("cityId"));
		return employeeDao.queryEmployeeList(param);
	}

}
