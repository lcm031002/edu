package com.edu.erp.orders.service;

import java.util.HashMap;
import java.util.Map;

import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TFee;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;

/**
 * 费用总表服务
 *
 */
public interface FeeService {
    /**
     * 保存费用总表
     * 
     * @param fee
     * @return
     * @throws Exception
     */
    int saveFee(TFee fee) throws Exception;

    /**
     * 删除费用总表
     * 
     * @param hashMap
     * @throws Exception
     */
    void deleteFee(HashMap<String, String> hashMap) throws Exception;

    /**
     * 通过订单ID和订单类型更新->订单编号
     * 
     * @param hashMap
     * @throws Exception
     */
    void updateFeeEncoderIdByOrderId(HashMap<String, String> hashMap) throws Exception;

    /**
     * 通过订单ID和订单类型更新->费用状态，完成时间
     * 
     * @param hashMap
     * @throws Exception
     */
    void updateFeeStatusByOrderId(HashMap<String, Object> hashMap) throws Exception;

    /**
     * 通过订单据ID->费用状态，完成时间
     * 
     * @param hashMap
     * @throws Exception
     */
    void updateFeeStatusByEncoderId(HashMap<String, Object> hashMap) throws Exception;

    /**
     * 通过操作编码和操作类型查询->费用的总额
     * 
     * @param param
     *            operate_type;operate_no
     * @throws Exception
     */
    TFee queryFeeAmountByChangeId(Map<String, Object> param) throws Exception;

    /**
     * 通过订单ID和订单类型查询->费用的总额
     * 
     * @param hashMap
     * @throws Exception
     */
    TFee queryFeeAmountByOrderId(HashMap<String, String> hashMap) throws Exception;

    /**
     * 通过操作编码和操作类型更新-》订单编号
     * 
     * @param hashMap
     * @throws Exception
     */
    void updateFeeEncoderIdByChangeId(Map<String, Object> hashMap) throws Exception;

    /**
     * 标准退费
     * 
     * @param tOrderChange
     * @param hashMap
     * @throws Exception
     */
    void refundFee(TOrderChange tOrderChange) throws Exception;

    /**
     * VIP退费
     * 
     * @param tOrderChange
     * @param hashMap
     * @throws Exception
     */
    void refundVipFee(TOrderChange tOrderChange) throws Exception;

    /**
     * 学员考勤，生成考勤费用信息
     * 
     * @param attend
     * @param orderCourse
     * @throws Exception
     */
    void createAttendFee(TAttendance attend, TOrderCourse orderCourse, Double attendAmount) throws Exception;

    TFee queryFeeByOrderIdAndFeeType(Long orderId, Integer feeType);
}
