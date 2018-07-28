package com.edu.erp.orders.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.TFeeDetailDao;
import com.edu.erp.model.TAttendance;
import com.edu.erp.model.TFee;
import com.edu.erp.model.TFeeDetail;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.orders.service.FeeDetailService;

/**
 * 费用明细服务
 *
 */
@Service(value = "feeDetailService")
public class FeeDetailServiceImpl implements FeeDetailService {
    private static final Logger log = Logger.getLogger(FeeDetailServiceImpl.class);

    @Resource(name = "tFeeDetailDao")
    private TFeeDetailDao tFeeDetailDao;

    @Override
    public int saveFeeDetail(TFeeDetail tFeeDetail) throws Exception {
        // TODO Auto-generated method stub
        return tFeeDetailDao.saveFeeDetail(tFeeDetail);
    }

    @Override
    public void deleteFeeDetail(HashMap<String, String> hashMap) throws Exception {
        tFeeDetailDao.deleteFeeDetail(hashMap);
    }

    @Override
    public List<TFeeDetail> queryFeeDetailByOrderId(Long order_id) throws Exception {
        TFeeDetail tFeeDetail = new TFeeDetail();
        tFeeDetail.setOrder_id(order_id);
        return tFeeDetailDao.queryFeeDetailByOrderId(tFeeDetail);
    }

    public void updateFeeIdByFee(TFeeDetail fee) throws Exception {
        tFeeDetailDao.updateFeeIdByFee(fee);
    }

    @Override
    public List<TFeeDetail> queryFeeDetailByChangeId(TFeeDetail tFeeDetail) throws Exception {
        return tFeeDetailDao.queryFeeDetailByChangeId(tFeeDetail);
    }

    @Override
    public void updateFeeIdByFeeChangeId(TFeeDetail fee) throws Exception {
        tFeeDetailDao.updateFeeIdByFeeChangeId(fee);
    }

    @Override
    public void createAttendFeeDetail(Long feeId, TAttendance attend, TOrderCourse orderCourse, Double attendAmount)
            throws Exception {
        TFeeDetail feeDetail = new TFeeDetail();
        feeDetail.setFee_id(feeId);
        feeDetail.setOrder_id(orderCourse.getOrder_id());
        feeDetail.setOrder_detail_id(orderCourse.getId());
        feeDetail.setFee_type(61L);
        feeDetail.setFee_flag(1L);
        feeDetail.setFee_amount(attendAmount);
        feeDetail.setCourse_sum(1L);
        feeDetail.setOperate_type(6L);
        feeDetail.setOperate_no(attend.getId());
        this.saveFeeDetail(feeDetail);
    }

}
