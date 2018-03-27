package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TOrderCourseLog;
import com.edu.erp.model.TOrderCourseTimesLog;

/**
 * 订单课程备份表
 * @ClassName: TOrderCourseLogDao
 * @Description: 
 * @author chenyuanlong chenyl@klxuexi.org
 * @date 2017年4月12日 下午1:42:29
 *
 */
@Repository(value = "tOrderCourseLogDao")
public interface TOrderCourseLogDao {

	void insert(TOrderCourseLog orderCourseLog) throws Exception;
	/**
	 * 是否存在价格变动
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	Integer queryChangePriceNumByChangeId(Long changeId) throws Exception;
	/**
	 * 是否包含考勤
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer queryAttendNumsByChangeId(Map<String, Object> map) throws Exception;
	/**
	 * 是否有批改操作
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer queryChangesNumsByChangeId(Map<String, Object> map) throws Exception;
	/**
	 * 是否存在多笔冻结退费
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer queryTuiFeiNumsByChangeId(Map<String, Object> map) throws Exception;
	/**
	 * 是否存在多笔冻结退费
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer queryRootCourseTuiFeiNumsByChangeId(Map<String, Object> map) throws Exception;
	/**
	 * 插入课程订单备份表
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	Integer insertOrderCourseLog(Map<String, Object> map) throws Exception;
	/**
	 * 插入课程订单课时备份表
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	Integer insertOrderCourseTimesLog(Map<String, Object> map) throws Exception;
	/**
	 * 查询订单课程备份表
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseLog> queryTOrderCourseLogByChangeId(@Param("changeId") Long changeId) throws Exception;
	
	List<TOrderCourseTimesLog> queryTOrderCourseTimesLogByChangeId(@Param("changeId") Long changeId) throws Exception;
	/**
	 * 查询课程课次备份表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseTimesLog> queryTOrderCourseTimesLogByChangeId(Map<String, Object> map)  throws Exception;
	/**
	 * 查询课程备份表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourseLog> queryTOrderCourseLogByMap(Map<String, Object> map)  throws Exception;
}
