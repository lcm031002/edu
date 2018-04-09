package com.edu.erp.employee.service;

import com.edu.erp.model.*;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface EmployeeMgrService {

    /**
     * @return PageInfo<Employee> 返回类型
     * @Title: queryEmployeeForPage
     * @Description: 查询员工档案信息列表
     */
    Page<EmployeeInfo> queryEmployeeForPage(Map<String, Object> params) throws Exception;

    /**
     * @return List<Map       <       String       ,               Object>> 返回类型
     * @Title: queryEmployee
     * @Description: 根据id查询员工档案
     */
    List<Map<String, Object>> queryEmployee(Long id) throws Exception;

    /**
     * @return List<Map       <       String       ,               Object>> 返回类型
     * @Title: queryFieldKey
     * @Description: 查询启用的员工信息字段名
     */
    List<Map<String, Object>> queryFieldKey() throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: insertEmployee
     * @Description: 添加员工档案
     */
    void insertEmployee(EmployeeInfo employeeInfo) throws Exception;

    /**
     * @return void 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployee
     * @Description: 更新员工档案
     */
    void updateEmployee(Map<String, Object> param) throws Exception;

    /**
     * @return List<Map       <       String       ,               Object>> 返回类型
     * @throws Exception 设定文件
     * @Title: queryEmploiyeeInfo
     * @Description: 根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
     */
    List<Map<String, Object>> queryEmployeeInfo(String searchInfo)
            throws Exception;

    /**
     * @return List<EmployeeEdu> 返回类型
     * @throws Exception 设定文件
     * @Title: queryEmployeeEdu
     * @Description:查询员工教育经历
     */
    List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addEmployeeEdu
     * @Description:添加员工教育经历
     */
    boolean addEmployeeEdu(EmployeeEdu param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeEdu
     * @Description:修改员工教育经历
     */
    boolean updateEmployeeEdu(EmployeeEdu param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: deleteEmployeeEdu
     * @Description:删除员工教育经历
     */
    boolean deleteEmployeeEdu(Long id) throws Exception;

    /**
     * @return List<EmployeeExperience> 返回类型
     * @throws Exception 设定文件
     * @Title: queryEmployeeExp
     * @Description:查询员工工作经历
     */
    List<EmployeeExperience> queryEmployeeExp(Long employee_id)
            throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addEmployeeExp
     * @Description:添加员工工作经历
     */
    boolean addEmployeeExp(EmployeeExperience param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeExp
     * @Description:修改员工工作经历
     */
    boolean updateEmployeeExp(EmployeeExperience param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: deleteEmployeeExp
     * @Description:删除员工工作经历
     */
    boolean deleteEmployeeExp(Long id) throws Exception;

    /**
     * @return List<EmployeeSummarize> 返回类型
     * @throws Exception 设定文件
     * @Title: queryEmployeeSum
     * @Description:查询员工工作总结
     */
    List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addEmployeeSum
     * @Description:添加员工工作总结
     */
    boolean addEmployeeSum(EmployeeSummarize param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeSum
     * @Description:修改员工工作总结
     */
    boolean updateEmployeeSum(EmployeeSummarize param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: deleteEmployeeSum
     * @Description:删除员工工作总结
     */
    boolean deleteEmployeeSum(Long id) throws Exception;

    /**
     * @return List<EmployeeReward> 返回类型
     * @throws Exception 设定文件
     * @Title: queryEmployeeRew
     * @Description:查询员工奖惩信息
     */
    List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addEmployeeRew
     * @Description:添加员工奖惩信息
     */
    boolean addEmployeeRew(EmployeeReward param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeRew
     * @Description:修改员工奖惩信息
     */
    boolean updateEmployeeRew(EmployeeReward param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: deleteEmployeeRew
     * @Description:删除员工奖惩信息
     */
    boolean deleteEmployeeRew(Long id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeStatic
     * @Description:修改员工任职/固定信息
     */
    boolean updateEmployeeStatic(Map<String, Object> param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: updateEmployeeImage
     * @Description:修改员工任职/固定信息
     */
    boolean updateEmployeeImage(Map<String, Object> param) throws Exception;

    /**
     * @param employeeId 员工ID
     * @return 员工对象
     * @throws Exception 设定文件 Employee 返回类型
     * @Title: queryEmployeeById
     * @Description: 通过员工ID查询员工信息
     */
    EmployeeInfo queryEmployeeById(Long employeeId) throws Exception;

    /**
     * @return List<Map       <       String       ,               Object>> 返回类型
     * @throws Exception 设定文件
     * @Title: queryPostByEmpId
     * @Description:按照员工id查询该员工绑定的岗位信息
     */
    List<Map<String, Object>> queryPostByEmpId(Long employee_id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: addEmpPost
     * @Description:添加员工岗位信息
     */
    boolean addEmpPost(Map<String, Object> param) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: removeEmpPost
     * @Description:启用禁用员工岗位
     */
    boolean removeEmpPost(Long id) throws Exception;

    /**
     * @return boolean 返回类型
     * @throws Exception 设定文件
     * @Title: setStatus
     * @Description:启用禁用员工状态
     */
    boolean setStatus(Long id) throws Exception;


    /**
     * 修改员工头像
     *
     * @param json
     * @throws Exception
     */
    String modifyPhoto(Map<String, String> json) throws Exception;

}
