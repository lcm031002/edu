package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.EmployeeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TAttendanceHt;
import com.github.pagehelper.Page;

@Repository(value = "attendanceDao")
public interface AttendanceDao {

    /**
     * 
     * @Title: attandanceSubmit
     * @Description: 学员考勤
     * @param param
     * @throws Exception
     *             设定文件 void 返回类型
     * 
     */
    void attandanceSubmit(Map<String, Object> param) throws Exception;
    /**
     * 保存考勤记录
     * @param tAttendance
     * @throws Exception
     */
    void saveAttandance(TAttendance tAttendance) throws Exception;
    /**
     * 保存考勤历史记录
     * @param tAttendance
     * @throws Exception
     */
    void saveAttandanceHt(TAttendance tAttendance) throws Exception;

    /**
     * 
     * @Title: updateAttandRemark
     * @Description: 修改考勤的备注信息
     * @param param
     * @throws Exception
     *             设定文件 void 返回类型
     * 
     */
    void updateAttandRemark(Map<String, Object> param) throws Exception;
    
    /**
     * 更新考勤
     * @param tAttendance
     * @throws Exception
     */
    void updateAttendance(TAttendance tAttendance) throws Exception;
    
    /**
     * 更新考勤历史
     * @param tAttendance
     * @throws Exception
     */
    void updateAttendanceHt(TAttendance tAttendance) throws Exception;
    

    /***
     * 晚辅导学生考勤
     * 
     * @param param
     *            入参: p_order_course_id : 考勤订单课程ID p_scheduling_id : 排课ID
     *            p_attend_id : 考勤ID p_student_id : 学生ID p_attend_type : 考勤类型
     *            p_course_date : 课程日期 p_branch_id : 校区ID p_input_user : 操作人
     *            p_remark : 备注 出参: o_err_code : 错误编号 o_err_desc : 错误描述
     * @throws Exception
     */
    void wfdAttend(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> queryStudentAttendanceById(Map<String, Object> param) throws Exception;
    /**
     * 根据考勤ID查询出考勤的信息
     * @param attendId
     * @return
     * @throws Exception
     */
    TAttendance queryAttendanceInfoById(Long attendId) throws Exception;

    /**
     * 教师考勤 DAO 接口
     * 
     * @param param
     *            入参： P_attend_id :考勤ID p_teacher_id :教师id集合 p_attend_type
     *            :教师考勤状态 31：正常 30：置空 p_attend_date :考勤日期 p_scheduling_id :排课ID
     *            p_branch_id :考勤校区 p_input_user :操作人 p_remark :备注 出参：
     *            o_err_code :错误编号 o_err_desc :错误描述
     * @throws Exception
     */
    void teacherAttend(Map<String, Object> param) throws Exception;

    /***
     *
     * @param schId
     * @return
     * @throws Exception
     */
    Map<String, Object> querySchedulingById(String schId) throws Exception;

    List<Map<String, Object>> queryAttendCourseTimesDetails(Map<String, Object> paramMap) throws Exception;

    Integer countAttendBySchedulingId(Map<String, Object> paramMap) throws Exception;
    /**
     * 当前单据已考勤次数
     * @param paramMap  order_course_id
     * @return
     * @throws Exception
     */
    Integer countAttend1ByOrderCourseId(Map<String, Object> paramMap) throws Exception;
    /**
     * 当前单据作为主单转班而来的子丹的已考勤次数 和 此单作为子单再转班数据
     * @param paramMap [order_course_id,root_course_id]
     * @return
     * @throws Exception
     */
    Integer countAttend2ByOrderCourseId(Map<String, Object> paramMap) throws Exception;
    /**
     * 当前单子作为子单已考勤的次数
     * @param paramMap [order_course_id,root_course_id]
     * @return
     * @throws Exception
     */
    Integer countAttend3ByOrderCourseId(Map<String, Object> paramMap) throws Exception;

    Integer countAttendByStudent(Map<String, Object> paramMap) throws Exception;

    List<Map<String, Object>> checkVideoUploadStatus(Map<String, Object> param) throws Exception;

    List<Map<String, Object>> checkSaveExtralesson(Map<String, Object> param) throws Exception;

    int saveExtralesson(Map<String, Object> param) throws Exception;

    /**
     * 
     * @Title: queryAttendance
     * @Description: 查询课程的考勤信息
     * @param orderDetailId
     *            课程ID
     * @return 返回考勤信息对象
     * @throws Exception
     *             设定文件 List<TAttendance> 返回类型
     * 
     */
    List<TAttendance> queryAttendance(Long orderDetailId) throws Exception;

    /**
     * 
     * @Title: queryOrderAttendance
     * @Description: 查询订单考勤
     * @param orderId
     *            订单id
     * @return 订单考勤记录
     * @throws Exception
     *             设定文件 List<TAttendance> 返回类型
     * 
     */
    List<TAttendance> queryOrderAttendance(Long orderId) throws Exception;

    /**
     *
     * @Title: queryYDYOrderAttendance
     * @Description: 查询1对1订单考勤
     * @param orderId
     *            订单id
     * @return 订单考勤记录
     * @throws Exception
     *             设定文件 List<TAttendance> 返回类型
     *
     */
    List<TAttendance> queryYDYOrderAttendance(Long orderId) throws Exception;

    /**
     * 
     * @Title: queryHistAttendance
     * @Description: 查询考勤历史信息
     * @param orderDetailId
     *            详单id
     * @return 返回考勤的历史记录信息
     * @throws Exception
     *             设定文件 List<TAttendanceHt> 返回类型
     * 
     */
    List<TAttendanceHt> queryAttendanceHistory(Long orderDetailId) throws Exception;

    /**
     * 
     * @Title: queryOrderAttendanceHistory
     * @Description: 查询订单考勤历史
     * @param orderId
     *            订单id
     * @return 返回订单考勤历史记录
     * @throws Exception
     *             设定文件 List<TAttendanceHt> 返回类型
     * 
     */
    List<TAttendanceHt> queryOrderAttendanceHistory(Long orderId) throws Exception;

    /**
     *
     * @Title: queryYDYOrderAttendanceHistory
     * @Description: 查询1对1订单考勤历史
     * @param orderId
     *            订单id
     * @return 返回订单考勤历史记录
     * @throws Exception
     *             设定文件 List<TAttendanceHt> 返回类型
     *
     */
    List<TAttendanceHt> queryYDYOrderAttendanceHistory(Long orderId) throws Exception;

    /**
     * 晚辅导批量考勤页面每日考勤情况统计结果
     * 
     * @param paramMap
     *            查询条件
     * @return 考勤情况统计
     * @throws Exception
     */
    Page<Map<String, Object>> queryPageForWfj(Map<String, Object> paramMap) throws Exception;

    /**
     * 晚辅导待考勤学生查询
     * 
     * @param paramMap
     *            查询条件
     * @return 待考勤学生列表
     * @throws Exception
     */
    List<Map<String, Object>> queryStudentsForWfdAttn(Map<String, Object> paramMap) throws Exception;

    /**
     * 晚辅导待考勤教师查询
     * 
     * @param paramMap
     *            查询条件
     * @return 待考勤教师列表
     * @throws Exception
     */
    List<Map<String, Object>> queryTeachersForWfdAttn(Map<String, Object> paramMap) throws Exception;

    /**
     * 学员主页-学员考勤-晚辅导 学员考勤明细查询
     * 
     * @param paramMap
     *            查询条件 条件1 student_id 学生编号 条件2 order_id 订单编号
     * @return 学员考勤明细
     */
    List<Map<String, Object>> queryWfdDetails(Map<String, Object> paramMap) throws Exception;
    
    /**
     * 统计1对1的未考勤且已排课数
     * @param paramMap
     * @return
     * @throws Exception
     */
    Integer countYdyAttendByOrderCourseId(Map<String, Object> paramMap) throws Exception;
    /**
     * 考勤次数统计 本单考勤次数、主单考勤次数和子单考勤次数统计
     * @param paramMap
     * @return
     * @throws Exception
     */
    Long countAttendByOrderCourseId(Map<String, Object> paramMap) throws Exception;
    
    /**
     * 查询考勤教师组信息
     * @param paramMap
     * @return
     */
	List<Map<String, Object>> queryTeacherLabelsPage(Map<String, Object> paramMap);

	 /**
     * 
     * @Title: attandanceWfdSubmit
     * @Description: 学员晚辅导考勤
     * @param param
     * @throws Exception
     *             设定文件 void 返回类型
     * 
     */
	void attandanceWfdSubmit(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询1对1的排课信息
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	Page<TAttendance> queryYDYScheduleCoursePage(Map<String, Object> queryParam)throws Exception;
	
	/**
	 * 查询课程考勤记录
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryWfdCourseAttendRecord(Map<String, Object> param) throws Exception;
	
	void addHtByAttendanceId(Map<String, Object> paramMap) throws Exception;
	
	/**
     * 更新考勤历史的remark字段
     * @param param
     * @throws Exception
     */
	void updateAttendHTRemark(Map<String, Object> param) throws Exception;
	/**
	 * 统计排课时间是否有冲突
	 * @param tAttendance
	 * @return
	 * @throws Exception
	 */
	Integer queryYDYConflictScheduleTimes(TAttendance tAttendance) throws Exception;

    /**
     * 统计排课时间冲突列表
     * @param tAttendance
     * @return
     * @throws Exception
     */
    List<TAttendance> queryYDYConflictScheduleList(TAttendance tAttendance) throws Exception;

    /**
     * 查询考勤记录是否在审批中 大于0：审批中
     * @param id 考勤ID
     * @return
     * @throws Exception
     */
    Integer countAudit(Long id) throws Exception;

    /**
     * 获取补课预约码前缀 py-培英 sh-双师
     * @param param 订单课程id
     * @return 补课预约码前缀
     * @throws Exception
     */
    String queryResvCodePrefix(Map<String, Object> param) throws Exception;

    /**
     * 查询考勤的信息
     * @param
     * @return
     */
    List<TAttendance> queryPrintAttendanceInfo(@Param("ids") String ids);

    @Select(" select id from tp_attend_type b  where b.business_type=#{businessType,jdbcType=NUMERIC} and b.id = #{attendTypeId,jdbcType=NUMERIC}")
    Long queryAttendType(@Param("businessType") Long businessType, @Param("attendTypeId") Long attendTypeId);

    @Select("select id from t_order t where t.id = #{id,jdbcType=NUMERIC}")
    Long lockOrderById(Long id);

    @Select("select count(1) from t_lock where type=#{type,jdbcType=NUMERIC} and resource_id=#{orderId,jdbcType=NUMERIC}")
    Integer validExistLock(@Param("type") Integer type, @Param("orderId") Long orderId);

    TAttendance queryValidAttendanceId(@Param("studentId") Long studentId, @Param("schedualingId") Long schedualingId);

    Integer insertAttendanceBjk(TAttendance attendance);

    Integer updateAttendanceBjk(TAttendance attendance);

    Integer saveAttendanceHtForBjk(@Param("attendanceId") Long attendanceId, @Param("lastAttendType") Long lastAttendType);

    @Select("select * from t_attendance a where a.for_quit = 1 and a.student_id = #{studentId,jdbcType=NUMERIC} and a.scheduling_id = #{schedualingId,jdbcType=NUMERIC}")
    TAttendance getForQuitAttendance(@Param("studentId") Long studentId, @Param("schedualingId") Long schedualingId);

    Integer getOrderAttendCourseTimes(Long orderId);

}