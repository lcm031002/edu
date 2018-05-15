package com.edu.erp.orders.service;

import java.util.List;
import java.util.Map;

import com.edu.erp.model.*;
import org.jbpm.api.ProcessEngine;

import com.github.pagehelper.Page;


public interface OrderChangeService {
	
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
	 * @Title: orderChangeRefund
	 * @Description: 订单退费
	 * @param refundObj 退费对象
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void orderChangeRefund(Map<String, Object> refundObj,ProcessEngine processEngine) throws Exception;
	/**
	 * 
	 * @Title: orderChangeFrozen
	 * @Description: 订单冻结
	 * @param processEngine 退费对象
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void orderChangeFrozen(Map<String, Object> frozenObj,ProcessEngine processEngine) throws Exception;
	
	/**
	 * 
	 * @Title: premiumAudit
	 * @Description: 退费审批通过
	 * @param refundObj 退费对象
	 * @throws Exception    设定文件
	 * void    返回类型
	 *
	 */
	void premiumAudit(Map<String, Object> refundObj) throws Exception;
	
	void orderChangeCheck(Map<String,Object> params)throws Exception ;
	
	/***
	 * Description ： 订单作废 service 接口
	 * @param orderInfo 订单信息
	 * @param remark	: 备注
	 * @param userId	：操作用户
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> cancelOrder(TabOrderInfo orderInfo, String remark, Long userId) throws Exception;
	
	/**
     * 退费单据-分页查询
     * @param param
     * @return
     * @throws Exception
     */
    Page<TOrderChange> queryRefundForPage(Map<String,Object> param) throws Exception;
    
    /**
     * 退费单据-详细的分页查询
     * @param param
     * @return
     * @throws Exception
     */
    Page<TCOrderCourse> queryRefundDetailForPage(Map<String,Object> param) throws Exception;
    
    /**
     * 退费单据-作废
     * @param remark
     * @param changeId
     * @param userId
     * @return
     * @throws Exception
     */
    void cancelRefund(String remark, Long changeId, Long userId) throws Exception;
    /**
     * 冻结退费-作废
     * @param remark
     * @param changeId
     * @param userId
     * @return
     * @throws Exception
     */
    void cancelFrozen(String remark, Long changeId, Long userId) throws Exception;
    /**
     * 退费单据-标记已取款
     * @param changeId
     * @param userId
     * @throws Exception
     */
    void drawRefund(Long changeId, Long userId) throws Exception;
    /**
	 * 根据退费单据编码查找单据课程信息
	 * @param change_no
	 * @return
	 * @throws Exception
	 */
	List<TOrderCourse> queryOrderCourseByChangeNo(String change_no)  throws Exception;
	/**
	 * 查询当前变更课程的主课程、当前课程、当前课程的子课程 变更记录数量
	 * @param param change_no(订单编号)
	 * @throws Exception
	 */
	Integer queryChangeCourseNum(Map<String,Object> param) throws Exception;
	  /**
     * 保存订单变动
     * @param tOrderChange
     * @return
     * @throws Exception
     */
    int saveOrderChange(TOrderChange tOrderChange) throws Exception;
    /**
     * 更新退费课程数据上 change_id
     * @param param order_id: 订单ID；change_id;change_no:单据编号
     * @throws Exception
     */
    void updateChangeIdOnChangeCourse(Map<String,Object> param) throws Exception;
    /**
     * 根据change_id查询出对应的订单批改信息
     * @param change_id
     * @return
     * @throws Exception
     */
    TOrderChange queryOrderChangeByChangId(Long change_id) throws Exception;
    /**
     * 
     * @param param  
     * premium_deduction_amount: 退费补扣金额;
     * fee_return_amount:退费金额
     * @throws Exception
     */
     void updateAmountByChangeId(Map<String,Object> param) throws Exception;
     /**
      * 更新状态
      * @param param
      * @throws Exception
      */
     void updateOrderChangeStatus(Map<String,Object> param) throws Exception;
     
 	/***
 	 * Description ： 订单作废 service 接口
 	 * @param remark	: 备注
 	 * @param orderId	：订单Id
 	 * @param userId	：操作用户
 	 * @return
 	 * @throws Exception
 	 */
 	Map<String,Object> doOrderChange_3(String remark,Long orderId,Long userId) throws Exception;
}


