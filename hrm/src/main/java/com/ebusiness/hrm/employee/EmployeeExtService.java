package com.ebusiness.hrm.employee;

import java.util.List;
import java.util.Map;

import com.ebusiness.hrm.model.EmployeeExt;

/**
 * @ClassName: EmployeeExtService
 * @Description: 员工档案服务
 * @author WP
 * @date 2016年9月28日 
 * 
 */
public interface EmployeeExtService {
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
	 * @Title: insertEmployeeExt
	 * @Description: 添加员工档案定义项
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean insertEmployeeExt(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: updateEmployeeExt
	 * @Description: 更新员工档案定义项
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return void 返回类型
	 */
	void updateEmployeeExt(EmployeeExt param) throws Exception;
	
	/**
	 * 
	 * @Title: removeEmployeeExt
	 * @Description: 启用/禁用
	 * 
	 * @throws Exception
	 *             设定文件
	 * @return boolean 返回类型
	 */
	boolean removeEmployeeExt(Integer id) throws Exception;
}
