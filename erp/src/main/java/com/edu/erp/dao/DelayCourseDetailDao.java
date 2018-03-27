package com.edu.erp.dao;

import com.edu.erp.course_manager.business.DelayCourseDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zenglw on 2017/12/9.
 */
@Repository(value = "delayCourseDetailDao")
public interface DelayCourseDetailDao {

    @SelectKey(before = true,keyProperty = "id",resultType = Long.class,statementType = StatementType.STATEMENT,statement = "select seq_delay_course_detail.nextval as id from dual")
    @Insert("insert into tab_delay_course_detail(" +
            "id,course_id,course_time,start_date,end_date_before_delay,end_date_after_delay,delay_course_record_id,mt_course_id) " +
            "values(" +
            "#{id},#{courseId},#{courseTime},#{startDateBeforeDelay},#{endDateBeforeDelay},#{endDateAfterDelay},#{delayCourseRecordId},#{mtCourseId}" +
            ")")
    void save(DelayCourseDetail delayCourseDetail);

    @Update("update t_course set end_date = #{endDate} where id = #{courseId}")
    void updateCourseEndDate(@Param("courseId") Long courseId, @Param("endDate") String endDate);

    @Update("update tab_delay_course_detail set error_message = #{errorMessage} where id = #{id}")
    void saveErrorMessage(@Param("id") Long id, @Param("errorMessage") String errorMessage);

    List<DelayCourseDetail> listDelayCourseDetail(Long delayCourseRecordId);

    @Update("update tab_delay_course_detail set syn_error_message = #{synErrorMessage} where id = #{id}")
    void saveSynErrorMessage(@Param("id") Long id, @Param("synErrorMessage") String synErrorMessage);
}
