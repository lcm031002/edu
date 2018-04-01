package com.edu.erp.orders.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.edu.erp.dao.TLockDao;
import com.edu.erp.dao.TOrderDao;
import com.edu.erp.dao.TabChangeCourseDao;
import com.edu.erp.model.TOrder;
import com.edu.erp.orders.service.OrderChangeCheckService;

@Service(value = "orderChangeCheckService")
public class OrderChangeCheckServiceImpl implements OrderChangeCheckService {
    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    @Resource(name = "tOrderDao")
    private TOrderDao tOrderDao;

    @Resource(name = "tabChangeCourseDao")
    private TabChangeCourseDao tabChangeCourseDao;

    @Override
    public boolean isOrderLock(Map<String, Object> paramMap) throws Exception {
        int count = this.tLockDao.queryLockOrderFlag(paramMap);
        return count > 0;
    }

    @Override
    public boolean isOrderLock(Map<String, Object> paramMap, String errorMsg) throws Exception {
        if (isOrderLock(paramMap)) {
            throw new Exception(StringUtils.isEmpty(errorMsg) ? "当前订单被锁定" : errorMsg);
        }
        return false;
    }

    @Override
    public boolean isOrderCanceled(Long orderId) throws Exception {
        TOrder order = this.tOrderDao.queryOrderInfo(orderId);
        return order != null && order.getOrder_status() == 0;
    }

    @Override
    public boolean isOrderCanceled(Long orderId, String errorMsg) throws Exception {
        if (isOrderCanceled(orderId)) {
            throw new Exception(StringUtils.isEmpty(errorMsg) ? "该订单已作废" : errorMsg);
        }
        return false;
    }

    @Override
    public boolean hasChangeCourseRecord(String changeNo) throws Exception {
        Integer count = this.tabChangeCourseDao.queryChangeCourseCount(changeNo);
        return count > 0;
    }

    @Override
    public boolean hasChangeCourseRecord(String changeNo, String errorMsg) throws Exception {
        if (hasChangeCourseRecord(changeNo)) {
            throw new Exception(StringUtils.isEmpty(errorMsg) ? "当前订单课程或其主订单课程或其子课程存在未完成的变更操作， 不能再次申请变更" : errorMsg);
        }
        return false;
    }

}
