/**  
 * @Title: StudentInfoService.java
 * @Package com.ebusiness.erp.student.service
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年9月14日 下午2:40:04
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.student.service;

import java.util.Map;

import com.edu.erp.model.StudentTraceInfo;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @ClassName: StudentTraceInfoService
 * @Description: 学员信息服务
 * @author zhuliyong zly@entstudy.com
 * @date 2016年9月14日 下午2:40:04
 * 
 */
public interface StudentTraceInfoService {

	Page<StudentTraceInfo> selectForPage(Map<String, Object> paramMap) throws Exception;

	StudentTraceInfo queryById(Long id) throws Exception;

	void add(StudentTraceInfo studentTraceInfo) throws Exception;

	void update(StudentTraceInfo studentTraceInfo) throws Exception;

	/**
	 * 查询教师课堂反馈信息
	 * @param paramMap 查询条件 上课起止日期
	 * @return 教师课堂反馈信息
	 * @throws Exception
	 */
	Map<String, Object> queryCourseInfo(Map<String, Object> paramMap) throws Exception;

	/**
	 * 上传学员跟踪附件信息
	 * @param studentTraceAttachs 附件信息
	 * @param traceId 学员跟踪信息ID
	 * @param userId 用户ID
	 * @throws Exception
	 */
	void uploadAttach(CommonsMultipartFile[] studentTraceAttachs, Long traceId, Long userId) throws Exception;

	/**
	 * 删除附件
	 * @param id 学员跟踪附件ID
	 * @throws Exception
	 */
	void deleteAttach(Long id) throws Exception;

}
