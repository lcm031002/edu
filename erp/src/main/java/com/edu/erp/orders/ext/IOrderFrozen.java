package com.edu.erp.orders.ext;

import java.util.Map;

import com.edu.erp.model.TOrderChange;

/**
 * 订单 冻结退费
 *
 */
public interface IOrderFrozen {
    /**
     * 冻结准备
     * 
     * @param refundMap
     *            退费订单信息
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    void readyPremium(Map<String, Object> refundMap, Long businessType) throws Exception;

    /**
     * 冻结
     * 
     * @param orderChange 订单变动
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    void frozenOrder(TOrderChange orderChange, Long businessType) throws Exception;
    /**
     * 冻结作废
     * 
     * @param orderChange 订单变动
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    void cancelFrozenOrder(TOrderChange orderChange, Long businessType) throws Exception;
}
