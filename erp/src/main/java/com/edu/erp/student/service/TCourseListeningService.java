/**  
 * @Title: TCourseListeningService.java
 * @Package com.ebusiness.erp.student.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年10月14日 下午3:10:13
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TCourseListening;
import com.github.pagehelper.Page;

/**
 * @ClassName: TCourseListeningService
 * @Description: 试听记录服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年10月14日 下午3:10:13
 *
 */
public interface TCourseListeningService {
	/**
	 * 
	 * @Title: queryListeningPage
	 * @Description: 查询学员的试听记录
	 * @param param 查询参数
	 * @return 试听记录列表
	 * @throws Exception    设定文件
	 * Page<Map<String,Object>>    返回类型
	 *
	 */
	Page<Map<String,Object>> queryListeningPage(Map<String,Object> param) throws Exception;
	
	void insertTCourseListening(Map<String,Object> param) throws Exception;
	
	void updateTCourseListening(Map<String,Object> param) throws Exception;
	
	List<Map<String,Object>> queryTCourseListeningSc(Map<String,Object> param) throws Exception;
	
	List<TCourseListening> queryListeningList(Map<String, Object> param) throws Exception;
}
