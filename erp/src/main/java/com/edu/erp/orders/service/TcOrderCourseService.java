package com.edu.erp.orders.service;

public interface TcOrderCourseService {
    /**
     * 根据changeNo获取tab_change_course信息，并新增tc_order_course记录
     * 
     * @param changeNo
     */
    void addByChangeNo(String changeNo) throws Exception;
    
    void addLock(Long orderChangeId, Long orderCourseId) throws Exception;
    
    /**
     * 课程批改校验
     * 1. 校验课程批改课时是否大于剩余课时
     * 2. 校验批改课次是否已考勤或者被挂起
     * @param orderChangeId
     * @throws Exception
     */
    void checkSurplusCountAndAttendStatus(Long orderChangeId) throws Exception;

    /**
     * 订单批改校验
     * @param orderCourseId 订单课程ID
     * @throws Exception
     */
    void checkOrderChange(Long orderCourseId) throws Exception;

}
