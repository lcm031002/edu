package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TAttendanceHt;

/**
 * 
 * @author KL-LL
 * 
 */
public interface StudentAttendanceService {

	/**
	 * 学生考勤主接口
	 * 
	 * @param param
	 *            <pre>
	 * attendanceId : 考勤记录ID,可为空
	 * schedulingId : 排课表ID,不可为空
	 * studentId    : 学生ID,不可为空
	 * courseDate   : 上课日期,班级课和一对一可为空,晚辅导不可为空
	 * attendType   : 考勤状态类型,不可为空
	 * userId       : 用户,考勤人,不可为空
	 * branchId     : 校区,考勤校区,不可为空
	 * errorCode    : 出参,是否出错
	 * errorDesc    : 出参,错误信息
	 * </pre>
	 * @throws Exception
	 */
	void attandanceSubmit(Map<String,Object> param) throws Exception;
	void updateAttandRemark(Map<String,Object> param) throws Exception;
	List<Map<String,Object>>  queryStudentAttendanceById(Map<String,Object> param)throws Exception;
	
	/**8
	 * 批量考勤接口
	 * @param listMap
	 * @throws Exception
	 */
	Map<String,Object> attandanceBatchSubmit(List<Map<String,Object>> listMap) throws Exception;
	
	/***
	 * 晚辅导  学生考勤
	 * @param json 
	 * {"students":[{"order_course_id":"","scheduling_id":"","attend_id":"","student_id":"","attend_type":""}],"course_date":"","p_remark":""}
	 * @param userId 用户ID
	 * @param branchId	校区Id
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> wfdAttend(String json,Long userId,Long branchId)throws Exception;
	
	/***
	 * 教师考勤 service 接口
	 * @param json
	 * {teachers:[{"teacher_id":"教师ID","attend_type":"考勤状态","attend_id":""}],"attend_date":"上课日期","remark":"备注"}
	 * @param userId   用户Id
	 * @param branchId 校区ID
	 * @return
	 * @throws Exception
	 */
	void teacherAttend(String json,Long userId,Long branchId)throws Exception;
	
	/***
	 * 
	 * @param schId
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> querySchedulingById(String schId)throws Exception;

	/**
	 * 查询课次考勤记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryAttendCourseTimesDetails(Map<String,Object> paramMap)throws Exception;
	/**查询对应课次是否已有挂起或正常上课的考勤记录
	 * @param paramMap   传入排课id
	 * @return
	 * @throws Exception
	 */
	public Integer countAttendBySchedulingId(Map<String, Object> paramMap) throws Exception;
	List<Map<String, Object>> checkVideoUploadStatus(Map<String, Object> param) throws Exception;
	List<Map<String, Object>> checkSaveExtralesson(Map<String, Object> param) throws Exception;
	String saveExtralesson(Map<String, Object> param) throws Exception;
	
	/**
     * 学员主页-学员考勤-晚辅导 学员考勤明细查询
     * @return 学员考勤明细
     */
	List<Map<String, Object>> queryWfdDetails(Map<String, Object> paramMap) throws Exception;
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
    void saveAttandanceHt(TAttendanceHt tAttendanceHt) throws Exception;
	
	/**
	 * 考勤管理-晚辅导
	 * @param jsonObj
	 * @param userId
	 * @param branchId
	 * @throws Exception
	 */
	void wfdAttend(JSONObject jsonObj, Long userId, Long branchId) throws Exception;
	
	/**
     * 一对一考勤
     * 
     * @param attend 待考勤信息
     * @param paramMap 地区、校区、处理人信息
     * @throws Exception
     */
    void ydyAttend(TAttendance attend, Map<String, Object> paramMap) throws Exception;
    /**
     * 更新考勤历史的remark字段
     * @param param
     * @throws Exception
     */
	void updateAttandHTRemark(Map<String, Object> param) throws Exception;

    void attendanceBjk(Map<String, Object> param) throws Exception;
}
