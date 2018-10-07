package com.edu.erp.dao;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.TOrder;
import org.springframework.stereotype.Repository;

import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourseTimesLog;
import com.github.pagehelper.Page;

/**
 * @ClassName: TOrderChangeDao
 * @Description: 订单批改动作
 *
 */
@Repository(value = "tOrderChangeDao")
public interface TOrderChangeDao {
	/**
	 * 
	 * @Title: queryOrderChange
	 * @Description: 订单批改信息
	 * @param orderId
	 *            订单id
	 * @return 返回订单批改信息
	 * @throws Exception
	 *             设定文件 List<TOrderChange> 返回类型
	 * 
	 */
	List<TOrderChange> queryOrderChange(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderChangeTimes
	 * @Description: 查询订单的批改动作课次信息
	 * @param orderId
	 *            订单ID
	 * @return 订单批改记录信息
	 * @throws Exception
	 *             设定文件 List<TOrderChange> 返回类型
	 * 
	 */
	List<TCOrderCourse> queryOrderChangeTimes(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderCourseChange
	 * @Description: 查询课程的
	 * @param orderDetailId
	 *            订单详单ID
	 * @return 返回订单详单的批改记录
	 * @throws Exception
	 *             设定文件 List<TOrderChange> 返回类型
	 * 
	 */
	List<TCOrderCourse> queryOrderCourseChange(Long orderDetailId)
			throws Exception;

	/**
	 * 
	 * @Title: queryOrderChangeCourseTimes
	 * @Description: 查询课程的批改信息
	 * @param orderDetailId
	 *            详单id
	 * @return 返回课程的批改记录信息
	 * @throws Exception
	 *             设定文件 List<TOrderCourseTimesLog> 返回类型
	 * 
	 */
	List<TOrderCourseTimesLog> queryOrderChangeCourseTimes(Long orderDetailId)
			throws Exception;

	/**
	 * 
	 * @Title: queryPremiumAudit
	 * @Description: 查询订单的退费审批信息
	 * @param orderId
	 *            订单Id
	 * @return 订单审批信息
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryPremiumAudit(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: queryOrderUnForward
	 * @Description: 查询订单补结转
	 * @param orderId
	 *            订单id
	 * @return 补结转数据
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryOrderUnForward(Long orderId)
			throws Exception;

	/**
	 * 
	 * @Title: queryOrderForward
	 * @Description: 查询预结转
	 * @param orderId
	 *            订单ID
	 * @return 返回预结转数据
	 * @throws Exception
	 *             设定文件 List<Map<String,Object>> 返回类型
	 * 
	 */
	List<Map<String, Object>> queryOrderForward(Long orderId) throws Exception;

	/**
	 * 
	 * @Title: orderChangeTransfer
	 * @Description: 转班对象
	 * @param transferObj
	 * <br/>
	 *            入参： p_order_detail_id :转出订单分录Id <br/>
	 *            P_output_count :转出数量 <br/>
	 *            p_output_times :转出课次 <br/>
	 *            p_input_course_id :转入课程 (如果没有课程表示转一元)<br/>
	 *            p_input_branch_id :转入校区 <br/>
	 *            P_input_count :转入数量 <br/>
	 *            p_input_times :转入课次 <br/>
	 *            P_input_user :操作人 <br/>
	 *            p_encoding :编号 <br/>
	 *            P_remark :备注 <br/>
	 *            出参: o_err_code :错误代码 <br/>
	 *            o_err_desc :错误描述 <br/>
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void orderChangeTransfer(Map<String, Object> transferObj) throws Exception;

	/**
	 * 
	 * @Title: readyPremium
	 * @Description: 准备退费数据
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void readyPremium(Map<String, Object> premiumObj) throws Exception;
	
	/**
	 * 
	 * @Title: readyPremium
	 * @Description: 准备冻结退费数据
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void readyFrozenPremium(Map<String, Object> premiumObj) throws Exception;

	/**
	 * 
	 * @Title: readyVIPPremium
	 * @Description: 准备VIP退费数据
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void readyVIPPremium(Map<String, Object> premiumObj) throws Exception;

	/**
	 * 
	 * @Title: deleteReadyPremium
	 * @Description: 删除退费准备数据
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void deleteReadyPremium(Map<String, Object> premiumObj) throws Exception;

	/**
	 * 
	 * @Title: premium
	 * @Description: 退费申请提交
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void premium(Map<String, Object> premiumObj) throws Exception;
	
	/**
	 * 
	 * @Title: premium
	 * @Description: 冻结退费
	 * @param premiumObj
	 *            退费对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void premiumFrozen(Map<String, Object> premiumObj) throws Exception;
	
	/**
	 * 
	 * @Title: premiumAudit
	 * @Description: 退费审批
	 * @param premiumObj 审批对象
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	Map<String, Object> premiumAudit(Map<String, Object> premiumObj) throws Exception;
	/**
	 * 
	 * @Title: orderCancel
	 * @Description: 订单取消
	 * @param orderObj
	 *            订单对象
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void orderCancel(Map<String, Object> orderObj) throws Exception;

	/**
	 * 
	 * @Title: premiumCancel
	 * @Description: 退费取消
	 * @param premiumObj
	 *            退费单据信息
	 * @throws Exception
	 *             设定文件 void 返回类型
	 * 
	 */
	void premiumCancel(Map<String, Object> premiumObj) throws Exception;

	/**
	 * 
	 * @Title: getRemainCourseTimesCount
	 * @Description: 通过订单详单查询剩余课时
	 * @param orderDetailId
	 *            订单详单id
	 * @return 剩余课时
	 * @throws Exception
	 *             设定文件 Long 返回类型
	 * 
	 */
	Integer getRemainCourseTimesCount(Long orderDetailId) throws Exception;

	/**
	 * 
	 * @Title: getRemainUnAttendanceInfo
	 * @Description: 查询剩余未考勤的课次信息
	 * @param orderDetailId
	 *            详单id
	 * @return 未考勤课次信息
	 * @throws Exception
	 *             设定文件 Map<String,Object> 返回类型
	 * 
	 */
	List<Map <String,Object>> getRemainUnAttendanceInfo(Long orderDetailId)
			throws Exception;
	
	/***
	 * Description:订单作废 DAO 接口
	 * @param param
	 *  入参 :p_order_id              :订单ID
     *         p_input_user            :操作用户     
     *         p_encoding 			 :编号
     *	       P_remark                :备注               
     * 出参 :o_err_code              :错误编号
     *       o_err_desc              :错误描述  
	 * @throws Exception
	 */
	void cancelOrder(Map param) throws Exception;
	
	/**
	 * 校验是否可以做订单作废
	 * @param params
	 * @throws Exception
	 */
    void orderChangeCheck(Map<String, Object> params) throws Exception ;
    
    /**
     * 退费单据-分页查询
     * @param param
     * @return
     * @throws Exception
     */
    Page<TOrderChange> queryRefundForPage(Map<String, Object> param) throws Exception;
    
    /**
     * 冻结单据-分页查询
     * @param param
     * @return
     * @throws Exception
     */
    Page<TOrderChange> queryFrozenForPage(Map<String, Object> param) throws Exception;
    
    /**
     * 退费单据-详细的分页查询
     * @param param
     * @return
     * @throws Exception
     */
    Page<TCOrderCourse> queryRefundDetailForPage(Map<String, Object> param) throws Exception;
    
    /***
	 * Description:退费作废 DAO 接口
	 * @param param
	 *  入参 :p_change_id             :退费批改ID
     *       p_input_user            :操作用户     
     *      p_encoding 				 :编号
     *	     P_remark                :备注               
     *  出参 :o_err_code              :错误编号
     *       o_err_desc              :错误描述  
	 * @throws Exception
	 */
	void cancelRefund(Map<String, Object> param) throws Exception;
	 /***
		 * Description:冻结退费作废 DAO 接口
		 * @param param
		 *  入参 :p_change_id             :退费批改ID
	     *       p_input_user            :操作用户     
	     *      p_encoding 				 :编号
	     *	     P_remark                :备注               
	     *  出参 :o_err_code              :错误编号
	     *       o_err_desc              :错误描述  
		 * @throws Exception
		 */
	void cancelFrozen(Map<String, Object> param) throws Exception;
	
	/**
	 * 标记为已取款
	 * @param param
	 * @throws Exception
	 */
	void drawRefund(Map<String, Object> param) throws Exception;
	/**
	 * 查询当前变更课程的主课程、当前课程、当前课程的子课程 变更记录数量
	 * @param param change_no(订单编号)
	 * @throws Exception
	 */
	Integer queryChangeCourseNum(Map<String, Object> param) throws Exception;
	  /**
     * 保存订单变动
     * @param tOrderChange
     * @return
     * @throws Exception
     */
    int saveOrderChange(TOrderChange tOrderChange) throws Exception;
    
    /**
     * 更新订单变动
     * @param tOrderChange
     * @throws Exception
     */
    void updateOrderChange(TOrderChange tOrderChange) throws Exception;
    /**
     * 更新退费课程数据上 change_id
     * @param param order_id: 订单ID；change_id;change_no:单据编号
     * @throws Exception
     */
    void updateChangeIdOnChangeCourse(Map<String, Object> param) throws Exception;
    /**
     * 根据change_id查询出对应的订单批改信息
     * @param change_id
     * @return
     * @throws Exception
     */
    TOrderChange queryOrderChangeByChangId(Long change_id) throws Exception;
   /**
    * 更新冻结补扣的信息
    * @param param  
    * premium_deduction_amount: 退费补扣金额;
    * fee_return_amount:退费金额
    * @throws Exception
    */
    void updateAmountByChangeId(Map<String, Object> param) throws Exception;
    /**
     * 更新状态
     * @param param
     * @throws Exception
     */
    void updateOrderChangeStatus(Map<String, Object> param) throws Exception;
    
    /**
     * 更新WEB数据中的changeId
     * @param param
     * invalid_id  作废前的变动ID
     * change_id  产生的新作废变动单的ID
     * @throws Exception
     */
    void updateInvalidIdByOrderChangeId(Map<String, Object> param) throws Exception;
    
    void updateFeeAmountByChangeId(Map<String, Object> param) throws Exception;

	void updateAmountsByChangeId(Map<String, Object> param) throws Exception;
    /**
     * 更新操作人员信息
     * @param param
     * @throws Exception
     */
    void updateInputUserByOrderChangeId(Map<String, Object> param) throws Exception;
    /**
     * 查询退费作废前，除当前变更外是否订单有批改操作
     * @param param
     * @return
     * @throws Exception
     */
    Integer queryPGOrderChangeNums(Map<String, Object> param) throws Exception;
    
	/***
	 * Description:订单作废 DAO 接口
	 * @param param
	 *  入参 :p_order_id              :订单ID
     *       p_input_user            :操作用户     
     *       p_encoding 			 :编号
     *	     P_remark                :备注               
     *  出参 :o_err_code              :错误编号
     *       o_err_desc              :错误描述  
	 * @throws Exception
	 */
	void doOrderChange_3(Map param) throws Exception;

	/**
	 * 冻结作废，查询课次上课时间已过的课次数
	 * @param changeId 批改ID
	 * @return
	 * @throws Exception
	 */
	Integer queryExpiredChangeCourseTimesCount(Long changeId) throws Exception;

	TOrder queryOrderByChangeId(Long changeId);

	void updateAuditInfo(Map<String, Object> paramMap);
}
