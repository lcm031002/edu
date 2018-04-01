package com.edu.erp.orders.ext;

import java.util.Map;

import com.edu.erp.model.TLadder;

/**
 * 订单退费
 *
 */
public interface IOrderRefund {
    /**
     * 退费准备
     * 
     * @param refundMap
     *            退费订单信息
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    void readyPremium(Map<String, Object> refundMap, Long businessType) throws Exception;

    /**
     * 退费
     * 
     * @param refundMap
     *            退费订单信息
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    void premium(Map<String, Object> refundMap, Long businessType) throws Exception;


    /**
     * 更新订单课程剩余可排课时信息
     * 退费审批中的课时不能进行排课，退费申请时剩余可排课时减去退费申请课时
     * @param refundMap 退费信息
     * @throws Exception
     */
    void updateOrderCourseScheduleCount(Map<String, Object> refundMap) throws Exception;
}
