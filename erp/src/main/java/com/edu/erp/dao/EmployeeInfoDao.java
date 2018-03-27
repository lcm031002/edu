package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.EmployeeInfo;
import com.github.pagehelper.Page;

public interface EmployeeInfoDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Page<EmployeeInfo> page(Map<String, Object> page) throws Exception;

	/**
	 * 根据条件查询List<T>
	 * 
	 * @param param
	 *            动态参数
	 * @return
	 * @throws Exception
	 */
	List<EmployeeInfo> list(Map<String, Object> param) throws Exception;

	/**
	 * 新增
	 * 
	 * @param employeeInfo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toAdd(EmployeeInfo employeeInfo) throws Exception;

	/**
	 * 根据ID修改
	 * 
	 * @param employeeInfo
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toUpdate(EmployeeInfo employeeInfo) throws Exception;

	/**
	 * 根据ids字符串改变状态
	 * 
	 * @param ids
	 * @param status
	 * @throws Exception
	 * @return 影响行数
	 */
	Integer toChangeStatus(Map<String, Object> param) throws Exception;

	/**
	 * 新增关系
	 * 
	 * @param refs
	 * @return
	 * @throws Exception
	 */
	Integer toAddDepRef(List<Map<String, Long>> refs) throws Exception;

	/**
	 * 删除员工对应的关系
	 * 
	 * @param 员工IDS
	 * @return
	 * @throws Exception
	 */
	Integer toRemoveDepRef(String ids) throws Exception;

	/**
	 * 
	 * @Title: queryEmployeeInfo
	 * @Description: 通过组织机构ID和roleID获取对应的员工信息
	 * @param org_id
	 *            ID
	 * @param role_id
	 *            角色
	 * @throws Exception
	 *             设定文件
	 * @return EmployeeInfo 返回类型
	 */
	List<EmployeeInfo> queryEmployeeInfoByOrgAndRole(Long org_id, Long role_id)
			throws Exception;
	
	List<EmployeeInfo> selectListCounselor(Map<String, Object> param)throws Exception;
	
	List<EmployeeInfo> queryBranchHander(Integer event_id) throws Exception;
	List<EmployeeInfo> queryBranchHander3(Integer event_id) throws Exception;
	
	public EmployeeInfo queryEmpInfoById(EmployeeInfo obj);
	
	public EmployeeInfo queryEmpInfoByOrgIdAndId(Map<String, Object> param);

	List<Map<String, Object>> queryEmployByConditions(Map<String, Object> params)throws Exception;
	
	Integer queryEmpSupervioer(Integer employee_id)throws Exception;
	Integer queryChangeId(String execution_id)throws Exception;
}
