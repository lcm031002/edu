package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourseRoomRel;

@Repository(value = "courseRoomRelDao")
public interface CourseRoomRelDao {

	List<TCourseRoomRel> selectForList(Map<String, Object> paramMap);

	TCourseRoomRel selectById(Long id) throws Exception;
	
	void insert(TCourseRoomRel pojo) ;
	
	void deleteById(TCourseRoomRel pojo) throws Exception;
	
	void deleteByCondition(TCourseRoomRel pojo);

	List<TCourseRoomRel> checkTimeConflict(Map<String, Object> paramMap);

	/**
	 * 批量绑定时检测课次是否已绑定教室
	 * @param tCourseRoomRel
	 * @return
	 * @throws Exception
	 */
    Long checkRoombind(TCourseRoomRel tCourseRoomRel) throws Exception;

	/**
	 * 更新录制任务视频编号
	 * @param courseRoomRel
	 * @throws Exception
	 */
	void updateVideoNo(TCourseRoomRel courseRoomRel) throws Exception;

    List<TCourseRoomRel> selectBindedList(Long courseId);

    Long selectRoomId(@Param("courseId") Long courseId, @Param("seq") Long seq) throws Exception;
}
