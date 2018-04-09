package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository(value = "employeeInfoDao")
public interface EmployeeInfoDao {

    /**
     * @return Page<Employee> 返回类型
     * @Title: queryEmployeeForPage
     * @Description: 查询员工档案信息列表
     */
    Page<EmployeeInfo> queryEmployeeForPage(Map<String, Object> params) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryEmployee
     * @Description: 根据id查询员工档案
     */
    List<Map<String, Object>> queryEmployee(Map<String, Object> param) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryFieldKey
     * @Description: 查询启用的员工信息字段名
     */
    List<Map<String, Object>> queryFieldKey() throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: inserEmployee
     * @Description: 新增员工
     */
    Integer insertEmployee(EmployeeInfo employeeInfo) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: updateEmployee
     * @Description: 更新员工档案
     */
    Integer updateEmployee(Map<String, Object> param) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryEmployeeInfo
     * @Description: 根据传入的条件查询多个符合条件的员工id和编码encoding和员工名employeeName
     */
    List<Map<String, Object>> queryEmployeeInfo(@Param(value = "searchInfo") String searchInfo) throws Exception;

    /**
     * @return List<EmployeeEdu> 返回类型
     * @Title: queryEmployeeEdu
     * @Description: 查询员工教育经历
     */
    List<EmployeeEdu> queryEmployeeEdu(Long employee_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addEmployeeEdu
     * @Description: 添加员工教育经历
     */
    Integer addEmployeeEdu(EmployeeEdu param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeEdu
     * @Description: 修改员工教育经历
     */
    Integer updateEmployeeEdu(EmployeeEdu param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: deleteEmployeeEdu
     * @Description: 删除员工教育经历
     */
    Integer deleteEmployeeEdu(Long id) throws Exception;


    /**
     * @return List<EmployeeExperience> 返回类型
     * @Title: queryEmployeeExp
     * @Description: 查询员工工作经历
     */
    List<EmployeeExperience> queryEmployeeExp(Long employee_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addEmployeeExp
     * @Description: 添加员工工作经历
     */
    Integer addEmployeeExp(EmployeeExperience param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeExp
     * @Description: 修改员工工作经历
     */
    Integer updateEmployeeExp(EmployeeExperience param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: deleteEmployeeExp
     * @Description: 删除员工工作经历
     */
    Integer deleteEmployeeExp(Long id) throws Exception;


    /**
     * @return List<EmployeeSummarize> 返回类型
     * @Title: queryEmployeeSum
     * @Description: 查询员工工作总结
     */
    List<EmployeeSummarize> queryEmployeeSum(Long employee_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addEmployeeSum
     * @Description: 添加员工工作总结
     */
    Integer addEmployeeSum(EmployeeSummarize param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeSum
     * @Description: 修改员工工作总结
     */
    Integer updateEmployeeSum(EmployeeSummarize param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: deleteEmployeeSum
     * @Description: 删除员工工作总结
     */
    Integer deleteEmployeeSum(Long id) throws Exception;


    /**
     * @return List<EmployeeReward> 返回类型
     * @Title: queryEmployeeRew
     * @Description: 查询员工奖惩信息
     */
    List<EmployeeReward> queryEmployeeRew(Long employee_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addEmployeeRew
     * @Description: 添加员工奖惩信息
     */
    Integer addEmployeeRew(EmployeeReward param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeRew
     * @Description: 修改员工奖惩信息
     */
    Integer updateEmployeeRew(EmployeeReward param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: deleteEmployeeRew
     * @Description: 删除员工奖惩信息
     */
    Integer deleteEmployeeRew(Long id) throws Exception;


    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeStatic
     * @Description: 修改员工任职信息
     */
    Integer updateEmployeeStatic(Map<String, Object> param) throws Exception;


    /**
     * @return Integer 返回类型
     * @Title: updateEmployeeImage
     * @Description: 修改员工任职信息头像
     */
    Integer updateEmployeeImage(Map<String, Object> param) throws Exception;

    /**
     * @param employeeId 员工ID
     * @return 员工信息对象
     * @throws Exception 设定文件
     *                   Employee    返回类型
     * @Title: queryEmployeeById
     * @Description: 查询用员工信息
     */
    EmployeeInfo queryEmployeeById(Long employeeId) throws Exception;

    /**
     * @return List<Map   <   String   ,       Object>> 返回类型
     * @Title: queryPostByEmpId
     * @Description: 根据传入的员工id查询该员工绑定的岗位信息
     */
    List<Map<String, Object>> queryPostByEmpId(@Param(value = "employee_id") Long employee_id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: addEmpPost
     * @Description: 添加员工岗位信息
     */
    Integer addEmpPost(Map<String, Object> param) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: removeEmpPost
     * @Description: 启用禁用员工岗位
     */
    Integer removeEmpPost(Long id) throws Exception;

    /**
     * @return Integer 返回类型
     * @Title: setStatus
     * @Description: 启用禁用员工状态
     */
    Integer setStatus(Long id) throws Exception;

    /**
     * 查询员工编码
     *
     * @param encoding
     * @return
     * @throws Exception
     */
    List<EmployeeInfo> queryEmployeeByEncoding(String encoding);

    /**
     * 查询员工编码
     *
     * @param employee
     * @return
     * @throws Exception
     */
    List<EmployeeInfo> queryOtherEmployeeByParam(EmployeeInfo employee);
}
