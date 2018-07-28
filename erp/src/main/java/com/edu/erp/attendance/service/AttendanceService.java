package com.edu.erp.attendance.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jbpm.api.ProcessEngine;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.erp.model.TAttendance;
import com.github.pagehelper.Page;

import net.sf.json.JSONObject;

/**
 * @ClassName: AttendanceService
 * @Description: 考勤服务
 *
 */
public interface AttendanceService {
    /**
     * 
     * @Title: attandanceBatchSubmit
     * @Description: 批量考勤服务
     * @param listMap
     *            待考勤学员列表
     * @param processEngine
     *            工作流引擎
     * @return 返回考勤的结果信息
     * @throws Exception
     *             设定文件 Map<String,Object> 返回类型
     *
     */
    void attandanceBatchSubmit(List<Map<String, Object>> listMap, ProcessEngine processEngine) throws Exception;

    /**
     * 考勤服务
     * 
     * @param attandanceObj
     *            待考勤学员信息
     * @param processEngine
     *            工作流引擎
     * @param lockInfos
     *            待审批信息
     * @throws Exception
     */
    void attandanceSubmit(Map<String, Object> attandanceObj, ProcessEngine processEngine,
            Map<String, Map<String, Object>> lockInfos) throws Exception;

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
     * 考勤检查：当前考勤课次存在退费审批，不能考勤
     * 
     * @param lockInfos
     *            审批信息
     * @param attandanceObj
     *            当前课次信息
     * @throws Exception
     */
    void checkLockInfo(Map<String, Map<String, Object>> lockInfos, Map<String, Object> attandanceObj) throws Exception;

    /**
     * 考勤检查：当前考勤课次存在退费审批，不能考勤
     * 
     * @param attandanceObj
     *            当前课次信息
     * @throws Exception
     */
    void checkLockInfo(Map<String, Object> attandanceObj) throws Exception;

    /**
     * 当前单据已考勤次数
     * 
     * @param paramMap
     *            order_course_id
     * @return
     * @throws Exception
     */
    Integer countAttend1ByOrderCourseId(Map<String, Object> paramMap) throws Exception;

    /**
     * 当前单据作为主单转班而来的子丹的已考勤次数 和 此单作为子单再转班数据
     * 
     * @param paramMap
     *            [order_course_id,root_course_id]
     * @return
     * @throws Exception
     */
    Integer countAttend2ByOrderCourseId(Map<String, Object> paramMap) throws Exception;

    /**
     * 当前单子作为子单已考勤的次数
     * 
     * @param paramMap
     *            [order_course_id,root_course_id]
     * @return
     * @throws Exception
     */
    Integer countAttend3ByOrderCourseId(Map<String, Object> paramMap) throws Exception;

    /**
     * 查询考勤教师组信息
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryTeacherLabelsPage(Map<String, Object> paramMap) throws Exception;

    /**
     * 
     * @Title: attandanceBatchSubmit
     * @Description: 晚辅导批量考勤服务
     * @param jsonObj
     *            待考勤学员列表
     * @param processEngine
     *            工作流引擎
     * @param request
     * @throws Exception
     *             设定文件 Map<String,Object> 返回类型
     *
     */
    void wfdAttandanceBatchSubmit(JSONObject jsonObj, ProcessEngine processEngine, HttpServletRequest request)
            throws Exception;

    /**
     * 晚辅导考勤服务
     * 
     * @param remark
     * @param course_date
     * 
     * @param jsonObj
     * @param processEngine
     * @param request
     * @return
     * @throws Exception
     */
    void wfdAttandanceSubmit(String course_date, String remark, JSONObject jsonObj, ProcessEngine processEngine,
            HttpServletRequest request) throws Exception;

    /**
     * 查询1对1的排课信息
     * 
     * @param queryParam
     * @return
     * @throws Exception
     */
    Page<TAttendance> queryYDYScheduleCoursePage(Map<String, Object> queryParam) throws Exception;

    /**
     * 一对一排课管理
     * 
     * @param tAttendance
     * @param orgModel
     * @throws Exception
     */
    ArrayList<TAttendance> addYDYScheduling(TAttendance tAttendance, OrgModel orgModel) throws Exception;
    
    /**
     * 一对一排课管理
     * 
     * @param tAttendanceList
     * @param orgModel
     * @throws Exception
     */
    ArrayList<TAttendance> addYDYSchedulingList(List<TAttendance> tAttendanceList, OrgModel orgModel) throws Exception;

    /**
     * 取消一对一课程排课
     * 
     * @param attend_ids
     * @throws Exception
     */
    void cancelYDYScheduling(String attend_ids) throws Exception;

    /**
     * 修改一对一排课信息
     * 
     * @param tAttendance
     * @throws Exception
     */
    void updateYDYScheduling(TAttendance tAttendance) throws Exception;

    /**
     * 查询晚辅导课程考勤记录
     * 
     * @param param
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryWfdCourseAttendRecord(Map<String, Object> param) throws Exception;

    /**
     * 考勤管理-一对一批量考勤
     * 
     * @param attendList
     *            待考勤列表
     * @param paramMap
     *            地区、校区及处理人编号信息
     * @throws Exception
     */
    void ydyAttendBatchSubmit(Collection<TAttendance> attendList, Map<String, Object> paramMap, ProcessEngine processEngine)
            throws Exception;
    
    /**查询对应课次是否已有挂起或正常上课的考勤记录
	 * @param paramMap   传入排课id
	 * @return
	 * @throws Exception
	 */
	public Integer countAttendBySchedulingId(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 一对一跨月置空审批通过处理
	 * @param attend
	 * @param param
	 * @throws Exception
	 */
	void do_kuayue_zhikong_ydy(TAttendance attend, Map<String, Object> param) throws Exception;

    /**
     * 更新考勤信息
     * @param attendance 待更新考勤信息
     * @throws Exception
     */
	void updateAttendance(TAttendance attendance) throws Exception;

    /**
     * 查询考勤冲突列表
     * @param attendance
     * @return
     */
    public List<TAttendance> queryYDYConflictScheduleList(TAttendance attendance) throws Exception ;

    /**
     * 查询打印信息
     * @param
     * @param attendListIds
     * @return
     * @throws Exception
     */
    List<TAttendance> queryPrintAttendanceInfo(String attendListIds) throws Exception;

}
