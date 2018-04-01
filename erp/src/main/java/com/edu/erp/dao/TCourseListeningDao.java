/**  
 * @Title: TCourseListeningDao.java
 * @Package com.ebusiness.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月14日 下午2:59:00
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourseListening;
import com.github.pagehelper.Page;

/**
 * @ClassName: TCourseListeningDao
 * @Description: 学员试听服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月14日 下午2:59:00
 *
 */
@Repository(value = "tCourseListeningDao")
public interface TCourseListeningDao {
	/**
	 * 
	 * @Title: queryListeningPage
	 * @Description: 查询学员的试听记录
	 * @param param 查询参数
	 * @return 试听记录列表
	 * @throws Exception    设定文件
	 * Page<TCourseListening>    返回类型
	 *
	 */
	Page<Map<String,Object>> queryListeningPage(Map<String,Object> param) throws Exception;
	
	void insertTCourseListening(Map<String,Object> param) throws Exception;
	
	void updateTCourseListening(Map<String,Object> param) throws Exception;
	
	List<Map<String,Object>> queryTCourseListeningSc(Map<String,Object> param) throws Exception;
	
	/**
	 * 财务处理-试听详情 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<TCourseListening> selectListeningList(Map<String, Object> param) throws Exception;
	
	/**
	 * 是否存在满足条件的试听 （学生Id，课程Id， 课次）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Integer isExist(Map<String,Object> param) throws Exception;

	/**
	 * 更新试听时间
	 * @param param
	 * @throws Exception
	 */
	void updateListeningDateByCourseSchedule(Map<String,Object> param) throws Exception;
}
