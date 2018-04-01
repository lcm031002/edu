/**  
 * @Title: StudentInfoService.java
 * @Package com.ebusiness.erp.student.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:40:04
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service;

import com.edu.erp.student.business.StudentAccount;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.edu.erp.model.ReferenceStudent;
import com.edu.erp.model.ResponseObject;
import com.edu.erp.model.StudentBusiness;
import com.edu.erp.model.StudentInfo;
import com.github.pagehelper.Page;

/**
 * @ClassName: StudentInfoService
 * @Description: 学员信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:40:04
 * 
 */
public interface StudentInfoService {
    /**
     * @Title: queryStudents
     * @Description: 学员搜索服务，支持分页
     * @param queryParam
     *            学员查询信息对象
     * @throws Exception
     *             设定文件
     * @return List<StudentInfo> 返回类型
     */
    Page<StudentInfo> queryStudents(Map<String, Object> queryParam) throws Exception;

    StudentInfo queryStudentById(Map<String, Object> queryParam) throws Exception;

    Map<String, Object> queryStudentIndexAccount(Map<String, Object> queryParam) throws Exception;

    Map<String, Object> queryStudentCurrCounselors(Map<String, Object> queryParam) throws Exception;

    List<Map<String, Object>> queryStudentOrders(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentOrdersBJK(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentOrdersWFD(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentCourse(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentCourseTimes(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentOrdersDetail(Map<String, Object> param) throws Exception;

    boolean checkStudentName(Map<String, Object> param) throws Exception;

    void updateStudentInfo(StudentInfo studentInfo) throws Exception;

    boolean checkStudentContactPhone(Map<String, Object> paramMap) throws Exception;

    /**
     * 
     * @Title: queryStudentAllCourseTimes
     * @Description: 查询学员的账户剩余课时
     * @param param
     *            student_id：学员ID，bu_id：团队ID
     * @return 返回学员在团队下剩余的课时， COURSE_TOTAL_COUNT：总报班课时，
     *         COURSE_SURPLUS_COUNT：总剩余课时
     * @throws Exception
     *             设定文件 Map<String,Object> 返回类型
     *
     */
    Map<String, Object> queryStudentAllCourseTimes(Map<String, Object> param) throws Exception;

    /**
     * 学员主页-学员考勤-晚辅导 订单列表查询
     * @param paramMap 查询条件
     * @return 晚辅导订单列表
     * @throws Exception
     */
    List<Map<String, Object>> queryStudentCourseWfd(Map<String, Object> paramMap) throws Exception;

    
	/**
	 * 
	 * @Description: 根据学生ID查询学生信息
	 * @param studentInfo
	 *            学生ID
	 * @return StudentInfo 返回类型
	 * @throws Exception
	 *             异常信息
	 */
	StudentBusiness selectOneStudent(StudentBusiness studentInfo) throws Exception;
	
	/**
     * 暑假节点更新学生年级
     * 7月1日 把学生年级更新为：“新” + 下一年级 如：目前年级为初一，则更新为新初二
     * 9月1日把学生年纪更新为：下一年级 如：新初二更新为初二
     * @param isSummerNode 是否暑假节点 Y-是 N-否，暑假节点代表7月1日
     * @throws Exception
     */
    void upgradeStudentGrade(String isSummerNode) throws Exception;

    /**
     * 新增学员
     * 
     * @param studentInfo
     * @throws Exception
     */
	void addStudentInfo(StudentInfo studentInfo) throws Exception;

	Integer insertStudentRel(Map<String, Object> paramMap) throws Exception;

	void delStudentRel(Long id) throws Exception;
	
	void toAdd(StudentInfo studentInfo, HttpServletRequest request) throws Exception;

	/**
	 * 修改学生头像
	 * @param json
	 * @throws Exception
	 */
	String modifyPhoto(Map<String, String> json) throws Exception;

	/**
	 * 通过id查询学生信息
	 * @param id
	 */
	StudentInfo queryStudentById(Long id) throws Exception;

	/**
	 * 通过订单ID查询学生信息
	 * @param orderId
	 */
	StudentInfo queryStudentByOrderId(Long orderId) throws Exception;
	
	/**
	 * 更新学员联系方式
	 * @param student
	 * @throws Exception
	 */
	void updateStudentContact(StudentInfo student) throws Exception;
	
	/**
	 * 查询学员历史推荐人
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	List<ReferenceStudent> queryReferenceStudentHt(Long studentId) throws Exception;

	/**
	 * 查询学员
	 * @param studentName 姓名
	 * @param phones 电话
	 * @return
	 */
	List<StudentInfo> queryStudentByNameAndPhone( String studentName, String phones, Long studentId);

	void updateStudentLoginNo(StudentInfo studentInfo) throws Exception;

	/**
	 * 查询学员账户信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<StudentAccount> queryStudentAccounts(Map<String, Object> paramMap) throws Exception;

	/**
	 * 是否活跃学员
	 * @param studentId
	 * @return true-活跃 false-非活跃
	 */
	boolean isStudentActive(Long studentId);

}
