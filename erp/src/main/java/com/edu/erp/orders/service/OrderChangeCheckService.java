package com.edu.erp.orders.service;

import java.util.Map;

public interface OrderChangeCheckService {
    /**
     * 校验订单是否被锁定
     * @param paramMap
     * @throws Exception
     */
    boolean isOrderLock(Map<String, Object> paramMap) throws Exception;
    
    /**
     * 校验订单是否被锁定
     * @param paramMap
     * @param errorMsg 订单被锁定错误信息
     * @throws Exception
     */
    boolean isOrderLock(Map<String, Object> paramMap, String errorMsg) throws Exception;
    
    /**
     * 校验订单是否作废
     * @param orderId
     * @return
     * @throws Exception
     */
    boolean isOrderCanceled(Long orderId) throws Exception;
    
    /**
     * 校验订单是否作废
     * @param orderId
     * @param errorMsg 订单已作废错误信息
     * @return
     * @throws Exception
     */
    boolean isOrderCanceled(Long orderId, String errorMsg) throws Exception;
    
    boolean hasChangeCourseRecord(String changeNo) throws Exception;
    
    boolean hasChangeCourseRecord(String changeNo, String errorMsg) throws Exception;

    /**
     * 校验订单是否有考勤
     * @param orderId 订单ID
     * @param errorMsg 错误信息
     * @return
     * @throws Exception
     */
    boolean hasAttendedCourseTimes(Long orderId, String errorMsg) throws Exception;

    /**
     * 校验订单是否有转出课次
     * @param orderId 订单ID
     * @param errorMsg 错误信息
     * @return
     * @throws Exception
     */
    boolean hasTransfer(Long orderId, String errorMsg) throws Exception;

    boolean checkOrderChangeCount(Long orderId, String errorMsg) throws Exception;
}
