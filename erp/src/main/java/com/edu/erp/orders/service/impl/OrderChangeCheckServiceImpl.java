package com.edu.erp.orders.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.util.NumberUtils;
import com.edu.erp.dao.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.edu.erp.model.TOrder;
import com.edu.erp.orders.service.OrderChangeCheckService;
import org.springframework.util.CollectionUtils;

@Service(value = "orderChangeCheckService")
public class OrderChangeCheckServiceImpl implements OrderChangeCheckService {
    @Resource(name = "tLockDao")
    private TLockDao tLockDao;

    @Resource(name = "tOrderDao")
    private TOrderDao tOrderDao;

    @Resource(name = "tabChangeCourseDao")
    private TabChangeCourseDao tabChangeCourseDao;

    @Resource(name = "attendanceDao")
    private AttendanceDao attendanceDao;

    @Resource(name = "tOrderCourseDao")
    private TOrderCourseDao tOrderCourseDao;

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

    @Override
    public boolean hasAttendedCourseTimes(Long orderId, String errorMsg) throws Exception {
        Integer attendCourseTimes = this.attendanceDao.getOrderAttendCourseTimes(orderId);
        if (attendCourseTimes != null && attendCourseTimes > 0) {
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new Exception(errorMsg);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTransfer(Long orderId, String errorMsg) throws Exception {
        Integer surplusCount = this.tOrderCourseDao.queryTransCampusTransfer(orderId);
        if (surplusCount > 0) {
            if (StringUtils.isNotEmpty(errorMsg)) {
                throw new Exception(errorMsg);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkOrderChangeCount(Long orderId, String errorMsg) throws Exception {
        Integer orderChangeCount = this.tOrderCourseDao.queryOrderChangeCount(orderId);
        if (orderChangeCount != null && orderChangeCount > 0) {
            Map<String, Object> orderChangeCountInfoMap = this.tOrderCourseDao.queryOrderChangeCountInfo(orderId);
            if (!CollectionUtils.isEmpty(orderChangeCountInfoMap)) {
                Integer courseTotalCount = NumberUtils.object2Integer(orderChangeCountInfoMap.get("COURSE_TOTAL_COUNT"));
                Integer courseSurplusCount = NumberUtils.object2Integer(orderChangeCountInfoMap.get("COURSE_SURPLUS_COUNT"));
                if (courseTotalCount != null && courseSurplusCount != null && courseTotalCount.intValue() != courseSurplusCount) {
                    if (StringUtils.isNotEmpty(errorMsg)) {
                        throw new Exception(errorMsg);
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
