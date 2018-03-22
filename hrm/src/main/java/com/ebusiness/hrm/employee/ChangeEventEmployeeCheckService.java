package com.ebusiness.hrm.employee;

import java.util.Map;

/**
 * 
 * @ClassName: ChangeEventEmployeeCheckService
 * @Description:TODO 人事异动审批流 接口
 * @author:liyj
 * @time:2016年12月30日 下午2:15:13
 */
public interface ChangeEventEmployeeCheckService {

	// 新申请一个工作流 存储数据
	boolean insertChangeEvent(Map<String, Object> param) throws Exception;

	// 人事异动参数存储
	boolean insertEventParam(Map<String, Object> param) throws Exception;

	// 人事异动处理历史存储
	boolean insertChangeeventHt(Map<String, Object> param) throws Exception;

	// 人事异动详情处理存储
	boolean updateChangeEvent(Map<String, Object> param) throws Exception;

	// 人事异动参数表 处理存储
	boolean insertoutcomeParam(Map<String, Object> param) throws Exception;

	
}
