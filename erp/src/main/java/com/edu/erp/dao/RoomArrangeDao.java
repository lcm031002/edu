package com.edu.erp.dao;

import com.edu.erp.course_manager.business.*;
import com.edu.erp.model.StudentInfo;
import com.edu.erp.model.TRoom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zenglw on 2017/11/13.
 */
@Repository( value = "roomArrangeDao")
public interface RoomArrangeDao {

    /**
     * 查找占用教室
     * @param roomQO
     * @return
     */
    List<RoomOccupancyInfo> listOccupancyRoom(ListRoomQO roomQO);

    /**
     * 查找闲置教室
     * @param roomQO
     * @return
     */
    //List<TRoom> listIdleRoom(ListRoomQO roomQO);

    /**
     * 查询教室信息
     * @param roomQO
     * @return
     */
    List<RoomOccupancyInfo> listRoom(ListRoomQO roomQO);

    /**
     * 查询延课教室信息
     * @param roomQO
     * @return
     */
    List<RoomOccupancyInfo> listDelayRoom(ListRoomQO roomQO);

    /**
     * 通过{roomId} 查询教室在{startDate} 和 {endDate} 之间的排课情况
     * @param startDate 上课开始时间
     * @param endDate 上课结束时间
     * @param roomId 教室ID
     */
    List<RoomSchedulingInfo> listRoomSchedulingInfo(@Param("startDate") int startDate, @Param("endDate") int endDate, @Param("roomId") Long roomId);

    /**
     * 根据{courseId} 查询课程的报班学员
     * @param courseId 课程ID
     * @return 学员信息
     */
    List<StudentInfo> listStudentOfCourse(@Param("courseId") Long courseId);

   // String getOccupancyRoomInfo(@Param("courseDateInNum") int courseDateInNum, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("roomId") Long roomId);

    //@Update("update t_course_room_rel set room_id = #{roomId} where course_id = #{courseId} and seq = #{courseTime}")
    //int updateRoomSchedulingRelationship(@Param("courseId") Long courseId, @Param("courseTime") int courseTime,@Param("roomId") Long roomId);

    List<RoomSchedulingInfo> listRoomSchedulingInfoByDateAndRooms(@Param("qos") List<SchedulingOfRoomInOneDateQO> qos);

    List<CourseStudentRelationship> listCourseStudentRelationships(@Param("courseIds") String courseIds);
}
