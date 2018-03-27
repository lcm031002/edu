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

import com.edu.erp.model.TCChangeCourse;
import com.edu.erp.model.TOrderCourse;

/**
 * @ClassName: TOrderDao
 * @Description: 正式订单服务
 * @author zhuliyong zly@entstudy.com
 * @date 2017年1月19日 下午6:20:18
 * 
 */
@Repository(value = "tOrderCourseDao")
public interface TOrderCourseDao {
    TOrderCourse queryOrderCourseById(Long id) throws Exception;
    
	/**
	 * 
	 * @Title: queryOrderInfo
	 * @Description: 查询订单信息
	 * @param orderId
	 *            订单ID
	 * @return 订单信息对象
	 * @throws Exception
	 *             设定文件 TOrder 返回类型
	 * 
	 */
	List<TOrderCourse> queryOrderDetailInfo(Long orderId) throws Exception;
	
	/**
	 * 
	 * @Title: queryStudentOrderCourse
	 * @Description: 查询学员特定业务类型的课程 
	 * @param studentId 学员ID
	 * @param businessType 业务类型，1-班级课，2-1对1,3-晚辅导
	 * @return 返回指定业务类型的课程列表
	 * @throws Exception    设定文件
	 * List<TOrderCourse>    返回类型
	 *
	 */
	List<TOrderCourse> queryStudentOrderCourse(Map<String, Object> queryParam)
			throws Exception;

	List<TOrderCourse> queryOrderCourseByRootTimes(Map<String, Object> queryParam)
			throws Exception;
	
	List<TOrderCourse> queryOrderCoursePage(Long orderId) throws Exception;
	
	/**
	 * 查询订单课程关系
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourse> queryOrderCourse(Map<String, Object> queryParam) throws Exception;
	
	/**
	 * 根据退费单据编码查找单据课程信息
	 * @param change_no
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourse> queryOrderCourseByChangeNo(String change_no)  throws Exception;
	/**
	 * 查询出所有课程的剩余课时数
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourse> queryCourseSurplusCount(Map<String, Object> queryParam) throws Exception;
	/**
	 * 查询出所有课程的预结转值
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourse> queryManageFee(Map<String, Object> queryParam) throws Exception;
	/**
	 * 查询出页面退费课程数据
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<TCChangeCourse> queryTabChangeCourse(Map<String, Object> queryParam) throws Exception;
	/**
	 * 更新对应的订单课程价格
	 * @param queryParam
	 * @throws Exception
	 */
	void updateTOrderCoursePrice(Map<String, Object> queryParam) throws Exception;
	
	/**
	 * 更新订单课程信息，只用于更新某些特定字段
	 * @param orderCourse
	 * @throws Exception
	 */
	void updateOrderCourse(TOrderCourse orderCourse) throws Exception;
	
	/**
	 * 新增订单课程（Id由外部传入）
	 * @param orderCourse
	 * @throws Exception
	 */
	void insertOrderCourseWithId(TOrderCourse orderCourse) throws Exception;
	
	/**
	 * 获取一个系统生成的 订单课程Id
	 * @return
	 * @throws Exception
	 */
	Long getOrderCourseId() throws Exception;
	
	/**
     * 查询预结转
     * @param queryParam 查询条件
     * 查询条件：id 订单课程编号
     * 查询条件：rootCourseId 主单编号
     * @return 预结转金额
     * @throws Exception
     */
	Map<String, Object> queryTotalManageFee(Map<String, Object> paramMap) throws Exception;
    
    Long queryOrderIdByChangeNo(String change_no) throws Exception;
    
    /**
	 * 更新订单课程信息，只用于更新某些特定字段
	 * @param orderCourse
	 * @throws Exception
	 */
	void updateOrderCourseByOrderCourseLog(Map<String, Object> paramMap) throws Exception;

	/**
	 * 校验订单批改课时是否可以批改
	 * @param paramMap 校验条件 orderId：订单ID changeTimes：批改课时
	 * @return Y-可批改 N-不可批改
	 * @throws Exception
	 */
	String checkOrderChangeTimes(Map<String, Object> paramMap) throws Exception;

    /**
     * 班级课晚辅导考勤更新剩余
     * @param orderCourse
     * @return
     */
	Integer updateOrderCourseForBjkAttandence(TOrderCourse orderCourse);

	/**
	 * 查询订单课程信息，用于和双师系统信息进行比对，看两边信息是否一致
	 * @param paramMap 查询条件 courseId：主场课程ID stuCourseId：分场课程ID orderNo：订单编号
	 * @return 订单课程列表
	 * @throws Exception
	 */
	List<Map<String, Object>> queryOrderCourseList4Compare(Map<String, Object> paramMap) throws Exception;
}
