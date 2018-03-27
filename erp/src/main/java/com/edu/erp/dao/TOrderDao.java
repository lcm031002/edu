/**  
 * @Title: TOrderDao.java
 * @Package com.edu.erp.dao
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年1月19日 下午6:20:18
 * @version KLXX ERPV4.0  
 */
package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCourse;
import com.edu.erp.model.TOrder;
import com.edu.erp.model.TOrderCourse;
import com.github.pagehelper.Page;

/**
 * @ClassName: TOrderDao
 * @Description: 正式订单服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月19日 下午6:20:18
 *
 */
@Repository(value = "tOrderDao")
public interface TOrderDao {
	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 查询订单信息
	 * @param orderId 订单ID
	 * @return 订单信息对象
	 * @throws Exception    设定文件
	 * TOrder    返回类型
	 *
	 */
	TOrder queryOrderInfo(Long orderId) throws Exception;
	
	
	/**
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Map<String, Object>> selectForPage(Map<String, Object> param) throws Exception;
	
	Object queryTuiFeiByStudentId(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> queryWfdOrderDetails(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> qf_queryWfdOrderDetails(Map<String, Object> param) throws Exception;
	
	 List<TCourse> queryCourseByBusiness(TCourse tCourse) throws Exception ;
		
	/**
     * 更新订单状态
     * @param tOrder
     * @return
     * @throws Exception
     */
    int updateOrderStatus(TOrder tOrder) throws Exception;
    
    /**
     * 保存正式单据
     * @param tOrder
     * @return
     * @throws Exception
     */
    int saveOrderInfo(TOrder tOrder) throws Exception;
    
    /**
     * 保存订单课程信息
     * @param tOrder
     * @return
     * @throws Exception
     */
    int saveOrderCourse(TOrderCourse tOrderCourse) throws Exception;
    
    /**
     * 查询学生个性化报班总的报班课时
     * @param paramMap 查询参数
     * 条件：create_date 订单创建时间，该时间之后创建的订单课时总和
     * 条件：student_id 学生编号
     * @return 报班总课时
     * @throws Exception
     */
    Integer selectStuYdyTotalCourseCount(Map<String, Object> paramMap) throws Exception;
    
    /**
	 * 查询晚辅导套餐详情
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> queryWfdComboOrderDetails(Map<String, Object> param)  throws Exception;

	/**
	 * 查询晚辅导课程考勤记录
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryWfdCourseAttendRecord(Map<String, Object> param)  throws Exception;
	/**
	 * 通过变更记录ID查询订单详情
	 * @param changeId
	 * @return
	 * @throws Exception
	 */
	TOrder queryOrderInfoByChangeId(Long changeId)  throws Exception;
	
}
