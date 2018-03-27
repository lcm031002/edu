package com.edu.erp.dao;

import com.edu.erp.course_manager.business.DelayCourseRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zenglw on 2017/12/9.
 */
@Repository
public interface DelayCourseRecordDao {

    @SelectKey(before = true,keyProperty = "id",resultType = Long.class,statementType = StatementType.STATEMENT,statement = "select seq_delay_course_record.nextval as id from dual")
    @Insert("insert into tab_delay_course_record(id,delay_course_num,create_time,create_user,delay_course_date,reason,bu_id) " +
            "values(#{id},#{delayCourseNum},sysdate,#{createUser},#{delayCourseDate},#{reason},#{buId})")
    void save(DelayCourseRecord delayCourseRecord);

    List<DelayCourseRecord> listDelayCourseRecord(Map<String, Object> param);

    @Select("select id from tab_delay_course_record where id = #{id} for update")
    Integer getWriteLock(Long id);

    @Update("update tab_delay_course_record set error = #{error} where id = #{id}")
    void update(DelayCourseRecord delayCourseRecord);
}
