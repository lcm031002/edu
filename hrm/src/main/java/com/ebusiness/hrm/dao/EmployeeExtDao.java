package com.ebusiness.hrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.model.EmployeeExt;

/**
 * @ClassName: EmployeeExtDao
 * @Description: 员工档案定义项持久层
 * @author WP
 * @date 2016年9月28日 
 * 
 */
@Repository(value="employeeExtDao")
public interface EmployeeExtDao {

	/**
	 * 
	 * @Title: queryEmployeeExt
	 * @Description: 查询员工档案定义项
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployeeExt();
	
	/**
	 * 
	 * @Title: queryEmployeeExtField
	 * @Description: 查询员工档案定义项启用的字段信息
	 * 
	 * @return List<Map<String, Object>> 返回类型
	 */
	List<Map<String, Object>> queryEmployeeExtField();
	
	/**
	 * 
	 * @Title: inserEmployeeExt
	 * @Description: 新增员工档案定义项
	 * 
	 * @return Integer 返回类型
	 */
	Integer insertEmployeeExt(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: inserEmployeefield
	 * @Description: 新增员工档案定义字段到信息表TAB_EMPLOYEE_INFO
	 * 
	 * @return void 返回类型
	 */
	void insertEmployeefield(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeExt
	 * @Description: 更新员工档案定义项
	 * 
	 * @return Integer 返回类型
	 */
	Integer updateEmployeeExt(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: queryEmployeeField
	 * @Description: 根据id查询修改前员工扩展字段信息
	 * 
	 * @return EmployeeExt 返回类型
	 */
	EmployeeExt queryEmployeeField(Long id) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeField
	 * @Description: 更新员工档案信息表中字段名
	 * 
	 * @return void 返回类型
	 */
	Integer updateEmployeeField(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: removeEmployeeExt
	 * @Description: 禁用启用
	 * 
	 * @return Integer 返回类型
	 */
	Integer removeEmployeeExt(Integer id) throws Exception;
}
