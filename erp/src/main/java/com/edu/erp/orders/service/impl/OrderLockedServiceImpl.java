package com.edu.erp.orders.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.TOrderLockDao;
import com.edu.erp.model.TOrderLock;
import com.edu.erp.model.TabOrderInfo;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderLockedService;
import com.edu.erp.orders.service.OrderService;

/**
 * @ClassName: OrderLockedServiceImpl
 * @Description: 订单锁定
 *
 */
@Service(value = "orderLockedService")
public class OrderLockedServiceImpl implements OrderLockedService {

	@Resource(name = "tOrderLockDao")
	private TOrderLockDao tOrderLockDao;
	
	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService;
	
	@Resource(name = "orderService")
    private OrderService orderService;
	
	@Override
	public List<TOrderLock> queryLockedOrderList(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param, "查询参数不能为空！");
		Assert.notNull(param.get("bu_id"), "团队不能为空！");
		Assert.hasText((String)param.get("start_date"), "开始日期不能为空！");
		Assert.hasText((String)param.get("end_date"), "截止日期不能为空！");
		
		return tOrderLockDao.selectLockedOrderList(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void carryForward(Map<String, Object> param, Long userId) throws Exception {
		Assert.notEmpty(param, "结转参数不能为空！");
		Assert.notNull(param.get("order_ids"), "订单Id不能为空！");
		Assert.notEmpty((List<Integer>)param.get("order_ids"), "订单Id不能为空！");
		Assert.hasText((String)param.get("remark"), "备注不能为空！");
		
		TabOrderInfo orderInfo = null;
		TOrderLock tOrderLock = new TOrderLock();
		Map<String,Object> rs = null;
		for(Integer order_id : (List<Integer>)param.get("order_ids")) {
			if (order_id == null) {
				continue;
			}
			orderInfo = orderInfoService.queryOrderInfo(Long.valueOf(order_id));
			if(orderInfo == null) {
				continue;
			}
			// 只有锁定中的订单可以结转
			if (orderInfo.getLock_status() == null || orderInfo.getLock_status().intValue() != 1) {
				continue;
			}

			// 查询剩余课次以及价格
			tOrderLock.setOrderId(Long.valueOf(order_id));
			rs = tOrderLockDao.countOrderCourse(tOrderLock);
			tOrderLock.setSurplus_cost(Double.valueOf(rs.get("SURPLUS_COST").toString()));
			tOrderLock.setCourse_surplus_count(Long.parseLong(rs.get("COURSE_SURPLUS_COUNT").toString()));
			
			// 自动考勤掉剩余的课次
			orderService.attendedOrderTimes(Long.valueOf(order_id), userId);
			
			tOrderLock.setUpdater(userId);
			tOrderLock.setUpdateTime(DateUtil.getCurrDateOfDate("yyyy-MM-dd HH:mm:ss"));
			tOrderLock.setStatus(2l);
			tOrderLock.setRemark((String)param.get("remark"));
			tOrderLock.setOldstatus(orderInfo.getLock_status());
			// 更新订单锁定状态
			tOrderLockDao.updateOrderLockStatus(tOrderLock);
			// 新增订单历史锁定信息
			tOrderLockDao.insertOrderLockHt(tOrderLock);
		}
	}

}
