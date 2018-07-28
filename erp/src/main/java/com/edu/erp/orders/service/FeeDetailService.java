package com.edu.erp.orders.service;

import java.util.HashMap;
import java.util.List;

import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TFee;
import com.edu.erp.model.TFeeDetail;
import com.edu.erp.model.TOrderCourse;

/**
 * 费用明细服务
 *
 */
public interface FeeDetailService {
    /**
     * 保存费用明细
     * 
     * @param tFeeDetail
     * @return
     * @throws Exception
     */
    int saveFeeDetail(TFeeDetail tFeeDetail) throws Exception;

    /**
     * 删除费用明细
     * 
     * @param hashMap
     * @throws Exception
     */
    void deleteFeeDetail(HashMap<String, String> hashMap) throws Exception;

    /**
     * 通过订单ID查询出订单明细表的分组信息
     * 
     * @param order_id
     * @return
     * @throws Exception
     */
    List<TFeeDetail> queryFeeDetailByOrderId(Long order_id) throws Exception;

    /**
     * 通过订单ID和费用类型更新费用ID
     * 
     * @param fee
     * @throws Exception
     */
    void updateFeeIdByFee(TFeeDetail fee) throws Exception;

    /**
     * 通过changeId更新费用ID
     * 
     * @param fee
     * @throws Exception
     */
    void updateFeeIdByFeeChangeId(TFeeDetail fee) throws Exception;

    /**
     * 通过批改ID查询出费用明细表的信息
     * 
     * @param tFeeDetail
     * @return
     * @throws Exception
     */
    List<TFeeDetail> queryFeeDetailByChangeId(TFeeDetail tFeeDetail) throws Exception;

    /**
     * 学员考勤，生成考勤明细费用信息
     * 
     * @param attend
     * @param orderCourse
     * @throws Exception
     */
    void createAttendFeeDetail(Long feeId, TAttendance attend, TOrderCourse orderCourse, Double attendAmount)
            throws Exception;
}
