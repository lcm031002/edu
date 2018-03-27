/**  
 * @Title: StudentInfoDao.java
 * @Package com.edu.erp.student.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:28:47
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import com.edu.erp.student.business.StudentAccount;
import java.util.List;
import java.util.Map;

import com.edu.erp.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

/**
 * @ClassName: StudentInfoDao
 * @Description: 学员信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:28:47
 * 
 */

@Repository(value = "studentInfoDao")
public interface StudentInfoDao {
    /**
     * @Title: queryStudents
     * @Description: 学员搜索服务，支持分页
     * @param queryParam
     *            学员查询信息对象
     * @throws Exception
     *             设定文件
     * @return Page<StudentInfo> 返回类型
     */
    Page<StudentInfo> queryStudents(Map<String, Object> queryParam) throws Exception;

    StudentInfo queryStudentById(Map<String, Object> queryParam) throws Exception;

    /**
     * 
     * @Title: queryStudentIndexAccount
     * @Description: 查询学员首页四个指标：试听次数、欠费金额、剩余课时、账户余额
     * @param param
     *            参数对象为Map格式， 参数1：必填项，studentId； 参数2：必填项，buId；
     * @throws Exception
     *             设定文件
     * @return Map<String,Object> 返回类型
     */
    Map<String, Object> queryStudentIndexAccount(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: queryStudentCurrCounselors
     * @Description: 查询学员当前的咨询师和学管师
     * @param param
     *            参数对象为Map格式， 参数1：必填项，studentId； 参数2：必填项，buId；
     * @throws Exception
     *             设定文件
     * @return Map<String,Object> 返回类型
     */
    Map<String, Object> queryStudentCurrCounselors(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: queryStudentOrders
     * @Description: 查询学员的报班单信息，分团队
     * @param param
     * @return
     * @throws Exception
     *             设定文件 List<Map<String,Object>> 返回类型
     * 
     */
    List<Map<String, Object>> queryStudentOrders(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentOrdersBJK(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentOrdersWFD(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentCourse(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentCourseTimes(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: queryStudentOrdersDetail
     * @Description: 查询学员的订单的详情
     * @param param
     *            参数1：必填项，studentId； 参数2：必填项，buId；
     * @return
     * @throws Exception
     *             设定文件 List<Map<String,Object>> 返回类型
     * 
     */
    List<Map<String, Object>> queryStudentOrdersDetail(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: checkStudentName
     * @Description: 检查学员名称或者手机号是否重复
     * @param param
     *            参数1：studentName，修改的目标学员名称 参数2：studentId，当前学员的ID
     *            参数3：phone，当前学员的手机号
     * 
     * @return
     * @throws Exception
     *             设定文件 Integer 返回类型
     * 
     */
    Integer checkStudentName(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: updateStudent
     * @Description: 更新学员的信息
     * @param student
     *            学员信息对象
     * @throws Exception
     *             设定文件 void 返回类型
     * 
     */
    void updateStudent(StudentInfo student) throws Exception;

    /**
     * 
     * @Title: checkStudentContactPhone
     * @Description: 查询学员的手机号的重复情况
     * @param paramMap
     * @return
     * @throws Exception
     *             设定文件 boolean 返回类型
     *
     */
    Integer checkStudentContactPhone(Map<String, Object> paramMap) throws Exception;

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
     * 学员主页-学员考勤-晚辅导 订单信息列表
     * 
     * @param paramMap
     *            查询条件
     * @return 订单信息列表
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
	StudentBusiness selectOneStudent(StudentBusiness studentInfo);
	
	/**
     * 暑假节点更新学生年级前记录更新历史，以便有问题可以回滚
     * @param isSummerNode  是否暑假节点 Y-是 N-否，暑假节点代表7月1日
     * @throws Exception
     */
    void addGradeUpgradeHis(String isSummerNode) throws Exception;
    
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

	Integer modifyPhoto(Map<String, Object> param)  throws Exception;

	/**
	 * 删除学员推荐关系
	 * @param id 学员id
	 * @throws Exception
	 */
	void delStudentRel(Long id) throws Exception;
	
	/**
	 * 更新学员默认联系方式
	 * @param studentInfo
	 */
	void updateStudentContace(StudentInfo studentInfo) throws Exception;

	/**
	 * 查询学员的最近推荐人
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	ReferenceStudent queryNewestRecord(Long studentId)throws Exception;

	/**
	 * 插入学员推荐人
	 * @param referenceStudent
	 * @throws Exception
	 */
	void insertNewReferenceStudent(ReferenceStudent referenceStudent)throws Exception;
	
	/**
	 * 查询学员推荐人历史
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	List<ReferenceStudent> queryReferenceStudentHt(Long studentId)throws Exception;

	/**
	 * 查询学员是否重复
	 * @param studentName
	 * @param buId
	 * @param phones
	 * @return
	 */
	Integer getRepeatStudentCount(@Param("studentName") String studentName, @Param("buId") Long buId, @Param("phones") String phones);


	/**
	 * 查询学员
	 * @param studentName 姓名
	 * @param phones 电话
	 * @return
	 */
	List<StudentInfo> queryStudentByNameAndPhone(@Param("studentName") String studentName, @Param("phones") String phones, @Param("studentId") Long studentId);

	List<StudentInfo> queryStuCourseSchedCount(Map<String, Object> paramMap) throws Exception;

	@Select(value = "select student_name from tab_student_info where id=#{id,jdbcType=NUMERIC}")
	StudentInfo queryById(@Param("id") Long id);

	@Select(value = "select old_stu_integral from tab_organization_info oi join tab_student_info si on oi.id = si.bu_id where si.id = #{studentId,jdbcType=NUMERIC} ")
	Long getIntegralThreshold(@Param("studentId") Long studentId);

	@Update("update tab_student_info set is_old_student=1 where id = #{studentId,jdbcType=NUMERIC}")
    Integer updateStudentToOld(Long student_id);

	StudentInfo queryStudentByOrderId(Long orderId) throws Exception;

	void saveStudentNameRecord(StudentNameReviseNote studentNameReviseNote);

	void saveStudentGradeRecord(StudentGradeReviseNote studentGradeReviseNote);

	void saveStudentStatusInfo(StudentStatusInfo studentStatusInfo);

	int  updateStudentStatus(StudentStatusInfo studentStatusInfo);

	int  ifStudentExist(StudentStatusInfo studentStatusInfo);

	StudentStatusInfo queryStudentStatusInfo(Map<String, Object> paramMap) throws  Exception;

    void saveStudentStatusReviseNote(StudentStatusReviseNote studentStatusReviseNote);

	/**
	 * 添加学员，默认设置学员为活跃学员
	 * @param studentId 学员ID
	 * @throws Exception
	 */
	void addStudentActive(Long studentId) throws Exception;

	void updateStudentLoginNo(StudentInfo studentInfo) throws Exception;

	/**
	 * 查询学员账户信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<StudentAccount> queryStudentAccounts(Map<String, Object> paramMap) throws Exception;

	void transStuAccount(Map<String, Object> paramMap) throws Exception;

	Integer queryStuActiveValue(Long studentId);

	int checkStudentselfPhone(Map<String, Object> param);
}
