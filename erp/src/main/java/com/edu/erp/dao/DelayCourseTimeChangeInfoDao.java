package com.edu.erp.dao;

import com.edu.erp.course_manager.business.DelayCourseTimeChangeInfo;
import com.edu.erp.course_manager.business.DelayCourseQO;
import com.edu.erp.course_manager.business.DelayCourseVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zenglw on 2017/12/7.
 */
@Repository
public interface DelayCourseTimeChangeInfoDao {

    List<DelayCourseTimeChangeInfo> listCourseSchedulingDetail(Long courseId);

    List<DelayCourseVO> listDelayCourse(DelayCourseQO delayCourseQO);

    /**
     * 查找双师延课课程
     * @param delayCourseQO
     * @return
     */
    List<DelayCourseVO> listMTDelayCourse(DelayCourseQO delayCourseQO);

    void save(DelayCourseTimeChangeInfo delayCourseTimeChangeInfo);

    @Update("update t_course_scheduling set course_times = course_times-1,update_time=sysdate,update_user=#{updateUser} where course_id = #{courseId} and course_times > #{courseTime}")
    void updateCourseSchedulingCourseTimeReduceOne(@Param("courseId") Long courseId, @Param("courseTime") Integer courseTime, @Param("updateUser") Long updateUser);

    @Update("update t_course_scheduling set course_times = #{lastCourseTime},course_date = #{changeInfo.courseDateAfterDelay},week_number=#{weekNum}," +
            "start_time=#{changeInfo.startTimeAfterDelay},end_time = #{changeInfo.endTimeAfterDelay},update_time=sysdate,update_user=#{updateUser}" +
            " where course_id = #{courseId} and course_times=#{delayCourseTime}")
    void updateCourseSchedulingCourseTime(@Param("courseId") Long courseId, @Param("delayCourseTime") Integer delayCourseTime,
                                          @Param("lastCourseTime") Integer lastCourseTime, @Param("changeInfo") DelayCourseTimeChangeInfo changeInfo,
                                          @Param("weekNum") int weekNum, @Param("updateUser") Long updateUser);

    List<DelayCourseTimeChangeInfo> listCourseSchedulingChangeInfo(Long delayCourseId);
}
